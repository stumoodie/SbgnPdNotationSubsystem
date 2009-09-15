package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.EnumSet;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IRootNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeAttribute;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IStatefulEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode.SidednessType;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;

public class BoParser implements IBoParser {
	private static final EnumSet<TreeTokenType> STATEFUL_EPN_CHILD_NODES
		= EnumSet.of(TreeTokenType.STATE_DESCN, TreeTokenType.UOI, TreeTokenType.CARDINALITY);
	
	private static final EnumSet<TreeTokenType> PROCESS_NODES
		= EnumSet.of(TreeTokenType.PROCESS, TreeTokenType.UNSPECIFIED_PROCESS, TreeTokenType.OMITTED_PROCESS,
					TreeTokenType.ASSOCIATION, TreeTokenType.DISOCCIATION, TreeTokenType.PHENOTYPE);

	private static final EnumSet<TreeTokenType> STATEFUL_EPNS
		= EnumSet.of(TreeTokenType.COMPLEX, TreeTokenType.MACROMOLECULE, TreeTokenType.NA_FEATURE);

	private static final EnumSet<TreeTokenType> EPN_NODES
		= EnumSet.of(TreeTokenType.SIMPLE_CHEMICAL,	TreeTokenType.UNSPECIFIED_ENTITY);
	
	private ITreeLexer lexer;
	private final INdomBuilder builder;
	
	public BoParser(INdomBuilder builder){
		this.builder = builder;
	}

	public void parse(ITreeLexer lexer) throws TreeParseException{
		this.lexer =lexer;
		rootsRule();
	}
	
	public INdomBuilder getNDomBuilder(){
		return this.builder;
	}
	
	private void rootsRule() throws TreeParseException{
		IMapDiagram mapDiagram = mapNodesRule();
		mapEdgesRule(mapDiagram);
		if(this.lexer.hasRightTokens()){
			throw new TreeParseException(this.lexer.getCurrent(), "There are unread tokens");
		}
		this.builder.buildComplete();
	}
	
	private IMapDiagram mapNodesRule() throws TreeParseException{
		lexer.match(TreeTokenType.NODE_ROOT);
		lexer.down();
		IMapDiagram retVal = mapDiagramRule();
		lexer.up();
		return retVal;
	}
	
	private IMapDiagram mapDiagramRule() throws TreeParseException{
		lexer.match(TreeTokenType.MAP_DIAGRAM);
		IRootNode rootNode = this.lexer.getCurrent().getTypedElement();
		this.builder.createMap(rootNode.getModel().getCanvas());
		IMapDiagram mapDiagram = this.builder.getNdom();
		lexer.down();
		mapChildrenRule(mapDiagram);
		lexer.up();
		return mapDiagram;
	}
	
	
	private void mapChildrenRule(IMapDiagram mapDiagram) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			mapChildRule(mapDiagram);
			mapChildrenRule(mapDiagram);
		}
		
	}
	
	private void mapChildRule(IMapDiagram mapDiagram) throws TreeParseException{
		if(this.lexer.isRightLookaheadMatch(TreeTokenType.COMPARTMENT)){
			compartmentRule(mapDiagram);
		}
		else if(this.lexer.isRightLookaheadMatch(STATEFUL_EPNS)){
			statefulEpnRule(mapDiagram);
		}
		else if(this.lexer.isRightLookaheadMatch(EPN_NODES)){
			epnsRule(mapDiagram);
		}
		else {
			processLikeTopNode(mapDiagram);
		}
	}
	
	// we have matched a compartment so this is the
	// current token
	private void compartmentRule(IMapDiagram mapDiagram) throws TreeParseException {
		this.lexer.match(TreeTokenType.COMPARTMENT);
		IShapeNode compartmentShape = this.lexer.getCurrent().getTypedElement();
		ICompartmentNode compartmentNode = mapDiagram.createCompartmentNode(compartmentShape);
		this.lexer.down();
		compartmentChildrenRule(compartmentNode);
		this.lexer.up();
	}

	private void compartmentChildrenRule(ICompartmentNode compartmentNode) throws TreeParseException{
		compartmentChildRule(compartmentNode);
		if(lexer.hasRightTokens()){
			compartmentChildrenRule(compartmentNode);
		}
	}
	
	private void compartmentChildRule(ICompartmentNode compartmentNode) throws TreeParseException{
		if(lexer.isRightLookaheadMatch(TreeTokenType.UOI)){
			lexer.match(TreeTokenType.UOI);
		}
		else if(lexer.isRightLookaheadMatch(STATEFUL_EPNS)){
			statefulEpnRule(compartmentNode);
		}
		else if(lexer.isRightLookaheadMatch(EPN_NODES)){
			epnsRule(compartmentNode);
		}
		else{
			processLikeTopNode(compartmentNode.getMapDiagram());
		}
	}
	
	private void processLikeTopNode(IMapDiagram map) throws TreeParseException{
		if(lexer.isRightLookaheadMatch(PROCESS_NODES)){
			processesRule(map);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.PERTURBING_AGENT)){
			perturbingAgentRule(map);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.SINK)){
			this.lexer.match(TreeTokenType.SINK);
			//TODO: do something to create a sink
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.SOURCE)){
			this.lexer.match(TreeTokenType.SOURCE);
			//TODO: do something to create a source
		}
		else{
			throw new TreeParseException(this.lexer.getCurrent(), "cannot match token in rule: nonCompartmentTopNode");
		}
	}
	
	
	private void statefulEpnRule(IEpnContainer compartment) throws TreeParseException{
		if(this.lexer.isRightLookaheadMatch(TreeTokenType.COMPLEX)){
			complexRule(compartment);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.MACROMOLECULE)){
			macromoleculeRule(compartment);
		}
		else{
			naFeatureRule(compartment);
		}
	}
		
	private void epnsRule(IEpnContainer compartment) throws TreeParseException{
		if(this.lexer.isRightLookaheadMatch(TreeTokenType.SIMPLE_CHEMICAL)){
			simpleChemicalRule(compartment);
		}
		else {
			unspecifiedEntityRule(compartment);
		}
	}

	private void macromoleculeRule(IEpnContainer container) throws TreeParseException {
		this.lexer.match(TreeTokenType.MACROMOLECULE);
		IShapeNode mmShape = this.lexer.getCurrent().getTypedElement();
		IMacromoleculeNode mm = container.createMacromoleculeNode(mmShape);
		this.lexer.down();
		macromoleculeChildrenRule(mm);
		this.lexer.up();
	}
	
	private void macromoleculeChildrenRule(IMacromoleculeNode mm) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			if(this.lexer.isRightLookaheadMatch(TreeTokenType.MATERIAL_TYPE)){
				this.lexer.match(TreeTokenType.MATERIAL_TYPE);
				//TODO: handle material type
				macromoleculeChildrenRule(mm);
			}
			else if(this.lexer.isRightLookaheadMatch(STATEFUL_EPN_CHILD_NODES)){
				statefulEpnChildRule(mm);
				macromoleculeChildrenRule(mm);
			}
		}
	}

	private void complexRule(IEpnContainer compartment) throws TreeParseException {
		this.lexer.match(TreeTokenType.COMPLEX);
		IShapeNode complexShape = this.lexer.getCurrent().getTypedElement();
		IComplexNode complexNode = compartment.createComplexNode(complexShape);
		this.lexer.down();
		complexChildrenRule(complexNode);
		this.lexer.up();
	}

	private void complexChildrenRule(IComplexNode complex) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			if(this.lexer.isRightLookaheadMatch(STATEFUL_EPNS)){
				statefulEpnRule(complex);
				complexChildrenRule(complex);
			}
			else if(this.lexer.isRightLookaheadMatch(EPN_NODES)){
				epnsRule(complex);
				complexChildrenRule(complex);
			}
			else{
				statefulEpnChildRule(complex);
				complexChildrenRule(complex);
			}
		}
	}
	
	private void naFeatureRule(IEpnContainer container) throws TreeParseException{
		this.lexer.match(TreeTokenType.NA_FEATURE);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		INucleicAcidFeatureNode naNode = container.createNucleicAcidFeatureNode(shapeNode);
		this.lexer.down();
		naFeatureChildrenRule(naNode);
		this.lexer.up();
	}
	
	private void naFeatureChildrenRule(INucleicAcidFeatureNode naNode)
			throws TreeParseException {
		if (this.lexer.hasRightTokens()) {
			if (this.lexer.isRightLookaheadMatch(TreeTokenType.CONCEPTUAL_TYPE)) {
				this.lexer.match(TreeTokenType.CONCEPTUAL_TYPE);
				naFeatureChildrenRule(naNode);
			} else {
				statefulEpnChildRule(naNode);
				naFeatureChildrenRule(naNode);
			}
		}
	}
	
	private void simpleChemicalRule(IEpnContainer compartment) throws TreeParseException{
		this.lexer.match(TreeTokenType.SIMPLE_CHEMICAL);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		ISimpleChemicalNode chemicalNode = compartment.createSimpleChemicalNode(shapeNode);
		this.lexer.down();
		epnChildrenRule(chemicalNode);
		this.lexer.up();
	}
	
	
	private void unspecifiedEntityRule(IEpnContainer compartment) throws TreeParseException{
		this.lexer.match(TreeTokenType.UNSPECIFIED_ENTITY);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		IUnspecifiedEntityNode unspecNode = compartment.createUnspecifiedEntityNode(shapeNode);
		this.lexer.down();
		epnChildrenRule(unspecNode);
		this.lexer.up();
	}
	

	private void epnChildrenRule(IEntityPoolNode epnNode) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			uoiRule(epnNode);
			epnChildrenRule(epnNode);
		}
	}
	
	private void perturbingAgentRule(IMapDiagram mapDiagram) throws TreeParseException{
		this.lexer.match(TreeTokenType.PERTURBING_AGENT);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		IPerturbationNode node = mapDiagram.createPerturbationNode(shapeNode);
		this.lexer.down();
		perturbingAgentChildren(node);
		this.lexer.up();
	}
	
	private void perturbingAgentChildren(IPerturbationNode perturningAgentNode) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			if(this.lexer.isRightLookaheadMatch(TreeTokenType.UOI)){
				uoiRule(perturningAgentNode);
				perturbingAgentChildren(perturningAgentNode);
			}
			else {
				this.lexer.match(TreeTokenType.PHYSICAL_CHARACTISTIC);
				// TODO: create physical characteristic
				perturbingAgentChildren(perturningAgentNode);
			}
		}
	}
	
	private void statefulEpnChildRule(IStatefulEntityPoolNode statefulEpn) throws TreeParseException {
		if(lexer.isRightLookaheadMatch(TreeTokenType.STATE_DESCN)){
			lexer.match(TreeTokenType.STATE_DESCN);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
			statefulEpn.createStateDescription(shapeNode);
		}
		else {
			uoiRule(statefulEpn);
		}
	}
	
	private void uoiRule(IAnnotateable epn) throws TreeParseException{
		this.lexer.match(TreeTokenType.UOI);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		epn.createUnitOfInformation(shapeNode);
	}
	
	
	private void processesRule(IMapDiagram map) throws TreeParseException{
		if(this.lexer.isRightLookaheadMatch(TreeTokenType.PROCESS)){
			this.lexer.match(TreeTokenType.PROCESS);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement(); 
			map.createProcessNode(shapeNode, ProcessNodeType.STANDARD);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.UNSPECIFIED_PROCESS)){
			this.lexer.match(TreeTokenType.UNSPECIFIED_PROCESS);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement(); 
			map.createProcessNode(shapeNode, ProcessNodeType.UNCERTAIN_PROCESS);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.OMITTED_PROCESS)){
			this.lexer.match(TreeTokenType.OMITTED_PROCESS);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement(); 
			map.createProcessNode(shapeNode, ProcessNodeType.OMITTED_PROCESS);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.ASSOCIATION)){
			this.lexer.match(TreeTokenType.ASSOCIATION);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement(); 
			map.createProcessNode(shapeNode, ProcessNodeType.ASSOCIATION);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.DISOCCIATION)){
			this.lexer.match(TreeTokenType.DISOCCIATION);
			IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement(); 
			map.createProcessNode(shapeNode, ProcessNodeType.DISOCCIATION);
		}
		else{
			phenotypeRule(map);
		}
	}
	
	private void phenotypeRule(IMapDiagram map) throws TreeParseException{
		this.lexer.match(TreeTokenType.PHENOTYPE);
		IShapeNode shapeNode = this.lexer.getCurrent().getTypedElement();
		IPhenotypeNode node = map.createPhenotypeNode(shapeNode);
		this.lexer.down();
		phenotypeChildrenRule(node);
		this.lexer.up();
	}
	
	private void phenotypeChildrenRule(IPhenotypeNode phenotypeNode) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			if(this.lexer.isRightLookaheadMatch(TreeTokenType.UOI)){
				uoiRule(phenotypeNode);
			}
		}
	}

	private void mapEdgesRule(IMapDiagram mapDiagram) throws TreeParseException{
		this.lexer.match(TreeTokenType.EDGE_ROOT);
		this.lexer.down();
		edgeChildrenRule(mapDiagram);
		this.lexer.up();
	}

	private void edgeChildrenRule(IMapDiagram mapDiagram) throws TreeParseException{
		if(this.lexer.hasRightTokens()){
			edgeChildRule(mapDiagram);
			edgeChildrenRule(mapDiagram);
		}
	}
	
	private void edgeChildRule(IMapDiagram mapDiagram) throws TreeParseException{
		if(this.lexer.isRightLookaheadMatch(TreeTokenType.CONSUMPTION_ARC)){
			this.lexer.match(TreeTokenType.CONSUMPTION_ARC);
			ILinkEdge edge = this.lexer.getCurrent().getTypedElement();
			IEntityPoolNode epnNode = mapDiagram.findEntityPoolNode(edge.getSourceShape().getAttribute().getCreationSerial());
			IProcessNode processNode = mapDiagram.findProcessNode(edge.getTargetShape().getAttribute().getCreationSerial());
			processNode.createConsumptionArc(edge, epnNode);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.PRODUCTION_ARC)){
			this.lexer.match(TreeTokenType.PRODUCTION_ARC);
			ILinkEdge edge = this.lexer.getCurrent().getTypedElement();
			IShapeAttribute srcNode = edge.getSourceShape().getAttribute();
			IShapeAttribute tgtNode = edge.getTargetShape().getAttribute();
			SidednessType sidedNess = SidednessType.RHS; 
			IProcessNode processNode = mapDiagram.findProcessNode(srcNode.getCreationSerial());
			IEntityPoolNode epnNode = null;
			if(processNode == null){
				// process node is not src so is on LHS
				sidedNess = SidednessType.LHS;
				processNode = mapDiagram.findProcessNode(tgtNode.getCreationSerial());
				epnNode = mapDiagram.findEntityPoolNode(srcNode.getCreationSerial());
			}
			else{
				epnNode = mapDiagram.findEntityPoolNode(tgtNode.getCreationSerial());
			}
			processNode.createProductionArc(edge, epnNode, sidedNess);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.MODULATION_ARC)){
			this.lexer.match(TreeTokenType.MODULATION_ARC);
			createModulationArc(mapDiagram, ModulatingArcType.MODULATION);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.STIMULATION_ARC)){
			this.lexer.match(TreeTokenType.STIMULATION_ARC);
			createModulationArc(mapDiagram, ModulatingArcType.STIMULATION);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.NECESSARY_STIMULATION_ARC)){
			this.lexer.match(TreeTokenType.NECESSARY_STIMULATION_ARC);
			createModulationArc(mapDiagram, ModulatingArcType.NECESSARY_STIMULATION);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.INHIBITION_ARC)){
			this.lexer.match(TreeTokenType.INHIBITION_ARC);
			createModulationArc(mapDiagram, ModulatingArcType.INHIBITION);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.CATALYSIS_ARC)){
			this.lexer.match(TreeTokenType.CATALYSIS_ARC);
			createModulationArc(mapDiagram, ModulatingArcType.CATALYSIS);
		}
		else if(this.lexer.isRightLookaheadMatch(TreeTokenType.LOGIC_ARC)){
			this.lexer.match(TreeTokenType.LOGIC_ARC);
			ILinkEdge edge = this.lexer.getCurrent().getTypedElement();
			IModulatingNode modulatingNode = mapDiagram.findModulatingNode(edge.getSourceShape().getAttribute().getCreationSerial());
			ILogicOperatorNode logicalOperatorNode = mapDiagram.findLogicalOperatorNode(edge.getTargetShape().getAttribute().getCreationSerial());
			logicalOperatorNode.createLogicArc(edge, modulatingNode);
		}
		else{
			throw new TreeParseException(this.lexer.getCurrent(), "An arc token was expected here");
		}
	}
	
	private void createModulationArc(IMapDiagram mapDiagram, ModulatingArcType type){
		ILinkEdge edge = this.lexer.getCurrent().getTypedElement();
		IModulatingNode epnNode = mapDiagram.findModulatingNode(edge.getSourceShape().getAttribute().getCreationSerial());
		IProcessNode processNode = mapDiagram.findProcessNode(edge.getTargetShape().getAttribute().getCreationSerial());
		processNode.createModulationArc(edge, type, epnNode);
	}
}

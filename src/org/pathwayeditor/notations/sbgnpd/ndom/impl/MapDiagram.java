package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapTerminalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ITagNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class MapDiagram implements IMapDiagram {
	private final Map<Integer, IConceptualProcessNode> processMap; 
	private final Set<ICompartmentNode> compartments;
	private final String name;
	private final IEpnContainer epnContainerDelegate;
	private final ICompartmentNode defaultCompartment;
	private final Map<Integer, ILogicOperatorNode> logicOperatorMap;
	
	public MapDiagram(ICanvas canvas){
		this.name = canvas.getName();
		this.compartments = new HashSet<ICompartmentNode>();
		this.processMap = new HashMap<Integer, IConceptualProcessNode>();
		this.logicOperatorMap = new HashMap<Integer, ILogicOperatorNode>();
		this.defaultCompartment = createDefaultCompartmentNode();
		this.epnContainerDelegate = new EpnContainer(this, this.defaultCompartment);
	}
	
	
	public ISinkNode createSinkNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createSinkNode(shapeNode);
	}


	public ISourceNode createSourceNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createSourceNode(shapeNode);
	}


	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainerDelegate.nodeIterator();
	}


	public ICompartmentNode createCompartmentNode(IShapeNode node) {
		CompartmentNode retVal = new CompartmentNode(this, node);
		if(this.compartments.contains(retVal)){
			throw new IllegalArgumentException("A compartment with this name already exists. name=" + name);
		}
		this.compartments.add(retVal);
		return retVal;
	}

	public ILogicOperatorNode createLogicOperatorNode(IShapeNode shapeNode, LogicOperatorType type) {
		LogicOperatorNode retVal = new LogicOperatorNode(shapeNode, type);
		return retVal;
	}


	public IPhenotypeNode createPhenotypeNode(IShapeNode shapeNode) {
		PhenotypeNode retVal = new PhenotypeNode(shapeNode);
		//FIXME: fix phenotype
//		if(this.phenotypes.contains(retVal)){
//			throw new IllegalArgumentException("A phenotype with this name already exists: name=" + name);
//		}
		return retVal;
	}

	public IProcessNode createProcessNode(IShapeNode shapeNode, ProcessNodeType type) {
		ProcessNode retVal = new ProcessNode(shapeNode, type);
		this.processMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public ISubMapDiagram createSubMapDiagram(ICanvas canvas) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public ISubMapNode createSubmapNode(IShapeNode shapeNode) {
		SubMapNode retVal = new SubMapNode(shapeNode);
		//FIXME: fix submap
//		if(this.submapNodes.contains(retVal)){
//			throw new IllegalArgumentException("SubMapNode with this name already exists: name=" + name);
//		}
		return retVal;
	}

	public String getName() {
		return this.name;
	}


	public Iterator<ICompartmentNode> compartmentIterator() {
		return this.compartments.iterator();
	}


	public int numCompartments() {
		return this.compartments.size();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MapDiagram))
			return false;
		MapDiagram other = (MapDiagram) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	private ICompartmentNode createDefaultCompartmentNode() {
		ICompartmentNode retVal = new DefaultCompartmentNode(this);
		this.compartments.add(retVal);
		return retVal;
	}


	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		return this.epnContainerDelegate.createComplexNode(shapeNode);
	}


	public IEntityPoolNode getEntityPoolNode(int nodeId) {
		return this.epnContainerDelegate.getEntityPoolNode(nodeId);
	}


	public IProcessNode getProcessNode(int nodeId) {
		return null;
	}


	public boolean containsEntityPoolNode(int identifier) {
		return epnContainerDelegate.containsEntityPoolNode(identifier);
	}


	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createMacromoleculeNode(shapeNode);
	}


	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createNucleicAcidFeatureNode(shapeNode);
	}


	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createSimpleChemicalNode(shapeNode);
	}


	public IUnspecifiedEntityNode createUnspecifiedEntityNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createUnspecifiedEntityNode(shapeNode);
	}


	public IPerturbationNode createPerturbationNode(IShapeNode shapeNode) {
		return epnContainerDelegate.createPerturbationNode(shapeNode);
	}


	@SuppressWarnings("unchecked")
	public <T extends IEntityPoolNode> T findEntityPoolNode(int identifier) {
		IEntityPoolNode retVal = null;
		Iterator<IEntityPoolNode> nodeIter = this.epnContainerDelegate.nodeIterator();
		while(nodeIter.hasNext() && retVal == null){
			IEntityPoolNode node = nodeIter.next();
			if(node.getIdentifier() == identifier){
				retVal = node;
			}
			else if(node instanceof IEpnContainer){
				retVal = ((IEpnContainer)node).getEntityPoolNode(identifier);
			}
		}
		return (T)retVal;
	}


	@SuppressWarnings("unchecked")
	public <T extends IConceptualProcessNode> T findProcessNode(int identifier) {
		IConceptualProcessNode retVal = this.processMap.get(identifier);
		return (T)retVal;
	}
	
	public ILogicOperatorNode findLogicalOperatorNode(int identifier){
		return this.logicOperatorMap.get(identifier);
	}


	public ICompartmentNode getCompartment() {
		return epnContainerDelegate.getCompartment();
	}


	@SuppressWarnings("unchecked")
	public <T extends IModulatingNode> T findModulatingNode(int identifier) {
		IModulatingNode modNode = this.findEntityPoolNode(identifier);
		if(modNode == null){
			modNode = this.findLogicalOperatorNode(identifier);
		}
		return (T)modNode;
	}


	public int totalNumEpns() {
		EpnCounter counter = new EpnCounter();
		this.visit(counter);
		return counter.getCount();
	}


	public int totalNumProcesses() {
		ProcessCounter counter = new ProcessCounter();
		this.visit(counter);
		return counter.getCount();
	}


	public void visit(IPdElementVisitor visitor) {
		this.epnContainerDelegate.visit(visitor);
		for(IConceptualProcessNode processNode: this.processMap.values()){
			processNode.visit(visitor);
		}
		for(ICompartmentNode compartment : this.compartments){
			compartment.visit(visitor);
		}
		for(ILogicOperatorNode logicOp : this.logicOperatorMap.values()){
			logicOp.visit(visitor);
		}
	}

	
	private static class ProcessCounter implements IPdElementVisitor{
		private int count = 0;
		
		
		public int getCount(){
			return this.count;
		}
		
		public void visitCompartment(ICompartmentNode pdElement) {

			
		}

		public void visitComplex(IComplexNode pdElement) {

			
		}

		public void visitConsumptionArc(IConsumptionArc pdElement) {

			
		}

		public void visitLogicArc(ILogicArc pdElement) {

			
		}

		public void visitLogicalOperator(ILogicOperatorNode pdElement) {

			
		}

		public void visitMacromolecule(IMacromoleculeNode pdElement) {

			
		}

		public void visitModulationArc(IModulationArc pdElement) {

			
		}

		public void visitNucleicAcidFeature(INucleicAcidFeatureNode pdElement) {

			
		}

		public void visitPerturbingAgent(IPerturbationNode pdElement) {

			
		}

		public void visitPhenotypeNode(IPhenotypeNode pdElement) {

			
		}

		public void visitProcess(IProcessNode pdElement) {
			this.count++;
		}

		public void visitProductionArc(IProductionArc pdElement) {

			
		}

		public void visitSimpleChemical(ISimpleChemicalNode pdElement) {

			
		}

		public void visitSinkNode(ISinkNode pdElement) {

			
		}

		public void visitSource(ISourceNode pdElement) {

			
		}

		public void visitStateDescription(IStateDescription pdElement) {

			
		}

		public void visitSubmapNode(ISubMapNode pdElement) {

			
		}

		public void visitSubmapTerminalNode(ISubMapTerminalNode pdElement) {

			
		}

		public void visitTagNode(ITagNode pdElement) {

			
		}

		public void visitUnitOfInformation(IUnitOfInformation pdElement) {

			
		}

		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {

			
		}
		
	}
	
	private static class EpnCounter implements IPdElementVisitor{
		private int count = 0;
		
		
		public int getCount(){
			return this.count;
		}
		
		public void visitCompartment(ICompartmentNode pdElement) {

			
		}

		public void visitComplex(IComplexNode pdElement) {
			this.count++;
		}

		public void visitConsumptionArc(IConsumptionArc pdElement) {

			
		}

		public void visitLogicArc(ILogicArc pdElement) {

			
		}

		public void visitLogicalOperator(ILogicOperatorNode pdElement) {

			
		}

		public void visitMacromolecule(IMacromoleculeNode pdElement) {
			this.count++;
		}

		public void visitModulationArc(IModulationArc pdElement) {

			
		}

		public void visitNucleicAcidFeature(INucleicAcidFeatureNode pdElement) {
			this.count++;
		}

		public void visitPerturbingAgent(IPerturbationNode pdElement) {
			this.count++;
		}

		public void visitPhenotypeNode(IPhenotypeNode pdElement) {

			
		}

		public void visitProcess(IProcessNode pdElement) {
		}

		public void visitProductionArc(IProductionArc pdElement) {

			
		}

		public void visitSimpleChemical(ISimpleChemicalNode pdElement) {
			this.count++;
		}

		public void visitSinkNode(ISinkNode pdElement) {
			this.count++;
		}

		public void visitSource(ISourceNode pdElement) {
			this.count++;
		}

		public void visitStateDescription(IStateDescription pdElement) {

			
		}

		public void visitSubmapNode(ISubMapNode pdElement) {

			
		}

		public void visitSubmapTerminalNode(ISubMapTerminalNode pdElement) {

			
		}

		public void visitTagNode(ITagNode pdElement) {

			
		}

		public void visitUnitOfInformation(IUnitOfInformation pdElement) {

			
		}

		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {
			this.count++;
		}
		
	}
}

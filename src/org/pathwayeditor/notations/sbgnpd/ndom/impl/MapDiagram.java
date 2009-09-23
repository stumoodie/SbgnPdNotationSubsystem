package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class MapDiagram implements IMapDiagram {
	private final Map<Integer, IConceptualProcessNode> processMap; 
	private final Map<Integer, ICompartmentNode> compartments;
	private final String name;
	private final ICompartmentNode defaultCompartment;
	private final Map<Integer, ILogicOperatorNode> logicOperatorMap;
	
	public MapDiagram(String name){
		this.name = name;
		this.compartments = new HashMap<Integer, ICompartmentNode>();
		this.processMap = new HashMap<Integer, IConceptualProcessNode>();
		this.logicOperatorMap = new HashMap<Integer, ILogicOperatorNode>();
		this.defaultCompartment = createDefaultCompartmentNode();
//		this.epnContainerDelegate = new EpnContainer(this, this.defaultCompartment);
	}
	
	
	public ISinkNode createSinkNode(int identifier) {
		return this.defaultCompartment.createSinkNode(identifier);
	}


	public ISourceNode createSourceNode(int identifier) {
		return this.defaultCompartment.createSourceNode(identifier);
	}


	public Iterator<IEntityPoolNode> nodeIterator() {
		return this.defaultCompartment.nodeIterator();
	}


	public ICompartmentNode createCompartmentNode(int identifier, String name) {
		CompartmentNode retVal = new CompartmentNode(this, identifier, name);
		this.compartments.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public ILogicOperatorNode createLogicOperatorNode(int identifier, LogicOperatorType type) {
		LogicOperatorNode retVal = new LogicOperatorNode(identifier, type);
		return retVal;
	}


	public IPhenotypeNode createPhenotypeNode(int identifier, String name) {
		PhenotypeNode retVal = new PhenotypeNode(identifier, name);
		this.processMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public IProcessNode createProcessNode(int identifier, ProcessNodeType type) {
		ProcessNode retVal = new ProcessNode(identifier, type);
		this.processMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public String getName() {
		return this.name;
	}


	public Iterator<ICompartmentNode> compartmentIterator() {
		return this.compartments.values().iterator();
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
		this.compartments.put(retVal.getIdentifier(), retVal);
		return retVal;
	}


	public IComplexNode createComplexNode(int identifier) {
		return this.defaultCompartment.createComplexNode(identifier);
	}


	public IEntityPoolNode getEntityPoolNode(int nodeId) {
		return this.defaultCompartment.getEntityPoolNode(nodeId);
	}


	public IProcessNode getProcessNode(int nodeId) {
		return null;
	}


	public boolean containsEntityPoolNode(int identifier) {
		return this.defaultCompartment.containsEntityPoolNode(identifier);
	}


	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name) {
		return this.defaultCompartment.createMacromoleculeNode(identifier, name);
	}


	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name) {
		return this.defaultCompartment.createNucleicAcidFeatureNode(identifier, name);
	}


	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name) {
		return this.defaultCompartment.createSimpleChemicalNode(identifier, name);
	}


	public IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name) {
		return this.defaultCompartment.createUnspecifiedEntityNode(identifier, name);
	}


	public IPerturbationNode createPerturbationNode(int identifier, String name) {
		return this.defaultCompartment.createPerturbationNode(identifier, name);
	}


	@SuppressWarnings("unchecked")
	public <T extends IEntityPoolNode> T findEntityPoolNode(int identifier) {
		IEntityPoolNode retVal = null;
		Iterator<ICompartmentNode> nodeIter = this.compartments.values().iterator();
		while(nodeIter.hasNext() && retVal == null){
			ICompartmentNode cmpt = nodeIter.next();
			retVal = cmpt.getEntityPoolNode(identifier);
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
		return this.defaultCompartment.getCompartment();
	}


	@SuppressWarnings("unchecked")
	public <T extends IModulatingNode> T findModulatingNode(int identifier) {
		IEntityPoolNode epn = this.findEntityPoolNode(identifier);
		IModulatingNode modNode = null;
		if(epn instanceof IModulatingNode){
			modNode = (IModulatingNode)epn;
		}
		if(modNode == null){
			modNode = this.findLogicalOperatorNode(identifier);
		}
		return (T)modNode;
	}


	@SuppressWarnings("unchecked")
	public <T extends IPdElement> T findElement(int identifier) {
		IPdElement retVal = this.findEntityPoolNode(identifier);
		if(retVal == null){
			retVal = this.processMap.get(identifier);
			if(retVal == null){
				retVal = this.compartments.get(identifier);
				if(retVal == null){
					retVal = this.logicOperatorMap.get(identifier);
				}
			}
		}
		return (T)retVal;
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
		for(IConceptualProcessNode processNode: this.processMap.values()){
			processNode.visit(visitor);
		}
		for(ICompartmentNode compartment : this.compartments.values()){
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

		public void visitUnitOfInformation(IUnitOfInformation pdElement) {

			
		}

		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {
			this.count++;
		}
		
	}
}

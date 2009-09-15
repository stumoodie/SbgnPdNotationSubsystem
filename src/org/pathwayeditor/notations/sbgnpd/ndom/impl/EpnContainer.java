package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class EpnContainer implements IEpnContainer {
	private final IEpnContainer container;
	private final ICompartmentNode compartment;
	private final Map<Integer, IEntityPoolNode> epnMap;
	
	public EpnContainer(IEpnContainer container, ICompartmentNode compartment){
		this.container = container;
		this.compartment = compartment;
		this.epnMap = new HashMap<Integer, IEntityPoolNode>();
	}
	
	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		IComplexNode retVal = new ComplexNode(this.container, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		IMacromoleculeNode retVal = new MacromoleculeNode(this.container, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(IShapeNode shapeNode) {
		INucleicAcidFeatureNode retVal = new NucleicAcidFeatureNode(this.container, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		ISimpleChemicalNode retVal = new SimpleChemicalNode(this.container, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(IShapeNode shapeNode) {
		IUnspecifiedEntityNode retVal = new UnspecifiedEntityNode(this.container, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public boolean containsEntityPoolNode(int identifier) {
		return this.epnMap.containsKey(identifier);
	}

	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return this.epnMap.get(identifier);
	}

	public IPerturbationNode createPerturbationNode(IShapeNode shapeNode) {
		IPerturbationNode retVal = new PerturbationNode(this, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public ISinkNode createSinkNode(IShapeNode shapeNode) {
		ISinkNode retVal = new SinkNode(this, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public ISourceNode createSourceNode(IShapeNode shapeNode) {
		ISourceNode retVal = new SourceNode(this, shapeNode);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	public Iterator<IEntityPoolNode> nodeIterator() {
		return this.epnMap.values().iterator();
	}

	public ICompartmentNode getCompartment() {
		return this.compartment;
	}

	public void visit(IPdElementVisitor visitor) {
		Iterator<IEntityPoolNode> iter = this.nodeIterator();
		while(iter.hasNext()){
			IEntityPoolNode node = iter.next();
			node.visit(visitor);
		}
	}
}

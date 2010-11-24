package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
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
	
	@Override
	public IComplexNode createComplexNode(int identifier, String asciiName) {
		IComplexNode retVal = new ComplexNode(this.container, identifier, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName) {
		IMacromoleculeNode retVal = new MacromoleculeNode(this.container, name, identifier, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName) {
		INucleicAcidFeatureNode retVal = new NucleicAcidFeatureNode(this.container, identifier, name, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName) {
		ISimpleChemicalNode retVal = new SimpleChemicalNode(this.container, identifier, name, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name, String asciiName) {
		IUnspecifiedEntityNode retVal = new UnspecifiedEntityNode(this.container, identifier, name, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public boolean containsEntityPoolNode(int identifier) {
		return this.epnMap.containsKey(identifier);
	}

	@Override
	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return this.epnMap.get(identifier);
	}

	@Override
	public IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName) {
		IPerturbationNode retVal = new PerturbationNode(this, identifier, name, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public ISinkNode createSinkNode(int identifier, String asciiName) {
		ISinkNode retVal = new SinkNode(this, identifier, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public ISourceNode createSourceNode(int identifier, String asciiName) {
		ISourceNode retVal = new SourceNode(this, identifier, asciiName);
		this.epnMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public Iterator<IEntityPoolNode> nodeIterator() {
		return this.epnMap.values().iterator();
	}

	@Override
	public ICompartmentNode getCompartment() {
		return this.compartment;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		Iterator<IEntityPoolNode> iter = this.nodeIterator();
		while(iter.hasNext()){
			IEntityPoolNode node = iter.next();
			node.visit(visitor);
		}
	}
}

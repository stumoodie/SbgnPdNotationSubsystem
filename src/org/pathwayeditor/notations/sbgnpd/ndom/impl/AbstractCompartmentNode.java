package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Iterator;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public abstract class AbstractCompartmentNode extends PdElement implements ICompartmentNode {
	private static final String SBO_TERM = "SBO:0000999";
	private final String name;
	private final IMapDiagram map;
	private final EpnContainer epnContainerDelegatee;
	
	protected AbstractCompartmentNode(IShapeNode shapeNode, IMapDiagram map, String name) {
		super(shapeNode.getAttribute().getCreationSerial(), SBO_TERM);
		this.name = name;
		this.map = map;
		this.epnContainerDelegatee = new EpnContainer(this, this);
	}

	protected AbstractCompartmentNode(int identifier, IMapDiagram map, String name) {
		super(identifier, SBO_TERM);
		this.name = name;
		this.map = map;
		this.epnContainerDelegatee = new EpnContainer(this, this);
	}

	public String getName() {
		return this.name;
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean containsEntityPoolNode(int identifier) {
		return epnContainerDelegatee.containsEntityPoolNode(identifier);
	}

	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createComplexNode(shapeNode);
	}

	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createMacromoleculeNode(shapeNode);
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(
			IShapeNode shapeNode) {
		return epnContainerDelegatee.createNucleicAcidFeatureNode(shapeNode);
	}

	public IPerturbationNode createPerturbationNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createPerturbationNode(shapeNode);
	}

	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createSimpleChemicalNode(shapeNode);
	}

	public ISinkNode createSinkNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createSinkNode(shapeNode);
	}

	public ISourceNode createSourceNode(IShapeNode shapeNode) {
		return epnContainerDelegatee.createSourceNode(shapeNode);
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			IShapeNode shapeNode) {
		return epnContainerDelegatee.createUnspecifiedEntityNode(shapeNode);
	}

	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return epnContainerDelegatee.getEntityPoolNode(identifier);
	}

	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainerDelegatee.nodeIterator();
	}

	public IMapDiagram getMapDiagram() {
		return this.map;
	}

	public final void visit(IPdElementVisitor visitor) {
		visitor.visitCompartment(this);
		this.epnContainerDelegatee.visit(visitor);
	}
}

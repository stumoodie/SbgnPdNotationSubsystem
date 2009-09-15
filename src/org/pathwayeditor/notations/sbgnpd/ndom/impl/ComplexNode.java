package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Iterator;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
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

public class ComplexNode extends StatefulEntityPoolNode implements IComplexNode {
	private static final String SBO_TERM = "SBO:000999";
	private static final String NAME_PROP_NAME = "name";
	private static final String ID_PREFIX = "complex";
	private final EpnContainer epnContainer;
	
	protected ComplexNode(IEpnContainer compartmentNode, IShapeNode shapeNode) {
		super(compartmentNode, getNameProperty(shapeNode),
				IdentifierFactory.getInstance().createIdentifier(ID_PREFIX, shapeNode), SBO_TERM);
		this.epnContainer = new EpnContainer(this, compartmentNode.getCompartment());
	}

	private static String getNameProperty(IShapeNode shapeNode){
		return shapeNode.getAttribute().getProperty(NAME_PROP_NAME).toString();
	}
	
	public boolean containsEntityPoolNode(int identifier) {
		return epnContainer.containsEntityPoolNode(identifier);
	}

	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		return epnContainer.createComplexNode(shapeNode);
	}

	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		return epnContainer.createMacromoleculeNode(shapeNode);
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(
			IShapeNode shapeNode) {
		return epnContainer.createNucleicAcidFeatureNode(shapeNode);
	}

	public IPerturbationNode createPerturbationNode(IShapeNode shapeNode) {
		return epnContainer.createPerturbationNode(shapeNode);
	}

	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		return epnContainer.createSimpleChemicalNode(shapeNode);
	}

	public ISinkNode createSinkNode(IShapeNode shapeNode) {
		return epnContainer.createSinkNode(shapeNode);
	}

	public ISourceNode createSourceNode(IShapeNode shapeNode) {
		return epnContainer.createSourceNode(shapeNode);
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			IShapeNode shapeNode) {
		return epnContainer.createUnspecifiedEntityNode(shapeNode);
	}

	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return epnContainer.getEntityPoolNode(identifier);
	}

	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainer.nodeIterator();
	}

	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitComplex(this);
		this.epnContainer.visit(visitor);
	}

}

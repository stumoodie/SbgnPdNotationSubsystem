package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubunitNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class ComplexNode extends StatefulEntityPoolNode implements IComplexNode {
	private static final String SBO_TERM = "SBO:000999";
	private static final String NAME_PROP_NAME = "name";
	private static final String ID_PREFIX = "complex";
	
	private final Set<ISubunitNode> subunits;
	
	protected ComplexNode(ICompartmentNode compartmentNode, IShapeNode shapeNode) {
		super(compartmentNode, getNameProperty(shapeNode),
				IdentifierFactory.getInstance().createIdentifier(ID_PREFIX, shapeNode), SBO_TERM);
		this.subunits = new HashSet<ISubunitNode>();
	}

	private static String getNameProperty(IShapeNode shapeNode){
		return shapeNode.getAttribute().getProperty(NAME_PROP_NAME).toString();
	}
	
	public Set<ISubunitNode> getSubunits() {
		return new HashSet<ISubunitNode>(this.subunits);
	}

	public int getCardinality() {
		return super.getCardinalityValue();
	}

	public void setCardinality(int cardinality) {
		super.setCardinalityValue(cardinality);
	}

	public ISubunitNode createSubunit(IEntityPoolNode subunitEpn) {
		ISubunitNode retVal = new SubunitNode(this, subunitEpn);
		this.subunits.add(retVal);
		return retVal;
	}

	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(
			IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}
}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.PhysicalEntityType;

public class PerturbationNode extends EntityPoolNode implements IPerturbationNode {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String PROP_NAME = "name";
	private static final String ID_PREFIX = "PerturbingAgent";
	private PhysicalEntityType physicalEntityType = null;
	
	public PerturbationNode(IEpnContainer container, IShapeNode shapeNode) {
		super(IdentifierFactory.getInstance().createIdentifier(ID_PREFIX, shapeNode), container, createName(shapeNode), SBO_TERM);
	}
	
	private static String createName(IShapeNode shapeNode){
		return shapeNode.getAttribute().getProperty(PROP_NAME).toString();
	}

	public PhysicalEntityType getPhysicalEntityType() {
		return this.physicalEntityType;
	}

	public void setPhysicalEntityType(PhysicalEntityType type) {
		this.physicalEntityType = type;
	}

	public ICloneMarker createCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICloneMarker getCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitPerturbingAgent(this);
	}

}

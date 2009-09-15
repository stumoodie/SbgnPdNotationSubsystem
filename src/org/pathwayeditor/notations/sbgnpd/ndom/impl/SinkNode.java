package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;

public class SinkNode extends EntityPoolNode implements ISinkNode {
	private static final String SBO_TERM = "SBO:000999";
	private static final String NAME_PROP_NAME = "name";

	public SinkNode(EpnContainer epnContainer, IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), epnContainer, getNameProperty(shapeNode), SBO_TERM);
	}

	private static String getNameProperty(IShapeNode shapeNode){
		return shapeNode.getAttribute().getProperty(NAME_PROP_NAME).toString();
	}
	
	public ICloneMarker createCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICloneMarker getCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSinkNode(this);
	}

}

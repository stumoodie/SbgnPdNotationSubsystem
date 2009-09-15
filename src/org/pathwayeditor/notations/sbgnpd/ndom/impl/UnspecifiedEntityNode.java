package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class UnspecifiedEntityNode extends EntityPoolNode implements IUnspecifiedEntityNode {
	private static final String SBO_TERM = "SBO:000999";
	private static final String NAME_PROP_NAME = "name";

	public UnspecifiedEntityNode(IEpnContainer container, IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), container, getNameProperty(shapeNode), SBO_TERM);
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
		visitor.visitUnspecifiedEntity(this);
	}
}

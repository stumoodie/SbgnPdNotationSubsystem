package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;

public class SourceNode extends EntityPoolNode implements ISourceNode {
	private static final String SBO_TERM = "SBO:000999";
	private static final String NAME_PROP_NAME = "name";

	public SourceNode(EpnContainer epnContainer, IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), epnContainer, getNameProperty(shapeNode), SBO_TERM);
	}

	private static String getNameProperty(IShapeNode shapeNode){
		return shapeNode.getAttribute().getProperty(NAME_PROP_NAME).toString();
	}
	
	protected void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSource(this);
	}
}

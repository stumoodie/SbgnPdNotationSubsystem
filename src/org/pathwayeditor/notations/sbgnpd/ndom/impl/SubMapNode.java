package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapNode;

public class SubMapNode extends BasicEntityNode implements ISubMapNode {
	private static final String SBO_TERM = null;
	
	public SubMapNode(IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), SBO_TERM);
	}

}

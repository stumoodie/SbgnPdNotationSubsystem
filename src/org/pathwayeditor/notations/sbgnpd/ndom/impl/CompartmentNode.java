package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public class CompartmentNode extends AbstractCompartmentNode implements ICompartmentNode {
	private static final String SHAPE_NAME = "name";
	
	public CompartmentNode(IMapDiagram map, IShapeNode shapeNode) {
		super(shapeNode, map, shapeNode.getAttribute().getProperty(SHAPE_NAME).toString());
	}

	public ICompartmentNode getCompartment() {
		return this;
	}

}

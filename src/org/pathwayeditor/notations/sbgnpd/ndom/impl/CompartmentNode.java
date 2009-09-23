package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public class CompartmentNode extends AbstractCompartmentNode implements ICompartmentNode {
	
	public CompartmentNode(IMapDiagram map, int identifier, String name) {
		super(identifier, map,name);
	}

	public ICompartmentNode getCompartment() {
		return this;
	}
}

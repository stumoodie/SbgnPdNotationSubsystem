package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public class DefaultCompartmentNode extends AbstractCompartmentNode {
	public static final String DEFAULT_NAME = "Default";
	
	public DefaultCompartmentNode(IMapDiagram map) {
		this(map, DEFAULT_NAME);
	}

	public DefaultCompartmentNode(IMapDiagram map, String name) {
		super(map, name);
	}

	public ICompartmentNode getCompartment() {
		return this;
	}

}

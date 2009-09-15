package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public class DefaultCompartmentNode extends AbstractCompartmentNode {
	private static final int INITIAL_ID_VAL = -1; 
	private static int idCounter = INITIAL_ID_VAL;
	private static final String DEFAULT_NAME = "Default";
	
	public DefaultCompartmentNode(IMapDiagram map) {
		this(map, DEFAULT_NAME);
	}

	public DefaultCompartmentNode(IMapDiagram map, String name) {
		super(createNewIdentidier(), map, name);
	}

	private static int createNewIdentidier(){
		return ++idCounter;
	}
	
	public ICompartmentNode getCompartment() {
		return this;
	}
}

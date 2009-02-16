package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

public interface ICompartmentNode {
	
	String getName();
	
	Iterator<IBasicEntityNode> nodeIterator();
}

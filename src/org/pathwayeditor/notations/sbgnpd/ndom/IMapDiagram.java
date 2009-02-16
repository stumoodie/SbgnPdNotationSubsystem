package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

public interface IMapDiagram {
	String getName();
	
	Iterator<IBasicEntityNode> getBasicEntityNodes();
}

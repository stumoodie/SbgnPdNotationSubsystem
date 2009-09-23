package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public interface INdomBuilder {

	IMapDiagram getNdom();
	
	void createMap(String name);

	void buildComplete();
}

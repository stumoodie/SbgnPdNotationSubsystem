package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public interface INdomBuilder {

	IMapDiagram getNdom();
	
	void createMap(ICanvas canvas);

	void buildComplete();
}

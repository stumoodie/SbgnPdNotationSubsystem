package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.impl.MapDiagram;

public class NdomBuilder implements INdomBuilder {
	private IMapDiagram ndom;
	
	public IMapDiagram getNdom(){
		return ndom;
	}
	
	public void createMap(ICanvas canvas) {
		this.ndom = new MapDiagram(canvas);
	}

	public void buildComplete() {
	}
}

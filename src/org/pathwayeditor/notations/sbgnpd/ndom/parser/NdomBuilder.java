package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.impl.MapDiagram;

public class NdomBuilder implements INdomBuilder {
	private IMapDiagram ndom;
	
	public IMapDiagram getNdom(){
		return ndom;
	}
	
	public void createMap(String name) {
		this.ndom = new MapDiagram(name);
	}

	public void buildComplete() {
	}
}

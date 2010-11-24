package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.impl.MapDiagram;

public class NdomBuilder implements INdomBuilder {
	private IMapDiagram ndom;
	
	@Override
	public IMapDiagram getNdom(){
		return ndom;
	}
	
	@Override
	public void createMap(String name) {
		this.ndom = new MapDiagram(name);
	}

	@Override
	public void buildComplete() {
	}
}

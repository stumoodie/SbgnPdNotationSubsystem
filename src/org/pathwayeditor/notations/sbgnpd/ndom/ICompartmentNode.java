package org.pathwayeditor.notations.sbgnpd.ndom;

import java.math.BigDecimal;


public interface ICompartmentNode extends IPdElement, IEpnContainer, IAnnotateable {
	
	IMapDiagram getMapDiagram();
	
	String getName();

	BigDecimal getVolume();
	
	void setVolume(BigDecimal volume);
}

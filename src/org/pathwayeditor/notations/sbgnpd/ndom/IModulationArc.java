package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IModulationArc extends IPdElement {

	IProcessNode getModulatedNode();
	
	IModulatingNode getModulator();
	
	ModulatingArcType getType();
}

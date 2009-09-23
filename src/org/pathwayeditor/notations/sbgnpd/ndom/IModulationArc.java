package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IModulationArc extends IPdElement {

	IModulateableNode getModulatedNode();
	
	IModulatingNode getModulator();
	
	ModulatingArcType getType();
}

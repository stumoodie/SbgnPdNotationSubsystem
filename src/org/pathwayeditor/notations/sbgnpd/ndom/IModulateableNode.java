package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

public interface IModulateableNode extends IPdElement {

	IModulationArc createModulationArc(int identifier, String asciiName, ModulatingArcType type, IModulatingNode modulator);

	Set<IModulationArc> getModulationArcs();
	
}

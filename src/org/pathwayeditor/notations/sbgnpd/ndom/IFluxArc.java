package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IFluxArc extends IPdElement {

	void setStoichiometry(int stoich);
	
	int getStoichiometry();

	IProcessNode getProcess();
}

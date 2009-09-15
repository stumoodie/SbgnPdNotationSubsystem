package org.pathwayeditor.notations.sbgnpd.ndom;


public interface IPdElement {

	int getIdentifier();
	
	String getSboId();
	
	void visit(IPdElementVisitor visitor);
}

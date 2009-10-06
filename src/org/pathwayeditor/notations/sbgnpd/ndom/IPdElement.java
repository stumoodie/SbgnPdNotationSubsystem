package org.pathwayeditor.notations.sbgnpd.ndom;


public interface IPdElement {

	int getIdentifier();
	
	String getSboId();
	
	String getAsciiName();
	
	void visit(IPdElementVisitor visitor);
}

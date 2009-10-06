package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IUnitOfInformation extends IPdElement {

	IAnnotateable getEntityPoolNode();
	
	String getAnnotation();

	void setAnnotation(String annotation);
}

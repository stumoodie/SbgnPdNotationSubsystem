package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IPerturbationNode extends IModulatingNode, IBasicEntityNode, IAnnotateable {

	String getName();
	
	PhysicalEntityType getPhysicalEntityType();
	
	void setPhysicalEntityType(PhysicalEntityType type);
}

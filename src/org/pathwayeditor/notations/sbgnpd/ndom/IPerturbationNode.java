package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IPerturbationNode extends IQuantifiableEntity, IModulatingNode {

	String getName();
	
	PhysicalEntityType getPhysicalEntityType();
	
	void setPhysicalEntityType(PhysicalEntityType type);
}

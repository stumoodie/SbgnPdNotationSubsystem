package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IPerturbationNode extends IQuantifiableEntity, IModulatingNode {

	@Override
	String getName();
	
	PhysicalEntityType getPhysicalEntityType();
	
	void setPhysicalEntityType(PhysicalEntityType type);
}

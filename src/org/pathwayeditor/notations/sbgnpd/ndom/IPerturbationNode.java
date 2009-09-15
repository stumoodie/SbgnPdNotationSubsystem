package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IPerturbationNode extends IEntityPoolNode {

	String getName();
	
	PhysicalEntityType getPhysicalEntityType();
	
	void setPhysicalEntityType(PhysicalEntityType type);
}

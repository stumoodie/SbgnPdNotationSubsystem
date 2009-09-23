package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IQuantifiableEntity extends IEntityPoolNode, IModulatingNode,
		IProduceableNode, IConsumeableNode, IAnnotateable {

	String getName();
	
	void setName(String name);
	
	int getEntityCount();
	
	void setEntityCount(int count);
	
}

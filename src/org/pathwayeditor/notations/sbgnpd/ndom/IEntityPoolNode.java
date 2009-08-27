package org.pathwayeditor.notations.sbgnpd.ndom;


public interface IEntityPoolNode extends IBasicEntityNode, IModulatingNode, IProduceableNode, IAnnotateable {

	String getName();
	
	ICompartmentNode getCompartment();

	ICloneMarker createCloneMarker();
	
	ICloneMarker getCloneMarker();
}

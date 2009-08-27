package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface IStatefulEntityPoolNode extends IEntityPoolNode, IAnnotateable {

	Set<IStateDescription> getStateDescriptions();
	
	IStateDescription createStateDescription(IShapeNode descriptionShape);
	
	IIdentifiedCloneMarker getCloneMarker();
	
	IIdentifiedCloneMarker createCloneMarker(String identifier);
}

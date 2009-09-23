package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

public interface IStatefulEntityPoolNode extends IQuantifiableEntity, IAnnotateable, IMultimerEntityPoolNode {

	String getName();
	
	Set<IStateDescription> getStateDescriptions();
	
	IStateDescription createStateDescription(int identifier, String name, String value);
}

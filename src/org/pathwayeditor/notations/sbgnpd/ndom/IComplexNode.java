package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

public interface IComplexNode extends IStatefulEntityPoolNode, IMultimerEntityPoolNode, IEPNContainer {

	Set<ISubunitNode> getSubunits(); 
	
}

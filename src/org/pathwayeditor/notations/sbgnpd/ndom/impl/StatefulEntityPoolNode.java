package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IEPNContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IIdentifiedCloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IStatefulEntityPoolNode;

public abstract class StatefulEntityPoolNode extends EntityPoolNode implements IStatefulEntityPoolNode {
//	private static final String STATE_NAME_PROP_NAME = "name";
	private static final String STATE_VALUE_PROP_NAME = "stateValue";
	private final Set<IStateDescription> stateDescriptions;
	
	protected StatefulEntityPoolNode(IEPNContainer compartmentNode, String name, int identifier,
			String sboTerm){
		super(identifier, compartmentNode, name, sboTerm);
		this.stateDescriptions = new HashSet<IStateDescription>();
	}
	
	public IIdentifiedCloneMarker createCloneMarker(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public IStateDescription createStateDescription(IShapeNode shapeNode) {
//		String name = shapeNode.getAttribute().getProperty(STATE_NAME_PROP_NAME).getValue().toString();
		String value = shapeNode.getAttribute().getProperty(STATE_VALUE_PROP_NAME).getValue().toString();
		IStateDescription retVal = new StateDescription("", value);
		this.stateDescriptions.add(retVal);
		return retVal;
	}

	public final IIdentifiedCloneMarker getCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Set<IStateDescription> getStateDescriptions() {
		return new HashSet<IStateDescription>(this.stateDescriptions);
	}

	public ICloneMarker createCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

}

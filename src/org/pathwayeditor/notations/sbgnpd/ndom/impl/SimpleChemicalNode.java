package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IAnnotationProperty;
import org.pathwayeditor.notations.sbgnpd.ndom.ICloneMarker;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;

public class SimpleChemicalNode extends EntityPoolNode implements ISimpleChemicalNode {
	private static final String SBO_TERM = "SBO:9999";
	private static final String NAME_PROP_NAME = "name";
	
	public SimpleChemicalNode(IEpnContainer container, IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), container, getName(shapeNode), SBO_TERM);
	}

	private static String getName(IShapeNode shapeNode){
		IAnnotationProperty prop = shapeNode.getAttribute().getProperty(NAME_PROP_NAME);
		return prop.getValue().toString();
	}
	
	public ICloneMarker createCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public ICloneMarker getCloneMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	public void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSimpleChemical(this);
	}
}

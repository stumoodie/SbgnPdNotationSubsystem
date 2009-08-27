package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public class UnitOfInformationHandler implements IAnnotateable {
//	private static final String PREFIX_PROP_NAME = "prefix";
	private static final String VALUE_PROP_NAME = "unitOfInfo";
	private final Set<IUnitOfInformation> unitsOfInformation;
	private final IAnnotateable annotateable;
	
	public UnitOfInformationHandler(IAnnotateable annotateable){
		this.annotateable = annotateable;
		this.unitsOfInformation = new HashSet<IUnitOfInformation>();
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return new HashSet<IUnitOfInformation>(this.unitsOfInformation);
	}

	public IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode){
//		String prefix = shapeNode.getAttribute().getProperty(PREFIX_PROP_NAME).getValue().toString();
		String value = shapeNode.getAttribute().getProperty(VALUE_PROP_NAME).getValue().toString();
		IUnitOfInformation retVal = new UnitOfInformation(this.annotateable, "", value);
		this.unitsOfInformation.add(retVal);
		return retVal;
	}

}

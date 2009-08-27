package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface IAnnotateable {

	Set<IUnitOfInformation> getUnitsOfInformation();

	IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode);
}

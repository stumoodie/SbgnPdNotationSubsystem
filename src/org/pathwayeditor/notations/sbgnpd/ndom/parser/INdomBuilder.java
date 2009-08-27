package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface INdomBuilder {

	void createComplex(IShapeNode complexShape);

	void createCompartment(IShapeNode complexShape);

	void createDefaultCompartment();

	void addStateDescription(IShapeNode shapeNode);

	void addUnitOfInformation(IShapeNode shapeNode);

	void finishComplexCreation();

}

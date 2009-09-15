package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface ICompartmentNode extends IPdElement, IEpnContainer {
	
	IMapDiagram getMapDiagram();
	
	String getName();
	
	Iterator<IEntityPoolNode> nodeIterator();

	Set<IUnitOfInformation> getUnitsOfInformation();
	
	ISourceNode createSourceNode(IShapeNode shapeNode);
	
	ISinkNode createSinkNode(IShapeNode shapeNode);
	
}

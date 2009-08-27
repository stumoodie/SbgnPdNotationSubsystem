package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;


public interface IMapDiagram extends IEPNContainer {
	
	String getName();
	
	ICompartmentNode createCompartmentNode(IShapeNode shapeNode);
	
	Iterator<ICompartmentNode> compartmentIterator();
	
	int numCompartments();
	
	IProcessNode createProcessNode(IShapeNode shapeNode, ProcessNodeType type);
	
	IPhenotypeNode createPhenotypeNode(IShapeNode shapeNode);
	
	IPerturbationNode createPerturbationNode(IShapeNode shapeNode);
	
	ILogicOperatorNode createLogicOperatorNode(IShapeNode shapeNode, LogicOperatorType type);
	
	ISubMapNode createSubmapNode(IShapeNode shapeNode);
	
	ISubMapDiagram createSubMapDiagram(ICanvas canvas);

	ICompartmentNode createDefaultCompartmentNode();
}

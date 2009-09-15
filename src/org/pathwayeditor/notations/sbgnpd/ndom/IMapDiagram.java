package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;


public interface IMapDiagram extends IEpnContainer {
	
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

	<T extends IEntityPoolNode> T findEntityPoolNode(int identifer);
	
	<T extends IConceptualProcessNode> T findProcessNode(int identifier);

	<T extends IModulatingNode> T findModulatingNode(int creationSerial);

	ILogicOperatorNode findLogicalOperatorNode(int identifier);

	int totalNumEpns();

	int totalNumProcesses();
}

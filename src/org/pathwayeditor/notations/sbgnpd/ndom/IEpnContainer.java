package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface IEpnContainer {
	
	IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode);
	
	INucleicAcidFeatureNode createNucleicAcidFeatureNode(IShapeNode shapeNode);
	
	IComplexNode createComplexNode(IShapeNode shapeNode);
	
	ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode);
	
	IUnspecifiedEntityNode createUnspecifiedEntityNode(IShapeNode shapeNode);
	
	IPerturbationNode createPerturbationNode(IShapeNode shapeNode);
	
	ISinkNode createSinkNode(IShapeNode shapeNode);
	
	ISourceNode createSourceNode(IShapeNode shapeNode);
	
	boolean containsEntityPoolNode(int integer);
	
	IEntityPoolNode getEntityPoolNode(int identifier);
	
	Iterator<IEntityPoolNode> nodeIterator();
	
	ICompartmentNode getCompartment();
	
	void visit(IPdElementVisitor visitor);
}

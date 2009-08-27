package org.pathwayeditor.notations.sbgnpd.ndom;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public interface IEPNContainer extends IBasicEntityNode {
	IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode);
	
	INucleicAcidFeatureNode createNucleicAcidFeatureNode(IShapeNode shapeNode);
	
	IComplexNode createComplexNode(IShapeNode shapeNode);
	
	ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode);
	
	IUnspecifiedEntityNode createUnspecifiedEntityNode(IShapeNode shapeNode);
	
	ICompartmentNode getCompartment();
}

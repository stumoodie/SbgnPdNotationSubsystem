package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

public interface IEpnContainer {
	
	IMacromoleculeNode createMacromoleculeNode(int identifier, String name);
	
	INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name);
	
	IComplexNode createComplexNode(int identifier);
	
	ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name);
	
	IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name);
	
	IPerturbationNode createPerturbationNode(int identifier, String name);
	
	ISinkNode createSinkNode(int identifier);
	
	ISourceNode createSourceNode(int identifier);
	
	boolean containsEntityPoolNode(int integer);
	
	IEntityPoolNode getEntityPoolNode(int identifier);
	
	Iterator<IEntityPoolNode> nodeIterator();
	
	ICompartmentNode getCompartment();
	
	void visit(IPdElementVisitor visitor);
}

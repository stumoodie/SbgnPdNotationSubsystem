package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

public interface IEpnContainer {
	
	IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName);
	
	INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName);
	
	IComplexNode createComplexNode(int identifier, String asciiName);
	
	ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName);
	
	IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name, String asciiName);
	
	IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName);
	
	ISinkNode createSinkNode(int identifier, String asciiName);
	
	ISourceNode createSourceNode(int identifier, String asciiName);
	
	boolean containsEntityPoolNode(int integer);
	
	IEntityPoolNode getEntityPoolNode(int identifier);
	
	Iterator<IEntityPoolNode> nodeIterator();
	
	ICompartmentNode getCompartment();
	
	void visit(IPdElementVisitor visitor);
}

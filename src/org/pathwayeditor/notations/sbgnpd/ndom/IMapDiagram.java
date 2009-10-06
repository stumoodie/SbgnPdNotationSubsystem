package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;


public interface IMapDiagram extends IEpnContainer {
	
	String getName();
	
	ICompartmentNode createCompartmentNode(int identifier, String name, String asciiName);
	
	Iterator<ICompartmentNode> compartmentIterator();
	
	int numCompartments();
	
	IProcessNode createProcessNode(int integer, String asciiName, ProcessNodeType type);
	
	IPhenotypeNode createPhenotypeNode(int identifier, String name, String asciiName);
	
	IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName);
	
	ILogicOperatorNode createLogicOperatorNode(int integer, String asciiName, LogicOperatorType type);
	
	<T extends IPdElement> T findElement(int identifer);
	
	<T extends IEntityPoolNode> T findEntityPoolNode(int identifer);
	
	<T extends IConceptualProcessNode> T findProcessNode(int identifier);

	<T extends IModulatingNode> T findModulatingNode(int creationSerial);

	ILogicOperatorNode findLogicalOperatorNode(int identifier);

	int totalNumEpns();

	int totalNumProcesses();
}

package org.pathwayeditor.notations.sbgnpd.ndom;


public interface ILogicOperatorNode extends IModulatingNode, IPdElement {

	LogicOperatorType getOperatorType();
	
	ILogicArc createLogicArc(int identifier, String asciiName, IModulatingNode modulatingNode);
	
}

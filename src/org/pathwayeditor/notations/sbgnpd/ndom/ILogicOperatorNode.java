package org.pathwayeditor.notations.sbgnpd.ndom;

public interface ILogicOperatorNode extends IModulatingNode {

	LogicOperatorType getOperatorType();
	
	ILogicArc createLogicArc(IModulatingNode modulatingNode);
	
}

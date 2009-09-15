package org.pathwayeditor.notations.sbgnpd.ndom;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;

public interface ILogicOperatorNode extends IModulatingNode, IPdElement {

	LogicOperatorType getOperatorType();
	
	ILogicArc createLogicArc(ILinkEdge edge, IModulatingNode modulatingNode);
	
}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class LogicArc extends PdElement implements ILogicArc {
	private static final String SBO_TERM = "SBO:999";
	private final IModulatingNode modulatingNode;
	private final ILogicOperatorNode logicalOperator;

	public LogicArc(ILinkEdge edge, IModulatingNode modulatingNode,	ILogicOperatorNode logicOperatorNode) {
		super(edge.getAttribute().getCreationSerial(), SBO_TERM);
		this.modulatingNode = modulatingNode;
		this.logicalOperator = logicOperatorNode;
	}

	public ILogicOperatorNode getLogicOperator() {
		return this.logicalOperator;
	}

	public IModulatingNode getModulatingNode() {
		return this.modulatingNode;
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitLogicArc(this);
	}

}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class LogicArc extends PdElement implements ILogicArc {
	private static final String SBO_TERM = "SBO:999";
	private final IModulatingNode modulatingNode;
	private final ILogicOperatorNode logicalOperator;

	public LogicArc(int identifier, String asciiName, IModulatingNode modulatingNode,	ILogicOperatorNode logicOperatorNode) {
		super(identifier, SBO_TERM, asciiName);
		this.modulatingNode = modulatingNode;
		this.logicalOperator = logicOperatorNode;
	}

	@Override
	public ILogicOperatorNode getLogicOperator() {
		return this.logicalOperator;
	}

	@Override
	public IModulatingNode getModulatingNode() {
		return this.modulatingNode;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitLogicArc(this);
	}

}

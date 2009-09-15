package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;

public class LogicOperatorNode extends PdElement implements ILogicOperatorNode {
	private final LogicOperatorType type;
	private final Set<ILogicArc> logicArcs;
	
	public LogicOperatorNode(IShapeNode shapeNode, LogicOperatorType type) {
		super(shapeNode.getAttribute().getCreationSerial(), getSboTerm(type));
		this.type = type;
		this.logicArcs = new HashSet<ILogicArc>();
	}

	private static String getSboTerm(LogicOperatorType type){
		return "SBO:999";
	}
	
	public LogicOperatorType getOperatorType() {
		return this.type;
	}

	public ILogicArc createLogicArc(ILinkEdge edge,	IModulatingNode modulatingNode) {
		ILogicArc retVal = new LogicArc(edge, modulatingNode, this);
		this.logicArcs.add(retVal);
		return retVal;
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitLogicalOperator(this);
		for(ILogicArc logicArc : this.logicArcs){
			logicArc.visit(visitor);
		}
	}

}

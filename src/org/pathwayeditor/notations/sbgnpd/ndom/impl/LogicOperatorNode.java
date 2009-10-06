package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;

public class LogicOperatorNode extends PdElement implements ILogicOperatorNode {
	private final LogicOperatorType type;
	private final Set<ILogicArc> logicArcs;
	
	public LogicOperatorNode(int identifier, String asciiName, LogicOperatorType type) {
		super(identifier, getSboTerm(type), asciiName);
		this.type = type;
		this.logicArcs = new HashSet<ILogicArc>();
	}

	private static String getSboTerm(LogicOperatorType type){
		//TODO: Select SBO term based on type of LO
		return "SBO:999";
	}
	
	public LogicOperatorType getOperatorType() {
		return this.type;
	}

	public ILogicArc createLogicArc(int identifier, String exportName,	IModulatingNode modulatingNode) {
		ILogicArc retVal = new LogicArc(identifier, exportName, modulatingNode, this);
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

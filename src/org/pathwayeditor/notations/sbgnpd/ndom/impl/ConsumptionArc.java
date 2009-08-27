package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;

public class ConsumptionArc implements IConsumptionArc {

	public ConsumptionArc(ILinkEdge linkEdge, IConsumeableNode consumeable,	ProcessNode processNode) {
		// TODO Auto-generated constructor stub
	}

	public IConsumeableNode getConsumableNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public IProcessNode getProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStoichiometry() {
		// TODO Auto-generated method stub
		return 0;
	}

	public IShapeNode getShapeNode() {
		// TODO Auto-generated method stub
		return null;
	}

}

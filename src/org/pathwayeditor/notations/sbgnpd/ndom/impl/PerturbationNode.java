package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.PhysicalEntityType;

public class PerturbationNode extends QuantifiableEntityPoolNode implements IPerturbationNode {
	private static final String SBO_TERM = "SBO:0000999";
	private PhysicalEntityType physicalEntityType = null;
	
	public PerturbationNode(IEpnContainer container, int identifier, String name) {
		super(identifier, container, name, SBO_TERM);
	}
	
	public PhysicalEntityType getPhysicalEntityType() {
		return this.physicalEntityType;
	}

	public void setPhysicalEntityType(PhysicalEntityType type) {
		this.physicalEntityType = type;
	}

	public void visitQuantifiedEpnChild(IPdElementVisitor visitor) {
		visitor.visitPerturbingAgent(this);
	}
}

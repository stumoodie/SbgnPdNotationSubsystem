package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;

public class ConsumptionArc extends PdElement implements IConsumptionArc {
	private final static String SBO_TERM = "SBO:00999";
	private final IConsumeableNode consumable;
	private final IProcessNode processNode;
	private int stoichiometry;
	
	
	public ConsumptionArc(int identifier, String asciiName, IConsumeableNode consumeable,	IProcessNode processNode) {
		super(identifier, SBO_TERM, asciiName);
		if(consumeable == null || processNode == null) throw new IllegalArgumentException("Process node or consumeable cannot be null.");

		this.consumable = consumeable;
		this.processNode = processNode;
	}

	public IConsumeableNode getConsumableNode() {
		return this.consumable;
	}

	public IProcessNode getProcess() {
		return this.processNode;
	}

	public int getStoichiometry() {
		return stoichiometry;
	}

	public void setStoichiometry(int stoichiometry) {
		this.stoichiometry = stoichiometry;
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitConsumptionArc(this);
	}

}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;

public class SimpleChemicalNode extends QuantifiableEntityPoolNode implements ISimpleChemicalNode {
	private static final String SBO_TERM = "SBO:9999";
	
	public SimpleChemicalNode(IEpnContainer container, int identifier, String name, String asciiName) {
		super(identifier, container, name, SBO_TERM, asciiName);
	}

	@Override
	public void visitQuantifiedEpnChild(IPdElementVisitor visitor) {
		visitor.visitSimpleChemical(this);
	}
}

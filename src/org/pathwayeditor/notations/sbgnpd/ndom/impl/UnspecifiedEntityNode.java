package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class UnspecifiedEntityNode extends QuantifiableEntityPoolNode implements IUnspecifiedEntityNode {
	private static final String SBO_TERM = "SBO:000999";

	public UnspecifiedEntityNode(IEpnContainer container, int identifier, String name, String asciiName) {
		super(identifier, container, name, SBO_TERM, asciiName);
	}

	protected void visitQuantifiedEpnChild(IPdElementVisitor visitor) {
		visitor.visitUnspecifiedEntity(this);
	}
}

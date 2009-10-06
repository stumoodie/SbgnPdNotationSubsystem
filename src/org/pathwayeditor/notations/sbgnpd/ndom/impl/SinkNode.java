package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;

public class SinkNode extends EntityPoolNode implements ISinkNode {
	private static final String SBO_TERM = "SBO:000999";

	public SinkNode(EpnContainer epnContainer, int identifier, String asciiName) {
		super(identifier, asciiName, epnContainer, SBO_TERM);
	}

	protected void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSinkNode(this);
	}

}

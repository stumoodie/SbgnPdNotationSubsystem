package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;

public class SourceNode extends EntityPoolNode implements ISourceNode {
	private static final String SBO_TERM = "SBO:000999";

	public SourceNode(EpnContainer epnContainer, int identifier) {
		super(identifier, epnContainer, SBO_TERM);
	}

	protected void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSource(this);
	}
}

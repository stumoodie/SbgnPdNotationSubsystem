package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;

public class SourceNode extends EntityPoolNode implements ISourceNode {
	private static final String SBO_TERM = "SBO:000999";

	public SourceNode(EpnContainer epnContainer, int identifier, String asciiName) {
		super(identifier, asciiName, epnContainer, SBO_TERM);
	}

	@Override
	protected void visitEpnChild(IPdElementVisitor visitor) {
		visitor.visitSource(this);
	}
}

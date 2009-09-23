package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualType;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class NucleicAcidFeatureNode extends StatefulEntityPoolNode implements INucleicAcidFeatureNode {
	private static final String SBO_TERM = "SBO:0000999";
	private IConceptualType conceptualType;
	
	public NucleicAcidFeatureNode(IEpnContainer compartment, int identifier, String name) {
		super(compartment, name, identifier, SBO_TERM);
	}

	public IConceptualType getConceptualType() {
		return this.conceptualType;
	}

	public void setConceptualType(IConceptualType type) {
		this.conceptualType = type;
	}

	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitNucleicAcidFeature(this);
	}


}

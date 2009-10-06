package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.MaterialType;

public class MacromoleculeNode extends StatefulEntityPoolNode implements IMacromoleculeNode {
	private static final String SBO_TERM = "SBO:0000999";
	
	private MaterialType materialType = null;
	
	public MacromoleculeNode(IEpnContainer compartment, String name, int identifier, String asciiName) {
		super(compartment, name, identifier, SBO_TERM, asciiName);
	}

	public MaterialType getMaterialType() {
		return this.materialType;
	}

	public void setMaterialType(MaterialType type) {
		this.materialType = type;
	}

	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitMacromolecule(this);
	}
}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.MaterialType;

public class MacromoleculeNode extends StatefulEntityPoolNode implements IMacromoleculeNode {
	private static final String NAME_STRING = "name";
	private static final String SBO_TERM = "SBO:0000999";
	private static final String MACROMOLECULE_PREFIX = "macromol";
	
	private MaterialType materialType = null;
	
	public MacromoleculeNode(IEpnContainer compartment, IShapeNode shapeNode) {
		super(compartment, shapeNode.getAttribute().getProperty(NAME_STRING).toString(),
				IdentifierFactory.getInstance().createIdentifier(MACROMOLECULE_PREFIX, shapeNode), SBO_TERM);
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

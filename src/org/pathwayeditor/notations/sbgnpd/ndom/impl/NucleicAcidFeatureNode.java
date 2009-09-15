package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualType;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class NucleicAcidFeatureNode extends StatefulEntityPoolNode implements INucleicAcidFeatureNode {

	private static final String NAME_STRING = "name";
	private static final String SBO_TERM = "SBO:0000999";
	private static final String MACROMOLECULE_PREFIX = "nafeature";
	private IConceptualType conceptualType;
	
	public NucleicAcidFeatureNode(IEpnContainer compartment, IShapeNode shapeNode) {
		super(compartment, shapeNode.getAttribute().getProperty(NAME_STRING).toString(),
				IdentifierFactory.getInstance().createIdentifier(MACROMOLECULE_PREFIX, shapeNode), SBO_TERM);
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

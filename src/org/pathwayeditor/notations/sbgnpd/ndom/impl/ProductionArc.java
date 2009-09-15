package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;

public class ProductionArc extends PdElement implements IProductionArc {
	private final static String SBO_TERM = "SBO:000999";
	private final IEntityPoolNode produceable;
	private final IProcessNode process;
	private int stoichiometry;
	
	public ProductionArc(ILinkEdge linkEdge, IEntityPoolNode produceable, IProcessNode processNode) {
		super(linkEdge.getAttribute().getCreationSerial(), SBO_TERM);
		this.produceable = produceable;
		this.process = processNode;
	}

	public IProduceableNode getProductionNode() {
		return this.produceable;
	}

	public IProcessNode getProcess() {
		return this.process;
	}

	public int getStoichiometry() {
		return stoichiometry;
	}

	public void setStoichiometry(int stoichiometry) {
		this.stoichiometry = stoichiometry;
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitProductionArc(this);
	}


}

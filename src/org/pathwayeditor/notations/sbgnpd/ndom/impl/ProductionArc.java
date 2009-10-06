package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;

public class ProductionArc extends PdElement implements IProductionArc {
	private final static String SBO_TERM = "SBO:000999";
	private final IProduceableNode produceable;
	private final IProcessNode process;
	private int stoichiometry;
	
	public ProductionArc(int identifier, String asciiName, IProduceableNode produceable, IProcessNode processNode) {
		super(identifier, SBO_TERM, asciiName);
		if(produceable == null || processNode == null) throw new IllegalArgumentException("Process node or produceable cannot be null.");
		
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

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public abstract class EntityPoolNode extends PdElement implements IEntityPoolNode {
	private static final int DEFAULT_CARDINALITY = 1;
	private final IEpnContainer compartment;
	private int cardinality = DEFAULT_CARDINALITY;

	protected EntityPoolNode(int identifier, String asciiName, IEpnContainer compartment, String sboTerm){
		super(identifier, sboTerm, asciiName);
		this.compartment = compartment;
	}
	
	public final ICompartmentNode getCompartment() {
		return this.compartment.getCompartment();
	}

	public final int getCardinality(){
		return this.cardinality;
	}
	
	public final void setCardinality(int cardVal){
		this.cardinality = cardVal;
	}
	
	public final void visit(IPdElementVisitor visitor){
		visitEpnChild(visitor);
	}
	
	protected abstract void visitEpnChild(IPdElementVisitor visitor);

}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public abstract class EntityPoolNode extends PdElement implements IEntityPoolNode {
	private static final int DEFAULT_CARDINALITY = 1;
	private final IEpnContainer compartment;
	private final String name;
	private final UnitOfInformationHandler handler;
	private int cardinality = DEFAULT_CARDINALITY;

	protected EntityPoolNode(int identifier, IEpnContainer compartment, String name, String sboTerm){
		super(identifier, sboTerm);
		this.name = name;
		this.compartment = compartment;
		this.handler = new UnitOfInformationHandler(this);
	}
	
	public final ICompartmentNode getCompartment() {
		return this.compartment.getCompartment();
	}

	public final String getName() {
		return this.name;
	}

	public final Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.handler.getUnitsOfInformation();
	}

	public IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode){
		return this.handler.createUnitOfInformation(shapeNode);
	}
	
	public final int getCardinality(){
		return this.cardinality;
	}
	
	public final void setCardinality(int cardVal){
		this.cardinality = cardVal;
	}
	
	public final void visit(IPdElementVisitor visitor){
		this.handler.visit(visitor);
		visitEpnChild(visitor);
	}
	
	protected abstract void visitEpnChild(IPdElementVisitor visitor);

}

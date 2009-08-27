package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEPNContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public abstract class EntityPoolNode extends BasicEntityNode implements IEntityPoolNode {
	private static final int DEFAULT_CARDINALITY = 1;
	private final IEPNContainer compartment;
	private final String name;
	private final UnitOfInformationHandler handler;
	private int cardinality = DEFAULT_CARDINALITY;

	protected EntityPoolNode(String identifier, IEPNContainer compartment, String name, String sboTerm){
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
	
	protected int getCardinalityValue(){
		return this.cardinality;
	}
	
	protected void setCardinalityValue(int cardVal){
		this.cardinality = cardVal;
	}

}

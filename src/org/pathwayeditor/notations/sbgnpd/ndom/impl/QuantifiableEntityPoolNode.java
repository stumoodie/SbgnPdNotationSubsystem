package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IQuantifiableEntity;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public abstract class QuantifiableEntityPoolNode extends EntityPoolNode implements IQuantifiableEntity {
	private static final int DEFAULT_MOLECULE_COUNT = 0;
	private String name;
	private final UnitOfInformationHandler handler;
	private int moleculeCount = DEFAULT_MOLECULE_COUNT;

	protected QuantifiableEntityPoolNode(int identifier, IEpnContainer compartment, String name, String sboTerm) {
		super(identifier, compartment, sboTerm);
		this.name = name;
		this.handler = new UnitOfInformationHandler(this);
	}

	public final String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public final Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.handler.getUnitsOfInformation();
	}

	public IUnitOfInformation createUnitOfInformation(int identifier, String name){
		return this.handler.createUnitOfInformation(identifier, name);
	}
	
	@Override
	protected final void visitEpnChild(IPdElementVisitor visitor) {
		this.handler.visit(visitor);
		visitQuantifiedEpnChild(visitor);
	}

	
	protected abstract void visitQuantifiedEpnChild(IPdElementVisitor visitor);

	public int getEntityCount() {
		return this.moleculeCount;
	}

	public void setEntityCount(int count) {
		this.moleculeCount = count;
	}
}

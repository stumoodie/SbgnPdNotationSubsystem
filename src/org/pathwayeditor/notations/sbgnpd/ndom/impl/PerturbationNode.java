package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.PhysicalEntityType;

public class PerturbationNode extends BasicEntityNode implements IPerturbationNode {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String PROP_NAME = "name";
	private static final String ID_PREFIX = "PerturbingAgent";
	private PhysicalEntityType physicalEntityType = null;
	private UnitOfInformationHandler handler;
	private final String name;
	
	public PerturbationNode(IShapeNode shapeNode) {
		super(IdentifierFactory.getInstance().createIdentifier(ID_PREFIX, shapeNode), SBO_TERM);
		this.handler = new UnitOfInformationHandler(this);
		this.name = shapeNode.getAttribute().getProperty(PROP_NAME).toString();
	}

	public String getName() {
		return this.name;
	}

	public PhysicalEntityType getPhysicalEntityType() {
		return this.physicalEntityType;
	}

	public void setPhysicalEntityType(PhysicalEntityType type) {
		this.physicalEntityType = type;
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.handler.getUnitsOfInformation();
	}

	public IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode) {
		return this.handler.createUnitOfInformation(shapeNode);
	}

}

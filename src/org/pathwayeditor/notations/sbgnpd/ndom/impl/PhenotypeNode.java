package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public class PhenotypeNode implements IPhenotypeNode {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String NAME_PROP = "name";
	private final UnitOfInformationHandler handler;
	private final IShapeNode shapeNode;
	
	
	public PhenotypeNode(IShapeNode shapeNode) {
		handler = new UnitOfInformationHandler(this);
		this.shapeNode = shapeNode;
	}

	public String getIdentifier() {
		return this.shapeNode.getAttribute().getProperty(NAME_PROP).getValue().toString();
	}

	public String getSboId() {
		return SBO_TERM;
	}

	public IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode) {
		return this.handler.createUnitOfInformation(shapeNode);
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.handler.getUnitsOfInformation();
	}

}

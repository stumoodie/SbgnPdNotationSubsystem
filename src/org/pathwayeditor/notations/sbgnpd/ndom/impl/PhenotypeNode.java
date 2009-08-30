package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public class PhenotypeNode extends BasicEntityNode implements IPhenotypeNode, IAnnotateable {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String NAME_PROP = "name";
	private final UnitOfInformationHandler handler;
	private final IShapeNode shapeNode;
	
	public PhenotypeNode(IShapeNode shapeNode) {
		super(shapeNode.getAttribute().getCreationSerial(), SBO_TERM);
		handler = new UnitOfInformationHandler(this);
		this.shapeNode = shapeNode;
	}

	public String getName() {
		return this.shapeNode.getAttribute().getProperty(NAME_PROP).getValue().toString();
	}

	public IUnitOfInformation createUnitOfInformation(IShapeNode shapeNode) {
		return handler.createUnitOfInformation(shapeNode);
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return handler.getUnitsOfInformation();
	}

}

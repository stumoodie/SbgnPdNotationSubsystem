package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public final class UnitOfInformation extends PdElement implements IUnitOfInformation {
	private final IAnnotateable entityPoolNode;
	private final String prefix;
	private final String value;
	
	public UnitOfInformation(int identifier, IAnnotateable entityPoolNode, String prefix, String value) {
		super(identifier, null);
		if(entityPoolNode == null || value == null) throw new IllegalArgumentException("parameters cannot be null");
		
		this.entityPoolNode = entityPoolNode;
		this.prefix = prefix;
		this.value = value;
	}

	public String getAnnotation() {
		return this.value;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public IAnnotateable getEntityPoolNode(){
		return this.entityPoolNode;
	}


	public void visit(IPdElementVisitor visitor) {
		visitor.visitUnitOfInformation(this);
	}
}

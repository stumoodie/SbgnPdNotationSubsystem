package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public final class UnitOfInformation implements IUnitOfInformation {
	private final IAnnotateable entityPoolNode;
	private final String prefix;
	private final String value;
	
	public UnitOfInformation(IAnnotateable entityPoolNode, String prefix, String value) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entityPoolNode == null) ? 0 : entityPoolNode.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnitOfInformation other = (UnitOfInformation) obj;
		if (entityPoolNode == null) {
			if (other.entityPoolNode != null)
				return false;
		} else if (!entityPoolNode.equals(other.entityPoolNode))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}

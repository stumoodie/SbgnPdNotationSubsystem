package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;

public abstract class PdElement implements IPdElement {
	private final String sboTerm;
	private final int identifier;
	
	protected PdElement(int uniqueId, String sboTerm){
		this.identifier = uniqueId;
		this.sboTerm = sboTerm;
	}
	
	public final String getSboId(){
		return this.sboTerm;
	}
	
	public final int getIdentifier() {
		return this.identifier;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + identifier;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PdElement))
			return false;
		PdElement other = (PdElement) obj;
		if (identifier != other.identifier)
			return false;
		return true;
	}

}

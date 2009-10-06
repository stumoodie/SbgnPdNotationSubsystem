package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;

public abstract class PdElement implements IPdElement {
	private final String sboTerm;
	private final int identifier;
	private final String asciiName;
	
	protected PdElement(int uniqueId, String sboTerm, String asciiName){
		this.identifier = uniqueId;
		this.sboTerm = sboTerm;
		this.asciiName = asciiName;
	}
	
	public final String getSboId(){
		return this.sboTerm;
	}
	
	public final int getIdentifier() {
		return this.identifier;
	}

	public String getAsciiName() {
		return asciiName;
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

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IBasicEntityNode;

public abstract class BasicEntityNode implements IBasicEntityNode {
	private final String sboTerm;
	private final String identifier;
	
	protected BasicEntityNode(String uniqueId, String sboTerm){
		this.identifier = uniqueId;
		this.sboTerm = sboTerm;
	}
	
	@Deprecated
	protected final String getIdentifierPrefix(){
		return "";
	}
	
 	
	public final String getSboId(){
		return this.sboTerm;
	}
	
	public final String getIdentifier() {
		return this.identifier;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifier == null) ? 0 : identifier.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BasicEntityNode))
			return false;
		BasicEntityNode other = (BasicEntityNode) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
}

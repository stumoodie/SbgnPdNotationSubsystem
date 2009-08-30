package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IBasicEntityNode;

public abstract class BasicEntityNode implements IBasicEntityNode {
	private final String sboTerm;
	private final int identifier;
	
	protected BasicEntityNode(int uniqueId, String sboTerm){
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + identifier;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BasicEntityNode))
			return false;
		BasicEntityNode other = (BasicEntityNode) obj;
		if (identifier != other.identifier)
			return false;
		return true;
	}

}

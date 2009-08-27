package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubunitNode;

public class SubunitNode implements ISubunitNode {
	private final IComplexNode complexNode;
	private final IEntityPoolNode subunitEpn;
	
	public SubunitNode(IComplexNode complexNode, IEntityPoolNode subunitEpn) {
		this.complexNode = complexNode;
		this.subunitEpn = subunitEpn;
	}

	public IEntityPoolNode getEpn() {
		return this.subunitEpn;
	}

	public IComplexNode getOwningComplex() {
		return this.complexNode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((complexNode == null) ? 0 : complexNode.hashCode());
		result = prime * result
				+ ((subunitEpn == null) ? 0 : subunitEpn.hashCode());
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
		SubunitNode other = (SubunitNode) obj;
		if (complexNode == null) {
			if (other.complexNode != null)
				return false;
		} else if (!complexNode.equals(other.complexNode))
			return false;
		if (subunitEpn == null) {
			if (other.subunitEpn != null)
				return false;
		} else if (!subunitEpn.equals(other.subunitEpn))
			return false;
		return true;
	}

	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("complex=");
		buf.append(this.complexNode.getIdentifier());
		buf.append(",epn=");
		buf.append(this.subunitEpn.getIdentifier());
		buf.append(")");
		return buf.toString();
	}
}

package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingElement;

public class BoToken implements IToken {
	private final IDrawingElement drawingElement;
	private final TreeTokenType type;
	
	public BoToken(TreeTokenType type, IDrawingElement element){
		this.type = type;
		this.drawingElement = element;
	}
	
	public IDrawingElement getElement() {
		return this.drawingElement;
	}

	public TreeTokenType getType() {
		return this.type;
	}

	@SuppressWarnings("unchecked")
	public IDrawingElement getTypedElement() {
		return this.drawingElement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((drawingElement == null) ? 0 : drawingElement.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BoToken))
			return false;
		BoToken other = (BoToken) obj;
		if (drawingElement == null) {
			if (other.drawingElement != null)
				return false;
		} else if (!drawingElement.equals(other.drawingElement))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(");
		buf.append("type=");
		buf.append(this.type);
		Integer serialNum = null;
		if(this.drawingElement != null){
			serialNum = this.drawingElement.getAttribute().getCreationSerial();
		}
		buf.append(",drawingElement:serial=");
		buf.append(serialNum);
		buf.append(")");
		return buf.toString();
	}
}

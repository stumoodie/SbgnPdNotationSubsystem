package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;

public class StateDescription extends PdElement implements IStateDescription {
	private String value;

	public StateDescription(int identifier, String value, String asciiName) {
		super(identifier, null, asciiName);
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitStateDescription(this);
	}

}

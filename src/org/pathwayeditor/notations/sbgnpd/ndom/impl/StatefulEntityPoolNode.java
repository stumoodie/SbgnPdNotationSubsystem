package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IStatefulEntityPoolNode;

public abstract class StatefulEntityPoolNode extends QuantifiableEntityPoolNode implements IStatefulEntityPoolNode {
	private static final String NO_NAME = "";
	private final Set<IStateDescription> stateDescriptions;
	
	protected StatefulEntityPoolNode(IEpnContainer compartmentNode, String name, int identifier, String sboTerm, String asciiName){
		super(identifier, compartmentNode, name, sboTerm, asciiName);
		this.stateDescriptions = new HashSet<IStateDescription>();
	}
	
	protected StatefulEntityPoolNode(IEpnContainer compartmentNode, int identifier,	String sboTerm, String asciiName){
		this(compartmentNode, NO_NAME, identifier, sboTerm, asciiName);
	}
	
	public IStateDescription createStateDescription(int identifier, String name, String value) {
		IStateDescription retVal = new StateDescription(identifier, name, value);
		this.stateDescriptions.add(retVal);
		return retVal;
	}

	public final Set<IStateDescription> getStateDescriptions() {
		return new HashSet<IStateDescription>(this.stateDescriptions);
	}

	protected final void visitQuantifiedEpnChild(IPdElementVisitor visitor){
		for(IStateDescription state : this.stateDescriptions){
			state.visit(visitor);
		}
		visitStatefuleEpnChild(visitor);
	}
	
	protected abstract void visitStatefuleEpnChild(IPdElementVisitor visitor);

}

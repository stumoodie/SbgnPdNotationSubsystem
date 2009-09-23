package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.notations.sbgnpd.ndom.IModulateableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;

public class ModulationArc extends PdElement implements IModulationArc {
	private final IModulatingNode modulatingNode;
	private final IModulateableNode processNode;
	private final ModulatingArcType type;
	
	public ModulationArc(int identifier, IModulatingNode modulator,	IModulateableNode processNode, ModulatingArcType type) {
		super(identifier, getSboTerm(type));
		if(modulator == null || processNode == null) throw new IllegalArgumentException("Process node or modulator cannot be null.");

		this.processNode = processNode;
		this.modulatingNode = modulator;
		this.type = type;
	}

	private static String getSboTerm(ModulatingArcType type){
		return "SBO:9999";
	}
	
	public IModulateableNode getModulatedNode() {
		return this.processNode;
	}

	public IModulatingNode getModulator() {
		return this.modulatingNode;
	}

	public ModulatingArcType getType(){
		return this.type;
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitModulationArc(this);
	}
	
}

package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;

public class ModulationArc extends PdElement implements IModulationArc {
	private final IModulatingNode modulatingNode;
	private final IProcessNode processNode;
	private final ModulatingArcType type;
	
	public ModulationArc(ILinkEdge linkEdge, IModulatingNode modulator,	IProcessNode processNode, ModulatingArcType type) {
		super(linkEdge.getAttribute().getCreationSerial(), getSboTerm(type));
		this.processNode = processNode;
		this.modulatingNode = modulator;
		this.type = type;
	}

	private static String getSboTerm(ModulatingArcType type){
		return "SBO:9999";
	}
	
	public IProcessNode getModulatedNode() {
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

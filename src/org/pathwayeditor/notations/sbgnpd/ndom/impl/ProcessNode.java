package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IFluxArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class ProcessNode extends PdElement implements IProcessNode {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String ID_PREFIX = "Process";
	private final ProcessNodeType type;
	private final Set<IFluxArc> inputs;
	private final Set<IFluxArc> outputs;
	private final Set<IModulationArc> modulations;
	
	public ProcessNode(IShapeNode shapeNode, ProcessNodeType type) {
		this(shapeNode, type, SBO_TERM);
	}

	
	protected ProcessNode(IShapeNode shapeNode, ProcessNodeType type, String sboTerm){
		super(IdentifierFactory.getInstance().createIdentifier(ID_PREFIX, shapeNode), sboTerm);
		this.type = type;
		this.inputs = new HashSet<IFluxArc>();
		this.outputs = new HashSet<IFluxArc>();
		this.modulations = new HashSet<IModulationArc>();
	}
	
	
	public IConsumptionArc createConsumptionArc(ILinkEdge linkEdge, IEntityPoolNode consumeable) {
		IConsumptionArc retVal = new ConsumptionArc(linkEdge, consumeable, this);
		this.inputs.add(retVal);
		return retVal;
	}

	public IModulationArc createModulationArc(ILinkEdge linkEdge, ModulatingArcType type, IModulatingNode modulator) {
		IModulationArc retVal = new ModulationArc(linkEdge, modulator, this, type);
		this.modulations.add(retVal);
		return retVal;
	}

	public IProductionArc createProductionArc(ILinkEdge linkEdge, IEntityPoolNode produceable, SidednessType type) {
		IProductionArc retVal = new ProductionArc(linkEdge, produceable, this);
		if(type.equals(SidednessType.RHS)){
			this.outputs.add(retVal);
		}
		else{
			this.inputs.add(retVal);
		}
		return retVal;
	}

	public Set<IFluxArc> getLhs() {
		return new HashSet<IFluxArc>(this.inputs);
	}

	public Set<IFluxArc> getRhs() {
		return new HashSet<IFluxArc>(this.outputs);
	}

	public ProcessNodeType getProcessType() {
		return this.type;
	}


	public Set<IModulationArc> getModulationArcs() {
		return new HashSet<IModulationArc>(this.modulations);
	}


	public void visit(IPdElementVisitor visitor) {
		visitor.visitProcess(this);
		for(IFluxArc fluxArc : this.inputs){
			fluxArc.visit(visitor);
		}
		for(IFluxArc fluxArc : this.outputs){
			fluxArc.visit(visitor);
		}
		for(IModulationArc modArc : this.modulations){
			modArc.visit(visitor);
		}
	}

}

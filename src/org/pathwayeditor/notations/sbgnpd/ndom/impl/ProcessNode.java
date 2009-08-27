package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IFluxArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class ProcessNode extends BasicEntityNode implements IProcessNode {
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
	
	
	public IConsumptionArc createConsumptionArc(ILinkEdge linkEdge, IConsumeableNode consumeable) {
		IConsumptionArc retVal = new ConsumptionArc(linkEdge, consumeable, this);
		this.inputs.add(retVal);
		return retVal;
	}

	public IModulationArc createModulationArc(ILinkEdge linkEdge, ModulatingArcType type, IModulatingNode modulator) {
		// TODO Auto-generated method stub
		return null;
	}

	public IConsumptionArc createProductionArc(ILinkEdge linkEdge, IProduceableNode produceable, SidednessType type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<IFluxArc> getInputs() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<IFluxArc> getOutputs() {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessNodeType getProcessType() {
		return this.type;
	}

}

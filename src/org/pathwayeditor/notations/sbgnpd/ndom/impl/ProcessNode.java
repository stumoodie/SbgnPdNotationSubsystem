package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IFluxArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class ProcessNode extends PdElement implements IProcessNode {
	private final ProcessNodeType type;
	private final Set<IFluxArc> inputs;
	private final Set<IFluxArc> outputs;
	private final Set<IModulationArc> modulations;
	private String fwdRateEquation = "";
	private String revRateEquation = "";
	
	protected ProcessNode(int identifier, String asciiName, ProcessNodeType type){
		super(identifier, getSboTerm(type), asciiName);
		this.type = type;
		this.inputs = new HashSet<IFluxArc>();
		this.outputs = new HashSet<IFluxArc>();
		this.modulations = new HashSet<IModulationArc>();
	}
	
	
	private static String getSboTerm(ProcessNodeType type){
		//TODO: Select SBO term based on type of PN
		return "SBO:999";
	}
	
	public IConsumptionArc createConsumptionArc(int identifier, String asciiName, IConsumeableNode consumeable) {
		IConsumptionArc retVal = new ConsumptionArc(identifier, asciiName, consumeable, this);
		this.inputs.add(retVal);
		return retVal;
	}

	public IModulationArc createModulationArc(int identifier, String asciiName, ModulatingArcType type, IModulatingNode modulator) {
		IModulationArc retVal = new ModulationArc(identifier, asciiName, modulator, this, type);
		this.modulations.add(retVal);
		return retVal;
	}

	public IProductionArc createProductionArc(int identifier, String asciiName, IProduceableNode produceable, SidednessType type) {
		IProductionArc retVal = new ProductionArc(identifier, asciiName, produceable, this);
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


	public String getFwdRateEquation() {
		return this.fwdRateEquation ;
	}

	
	public void setFwdRateEquation(String rateEquation){
		this.fwdRateEquation = rateEquation;
	}


	public String getRevRateEquation() {
		return this.revRateEquation  ;
	}

	
	public void setRevRateEquation(String rateEquation){
		this.revRateEquation = rateEquation;
	}
}

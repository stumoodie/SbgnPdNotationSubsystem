/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
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
	
	@Override
	public IConsumptionArc createConsumptionArc(int identifier, String asciiName, IConsumeableNode consumeable) {
		IConsumptionArc retVal = new ConsumptionArc(identifier, asciiName, consumeable, this);
		this.inputs.add(retVal);
		return retVal;
	}

	@Override
	public IModulationArc createModulationArc(int identifier, String asciiName, ModulatingArcType type, IModulatingNode modulator) {
		IModulationArc retVal = new ModulationArc(identifier, asciiName, modulator, this, type);
		this.modulations.add(retVal);
		return retVal;
	}

	@Override
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

	@Override
	public Set<IFluxArc> getLhs() {
		return new HashSet<IFluxArc>(this.inputs);
	}

	@Override
	public Set<IFluxArc> getRhs() {
		return new HashSet<IFluxArc>(this.outputs);
	}

	@Override
	public ProcessNodeType getProcessType() {
		return this.type;
	}


	@Override
	public Set<IModulationArc> getModulationArcs() {
		return new HashSet<IModulationArc>(this.modulations);
	}


	@Override
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


	@Override
	public String getFwdRateEquation() {
		return this.fwdRateEquation ;
	}

	
	@Override
	public void setFwdRateEquation(String rateEquation){
		this.fwdRateEquation = rateEquation;
	}


	@Override
	public String getRevRateEquation() {
		return this.revRateEquation  ;
	}

	
	@Override
	public void setRevRateEquation(String rateEquation){
		this.revRateEquation = rateEquation;
	}
}

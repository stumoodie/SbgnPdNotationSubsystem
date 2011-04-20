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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class MapDiagram implements IMapDiagram {
	private final Map<Integer, IConceptualProcessNode> processMap; 
	private final Map<Integer, ICompartmentNode> compartments;
	private final String name;
	private final ICompartmentNode defaultCompartment;
	private final Map<Integer, ILogicOperatorNode> logicOperatorMap;
	
	public MapDiagram(String name){
		this.name = name;
		this.compartments = new HashMap<Integer, ICompartmentNode>();
		this.processMap = new HashMap<Integer, IConceptualProcessNode>();
		this.logicOperatorMap = new HashMap<Integer, ILogicOperatorNode>();
		this.defaultCompartment = createDefaultCompartmentNode();
//		this.epnContainerDelegate = new EpnContainer(this, this.defaultCompartment);
	}
	
	
	@Override
	public ISinkNode createSinkNode(int identifier, String asciiName) {
		return this.defaultCompartment.createSinkNode(identifier, asciiName);
	}


	@Override
	public ISourceNode createSourceNode(int identifier, String asciiName) {
		return this.defaultCompartment.createSourceNode(identifier, asciiName);
	}


	@Override
	public Iterator<IEntityPoolNode> nodeIterator() {
		return this.defaultCompartment.nodeIterator();
	}


	@Override
	public ICompartmentNode createCompartmentNode(int identifier, String name, String asciiName) {
		CompartmentNode retVal = new CompartmentNode(this, identifier, name, asciiName);
		this.compartments.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public ILogicOperatorNode createLogicOperatorNode(int identifier, String asciiName, LogicOperatorType type) {
		LogicOperatorNode retVal = new LogicOperatorNode(identifier, asciiName, type);
		return retVal;
	}


	@Override
	public IPhenotypeNode createPhenotypeNode(int identifier, String name, String asciiName) {
		PhenotypeNode retVal = new PhenotypeNode(identifier, name, asciiName);
		this.processMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public IProcessNode createProcessNode(int identifier, String asciiName, ProcessNodeType type) {
		ProcessNode retVal = new ProcessNode(identifier, asciiName, type);
		this.processMap.put(retVal.getIdentifier(), retVal);
		return retVal;
	}

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public Iterator<ICompartmentNode> compartmentIterator() {
		return this.compartments.values().iterator();
	}


	@Override
	public int numCompartments() {
		return this.compartments.size();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MapDiagram))
			return false;
		MapDiagram other = (MapDiagram) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	private ICompartmentNode createDefaultCompartmentNode() {
		ICompartmentNode retVal = new DefaultCompartmentNode(this);
		this.compartments.put(retVal.getIdentifier(), retVal);
		return retVal;
	}


	@Override
	public IComplexNode createComplexNode(int identifier, String asciiName) {
		return this.defaultCompartment.createComplexNode(identifier, asciiName);
	}


	@Override
	public IEntityPoolNode getEntityPoolNode(int nodeId) {
		return this.defaultCompartment.getEntityPoolNode(nodeId);
	}


	public IProcessNode getProcessNode(int nodeId) {
		return null;
	}


	@Override
	public boolean containsEntityPoolNode(int identifier) {
		return this.defaultCompartment.containsEntityPoolNode(identifier);
	}


	@Override
	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName) {
		return this.defaultCompartment.createMacromoleculeNode(identifier, name, asciiName);
	}


	@Override
	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName) {
		return this.defaultCompartment.createNucleicAcidFeatureNode(identifier, name, asciiName);
	}


	@Override
	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName) {
		return this.defaultCompartment.createSimpleChemicalNode(identifier, name, asciiName);
	}


	@Override
	public IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name, String asciiName) {
		return this.defaultCompartment.createUnspecifiedEntityNode(identifier, name, asciiName);
	}


	@Override
	public IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName) {
		return this.defaultCompartment.createPerturbationNode(identifier, name, asciiName);
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends IEntityPoolNode> T findEntityPoolNode(int identifier) {
		IEntityPoolNode retVal = null;
		Iterator<ICompartmentNode> nodeIter = this.compartments.values().iterator();
		while(nodeIter.hasNext() && retVal == null){
			ICompartmentNode cmpt = nodeIter.next();
			retVal = cmpt.getEntityPoolNode(identifier);
		}
		return (T)retVal;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends IConceptualProcessNode> T findProcessNode(int identifier) {
		IConceptualProcessNode retVal = this.processMap.get(identifier);
		return (T)retVal;
	}
	
	@Override
	public ILogicOperatorNode findLogicalOperatorNode(int identifier){
		return this.logicOperatorMap.get(identifier);
	}


	@Override
	public ICompartmentNode getCompartment() {
		return this.defaultCompartment.getCompartment();
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends IModulatingNode> T findModulatingNode(int identifier) {
		IEntityPoolNode epn = this.findEntityPoolNode(identifier);
		IModulatingNode modNode = null;
		if(epn instanceof IModulatingNode){
			modNode = (IModulatingNode)epn;
		}
		if(modNode == null){
			modNode = this.findLogicalOperatorNode(identifier);
		}
		return (T)modNode;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends IPdElement> T findElement(int identifier) {
		IPdElement retVal = this.findEntityPoolNode(identifier);
		if(retVal == null){
			retVal = this.processMap.get(identifier);
			if(retVal == null){
				retVal = this.compartments.get(identifier);
				if(retVal == null){
					retVal = this.logicOperatorMap.get(identifier);
				}
			}
		}
		return (T)retVal;
	}

	
	@Override
	public int totalNumEpns() {
		EpnCounter counter = new EpnCounter();
		this.visit(counter);
		return counter.getCount();
	}


	@Override
	public int totalNumProcesses() {
		ProcessCounter counter = new ProcessCounter();
		this.visit(counter);
		return counter.getCount();
	}


	@Override
	public void visit(IPdElementVisitor visitor) {
		for(IConceptualProcessNode processNode: this.processMap.values()){
			processNode.visit(visitor);
		}
		for(ICompartmentNode compartment : this.compartments.values()){
			compartment.visit(visitor);
		}
		for(ILogicOperatorNode logicOp : this.logicOperatorMap.values()){
			logicOp.visit(visitor);
		}
	}

	

	private static class ProcessCounter implements IPdElementVisitor{
		private int count = 0;
		
		
		public int getCount(){
			return this.count;
		}
		
		@Override
		public void visitCompartment(ICompartmentNode pdElement) {

			
		}

		@Override
		public void visitComplex(IComplexNode pdElement) {

			
		}

		@Override
		public void visitConsumptionArc(IConsumptionArc pdElement) {

			
		}

		@Override
		public void visitLogicArc(ILogicArc pdElement) {

			
		}

		@Override
		public void visitLogicalOperator(ILogicOperatorNode pdElement) {

			
		}

		@Override
		public void visitMacromolecule(IMacromoleculeNode pdElement) {

			
		}

		@Override
		public void visitModulationArc(IModulationArc pdElement) {

			
		}

		@Override
		public void visitNucleicAcidFeature(INucleicAcidFeatureNode pdElement) {

			
		}

		@Override
		public void visitPerturbingAgent(IPerturbationNode pdElement) {

			
		}

		@Override
		public void visitPhenotypeNode(IPhenotypeNode pdElement) {

			
		}

		@Override
		public void visitProcess(IProcessNode pdElement) {
			this.count++;
		}

		@Override
		public void visitProductionArc(IProductionArc pdElement) {

			
		}

		@Override
		public void visitSimpleChemical(ISimpleChemicalNode pdElement) {

			
		}

		@Override
		public void visitSinkNode(ISinkNode pdElement) {

			
		}

		@Override
		public void visitSource(ISourceNode pdElement) {

			
		}

		@Override
		public void visitStateDescription(IStateDescription pdElement) {

			
		}

		@Override
		public void visitUnitOfInformation(IUnitOfInformation pdElement) {

			
		}

		@Override
		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {

			
		}
		
	}
	
	private static class EpnCounter implements IPdElementVisitor{
		private int count = 0;
		
		
		public int getCount(){
			return this.count;
		}
		
		@Override
		public void visitCompartment(ICompartmentNode pdElement) {

			
		}

		@Override
		public void visitComplex(IComplexNode pdElement) {
			this.count++;
		}

		@Override
		public void visitConsumptionArc(IConsumptionArc pdElement) {

			
		}

		@Override
		public void visitLogicArc(ILogicArc pdElement) {

			
		}

		@Override
		public void visitLogicalOperator(ILogicOperatorNode pdElement) {

			
		}

		@Override
		public void visitMacromolecule(IMacromoleculeNode pdElement) {
			this.count++;
		}

		@Override
		public void visitModulationArc(IModulationArc pdElement) {

			
		}

		@Override
		public void visitNucleicAcidFeature(INucleicAcidFeatureNode pdElement) {
			this.count++;
		}

		@Override
		public void visitPerturbingAgent(IPerturbationNode pdElement) {
			this.count++;
		}

		@Override
		public void visitPhenotypeNode(IPhenotypeNode pdElement) {

			
		}

		@Override
		public void visitProcess(IProcessNode pdElement) {
		}

		@Override
		public void visitProductionArc(IProductionArc pdElement) {

			
		}

		@Override
		public void visitSimpleChemical(ISimpleChemicalNode pdElement) {
			this.count++;
		}

		@Override
		public void visitSinkNode(ISinkNode pdElement) {
			this.count++;
		}

		@Override
		public void visitSource(ISourceNode pdElement) {
			this.count++;
		}

		@Override
		public void visitStateDescription(IStateDescription pdElement) {

			
		}

		@Override
		public void visitUnitOfInformation(IUnitOfInformation pdElement) {

			
		}

		@Override
		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {
			this.count++;
		}
		
	}
}

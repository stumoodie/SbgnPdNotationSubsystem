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

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public abstract class AbstractCompartmentNode extends PdElement implements ICompartmentNode {
	private static final String SBO_TERM = "SBO:0000999";
	private final String name;
	private final IMapDiagram map;
	private final EpnContainer epnContainerDelegatee;
	private final UnitOfInformationHandler uoiHandler;
	private BigDecimal volume = BigDecimal.ZERO;
	
	protected AbstractCompartmentNode(int identifier, String asciiName, IMapDiagram map, String name) {
		super(identifier, SBO_TERM, asciiName);
		this.name = name;
		this.map = map;
		this.epnContainerDelegatee = new EpnContainer(this, this);
		this.uoiHandler = new UnitOfInformationHandler(this);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.uoiHandler.getUnitsOfInformation();
	}

	@Override
	public IUnitOfInformation createUnitOfInformation(int identifier,
			String value) {
		return uoiHandler.createUnitOfInformation(identifier, value);
	}

	@Override
	public boolean containsEntityPoolNode(int identifier) {
		return epnContainerDelegatee.containsEntityPoolNode(identifier);
	}

	@Override
	public IComplexNode createComplexNode(int identifier, String asciiName) {
		return epnContainerDelegatee.createComplexNode(identifier, asciiName);
	}

	@Override
	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName) {
		return epnContainerDelegatee.createMacromoleculeNode(identifier, name, asciiName);
	}

	@Override
	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName) {
		return epnContainerDelegatee.createNucleicAcidFeatureNode(identifier, name, asciiName);
	}

	@Override
	public IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName) {
		return epnContainerDelegatee.createPerturbationNode(identifier, name, asciiName);
	}

	@Override
	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName) {
		return epnContainerDelegatee.createSimpleChemicalNode(identifier, name, asciiName);
	}

	@Override
	public ISinkNode createSinkNode(int identifier, String asciiName) {
		return epnContainerDelegatee.createSinkNode(identifier, asciiName);
	}

	@Override
	public ISourceNode createSourceNode(int identifier, String asciiName) {
		return epnContainerDelegatee.createSourceNode(identifier, asciiName);
	}

	@Override
	public IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name, String asciiName) {
		return epnContainerDelegatee.createUnspecifiedEntityNode(identifier, name, asciiName);
	}

	@Override
	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return epnContainerDelegatee.getEntityPoolNode(identifier);
	}

	@Override
	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainerDelegatee.nodeIterator();
	}

	@Override
	public IMapDiagram getMapDiagram() {
		return this.map;
	}

	@Override
	public final void visit(IPdElementVisitor visitor) {
		visitor.visitCompartment(this);
		this.epnContainerDelegatee.visit(visitor);
	}

	@Override
	public BigDecimal getVolume() {
		return volume;
	}

	@Override
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
}

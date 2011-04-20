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

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;

public class PhenotypeNode extends PdElement implements IPhenotypeNode, IAnnotateable {
	private static final String SBO_TERM = "SBO:0000999";
	private final UnitOfInformationHandler handler;
	private final Set<IModulationArc> modulations;
	private final String name;
	
	public PhenotypeNode(int identifier, String name, String asciiName) {
		super(identifier, SBO_TERM, asciiName);
		handler = new UnitOfInformationHandler(this);
		this.modulations = new HashSet<IModulationArc>();
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IUnitOfInformation createUnitOfInformation(int identifier, String name) {
		return handler.createUnitOfInformation(identifier, name);
	}

	@Override
	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return handler.getUnitsOfInformation();
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitPhenotypeNode(this);
	}

	@Override
	public String getRateFunction() {
		return "";
	}

	@Override
	public IModulationArc createModulationArc(int identifier, String asciiName, ModulatingArcType type, IModulatingNode modulator) {
		IModulationArc retVal = new ModulationArc(identifier, asciiName, modulator, this, type);
		this.modulations.add(retVal);
		return retVal;
	}


	@Override
	public Set<IModulationArc> getModulationArcs() {
		return new HashSet<IModulationArc>(this.modulations);
	}



}

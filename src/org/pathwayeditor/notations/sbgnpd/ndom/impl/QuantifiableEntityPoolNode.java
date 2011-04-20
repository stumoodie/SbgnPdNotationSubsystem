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

import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IQuantifiableEntity;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public abstract class QuantifiableEntityPoolNode extends EntityPoolNode implements IQuantifiableEntity {
	private static final int DEFAULT_MOLECULE_COUNT = 0;
	private String name;
	private final UnitOfInformationHandler handler;
	private int moleculeCount = DEFAULT_MOLECULE_COUNT;

	protected QuantifiableEntityPoolNode(int identifier, IEpnContainer compartment, String name, String sboTerm, String asciiName) {
		super(identifier, asciiName, compartment, sboTerm);
		this.name = name;
		this.handler = new UnitOfInformationHandler(this);
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public final Set<IUnitOfInformation> getUnitsOfInformation() {
		return this.handler.getUnitsOfInformation();
	}

	@Override
	public IUnitOfInformation createUnitOfInformation(int identifier, String name){
		return this.handler.createUnitOfInformation(identifier, name);
	}
	
	@Override
	protected final void visitEpnChild(IPdElementVisitor visitor) {
		this.handler.visit(visitor);
		visitQuantifiedEpnChild(visitor);
	}

	
	protected abstract void visitQuantifiedEpnChild(IPdElementVisitor visitor);

	@Override
	public int getEntityCount() {
		return this.moleculeCount;
	}

	@Override
	public void setEntityCount(int count) {
		this.moleculeCount = count;
	}
}

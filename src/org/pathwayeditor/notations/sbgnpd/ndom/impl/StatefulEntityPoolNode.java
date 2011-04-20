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

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IStatefulEntityPoolNode;

public abstract class StatefulEntityPoolNode extends QuantifiableEntityPoolNode implements IStatefulEntityPoolNode {
	private static final String NO_NAME = "";
	private final Set<IStateDescription> stateDescriptions;
	
	protected StatefulEntityPoolNode(IEpnContainer compartmentNode, String name, int identifier, String sboTerm, String asciiName){
		super(identifier, compartmentNode, name, sboTerm, asciiName);
		this.stateDescriptions = new HashSet<IStateDescription>();
	}
	
	protected StatefulEntityPoolNode(IEpnContainer compartmentNode, int identifier,	String sboTerm, String asciiName){
		this(compartmentNode, NO_NAME, identifier, sboTerm, asciiName);
	}
	
	@Override
	public IStateDescription createStateDescription(int identifier, String name, String value) {
		IStateDescription retVal = new StateDescription(identifier, name, value);
		this.stateDescriptions.add(retVal);
		return retVal;
	}

	@Override
	public final Set<IStateDescription> getStateDescriptions() {
		return new HashSet<IStateDescription>(this.stateDescriptions);
	}

	@Override
	protected final void visitQuantifiedEpnChild(IPdElementVisitor visitor){
		for(IStateDescription state : this.stateDescriptions){
			state.visit(visitor);
		}
		visitStatefuleEpnChild(visitor);
	}
	
	protected abstract void visitStatefuleEpnChild(IPdElementVisitor visitor);

}

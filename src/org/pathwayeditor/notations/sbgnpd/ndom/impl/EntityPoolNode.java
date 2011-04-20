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

import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public abstract class EntityPoolNode extends PdElement implements IEntityPoolNode {
	private static final int DEFAULT_CARDINALITY = 1;
	private final IEpnContainer compartment;
	private int cardinality = DEFAULT_CARDINALITY;

	protected EntityPoolNode(int identifier, String asciiName, IEpnContainer compartment, String sboTerm){
		super(identifier, sboTerm, asciiName);
		this.compartment = compartment;
	}
	
	@Override
	public final ICompartmentNode getCompartment() {
		return this.compartment.getCompartment();
	}

	public final int getCardinality(){
		return this.cardinality;
	}
	
	public final void setCardinality(int cardVal){
		this.cardinality = cardVal;
	}
	
	@Override
	public final void visit(IPdElementVisitor visitor){
		visitEpnChild(visitor);
	}
	
	protected abstract void visitEpnChild(IPdElementVisitor visitor);

}

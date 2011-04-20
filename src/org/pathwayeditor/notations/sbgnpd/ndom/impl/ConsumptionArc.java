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

import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;

public class ConsumptionArc extends PdElement implements IConsumptionArc {
	private final static String SBO_TERM = "SBO:00999";
	private final IConsumeableNode consumable;
	private final IProcessNode processNode;
	private int stoichiometry;
	
	
	public ConsumptionArc(int identifier, String asciiName, IConsumeableNode consumeable,	IProcessNode processNode) {
		super(identifier, SBO_TERM, asciiName);
		if(consumeable == null || processNode == null) throw new IllegalArgumentException("Process node or consumeable cannot be null.");

		this.consumable = consumeable;
		this.processNode = processNode;
	}

	@Override
	public IConsumeableNode getConsumableNode() {
		return this.consumable;
	}

	@Override
	public IProcessNode getProcess() {
		return this.processNode;
	}

	@Override
	public int getStoichiometry() {
		return stoichiometry;
	}

	@Override
	public void setStoichiometry(int stoichiometry) {
		this.stoichiometry = stoichiometry;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitConsumptionArc(this);
	}

}

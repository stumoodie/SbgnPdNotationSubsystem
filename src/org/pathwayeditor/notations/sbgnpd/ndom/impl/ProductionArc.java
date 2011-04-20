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

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;

public class ProductionArc extends PdElement implements IProductionArc {
	private final static String SBO_TERM = "SBO:000999";
	private final IProduceableNode produceable;
	private final IProcessNode process;
	private int stoichiometry;
	
	public ProductionArc(int identifier, String asciiName, IProduceableNode produceable, IProcessNode processNode) {
		super(identifier, SBO_TERM, asciiName);
		if(produceable == null || processNode == null) throw new IllegalArgumentException("Process node or produceable cannot be null.");
		
		this.produceable = produceable;
		this.process = processNode;
	}

	@Override
	public IProduceableNode getProductionNode() {
		return this.produceable;
	}

	@Override
	public IProcessNode getProcess() {
		return this.process;
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
		visitor.visitProductionArc(this);
	}


}

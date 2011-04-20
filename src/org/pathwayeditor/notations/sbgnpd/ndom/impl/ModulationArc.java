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

import org.pathwayeditor.notations.sbgnpd.ndom.IModulateableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;

public class ModulationArc extends PdElement implements IModulationArc {
	private final IModulatingNode modulatingNode;
	private final IModulateableNode processNode;
	private final ModulatingArcType type;
	
	public ModulationArc(int identifier, String asciiName, IModulatingNode modulator,	IModulateableNode processNode, ModulatingArcType type) {
		super(identifier, getSboTerm(type), asciiName);
		if(modulator == null || processNode == null) throw new IllegalArgumentException("Process node or modulator cannot be null.");

		this.processNode = processNode;
		this.modulatingNode = modulator;
		this.type = type;
	}

	private static String getSboTerm(ModulatingArcType type){
		return "SBO:9999";
	}
	
	@Override
	public IModulateableNode getModulatedNode() {
		return this.processNode;
	}

	@Override
	public IModulatingNode getModulator() {
		return this.modulatingNode;
	}

	@Override
	public ModulatingArcType getType(){
		return this.type;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitModulationArc(this);
	}
	
}

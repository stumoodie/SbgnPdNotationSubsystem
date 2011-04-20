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

import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;

public class SimpleChemicalNode extends QuantifiableEntityPoolNode implements ISimpleChemicalNode {
	private static final String SBO_TERM = "SBO:9999";
	
	public SimpleChemicalNode(IEpnContainer container, int identifier, String name, String asciiName) {
		super(identifier, container, name, SBO_TERM, asciiName);
	}

	@Override
	public void visitQuantifiedEpnChild(IPdElementVisitor visitor) {
		visitor.visitSimpleChemical(this);
	}
}

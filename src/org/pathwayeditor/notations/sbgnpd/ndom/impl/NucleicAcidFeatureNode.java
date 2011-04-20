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

import org.pathwayeditor.notations.sbgnpd.ndom.IConceptualType;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class NucleicAcidFeatureNode extends StatefulEntityPoolNode implements INucleicAcidFeatureNode {
	private static final String SBO_TERM = "SBO:0000999";
	private IConceptualType conceptualType;
	
	public NucleicAcidFeatureNode(IEpnContainer compartment, int identifier, String name, String asciiName) {
		super(compartment, name, identifier, SBO_TERM, asciiName);
	}

	@Override
	public IConceptualType getConceptualType() {
		return this.conceptualType;
	}

	@Override
	public void setConceptualType(IConceptualType type) {
		this.conceptualType = type;
	}

	@Override
	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitNucleicAcidFeature(this);
	}


}

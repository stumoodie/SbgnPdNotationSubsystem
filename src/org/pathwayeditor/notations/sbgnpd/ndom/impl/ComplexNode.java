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

import java.util.Iterator;

import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class ComplexNode extends StatefulEntityPoolNode implements IComplexNode {
	private static final String SBO_TERM = "SBO:000999";
	private final EpnContainer epnContainer;
	
	protected ComplexNode(IEpnContainer compartmentNode, int identifier, String asciiName) {
		super(compartmentNode, identifier, SBO_TERM, asciiName);
		this.epnContainer = new EpnContainer(this, compartmentNode.getCompartment());
	}

	@Override
	public boolean containsEntityPoolNode(int identifier) {
		return epnContainer.containsEntityPoolNode(identifier);
	}

	@Override
	public IComplexNode createComplexNode(int identifier, String asciiName) {
		return epnContainer.createComplexNode(identifier, asciiName);
	}

	@Override
	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName) {
		return epnContainer.createMacromoleculeNode(identifier, name, asciiName);
	}

	@Override
	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName) {
		return epnContainer.createNucleicAcidFeatureNode(identifier, name, asciiName);
	}

	@Override
	public IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName) {
		return epnContainer.createPerturbationNode(identifier, name, asciiName);
	}

	@Override
	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName) {
		return epnContainer.createSimpleChemicalNode(identifier, name, asciiName);
	}

	@Override
	public ISinkNode createSinkNode(int identifier, String asciiName) {
		return epnContainer.createSinkNode(identifier, asciiName);
	}

	@Override
	public ISourceNode createSourceNode(int identifier, String asciiName) {
		return epnContainer.createSourceNode(identifier, asciiName);
	}

	@Override
	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			int identifier, String name, String asciiName) {
		return epnContainer.createUnspecifiedEntityNode(identifier, name, asciiName);
	}

	@Override
	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return epnContainer.getEntityPoolNode(identifier);
	}

	@Override
	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainer.nodeIterator();
	}

	@Override
	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitComplex(this);
		this.epnContainer.visit(visitor);
	}

}

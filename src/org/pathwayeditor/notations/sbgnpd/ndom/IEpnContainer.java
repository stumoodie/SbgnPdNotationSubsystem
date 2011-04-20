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
package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Iterator;

public interface IEpnContainer {
	
	IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName);
	
	INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName);
	
	IComplexNode createComplexNode(int identifier, String asciiName);
	
	ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName);
	
	IUnspecifiedEntityNode createUnspecifiedEntityNode(int identifier, String name, String asciiName);
	
	IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName);
	
	ISinkNode createSinkNode(int identifier, String asciiName);
	
	ISourceNode createSourceNode(int identifier, String asciiName);
	
	boolean containsEntityPoolNode(int integer);
	
	IEntityPoolNode getEntityPoolNode(int identifier);
	
	Iterator<IEntityPoolNode> nodeIterator();
	
	ICompartmentNode getCompartment();
	
	void visit(IPdElementVisitor visitor);
}

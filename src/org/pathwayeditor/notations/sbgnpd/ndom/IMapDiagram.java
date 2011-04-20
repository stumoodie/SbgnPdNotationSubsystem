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


public interface IMapDiagram extends IEpnContainer {
	
	String getName();
	
	ICompartmentNode createCompartmentNode(int identifier, String name, String asciiName);
	
	Iterator<ICompartmentNode> compartmentIterator();
	
	int numCompartments();
	
	IProcessNode createProcessNode(int integer, String asciiName, ProcessNodeType type);
	
	IPhenotypeNode createPhenotypeNode(int identifier, String name, String asciiName);
	
	@Override
	IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName);
	
	ILogicOperatorNode createLogicOperatorNode(int integer, String asciiName, LogicOperatorType type);
	
	<T extends IPdElement> T findElement(int identifer);
	
	<T extends IEntityPoolNode> T findEntityPoolNode(int identifer);
	
	<T extends IConceptualProcessNode> T findProcessNode(int identifier);

	<T extends IModulatingNode> T findModulatingNode(int creationSerial);

	ILogicOperatorNode findLogicalOperatorNode(int identifier);

	int totalNumEpns();

	int totalNumProcesses();
}

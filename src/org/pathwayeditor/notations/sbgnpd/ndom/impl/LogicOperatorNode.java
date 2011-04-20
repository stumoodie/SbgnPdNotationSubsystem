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

import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;

public class LogicOperatorNode extends PdElement implements ILogicOperatorNode {
	private final LogicOperatorType type;
	private final Set<ILogicArc> logicArcs;
	
	public LogicOperatorNode(int identifier, String asciiName, LogicOperatorType type) {
		super(identifier, getSboTerm(type), asciiName);
		this.type = type;
		this.logicArcs = new HashSet<ILogicArc>();
	}

	private static String getSboTerm(LogicOperatorType type){
		//TODO: Select SBO term based on type of LO
		return "SBO:999";
	}
	
	@Override
	public LogicOperatorType getOperatorType() {
		return this.type;
	}

	@Override
	public ILogicArc createLogicArc(int identifier, String exportName,	IModulatingNode modulatingNode) {
		ILogicArc retVal = new LogicArc(identifier, exportName, modulatingNode, this);
		this.logicArcs.add(retVal);
		return retVal;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitLogicalOperator(this);
		for(ILogicArc logicArc : this.logicArcs){
			logicArc.visit(visitor);
		}
	}

}

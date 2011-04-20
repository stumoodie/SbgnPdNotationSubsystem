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

import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;

public class LogicArc extends PdElement implements ILogicArc {
	private static final String SBO_TERM = "SBO:999";
	private final IModulatingNode modulatingNode;
	private final ILogicOperatorNode logicalOperator;

	public LogicArc(int identifier, String asciiName, IModulatingNode modulatingNode,	ILogicOperatorNode logicOperatorNode) {
		super(identifier, SBO_TERM, asciiName);
		this.modulatingNode = modulatingNode;
		this.logicalOperator = logicOperatorNode;
	}

	@Override
	public ILogicOperatorNode getLogicOperator() {
		return this.logicalOperator;
	}

	@Override
	public IModulatingNode getModulatingNode() {
		return this.modulatingNode;
	}

	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitLogicArc(this);
	}

}

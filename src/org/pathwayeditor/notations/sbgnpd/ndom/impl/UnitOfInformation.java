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

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public final class UnitOfInformation extends PdElement implements IUnitOfInformation {
	private final IAnnotateable entityPoolNode;
	private String value;
	
	public UnitOfInformation(int identifier, IAnnotateable entityPoolNode, String value, String asciiName) {
		super(identifier, null, asciiName);
		if(entityPoolNode == null || value == null) throw new IllegalArgumentException("parameters cannot be null");
		
		this.entityPoolNode = entityPoolNode;
		this.value = value;
	}

	@Override
	public String getAnnotation() {
		return this.value;
	}

	@Override
	public void setAnnotation(String annotation) {
		this.value = annotation;
	}

	@Override
	public IAnnotateable getEntityPoolNode(){
		return this.entityPoolNode;
	}


	@Override
	public void visit(IPdElementVisitor visitor) {
		visitor.visitUnitOfInformation(this);
	}
}

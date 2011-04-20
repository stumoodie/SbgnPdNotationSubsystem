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

import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;

public abstract class PdElement implements IPdElement {
	private final String sboTerm;
	private final int identifier;
	private final String asciiName;
	
	protected PdElement(int uniqueId, String sboTerm, String asciiName){
		this.identifier = uniqueId;
		this.sboTerm = sboTerm;
		this.asciiName = asciiName;
	}
	
	@Override
	public final String getSboId(){
		return this.sboTerm;
	}
	
	@Override
	public final int getIdentifier() {
		return this.identifier;
	}

	@Override
	public String getAsciiName() {
		return asciiName;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + identifier;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PdElement))
			return false;
		PdElement other = (PdElement) obj;
		if (identifier != other.identifier)
			return false;
		return true;
	}

}

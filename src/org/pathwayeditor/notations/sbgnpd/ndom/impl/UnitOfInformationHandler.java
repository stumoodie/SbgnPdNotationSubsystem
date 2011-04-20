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

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;

public class UnitOfInformationHandler implements IAnnotateable {
//	private static final String PREFIX_PROP_NAME = "prefix";
//	private static final String VALUE_PROP_NAME = "unitOfInfo";
	private final Set<IUnitOfInformation> unitsOfInformation;
	private final IAnnotateable annotateable;
	
	public UnitOfInformationHandler(IAnnotateable annotateable){
		this.annotateable = annotateable;
		this.unitsOfInformation = new HashSet<IUnitOfInformation>();
	}

	@Override
	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return new HashSet<IUnitOfInformation>(this.unitsOfInformation);
	}

	@Override
	public IUnitOfInformation createUnitOfInformation(int identifier, String value){
		IUnitOfInformation retVal = new UnitOfInformation(identifier, this.annotateable, "", value);
		this.unitsOfInformation.add(retVal);
		return retVal;
	}

	public void visit(IPdElementVisitor visitor) {
		for(IUnitOfInformation uoi : this.unitsOfInformation){
			uoi.visit(visitor);
		}
	}

}

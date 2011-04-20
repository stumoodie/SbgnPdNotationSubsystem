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
package org.pathwayeditor.notations.sbgnpd.export.sbgntext;

import java.util.HashMap;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class Ndom2SbgnTextTypeMapper {
	private static Ndom2SbgnTextTypeMapper anInstance = null;
	private final Map<Enum<?>, String> lookup;
	
	public static Ndom2SbgnTextTypeMapper getInstance() {
		if(anInstance == null){
			anInstance = new Ndom2SbgnTextTypeMapper();
		}
		return anInstance;
	}

	private Ndom2SbgnTextTypeMapper(){
		this.lookup = new HashMap<Enum<?>, String>();
		this.lookup.put(ProcessNodeType.ASSOCIATION, "Association");
		this.lookup.put(ProcessNodeType.DISSOCIATION, "Dissociation");
		this.lookup.put(ProcessNodeType.STANDARD, "Process");
		this.lookup.put(ProcessNodeType.UNCERTAIN_PROCESS, "Uncertain");
		this.lookup.put(ProcessNodeType.OMITTED_PROCESS, "Omitted");
		this.lookup.put(ModulatingArcType.CATALYSIS, "Catalysis");
		this.lookup.put(ModulatingArcType.INHIBITION, "Inhibition");
		this.lookup.put(ModulatingArcType.MODULATION, "Modulation");
		this.lookup.put(ModulatingArcType.NECESSARY_STIMULATION, "NecessaryStmulation");
		this.lookup.put(ModulatingArcType.STIMULATION, "Stimulation");
	}
	
	public String getBiopepaProcessType(ProcessNodeType processType) {
		return this.lookup.get(processType);
	}

	public Object getBiopepaModulationType(ModulatingArcType type) {
		return this.lookup.get(type);
	}

}

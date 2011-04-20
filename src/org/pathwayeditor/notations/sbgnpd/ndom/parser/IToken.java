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
package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingElement;


public interface IToken {

	enum TreeTokenType { NODE_ROOT, EDGE_ROOT, COMPARTMENT, MACROMOLECULE, COMPLEX,
		NA_FEATURE, SIMPLE_CHEMICAL, UNSPECIFIED_ENTITY,
		PROCESS, UNSPECIFIED_PROCESS, OMITTED_PROCESS, SOURCE, SINK, PERTURBING_AGENT,
		PHENOTYPE, SUBMAP, OR_OP, AND_OP, NOT_OP, TAG, UOI, STATE_DESCN, PRODUCTION_ARC,
		CONSUMPTION_ARC, MODULATION_ARC, STIMULATION_ARC, CATALYSIS_ARC, INHIBITION_ARC,
		NECESSARY_STIMULATION_ARC, LOGIC_ARC, EQUIVALENCE_ARC, CARDINALITY, MATERIAL_TYPE,
		MAP_DIAGRAM, ASSOCIATION, DISOCCIATION, CONCEPTUAL_TYPE, PHYSICAL_CHARACTISTIC
	}

	TreeTokenType getType();
	
	IDrawingElement getElement();

	<T extends IDrawingElement> T getTypedElement();
}

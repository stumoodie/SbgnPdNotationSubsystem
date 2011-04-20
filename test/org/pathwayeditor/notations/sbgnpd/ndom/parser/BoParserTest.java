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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdgeFactory;
import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.drawingprimitives.IRootNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNodeFactory;
import org.pathwayeditor.businessobjects.impl.facades.LinkEdgeFactoryFacade;
import org.pathwayeditor.businessobjects.impl.facades.RootNodeFacade;
import org.pathwayeditor.businessobjects.impl.facades.ShapeNodeFactoryFacade;
import org.pathwayeditor.businessobjects.management.IModelFactory;
import org.pathwayeditor.businessobjects.management.ModelFactory;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;

public class BoParserTest {
	private static final String CANVAS_NAME = "testCanvas";
	private static final int EXPECTED_NUMS_PROCESSES = 1;
	private static final int EXPECTED_NUM_EPNS = 5;
	private static final int EXPECTED_NUM_CPTS = 3;
	private IModel canvas;
	private SbgnPdNotationSyntaxService sbgnSyntax;
	private IBoParser testInstance;
	private ITreeLexer lexer;

	@Before
	public void setUp() throws Exception {
		SbgnPdNotationSubsystem sbgnSubsystem = new SbgnPdNotationSubsystem();
		this.sbgnSyntax = sbgnSubsystem.getSyntaxService();
		IModelFactory canvasFact = new ModelFactory();
		canvasFact.setNotationSubsystem(sbgnSubsystem);
		canvasFact.setName(CANVAS_NAME);
		this.canvas = canvasFact.createModel();
		IRootNode root = new RootNodeFacade(this.canvas.getGraph().getRoot());
		IShapeNode cmpt1 = createShapeNode(root, this.sbgnSyntax.getCompartment());
		// empty compartment
		createShapeNode(root, this.sbgnSyntax.getCompartment());
		// empty mm on the root compartment
		createShapeNode(root, this.sbgnSyntax.getMacromolecule());
		IShapeNode mm1 = createShapeNode(cmpt1, this.sbgnSyntax.getMacromolecule());
		createShapeNode(mm1, this.sbgnSyntax.getState());
		createShapeNode(mm1, this.sbgnSyntax.getUnitOfInf());
		// empty complex
		createShapeNode(cmpt1, this.sbgnSyntax.getComplex());
		IShapeNode mm2 = createShapeNode(cmpt1, this.sbgnSyntax.getMacromolecule());
		IShapeNode na = createShapeNode(cmpt1, this.sbgnSyntax.getGeneticUnit());
		createShapeNode(na, this.sbgnSyntax.getState());
		createShapeNode(na, this.sbgnSyntax.getState());
		IShapeNode process = createShapeNode(root, this.sbgnSyntax.getProcess());
		createLinkEdge(mm1, process, this.sbgnSyntax.getConsumption());
		createLinkEdge(process, na, this.sbgnSyntax.getProduction());
		createLinkEdge(mm2, process, this.sbgnSyntax.getModulation());
		this.testInstance = new BoParser(new NdomBuilder());
		this.lexer = new BoTreeLexer(this.canvas);
	}
	
	
	private static ILinkEdge createLinkEdge(IShapeNode source, IShapeNode target, LinkObjectType type) {
		ILinkEdgeFactory linkFact = new LinkEdgeFactoryFacade(source.getGraphElement().getGraph().edgeFactory());
		linkFact.setShapeNodePair(source, target);
		linkFact.setObjectType(type);
		return linkFact.createLinkEdge();
	}


	private static IShapeNode createShapeNode(IDrawingNode parent, IShapeObjectType objectType){
		IShapeNodeFactory shapeFact = new ShapeNodeFactoryFacade(parent.getGraphElement().getChildCompoundGraph().nodeFactory());
		shapeFact.setObjectType(objectType);
		IShapeNode cmpt = shapeFact.createShapeNode();
		return cmpt;
	}

	@After
	public void tearDown() throws Exception {
		this.canvas = null;
		this.sbgnSyntax = null;
		this.testInstance = null;
	}

	@Test
	public void testGetMapDiagram() {
		assertNull("no map set", this.testInstance.getNDomBuilder().getNdom());
	}

	@Test
	public void testParse() throws TreeParseException {
		this.testInstance.parse(this.lexer);
		assertNotNull("map created", this.testInstance.getNDomBuilder().getNdom());
		IMapDiagram mapDiagram = this.testInstance.getNDomBuilder().getNdom();
		assertEquals("expected num epns", EXPECTED_NUM_EPNS, mapDiagram.totalNumEpns());
		assertEquals("expected num epns", EXPECTED_NUMS_PROCESSES, mapDiagram.totalNumProcesses());
		assertEquals("expected num compartents", EXPECTED_NUM_CPTS, mapDiagram.numCompartments());
	}
}

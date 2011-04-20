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
package org.pathwayeditor.notations.sbgnpd.export.biopepa;


import java.io.File;

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
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IAnnotatedObject;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IIntegerAnnotationProperty;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IPlainTextAnnotationProperty;
import org.pathwayeditor.businessobjects.impl.facades.LinkEdgeFactoryFacade;
import org.pathwayeditor.businessobjects.impl.facades.RootNodeFacade;
import org.pathwayeditor.businessobjects.impl.facades.ShapeNodeFactoryFacade;
import org.pathwayeditor.businessobjects.management.IModelFactory;
import org.pathwayeditor.businessobjects.management.ModelFactory;
import org.pathwayeditor.businessobjects.notationsubsystem.ExportServiceException;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.notations.sbgnpd.export.sbgntext.SbgnTextExportController;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;

public class BioPepaExportControllerTest {
	private static final String CANVAS_NAME = "testCanvas";
	private static final String EPN_COUNT_PROP_NAME = "entityCount";
	private static final String RATE_FWD_EQN_PROP_NAME = "fwdRate";
	private static final String RATE_REV_EQN_PROP_NAME = "revRate";
	
	private SbgnTextExportController testInstance;
	private File testFile;

	private IModel createTestCanvas(){
		SbgnPdNotationSubsystem sbgnSubsystem = new SbgnPdNotationSubsystem();
		SbgnPdNotationSyntaxService sbgnSyntax = sbgnSubsystem.getSyntaxService();
		IModelFactory canvasFact = new ModelFactory();
		canvasFact.setNotationSubsystem(sbgnSubsystem);
		canvasFact.setName(CANVAS_NAME);
		IModel canvas = canvasFact.createModel();
		IRootNode root = new RootNodeFacade(canvas.getGraph().getRoot());
		IShapeNode cmpt = createShapeNode(root, sbgnSyntax.getCompartment());
		IShapeNode mm1 = createShapeNode(cmpt, sbgnSyntax.getMacromolecule());
		setIntegerProperty(mm1.getAttribute(), EPN_COUNT_PROP_NAME, 100);
		createShapeNode(mm1, sbgnSyntax.getState());
		createShapeNode(mm1, sbgnSyntax.getUnitOfInf());
		IShapeNode mm2 = createShapeNode(cmpt, sbgnSyntax.getMacromolecule());
		setIntegerProperty(mm2.getAttribute(), EPN_COUNT_PROP_NAME, 1000);
		IShapeNode na = createShapeNode(cmpt, sbgnSyntax.getGeneticUnit());
		setIntegerProperty(na.getAttribute(), EPN_COUNT_PROP_NAME, 10000);
		createShapeNode(na, sbgnSyntax.getState());
		createShapeNode(na, sbgnSyntax.getState());
		IShapeNode process = createShapeNode(cmpt, sbgnSyntax.getProcess());
		setTextProperty(process.getAttribute(), RATE_FWD_EQN_PROP_NAME, "1 * 5");
		setTextProperty(process.getAttribute(), RATE_REV_EQN_PROP_NAME, "");
		createLinkEdge(mm1, process, sbgnSyntax.getConsumption());
		createLinkEdge(process, na, sbgnSyntax.getProduction());
		createLinkEdge(mm2, process, sbgnSyntax.getModulation());
		
		return canvas;
	}
	
	private void setIntegerProperty(IAnnotatedObject obj, String name, int value){
		IIntegerAnnotationProperty prop = (IIntegerAnnotationProperty)obj.getProperty(name);
		prop.setValue(value);
	}
	
	
	private void setTextProperty(IAnnotatedObject obj, String name, String value){
		IPlainTextAnnotationProperty prop = (IPlainTextAnnotationProperty)obj.getProperty(name);
		prop.setValue(value);
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

	@Before
	public void setUp() throws Exception {
		this.testInstance = new SbgnTextExportController();
		this.testFile = File.createTempFile("foo", "txt");
		this.testInstance.setExportFile(testFile);
		IModel canvasToExport = createTestCanvas();
		this.testInstance.setCanvasToExport(canvasToExport);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		if(this.testFile != null){
//			this.testFile.delete();
		}
		this.testFile = null;
	}

	
	@Test
	public void testWriteCanvas() throws ExportServiceException{
		this.testInstance.exportFile();
		System.out.println("Test file=" + this.testFile.getPath());
	}
}

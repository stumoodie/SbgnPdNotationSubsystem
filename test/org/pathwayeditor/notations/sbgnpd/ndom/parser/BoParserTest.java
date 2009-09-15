package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdgeFactory;
import org.pathwayeditor.businessobjects.drawingprimitives.IRootNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNodeFactory;
import org.pathwayeditor.businessobjects.management.NonPersistentCanvasFactory;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;

public class BoParserTest {
	private static final String CANVAS_NAME = "testCanvas";
	private static final int EXPECTED_NUMS_PROCESSES = 1;
	private static final int EXPECTED_NUM_EPNS = 3;
	private ICanvas canvas;
	private SbgnPdNotationSyntaxService sbgnSyntax;
	private IBoParser testInstance;
	private ITreeLexer lexer;

	@Before
	public void setUp() throws Exception {
		SbgnPdNotationSubsystem sbgnSubsystem = new SbgnPdNotationSubsystem();
		this.sbgnSyntax = sbgnSubsystem.getSyntaxService();
		NonPersistentCanvasFactory canvasFact = NonPersistentCanvasFactory.getInstance();
		canvasFact.setNotationSubsystem(sbgnSubsystem);
		canvasFact.setCanvasName(CANVAS_NAME);
		this.canvas = canvasFact.createNewCanvas();
		IRootNode root = this.canvas.getModel().getRootNode();
		IShapeNode cmpt = createShapeNode(root, this.sbgnSyntax.getCompartment());
		IShapeNode mm1 = createShapeNode(cmpt, this.sbgnSyntax.getMacromolecule());
		createShapeNode(mm1, this.sbgnSyntax.getState());
		createShapeNode(mm1, this.sbgnSyntax.getUnitOfInf());
		IShapeNode mm2 = createShapeNode(cmpt, this.sbgnSyntax.getMacromolecule());
		IShapeNode na = createShapeNode(cmpt, this.sbgnSyntax.getGeneticUnit());
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
		ILinkEdgeFactory linkFact = source.getModel().linkEdgeFactory();
		linkFact.setShapeNodePair(source, target);
		linkFact.setObjectType(type);
		return linkFact.createLinkEdge();
	}


	private static IShapeNode createShapeNode(IDrawingNode parent, IShapeObjectType objectType){
		IShapeNodeFactory shapeFact = parent.getSubModel().shapeNodeFactory();
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
	}
}

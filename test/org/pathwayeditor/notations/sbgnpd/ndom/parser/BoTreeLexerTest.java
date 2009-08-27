package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdgeFactory;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNodeFactory;
import org.pathwayeditor.businessobjects.management.NonPersistentCanvasFactory;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;

public class BoTreeLexerTest {
	private static final String CANVAS_NAME = "testCanvas";
	private ICanvas canvas;
	private SbgnPdNotationSyntaxService sbgnSyntax;
	private ITreeLexer testInstance;
	
	@Before
	public void setUp() throws Exception {
		SbgnPdNotationSubsystem sbgnSubsystem = new SbgnPdNotationSubsystem();
		this.sbgnSyntax = sbgnSubsystem.getSyntaxService();
		NonPersistentCanvasFactory canvasFact = NonPersistentCanvasFactory.getInstance();
		canvasFact.setNotationSubsystem(sbgnSubsystem);
		canvasFact.setCanvasName(CANVAS_NAME);
		this.canvas = canvasFact.createNewCanvas();
		IShapeNode cmpt = createShapeNode(this.canvas.getModel().getRootNode(), this.sbgnSyntax.getCompartment());
		IShapeNode mm1 = createShapeNode(cmpt, this.sbgnSyntax.getMacromolecule());
		createShapeNode(mm1, this.sbgnSyntax.getState());
		createShapeNode(mm1, this.sbgnSyntax.getUnitOfInf());
		IShapeNode na = createShapeNode(cmpt, this.sbgnSyntax.getGeneticUnit());
		createShapeNode(na, this.sbgnSyntax.getState());
		createShapeNode(na, this.sbgnSyntax.getState());
		IShapeNode process = createShapeNode(cmpt, this.sbgnSyntax.getProcess());
		createLinkEdge(mm1, process, this.sbgnSyntax.getConsumption());
		createLinkEdge(process, na, this.sbgnSyntax.getProduction());
		this.testInstance = new BoTreeLexer(this.canvas);
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
	public void testRightDown() throws TreeParseException {
		assertFalse("has no down tokens", this.testInstance.hasDownTokens());
		assertTrue("has right tokens", this.testInstance.hasRightTokens());
//		assertTrue("has unread tokens", this.testInstance.hasUnreadTokens());
		assertNull("curr token is not set", this.testInstance.getCurrent());
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.down();
		assertTrue("has right tokens", this.testInstance.hasRightTokens());
		assertFalse("has no down tokens", this.testInstance.hasDownTokens());
//		assertTrue("has unread tokens", this.testInstance.hasUnreadTokens());
		assertNull("curr token is not set", this.testInstance.getCurrent());
	}

	@Test(expected=IllegalStateException.class)
	public void testDownOnly() {
		this.testInstance.down();
	}

	@Test
	public void testGetCurrent() {
		assertNull("curr token is not set", this.testInstance.getCurrent());
	}

	@Test
	public void testHasDownTokens() {
		assertFalse("has no down tokens", this.testInstance.hasDownTokens());
	}

	@Test
	public void testHasRightTokens() {
		assertTrue("has right tokens", this.testInstance.hasRightTokens());
	}

	@Test
	public void testIsRightLookaheadMatchEnumSetOfTreeTokenType() {
		EnumSet<TreeTokenType> testSet = EnumSet.of(TreeTokenType.MAP_DIAGRAM, TreeTokenType.NODE_ROOT, TreeTokenType.MACROMOLECULE);
		assertTrue("lookahead expected", this.testInstance.isRightLookaheadMatch(testSet));
	}

	@Test
	public void testIsRightLookaheadMatchTreeTokenType() {
		assertTrue("lookahead expected", this.testInstance.isRightLookaheadMatch(TreeTokenType.NODE_ROOT));
	}

	@Test
	public void testMatch() throws TreeParseException {
		this.testInstance.match(TreeTokenType.NODE_ROOT);
	}
	
	@Test(expected=UnexpectedTokenException.class)
	public void testNoMatch() throws TreeParseException {
		this.testInstance.match(TreeTokenType.MACROMOLECULE);
	}

	@Test(expected=NoSuchElementException.class)
	public void testNoMoreChildren() throws TreeParseException {
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.match(TreeTokenType.EDGE_ROOT);
		this.testInstance.match(TreeTokenType.MACROMOLECULE);
	}

//	@Test
//	public void testUnreadTokens(){
//		assertTrue("Unread tokens expected", this.testInstance.hasUnreadTokens());
//	}
	
	@Test(expected=IllegalStateException.class)
	public void testNoCurrentTokenUp() {
		this.testInstance.up();
	}

	@Test(expected=IllegalStateException.class)
	public void testTokenUp() throws TreeParseException {
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.up();
	}

	@Test
	public void testTokenDownUp() throws TreeParseException {
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.MAP_DIAGRAM);
		assertFalse("no right tokens", this.testInstance.hasRightTokens());
		assertTrue("down tokens", this.testInstance.hasDownTokens());
		this.testInstance.down();
		assertTrue("right tokens", this.testInstance.hasRightTokens());
		assertFalse("no down tokens", this.testInstance.hasDownTokens());
		this.testInstance.match(TreeTokenType.COMPARTMENT);
		assertFalse("no right tokens", this.testInstance.hasRightTokens());
		assertTrue("has down tokens", this.testInstance.hasDownTokens());
		this.testInstance.up();
		assertEquals("curr token is not set", TreeTokenType.MAP_DIAGRAM, this.testInstance.getCurrent().getType());
		assertFalse("no right tokens", this.testInstance.hasRightTokens());
		assertTrue("down tokens", this.testInstance.hasDownTokens());
	}

	@Test
	public void testTokenDownToBottomThenUpOneAndCurrentNodeIsSet() throws TreeParseException {
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.MAP_DIAGRAM);
		assertFalse("no right tokens", this.testInstance.hasRightTokens());
		assertTrue("down tokens", this.testInstance.hasDownTokens());
		this.testInstance.down();
		assertTrue("right tokens", this.testInstance.hasRightTokens());
		assertFalse("no down tokens", this.testInstance.hasDownTokens());
		this.testInstance.match(TreeTokenType.COMPARTMENT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.MACROMOLECULE);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.STATE_DESCN);
		this.testInstance.up();
		assertEquals("expected token", TreeTokenType.MACROMOLECULE, this.testInstance.getCurrent().getType());
		assertTrue("has right tokens", this.testInstance.hasRightTokens());
		assertTrue("down tokens", this.testInstance.hasDownTokens());
		assertTrue("expected look right", this.testInstance.isRightLookaheadMatch(TreeTokenType.NA_FEATURE));
		this.testInstance.match(TreeTokenType.NA_FEATURE);
	}
	
	@Test
	public void testExhaustiveWalkOverInputTree() throws TreeParseException{
		this.testInstance.match(TreeTokenType.NODE_ROOT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.MAP_DIAGRAM);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.COMPARTMENT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.MACROMOLECULE);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.STATE_DESCN);
		this.testInstance.match(TreeTokenType.UOI);
		this.testInstance.up();
		this.testInstance.match(TreeTokenType.NA_FEATURE);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.STATE_DESCN);
		this.testInstance.match(TreeTokenType.STATE_DESCN);
		this.testInstance.up();
		this.testInstance.match(TreeTokenType.PROCESS);
		this.testInstance.up();
		this.testInstance.up();
		this.testInstance.up();
		this.testInstance.match(TreeTokenType.EDGE_ROOT);
		this.testInstance.down();
		this.testInstance.match(TreeTokenType.CONSUMPTION_ARC);
		this.testInstance.match(TreeTokenType.PRODUCTION_ARC);
		assertFalse("read all tokens", this.testInstance.hasRightTokens());
	}

}

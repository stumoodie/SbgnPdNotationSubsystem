package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingElement;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.drawingprimitives.IRootNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ITypedDrawingNode;
import org.pathwayeditor.businessobjects.impl.facades.LinkEdgeFacade;
import org.pathwayeditor.businessobjects.impl.facades.RootNodeFacade;
import org.pathwayeditor.businessobjects.impl.facades.ShapeNodeFacade;
import org.pathwayeditor.businessobjects.impl.facades.SubModelFacade;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;

import uk.ac.ed.inf.graph.compound.ICompoundEdge;
import uk.ac.ed.inf.graph.compound.ICompoundNode;

public class BoTreeLexer implements ITreeLexer {
	private ILexerTreeNode currNode;
	private final LinkedList<ILexerTreeNode> treeStack;
	private final TokenFactory tokenFactory;
	private final IModel model;
	
	public BoTreeLexer(IModel canvas){
		model = canvas;
		this.treeStack = new LinkedList<ILexerTreeNode>();
		IRootNode rootNode = new RootNodeFacade(model.getGraph().getRoot());
		this.currNode = createTreeRootNode(rootNode);
		this.tokenFactory = new TokenFactory((SbgnPdNotationSyntaxService)rootNode.getAttribute().getObjectType().getSyntaxService());
	}
	
	
	private ILexerTreeNode createTreeNode(IToken parentType){
		ITypedDrawingNode parentDrawingNode = parentType.getTypedElement();
		BoLexerTreeNode node = new BoLexerTreeNode(parentType);
		Iterator<ICompoundNode> iter = new SubModelFacade(parentDrawingNode.getGraphElement().getChildCompoundGraph()).shapeNodeIterator();
		while(iter.hasNext()){
			IShapeNode childNode = new ShapeNodeFacade(iter.next());
			IToken childToken = this.tokenFactory.createToken(childNode);
			node.addChild(childToken);
		}
		return node;
	}
	
	private ILexerTreeNode createNodeRootTreeNode(IToken parentType){
		ITypedDrawingNode parentDrawingNode = parentType.getTypedElement();
		BoLexerTreeNode node = new BoLexerTreeNode(parentType);
		node.addChild(new BoToken(TreeTokenType.MAP_DIAGRAM, parentDrawingNode));
		return node;
	}
	
	private ILexerTreeNode createEdgeRootTreeNode(IToken parentType){
		BoLexerTreeNode node = new BoLexerTreeNode(parentType);
		Iterator<ICompoundEdge> iter = model.linkEdgeIterator();
		while(iter.hasNext()){
			ILinkEdge childNode = new LinkEdgeFacade(iter.next());
			IToken childToken = this.tokenFactory.createToken(childNode);
			node.addChild(childToken);
		}
		return node;
	}
	
	private ILexerTreeNode createTreeRootNode(IRootNode rootNode){
		IToken rootToken = new BoToken(null, null);
		BoLexerTreeNode node = new BoLexerTreeNode(rootToken);
		node.addChild(new BoToken(TreeTokenType.NODE_ROOT, rootNode));
		node.addChild(new BoToken(TreeTokenType.EDGE_ROOT, rootNode));
		return node;
	}
	
	@Override
	public void down() {
		if(this.currNode.getCurrentChild() == null) throw new IllegalStateException("Current token not set");
		
		this.treeStack.addFirst(this.currNode);
		if(this.currNode.getCurrentChild().getType().equals(TreeTokenType.EDGE_ROOT)){
			this.currNode = createEdgeRootTreeNode(this.currNode.getCurrentChild());
		}
		else if(this.currNode.getCurrentChild().getType().equals(TreeTokenType.NODE_ROOT)){
			this.currNode = createNodeRootTreeNode(this.currNode.getCurrentChild());
		}
		else{
			this.currNode = createTreeNode(this.currNode.getCurrentChild());
		}
	}

	@Override
	public IToken getCurrent() {
		return this.currNode.getCurrentChild();
	}

	@Override
	public boolean hasDownTokens() {
		boolean retVal = false;
		if(this.currNode.getCurrentChild() != null){
			IDrawingElement element = this.currNode.getCurrentChild().getTypedElement();
			if(element instanceof IDrawingNode){
				IDrawingNode node = (IDrawingNode)element;
				retVal = new SubModelFacade(node.getGraphElement().getChildCompoundGraph()).numShapeNodes() > 0;
			}
		}
		return retVal;
	}	

	@Override
	public boolean hasRightTokens() {
		return this.currNode.numChildren() > 0;
	}

	@Override
	public boolean isRightLookaheadMatch(EnumSet<TreeTokenType> types) {
		TreeTokenType rightType = this.currNode.peekNextChild().getType();
		return types.contains(rightType);
	}

	@Override
	public boolean isRightLookaheadMatch(TreeTokenType type) {
		TreeTokenType rightType = this.currNode.peekNextChild().getType();
		return type.equals(rightType);
	}

	@Override
	public void match(TreeTokenType expectedToken) throws UnexpectedTokenException {
		if(!this.hasRightTokens()) throw new NoSuchElementException("no more right tokens");
		
		this.currNode.nextChild();
		if(!this.currNode.getCurrentChild().getType().equals(expectedToken)){
			throw new UnexpectedTokenException(expectedToken, this.currNode.getCurrentChild());
		}
	}

	@Override
	public void up() {
		if(!this.treeStack.isEmpty()){
			this.currNode = this.treeStack.poll();
		}
		else{
			throw new IllegalStateException("At root of tree cannot go up");
		}
	}

}

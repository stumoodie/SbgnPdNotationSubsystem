package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingElement;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.drawingprimitives.IRootNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ITypedDrawingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;

public class BoTreeLexer implements ITreeLexer {
	private ILexerTreeNode currNode;
	private final LinkedList<ILexerTreeNode> treeStack;
	private final TokenFactory tokenFactory;
	
	public BoTreeLexer(ICanvas canvas){
		this.treeStack = new LinkedList<ILexerTreeNode>();
		this.currNode = createTreeRootNode(canvas.getModel().getRootNode());
		this.tokenFactory = new TokenFactory((SbgnPdNotationSyntaxService)canvas.getNotationSubsystem().getSyntaxService());
	}
	
	
	private ILexerTreeNode createTreeNode(IToken parentType){
		ITypedDrawingNode parentDrawingNode = parentType.getTypedElement();
		BoLexerTreeNode node = new BoLexerTreeNode(parentType);
		Iterator<IShapeNode> iter = parentDrawingNode.getSubModel().shapeNodeIterator();
		while(iter.hasNext()){
			IShapeNode childNode = iter.next();
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
		ITypedDrawingNode parentDrawingNode = parentType.getTypedElement();
		BoLexerTreeNode node = new BoLexerTreeNode(parentType);
		IModel model = parentDrawingNode.getModel();
		Iterator<ILinkEdge> iter = model.linkEdgeIterator();
		while(iter.hasNext()){
			ILinkEdge childNode = iter.next();
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

	public IToken getCurrent() {
		return this.currNode.getCurrentChild();
	}

	public boolean hasDownTokens() {
		boolean retVal = false;
		if(this.currNode.getCurrentChild() != null){
			IDrawingElement element = this.currNode.getCurrentChild().getTypedElement();
			if(element instanceof IDrawingNode){
				IDrawingNode node = (IDrawingNode)element;
				retVal = node.getSubModel().numShapeNodes() > 0;
			}
		}
		return retVal;
	}	

	public boolean hasRightTokens() {
		return this.currNode.numChildren() > 0;
	}

	public boolean isRightLookaheadMatch(EnumSet<TreeTokenType> types) {
		TreeTokenType rightType = this.currNode.peekNextChild().getType();
		return types.contains(rightType);
	}

	public boolean isRightLookaheadMatch(TreeTokenType type) {
		TreeTokenType rightType = this.currNode.peekNextChild().getType();
		return type.equals(rightType);
	}

	public void match(TreeTokenType expectedToken) throws UnexpectedTokenException {
		if(!this.hasRightTokens()) throw new NoSuchElementException("no more right tokens");
		
		this.currNode.nextChild();
		if(!this.currNode.getCurrentChild().getType().equals(expectedToken)){
			throw new UnexpectedTokenException(expectedToken, this.currNode.getCurrentChild());
		}
	}

	public void up() {
		if(!this.treeStack.isEmpty()){
			this.currNode = this.treeStack.poll();
		}
		else{
			throw new IllegalStateException("At root of tree cannot go up");
		}
	}

}

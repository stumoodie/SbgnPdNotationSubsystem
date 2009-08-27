package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.LinkedList;
import java.util.Queue;

public class BoLexerTreeNode implements ILexerTreeNode {
	private final IToken token;
	private final Queue<IToken> children;
	private IToken currentChild = null;
	
	public BoLexerTreeNode(IToken currToken){
		this.token = currToken;
		this.children = new LinkedList<IToken>();
	}
	
	void addChild(IToken child){
		this.children.add(child);
	}
	
	
	public IToken getParentToken() {
		return this.token;
	}

	public void nextChild() {
		this.currentChild = this.children.poll();
	}
	
	public int numChildren(){
		return this.children.size();
	}

	public IToken getCurrentChild() {
		return this.currentChild;
	}

	public IToken peekNextChild() {
		return this.children.peek();
	}

	
	@Override
	public String toString(){
		StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
		buf.append("(parent=");
		buf.append(this.token);
		buf.append(",currChild=");
		buf.append(this.currentChild);
		buf.append(",children=");
		buf.append(this.children);
		buf.append(")");
		return buf.toString();
	}
}

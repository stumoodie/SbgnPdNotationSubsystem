package org.pathwayeditor.notations.sbgnpd.ndom.parser;

public interface ILexerTreeNode {

	IToken getParentToken();

	void nextChild();
	
	IToken getCurrentChild();
	
	int numChildren();

	IToken peekNextChild();
	
}

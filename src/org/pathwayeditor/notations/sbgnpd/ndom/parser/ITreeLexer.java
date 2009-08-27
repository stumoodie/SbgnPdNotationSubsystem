package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.EnumSet;

import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;

public interface ITreeLexer {
//	void right();
	
	void down();
	
	void up();

	IToken getCurrent();
	
	void match(TreeTokenType expectedToken) throws UnexpectedTokenException;
	
//	void matchCurrent(EnumSet<IToken.TreeTokenType> expectedTokens) throws UnexpectedTokenException;
	
//	boolean isCurrentMatch(EnumSet<IToken.TreeTokenType> enumSet);
//
//	boolean isCurrentMatch(IToken.TreeTokenType compartment);

	boolean hasDownTokens();

	boolean hasRightTokens();

//	boolean hasUnreadTokens();
	
//	boolean isDownLookaheadMatch(EnumSet<IToken.TreeTokenType> enumSet);
//
//	boolean isDownLookaheadMatch(IToken.TreeTokenType compartment);

	boolean isRightLookaheadMatch(EnumSet<IToken.TreeTokenType> enumSet);

	boolean isRightLookaheadMatch(IToken.TreeTokenType compartment);
}

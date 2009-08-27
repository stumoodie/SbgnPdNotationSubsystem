package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;

public class UnexpectedTokenException extends TreeParseException {
	private static final long serialVersionUID = 8409555928178813340L;

	public UnexpectedTokenException(TreeTokenType expectedToken, IToken currentToken){
		super(currentToken, createMsg(expectedToken));
	}
	
	private static String createMsg(TreeTokenType expectedToken){
		StringBuilder buf = new StringBuilder("Expected token=");
		buf.append(expectedToken);
		buf.append(".");
		return buf.toString();
	}
}

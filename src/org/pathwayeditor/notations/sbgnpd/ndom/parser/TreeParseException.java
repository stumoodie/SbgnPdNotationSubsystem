package org.pathwayeditor.notations.sbgnpd.ndom.parser;

public class TreeParseException extends Exception {
	private static final long serialVersionUID = 6836465476036389491L;

	public TreeParseException(IToken current, String msg) {
		super(createMsg(current, msg));
	}

	
	public TreeParseException(IToken current, String msg, Throwable t) {
		super(createMsg(current, msg), t);
	}

	
	private static String createMsg(IToken current, String msg){
		StringBuilder buf = new StringBuilder("Parse error at token=");
		buf.append(current.toString());
		buf.append(". Error msg=");
		buf.append(msg);
		return buf.toString();
	}
}

package org.pathwayeditor.notations.sbgnpd.ndom.parser;


public interface IBoParser {

	INdomBuilder getNDomBuilder();
	
	void parse(ITreeLexer lexer) throws TreeParseException;

}

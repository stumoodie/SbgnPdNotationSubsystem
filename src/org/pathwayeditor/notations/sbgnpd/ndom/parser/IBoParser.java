package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public interface IBoParser {

	IMapDiagram getMapDiagram();
	
	void parse(ITreeLexer lexer) throws TreeParseException;

}

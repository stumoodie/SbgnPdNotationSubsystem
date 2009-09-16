package org.pathwayeditor.notations.sbgnpd.export;

public interface IExportInfo {
	enum InfoType { WARNING, ERROR, INFO };
	
	InfoType getType();
	
	String getMessage();
}

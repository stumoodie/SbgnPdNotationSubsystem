package org.pathwayeditor.notations.sbgnpd.export;

public interface IReportItem {
	enum InfoType { WARNING, ERROR, INFO };
	
	InfoType getType();
	
	String getMessage();
}

package org.pathwayeditor.notations.sbgnpd.export;

import java.io.File;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public interface IExportWriter {

	File getFile();
	
	IMapDiagram getNdom();

	IReportLog getReportLog();
	
	void writeExport();
	
}

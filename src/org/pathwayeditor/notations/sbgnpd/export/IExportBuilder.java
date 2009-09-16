package org.pathwayeditor.notations.sbgnpd.export;

import java.io.File;
import java.util.Iterator;

import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;

public interface IExportBuilder {

	File getFile();
	
	IMapDiagram getNdom();
	
	void writeExport();
	
	boolean hasErrors();
	
	boolean hasWarnings();
	
	Iterator<IExportInfo> infoIterator();
}

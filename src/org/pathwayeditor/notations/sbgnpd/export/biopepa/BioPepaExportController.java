package org.pathwayeditor.notations.sbgnpd.export.biopepa;

import java.io.File;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.notationsubsystem.ExportServiceException;
import org.pathwayeditor.notations.sbgnpd.export.IExportWriter;
import org.pathwayeditor.notations.sbgnpd.export.IReportLog;
import org.pathwayeditor.notations.sbgnpd.export.ReportLog;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.BoParser;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.BoTreeLexer;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IBoParser;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.ITreeLexer;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.NdomBuilder;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.TreeParseException;

public class BioPepaExportController {
	private File exportFile;
	private ICanvas canvasToExport;
	
	public BioPepaExportController(){
		
	}

	public File getExportFile() {
		return exportFile;
	}

	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
	}

	public ICanvas getCanvasToExport() {
		return canvasToExport;
	}

	public void setCanvasToExport(ICanvas canvasToExport) {
		this.canvasToExport = canvasToExport;
	}
	
	public void exportFile() throws ExportServiceException {
		try {
			IBoParser parser = new BoParser(new NdomBuilder());
			ITreeLexer lexer = new BoTreeLexer(this.canvasToExport);
			parser.parse(lexer);
			IMapDiagram ndom = parser.getNDomBuilder().getNdom();
			IReportLog log = new ReportLog();
			IExportWriter writer = new BioPepaExportWriter(ndom, this.getExportFile(), log);
			writer.writeExport();
			if(log.hasErrors()){
				throw new ExportServiceException("One or more errors occurred during export to BioPEPA.");
			}
		} catch (TreeParseException e) {
			throw new ExportServiceException("An error occurred generating the NDOM. The map may not be a valid SBGN-PD map.", e);
		}
	}
	
}

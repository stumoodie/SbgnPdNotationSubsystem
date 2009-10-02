package org.pathwayeditor.notations.sbgnpd.services;

import java.io.File;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.notationsubsystem.ExportServiceException;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationExportService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.export.biopepa.BioPepaExportController;

public class SbgnPdBioPepaExportService implements INotationExportService {
	private final INotationSubsystem subsystem;
	private final BioPepaExportController controller;
	

	public SbgnPdBioPepaExportService(INotationSubsystem subsystem){
		this.subsystem = subsystem;
		this.controller = new BioPepaExportController();
	}
	
	public void exportMap(ICanvas canvas, File exportFile) throws ExportServiceException {
		if(exportFile.getParentFile().canWrite()
				&& ((exportFile.exists() && exportFile.canWrite()) || !exportFile.exists())){
			this.controller.setExportFile(exportFile);
			this.controller.setCanvasToExport(canvas);
			this.controller.exportFile();
		}
		else{
			throw new ExportServiceException("Chosen export file cannot be written to: " + exportFile.getName());
		}
	}

	public String getCode() {
		return "biopepa";
	}

	public String getDisplayName() {
		return "BioPEPA";
	}

	public String getRecommendedSuffix() {
		return "sbgnTEXT";
	}

	public INotation getNotation() {
		return this.subsystem.getNotation();
	}

	public INotationSubsystem getNotationSubsystem() {
		return this.subsystem;
	}

}

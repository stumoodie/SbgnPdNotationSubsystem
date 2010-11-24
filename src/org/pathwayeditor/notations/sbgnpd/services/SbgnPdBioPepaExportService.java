package org.pathwayeditor.notations.sbgnpd.services;

import java.io.File;

import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.notationsubsystem.ExportServiceException;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationExportService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.notations.sbgnpd.export.sbgntext.SbgnTextExportController;

public class SbgnPdBioPepaExportService implements INotationExportService {
	private final INotationSubsystem subsystem;
	private final SbgnTextExportController controller;
	

	public SbgnPdBioPepaExportService(INotationSubsystem subsystem){
		this.subsystem = subsystem;
		this.controller = new SbgnTextExportController();
	}
	
	@Override
	public void exportMap(IModel canvas, File exportFile) throws ExportServiceException {
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

	@Override
	public String getCode() {
		return "sbgntext";
	}

	@Override
	public String getDisplayName() {
		return "SBGN textual mapping";
	}

	@Override
	public String getRecommendedSuffix() {
		return "sbt";
	}

	@Override
	public INotation getNotation() {
		return this.subsystem.getNotation();
	}

	@Override
	public INotationSubsystem getNotationSubsystem() {
		return this.subsystem;
	}

}

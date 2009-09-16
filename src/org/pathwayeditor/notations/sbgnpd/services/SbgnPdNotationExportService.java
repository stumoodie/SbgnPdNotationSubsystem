package org.pathwayeditor.notations.sbgnpd.services;

import java.io.File;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.notationsubsystem.ExportServiceException;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationExportService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;

public class SbgnPdNotationExportService implements INotationExportService {
	private final INotationSubsystem subsystem;
	

	public SbgnPdNotationExportService(INotationSubsystem subsystem){
		this.subsystem = subsystem;
	}
	
	public void exportMap(ICanvas canvas, File exportFile) throws ExportServiceException {
		
	}

	public String getCode() {
		return "biopepa";
	}

	public String getDisplayName() {
		return "BioPEPA";
	}

	public String getRecommendedSuffix() {
		return "bpp";
	}

	public INotation getNotation() {
		return this.subsystem.getNotation();
	}

	public INotationSubsystem getNotationSubsystem() {
		return this.subsystem;
	}

}

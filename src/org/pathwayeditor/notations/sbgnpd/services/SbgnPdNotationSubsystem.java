package org.pathwayeditor.notations.sbgnpd.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.Version;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationAutolayoutService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationConversionService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationExportService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationImportService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationPluginService;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationValidationService;
import org.pathwayeditor.notationsubsystem.toolkit.definition.GeneralNotation;

public class SbgnPdNotationSubsystem implements INotationSubsystem {
	private static final String GLOBAL_ID = "org.pathwayeditor.notations.sbgnpd";
	private static final String DESCRIPTION = "SBGN Process Description Notation";
	private static final String NAME = "SBGN-PD";
	private static final Version VERSION = new Version(0, 9, 3);
	private SbgnPdNotationSyntaxService syntaxService;
	private SbgnPdValidationService validationService;
	private final List<INotationExportService> exportServices;
	private INotation context;

	public SbgnPdNotationSubsystem() {
		this.context = new GeneralNotation(GLOBAL_ID, NAME, DESCRIPTION, VERSION);
		this.syntaxService = new SbgnPdNotationSyntaxService(this);
		this.validationService=new SbgnPdValidationService(this);
		this.exportServices = new LinkedList<INotationExportService>();
		this.exportServices.add(new SbgnPdBioPepaExportService(this));
	}
	

	public INotation getNotation() {
		return this.context;
	}

	public Set<INotationExportService> getExportServices() {
		return new HashSet<INotationExportService>(this.exportServices);
	}

	public Set<INotationImportService> getImportServices() {
		return Collections.emptySet();
	}

	public Set<INotationPluginService> getPluginServices() {
		return Collections.emptySet();
	}

	public SbgnPdNotationSyntaxService getSyntaxService() {
		return this.syntaxService;
	}

	public INotationValidationService getValidationService() {
		return validationService;
	}

	public Set<INotationConversionService> getConversionServices() {
		return Collections.emptySet();
	}

	public INotationAutolayoutService getAutolayoutService() {
		return new DefaultAutolayoutService();
	}


	private class DefaultAutolayoutService implements INotationAutolayoutService {


		public boolean isImplemented() {
			return false;
		}

        public void layout(ICanvas canvas) {
            throw new UnsupportedOperationException("Notation subsystem does not support this operation");
        }

        public INotation getNotation() {
            return SbgnPdNotationSubsystem.this.getNotation();
        }

        public INotationSubsystem getNotationSubsystem() {
            return SbgnPdNotationSubsystem.this;
        }
		
	}

    public boolean isFallback() {
        return false;
    }


	public void registerCanvas(ICanvas canvasToRegister) {
		// Do nothing at the moment
	}


	public void unregisterCanvas(ICanvas canvasToRegister) {
		// Do nothing at the moment
	}
}

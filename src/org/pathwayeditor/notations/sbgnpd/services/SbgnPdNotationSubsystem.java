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
	private static final String GLOBAL_ID = NotationResources.getString("SbgnPdNotationSubsystem.notation_global_id"); //$NON-NLS-1$
	private static final String DESCRIPTION = NotationResources.getString("SbgnPdNotationSubsystem.notation_description"); //$NON-NLS-1$
	private static final String NAME = NotationResources.getString("SbgnPdNotationSubsystem.notation_short_name"); //$NON-NLS-1$
	private static final String MAJOR_REVISION_NUM = NotationResources.getString("SbgnPdNotationSubsystem.notation_major_revision"); //$NON-NLS-1$
	private static final String MINOR_REVISION_NUM = NotationResources.getString("SbgnPdNotationSubsystem.notation_minor_revision"); //$NON-NLS-1$
	private static final String PATCH_VERSION_NUM = NotationResources.getString("SbgnPdNotationSubsystem.notation_patch_revision"); //$NON-NLS-1$
	private static final Version VERSION = new Version(Integer.valueOf(MAJOR_REVISION_NUM),
				Integer.valueOf(MINOR_REVISION_NUM), Integer.valueOf(PATCH_VERSION_NUM));
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
            throw new UnsupportedOperationException("Notation subsystem does not support this operation"); //$NON-NLS-1$
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

/*
  Licensed to the Court of the University of Edinburgh (UofE) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The UofE licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
*/
package org.pathwayeditor.notations.sbgnpd.services;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
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
	private final SbgnPdNotationSyntaxService syntaxService;
	private final SbgnPdValidationService validationService;
	private final List<INotationExportService> exportServices;
	private final INotation context;

	public SbgnPdNotationSubsystem() {
		this.context = new GeneralNotation(GLOBAL_ID, NAME, DESCRIPTION, VERSION);
		this.syntaxService = new SbgnPdNotationSyntaxService(this);
		this.validationService=new SbgnPdValidationService(this);
		this.exportServices = new LinkedList<INotationExportService>();
		this.exportServices.add(new SbgnPdBioPepaExportService(this));
	}
	

	@Override
	public INotation getNotation() {
		return this.context;
	}

	@Override
	public Set<INotationExportService> getExportServices() {
		return new HashSet<INotationExportService>(this.exportServices);
	}

	@Override
	public Set<INotationImportService> getImportServices() {
		return Collections.emptySet();
	}

	@Override
	public Set<INotationPluginService> getPluginServices() {
		return Collections.emptySet();
	}

	@Override
	public SbgnPdNotationSyntaxService getSyntaxService() {
		return this.syntaxService;
	}

	@Override
	public INotationValidationService getValidationService() {
		return validationService;
	}

	@Override
	public Set<INotationConversionService> getConversionServices() {
		return Collections.emptySet();
	}

	@Override
	public INotationAutolayoutService getAutolayoutService() {
		return new DefaultAutolayoutService();
	}


	private class DefaultAutolayoutService implements INotationAutolayoutService {


		@Override
		public boolean isImplemented() {
			return false;
		}

        @Override
		public void layout(IModel canvas) {
            throw new UnsupportedOperationException("Notation subsystem does not support this operation"); //$NON-NLS-1$
        }

        @Override
		public INotation getNotation() {
            return SbgnPdNotationSubsystem.this.getNotation();
        }

        @Override
		public INotationSubsystem getNotationSubsystem() {
            return SbgnPdNotationSubsystem.this;
        }
		
	}

    @Override
	public boolean isFallback() {
        return false;
    }


	@Override
	public void registerModel(IModel canvasToRegister) {
		// Do nothing at the moment
	}


	@Override
	public void unregisterModel(IModel canvasToRegister) {
		// Do nothing at the moment
	}
}

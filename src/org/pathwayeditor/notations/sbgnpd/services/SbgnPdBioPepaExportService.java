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

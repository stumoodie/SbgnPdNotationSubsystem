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
package org.pathwayeditor.notations.sbgnpd.export.sbgntext;

import java.io.File;

import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
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

public class SbgnTextExportController {
	private File exportFile;
	private IModel canvasToExport;
	
	public SbgnTextExportController(){
		
	}

	public File getExportFile() {
		return exportFile;
	}

	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
	}

	public IModel getCanvasToExport() {
		return canvasToExport;
	}

	public void setCanvasToExport(IModel canvasToExport) {
		this.canvasToExport = canvasToExport;
	}
	
	public void exportFile() throws ExportServiceException {
		try {
			IBoParser parser = new BoParser(new NdomBuilder());
			ITreeLexer lexer = new BoTreeLexer(this.canvasToExport);
			parser.parse(lexer);
			IMapDiagram ndom = parser.getNDomBuilder().getNdom();
			IReportLog log = new ReportLog();
			IExportWriter writer = new SbgnTextExportWriter(ndom, this.getExportFile(), log);
			writer.writeExport();
			if(log.hasErrors()){
				throw new ExportServiceException("One or more errors occurred during export to BioPEPA.");
			}
		} catch (TreeParseException e) {
			throw new ExportServiceException("An error occurred generating the NDOM. The map may not be a valid SBGN-PD map.", e);
		}
	}
	
}

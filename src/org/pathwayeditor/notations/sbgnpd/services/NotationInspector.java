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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.Colour;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LineStyle;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.RGB;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IBooleanPropertyDefinition;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IIntegerPropertyDefinition;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.INumberPropertyDefinition;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IPropertyDefinition;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSyntaxService;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.figure.definition.FigureDefinitionCompiler;
import org.pathwayeditor.figure.definition.ICompiledFigureDefinition;
import org.pathwayeditor.figure.geometry.Dimension;
import org.pathwayeditor.figure.geometry.Envelope;
import org.pathwayeditor.figure.geometry.Point;
import org.pathwayeditor.figure.rendering.FigureRenderer;
import org.pathwayeditor.figure.rendering.FigureRenderingController;
import org.pathwayeditor.figure.rendering.GenericFont;
import org.pathwayeditor.figure.rendering.IFont.Style;
import org.pathwayeditor.figure.rendering.GraphicalTextAlignment;
import org.pathwayeditor.figure.rendering.GraphicsInstructionList;
import org.pathwayeditor.figure.rendering.IFigureRenderingController;

public class NotationInspector {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final double X_START = 10.0;
	private static final double Y_START = 10.0;
	private static final double SEP_WIDTH = 10.0;
	private static final double MAX_WIDTH = 400.0;
	private static final double SEP_HEIGHT = 40.0;
//	private static final double SHAPE_WIDTH = 50.0;
//	private static final double SHAPE_HEIGHT = 40.0;
	private static final double MIN_WIDTH = 100.0;
	private final INotationSubsystem notationSubsystem;
	private final PostscriptGraphicsEngine graphicsEngine;
	
	public NotationInspector(INotationSubsystem ns){
		this.notationSubsystem = ns;
		this.graphicsEngine = new PostscriptGraphicsEngine();
	}
	
	public INotationSubsystem getNotationSubsystem(){
		return this.notationSubsystem;
	}
	
	public void writeShapes(File psFile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(psFile));
		this.graphicsEngine.setWriter(writer);
		INotationSyntaxService syntaxService = this.notationSubsystem.getSyntaxService();
		Iterator<IShapeObjectType> iter = syntaxService.shapeTypeIterator();
		double x = X_START;
		double y = Y_START;
		double maxY = 0.0;
		while(iter.hasNext()){
			IShapeObjectType objectType = iter.next();
			logger.debug("Drawing object type: " + objectType.getName());
			String shapeDefn = objectType.getDefaultAttributes().getShapeDefinition();
			FigureDefinitionCompiler compiler = new FigureDefinitionCompiler(shapeDefn);
			compiler.compile();
			ICompiledFigureDefinition defn = compiler.getCompiledFigureDefinition();
			IFigureRenderingController controller = new FigureRenderingController(defn);
			Set<IPropertyDefinition> boundProps = getBoundProperties(defn, objectType);
			assignedBoundValues(controller, boundProps);
			Dimension shapeSize = objectType.getDefaultAttributes().getSize();
			controller.setRequestedEnvelope(new Envelope(new Point(x, y), shapeSize));
			controller.setFillColour(Colour.GREEN);
			controller.generateFigureDefinition();
			shapeSize = controller.getRequestedEnvelope().getDimension();
			GraphicsInstructionList graphicsInst = controller.getFigureDefinition();
			drawGlyph(graphicsInst);
			Envelope env = controller.getRequestedEnvelope();
			this.graphicsEngine.setLineWidth(1.0);
			this.graphicsEngine.setLineStyle(LineStyle.DOT);
			this.graphicsEngine.setLineColor(Colour.RED);
			this.graphicsEngine.drawRectangle(env.getOrigin().getX(), env.getOrigin().getY(),
					env.getDimension().getWidth(), env.getDimension().getHeight());
			writeTypeName(controller.getEnvelope(), objectType);
			x += Math.max(MIN_WIDTH, shapeSize.getWidth()) + SEP_WIDTH;
			maxY = Math.max(maxY, shapeSize.getHeight());
			if(x > MAX_WIDTH){
				x = X_START;
				y += maxY + SEP_HEIGHT;
				maxY = 0.0;
			}
		}
		this.graphicsEngine.end();
		writer.close();
	}
	
	private void writeTypeName(Envelope glyphBox, IShapeObjectType objectType) {
		String name = objectType.getName();
		Point textLocation = glyphBox.translate(new Point(0.0, -20.0)).getOrigin();
		this.graphicsEngine.setFont(new GenericFont(10.0, EnumSet.of(Style.ITALIC)));
		this.graphicsEngine.drawString(name, textLocation.getX(), textLocation.getY(), GraphicalTextAlignment.E);
	}

	private void drawGlyph(GraphicsInstructionList graphicsList){
		FigureRenderer drawer = new FigureRenderer(graphicsList);
		drawer.drawFigure(this.graphicsEngine);
	}
	
	private void assignedBoundValues(IFigureRenderingController controller, Set<IPropertyDefinition> boundProps) {
		for(IPropertyDefinition propDefn : boundProps){
			if(propDefn instanceof IIntegerPropertyDefinition){
				controller.setBindInteger(propDefn.getName(), ((IIntegerPropertyDefinition)propDefn).getDefaultValue());
			}
			else if(propDefn instanceof IIntegerPropertyDefinition){
				controller.setBindInteger(propDefn.getName(), ((IIntegerPropertyDefinition)propDefn).getDefaultValue());
			}
			else if(propDefn instanceof IBooleanPropertyDefinition){
				controller.setBindBoolean(propDefn.getName(), ((IBooleanPropertyDefinition)propDefn).getDefaultValue());
//				controller.setBindBoolean(propDefn.getName(), Boolean.TRUE);
			}
			else if(propDefn instanceof INumberPropertyDefinition){
				controller.setBindDouble(propDefn.getName(), ((INumberPropertyDefinition)propDefn).getDefaultValue().doubleValue());
			}
			else{
				controller.setBindString(propDefn.getName(),propDefn.getDefaultValue().toString());
			}
		}
	}

	private Set<IPropertyDefinition> getBoundProperties(ICompiledFigureDefinition defn, IShapeObjectType objectType){
		Set<IPropertyDefinition> boundProperties = new HashSet<IPropertyDefinition>();
		Set<String> bindVarNames = defn.getBindVariableNames();
		for(String varName : bindVarNames){
			if(objectType.getDefaultAttributes().containsPropertyDefinition(varName)){
				IPropertyDefinition propDefn = objectType.getDefaultAttributes().getPropertyDefinition(varName);
				boundProperties.add(propDefn);
				
			}
			else{
				throw new RuntimeException("No property found for this bind variable: " + varName);
			}
		}
		return boundProperties;
	}
	
	public static final void main(String argv[]){
		String psFileName = argv[0];
		NotationInspector insp = new NotationInspector(new SbgnPdNotationSubsystem());
		try {
			File shapeFile = new File(psFileName);
			insp.writeShapes(shapeFile);
		} catch (IOException e) {
			System.err.println("Error occurred: " + e.getMessage());
			System.exit(1);
		}
		System.exit(0);
	}
}

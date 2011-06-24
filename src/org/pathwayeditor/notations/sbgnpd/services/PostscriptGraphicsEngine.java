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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LineStyle;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.Colour;
import org.pathwayeditor.figure.rendering.GraphicalTextAlignment;
import org.pathwayeditor.figure.rendering.IFont;
import org.pathwayeditor.figure.rendering.IFont.Style;
import org.pathwayeditor.figure.rendering.IGraphicsEngine;

public class PostscriptGraphicsEngine implements IGraphicsEngine {
	private static final String PS_TEMPLATE_FILE_NAME = "postscript.stg";
	private static final String SOLID_LINE_STYLE = "S";
	private static final String DASH_DOT_LINESTYLE = "DT";
	private static final String DASH_LINESTYLE = "D";
	private static final String DASH_DOT_DOT_LINESTYLE = "DTT";
	private static final String DOT_LINESTYLE = "T";
	private BufferedWriter writer;
	private final StringTemplateGroup stg;
	private Colour fillColour = Colour.WHITE;
	private Colour lineColour = Colour.BLACK;
	private Colour fontColour = Colour.BLACK;
	private final Map<Style, String> fontStyleMapping;

	public PostscriptGraphicsEngine(){
		Reader r = new InputStreamReader(this.getClass().getResourceAsStream(PS_TEMPLATE_FILE_NAME));
		stg = new StringTemplateGroup(r);
		this.fontStyleMapping = new HashMap<Style, String>();
		this.fontStyleMapping.put(Style.BOLD, "B");
		this.fontStyleMapping.put(Style.ITALIC, "I");
		this.fontStyleMapping.put(Style.NORMAL, "N");
	}
	
	private void writeColour(Colour col){
		try {
			StringTemplate t = stg.getInstanceOf("setColour");
			t.setAttribute("red", col.getRgb().getRed());
			t.setAttribute("green", col.getRgb().getGreen());
			t.setAttribute("blue", col.getRgb().getBlue());
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void writeLineColour(){
		if(this.lineColour != null){
			writeColour(this.lineColour);
		}
	}
	
	private void writeFillColour(){
		if(this.fillColour != null){
			writeColour(this.fillColour);
		}
	}
	
	@Override
	public void drawArc(double pos, double pos2, double widthSize,
			double heightSize, double roundedOffset, double roundedLength) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawArc");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("w", widthSize);
				t.setAttribute("h", heightSize);
				t.setAttribute("startAng", roundedOffset);
				t.setAttribute("lenAng", roundedLength);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void drawLine(double beginPos, double beginPos2, double endPos, double endPos2) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawLine");
				t.setAttribute("x1", beginPos);
				t.setAttribute("y1", beginPos2);
				t.setAttribute("x2", endPos);
				t.setAttribute("y2", endPos2);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void drawOval(double pos, double pos2, double widthSize,	double heightSize) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawOval");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("w", widthSize);
				t.setAttribute("h", heightSize);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void drawPoint(double pos, double pos2) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawPoint");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawPolygon(double[] pointArr) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawPolygon");
				List<Double> points = new ArrayList<Double>(pointArr.length);
				for(double point : pointArr){
					points.add(point);
				}
				t.setAttribute("points", points);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawPolyline(double[] pointArr) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawPolyline");
				List<Double> points = new ArrayList<Double>(pointArr.length);
				for(double point : pointArr){
					points.add(point);
				}
				t.setAttribute("points", points);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawRectangle(double pos, double pos2, double widthSize, double heightSize) {
		try {
			if(this.lineColour.getAlpha() > 0){
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawRect");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("w", widthSize);
				t.setAttribute("h", heightSize);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawRoundRectangle(double x, double y, double width,
			double height, double arcWidthSize, double arcHeightSize) {
		try {
			if (this.lineColour.getAlpha() > 0) {
				writeLineColour();
				StringTemplate t = stg.getInstanceOf("drawRRect");
				t.setAttribute("x", x);
				t.setAttribute("y", y);
				t.setAttribute("w", width);
				t.setAttribute("h", height);
				t.setAttribute("cornerWidth", arcWidthSize);
				t.setAttribute("cornerHeight", arcHeightSize);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawString(String text, double pos, double pos2, GraphicalTextAlignment alignment) {
		try {
			if (this.fontColour.getAlpha() > 0) {
				writeFontColour();
				StringTemplate t = stg.getInstanceOf("drawString");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("alignment", alignment.toString());
				Pattern pat = Pattern.compile("\\\\");
				Matcher mat = pat.matcher(text);
				String processedText = mat.replaceAll("\\\\\\\\");
				t.setAttribute("text", processedText);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeFontColour() {
		if(this.fontColour != null){
			writeColour(this.fontColour);
		}
	}

	public void fillArc(double pos, double pos2, double widthSize,
			double heightSize, double roundedOffset, double roundedLength) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillArc");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("w", widthSize);
				t.setAttribute("h", heightSize);
				t.setAttribute("startAng", roundedOffset);
				t.setAttribute("lenAng", roundedLength);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillOval(double pos, double pos2, double widthSize,	double heightSize) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillOval");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("w", widthSize);
				t.setAttribute("h", heightSize);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillPolygon(double[] pointArr) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillPolygon");
				List<Double> points = new ArrayList<Double>(pointArr.length);
				for (double point : pointArr) {
					points.add(point);
				}
				t.setAttribute("points", points);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillRectangle(double x, double y, double width,	double height) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillRect");
				t.setAttribute("x", x);
				t.setAttribute("y", y);
				t.setAttribute("w", width);
				t.setAttribute("h", height);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillRoundRectangle(double x, double y, double width,
			double height, double arcWidthSize, double arcHeightSize) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillRRect");
				t.setAttribute("x", x);
				t.setAttribute("y", y);
				t.setAttribute("w", width);
				t.setAttribute("h", height);
				t.setAttribute("cornerWidth", arcWidthSize);
				t.setAttribute("cornerHeight", arcHeightSize);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillString(String text, double pos, double pos2, GraphicalTextAlignment alignment) {
		try {
			if (this.fillColour.getAlpha() > 0) {
				writeFillColour();
				StringTemplate t = stg.getInstanceOf("fillString");
				t.setAttribute("x", pos);
				t.setAttribute("y", pos2);
				t.setAttribute("alignment", alignment.toString());
				t.setAttribute("text", text);
				this.writer.append(t.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setFillColor(Colour color) {
		this.fillColour = color;
	}

	
	
	public void setFont(IFont modifiedFont) {
		try {
			StringTemplate t = stg.getInstanceOf("setFont");
			StringBuilder styles = new StringBuilder();
			for(IFont.Style style : modifiedFont.getStyle()){
				styles.append(this.fontStyleMapping.get(style));
			}
			t.setAttribute("styles", styles.toString());
			t.setAttribute("size", modifiedFont.getFontSize());
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setLineColor(Colour color) {
		this.lineColour = color;
	}

	public void setLineStyle(LineStyle ls) {
		try {
			String lsStr = getLineStyleMapping(ls);
			StringTemplate t = stg.getInstanceOf("setLineStyle");
			t.setAttribute("ls", lsStr);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getLineStyleMapping(LineStyle ls) {
		String retVal = SOLID_LINE_STYLE;
		switch(ls){
			case DASH_DOT:
				retVal = DASH_DOT_LINESTYLE;
				break;
			case DASH_DOT_DOT:
				retVal = DASH_DOT_DOT_LINESTYLE;
				break;
			case DOT:
				retVal = DOT_LINESTYLE;
				break;
			case DASHED:
				retVal = DASH_LINESTYLE;
				break;
		}
		return retVal;
	}

	public void setLineWidth(double lineWidthVal) {
		try {
			StringTemplate t = stg.getInstanceOf("setLineWidth");
			t.setAttribute("lw", lineWidthVal);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setWriter(BufferedWriter writer) throws IOException {
		this.writer = writer;
		initialise();
	}

	private void initialise() throws IOException{
		StringTemplate t = stg.getInstanceOf("header");
		this.writer.append(t.toString());
	}
	
	
	public void end() throws IOException{
		StringTemplate t = stg.getInstanceOf("footer");
		this.writer.append(t.toString());
		this.writer = null;
	}

	@Override
	public void setFontColor(Colour color) {
		this.fontColour = color;
	}
}

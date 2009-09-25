package org.pathwayeditor.notations.sbgnpd.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LineStyle;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.RGB;
import org.pathwayeditor.figure.figuredefn.IFont;
import org.pathwayeditor.figure.figuredefn.IGraphicsEngine;
import org.pathwayeditor.figure.figuredefn.GraphicsInstruction.GraphicalTextAlignment;
import org.pathwayeditor.figure.figuredefn.IFont.Style;

public class PostscriptGraphicsEngine implements IGraphicsEngine {
	private static final String PS_TEMPLATE_FILE_NAME = "postscript.stg";
	private static final String SOLID_LINE_STYLE = "S";
	private static final String DASH_DOT_LINESTYLE = "DT";
	private static final String DASH_LINESTYLE = "D";
	private static final String DASH_DOT_DOT_LINESTYLE = "DTT";
	private static final String DOT_LINESTYLE = "T";
	private BufferedWriter writer;
	private final StringTemplateGroup stg;
	private RGB fillColour;
	private RGB lineColour;

	public PostscriptGraphicsEngine(){
		Reader r = new InputStreamReader(this.getClass().getResourceAsStream(PS_TEMPLATE_FILE_NAME));
		stg = new StringTemplateGroup(r);
	}
	
	private void writeColour(RGB col){
		try {
			StringTemplate t = stg.getInstanceOf("setColour");
			t.setAttribute("red", col.getRed());
			t.setAttribute("green", col.getGreen());
			t.setAttribute("blue", col.getBlue());
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
	
	public void drawArc(double pos, double pos2, double widthSize,
			double heightSize, double roundedOffset, double roundedLength) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawArc");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("w", widthSize);
			t.setAttribute("h", heightSize);
			t.setAttribute("startAng", roundedOffset);
			t.setAttribute("lenAng", roundedLength);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawLine(double beginPos, double beginPos2, double endPos, double endPos2) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawLine");
			t.setAttribute("x1", beginPos);
			t.setAttribute("y1", beginPos2);
			t.setAttribute("x2", endPos);
			t.setAttribute("y2", endPos2);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawOval(double pos, double pos2, double widthSize,	double heightSize) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawOval");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("w", widthSize);
			t.setAttribute("h", heightSize);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawPoint(double pos, double pos2) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawPoint");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawPolygon(double[] pointArr) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawPolygon");
			List<Double> points = new ArrayList<Double>(pointArr.length);
			for(double point : pointArr){
				points.add(point);
			}
			t.setAttribute("points", points);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawPolyline(double[] pointArr) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawPolyline");
			List<Double> points = new ArrayList<Double>(pointArr.length);
			for(double point : pointArr){
				points.add(point);
			}
			t.setAttribute("points", points);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawRectangle(double pos, double pos2, double widthSize, double heightSize) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawRect");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("w", widthSize);
			t.setAttribute("h", heightSize);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawRoundRectangle(double x, double y, double width,
			double height, double arcWidthSize, double arcHeightSize) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawRRect");
			t.setAttribute("x", x);
			t.setAttribute("y", y);
			t.setAttribute("w", width);
			t.setAttribute("h", height);
			t.setAttribute("cornerWidth", arcWidthSize);
			t.setAttribute("cornerHeight", arcHeightSize);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void drawString(String text, double pos, double pos2, GraphicalTextAlignment alignment) {
		try {
			writeLineColour();
			StringTemplate t = stg.getInstanceOf("drawString");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("alignment", alignment.toString());
			t.setAttribute("text", text);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillArc(double pos, double pos2, double widthSize,
			double heightSize, double roundedOffset, double roundedLength) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillArc");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("w", widthSize);
			t.setAttribute("h", heightSize);
			t.setAttribute("startAng", roundedOffset);
			t.setAttribute("lenAng", roundedLength);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillOval(double pos, double pos2, double widthSize,	double heightSize) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillOval");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("w", widthSize);
			t.setAttribute("h", heightSize);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillPolygon(double[] pointArr) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillPolygon");
			List<Double> points = new ArrayList<Double>(pointArr.length);
			for(double point : pointArr){
				points.add(point);
			}
			t.setAttribute("points", points);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillRectangle(double x, double y, double width,	double height) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillRect");
			t.setAttribute("x", x);
			t.setAttribute("y", y);
			t.setAttribute("w", width);
			t.setAttribute("h", height);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillRoundRectangle(double x, double y, double width,
			double height, double arcWidthSize, double arcHeightSize) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillRRect");
			t.setAttribute("x", x);
			t.setAttribute("y", y);
			t.setAttribute("w", width);
			t.setAttribute("h", height);
			t.setAttribute("cornerWidth", arcWidthSize);
			t.setAttribute("cornerHeight", arcHeightSize);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void fillString(String text, double pos, double pos2, GraphicalTextAlignment alignment) {
		try {
			writeFillColour();
			StringTemplate t = stg.getInstanceOf("fillString");
			t.setAttribute("x", pos);
			t.setAttribute("y", pos2);
			t.setAttribute("alignment", alignment.toString());
			t.setAttribute("text", text);
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setFillColor(RGB color) {
		this.fillColour = color;
	}

	public void setFont(IFont modifiedFont) {
		try {
			StringTemplate t = stg.getInstanceOf("setFont");
			StringBuilder styles = new StringBuilder();
			for(Style style : modifiedFont.getStyle()){
				styles.append(style.toString());
			}
			t.setAttribute("styles", styles.toString());
			t.setAttribute("size", modifiedFont.getFontSize());
			this.writer.append(t.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setLineColor(RGB color) {
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
}

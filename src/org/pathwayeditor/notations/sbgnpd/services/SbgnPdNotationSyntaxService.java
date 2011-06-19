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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LabelLocationPolicy;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LineStyle;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LinkEndDecoratorShape;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.Colour;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.INumberPropertyDefinition;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IPlainTextPropertyDefinition;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IPropertyDefinition;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSyntaxService;
import org.pathwayeditor.businessobjects.typedefn.ILabelObjectType;
import org.pathwayeditor.businessobjects.typedefn.ILinkObjectType;
import org.pathwayeditor.businessobjects.typedefn.ILinkObjectType.LinkEditableAttributes;
import org.pathwayeditor.businessobjects.typedefn.ILinkTerminusDefinition.LinkTermEditableAttributes;
import org.pathwayeditor.businessobjects.typedefn.IObjectType;
import org.pathwayeditor.businessobjects.typedefn.IRootObjectType;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType.EditableShapeAttributes;
import org.pathwayeditor.figure.geometry.Dimension;
import org.pathwayeditor.notationsubsystem.toolkit.definition.BooleanPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.IntegerPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LabelObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkTerminusDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.NumberPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.PlainTextPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.RootObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.ShapeObjectType;

public class SbgnPdNotationSyntaxService implements INotationSyntaxService {
	private static final int NUM_ROOT_OTS = 1;
	private static final String UNIT_OF_INFO_DEFN = "curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "x y w h rect";
	private static final String STATE_DEFN = "curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "h w le  {x y w h 1 h mul 1 h mul rrect}\n"
		+ "{x y w h 1 w mul 1 w mul rrect} ifelse";
//	private static final String STATE_DEFN = "curbounds /h exch def /w exch def /y exch def /x exch def\n"
//		+ "x y w h oval";
	private static final String SIMPLE_CHEM_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n" +
		"/xoffset { w mul x add } def /yoffset { h mul y add } def\n" +
		"/cardinalityBox { /card exch def /fsize exch def /cpy exch def /cpx exch def\n" +
		"fsize setfontsize\n" +
		"card cvs textbounds /hoff exch curlinewidth 2 mul add h div def /woff exch curlinewidth 2 mul add w div def \n" +
		"cpx woff 2 div sub xoffset cpy hoff 2 div sub yoffset woff w mul hoff h mul rect\n" +
		"gsave\n" +
		"null setfillcol cpx xoffset cpy yoffset (C) card cvs text\n" +
		"grestore\n" +
		"} def\n" +
		":cardinality 1 gt {\n" +
		"0.10 xoffset 0.10 yoffset 0.90 w mul 0.90 h mul oval\n" +
		"0 xoffset 0 yoffset 0.90 w mul 0.90 h mul oval\n" +
		"0.25 0.05 :cardFontSize :cardinality cardinalityBox\n" +
		"}\n" +
		"{ x y w h oval } ifelse";
	private static final String UNSPECIFIED_ENTITY_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "x y w h oval";
	private static final String COMPARTMENT_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "gsave null setlinecol\n"
		+ "x 0.10 yoffset w 0.81 h mul rect\n"
		+ "0.4 xoffset y 0.22 w mul 0.10 h mul rect\n"
		+ " 0.4 xoffset 0.90 yoffset 0.22 w mul 0.10 h mul rect\n"
		+ "grestore\n"
		+ "x 0.90 yoffset x 0.10 yoffset line 0.40 xoffset 0 yoffset 0.60 xoffset 0 yoffset line\n"
		+ "1.00 xoffset 0.10 yoffset 1.00 xoffset 0.90 yoffset line\n"
		+ "0.60 xoffset 1.00 yoffset 0.40 xoffset 1.00 yoffset line\n"
		+ "0 xoffset 0 yoffset 0.80 w mul 0.20 h mul 90 90 arc\n"
		+ "0.20 xoffset 0 yoffset 0.80 w mul 0.20 h mul 0 90 arc\n"
		+ "0 xoffset 0.80 yoffset 0.80 w mul 0.20 h mul 180 90 arc\n"
		+ "0.20 xoffset 0.80 yoffset 0.80 w mul 0.20 h mul 270 90 arc";
	private static final String COMPLEX_DEFN = 
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n" + 
		":cardinality 1 gt {\n" +
		"/cardinalityBox { /card exch def /fsize exch def /cpy exch def /cpx exch def\n" +
		"fsize setfontsize\n" +
		"card cvs textbounds /hoff exch curlinewidth 2 mul add h div def /woff exch curlinewidth 2 mul add w div def \n" +
		"cpx woff 2 div sub xoffset cpy hoff 2 div sub yoffset woff w mul hoff h mul rect\n" +
		"gsave\n" +
		"null setfillcol cpx xoffset cpy yoffset (C) card cvs text\n" +
		"grestore\n" +
		"} def\n" +
		"/xoffset { w 0.9 mul mul x 0.1 w mul add add } def /yoffset { h 0.9 mul mul y 0.1 h mul add add } def\n" +
		"[0 xoffset 0.15 yoffset 0.15 xoffset 0 yoffset 0.85 xoffset 0 yoffset 1.00 xoffset 0.15 yoffset 1.00 xoffset 0.85 yoffset 0.85 xoffset 1.00 yoffset 0.15 xoffset 1.00 yoffset 0 xoffset 0.85 yoffset] pgon\n" +
		"/xoffset { w 0.9 mul mul x add } def /yoffset { h 0.9 mul mul y add } def\n" +
		"[0 xoffset 0.15 yoffset 0.15 xoffset 0 yoffset 0.85 xoffset 0 yoffset 1.00 xoffset 0.15 yoffset 1.00 xoffset 0.85 yoffset 0.85 xoffset 1.00 yoffset 0.15 xoffset 1.00 yoffset 0 xoffset 0.85 yoffset] pgon\n" +
		"0.3 0 :cardFontSize :cardinality cardinalityBox\n" +
		"}\n" +
		"{\n" +
		"/xoffset { w mul x add } def /yoffset { h mul y add } def\n" +
		"[0 xoffset 0.15 yoffset 0.15 xoffset 0 yoffset 0.85 xoffset 0 yoffset 1.00 xoffset 0.15 yoffset 1.00 xoffset 0.85 yoffset 0.85 xoffset 1.00 yoffset 0.15 xoffset 1.00 yoffset 0 xoffset 0.85 yoffset] pgon\n" +
		"} ifelse";
//
//	"(C) setanchor\n" +
//	"curbounds /h exch def /w exch def /y exch def /x exch def\n" +
//	"/xoffset { w mul x add } def /yoffset { h mul y add } def\n" +
//	":cardinality 1.0 gt {\n" +
//	"0.10 xoffset 0.10 yoffset 0.90 w mul 0.90 h mul 0.20 w mul 0.20 h mul rrect\n" +
//	"0 xoffset 0 yoffset 0.90 w mul 0.90 h mul 0.20 w mul 0.20 h mul rrect\n" +
//	"{ x y w h 0.2 w mul 0.20 h mul rrect } ifelse";
	private static final String NUCLEIC_ACID_FEATURE_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "gsave null setlinecol x y w 0.82 h mul rect\n"
			+ "0.20 xoffset 0.80 yoffset 0.62 w mul 0.20 h mul rect\n"
			+ "0 xoffset 0.60 yoffset 0.40 w mul 0.40 h mul 180 90 arc\n"
			+ "0.60 xoffset 0.60 yoffset 0.40 w mul 0.40 h mul 270 90 arc\n" 
			+ "grestore\n"
			+ "[0 xoffset 0.80 yoffset 0 xoffset 0 yoffset 1.00 xoffset 0 yoffset 1.00 xoffset 0.80 yoffset ] pline\n"
			+ "0.20 xoffset 1.00 yoffset 0.80 xoffset 1.00 yoffset line\n"
			+ "0 xoffset 0.60 yoffset 0.40 w mul 0.40 h mul 180 90 arc\n"
			+ "0.60 xoffset 0.60 yoffset 0.40 w mul 0.40 h mul 270 90 arc";
	private static final String EMPTY_SET_DEFN = 
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "0.05 xoffset 0.05 yoffset 0.90 w mul 0.90 h mul oval 1.00 xoffset 0 yoffset 0 xoffset 1.00 yoffset line";
	private static final String PHENOTYPE_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "[0.25 xoffset 0 yoffset 0.75 xoffset 0 yoffset 1.00 xoffset 0.50 yoffset 0.75 xoffset 1.00 yoffset 0.25 xoffset 1.00 yoffset 0 xoffset 0.50 yoffset] pgon";
	private static final String PERTURBATION_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "[0 xoffset 0 yoffset 1.00 xoffset 0 yoffset 0.70 xoffset 0.50 yoffset 1.00 xoffset 1.00 yoffset 0 xoffset 1.00 yoffset 0.30 xoffset 0.50 yoffset] pgon";
	private static final String PROCESS_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "/lugLen 0.15 def /firstBoxOffset lugLen def /sndBoxOffset 1.0 lugLen sub def\n"
		+ ":vertFlag\n"
		+ "{0.50 xoffset 0 yoffset 0.50 xoffset firstBoxOffset yoffset line\n"
		+ "0.50 xoffset sndBoxOffset yoffset 0.50 xoffset 1.00 yoffset line\n"
		+ "0.0 xoffset firstBoxOffset yoffset w 1.0 lugLen 2.0 mul sub h mul rect\n"
		+ "}\n"
		+ "{0 xoffset 0.50 yoffset firstBoxOffset xoffset 0.50 yoffset line\n"
		+ "sndBoxOffset xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
		+ "firstBoxOffset xoffset 0.0 yoffset 1.0 lugLen 2.0 mul sub w mul h rect\n"
		+ "}\n"
		+ "ifelse\n"
		+ "[0.0 xoffset 0.5 yoffset 1.0 xoffset 0.5 yoffset 0.5 xoffset 0.0 yoffset 0.5 xoffset 1.0 yoffset] (S) setanchor}\n"
		;
	private static final String OMITTED_PROCESS_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "/lugLen 0.15 def /firstBoxOffset lugLen def /sndBoxOffset 1.0 lugLen sub def\n"
		+ ":vertFlag\n"
		+ "{0.50 xoffset 0 yoffset 0.50 xoffset firstBoxOffset yoffset line\n"
		+ "0.50 xoffset sndBoxOffset yoffset 0.50 xoffset 1.00 yoffset line\n"
		+ "0.0 xoffset firstBoxOffset yoffset w 1.0 lugLen 2.0 mul sub h mul rect\n"
		+ "}\n"
		+ "{0 xoffset 0.50 yoffset firstBoxOffset xoffset 0.50 yoffset line\n"
		+ "sndBoxOffset xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
		+ "firstBoxOffset xoffset 0.0 yoffset 1.0 lugLen 2.0 mul sub w mul h rect\n"
		+ "}\n"
		+ "ifelse\n"
		+ "0.5 h mul setfontsize 0.5 xoffset 0.5 yoffset (C) (\\\\) text\n"
		+ "[0.0 xoffset 0.5 yoffset 1.0 xoffset 0.5 yoffset 0.5 xoffset 0.0 yoffset 0.5 xoffset 1.0 yoffset] (S) setanchor}\n"
		;
	private static final String UNCERTAIN_PROCESS_DEFN =
			"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "/lugLen 0.15 def /firstBoxOffset lugLen def /sndBoxOffset 1.0 lugLen sub def\n"
			+ ":vertFlag\n"
			+ "{0.50 xoffset 0 yoffset 0.50 xoffset firstBoxOffset yoffset line\n"
			+ "0.50 xoffset sndBoxOffset yoffset 0.50 xoffset 1.00 yoffset line\n"
			+ "0.0 xoffset firstBoxOffset yoffset w 1.0 lugLen 2.0 mul sub h mul rect\n"
			+ "}\n"
			+ "{0 xoffset 0.50 yoffset firstBoxOffset xoffset 0.50 yoffset line\n"
			+ "sndBoxOffset xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
			+ "firstBoxOffset xoffset 0.0 yoffset 1.0 lugLen 2.0 mul sub w mul h rect\n"
			+ "}\n"
			+ "ifelse\n"
			+ "0.5 h mul setfontsize 0.5 xoffset 0.5 yoffset (C) (?) text\n"
			+ "[0.0 xoffset 0.5 yoffset 1.0 xoffset 0.5 yoffset 0.5 xoffset 0.0 yoffset 0.5 xoffset 1.0 yoffset] (S) setanchor}\n"
			;
	private static final String ASSOC_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "curlinecol setfillcol\n"
		+ "/lugLen 0.15 def /firstBoxOffset lugLen def /sndBoxOffset 1.0 lugLen sub def\n"
		+ ":vertFlag\n"
		+ "{0.50 xoffset 0 yoffset 0.50 xoffset firstBoxOffset yoffset line\n"
		+ "0.50 xoffset sndBoxOffset yoffset 0.50 xoffset 1.00 yoffset line\n"
		+ "0.0 xoffset firstBoxOffset yoffset w 1.0 lugLen 2.0 mul sub h mul oval\n"
		+ "}\n"
		+ "{0 xoffset 0.50 yoffset firstBoxOffset xoffset 0.50 yoffset line\n"
		+ "sndBoxOffset xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
		+ "firstBoxOffset xoffset 0.0 yoffset 1.0 lugLen 2.0 mul sub w mul h oval\n"
		+ "}\n"
		+ "ifelse\n"
		+ "[0.0 xoffset 0.5 yoffset 1.0 xoffset 0.5 yoffset 0.5 xoffset 0.0 yoffset 0.5 xoffset 1.0 yoffset] (S) setanchor}\n"
		;
	private static final String DISSOC_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "/lugLen 0.15 def /firstBoxOffset lugLen def /sndBoxOffset 1.0 lugLen sub def\n"
		+ "/diampc 1.0 lugLen 2.0 mul sub def"
		+ ":vertFlag\n"
		+ "{0.50 xoffset 0 yoffset 0.50 xoffset firstBoxOffset yoffset line\n"
		+ "0.50 xoffset sndBoxOffset yoffset 0.50 xoffset 1.00 yoffset line\n"
		+ "0.0 xoffset firstBoxOffset yoffset w diampc h mul oval\n"
		+ "/indim 0.6 def\n"
		+ "/inoffset 0.2 def\n"
		+ "inoffset xoffset firstBoxOffset inoffset diampc mul add yoffset indim w mul indim diampc mul h mul oval\n"
		+ "}\n"
		+ "{0 xoffset 0.50 yoffset firstBoxOffset xoffset 0.50 yoffset line\n"
		+ "sndBoxOffset xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
		+ "firstBoxOffset xoffset 0.0 yoffset 1.0 lugLen 2.0 mul sub w mul h oval\n"
		+ "/indim 0.6 def\n"
		+ "/inoffset 0.2 def\n"
		+ "firstBoxOffset inoffset diampc mul add xoffset inoffset yoffset indim diampc mul w mul indim h mul oval\n"
		+ "}\n"
		+ "ifelse\n"
		+ "[0.0 xoffset 0.5 yoffset 1.0 xoffset 0.5 yoffset 0.5 xoffset 0.0 yoffset 0.5 xoffset 1.0 yoffset] (S) setanchor}\n"
		;
	private static final String MACROMOLECULE_DEFN =
		"(C) setanchor\n" +
		"curbounds /h exch def /w exch def /y exch def /x exch def\n" +
		"/xoffset { w mul x add } def /yoffset { h mul y add } def\n" +
		"/cardinalityBox { /card exch def /fsize exch def /cpy exch def /cpx exch def\n" +
		"fsize setfontsize\n" +
		"card cvs textbounds /hoff exch curlinewidth 2 mul add h div def /woff exch curlinewidth 2 mul add w div def \n" +
		"cpx woff 2 div sub xoffset cpy hoff 2 div sub yoffset woff w mul hoff h mul rect\n" +
		"gsave\n" +
		"null setfillcol cpx xoffset cpy yoffset (C) card cvs text\n" +
		"grestore\n" +
		"} def\n" +
		":cardinality 1 gt {\n" +
		"0.10 xoffset 0.10 yoffset 0.90 w mul 0.90 h mul 0.20 w mul 0.20 h mul rrect\n" +
		"0 xoffset 0 yoffset 0.90 w mul 0.90 h mul 0.20 w mul 0.20 h mul rrect\n" +
		"0.3 0 :cardFontSize :cardinality cardinalityBox\n" +
		"}\n" +
		"{ x y w h 0.2 w mul 0.20 h mul rrect } ifelse";
	private static final String AND_SHAPE_DEFN =
			"curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "x y w h oval h 0.35 mul setfontsize null setfillcol 0.5 xoffset 0.5 yoffset (C) (AND) text\n"
			+ ":vertFlag\n"
			+ "{0.50 xoffset 0 yoffset 0.50 xoffset -0.20 yoffset line\n"
			+ "0.50 xoffset 1.20 yoffset 0.50 xoffset 1.00 yoffset line\n"
			+ "[0.5 xoffset -0.2 yoffset 0.5 xoffset 1.2 yoffset] (S) setanchor}\n"
			+ "{0 xoffset 0.50 yoffset -0.20 xoffset 0.50 yoffset line\n"
			+ "1.20 xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
			+ "[-0.2 xoffset 0.5 yoffset 1.2 xoffset 0.5 yoffset] (S) setanchor}\n"
			+  "ifelse\n";
	private static final String NOT_SHAPE_DEFN =
		"curbounds /h exch def /w exch def /y exch def /x exch def\n"
		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
		+ "x y w h oval h 0.35 mul setfontsize null setfillcol 0.5 xoffset 0.5 yoffset (C) (NOT) text\n"
		+ ":vertFlag\n"
		+ "{0.50 xoffset 0 yoffset 0.50 xoffset -0.20 yoffset line\n"
		+ "0.50 xoffset 1.20 yoffset 0.50 xoffset 1.00 yoffset line\n"
		+ "[0.5 xoffset -0.2 yoffset 0.5 xoffset 1.2 yoffset] (S) setanchor}\n"
		+ "{0 xoffset 0.50 yoffset -0.20 xoffset 0.50 yoffset line\n"
		+ "1.20 xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
		+ "[-0.2 xoffset 0.5 yoffset 1.2 xoffset 0.5 yoffset] (S) setanchor}\n"
		+  "ifelse\n";
	private static final String OR_SHAPE_DEFN = "curbounds /h exch def /w exch def /y exch def /x exch def\n"
			+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
			+ "x y w h oval h 0.35 mul setfontsize null setfillcol 0.5 xoffset 0.5 yoffset (C) (OR) text\n"
			+ ":vertFlag\n"
			+ "{0.50 xoffset 0 yoffset 0.50 xoffset -0.20 yoffset line\n"
			+ "0.50 xoffset 1.20 yoffset 0.50 xoffset 1.00 yoffset line\n"
			+ "[0.5 xoffset -0.2 yoffset 0.5 xoffset 1.2 yoffset] (S) setanchor}\n"
			+ "{0 xoffset 0.50 yoffset -0.20 xoffset 0.50 yoffset line\n"
			+ "1.20 xoffset 0.50 yoffset 1.00 xoffset 0.50 yoffset line\n"
			+ "[-0.2 xoffset 0.5 yoffset 1.2 xoffset 0.5 yoffset] (S) setanchor}\n"
			+  "ifelse\n";
	private static final int NAME_LABEL_UID = 1001;
	private static final int STOICH_LABEL_UID = 1002;
	private static final int STATE_LABEL_UID = 1003;
	private static final int UNIT_INFO_LABEL_UID = 1004;
	
//	private static final String OR_SHAPE_V_DEFN = "curbounds /h exch def /w exch def /y exch def /x exch def\n"
//		+ "/xoffset { w mul x add } def /yoffset { h mul y add } def\n"
//		+ "x y w h oval h 0.35 mul setfontsize null setfillcol 0.5 xoffset 0.5 yoffset (C) (OR) text\n" 
//		+ "0.50 xoffset 0 yoffset 0.50 xoffset -0.20 yoffset line\n"
//		+ "0.50 xoffset 1.20 yoffset 0.50 xoffset 1.00 yoffset line\n" +
//		"[0.5 xoffset -0.2 yoffset 0.5 xoffset 1.2 yoffset ] (S) setanchor\n";
	private final INotation context;
	private final Map<Integer, IShapeObjectType> shapes = new HashMap<Integer, IShapeObjectType>();
	private final Map<Integer, ILinkObjectType> links = new HashMap<Integer, ILinkObjectType>();

	private RootObjectType rmo;
	// shapes
	private final ShapeObjectType State;
	private final ShapeObjectType UnitOfInf;
	private final ShapeObjectType Compartment;
	private final ShapeObjectType Complex;
	private final ShapeObjectType nucleicAcidFeature;
	private final ShapeObjectType Macromolecule;
	private final ShapeObjectType SimpleChem;
	private final ShapeObjectType UnspecEntity;
	private final ShapeObjectType Sink;
	private final ShapeObjectType Source;
	private final ShapeObjectType PerturbingAgent;
	private final ShapeObjectType Phenotype;
	private final ShapeObjectType Process;
//	private ShapeObjectType ProcessV;
	private final ShapeObjectType OmittedProcess;
	private final ShapeObjectType UncertainProcess;
	private final ShapeObjectType Association;
	private final ShapeObjectType Dissociation;
	private final ShapeObjectType AndGate;
	private final ShapeObjectType OrGate;
//	private ShapeObjectType OrGateV;
	private final ShapeObjectType NotGate;

	// links
	private final LinkObjectType Consumption;
	private final LinkObjectType Production;
	private final LinkObjectType Modulation;
	private final LinkObjectType Stimulation;
	private final LinkObjectType Catalysis;
	private final LinkObjectType Inhibition;
	private final LinkObjectType Trigger;
	private final LinkObjectType LogicArc;

	private final INotationSubsystem serviceProvider;
	private final Map<IPropertyDefinition, ILabelObjectType> labelPropMap;
	private final LabelObjectType unitOfInfoLabelObjectType = new LabelObjectType(this, UNIT_INFO_LABEL_UID, "UnitOfInformationLabel");
	private final PlainTextPropertyDefinition nameLabelPropertyDefinition = new PlainTextPropertyDefinition("name", "Name");
	private final LabelObjectType nameLabelObjectType = new LabelObjectType(this, NAME_LABEL_UID, "NameLabel");
	private final IntegerPropertyDefinition stoichiometryPropertyDefinition = new IntegerPropertyDefinition("stoich", 1);
	private final LabelObjectType stoichiometryLabelObjectType = new LabelObjectType(this, STOICH_LABEL_UID, "StoichiometryLabelUid");
	private final PlainTextPropertyDefinition stateValuePropertyDescription = new PlainTextPropertyDefinition("stateValue", "?");
	private final LabelObjectType stateValueLabelObjectType = new LabelObjectType(this, STATE_LABEL_UID, "stateLabel");
	private final PlainTextPropertyDefinition unitOfInfoPropertyDescription = new PlainTextPropertyDefinition("unitOfInfo", "Info");

	public SbgnPdNotationSyntaxService(INotationSubsystem serviceProvider) {
		this.serviceProvider = serviceProvider;
		this.context = serviceProvider.getNotation();
		this.labelPropMap = new HashMap<IPropertyDefinition, ILabelObjectType>();
		// "SBGN-PD"
		// "SBGN process diagram language context"
		// 1_0_0
		createNameProperty();
		createStoichiometryProperty();
		createStateValueProperty();
		createUnitOfInformationValueProperty();
		
		createRMO();
		// shapes
		this.State = new ShapeObjectType(this, 10, "State");
		createState();
		this.UnitOfInf = new ShapeObjectType(this, 11, "Unit Of Information");
		createUnitOfInf();
		this.Compartment = new ShapeObjectType(this, 13, "Compartment");
		createCompartment();
		this.Complex = new ShapeObjectType(this, 14, "Complex");
		createComplex();
		this.nucleicAcidFeature = new ShapeObjectType(this, 15, "Nucleic Acid Feature");
		createNucleicAcidFeature();
		this.Macromolecule = new ShapeObjectType(this, 16, "Macromolecule");
		createMacromolecule();
		this.SimpleChem = new ShapeObjectType(this, 18, "Simple Chemical");
		createSimpleChem();
		this.UnspecEntity = new ShapeObjectType(this, 110, "Unspecified Entity");
		createUnspecEntity();
		this.Sink = new ShapeObjectType(this, 111, "Sink");
		createSink();
		this.Source = new ShapeObjectType(this, 112, "Source");
		createSource();
		this.PerturbingAgent = new ShapeObjectType(this, 113, "Perturbing Agent");
		createPerturbingAgent();
		this.Phenotype = new ShapeObjectType(this, 114, "Phenotype");
		createPhenotype();
		this.Process = new ShapeObjectType(this, 118, "Process");
		createProcess();
		this.OmittedProcess = new ShapeObjectType(this, 119, "Omitted Process");
		createOmittedProcess();
		this.UncertainProcess = new ShapeObjectType(this, 120, "Uncertain Process");
		createUncertainProcess();
		this.Association = new ShapeObjectType(this, 121, "Association");
		createAssociation();
		this.Dissociation = new ShapeObjectType(this, 122, "Dissociation");
		createDissociation();
		this.AndGate = new ShapeObjectType(this, 123, "AND");
		createAndGate();
		this.OrGate = new ShapeObjectType(this, 124, "OR");
		createOrGate();
		this.NotGate = new ShapeObjectType(this, 125, "NOT");
		createNotGate();

		defineParentingRMO();
		// shapes parenting
		defineParentingState();
		defineParentingUnitOfInf();
		defineParentingCompartment();
		defineParentingComplex();
		defineParentingGeneticUnit();
		defineParentingMacromolecule();
		defineParentingSimpleChem();
		defineParentingUnspecEntity();
		defineParentingSink();
		defineParentingSource();
		defineParentingPerturbation();
		defineParentingObservable();
		defineParentingProcess();
		defineParentingOmittedProcess();
		defineParentingUncertainProcess();
		defineParentingAssociation();
		defineParentingDissociation();
		defineParentingAndGate();
		defineParentingOrGate();
		defineParentingNotGate();

		// links
		this.Consumption = new LinkObjectType(this, 20, "Consumption");
		createConsumption();
		this.Production = new LinkObjectType(this, 21, "Production");
		createProduction();
		this.Modulation = new LinkObjectType(this, 22, "Modulation");
		createModulation();
		this.Stimulation = new LinkObjectType(this, 23, "Stimulation");
		createStimulation();
		this.Catalysis = new LinkObjectType(this, 24, "Catalysis");
		createCatalysis();
		this.Inhibition = new LinkObjectType(this, 25, "Inhibition");
		createInhibition();
		this.Trigger = new LinkObjectType(this, 26, "Necessary Stimulation");
		createTrigger();
		this.LogicArc = new LinkObjectType(this, 27, "LogicArc");
		createLogicArc();

		// shape set
		this.shapes.put(this.State.getUniqueId(), this.State);
		this.shapes.put(this.UnitOfInf.getUniqueId(), this.UnitOfInf);
		this.shapes.put(this.Compartment.getUniqueId(), this.Compartment);
		this.shapes.put(this.Complex.getUniqueId(), this.Complex);
		this.shapes.put(this.nucleicAcidFeature.getUniqueId(), this.nucleicAcidFeature);
		this.shapes.put(this.Macromolecule.getUniqueId(), this.Macromolecule);
		this.shapes.put(this.SimpleChem.getUniqueId(), this.SimpleChem);
		this.shapes.put(this.UnspecEntity.getUniqueId(), this.UnspecEntity);
		this.shapes.put(this.Sink.getUniqueId(), this.Sink);
		this.shapes.put(this.Source.getUniqueId(), this.Source);
		this.shapes.put(this.PerturbingAgent.getUniqueId(), this.PerturbingAgent);
		this.shapes.put(this.Phenotype.getUniqueId(), this.Phenotype);
//		this.shapes.put(this.ProcessV.getUniqueId(), this.ProcessV);
		this.shapes.put(this.Process.getUniqueId(), this.Process);
		this.shapes.put(this.OmittedProcess.getUniqueId(), this.OmittedProcess);
		this.shapes.put(this.UncertainProcess.getUniqueId(),
				this.UncertainProcess);
		this.shapes.put(this.Association.getUniqueId(), this.Association);
		this.shapes.put(this.Dissociation.getUniqueId(), this.Dissociation);
		this.shapes.put(this.AndGate.getUniqueId(), this.AndGate);
		this.shapes.put(this.OrGate.getUniqueId(), this.OrGate);
//		this.shapes.put(this.OrGateV.getUniqueId(), this.OrGateV);
		this.shapes.put(this.NotGate.getUniqueId(), this.NotGate);

		// link set
		this.links.put(this.Consumption.getUniqueId(), this.Consumption);
		this.links.put(this.Production.getUniqueId(), this.Production);
		this.links.put(this.Modulation.getUniqueId(), this.Modulation);
		this.links.put(this.Stimulation.getUniqueId(), this.Stimulation);
		this.links.put(this.Catalysis.getUniqueId(), this.Catalysis);
		this.links.put(this.Inhibition.getUniqueId(), this.Inhibition);
		this.links.put(this.Trigger.getUniqueId(), this.Trigger);
		this.links.put(this.LogicArc.getUniqueId(), this.LogicArc);
	}

	public INotationSubsystem getNotationSubsystem() {
		return serviceProvider;
	}

	public INotation getNotation() {
		return this.context;
	}

	public Iterator<ILinkObjectType> linkTypeIterator() {
		return this.links.values().iterator();
	}

	public IRootObjectType getRootObjectType() {
		return this.rmo;
	}

	public Iterator<IShapeObjectType> shapeTypeIterator() {
		return this.shapes.values().iterator();
	}

	public Iterator<IObjectType> objectTypeIterator() {
		Set<IObjectType> retVal = new HashSet<IObjectType>(this.shapes.values());
		retVal.addAll(this.links.values());
		retVal.add(this.rmo);
		return retVal.iterator();
	}

	public boolean containsLinkObjectType(int uniqueId) {
		return this.links.containsKey(uniqueId);
	}

	public boolean containsObjectType(int uniqueId) {
		boolean retVal = this.links.containsKey(uniqueId);
		if (!retVal) {
			retVal = this.shapes.containsKey(uniqueId);
		}
		if (!retVal) {
			retVal = this.rmo.getUniqueId() == uniqueId;
		}
		return retVal;
	}

	public boolean containsShapeObjectType(int uniqueId) {
		return this.shapes.containsKey(uniqueId);
	}

	public ILinkObjectType getLinkObjectType(int uniqueId) {
		return this.links.get(uniqueId);
	}

	public IObjectType getObjectType(int uniqueId) {
		IObjectType retVal = this.links.get(uniqueId);
		if (retVal == null) {
			retVal = this.shapes.get(uniqueId);
		}
		if (retVal == null) {
			retVal = (this.rmo.getUniqueId() == uniqueId) ? this.rmo : null;
		}
		if (retVal == null) {
			throw new IllegalArgumentException(
					"The unique Id was not present in this notation subsystem");
		}
		return retVal;
	}

	public IShapeObjectType getShapeObjectType(int uniqueId) {
		return this.shapes.get(uniqueId);
	}

	private <T extends IObjectType> T findObjectTypeByName(
			Collection<? extends T> otSet, String name) {
		T retVal = null;
		for (T val : otSet) {
			if (val.getName().equals(name)) {
				retVal = val;
				break;
			}
		}
		return retVal;
	}

	public ILinkObjectType findLinkObjectTypeByName(String name) {
		return findObjectTypeByName(this.links.values(), name);
	}

	public IShapeObjectType findShapeObjectTypeByName(String name) {
		return findObjectTypeByName(this.shapes.values(), name);
	}

	public int numLinkObjectTypes() {
		return this.links.size();
	}

	public int numShapeObjectTypes() {
		return this.shapes.size();
	}

	public int numObjectTypes() {
		return this.numLinkObjectTypes() + this.numShapeObjectTypes()
				+ NUM_ROOT_OTS;
	}

	private void createRMO() {
		this.rmo = new RootObjectType(0, "Root Object", "ROOT_OBJECT", this);
	}

	private void defineParentingRMO() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf, this.Compartment, this.Complex,
				this.nucleicAcidFeature, this.Macromolecule, this.SimpleChem,
				this.UnspecEntity, this.Sink, this.Source, this.PerturbingAgent,
				this.Phenotype,
				this.Process,  this.OmittedProcess, this.UncertainProcess,
				this.Association, this.Dissociation, this.AndGate, this.OrGate,
				this.NotGate }));
		set.removeAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf }));
		for (IShapeObjectType child : set) {
			this.rmo.getParentingRules().addChild(child);
		}

	}

	private void createStateValueProperty(){
		stateValuePropertyDescription.setEditable(true);
		stateValuePropertyDescription.setDisplayName("Value");
		stateValueLabelObjectType.setAlwaysDisplayed(true);
		stateValueLabelObjectType.getDefaultAttributes().setNoBorder(true);
		stateValueLabelObjectType.getDefaultAttributes().setNoFill(true);
		stateValueLabelObjectType.getDefaultAttributes().setMinimumSize(new Dimension(30, 30));
		this.labelPropMap.put(stateValuePropertyDescription, stateValueLabelObjectType);
	}
	
	private void createState() {
		this.State.setDescription("State decription.");
//		this.State.getDefaultAttributes().addPropertyDefinition(createStoichiometryProperty());
		this.State.getDefaultAttributes().addPropertyDefinition(this.stateValuePropertyDescription);
		this.State.getDefaultAttributes().setShapeDefinition(STATE_DEFN);
		this.State.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.State.getDefaultAttributes().setLineWidth(1);
		this.State.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.State.getDefaultAttributes().setLineColour(Colour.BLACK);
		this.State.getDefaultAttributes().setSize(new Dimension(40, 30));

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.State.setEditableAttributes(editableAttributes);
	}

	private void defineParentingState() {
		this.State.getParentingRules().clear();
	}

	public ShapeObjectType getState() {
		return this.State;
	}
	
	private void createUnitOfInformationValueProperty(){
		//		infoDescnProp.setAlwaysDisplayed(true);
		unitOfInfoPropertyDescription.setEditable(true);
		unitOfInfoPropertyDescription.setDisplayName("Information");
		this.unitOfInfoLabelObjectType.setAlwaysDisplayed(true);
		this.unitOfInfoLabelObjectType.getDefaultAttributes().setNoFill(true);
		this.unitOfInfoLabelObjectType.getDefaultAttributes().setNoBorder(true);
		this.labelPropMap.put(unitOfInfoPropertyDescription, unitOfInfoLabelObjectType);
	}

	private void createUnitOfInf() {
		this.UnitOfInf.setDescription("Unit of information");
		
		this.UnitOfInf.getDefaultAttributes().addPropertyDefinition(this.unitOfInfoPropertyDescription);
		this.UnitOfInf.getDefaultAttributes().setShapeDefinition(UNIT_OF_INFO_DEFN);
		this.UnitOfInf.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.UnitOfInf.getDefaultAttributes().setSize(new Dimension(45, 25));
		this.UnitOfInf.getDefaultAttributes().setLineWidth(1);
		this.UnitOfInf.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.UnitOfInf.getDefaultAttributes().setLineColour(Colour.BLACK);
		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.UnitOfInf.setEditableAttributes(editableAttributes);
	}

	private void defineParentingUnitOfInf() {
		this.UnitOfInf.getParentingRules().clear();
	}

	public ShapeObjectType getUnitOfInf() {
		return this.UnitOfInf;
	}

	private void createCompartment() {
		this.Compartment.setDescription("Compartment");
		this.Compartment.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.Compartment.getDefaultAttributes().addPropertyDefinition(createCompartmentVolumeProperty());
		this.Compartment.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Compartment.getDefaultAttributes().setShapeDefinition(COMPARTMENT_DEFN);
		this.Compartment.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.Compartment.getDefaultAttributes().setSize(new Dimension(200, 200));
		this.Compartment.getDefaultAttributes().setLineWidth(3);
		this.Compartment.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Compartment.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Compartment.setEditableAttributes(editableAttributes);
	}

	private void defineParentingCompartment() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf, this.Compartment, this.Complex,
				this.nucleicAcidFeature, this.Macromolecule, this.SimpleChem,
				this.UnspecEntity, this.Sink, this.Source, this.PerturbingAgent,
				this.Phenotype,
				this.Process,  this.OmittedProcess, this.UncertainProcess,
				this.Association, this.Dissociation, this.AndGate, this.OrGate,
				this.NotGate }));
		set.removeAll(Arrays.asList(new IShapeObjectType[] { this.State }));
		for (IShapeObjectType child : set) {
			this.Compartment.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getCompartment() {
		return this.Compartment;
	}

	private NumberPropertyDefinition createCardFontSizeProperty(){
		NumberPropertyDefinition cardFontSizeProp = new NumberPropertyDefinition("cardFontSize", new BigDecimal(12.0));
//		cardFontSizeProp.setVisualisable(false);
		cardFontSizeProp.setEditable(true);
		cardFontSizeProp.setDisplayName("Cardinality Font Size");
		return cardFontSizeProp;
	}
	
	private IntegerPropertyDefinition createCardinalityProperty(){
		IntegerPropertyDefinition cardinalityProp = new IntegerPropertyDefinition("cardinality", 1);
//		cardinalityProp.setVisualisable(false);
		cardinalityProp.setEditable(true);
		cardinalityProp.setDisplayName("Cardinality");
//		cardinalityProp.getLabelDefaults().setLineWidth(1.0);
//		cardinalityProp.getLabelDefaults().setFillColour(Colour.WHITE);
//		cardinalityProp.getLabelDefaults().setLineColour(Colour.BLACK);
//		cardinalityProp.getLabelDefaults().setNoFill(false);
//		cardinalityProp.getLabelDefaults().setNoBorder(false);
//		cardinalityProp.getLabelDefaults().setLabelLocationPolicy(LabelLocationPolicy.COMPASS);
		return cardinalityProp;
	}
	
	
	private void createStoichiometryProperty(){
		//		cardinalityProp.setVisualisable(true);
		stoichiometryPropertyDefinition.setEditable(true);
		stoichiometryPropertyDefinition.setDisplayName("Stoichiometry");
		stoichiometryLabelObjectType.getDefaultAttributes().setLineWidth(1.0);
		stoichiometryLabelObjectType.getDefaultAttributes().setFillColour(Colour.WHITE);
		stoichiometryLabelObjectType.getDefaultAttributes().setLineColour(Colour.BLACK);
		stoichiometryLabelObjectType.getDefaultAttributes().setNoFill(false);
		stoichiometryLabelObjectType.getDefaultAttributes().setNoBorder(false);
		stoichiometryLabelObjectType.getDefaultAttributes().setLabelLocationPolicy(LabelLocationPolicy.COMPASS);
		this.labelPropMap.put(stoichiometryPropertyDefinition, stoichiometryLabelObjectType);
	}
	
	
	private void createNameProperty(){
		nameLabelObjectType.setAlwaysDisplayed(true);
//		nameProp.setAlwaysDisplayed(true);
		nameLabelPropertyDefinition.setEditable(true);
		nameLabelPropertyDefinition.setDisplayName("Name");
		nameLabelObjectType.getDefaultAttributes().setNoFill(true);
		nameLabelObjectType.getDefaultAttributes().setNoBorder(true);
		this.labelPropMap.put(nameLabelPropertyDefinition, nameLabelObjectType);
	}
	
	private IntegerPropertyDefinition createEntityCountProperty(){
		IntegerPropertyDefinition retVal = new IntegerPropertyDefinition("entityCount", 0);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDisplayName("Entity Count");
		return retVal;
	}
	
	private IPlainTextPropertyDefinition createForwardRateEquationProperty(){
		PlainTextPropertyDefinition retVal = new PlainTextPropertyDefinition("fwdRate", "");
//		retVal.setAlwaysDisplayed(false);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDisplayName("Forward Rate");
		return retVal;
	}
	
	private IPlainTextPropertyDefinition createExportNameProperty(){
		PlainTextPropertyDefinition retVal = new PlainTextPropertyDefinition("exportName", "");
//		retVal.setAlwaysDisplayed(false);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDisplayName("Export Name");
		return retVal;
	}
	
	private IPlainTextPropertyDefinition createReverseRateEquationProperty(){
		PlainTextPropertyDefinition retVal = new PlainTextPropertyDefinition("revRate", "");
//		retVal.setAlwaysDisplayed(false);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDisplayName("Reverse Rate");
		return retVal;
	}
	
	private INumberPropertyDefinition createCompartmentVolumeProperty(){
		NumberPropertyDefinition retVal = new NumberPropertyDefinition("compVol", BigDecimal.ZERO);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDisplayName("Volume (nL)");
		return retVal;
	}
	
	private void createComplex() {
		this.Complex.setDescription("Complex");
		this.Complex.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.Complex.getDefaultAttributes().addPropertyDefinition(createCardinalityProperty());
		this.Complex.getDefaultAttributes().addPropertyDefinition(createCardFontSizeProperty());
		this.Complex.getDefaultAttributes().addPropertyDefinition(createEntityCountProperty());
		this.Complex.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Complex.getDefaultAttributes().setShapeDefinition(COMPLEX_DEFN);
		this.Complex.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.Complex.getDefaultAttributes().setSize(new Dimension(120, 80));
		this.Complex.getDefaultAttributes().setLineWidth(1);
		this.Complex.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Complex.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
	}

	private void defineParentingComplex() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf, this.Macromolecule, this.SimpleChem,
				this.Complex,this.nucleicAcidFeature }));
		for (IShapeObjectType child : set) {
			this.Complex.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getComplex() {
		return this.Complex;
	}

	private void createNucleicAcidFeature() {
		this.nucleicAcidFeature.setDescription("Nucleic Acid Feature");
		this.nucleicAcidFeature.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.nucleicAcidFeature.getDefaultAttributes().addPropertyDefinition(createEntityCountProperty());
		this.nucleicAcidFeature.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.nucleicAcidFeature.getDefaultAttributes().setShapeDefinition(NUCLEIC_ACID_FEATURE_DEFN);
		this.nucleicAcidFeature.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.nucleicAcidFeature.getDefaultAttributes().setSize(new Dimension(60, 40));
		this.nucleicAcidFeature.getDefaultAttributes().setLineWidth(1);
		this.nucleicAcidFeature.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.nucleicAcidFeature.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.nucleicAcidFeature.setEditableAttributes(editableAttributes);
	}

	private void defineParentingGeneticUnit() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf }));
		for (IShapeObjectType child : set) {
			this.nucleicAcidFeature.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getGeneticUnit() {
		return this.nucleicAcidFeature;
	}

	private void createMacromolecule() {
		this.Macromolecule.setDescription("Macromolecule");
		this.Macromolecule.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.Macromolecule.getDefaultAttributes().addPropertyDefinition(createEntityCountProperty());
		this.Macromolecule.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Macromolecule.getDefaultAttributes().setShapeDefinition(MACROMOLECULE_DEFN);
		this.Macromolecule.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.Macromolecule.getDefaultAttributes().setLineColour(Colour.BLACK);
		this.Macromolecule.getDefaultAttributes().setLineWidth(1);
		this.Macromolecule.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Macromolecule.getDefaultAttributes().setSize(new Dimension(60, 40));

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Macromolecule.setEditableAttributes(editableAttributes);
		this.Macromolecule.getDefaultAttributes().addPropertyDefinition(createCardinalityProperty());
		this.Macromolecule.getDefaultAttributes().addPropertyDefinition(createCardFontSizeProperty());
	}

	private void defineParentingMacromolecule() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
				this.UnitOfInf }));
		for (IShapeObjectType child : set) {
			this.Macromolecule.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getMacromolecule() {
		return this.Macromolecule;
	}

	private void createSimpleChem() {
		this.SimpleChem.setDescription("Simple chemical");
		this.SimpleChem.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.SimpleChem.getDefaultAttributes().addPropertyDefinition(createCardinalityProperty());
		this.SimpleChem.getDefaultAttributes().addPropertyDefinition(createCardFontSizeProperty());
		this.SimpleChem.getDefaultAttributes().addPropertyDefinition(createEntityCountProperty());
		this.SimpleChem.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.SimpleChem.getDefaultAttributes().setShapeDefinition(SIMPLE_CHEM_DEFN);
		this.SimpleChem.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.SimpleChem.getDefaultAttributes().setSize(new Dimension(40, 40));
		this.SimpleChem.getDefaultAttributes().setLineWidth(1.0);
		this.SimpleChem.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.SimpleChem.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.SimpleChem.setEditableAttributes(editableAttributes);
	}

	private void defineParentingSimpleChem() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] {}));
		for (IShapeObjectType child : set) {
			this.SimpleChem.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getSimpleChem() {
		return this.SimpleChem;
	}

	private void createUnspecEntity() {
		this.UnspecEntity.setDescription("Unspecified entity");
		this.UnspecEntity.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.UnspecEntity.getDefaultAttributes().addPropertyDefinition(createEntityCountProperty());
		this.UnspecEntity.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.UnspecEntity.getDefaultAttributes().setShapeDefinition(UNSPECIFIED_ENTITY_DEFN);
		this.UnspecEntity.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.UnspecEntity.getDefaultAttributes().setSize(new Dimension(60, 40));
		this.UnspecEntity.getDefaultAttributes().setLineWidth(1);
		this.UnspecEntity.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.UnspecEntity.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.UnspecEntity.setEditableAttributes(editableAttributes);
	}

	private void defineParentingUnspecEntity() {
		HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] {}));
		for (IShapeObjectType child : set) {
			this.UnspecEntity.getParentingRules().addChild(child);
		}

	}

	public ShapeObjectType getUnspecEntity() {
		return this.UnspecEntity;
	}

	private void createSink() {
		this.Sink.setDescription("Sink");
		this.Sink.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Sink.getDefaultAttributes().setShapeDefinition(EMPTY_SET_DEFN);
		this.Sink.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.Sink.getDefaultAttributes().setSize(new Dimension(30, 30));
		this.Sink.getDefaultAttributes().setLineWidth(1);
		this.Sink.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Sink.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Sink.setEditableAttributes(editableAttributes);
	}

	private void defineParentingSink() {
		this.Sink.getParentingRules().clear();
	}

	public ShapeObjectType getSink() {
		return this.Sink;
	}

	private void createSource() {
		this.Source.setDescription("Source");
		this.Source.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Source.getDefaultAttributes().setShapeDefinition(EMPTY_SET_DEFN);
		this.Source.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.Source.getDefaultAttributes().setSize(new Dimension(30, 30));
		this.Source.getDefaultAttributes().setLineWidth(1);
		this.Source.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Source.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Source.setEditableAttributes(editableAttributes);
	}

	private void defineParentingSource() {
		this.Source.getParentingRules().clear();
	}

	public ShapeObjectType getSource() {
		return this.Source;
	}

	private void createPerturbingAgent() {
		this.PerturbingAgent.setDescription("Perturbing Agent");
		this.PerturbingAgent.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.PerturbingAgent.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.PerturbingAgent.getDefaultAttributes().setShapeDefinition(PERTURBATION_DEFN);
		this.PerturbingAgent.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.PerturbingAgent.getDefaultAttributes().setSize(new Dimension(80, 60));
		this.PerturbingAgent.getDefaultAttributes().setLineWidth(1);
		this.PerturbingAgent.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.PerturbingAgent.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.PerturbingAgent.setEditableAttributes(editableAttributes);
	}

	private void defineParentingPerturbation() {
		this.PerturbingAgent.getParentingRules().clear();
	}

	public ShapeObjectType getPerturbation() {
		return this.PerturbingAgent;
	}

	private void createPhenotype() {
		this.Phenotype.setDescription("Phenotype");
		this.Phenotype.getDefaultAttributes().setShapeDefinition(PHENOTYPE_DEFN);
		this.Phenotype.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Phenotype.getDefaultAttributes().addPropertyDefinition(this.nameLabelPropertyDefinition);
		this.Phenotype.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.Phenotype.getDefaultAttributes().setLineColour(Colour.BLACK);
		this.Phenotype.getDefaultAttributes().setSize(new Dimension(80, 60));
		this.Phenotype.getDefaultAttributes().setLineWidth(1);
		this.Phenotype.getDefaultAttributes().setLineStyle(LineStyle.SOLID);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Phenotype.setEditableAttributes(editableAttributes);
	}

	private void defineParentingObservable() {
		this.Phenotype.getParentingRules().clear();
	}

	public ShapeObjectType getObservable() {
		return this.Phenotype;
	}


	private void createProcess() {
		this.Process.setDescription("Process node");
		this.Process.getDefaultAttributes().addPropertyDefinition(createForwardRateEquationProperty());
		this.Process.getDefaultAttributes().addPropertyDefinition(createReverseRateEquationProperty());
		this.Process.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.Process.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Process.getDefaultAttributes().setShapeDefinition(PROCESS_DEFN);
		this.Process.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.Process.getDefaultAttributes().setSize(new Dimension(30.0, 30.0*0.7));
		this.Process.getDefaultAttributes().setLineWidth(1);
		this.Process.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Process.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Process.setEditableAttributes(editableAttributes);
	}

	private IPropertyDefinition createVerticalAlignmentProperty() {
		BooleanPropertyDefinition retVal = new BooleanPropertyDefinition("vertFlag", true);
//		retVal.setAlwaysDisplayed(false);
//		retVal.setVisualisable(false);
		retVal.setEditable(true);
		retVal.setDefaultValue(Boolean.FALSE);
		retVal.setDisplayName("Vertical Lugs?");
		return retVal;
	}

	private void defineParentingProcess() {
		this.Process.getParentingRules().clear();
	}

	public ShapeObjectType getProcess() {
		return this.Process;
	}

	private void createOmittedProcess() {
		this.OmittedProcess.setDescription("Omitted process");
		this.OmittedProcess.getDefaultAttributes().addPropertyDefinition(createForwardRateEquationProperty());
		this.OmittedProcess.getDefaultAttributes().addPropertyDefinition(createReverseRateEquationProperty());
		this.OmittedProcess.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.OmittedProcess.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.OmittedProcess.getDefaultAttributes().setShapeDefinition(OMITTED_PROCESS_DEFN);
		this.OmittedProcess.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.OmittedProcess.getDefaultAttributes().setSize(new Dimension(30, 30.0*0.7));
		this.OmittedProcess.getDefaultAttributes().setLineWidth(1);
		this.OmittedProcess.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.OmittedProcess.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.OmittedProcess.setEditableAttributes(editableAttributes);
	}

	private void defineParentingOmittedProcess() {
		this.OmittedProcess.getParentingRules().clear();
	}

	public ShapeObjectType getOmittedProcess() {
		return this.OmittedProcess;
	}

	private void createUncertainProcess() {
		this.UncertainProcess.setDescription("Uncertain process");
		this.UncertainProcess.getDefaultAttributes().addPropertyDefinition(createForwardRateEquationProperty());
		this.UncertainProcess.getDefaultAttributes().addPropertyDefinition(createReverseRateEquationProperty());
		this.UncertainProcess.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.UncertainProcess.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.UncertainProcess.getDefaultAttributes().setShapeDefinition(UNCERTAIN_PROCESS_DEFN);
		this.UncertainProcess.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.UncertainProcess.getDefaultAttributes().setSize(new Dimension(30, 30.0*0.7));
		this.UncertainProcess.getDefaultAttributes().setLineWidth(1);
		this.UncertainProcess.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.UncertainProcess.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.UncertainProcess.setEditableAttributes(editableAttributes);
	}

	private void defineParentingUncertainProcess() {
		this.UncertainProcess.getParentingRules().clear();
	}

	public ShapeObjectType getUncertainProcess() {
		return this.UncertainProcess;
	}

	private void createAssociation() {
		this.Association.setDescription("Association");
		this.Association.getDefaultAttributes().addPropertyDefinition(createForwardRateEquationProperty());
		this.Association.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Association.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.Association.getDefaultAttributes().setShapeDefinition(ASSOC_DEFN);
		this.Association.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.Association.getDefaultAttributes().setSize(new Dimension(30.0, 30.0*0.7));
		this.Association.getDefaultAttributes().setLineWidth(1);
		this.Association.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Association.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Association.setEditableAttributes(editableAttributes);
	}

	private void defineParentingAssociation() {
		this.Association.getParentingRules().clear();
	}

	public ShapeObjectType getAssociation() {
		return this.Association;
	}

	private void createDissociation() {
		this.Dissociation.setDescription("Dissociation arc.");
		this.Dissociation.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Dissociation.getDefaultAttributes().addPropertyDefinition(createForwardRateEquationProperty());
		this.Dissociation.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.Dissociation.getDefaultAttributes().setShapeDefinition(DISSOC_DEFN);
		this.Dissociation.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.Dissociation.getDefaultAttributes().setSize(new Dimension(30.0, 30.0*0.7));
		this.Dissociation.getDefaultAttributes().setLineWidth(1);
		this.Dissociation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Dissociation.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.Dissociation.setEditableAttributes(editableAttributes);
	}

	private void defineParentingDissociation() {
		this.Dissociation.getParentingRules().clear();
	}

	public ShapeObjectType getDissociation() {
		return this.Dissociation;
	}

	private void createAndGate() {
		this.AndGate.setDescription("AND Logical Operator");
		this.AndGate.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.AndGate.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.AndGate.getDefaultAttributes().setShapeDefinition(AND_SHAPE_DEFN);
		this.AndGate.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.AndGate.getDefaultAttributes().setSize(new Dimension(30, 30));
		this.AndGate.getDefaultAttributes().setLineWidth(1);
		this.AndGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.AndGate.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.AndGate.setEditableAttributes(editableAttributes);
	}

	private void defineParentingAndGate() {
		this.AndGate.getParentingRules().clear();
	}

	public ShapeObjectType getAndGate() {
		return this.AndGate;
	}

	private void createOrGate() {
		this.OrGate.setDescription("OR Logical Operator");
		this.OrGate.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.OrGate.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.OrGate.getDefaultAttributes().setShapeDefinition(OR_SHAPE_DEFN);
		this.OrGate.getDefaultAttributes().setFillColour(new Colour(255, 255, 255));
		this.OrGate.getDefaultAttributes().setSize(new Dimension(30, 30));
		this.OrGate.getDefaultAttributes().setLineWidth(1);
		this.OrGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.OrGate.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.OrGate.setEditableAttributes(editableAttributes);
	}


	private void defineParentingOrGate() {
		this.OrGate.getParentingRules().clear();
	}

	public ShapeObjectType getOrGate() {
		return this.OrGate;
	}

	private void createNotGate() {
		this.NotGate.setDescription("NOT Logical Operator");
		this.NotGate.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.NotGate.getDefaultAttributes().addPropertyDefinition(createVerticalAlignmentProperty());
		this.NotGate.getDefaultAttributes().setShapeDefinition(NOT_SHAPE_DEFN);
		this.NotGate.getDefaultAttributes().setFillColour(Colour.WHITE);
		this.NotGate.getDefaultAttributes().setSize(new Dimension(30, 30));
		this.NotGate.getDefaultAttributes().setLineWidth(1);
		this.NotGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.NotGate.getDefaultAttributes().setLineColour(Colour.BLACK);

		EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
				.noneOf(EditableShapeAttributes.class);
		if (true) {
			editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
		}
		if (true) {
			editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
		}
		this.NotGate.setEditableAttributes(editableAttributes);
	}

	private void defineParentingNotGate() {
		this.NotGate.getParentingRules().clear();
	}

	public ShapeObjectType getNotGate() {
		return this.NotGate;
	}

	private void createConsumption() {
		Set<IShapeObjectType> set = null;
		this.Consumption.setDescription("Consumption arc.");
		this.Consumption.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Consumption.getDefaultAttributes().addPropertyDefinition(this.stoichiometryPropertyDefinition);
		this.Consumption.getDefaultAttributes().setLineWidth(1);
		this.Consumption.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Consumption.getDefaultAttributes().setLineColour(Colour.BLACK);
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Consumption.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Consumption.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Consumption.getDefaultAttributes().setLineWidthEditable(true);
		this.Consumption.setEditableAttributes(editableAttributes);
		LinkTerminusDefinition sport = this.Consumption
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Consumption
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap(5);
		sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 0);
		tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);
		tport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.Complex, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Consumption.getLinkConnectionRules().addConnection(
					this.Source, tgt);
		}

	}

	public LinkObjectType getConsumption() {
		return this.Consumption;
	}

	private void createProduction() {
		Set<IShapeObjectType> set = null;
		this.Production.setDescription("Production arc.");
		this.Production.getDefaultAttributes().addPropertyDefinition(this.stoichiometryPropertyDefinition);
		this.Production.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Production.getDefaultAttributes().setLineWidth(1);
		this.Production.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Production.getDefaultAttributes().setLineColour(Colour.BLACK);
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Production.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Production.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Production.getDefaultAttributes().setLineWidthEditable(true);
		this.Production.setEditableAttributes(editableAttributes);

		// LinkEndDefinition sport=this.Production.getLinkSource();
		// LinkEndDefinition tport=this.Production.getLinkTarget();
		LinkTerminusDefinition sport = this.Production
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Production
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 5);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.TRIANGLE);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(10, 10));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Complex,
				this.Macromolecule, this.nucleicAcidFeature,this.SimpleChem, this.UnspecEntity,
				this.Sink }));
		for (IShapeObjectType tgt : set) {
			this.Production.getLinkConnectionRules().addConnection(
					this.Process, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Complex,
				this.Macromolecule, this.nucleicAcidFeature,this.SimpleChem, this.UnspecEntity,
				this.Sink }));
		for (IShapeObjectType tgt : set) {
			this.Production.getLinkConnectionRules().addConnection(
					this.OmittedProcess, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Complex,
				this.Macromolecule, this.nucleicAcidFeature,this.SimpleChem, this.UnspecEntity,
				this.Sink }));
		for (IShapeObjectType tgt : set) {
			this.Production.getLinkConnectionRules().addConnection(
					this.UncertainProcess, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Complex,
				this.Macromolecule, this.nucleicAcidFeature,this.SimpleChem, this.UnspecEntity,
				this.Sink }));
		for (IShapeObjectType tgt : set) {
			this.Production.getLinkConnectionRules().addConnection(
					this.Association, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Complex,
				this.Macromolecule, this.nucleicAcidFeature,this.SimpleChem, this.UnspecEntity,
				this.Sink }));
		for (IShapeObjectType tgt : set) {
			this.Production.getLinkConnectionRules().addConnection(
					this.Dissociation, tgt);
		}

	}

	public LinkObjectType getProduction() {
		return this.Production;
	}

	private void createModulation() {
		Set<IShapeObjectType> set = null;
		this.Modulation.setDescription("Modulation arc.");
		this.Modulation.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		int[] lc = new int[] { 0, 0, 0 };
		this.Modulation.getDefaultAttributes().setLineWidth(1);
		this.Modulation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Modulation.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Modulation.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Modulation.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Modulation.getDefaultAttributes().setLineWidthEditable(true);
		this.Modulation.setEditableAttributes(editableAttributes);

		// LinkEndDefinition sport=this.Modulation.getLinkSource();
		// LinkEndDefinition tport=this.Modulation.getLinkTarget();
		LinkTerminusDefinition sport = this.Modulation
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Modulation
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(8, 8));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 5);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.EMPTY_DIAMOND);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(5, 5));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.Complex, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.PerturbingAgent, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.AndGate, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(this.OrGate,
					tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Modulation.getLinkConnectionRules().addConnection(
					this.NotGate, tgt);
		}

	}

	public LinkObjectType getModulation() {
		return this.Modulation;
	}

	private void createStimulation() {
		Set<IShapeObjectType> set = null;
		this.Stimulation.setDescription("Stimulation arc.");
		int[] lc = new int[] { 0, 0, 0 };
		this.Stimulation.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Stimulation.getDefaultAttributes().setLineWidth(1);
		this.Stimulation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Stimulation.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Stimulation.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Stimulation.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Stimulation.getDefaultAttributes().setLineWidthEditable(true);
		this.Stimulation.setEditableAttributes(editableAttributes);

		// LinkEndDefinition sport=this.Stimulation.getLinkSource();
		// LinkEndDefinition tport=this.Stimulation.getLinkTarget();
		LinkTerminusDefinition sport = this.Stimulation
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Stimulation
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 5);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.EMPTY_TRIANGLE);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(10, 10));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.Complex, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.PerturbingAgent, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.AndGate, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.OrGate, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Stimulation.getLinkConnectionRules().addConnection(
					this.NotGate, tgt);
		}

	}

	public LinkObjectType getStimulation() {
		return this.Stimulation;
	}

	private void createCatalysis() {
		Set<IShapeObjectType> set = null;
		this.Catalysis.setDescription("Catalysis.");
		this.Catalysis.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		int[] lc = new int[] { 0, 0, 0 };
		this.Catalysis.getDefaultAttributes().setLineWidth(1);
		this.Catalysis.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Catalysis.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Catalysis.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Catalysis.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Catalysis.getDefaultAttributes().setLineWidthEditable(true);
		this.Catalysis.setEditableAttributes(editableAttributes);
		LinkTerminusDefinition sport = this.Catalysis
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Catalysis
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 10);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.EMPTY_CIRCLE);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(10, 10));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(this.Complex,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(this.AndGate,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(this.OrGate,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation }));
		for (IShapeObjectType tgt : set) {
			this.Catalysis.getLinkConnectionRules().addConnection(this.NotGate,
					tgt);
		}

	}

	public LinkObjectType getCatalysis() {
		return this.Catalysis;
	}

	private void createInhibition() {
		Set<IShapeObjectType> set = null;
		this.Inhibition.setDescription("Inhibition arc.");
		this.Inhibition.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		int[] lc = new int[] { 0, 0, 0 };
		this.Inhibition.getDefaultAttributes().setLineWidth(1);
		this.Inhibition.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Inhibition.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Inhibition.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Inhibition.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Inhibition.getDefaultAttributes().setLineWidthEditable(true);
		this.Inhibition.setEditableAttributes(editableAttributes);

		LinkTerminusDefinition sport = this.Inhibition
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Inhibition
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 10);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.BAR);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(10, 10));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.Complex, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.PerturbingAgent, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.AndGate, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(this.OrGate,
					tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Inhibition.getLinkConnectionRules().addConnection(
					this.NotGate, tgt);
		}

	}

	public LinkObjectType getInhibition() {
		return this.Inhibition;
	}

	private void createTrigger() {
		Set<IShapeObjectType> set = null;
		this.Trigger.setDescription("Necessary stimulation.");
		int[] lc = new int[] { 0, 0, 0 };
		this.Trigger.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		this.Trigger.getDefaultAttributes().setLineWidth(1);
		this.Trigger.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.Trigger.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.Trigger.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.Trigger.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.Trigger.getDefaultAttributes().setLineWidthEditable(true);
		this.Trigger.setEditableAttributes(editableAttributes);

		// LinkEndDefinition sport=this.Trigger.getLinkSource();
		// LinkEndDefinition tport=this.Trigger.getLinkTarget();
		LinkTerminusDefinition sport = this.Trigger
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.Trigger
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 0);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 5);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.TRIANGLE_BAR);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(5, 5));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process, 
				this.OmittedProcess, this.UncertainProcess, this.Association,
				this.Dissociation, this.Phenotype }));
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(this.Complex,
					tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(
					this.PerturbingAgent, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(this.AndGate,
					tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(this.OrGate,
					tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.Trigger.getLinkConnectionRules().addConnection(this.NotGate,
					tgt);
		}

	}

	public LinkObjectType getTrigger() {
		return this.Trigger;
	}

	private void createLogicArc() {
		Set<IShapeObjectType> set = null;
		this.LogicArc.setDescription("Logic arc.");
		this.LogicArc.getDefaultAttributes().addPropertyDefinition(createExportNameProperty());
		int[] lc = new int[] { 0, 0, 0 };
		this.LogicArc.getDefaultAttributes().setLineWidth(1);
		this.LogicArc.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
		this.LogicArc.getDefaultAttributes().setLineColour(
				new Colour(lc[0], lc[1], lc[2]));
		EnumSet<LinkEditableAttributes> editableAttributes = EnumSet
				.noneOf(LinkEditableAttributes.class);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.COLOUR);
		}
		// this.LogicArc.getDefaultAttributes().setLineColourEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
		}
		// this.LogicArc.getDefaultAttributes().setLineStyleEditable(true);
		if (true) {
			editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
		}
		// this.LogicArc.getDefaultAttributes().setLineWidthEditable(true);
		this.LogicArc.setEditableAttributes(editableAttributes);

		// LinkEndDefinition sport=this.LogicArc.getLinkSource();
		// LinkEndDefinition tport=this.LogicArc.getLinkTarget();
		LinkTerminusDefinition sport = this.LogicArc
				.getSourceTerminusDefinition();
		LinkTerminusDefinition tport = this.LogicArc
				.getTargetTerminusDefinition();
		sport.getDefaultAttributes().setGap((short) 2);
		sport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 8,8);
		sport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editablesportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// sport.getDefaultAttributes().setShapeTypeEditable(true);
		// sport.getDefaultAttributes().setColourEditable(true);
		sport.setEditableAttributes(editablesportAttributes);
		tport.getDefaultAttributes().setGap((short) 0);
		tport.getDefaultAttributes().setEndDecoratorType(
				LinkEndDecoratorShape.NONE);// , 5,5);
		tport.getDefaultAttributes().setEndSize(new Dimension(0, 0));
		EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet
				.of(LinkTermEditableAttributes.END_SIZE,
						LinkTermEditableAttributes.OFFSET);
		if (true) {
			editabletportAttributes
					.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
		}
		// tport.getDefaultAttributes().setShapeTypeEditable(true);
		// tport.getDefaultAttributes().setColourEditable(true);
		tport.setEditableAttributes(editabletportAttributes);

		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(this.Complex,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(
					this.Macromolecule, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(
					this.SimpleChem, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(
					this.UnspecEntity, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(
					this.PerturbingAgent, tgt);
		}
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(
					this.nucleicAcidFeature, tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(this.AndGate,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(this.OrGate,
					tgt);
		}
		set = new HashSet<IShapeObjectType>();
		set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
				this.OrGate, this.NotGate }));
		for (IShapeObjectType tgt : set) {
			this.LogicArc.getLinkConnectionRules().addConnection(this.NotGate,
					tgt);
		}

	}

	public LinkObjectType getLogicArc() {
		return this.LogicArc;
	}

	public ILabelObjectType getLabelObjectType(int uniqueId) {
		ILabelObjectType retVal = null;
		for(ILabelObjectType labelObjectType : this.labelPropMap.values()){
			if(labelObjectType.getUniqueId() == uniqueId){
				retVal = labelObjectType;
				break;
			}
		}
		return retVal;
	}

	public ILabelObjectType getLabelObjectTypeByProperty(IPropertyDefinition propDefn) {
		return this.labelPropMap.get(propDefn);
	}

	public boolean isVisualisableProperty(IPropertyDefinition propDefn) {
		return this.labelPropMap.containsKey(propDefn);
	}

}

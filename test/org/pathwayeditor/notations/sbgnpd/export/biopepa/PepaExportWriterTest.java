package org.pathwayeditor.notations.sbgnpd.export.biopepa;


import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode.SidednessType;
import org.pathwayeditor.notations.sbgnpd.ndom.impl.MapDiagram;

public class PepaExportWriterTest {
	private BioPepaExportWriter testInstance = null;
	private IMapDiagram mockNdom = null;
	
	@Before
	public void setUp() throws Exception {
//		ICanvas mockCanvas = EasyMock.createMock("mockCanvas", ICanvas.class);
//		EasyMock.expect(mockCanvas.getName()).andStubReturn("map");
//		IShapeNode mockShape1 = EasyMock.createMock("mockShape1", IShapeNode.class);
//		IShapeAttribute mockShape1Att = EasyMock.createMock("mockShape1Att", IShapeAttribute.class);
//		EasyMock.expect(mockShape1.getAttribute()).andStubReturn(mockShape1Att);
//		EasyMock.expect(mockShape1Att.getCreationSerial()).andStubReturn(1);
//		IAnnotationProperty mockProp1 = EasyMock.createMock("mockProp1", IAnnotationProperty.class);
//		EasyMock.expect(mockShape1Att.getProperty("name")).andStubReturn(mockProp1);
//		EasyMock.expect(mockProp1.getValue()).andStubReturn("foo");
//		
//		IShapeNode mockShape2 = EasyMock.createMock("mockShape2", IShapeNode.class);
//		IShapeAttribute mockShape2Att = EasyMock.createMock("mockShape2Att", IShapeAttribute.class);
//		EasyMock.expect(mockShape2.getAttribute()).andStubReturn(mockShape2Att);
//		EasyMock.expect(mockShape2Att.getCreationSerial()).andStubReturn(2);
//		IAnnotationProperty mockProp2 = EasyMock.createMock("mockProp2", IAnnotationProperty.class);
//		EasyMock.expect(mockShape2Att.getProperty("name")).andStubReturn(mockProp2);
//		EasyMock.expect(mockProp2.getValue()).andStubReturn("bar");
//
//		IShapeNode mockShape3 = EasyMock.createMock("mockShape3", IShapeNode.class);
//		IShapeAttribute mockShape3Att = EasyMock.createMock("mockShape3Att", IShapeAttribute.class);
//		EasyMock.expect(mockShape3.getAttribute()).andStubReturn(mockShape3Att);
//		EasyMock.expect(mockShape3Att.getCreationSerial()).andStubReturn(3);
//		IAnnotationProperty mockShape3Prop1 = EasyMock.createMock("mockShape3Prop1", IAnnotationProperty.class);
//		EasyMock.expect(mockShape3Att.getProperty("name")).andStubReturn(mockShape3Prop1);
//		EasyMock.expect(mockShape3Prop1.getValue()).andStubReturn("bar2");
//
//		IShapeNode mockShape4 = EasyMock.createMock("mockShape4", IShapeNode.class);
//		IShapeAttribute mockShape4Att = EasyMock.createMock("mockShape4Att", IShapeAttribute.class);
//		EasyMock.expect(mockShape4.getAttribute()).andStubReturn(mockShape4Att);
//		EasyMock.expect(mockShape4Att.getCreationSerial()).andStubReturn(3);
//		IAnnotationProperty mockShape4Prop1 = EasyMock.createMock("mockShape4Prop1", IAnnotationProperty.class);
//		EasyMock.expect(mockShape4Att.getProperty("name")).andStubReturn(mockShape4Prop1);
//		EasyMock.expect(mockShape4Prop1.getValue()).andStubReturn("bar2");
//
//		ILinkEdge mockEdge1 = EasyMock.createMock("mockEdge1", ILinkEdge.class);
//		ILinkAttribute mockEdge1Att = EasyMock.createMock("mockEdge1Att", ILinkAttribute.class);
//		EasyMock.expect(mockEdge1.getAttribute()).andStubReturn(mockEdge1Att);
//		EasyMock.expect(mockEdge1Att.getCreationSerial()).andStubReturn(4);
//		IAnnotationProperty mockEdge1Prop1 = EasyMock.createMock("mockEdge1Prop1", IAnnotationProperty.class);
//		EasyMock.expect(mockEdge1Att.getProperty("name")).andStubReturn(mockEdge1Prop1);
//		EasyMock.expect(mockEdge1Prop1.getValue()).andStubReturn("prop1");
//
//		ILinkEdge mockEdge2 = EasyMock.createMock("mockEdge2", ILinkEdge.class);
//		ILinkAttribute mockEdge2Att = EasyMock.createMock("mockEdge2Att", ILinkAttribute.class);
//		EasyMock.expect(mockEdge2.getAttribute()).andStubReturn(mockEdge2Att);
//		EasyMock.expect(mockEdge2Att.getCreationSerial()).andStubReturn(5);
//		IAnnotationProperty mockEdge2Prop1 = EasyMock.createMock("mockEdge2Prop1", IAnnotationProperty.class);
//		EasyMock.expect(mockEdge2Att.getProperty("name")).andStubReturn(mockEdge2Prop1);
//		EasyMock.expect(mockEdge2Prop1.getValue()).andStubReturn("prop1");
//
//		ILinkEdge mockEdge3 = EasyMock.createMock("mockEdge3", ILinkEdge.class);
//		ILinkAttribute mockEdge3Att = EasyMock.createMock("mockEdge3Att", ILinkAttribute.class);
//		EasyMock.expect(mockEdge3.getAttribute()).andStubReturn(mockEdge3Att);
//		EasyMock.expect(mockEdge3Att.getCreationSerial()).andStubReturn(5);
//		IAnnotationProperty mockEdge3Prop1 = EasyMock.createMock("mockEdge3Prop1", IAnnotationProperty.class);
//		EasyMock.expect(mockEdge3Att.getProperty("name")).andStubReturn(mockEdge3Prop1);
//		EasyMock.expect(mockEdge3Prop1.getValue()).andStubReturn("prop1");
//
//		
//		EasyMock.replay(mockCanvas);
//		EasyMock.replay(mockShape1);
//		EasyMock.replay(mockShape2);
//		EasyMock.replay(mockShape1Att);
//		EasyMock.replay(mockShape2Att);
//		EasyMock.replay(mockProp1);
//		EasyMock.replay(mockProp2);
//
//		EasyMock.replay(mockShape3);
//		EasyMock.replay(mockShape3Att);
//		EasyMock.replay(mockShape3Prop1);
//
//		EasyMock.replay(mockShape4);
//		EasyMock.replay(mockShape4Att);
//		EasyMock.replay(mockShape4Prop1);
//
//		EasyMock.replay(mockEdge1);
//		EasyMock.replay(mockEdge1Att);
//		EasyMock.replay(mockEdge1Prop1);
//
//		EasyMock.replay(mockEdge2);
//		EasyMock.replay(mockEdge2Att);
//		EasyMock.replay(mockEdge2Prop1);
//
//		EasyMock.replay(mockEdge3);
//		EasyMock.replay(mockEdge3Att);
//		EasyMock.replay(mockEdge3Prop1);

		
		this.mockNdom = new MapDiagram("mockNdom");
		IMacromoleculeNode mm1 = this.mockNdom.createMacromoleculeNode(1, "mm1");
		IMacromoleculeNode mm2 = this.mockNdom.createMacromoleculeNode(2, "mm2");
		mm2.setCardinality(2);
		IMacromoleculeNode mm3 = this.mockNdom.createMacromoleculeNode(3, "mm3");
		
		IProcessNode p1 = this.mockNdom.createProcessNode(4, ProcessNodeType.STANDARD);
		
		IConsumptionArc ca1 = p1.createConsumptionArc(5, mm1);
		ca1.setStoichiometry(1);
		IProductionArc pa1 = p1.createProductionArc(6, mm2, SidednessType.RHS);
		pa1.setStoichiometry(2);
		p1.createModulationArc(7, ModulatingArcType.STIMULATION, mm3);
		
		this.testInstance = new BioPepaExportWriter(this.mockNdom, new File("foo.txt"), null);
	}

	@After
	public void tearDown() throws Exception {
		this.testInstance = null;
		this.mockNdom = null;
	}

	@Test
	public void testTemplateWriting() throws IOException{
		this.testInstance.writeExport();
	}
	
}

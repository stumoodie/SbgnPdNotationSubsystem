package org.pathwayeditor.notations.sbgnpd.export.sbgntext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.pathwayeditor.notations.sbgnpd.export.IExportWriter;
import org.pathwayeditor.notations.sbgnpd.export.IReportLog;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumeableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IConsumptionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulateableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElement;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProduceableNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProductionArc;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IStateDescription;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class SbgnTextExportWriter implements IExportWriter {
	private static final String BIOPEPA_TEMPLATE_FILE_NAME = "biopepa.stg";
	private static final String ELEMENT_NAME = "element";
	private final IMapDiagram ndom;
	private final File exportFile;
	private final IReportLog reportLog;
	private Writer stream = null;
	private StringTemplateGroup stg;
	
	public SbgnTextExportWriter(IMapDiagram ndom, File exportFile, IReportLog reportLog){
		this.ndom = ndom;
		this.exportFile = exportFile;
		this.reportLog = reportLog;
	}
	
	public File getFile() {
		return this.exportFile;
	}

	public IMapDiagram getNdom() {
		return this.ndom;
	}

	public IReportLog getReportLog(){
		return this.reportLog;
	}
	
	public void writeExport() {
		try {
			this.stream = new BufferedWriter(new FileWriter(this.exportFile));
			Reader r = new InputStreamReader(this.getClass().getResourceAsStream(BIOPEPA_TEMPLATE_FILE_NAME));
			stg = new StringTemplateGroup(r);
			writeHeader();
			writeMapStart();
			writeNodes();
			writeMapEnd();
		}
		catch (IOException e) {
			this.reportLog.reportError("An I/O error was detected: ", e);
		}
		finally{
			try {
				if(this.stream != null){
					this.stream.close();
				}
				this.stream = null;
			} catch (IOException e) {
				this.reportLog.reportError("An I/O error was detected: ", e);
			}
		}
	}

	private void writeMapEnd() throws IOException {
		StringTemplate t = stg.getInstanceOf("mapEnd");
		this.stream.append(t.toString());
	}

	private void writeMapStart() throws IOException {
		StringTemplate t = stg.getInstanceOf("mapStart");
		t.setAttribute("name", this.ndom.getName());
		this.stream.append(t.toString());
	}

	private void writeHeader() throws IOException{
		StringTemplate t = stg.getInstanceOf("header");
        this.stream.write(t.toString());
	}
	
	private void writeNodes() throws IOException{
		NdomVisitor visitor = new NdomVisitor();
		this.ndom.visit(visitor);
	}
	
	
	private void writeEpn(String id, String name, String type, int cmptId, int cardinality, int count){
		try {
			StringTemplate t = stg.getInstanceOf("epn");
			t.setAttribute("id", id);
			t.setAttribute("name", name);
			t.setAttribute("type", type);
			t.setAttribute("compartment", cmptId);
			t.setAttribute("cardinality", cardinality);
			t.setAttribute("count", count);
			stream.write(t.toString());
		} catch (IOException e) {
			reportLog.reportError("IOException thown", e);
		}
	}
	
	
	private void writeProcess(String id, String type, String propensityFunction){
		try {
			StringTemplate t = stg.getInstanceOf("process");
			t.setAttribute("id", id);
			t.setAttribute("type", type);
			t.setAttribute("propFunc", propensityFunction);
			stream.write(t.toString());
		} catch (IOException e) {
			reportLog.reportError("IOException thown", e);
		}
	}
	
	
	private void writeConsumptionArc(String id, int stoichiometry, IConsumeableNode epn, IProcessNode process){
		try {
			StringTemplate t = stg.getInstanceOf("fluxArc");
			t.setAttribute("id", id);
			t.setAttribute("type", "Consumption");
			t.setAttribute("stoich", stoichiometry);
			t.setAttribute("epnId", epn.getIdentifier());
			t.setAttribute("processId", process.getIdentifier());
			stream.write(t.toString());
		} catch (IOException e) {
			reportLog.reportError("IOException thown", e);
		}
	}
	
	
	private void writeProductionArc(String id, int stoichiometry, IProduceableNode epn, IProcessNode process){
		try {
			StringTemplate t = stg.getInstanceOf("fluxArc");
			t.setAttribute("id", id);
			t.setAttribute("type", "Production");
			t.setAttribute("stoich", stoichiometry);
			t.setAttribute("epnId", epn.getIdentifier());
			t.setAttribute("processId", process.getIdentifier());
			stream.write(t.toString());
		} catch (IOException e) {
			reportLog.reportError("IOException thown", e);
		}
	}
	
	
	private void writeModulationArc(String id, ModulatingArcType type, IModulatingNode epn, IModulateableNode process){
		try {
			StringTemplate t = stg.getInstanceOf("modulationArc");
			t.setAttribute("id", id);
			t.setAttribute("type", Ndom2SbgnTextTypeMapper.getInstance().getBiopepaModulationType(type));
			t.setAttribute("epnId", epn.getIdentifier());
			t.setAttribute("processId", process.getIdentifier());
			stream.write(t.toString());
		} catch (IOException e) {
			reportLog.reportError("IOException thown", e);
		}
	}
	
	private String getIdentifierName(IPdElement element){
		String retVal = element.getAsciiName();
		if(retVal == null || retVal.length() == 0){
			StringBuilder buf = new StringBuilder(ELEMENT_NAME);
			buf.append(element.getIdentifier());
			retVal = buf.toString();
		}
		return retVal;
	}
	

	
	private class NdomVisitor implements IPdElementVisitor{

		private static final int DEFAULT_CARDINALITY = 1;
		private static final String DEFAULT_NAME = "";
		private static final int SINK_MOL_COUNT = -1;
		private static final int SOURCE_MOL_COUNT = -1;

		public void visitCompartment(ICompartmentNode node) {
			try{
				StringTemplate t = stg.getInstanceOf("compartment");
				t.setAttribute("id", getIdentifierName(node));
				t.setAttribute("name", node.getName());
				stream.write(t.toString());
			}
			catch(IOException ex){
				reportLog.reportError("IOException thown", ex);
			}
		}

		public void visitComplex(IComplexNode node) {
			writeEpn(getIdentifierName(node), node.getName(), "Complex", node.getCompartment().getIdentifier(),
					node.getCardinality(), node.getEntityCount());
		}

		public void visitConsumptionArc(IConsumptionArc pdElement) {
			writeConsumptionArc(getIdentifierName(pdElement), pdElement.getStoichiometry(), pdElement.getConsumableNode(), pdElement.getProcess());
		}

		public void visitLogicArc(ILogicArc pdElement) {
			reportLog.reportWarning("logical arcs are not supported and will not be exported into this format");
		}

		public void visitLogicalOperator(ILogicOperatorNode pdElement) {
			reportLog.reportWarning("logical operators are not supported and will not be exported into this format");
		}

		public void visitMacromolecule(IMacromoleculeNode node) {
			writeEpn(getIdentifierName(node), node.getName(), "Macromolecule", node.getCompartment().getIdentifier(),
					node.getCardinality(), node.getEntityCount());
		}

		public void visitModulationArc(IModulationArc pdElement) {
			writeModulationArc(getIdentifierName(pdElement), pdElement.getType(), pdElement.getModulator(), pdElement.getModulatedNode());
		}

		public void visitNucleicAcidFeature(INucleicAcidFeatureNode node) {
			writeEpn(getIdentifierName(node), node.getName(), "NucleicAcidFeature", node.getCompartment().getIdentifier(),
					node.getCardinality(), node.getEntityCount());
		}

		public void visitPerturbingAgent(IPerturbationNode node) {
			writeEpn(getIdentifierName(node), node.getName(), "PerturbingAgent", node.getCompartment().getIdentifier(),
					DEFAULT_CARDINALITY, node.getEntityCount());
		}

		public void visitPhenotypeNode(IPhenotypeNode pdElement) {
			writeProcess(getIdentifierName(pdElement), "Phenotype", pdElement.getRateFunction());
		}

		public void visitProcess(IProcessNode pdElement) {
			ProcessNodeType processType = pdElement.getProcessType();
			Ndom2SbgnTextTypeMapper.getInstance().getBiopepaProcessType(processType);
			writeProcess(getIdentifierName(pdElement), "Process", pdElement.getFwdRateEquation());
		}

		public void visitProductionArc(IProductionArc pdElement) {
			writeProductionArc(getIdentifierName(pdElement), pdElement.getStoichiometry(), pdElement.getProductionNode(), pdElement.getProcess());
		}

		public void visitSimpleChemical(ISimpleChemicalNode pdElement) {
			writeEpn(getIdentifierName(pdElement), pdElement.getName(), "SimpleChemical", pdElement.getCompartment().getIdentifier(),
					pdElement.getCardinality(), pdElement.getEntityCount());
		}

		public void visitSinkNode(ISinkNode pdElement) {
			writeEpn(getIdentifierName(pdElement), DEFAULT_NAME, "Sink", pdElement.getCompartment().getIdentifier(),
					DEFAULT_CARDINALITY, SINK_MOL_COUNT);
		}

		public void visitSource(ISourceNode pdElement) {
			writeEpn(getIdentifierName(pdElement), DEFAULT_NAME, "Source", pdElement.getCompartment().getIdentifier(),
					DEFAULT_CARDINALITY, SOURCE_MOL_COUNT);
		}

		public void visitStateDescription(IStateDescription pdElement) {
		}

		public void visitUnitOfInformation(IUnitOfInformation pdElement) {
		}

		public void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement) {
			writeEpn(getIdentifierName(pdElement), pdElement.getName(), "UnspecifiedEntity", pdElement.getCompartment().getIdentifier(),
					DEFAULT_CARDINALITY, pdElement.getEntityCount());
		}
		
	}
}

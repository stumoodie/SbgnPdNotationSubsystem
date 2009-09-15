package org.pathwayeditor.notations.sbgnpd.ndom;


public interface IPdElementVisitor {

	void visitUnspecifiedEntity(IUnspecifiedEntityNode pdElement);
	
	void visitSimpleChemical(ISimpleChemicalNode pdElement);
	
	void visitMacromolecule(IMacromoleculeNode pdElement);
	
	void visitComplex(IComplexNode pdElement);
	
	void visitSinkNode(ISinkNode pdElement);
	
	void visitSource(ISourceNode pdElement);
	
	void visitPerturbingAgent(IPerturbationNode pdElement);
	
	void visitProcess(IProcessNode pdElement);
	
	void visitLogicalOperator(ILogicOperatorNode pdElement);
	
	void visitNucleicAcidFeature(INucleicAcidFeatureNode pdElement);
	
	void visitPhenotypeNode(IPhenotypeNode pdElement);
	
	void visitUnitOfInformation(IUnitOfInformation pdElement);
	
	void visitStateDescription(IStateDescription pdElement);
	
	void visitProductionArc(IProductionArc pdElement);
	
	void visitConsumptionArc(IConsumptionArc pdElement);
	
	void visitModulationArc(IModulationArc pdElement);

	void visitLogicArc(ILogicArc pdElement);
	
	void visitCompartment(ICompartmentNode pdElement);
	
	void visitSubmapNode(ISubMapNode pdElement);
	
	void visitTagNode(ITagNode pdElement);
	
	void visitSubmapTerminalNode(ISubMapTerminalNode pdElement);
}

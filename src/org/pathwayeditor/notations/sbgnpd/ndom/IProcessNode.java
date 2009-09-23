package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

public interface IProcessNode extends IPdElement, IModulateableNode, IConceptualProcessNode {
	enum SidednessType { LHS, RHS };
	
	
	ProcessNodeType getProcessType();
	
	Set<IFluxArc> getLhs();
	
	Set<IFluxArc> getRhs();
	
	IConsumptionArc createConsumptionArc(int identifier, IConsumeableNode consumeable);
	
	IProductionArc createProductionArc(int identifier, IProduceableNode produceable, SidednessType type);
	
	String getFwdRateEquation();
	
	void setFwdRateEquation(String rateFunction);
	
	String getRevRateEquation();
	
	void setRevRateEquation(String rateFunction);
}

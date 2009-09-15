package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;

public interface IProcessNode extends IPdElement, IModulateableNode, IConceptualProcessNode {
	enum SidednessType { LHS, RHS };
	
	
	ProcessNodeType getProcessType();
	
	Set<IFluxArc> getLhs();
	
	Set<IFluxArc> getRhs();
	
	Set<IModulationArc> getModulationArcs();
	
	IConsumptionArc createConsumptionArc(ILinkEdge linkEdge, IEntityPoolNode consumeable);
	
	IProductionArc createProductionArc(ILinkEdge linkEdge, IEntityPoolNode produceable, SidednessType type);
	
	IModulationArc createModulationArc(ILinkEdge linkEdge, ModulatingArcType type, IModulatingNode modulator);
}

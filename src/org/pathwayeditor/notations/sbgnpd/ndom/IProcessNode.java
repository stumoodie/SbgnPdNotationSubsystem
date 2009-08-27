package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;

public interface IProcessNode extends IBasicEntityNode, IModulateableNode {
	enum SidednessType { INPUT, OUTPUT };
	
	
	ProcessNodeType getProcessType();
	
	Set<IFluxArc> getInputs();
	
	Set<IFluxArc> getOutputs();
	
	IConsumptionArc createConsumptionArc(ILinkEdge linkEdge, IConsumeableNode consumeable);
	
	IConsumptionArc createProductionArc(ILinkEdge linkEdge, IProduceableNode produceable, SidednessType type);
	
	IModulationArc createModulationArc(ILinkEdge linkEdge, ModulatingArcType type, IModulatingNode modulator);
}

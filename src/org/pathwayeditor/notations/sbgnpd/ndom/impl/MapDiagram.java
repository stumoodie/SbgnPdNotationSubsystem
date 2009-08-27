package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ILogicOperatorNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IProcessNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.ISubMapNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;
import org.pathwayeditor.notations.sbgnpd.ndom.LogicOperatorType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class MapDiagram implements IMapDiagram {
	private final Set<IPhenotypeNode> phenotypes;
	private final Set<ICompartmentNode> compartments;
	private final Set<IPerturbationNode> pertrubations;
	private final Set<ISubMapNode> submapNodes;
	private final String name;
	
	public MapDiagram(ICanvas canvas){
		this.name = canvas.getName();
		this.compartments = new HashSet<ICompartmentNode>();
		this.pertrubations = new HashSet<IPerturbationNode>();
		this.phenotypes = new HashSet<IPhenotypeNode>();
		this.submapNodes = new HashSet<ISubMapNode>();
	}
	
	
	public ICompartmentNode createCompartmentNode(IShapeNode node) {
		CompartmentNode retVal = new CompartmentNode(this, node);
		if(this.compartments.contains(retVal)){
			throw new IllegalArgumentException("A compartment with this name already exists. name=" + name);
		}
		this.compartments.add(retVal);
		return retVal;
	}

	public ILogicOperatorNode createLogicOperatorNode(IShapeNode shapeNode, LogicOperatorType type) {
		LogicOperatorNode retVal = new LogicOperatorNode(shapeNode, type);
		return retVal;
	}

	public IPerturbationNode createPerturbationNode(IShapeNode shapeNode) {
		PerturbationNode retVal = new PerturbationNode(shapeNode);
		if(this.pertrubations.contains(retVal)){
			throw new IllegalArgumentException("A pertrubation with this name already exists: name=" + name);
		}
		return retVal;
	}

	public IPhenotypeNode createPhenotypeNode(IShapeNode shapeNode) {
		PhenotypeNode retVal = new PhenotypeNode(shapeNode);
		if(this.phenotypes.contains(retVal)){
			throw new IllegalArgumentException("A phenotype with this name already exists: name=" + name);
		}
		return retVal;
	}

	public IProcessNode createProcessNode(IShapeNode shapeNode, ProcessNodeType type) {
		ProcessNode retVal = new ProcessNode(shapeNode, type);
		return retVal;
	}

	public ISubMapDiagram createSubMapDiagram(ICanvas canvas) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public ISubMapNode createSubmapNode(IShapeNode shapeNode) {
		SubMapNode retVal = new SubMapNode(shapeNode);
		if(this.submapNodes.contains(retVal)){
			throw new IllegalArgumentException("SubMapNode with this name already exists: name=" + name);
		}
		return retVal;
	}

	public String getName() {
		return this.name;
	}


	public Iterator<ICompartmentNode> compartmentIterator() {
		return this.compartments.iterator();
	}


	public int numCompartments() {
		return this.compartments.size();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MapDiagram))
			return false;
		MapDiagram other = (MapDiagram) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	public ICompartmentNode createDefaultCompartmentNode() {
		ICompartmentNode retVal = new DefaultCompartmentNode(this);
		this.compartments.add(retVal);
		return retVal;
	}


	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}


	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}


	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(
			IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}


	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}


	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}


	public ICompartmentNode getCompartment() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getSboId() {
		// TODO Auto-generated method stub
		return null;
	}

}

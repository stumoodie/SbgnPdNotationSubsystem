package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.Iterator;

import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEpnContainer;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPerturbationNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public class ComplexNode extends StatefulEntityPoolNode implements IComplexNode {
	private static final String SBO_TERM = "SBO:000999";
	private final EpnContainer epnContainer;
	
	protected ComplexNode(IEpnContainer compartmentNode, int identifier, String asciiName) {
		super(compartmentNode, identifier, SBO_TERM, asciiName);
		this.epnContainer = new EpnContainer(this, compartmentNode.getCompartment());
	}

	public boolean containsEntityPoolNode(int identifier) {
		return epnContainer.containsEntityPoolNode(identifier);
	}

	public IComplexNode createComplexNode(int identifier, String asciiName) {
		return epnContainer.createComplexNode(identifier, asciiName);
	}

	public IMacromoleculeNode createMacromoleculeNode(int identifier, String name, String asciiName) {
		return epnContainer.createMacromoleculeNode(identifier, name, asciiName);
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(int identifier, String name, String asciiName) {
		return epnContainer.createNucleicAcidFeatureNode(identifier, name, asciiName);
	}

	public IPerturbationNode createPerturbationNode(int identifier, String name, String asciiName) {
		return epnContainer.createPerturbationNode(identifier, name, asciiName);
	}

	public ISimpleChemicalNode createSimpleChemicalNode(int identifier, String name, String asciiName) {
		return epnContainer.createSimpleChemicalNode(identifier, name, asciiName);
	}

	public ISinkNode createSinkNode(int identifier, String asciiName) {
		return epnContainer.createSinkNode(identifier, asciiName);
	}

	public ISourceNode createSourceNode(int identifier, String asciiName) {
		return epnContainer.createSourceNode(identifier, asciiName);
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(
			int identifier, String name, String asciiName) {
		return epnContainer.createUnspecifiedEntityNode(identifier, name, asciiName);
	}

	public IEntityPoolNode getEntityPoolNode(int identifier) {
		return epnContainer.getEntityPoolNode(identifier);
	}

	public Iterator<IEntityPoolNode> nodeIterator() {
		return epnContainer.nodeIterator();
	}

	protected void visitStatefuleEpnChild(IPdElementVisitor visitor) {
		visitor.visitComplex(this);
		this.epnContainer.visit(visitor);
	}

}

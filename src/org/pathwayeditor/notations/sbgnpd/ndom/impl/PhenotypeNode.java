package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Set;

import org.pathwayeditor.notations.sbgnpd.ndom.IAnnotateable;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulatingNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IModulationArc;
import org.pathwayeditor.notations.sbgnpd.ndom.IPdElementVisitor;
import org.pathwayeditor.notations.sbgnpd.ndom.IPhenotypeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;

public class PhenotypeNode extends PdElement implements IPhenotypeNode, IAnnotateable {
	private static final String SBO_TERM = "SBO:0000999";
	private final UnitOfInformationHandler handler;
	private final Set<IModulationArc> modulations;
	private final String name;
	
	public PhenotypeNode(int identifier, String name, String asciiName) {
		super(identifier, SBO_TERM, asciiName);
		handler = new UnitOfInformationHandler(this);
		this.modulations = new HashSet<IModulationArc>();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public IUnitOfInformation createUnitOfInformation(int identifier, String name) {
		return handler.createUnitOfInformation(identifier, name);
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		return handler.getUnitsOfInformation();
	}

	public void visit(IPdElementVisitor visitor) {
		visitor.visitPhenotypeNode(this);
	}

	public String getRateFunction() {
		return "";
	}

	public IModulationArc createModulationArc(int identifier, String asciiName, ModulatingArcType type, IModulatingNode modulator) {
		IModulationArc retVal = new ModulationArc(identifier, asciiName, modulator, this, type);
		this.modulations.add(retVal);
		return retVal;
	}


	public Set<IModulationArc> getModulationArcs() {
		return new HashSet<IModulationArc>(this.modulations);
	}



}

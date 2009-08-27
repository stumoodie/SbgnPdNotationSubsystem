package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IComplexNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IEntityPoolNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMacromoleculeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.INucleicAcidFeatureNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISimpleChemicalNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISinkNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ISourceNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnitOfInformation;
import org.pathwayeditor.notations.sbgnpd.ndom.IUnspecifiedEntityNode;

public abstract class AbstractCompartmentNode extends BasicEntityNode implements ICompartmentNode {
	private static final String SBO_TERM = "SBO:0000999";
	private static final String PREFIX = "Compartent";
	private final String name;
	private final IMapDiagram map;
	private final Set<IEntityPoolNode> epns;
	
	public AbstractCompartmentNode(IShapeNode shapeNode, IMapDiagram map, String name) {
		super(IdentifierFactory.getInstance().createIdentifier(PREFIX, shapeNode), SBO_TERM);
		this.name = name;
		this.map = map;
		this.epns = new HashSet<IEntityPoolNode>();
	}

	public AbstractCompartmentNode(IMapDiagram map, String name) {
		this(null, map, name);
	}
	
	public IComplexNode createComplexNode(IShapeNode shapeNode) {
		IComplexNode retVal = new ComplexNode(this, shapeNode);
		this.epns.add(retVal);
		return retVal;
	}

	public IMacromoleculeNode createMacromoleculeNode(IShapeNode shapeNode) {
		IMacromoleculeNode retVal = new MacromoleculeNode(this, shapeNode);
		this.epns.add(retVal);
		return retVal;
	}

	public INucleicAcidFeatureNode createNucleicAcidFeatureNode(IShapeNode shapeNode) {
		return new NucleicAcidFeatureNode(this, shapeNode);
	}

	public ISimpleChemicalNode createSimpleChemicalNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISinkNode createSinkNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISourceNode createSourceNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public IUnspecifiedEntityNode createUnspecifiedEntityNode(IShapeNode shapeNode) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return this.name;
	}

	public Set<IUnitOfInformation> getUnitsOfInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<IEntityPoolNode> nodeIterator() {
		return this.epns.iterator();
	}

	public IMapDiagram getMapDiagram() {
		return this.map;
	}

}

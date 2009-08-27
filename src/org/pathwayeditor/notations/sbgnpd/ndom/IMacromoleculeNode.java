package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IMacromoleculeNode extends IStatefulEntityPoolNode, IMultimerEntityPoolNode {

	void setMaterialType(MaterialType type);
	
	MaterialType getMaterialType();
	
}

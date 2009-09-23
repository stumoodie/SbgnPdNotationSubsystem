package org.pathwayeditor.notations.sbgnpd.ndom;

public interface IMacromoleculeNode extends IStatefulEntityPoolNode {

	void setMaterialType(MaterialType type);
	
	MaterialType getMaterialType();
	
}

package org.pathwayeditor.notations.sbgnpd.ndom;

public final class MaterialType implements IControlledVocabulary {
	private final String namel;
	private String label;
	private String sboTerm;
	
	public MaterialType(String name, String label, String sboTerm){
		this.namel = name;
		this.label = label;
		this.sboTerm = sboTerm;
	}
	
	public String getSboTerm(){
		return this.sboTerm;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public String getName(){
		return this.namel;
	}
}

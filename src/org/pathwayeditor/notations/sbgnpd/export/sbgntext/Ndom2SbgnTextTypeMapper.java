package org.pathwayeditor.notations.sbgnpd.export.sbgntext;

import java.util.HashMap;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class Ndom2SbgnTextTypeMapper {
	private static Ndom2SbgnTextTypeMapper anInstance = null;
	private final Map<Enum<?>, String> lookup;
	
	public static Ndom2SbgnTextTypeMapper getInstance() {
		if(anInstance == null){
			anInstance = new Ndom2SbgnTextTypeMapper();
		}
		return anInstance;
	}

	private Ndom2SbgnTextTypeMapper(){
		this.lookup = new HashMap<Enum<?>, String>();
		this.lookup.put(ProcessNodeType.ASSOCIATION, "Association");
		this.lookup.put(ProcessNodeType.DISSOCIATION, "Dissociation");
		this.lookup.put(ProcessNodeType.STANDARD, "Process");
		this.lookup.put(ProcessNodeType.UNCERTAIN_PROCESS, "Uncertain");
		this.lookup.put(ProcessNodeType.OMITTED_PROCESS, "Omitted");
		this.lookup.put(ModulatingArcType.CATALYSIS, "Catalysis");
		this.lookup.put(ModulatingArcType.INHIBITION, "Inhibition");
		this.lookup.put(ModulatingArcType.MODULATION, "Modulation");
		this.lookup.put(ModulatingArcType.NECESSARY_STIMULATION, "NecessaryStmulation");
		this.lookup.put(ModulatingArcType.STIMULATION, "Stimulation");
	}
	
	public String getBiopepaProcessType(ProcessNodeType processType) {
		return this.lookup.get(processType);
	}

	public Object getBiopepaModulationType(ModulatingArcType type) {
		return this.lookup.get(type);
	}

}

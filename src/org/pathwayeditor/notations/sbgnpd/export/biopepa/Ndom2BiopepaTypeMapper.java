package org.pathwayeditor.notations.sbgnpd.export.biopepa;

import java.util.HashMap;
import java.util.Map;

import org.pathwayeditor.notations.sbgnpd.ndom.ModulatingArcType;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;

public class Ndom2BiopepaTypeMapper {
	private static Ndom2BiopepaTypeMapper anInstance = null;
	private final Map<Enum<?>, String> lookup;
	
	public static Ndom2BiopepaTypeMapper getInstance() {
		if(anInstance == null){
			anInstance = new Ndom2BiopepaTypeMapper();
		}
		return anInstance;
	}

	private Ndom2BiopepaTypeMapper(){
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

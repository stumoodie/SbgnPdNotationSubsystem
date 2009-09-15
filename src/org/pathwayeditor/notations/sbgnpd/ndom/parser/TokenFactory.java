package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingElement;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.ILinkEdge;
import org.pathwayeditor.businessobjects.drawingprimitives.ITypedDrawingNode;
import org.pathwayeditor.businessobjects.typedefn.IObjectType;
import org.pathwayeditor.notations.sbgnpd.ndom.parser.IToken.TreeTokenType;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;

public class TokenFactory {
	private final SbgnPdNotationSyntaxService syntaxService;
	
	public TokenFactory(SbgnPdNotationSyntaxService syntaxService){
		this.syntaxService = syntaxService;
	}
	
	public IToken createRootNode(){
		return new IToken() {
			private final IDrawingNode element = null;
			
			public IDrawingElement getElement() {
				return element;
			}

			public TreeTokenType getType() {
				return TreeTokenType.NODE_ROOT;
			}

			@SuppressWarnings("unchecked")
			public IDrawingNode getTypedElement() {
				return element;
			}
			
		};
	}
	
	public IToken createToken(ITypedDrawingNode shapeNode){
		IObjectType objectType = shapeNode.getAttribute().getObjectType();
		TreeTokenType type = TreeTokenType.MAP_DIAGRAM;
		if(objectType.getUniqueId() == this.syntaxService.getCompartment().getUniqueId()){
			type = TreeTokenType.COMPARTMENT;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getMacromolecule().getUniqueId()){
			type = TreeTokenType.MACROMOLECULE;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getComplex().getUniqueId()){
			type = TreeTokenType.COMPLEX;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getSimpleChem().getUniqueId()){
			type = TreeTokenType.SIMPLE_CHEMICAL;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getOmittedProcess().getUniqueId()){
			type = TreeTokenType.OMITTED_PROCESS;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getUnspecEntity().getUniqueId()){
			type = TreeTokenType.UNSPECIFIED_ENTITY;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getGeneticUnit().getUniqueId()){
			type = TreeTokenType.NA_FEATURE;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getObservable().getUniqueId()){
			type = TreeTokenType.PHENOTYPE;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getPerturbation().getUniqueId()){
			type = TreeTokenType.PERTURBING_AGENT;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getState().getUniqueId()){
			type = TreeTokenType.STATE_DESCN;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getUnitOfInf().getUniqueId()){
			type = TreeTokenType.UOI;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getProcess().getUniqueId()){
			type = TreeTokenType.PROCESS;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getUncertainProcess().getUniqueId()){
			type = TreeTokenType.UNSPECIFIED_PROCESS;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getOmittedProcess().getUniqueId()){
			type = TreeTokenType.OMITTED_PROCESS;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getAssociation().getUniqueId()){
			type = TreeTokenType.ASSOCIATION;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getDissociation().getUniqueId()){
			type = TreeTokenType.DISOCCIATION;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getAndGate().getUniqueId()){
			type = TreeTokenType.AND_OP;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getOrGate().getUniqueId()){
			type = TreeTokenType.OR_OP;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getNotGate().getUniqueId()){
			type = TreeTokenType.NOT_OP;
		}
		return new BoToken(type, shapeNode);
	}

	public IToken createToken(ILinkEdge edge) {
		IObjectType objectType = edge.getAttribute().getObjectType();
		TreeTokenType type = TreeTokenType.EDGE_ROOT;
		if(objectType.getUniqueId() == this.syntaxService.getConsumption().getUniqueId()){
			type = TreeTokenType.CONSUMPTION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getProduction().getUniqueId()){
			type = TreeTokenType.PRODUCTION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getModulation().getUniqueId()){
			type = TreeTokenType.MODULATION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getStimulation().getUniqueId()){
			type = TreeTokenType.STIMULATION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getInhibition().getUniqueId()){
			type = TreeTokenType.INHIBITION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getTrigger().getUniqueId()){
			type = TreeTokenType.NECESSARY_STIMULATION_ARC;
		}
		else if(objectType.getUniqueId() == this.syntaxService.getCatalysis().getUniqueId()){
			type = TreeTokenType.CATALYSIS_ARC;
		}
		else{
			throw new RuntimeException("Cannot handle edge of unknown object type: " + edge.toString());
		}
		return new BoToken(type, edge);
	}
}

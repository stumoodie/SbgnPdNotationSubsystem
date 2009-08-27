package org.pathwayeditor.notations.sbgnpd.ndom.parser;

import java.util.Iterator;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
import org.pathwayeditor.businessobjects.drawingprimitives.IDrawingNode;
import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;
import org.pathwayeditor.notations.sbgnpd.ndom.ICompartmentNode;
import org.pathwayeditor.notations.sbgnpd.ndom.IMapDiagram;
import org.pathwayeditor.notations.sbgnpd.ndom.ProcessNodeType;
import org.pathwayeditor.notations.sbgnpd.ndom.impl.MapDiagram;
import org.pathwayeditor.notations.sbgnpd.services.SbgnPdNotationSyntaxService;

public class NdomBuilder {

	private IMapDiagram mapDiagram;
	private ICanvas canvas;


	public NdomBuilder(){
		
	}
	
	
	public void setCanvas(ICanvas canvas){
		this.canvas = canvas;
	}
	
	public ICanvas getCanvas() {
		return canvas;
	}


	public void buildNdom() {
		if (!this.isCanvasValid())
			throw new IllegalStateException(
					"The canvas is invalid and cannot be used to create the Ndom");

		this.mapDiagram = new MapDiagram(this.canvas);
		IModel canvasModel = this.canvas.getModel();
		Iterator<IDrawingNode> drawingNodeIter = canvasModel.getRootNode()
				.getSubModel().drawingNodeIterator();
		if (isCompartmentPresent()) {
			ICompartmentNode compartmentNode = this.mapDiagram
					.createDefaultCompartmentNode();
			readCompartment(compartmentNode, drawingNodeIter);
		} else {
			while (drawingNodeIter.hasNext()) {
				IDrawingNode node = drawingNodeIter.next();
				// exclude labels
				if (node instanceof IShapeNode) {
					IShapeNode shapeNode = (IShapeNode) node;
					int typeId = shapeNode.getAttribute().getObjectType().getUniqueId();
					SbgnPdNotationSyntaxService syntaxService = (SbgnPdNotationSyntaxService) this.canvas.getNotationSubsystem().getSyntaxService();
					if (syntaxService.getCompartment().getUniqueId() == typeId) {
						ICompartmentNode compartmentNode = this.mapDiagram.createCompartmentNode(shapeNode);
						readCompartment(compartmentNode, shapeNode.getSubModel().drawingNodeIterator());
					}
					else if(syntaxService.getProcess().getUniqueId() == typeId){
						//TODO: process process and other non-compartment objects.
					}
				}
			}
		}
		
	}
	
	public boolean isCompartmentPresent() {
		SbgnPdNotationSyntaxService syntaxService = (SbgnPdNotationSyntaxService)this.canvas.getNotationSubsystem().getSyntaxService();
		IModel canvasModel = this.canvas.getModel();
		Iterator<IDrawingNode> drawingNodeIter = canvasModel.getRootNode().getSubModel().drawingNodeIterator();
		boolean retVal = false;
		while(drawingNodeIter.hasNext() && !retVal){
			IDrawingNode node = drawingNodeIter.next();
			// exclude labels
			if(node instanceof IShapeNode){
				IShapeNode shapeNode = (IShapeNode)node; 
				int typeId = shapeNode.getAttribute().getObjectType().getUniqueId();
				if(syntaxService.getCompartment().getUniqueId() == typeId){
					retVal = true;
				}
			}
		}
		return retVal;
	}


	public boolean isCanvasValid() {
		//TODO: implement me!
		return true;
	}


	private void readCompartment(ICompartmentNode compartmentNode, Iterator<IDrawingNode> childIter) {
		SbgnPdNotationSyntaxService syntaxService = (SbgnPdNotationSyntaxService)this.canvas.getNotationSubsystem().getSyntaxService();
		Iterator<IDrawingNode> drawingNodeIter = childIter;
		while(drawingNodeIter.hasNext()){
			IDrawingNode node = drawingNodeIter.next();
			// exclude labels
			if(node instanceof IShapeNode){
				IShapeNode shapeNode = (IShapeNode)node;
				int typeId = shapeNode.getAttribute().getObjectType().getUniqueId();
				if(syntaxService.getMacromolecule().getUniqueId() == typeId){
					compartmentNode.createMacromoleculeNode(shapeNode);
				}
				else if(syntaxService.getComplex().getUniqueId() == typeId){
					compartmentNode.createComplexNode(shapeNode);
				}
				else if(syntaxService.getGeneticUnit().getUniqueId() == typeId){
					compartmentNode.createNucleicAcidFeatureNode(shapeNode);
				}
				else if(syntaxService.getSink().getUniqueId() == typeId){
					compartmentNode.createSinkNode(shapeNode);
				}
				else if(syntaxService.getSource().getUniqueId() == typeId){
					compartmentNode.createSourceNode(shapeNode);
				}
				else if(syntaxService.getSimpleChem().getUniqueId() == typeId){
					compartmentNode.createSimpleChemicalNode(shapeNode);
				}
				else if(syntaxService.getProcess().getUniqueId() == typeId){
					this.mapDiagram.createProcessNode(shapeNode, ProcessNodeType.STANDARD);
				}
				else if(syntaxService.getOmittedProcess().getUniqueId() == typeId){
					this.mapDiagram.createProcessNode(shapeNode, ProcessNodeType.OMITTED_PROCESS);
				}
				else if(syntaxService.getUnspecEntity().getUniqueId() == typeId){
					this.mapDiagram.createProcessNode(shapeNode, ProcessNodeType.UNCERTAIN_PROCESS);
				}
				else if(syntaxService.getAssociation().getUniqueId() == typeId){
					this.mapDiagram.createProcessNode(shapeNode, ProcessNodeType.ASSOCIATION);
				}
				else if(syntaxService.getDissociation().getUniqueId() == typeId){
					this.mapDiagram.createProcessNode(shapeNode, ProcessNodeType.DISOCCIATION);
				}
				else if(syntaxService.getCompartment().getUniqueId() == typeId){
					throw new IllegalStateException("Compartments cannot contain compartments");
				}
			}
		}
	}


	public IMapDiagram getMapDiagram(){
		return this.mapDiagram;
	}
}

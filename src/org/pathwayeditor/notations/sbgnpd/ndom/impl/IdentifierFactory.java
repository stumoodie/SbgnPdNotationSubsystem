package org.pathwayeditor.notations.sbgnpd.ndom.impl;

import org.pathwayeditor.businessobjects.drawingprimitives.IShapeNode;

public class IdentifierFactory {
	private static IdentifierFactory anInstance;
	
	public static IdentifierFactory getInstance(){
		if(anInstance == null){
			anInstance = new IdentifierFactory();
		}
		return anInstance;
	}
	
	
	private IdentifierFactory()	{
		
	}
	
	public String createIdentifier(String prefix, IShapeNode shapeNode){
		StringBuilder builder = new StringBuilder(prefix);
		builder.append(shapeNode.getAttribute().getCreationSerial());
		return builder.toString();
	}
	
}

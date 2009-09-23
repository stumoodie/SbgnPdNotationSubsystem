package org.pathwayeditor.notations.sbgnpd.ndom;

import java.util.Set;

public interface IAnnotateable {

	Set<IUnitOfInformation> getUnitsOfInformation();

	IUnitOfInformation createUnitOfInformation(int identifier, String name);
}

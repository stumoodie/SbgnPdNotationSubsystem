package org.pathwayeditor.notations.sbgnpd.services;

import java.util.Set;

import org.pathwayeditor.businessobjects.drawingprimitives.IModel;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationValidationService;
import org.pathwayeditor.businessobjects.notationsubsystem.IValidationReport;
import org.pathwayeditor.businessobjects.notationsubsystem.IValidationRuleDefinition;

public class SbgnPdValidationService implements INotationValidationService {

    private final INotationSubsystem serviceProvider;

    public SbgnPdValidationService(INotationSubsystem provider) {
        this.serviceProvider = provider;
    }

    public INotationSubsystem getServiceProvider() {
        return serviceProvider;
    }

    @Override
	public IModel getCanvasBeingValidated() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public IValidationReport getValidationReport() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public boolean hasBeenValidated() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public boolean hasWarnings() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public boolean isImplemented() {
        return false;
    }

    public boolean isValid() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public boolean isReadyToValidate() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
    public void setCanvasToValidate(IModel mapToValidate) {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public void validate() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public Set<IValidationRuleDefinition> getRules() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    @Override
	public INotation getNotation() {
        return this.serviceProvider.getNotation();
    }

    @Override
	public INotationSubsystem getNotationSubsystem() {
        return this.serviceProvider;
    }

}

package org.sbgn.epe.Sbgn1_1_0;

import java.util.List;

import org.pathwayeditor.businessobjects.drawingprimitives.ICanvas;
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

    public ICanvas getMapBeingValidated() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public IValidationReport getValidationReport() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public boolean hasMapBeenValidated() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public boolean hasWarnings() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public boolean isImplemented() {
        return false;
    }

    public boolean isMapValid() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public boolean isReadyToValidate() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public void setMapToValidate(ICanvas mapToValidate) {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public void validateMap() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public List<IValidationRuleDefinition> getRules() {
        throw new UnsupportedOperationException(
                "Validation service has not been implemented for this notation subsystem");
    }

    public INotation getNotation() {
        return this.serviceProvider.getNotation();
    }

    public INotationSubsystem getNotationSubsystem() {
        return this.serviceProvider;
    }

}

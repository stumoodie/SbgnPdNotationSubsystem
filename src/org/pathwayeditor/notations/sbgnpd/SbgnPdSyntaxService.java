package org.pathwayeditor.notations.sbgnpd;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.pathwayeditor.businessobjects.drawingprimitives.attributes.ConnectionRouter;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LineStyle;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.LinkEndDecoratorShape;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.PrimitiveShapeType;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.RGB;
import org.pathwayeditor.businessobjects.drawingprimitives.attributes.Size;
import org.pathwayeditor.businessobjects.drawingprimitives.properties.IPropertyDefinition;
import org.pathwayeditor.businessobjects.notationsubsystem.INotation;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSubsystem;
import org.pathwayeditor.businessobjects.notationsubsystem.INotationSyntaxService;
import org.pathwayeditor.businessobjects.typedefn.ILinkObjectType;
import org.pathwayeditor.businessobjects.typedefn.IObjectType;
import org.pathwayeditor.businessobjects.typedefn.IRootObjectType;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType;
import org.pathwayeditor.businessobjects.typedefn.ILinkObjectType.LinkEditableAttributes;
import org.pathwayeditor.businessobjects.typedefn.ILinkTerminusDefinition.LinkTermEditableAttributes;
import org.pathwayeditor.businessobjects.typedefn.IShapeObjectType.EditableShapeAttributes;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.FormattedTextPropertyDefinition;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.LinkObjectType;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.LinkTerminusDefinition;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.NumberPropertyDefinition;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.PlainTextPropertyDefinition;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.RootObjectType;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.ShapeObjectType;
import org.pathwayeditor.contextadapter.toolkit.ctxdefn.TextPropertyDefinition;

public class SbgnPdSyntaxService implements INotationSyntaxService {

    private static IPropertyDefinition reassignVal(IPropertyDefinition prop,
            String val, boolean isEdit, boolean isVis) {
        if (prop instanceof TextPropertyDefinition)
            return reassignVal((TextPropertyDefinition) prop, val, isEdit,
                    isVis);
        if (prop instanceof FormattedTextPropertyDefinition)
            return reassignVal((FormattedTextPropertyDefinition) prop, val,
                    isEdit, isVis);
        if (prop instanceof NumberPropertyDefinition)
            return reassignVal((NumberPropertyDefinition) prop, val, isEdit,
                    isVis);
        return prop;
    }

    private static TextPropertyDefinition reassignVal(
            TextPropertyDefinition prop, String val, boolean isEdit,
            boolean isVis) {
        TextPropertyDefinition newP = new PlainTextPropertyDefinition(prop
                .getName(), val, (prop.isVisualisable() | isVis), (prop
                .isEditable() & isEdit));
        return newP;
    }

    private static FormattedTextPropertyDefinition reassignVal(
            FormattedTextPropertyDefinition prop, String val, boolean isEdit,
            boolean isVis) {
        FormattedTextPropertyDefinition newP = new FormattedTextPropertyDefinition(
                prop.getName(), val, (prop.isVisualisable() | isVis), (prop
                        .isEditable() & isEdit));
        return newP;
    }

    private static NumberPropertyDefinition reassignVal(
            NumberPropertyDefinition prop, String val, boolean isEdit,
            boolean isVis) {
        NumberPropertyDefinition newP = new NumberPropertyDefinition(prop
                .getName(), val, (prop.isVisualisable() | isVis), (prop
                .isEditable() & isEdit));
        return newP;
    }

    private final Map<Integer, IShapeObjectType> shapeSet = new HashMap<Integer, IShapeObjectType>();
    private final Map<Integer, ILinkObjectType> linkSet = new HashMap<Integer, ILinkObjectType>();
    // private final Set <IPropertyDefinition> propSet=new
    // HashSet<IPropertyDefinition>();

    private RootObjectType rmo;
    // shapes
    private ShapeObjectType State;
    private ShapeObjectType UnitOfInf;
    private ShapeObjectType CloneMarker;
    private ShapeObjectType Compartment;
    private ShapeObjectType Complex;
    private ShapeObjectType GeneticUnit;
    private ShapeObjectType Macromolecule;
    private ShapeObjectType MMultimer;
    private ShapeObjectType SimpleChem;
    private ShapeObjectType SMultimer;
    private ShapeObjectType UnspecEntity;
    private ShapeObjectType Sink;
    private ShapeObjectType Source;
    private ShapeObjectType Perturbation;
    private ShapeObjectType Observable;
    private ShapeObjectType Submap;
    private ShapeObjectType Interface;
    private ShapeObjectType Tag;
    private ShapeObjectType Process;
    private ShapeObjectType OmittedProcess;
    private ShapeObjectType UncertainProcess;
    private ShapeObjectType Association;
    private ShapeObjectType Dissociation;
    private ShapeObjectType AndGate;
    private ShapeObjectType OrGate;
    private ShapeObjectType NotGate;

    // links
    private LinkObjectType Consumption;
    private LinkObjectType Production;
    private LinkObjectType Modulation;
    private LinkObjectType Stimulation;
    private LinkObjectType Catalysis;
    private LinkObjectType Inhibition;
    private LinkObjectType Trigger;
    private LinkObjectType LogicArc;
    private LinkObjectType EquivalenceArc;

    private INotationSubsystem serviceProvider;

    public INotationSubsystem getServiceProvider() {
        return serviceProvider;
    }

    public SbgnPdSyntaxService(INotationSubsystem serviceProvider) {
        this.serviceProvider = serviceProvider;
        createRMO();
        // shapes
        this.State = new ShapeObjectType(this, 10, "State");
        createState();
        this.UnitOfInf = new ShapeObjectType(this, 11, "UnitOfInf");
        createUnitOfInf();
        this.CloneMarker = new ShapeObjectType(this, 12, "CloneMarker");
        createCloneMarker();
        this.Compartment = new ShapeObjectType(this, 13, "Compartment");
        createCompartment();
        this.Complex = new ShapeObjectType(this, 14, "Complex");
        createComplex();
        this.GeneticUnit = new ShapeObjectType(this, 15, "GeneticUnit");
        createGeneticUnit();
        this.Macromolecule = new ShapeObjectType(this, 16, "Macromolecule");
        createMacromolecule();
        this.MMultimer = new ShapeObjectType(this, 17, "MMultimer");
        createMMultimer();
        this.SimpleChem = new ShapeObjectType(this, 18, "SimpleChem");
        createSimpleChem();
        this.SMultimer = new ShapeObjectType(this, 19, "SMultimer");
        createSMultimer();
        this.UnspecEntity = new ShapeObjectType(this, 110, "UnspecEntity");
        createUnspecEntity();
        this.Sink = new ShapeObjectType(this, 111, "Sink");
        createSink();
        this.Source = new ShapeObjectType(this, 112, "Source");
        createSource();
        this.Perturbation = new ShapeObjectType(this, 113, "Perturbation");
        createPerturbation();
        this.Observable = new ShapeObjectType(this, 114, "Observable");
        createObservable();
        this.Submap = new ShapeObjectType(this, 115, "Submap");
        createSubmap();
        this.Interface = new ShapeObjectType(this, 116, "Interface");
        createInterface();
        this.Tag = new ShapeObjectType(this, 117, "Tag");
        createTag();
        this.Process = new ShapeObjectType(this, 118, "Process");
        createTransition();
        this.OmittedProcess = new ShapeObjectType(this, 119, "OmittedProcess");
        createOmittedProcess();
        this.UncertainProcess = new ShapeObjectType(this, 120,
                "UncertainProcess");
        createUncertainProcess();
        this.Association = new ShapeObjectType(this, 121, "Association");
        createAssociation();
        this.Dissociation = new ShapeObjectType(this, 122, "Dissociation");
        createDissociation();
        this.AndGate = new ShapeObjectType(this, 123, "AndGate");
        createAndGate();
        this.OrGate = new ShapeObjectType(this, 124, "OrGate");
        createOrGate();
        this.NotGate = new ShapeObjectType(this, 125, "NotGate");
        createNotGate();

        defineParentingRMO();
        // shapes parenting
        defineParentingState();
        defineParentingUnitOfInf();
        defineParentingCloneMarker();
        defineParentingCompartment();
        defineParentingComplex();
        defineParentingGeneticUnit();
        defineParentingMacromolecule();
        defineParentingMMultimer();
        defineParentingSimpleChem();
        defineParentingSMultimer();
        defineParentingUnspecEntity();
        defineParentingSink();
        defineParentingSource();
        defineParentingPerturbation();
        defineParentingObservable();
        defineParentingSubmap();
        defineParentingInterface();
        defineParentingTag();
        defineParentingProcess();
        defineParentingOmittedProcess();
        defineParentingUncertainProcess();
        defineParentingAssociation();
        defineParentingDissociation();
        defineParentingAndGate();
        defineParentingOrGate();
        defineParentingNotGate();

        // links
        this.Consumption = new LinkObjectType(this, 20, "Consumption");
        createConsumption();
        this.Production = new LinkObjectType(this, 21, "Production");
        createProduction();
        this.Modulation = new LinkObjectType(this, 22, "Modulation");
        createModulation();
        this.Stimulation = new LinkObjectType(this, 23, "Stimulation");
        createStimulation();
        this.Catalysis = new LinkObjectType(this, 24, "Catalysis");
        createCatalysis();
        this.Inhibition = new LinkObjectType(this, 25, "Inhibition");
        createInhibition();
        this.Trigger = new LinkObjectType(this, 26, "Trigger");
        createTrigger();
        this.LogicArc = new LinkObjectType(this, 27, "LogicArc");
        createLogicArc();
        this.EquivalenceArc = new LinkObjectType(this, 28, "EquivalenceArc");
        createEquivalenceArc();

        // shape set
        this.shapeSet.put(State.getUniqueId(), State);
        this.shapeSet.put(UnitOfInf.getUniqueId(), UnitOfInf);
        this.shapeSet.put(CloneMarker.getUniqueId(), CloneMarker);
        this.shapeSet.put(Compartment.getUniqueId(), Compartment);
        this.shapeSet.put(Complex.getUniqueId(), Complex);
        this.shapeSet.put(GeneticUnit.getUniqueId(), GeneticUnit);
        this.shapeSet.put(Macromolecule.getUniqueId(), Macromolecule);
        this.shapeSet.put(MMultimer.getUniqueId(), MMultimer);
        this.shapeSet.put(SimpleChem.getUniqueId(), SimpleChem);
        this.shapeSet.put(SMultimer.getUniqueId(), SMultimer);
        this.shapeSet.put(UnspecEntity.getUniqueId(), UnspecEntity);
        this.shapeSet.put(Sink.getUniqueId(), Sink);
        this.shapeSet.put(Source.getUniqueId(), Source);
        this.shapeSet.put(Perturbation.getUniqueId(), Perturbation);
        this.shapeSet.put(Observable.getUniqueId(), Observable);
        this.shapeSet.put(Submap.getUniqueId(), Submap);
        this.shapeSet.put(Interface.getUniqueId(), Interface);
        this.shapeSet.put(Tag.getUniqueId(), Tag);
        this.shapeSet.put(Process.getUniqueId(), Process);
        this.shapeSet.put(OmittedProcess.getUniqueId(), OmittedProcess);
        this.shapeSet.put(UncertainProcess.getUniqueId(), UncertainProcess);
        this.shapeSet.put(Association.getUniqueId(), Association);
        this.shapeSet.put(Dissociation.getUniqueId(), Dissociation);
        this.shapeSet.put(AndGate.getUniqueId(), AndGate);
        this.shapeSet.put(OrGate.getUniqueId(), OrGate);
        this.shapeSet.put(NotGate.getUniqueId(), NotGate);

        // link set
        this.linkSet.put(Consumption.getUniqueId(), Consumption);
        this.linkSet.put(Production.getUniqueId(), Production);
        this.linkSet.put(Modulation.getUniqueId(), Modulation);
        this.linkSet.put(Stimulation.getUniqueId(), Stimulation);
        this.linkSet.put(Catalysis.getUniqueId(), Catalysis);
        this.linkSet.put(Inhibition.getUniqueId(), Inhibition);
        this.linkSet.put(Trigger.getUniqueId(), Trigger);
        this.linkSet.put(LogicArc.getUniqueId(), LogicArc);
        this.linkSet.put(EquivalenceArc.getUniqueId(), EquivalenceArc);
    }

    public INotation getNotation() {
        return this.serviceProvider.getNotation();
    }

    private void createRMO() {
        this.rmo = new RootObjectType(-10, this);
    }

    private void defineParentingRMO() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.CloneMarker, this.Compartment,
                this.Complex, this.GeneticUnit, this.Macromolecule,
                this.MMultimer, this.SimpleChem, this.SMultimer,
                this.UnspecEntity, this.Sink, this.Source, this.Perturbation,
                this.Observable, this.Submap, this.Interface, this.Tag,
                this.Process, this.OmittedProcess, this.UncertainProcess,
                this.Association, this.Dissociation, this.AndGate, this.OrGate,
                this.NotGate }));
        set.removeAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf }));
        for (IShapeObjectType child : set) {
            this.rmo.getParentingRules().addChild(child);
        }

    }

    private void createState() {
        this.State.setDescription("State variable");// ment to be
                                                    // TypeDescription rather
        this.State.getDefaultAttributes().setName("");
        this.State.getDefaultAttributes().setDescription("");
        this.State.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.State.getDefaultAttributes().setLineWidth(1);
        this.State.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.State.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.State.getDefaultAttributes().setSize(new Size(25, 25));
        this.State.getDefaultAttributes().setFillColour(new RGB(240, 247, 255));
        this.State.getDefaultAttributes().setUrl("");
        final EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
        IPropertyDefinition label = reassignVal(getPropLabel(),
                "State variable", true, false);
        State.getDefaultAttributes().addPropertyDefinition(label);

    }

    private void defineParentingState() {
        this.State.getParentingRules().clear();
    }

    public ShapeObjectType getState() {
        return this.State;
    }

    private void createUnitOfInf() {
        this.UnitOfInf.setDescription("Auxiliary information box");// ment to be
                                                                   // TypeDescription
                                                                   // rather

        this.UnitOfInf.getDefaultAttributes().setName("");
        this.UnitOfInf.getDefaultAttributes().setDescription("");
        this.UnitOfInf.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.UnitOfInf.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.UnitOfInf.getDefaultAttributes().setLineWidth(1);
        this.UnitOfInf.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.UnitOfInf.getDefaultAttributes().setSize(new Size(65, 25));
        this.UnitOfInf.getDefaultAttributes().setFillColour(
                new RGB(240, 247, 255));
        this.UnitOfInf.getDefaultAttributes().setUrl("");
        final EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
        IPropertyDefinition Label = reassignVal(getPropLabel(),
                "Unit of Information", true, false);
        UnitOfInf.getDefaultAttributes().addPropertyDefinition(Label);

    }

    private void defineParentingUnitOfInf() {
        this.UnitOfInf.getParentingRules().clear();
    }

    public ShapeObjectType getUnitOfInf() {
        return this.UnitOfInf;
    }

    private void createCloneMarker() {
        this.CloneMarker.setDescription("Mark the node that have been cloned");
        this.CloneMarker.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.CloneMarker.getDefaultAttributes().setLineWidth(1);
        this.CloneMarker.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.CloneMarker.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.BOTTOM_ROUNDED_RECTANGLE);
        this.CloneMarker.getDefaultAttributes().setFillColour(new RGB(0, 0, 0));
        this.CloneMarker.getDefaultAttributes().setSize(new Size(40, 20));
        this.CloneMarker.getDefaultAttributes().setUrl("");
        this.CloneMarker.getDefaultAttributes().setName("");
        this.CloneMarker.getDefaultAttributes().setDescription("");
        this.CloneMarker.getDefaultAttributes().setDetailedDescription("");
        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
    }

    private void defineParentingCloneMarker() {
        this.CloneMarker.getParentingRules().clear();
    }

    public ShapeObjectType getCloneMarker() {
        return this.CloneMarker;
    }

    private void createCompartment() {
        this.Compartment.setDescription("Functional compartment");// ment to be
                                                                  // TypeDescription
                                                                  // rather

        this.Compartment.getDefaultAttributes().setName("Compartment Name");
        this.Compartment.getDefaultAttributes().setDescription("");
        this.Compartment.getDefaultAttributes().setDetailedDescription("");
        this.Compartment.getDefaultAttributes().setUrl("");
        this.Compartment.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.Compartment.getDefaultAttributes().setSize(new Size(200, 200));
        this.Compartment.getDefaultAttributes().setLineWidth(3);
        this.Compartment.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Compartment.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.Compartment.getDefaultAttributes().setFillColour(
                new RGB(248, 234, 190));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Compartment.setEditableAttributes(editableAttributes);

        IPropertyDefinition GOTerm = reassignVal(getPropGOTerm(), " ", true,
                false);
        Compartment.getDefaultAttributes().addPropertyDefinition(GOTerm);
    }

    private void defineParentingCompartment() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.CloneMarker, this.Compartment,
                this.Complex, this.GeneticUnit, this.Macromolecule,
                this.MMultimer, this.SimpleChem, this.SMultimer,
                this.UnspecEntity, this.Sink, this.Source, this.Perturbation,
                this.Observable, this.Submap, this.Interface, this.Tag,
                this.Process, this.OmittedProcess, this.UncertainProcess,
                this.Association, this.Dissociation, this.AndGate, this.OrGate,
                this.NotGate }));
        set.removeAll(Arrays.asList(new IShapeObjectType[] { this.State }));
        for (IShapeObjectType child : set) {
            this.Compartment.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getCompartment() {
        return this.Compartment;
    }

    private void createComplex() {
        this.Complex.setDescription("SBGN complex");// ment to be
                                                    // TypeDescription rather

        this.Complex.getDefaultAttributes().setName("");
        this.Complex.getDefaultAttributes().setDescription("");
        this.Complex.getDefaultAttributes().setDetailedDescription("");
        this.Complex.getDefaultAttributes().setUrl("");
        this.Complex.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.IRREGULAR_OCTAGON);
        this.Complex.getDefaultAttributes().setSize(new Size(120, 80));
        this.Complex.getDefaultAttributes().setLineWidth(1);
        this.Complex.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Complex.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.Complex.getDefaultAttributes().setFillColour(
                new RGB(201, 223, 198));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Complex.setEditableAttributes(editableAttributes);
    }

    private void defineParentingComplex() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.Macromolecule, this.SimpleChem,
                this.Complex, this.CloneMarker, this.GeneticUnit,
                this.MMultimer, this.SMultimer, this.UnspecEntity }));
        for (IShapeObjectType child : set) {
            this.Complex.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getComplex() {
        return this.Complex;
    }

    private void createGeneticUnit() {
        this.GeneticUnit.setDescription("Unit of genetical information");// ment
                                                                         // to
                                                                         // be
                                                                         // TypeDescription
                                                                         // rather

        this.GeneticUnit.getDefaultAttributes().setName("");
        this.GeneticUnit.getDefaultAttributes().setDescription("");
        this.GeneticUnit.getDefaultAttributes().setDetailedDescription("");
        this.GeneticUnit.getDefaultAttributes().setUrl("");
        this.GeneticUnit.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.BOTTOM_ROUNDED_RECTANGLE);
        this.GeneticUnit.getDefaultAttributes().setSize(new Size(60, 40));
        this.GeneticUnit.getDefaultAttributes().setLineWidth(1);
        this.GeneticUnit.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.GeneticUnit.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.GeneticUnit.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.GeneticUnit.setEditableAttributes(editableAttributes);
    }

    private void defineParentingGeneticUnit() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.GeneticUnit.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getGeneticUnit() {
        return this.GeneticUnit;
    }

    private void createMacromolecule() {
        this.Macromolecule.setDescription("Macromolecule");// ment to be
                                                           // TypeDescription
                                                           // rather

        this.Macromolecule.getDefaultAttributes().setName("");
        this.Macromolecule.getDefaultAttributes().setDescription("");
        this.Macromolecule.getDefaultAttributes().setDetailedDescription("");
        this.Macromolecule.getDefaultAttributes().setUrl("");
        this.Macromolecule.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ROUNDED_RECTANGLE);
        this.Macromolecule.getDefaultAttributes().setSize(new Size(60, 40));
        this.Macromolecule.getDefaultAttributes().setLineWidth(1);
        this.Macromolecule.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Macromolecule.getDefaultAttributes().setLineColour(
                new RGB(1, 1, 1));
        this.Macromolecule.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Macromolecule.setEditableAttributes(editableAttributes);
    }

    private void defineParentingMacromolecule() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.Macromolecule.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getMacromolecule() {
        return this.Macromolecule;
    }

    private void createMMultimer() {
        this.MMultimer.setDescription("Macromoleclar Multimer");// ment to be
                                                                // TypeDescription
                                                                // rather

        this.MMultimer.getDefaultAttributes().setName("");
        this.MMultimer.getDefaultAttributes().setDescription("");
        this.MMultimer.getDefaultAttributes().setDetailedDescription("");
        this.MMultimer.getDefaultAttributes().setUrl("");
        this.MMultimer.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.MULTIMER);
        this.MMultimer.getDefaultAttributes().setSize(new Size(90, 40));
        this.MMultimer.getDefaultAttributes().setLineWidth(1);
        this.MMultimer.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.MMultimer.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.MMultimer.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.MMultimer.setEditableAttributes(editableAttributes);
    }

    private void defineParentingMMultimer() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.State,
                this.UnitOfInf, this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.MMultimer.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getMMultimer() {
        return this.MMultimer;
    }

    private void createSimpleChem() {
        this.SimpleChem.setDescription("Simple chemical");// ment to be
                                                          // TypeDescription
                                                          // rather

        this.SimpleChem.getDefaultAttributes().setName("");
        this.SimpleChem.getDefaultAttributes().setDescription("");
        this.SimpleChem.getDefaultAttributes().setDetailedDescription("");
        this.SimpleChem.getDefaultAttributes().setUrl("");
        this.SimpleChem.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.SimpleChem.getDefaultAttributes().setSize(new Size(30, 30));
        this.SimpleChem.getDefaultAttributes().setLineWidth(1);
        this.SimpleChem.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.SimpleChem.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.SimpleChem.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.SimpleChem.setEditableAttributes(editableAttributes);

        IPropertyDefinition Label = reassignVal(getPropLabel(),
                "Simple chemical", true, false);
        SimpleChem.getDefaultAttributes().addPropertyDefinition(Label);

    }

    private void defineParentingSimpleChem() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.SimpleChem.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getSimpleChem() {
        return this.SimpleChem;
    }

    private void createSMultimer() {
        this.SMultimer.setDescription("Chemical Multimer");// ment to be
                                                           // TypeDescription
                                                           // rather

        this.SMultimer.getDefaultAttributes().setName("");
        this.SMultimer.getDefaultAttributes().setDescription("");
        this.SMultimer.getDefaultAttributes().setDetailedDescription("");
        this.SMultimer.getDefaultAttributes().setUrl("");
        this.SMultimer.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE_MULTIMER);
        this.SMultimer.getDefaultAttributes().setSize(new Size(30, 30));
        this.SMultimer.getDefaultAttributes().setLineWidth(1);
        this.SMultimer.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.SMultimer.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.SMultimer.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.SMultimer.setEditableAttributes(editableAttributes);
    }

    private void defineParentingSMultimer() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.SMultimer.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getSMultimer() {
        return this.SMultimer;
    }

    private void createUnspecEntity() {
        this.UnspecEntity.setDescription("Unspecified entity");// ment to be
                                                               // TypeDescription
                                                               // rather

        this.UnspecEntity.getDefaultAttributes().setName("");
        this.UnspecEntity.getDefaultAttributes().setDescription("");
        this.UnspecEntity.getDefaultAttributes().setDetailedDescription("");
        this.UnspecEntity.getDefaultAttributes().setUrl("");
        this.UnspecEntity.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.UnspecEntity.getDefaultAttributes().setSize(new Size(60, 40));
        this.UnspecEntity.getDefaultAttributes().setLineWidth(1);
        this.UnspecEntity.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.UnspecEntity.getDefaultAttributes()
                .setLineColour(new RGB(1, 1, 1));
        this.UnspecEntity.getDefaultAttributes().setFillColour(
                new RGB(219, 223, 254));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.UnspecEntity.setEditableAttributes(editableAttributes);
    }

    private void defineParentingUnspecEntity() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.UnspecEntity.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getUnspecEntity() {
        return this.UnspecEntity;
    }

    private void createSink() {
        this.Sink.setDescription("Sink");// ment to be TypeDescription rather

        this.Sink.getDefaultAttributes().setName("");
        this.Sink.getDefaultAttributes().setDescription("");
        this.Sink.getDefaultAttributes().setDetailedDescription("");
        this.Sink.getDefaultAttributes().setUrl("");
        this.Sink.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.EMPTY_SET);
        this.Sink.getDefaultAttributes().setSize(new Size(30, 30));
        this.Sink.getDefaultAttributes().setLineWidth(1);
        this.Sink.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Sink.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.Sink.getDefaultAttributes().setFillColour(new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Sink.setEditableAttributes(editableAttributes);
    }

    private void defineParentingSink() {
        this.Sink.getParentingRules().clear();
    }

    public ShapeObjectType getSink() {
        return this.Sink;
    }

    private void createSource() {
        this.Source.setDescription("Source");// ment to be TypeDescription
                                             // rather

        this.Source.getDefaultAttributes().setName("");
        this.Source.getDefaultAttributes().setDescription("");
        this.Source.getDefaultAttributes().setDetailedDescription("");
        this.Source.getDefaultAttributes().setUrl("");
        this.Source.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.EMPTY_SET);
        this.Source.getDefaultAttributes().setSize(new Size(30, 30));
        this.Source.getDefaultAttributes().setLineWidth(1);
        this.Source.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Source.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.Source.getDefaultAttributes()
                .setFillColour(new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Source.setEditableAttributes(editableAttributes);
    }

    private void defineParentingSource() {
        this.Source.getParentingRules().clear();
    }

    public ShapeObjectType getSource() {
        return this.Source;
    }

    private void createPerturbation() {
        this.Perturbation.setDescription("Perturbation");// ment to be
                                                         // TypeDescription
                                                         // rather

        this.Perturbation.getDefaultAttributes().setName("");
        this.Perturbation.getDefaultAttributes().setDescription("");
        this.Perturbation.getDefaultAttributes().setDetailedDescription("");
        this.Perturbation.getDefaultAttributes().setUrl("");
        this.Perturbation.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.XSHAPE);
        this.Perturbation.getDefaultAttributes().setSize(new Size(80, 60));
        this.Perturbation.getDefaultAttributes().setLineWidth(1);
        this.Perturbation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Perturbation.getDefaultAttributes()
                .setLineColour(new RGB(1, 1, 1));
        this.Perturbation.getDefaultAttributes().setFillColour(
                new RGB(255, 250, 145));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Perturbation.setEditableAttributes(editableAttributes);
    }

    private void defineParentingPerturbation() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.CloneMarker }));
        for (IShapeObjectType child : set) {
            this.Perturbation.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getPerturbation() {
        return this.Perturbation;
    }

    private void createObservable() {
        this.Observable.setDescription("Observable");// ment to be
                                                     // TypeDescription rather

        this.Observable.getDefaultAttributes().setName("");
        this.Observable.getDefaultAttributes().setDescription("");
        this.Observable.getDefaultAttributes().setDetailedDescription("");
        this.Observable.getDefaultAttributes().setUrl("");
        this.Observable.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.HEXAGON);
        this.Observable.getDefaultAttributes().setSize(new Size(80, 60));
        this.Observable.getDefaultAttributes().setLineWidth(1);
        this.Observable.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Observable.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.Observable.getDefaultAttributes().setFillColour(
                new RGB(255, 250, 145));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Observable.setEditableAttributes(editableAttributes);
    }

    private void defineParentingObservable() {
        this.Observable.getParentingRules().clear();
    }

    public ShapeObjectType getObservable() {
        return this.Observable;
    }

    private void createSubmap() {
        this.Submap.setDescription("collapsed part of diagram");// ment to be
                                                                // TypeDescription
                                                                // rather

        this.Submap.getDefaultAttributes().setName("");
        this.Submap.getDefaultAttributes().setDescription("");
        this.Submap.getDefaultAttributes().setDetailedDescription("");
        this.Submap.getDefaultAttributes().setUrl("");
        this.Submap.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.Submap.getDefaultAttributes().setSize(new Size(120, 120));
        this.Submap.getDefaultAttributes().setLineWidth(1);
        this.Submap.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Submap.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.Submap.getDefaultAttributes()
                .setFillColour(new RGB(255, 250, 145));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Submap.setEditableAttributes(editableAttributes);
    }

    private void defineParentingSubmap() {
        HashSet<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Interface,
                this.UnitOfInf }));
        for (IShapeObjectType child : set) {
            this.Submap.getParentingRules().addChild(child);
        }

    }

    public ShapeObjectType getSubmap() {
        return this.Submap;
    }

    private void createInterface() {
        this.Interface.setDescription("Interface to the submap");// ment to be
                                                                 // TypeDescription
                                                                 // rather

        this.Interface.getDefaultAttributes().setName("");
        this.Interface.getDefaultAttributes().setDescription("");
        this.Interface.getDefaultAttributes().setDetailedDescription("");
        this.Interface.getDefaultAttributes().setUrl("");
        this.Interface.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.Interface.getDefaultAttributes().setSize(new Size(20, 35));
        this.Interface.getDefaultAttributes().setLineWidth(1);
        this.Interface.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Interface.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.Interface.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Interface.setEditableAttributes(editableAttributes);
    }

    private void defineParentingInterface() {
        this.Interface.getParentingRules().clear();
    }

    public ShapeObjectType getInterface() {
        return this.Interface;
    }

    private void createTag() {
        this.Tag.setDescription("Mark node to be interface to submap");// ment
                                                                       // to be
                                                                       // TypeDescription
                                                                       // rather

        this.Tag.getDefaultAttributes().setName("");
        this.Tag.getDefaultAttributes().setDescription("");
        this.Tag.getDefaultAttributes().setDetailedDescription("");
        this.Tag.getDefaultAttributes().setUrl("");
        this.Tag.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RH_SIGN_ARROW);
        this.Tag.getDefaultAttributes().setSize(new Size(40, 20));
        this.Tag.getDefaultAttributes().setLineWidth(1);
        this.Tag.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Tag.getDefaultAttributes().setLineColour(new RGB(1, 1, 1));
        this.Tag.getDefaultAttributes().setFillColour(new RGB(255, 250, 145));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Tag.setEditableAttributes(editableAttributes);
    }

    private void defineParentingTag() {
        this.Tag.getParentingRules().clear();
    }

    public ShapeObjectType getTag() {
        return this.Tag;
    }

    private void createTransition() {
        this.Process.setDescription("Process");// ment to be TypeDescription
                                               // rather

        this.Process.getDefaultAttributes().setName("");
        this.Process.getDefaultAttributes().setDescription("");
        this.Process.getDefaultAttributes().setDetailedDescription("");
        this.Process.getDefaultAttributes().setUrl("");
        this.Process.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.Process.getDefaultAttributes().setSize(new Size(20, 20));
        this.Process.getDefaultAttributes().setLineWidth(2);
        this.Process.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Process.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.Process.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Process.setEditableAttributes(editableAttributes);
    }

    private void defineParentingProcess() {
        this.Process.getParentingRules().clear();
    }

    public ShapeObjectType getTransition() {
        return this.Process;
    }

    private void createOmittedProcess() {
        this.OmittedProcess.setDescription("Omitted Process");// ment to be
                                                              // TypeDescription
                                                              // rather

        this.OmittedProcess.getDefaultAttributes().setName("//");
        this.OmittedProcess.getDefaultAttributes().setDescription("");
        this.OmittedProcess.getDefaultAttributes().setDetailedDescription("");
        this.OmittedProcess.getDefaultAttributes().setUrl("");
        this.OmittedProcess.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.OmittedProcess.getDefaultAttributes().setSize(new Size(20, 20));
        this.OmittedProcess.getDefaultAttributes().setLineWidth(2);
        this.OmittedProcess.getDefaultAttributes()
                .setLineStyle(LineStyle.SOLID);
        this.OmittedProcess.getDefaultAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.OmittedProcess.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.OmittedProcess.setEditableAttributes(editableAttributes);
    }

    private void defineParentingOmittedProcess() {
        this.OmittedProcess.getParentingRules().clear();
    }

    public ShapeObjectType getOmittedProcess() {
        return this.OmittedProcess;
    }

    private void createUncertainProcess() {
        this.UncertainProcess.setDescription("Uncertain Process");// ment to be
                                                                  // TypeDescription
                                                                  // rather

        this.UncertainProcess.getDefaultAttributes().setName("?");
        this.UncertainProcess.getDefaultAttributes().setDescription("");
        this.UncertainProcess.getDefaultAttributes().setDetailedDescription("");
        this.UncertainProcess.getDefaultAttributes().setUrl("");
        this.UncertainProcess.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.RECTANGLE);
        this.UncertainProcess.getDefaultAttributes().setSize(new Size(20, 20));
        this.UncertainProcess.getDefaultAttributes().setLineWidth(2);
        this.UncertainProcess.getDefaultAttributes().setLineStyle(
                LineStyle.SOLID);
        this.UncertainProcess.getDefaultAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.UncertainProcess.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.UncertainProcess.setEditableAttributes(editableAttributes);
    }

    private void defineParentingUncertainProcess() {
        this.UncertainProcess.getParentingRules().clear();
    }

    public ShapeObjectType getUncertainProcess() {
        return this.UncertainProcess;
    }

    private void createAssociation() {
        this.Association.setDescription("Association");// ment to be
                                                       // TypeDescription rather

        this.Association.getDefaultAttributes().setName("");
        this.Association.getDefaultAttributes().setDescription("");
        this.Association.getDefaultAttributes().setDetailedDescription("");
        this.Association.getDefaultAttributes().setUrl("");
        this.Association.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.Association.getDefaultAttributes().setSize(new Size(20, 20));
        this.Association.getDefaultAttributes().setLineWidth(1);
        this.Association.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Association.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.Association.getDefaultAttributes().setFillColour(new RGB(0, 0, 0));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Association.setEditableAttributes(editableAttributes);
    }

    private void defineParentingAssociation() {
        this.Association.getParentingRules().clear();
    }

    public ShapeObjectType getAssociation() {
        return this.Association;
    }

    private void createDissociation() {
        this.Dissociation.setDescription("Dissociation");// ment to be
                                                         // TypeDescription
                                                         // rather

        this.Dissociation.getDefaultAttributes().setName("");
        this.Dissociation.getDefaultAttributes().setDescription("");
        this.Dissociation.getDefaultAttributes().setDetailedDescription("");
        this.Dissociation.getDefaultAttributes().setUrl("");
        this.Dissociation.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.CONCENTRIC_CIRCLES);
        this.Dissociation.getDefaultAttributes().setSize(new Size(20, 20));
        this.Dissociation.getDefaultAttributes().setLineWidth(1);
        this.Dissociation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.Dissociation.getDefaultAttributes()
                .setLineColour(new RGB(0, 0, 0));
        this.Dissociation.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.Dissociation.setEditableAttributes(editableAttributes);
    }

    private void defineParentingDissociation() {
        this.Dissociation.getParentingRules().clear();
    }

    public ShapeObjectType getDissociation() {
        return this.Dissociation;
    }

    private void createAndGate() {
        this.AndGate.setDescription("And Gate");// ment to be TypeDescription
                                                // rather

        this.AndGate.getDefaultAttributes().setName("AND");
        this.AndGate.getDefaultAttributes().setDescription("");
        this.AndGate.getDefaultAttributes().setDetailedDescription("");
        this.AndGate.getDefaultAttributes().setUrl("");
        this.AndGate.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.AndGate.getDefaultAttributes().setSize(new Size(40, 40));
        this.AndGate.getDefaultAttributes().setLineWidth(1);
        this.AndGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.AndGate.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.AndGate.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.AndGate.setEditableAttributes(editableAttributes);
    }

    private void defineParentingAndGate() {
        this.AndGate.getParentingRules().clear();
    }

    public ShapeObjectType getAndGate() {
        return this.AndGate;
    }

    private void createOrGate() {
        this.OrGate.setDescription("OR logic Gate");// ment to be
                                                    // TypeDescription rather

        this.OrGate.getDefaultAttributes().setName("OR");
        this.OrGate.getDefaultAttributes().setDescription("");
        this.OrGate.getDefaultAttributes().setDetailedDescription("");
        this.OrGate.getDefaultAttributes().setUrl("");
        this.OrGate.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.OrGate.getDefaultAttributes().setSize(new Size(20, 20));
        this.OrGate.getDefaultAttributes().setLineWidth(1);
        this.OrGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.OrGate.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.OrGate.getDefaultAttributes()
                .setFillColour(new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.OrGate.setEditableAttributes(editableAttributes);
    }

    private void defineParentingOrGate() {
        this.OrGate.getParentingRules().clear();
    }

    public ShapeObjectType getOrGate() {
        return this.OrGate;
    }

    private void createNotGate() {
        this.NotGate.setDescription("NOT logic Gate");// ment to be
                                                      // TypeDescription rather

        this.NotGate.getDefaultAttributes().setName("NOT");
        this.NotGate.getDefaultAttributes().setDescription("");
        this.NotGate.getDefaultAttributes().setDetailedDescription("");
        this.NotGate.getDefaultAttributes().setUrl("");
        this.NotGate.getDefaultAttributes().setShapeType(
                PrimitiveShapeType.ELLIPSE);
        this.NotGate.getDefaultAttributes().setSize(new Size(40, 40));
        this.NotGate.getDefaultAttributes().setLineWidth(1);
        this.NotGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
        this.NotGate.getDefaultAttributes().setLineColour(new RGB(0, 0, 0));
        this.NotGate.getDefaultAttributes().setFillColour(
                new RGB(255, 255, 255));

        EnumSet<EditableShapeAttributes> editableAttributes = EnumSet
                .noneOf(EditableShapeAttributes.class);
        editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
        editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
        editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
        this.NotGate.setEditableAttributes(editableAttributes);
    }

    private void defineParentingNotGate() {
        this.NotGate.getParentingRules().clear();
    }

    public ShapeObjectType getNotGate() {
        return this.NotGate;
    }

    private void createConsumption() {
        this.Consumption
                .setDescription("Consumption is the arc used to represent the fact that an entity only affects a process, but is not affected by it");

        this.Consumption.getDefaultLinkAttributes().setLineWidth(1);
        this.Consumption.getDefaultLinkAttributes().setLineStyle(
                LineStyle.SOLID);
        this.Consumption.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Consumption.getDefaultLinkAttributes().setName("Consumption Link");
        this.Consumption.getDefaultLinkAttributes().setUrl("");
        this.Consumption.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Consumption.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Consumption.getDefaultLinkAttributes().addPropertyDefinition(
                Stoich);

        LinkTerminusDefinition sport = this.Consumption
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Consumption
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 0);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        Set<IShapeObjectType> set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.Complex, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.GeneticUnit, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Consumption.getLinkConnectionRules().addConnection(
                    this.Source, tgt);
        }

    }

    public LinkObjectType getConsumption() {
        return this.Consumption;
    }

    private void createProduction() {
        HashSet<IShapeObjectType> set = null;

        this.Production.getDefaultLinkAttributes().setLineWidth(1);
        this.Production.getDefaultLinkAttributes()
                .setLineStyle(LineStyle.SOLID);
        this.Production.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Production.getDefaultLinkAttributes().setName("Production Link");
        this.Production.getDefaultLinkAttributes().setUrl("");
        this.Production.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Production.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Production.getDefaultLinkAttributes()
                .addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.Production
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 0);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Production
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 5);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.TRIANGLE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays
                .asList(new IShapeObjectType[] { this.Complex,
                        this.Macromolecule, this.MMultimer, this.SimpleChem,
                        this.SMultimer, this.UnspecEntity, this.Sink,
                        this.GeneticUnit }));
        for (IShapeObjectType tgt : set) {
            this.Production.getLinkConnectionRules().addConnection(
                    this.Process, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays
                .asList(new IShapeObjectType[] { this.Complex,
                        this.Macromolecule, this.MMultimer, this.SimpleChem,
                        this.SMultimer, this.UnspecEntity, this.Sink,
                        this.GeneticUnit }));
        for (IShapeObjectType tgt : set) {
            this.Production.getLinkConnectionRules().addConnection(
                    this.OmittedProcess, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays
                .asList(new IShapeObjectType[] { this.Complex,
                        this.Macromolecule, this.MMultimer, this.SimpleChem,
                        this.SMultimer, this.UnspecEntity, this.Sink,
                        this.GeneticUnit }));
        for (IShapeObjectType tgt : set) {
            this.Production.getLinkConnectionRules().addConnection(
                    this.UncertainProcess, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays
                .asList(new IShapeObjectType[] { this.Complex,
                        this.Macromolecule, this.MMultimer, this.SimpleChem,
                        this.SMultimer, this.UnspecEntity, this.Sink,
                        this.GeneticUnit }));
        for (IShapeObjectType tgt : set) {
            this.Production.getLinkConnectionRules().addConnection(
                    this.Association, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays
                .asList(new IShapeObjectType[] { this.Complex,
                        this.Macromolecule, this.MMultimer, this.SimpleChem,
                        this.SMultimer, this.UnspecEntity, this.Sink,
                        this.GeneticUnit }));
        for (IShapeObjectType tgt : set) {
            this.Production.getLinkConnectionRules().addConnection(
                    this.Dissociation, tgt);
        }

    }

    public LinkObjectType getProduction() {
        return this.Production;
    }

    private void createModulation() {
        HashSet<IShapeObjectType> set = null;

        this.Modulation.getDefaultLinkAttributes().setLineWidth(1);
        this.Modulation.getDefaultLinkAttributes()
                .setLineStyle(LineStyle.SOLID);
        this.Modulation.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Modulation.getDefaultLinkAttributes().setName("Modulation Link");
        this.Modulation.getDefaultLinkAttributes().setUrl("");
        this.Modulation.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Modulation.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Modulation.getDefaultLinkAttributes()
                .addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.Modulation
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Modulation
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 5);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.EMPTY_DIAMOND);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.Complex, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.AndGate, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(this.OrGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Modulation.getLinkConnectionRules().addConnection(
                    this.NotGate, tgt);
        }

    }

    public LinkObjectType getModulation() {
        return this.Modulation;
    }

    private void createStimulation() {
        HashSet<IShapeObjectType> set = null;

        this.Stimulation.getDefaultLinkAttributes().setLineWidth(1);
        this.Stimulation.getDefaultLinkAttributes().setLineStyle(
                LineStyle.SOLID);
        this.Stimulation.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Stimulation.getDefaultLinkAttributes().setName("Stimulation Link");
        this.Stimulation.getDefaultLinkAttributes().setUrl("");
        this.Stimulation.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Stimulation.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Stimulation.getDefaultLinkAttributes().addPropertyDefinition(
                Stoich);

        LinkTerminusDefinition sport = this.Stimulation
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Stimulation
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 5);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.EMPTY_TRIANGLE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.Complex, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.AndGate, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.OrGate, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Stimulation.getLinkConnectionRules().addConnection(
                    this.NotGate, tgt);
        }

    }

    public LinkObjectType getStimulation() {
        return this.Stimulation;
    }

    private void createCatalysis() {
        HashSet<IShapeObjectType> set = null;
        this.Catalysis
                .setDescription("A catalysis is a particular case of stimulation.");

        this.Catalysis.getDefaultLinkAttributes().setLineWidth(1);
        this.Catalysis.getDefaultLinkAttributes().setLineStyle(LineStyle.SOLID);
        this.Catalysis.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Catalysis.getDefaultLinkAttributes().setName("Catalysis Link");
        this.Catalysis.getDefaultLinkAttributes().setUrl("");
        this.Catalysis.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Catalysis.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Catalysis.getDefaultLinkAttributes().addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.Catalysis
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Catalysis
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 10);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.EMPTY_CIRCLE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(this.Complex,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(this.AndGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(this.OrGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Catalysis.getLinkConnectionRules().addConnection(this.NotGate,
                    tgt);
        }

    }

    public LinkObjectType getCatalysis() {
        return this.Catalysis;
    }

    private void createInhibition() {
        HashSet<IShapeObjectType> set = null;

        this.Inhibition
                .setDescription("An inhibition affects negatively the flux of a process represented by the target transition.");

        this.Inhibition.getDefaultLinkAttributes().setLineWidth(1);
        this.Inhibition.getDefaultLinkAttributes()
                .setLineStyle(LineStyle.SOLID);
        this.Inhibition.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.Inhibition.getDefaultLinkAttributes().setName("Inhibition Link");
        this.Inhibition.getDefaultLinkAttributes().setUrl("");
        this.Inhibition.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Inhibition.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Inhibition.getDefaultLinkAttributes()
                .addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.Inhibition
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Inhibition
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 5);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.BAR);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.Complex, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.AndGate, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(this.OrGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Inhibition.getLinkConnectionRules().addConnection(
                    this.NotGate, tgt);
        }

    }

    public LinkObjectType getInhibition() {
        return this.Inhibition;
    }

    private void createTrigger() {
        HashSet<IShapeObjectType> set = null;

        this.Trigger
                .setDescription("A trigger effect, or absolute stimulation, is a stimulation that is necessary for a process to take place.");

        this.Trigger.getDefaultLinkAttributes().setLineWidth(1);
        this.Trigger.getDefaultLinkAttributes().setLineStyle(LineStyle.SOLID);
        this.Trigger.getDefaultLinkAttributes().setLineColour(new RGB(0, 0, 0));
        this.Trigger.getDefaultLinkAttributes().setName("Trigger Link");
        this.Trigger.getDefaultLinkAttributes().setUrl("");
        this.Trigger.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.Trigger.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.Trigger.getDefaultLinkAttributes().addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.Trigger
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.Trigger
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 0);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.TRIANGLE_BAR);
        tport.getLinkTerminusDefaults().setEndSize(new Size(5, 5));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.Complex,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.MMultimer,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.SMultimer,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.AndGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.OrGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Process,
                this.OmittedProcess, this.UncertainProcess, this.Association,
                this.Dissociation, this.Observable }));
        for (IShapeObjectType tgt : set) {
            this.Trigger.getLinkConnectionRules().addConnection(this.NotGate,
                    tgt);
        }

    }

    public LinkObjectType getTrigger() {
        return this.Trigger;
    }

    private void createLogicArc() {
        HashSet<IShapeObjectType> set = null;

        this.LogicArc
                .setDescription("Logic arc is the arc used to represent the fact that an entity influences outcome of logic operator.");
        this.LogicArc.getDefaultLinkAttributes().setLineWidth(1);
        this.LogicArc.getDefaultLinkAttributes().setLineStyle(LineStyle.SOLID);
        this.LogicArc.getDefaultLinkAttributes()
                .setLineColour(new RGB(0, 0, 0));
        this.LogicArc.getDefaultLinkAttributes().setName("Logic Arc");
        this.LogicArc.getDefaultLinkAttributes().setUrl("");
        this.LogicArc.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.LogicArc.setEditableAttributes(linkEditableAttributes);

        IPropertyDefinition Stoich = reassignVal(getPropStoich(), "1", true,
                false);
        this.LogicArc.getDefaultLinkAttributes().addPropertyDefinition(Stoich);

        LinkTerminusDefinition sport = this.LogicArc
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.LogicArc
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 0);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(this.Complex,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(
                    this.Perturbation, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(this.AndGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(this.OrGate,
                    tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.AndGate,
                this.OrGate, this.NotGate }));
        for (IShapeObjectType tgt : set) {
            this.LogicArc.getLinkConnectionRules().addConnection(this.NotGate,
                    tgt);
        }

    }

    public LinkObjectType getLogicArc() {
        return this.LogicArc;
    }

    private void createEquivalenceArc() {
        HashSet<IShapeObjectType> set = null;

        this.EquivalenceArc
                .setDescription("Equivalence Arc is the arc used to represent the fact that all entities marked by the tag are equivalent.");

        this.EquivalenceArc.getDefaultLinkAttributes().setLineWidth(1);
        this.EquivalenceArc.getDefaultLinkAttributes().setLineStyle(
                LineStyle.SOLID);
        this.EquivalenceArc.getDefaultLinkAttributes().setLineColour(
                new RGB(0, 0, 0));
        this.EquivalenceArc.getDefaultLinkAttributes().setName(
                "Equivalence Arc");
        this.EquivalenceArc.getDefaultLinkAttributes().setUrl("");
        this.EquivalenceArc.getDefaultLinkAttributes().setRouter(
                ConnectionRouter.SHORTEST_PATH);

        EnumSet<LinkEditableAttributes> linkEditableAttributes = EnumSet
                .noneOf(LinkEditableAttributes.class);
        linkEditableAttributes.add(LinkEditableAttributes.COLOUR);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_STYLE);
        linkEditableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
        this.EquivalenceArc.setEditableAttributes(linkEditableAttributes);

        LinkTerminusDefinition sport = this.EquivalenceArc
                .getSourceTerminusDefinition();
        sport.getLinkTerminusDefaults().setGap((short) 5);
        sport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        sport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        sport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        sport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        sport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> srcLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        sport.setEditableAttributes(srcLinkEditableAttributes);

        LinkTerminusDefinition tport = this.EquivalenceArc
                .getTargetTerminusDefinition();
        tport.getLinkTerminusDefaults().setGap((short) 5);
        tport.getLinkTerminusDefaults().setLinkEndDecoratorShape(
                LinkEndDecoratorShape.NONE);
        tport.getLinkTerminusDefaults().setEndSize(new Size(8, 8));
        tport.getLinkTerminusDefaults().setTermDecoratorType(
                PrimitiveShapeType.RECTANGLE);
        tport.getLinkTerminusDefaults().setTermSize(new Size(0, 0));
        tport.getLinkTerminusDefaults().setTermColour(new RGB(255, 255, 255));
        EnumSet<LinkTermEditableAttributes> tgtLinkEditableAttributes = EnumSet
                .noneOf(LinkTermEditableAttributes.class);
        tport.setEditableAttributes(tgtLinkEditableAttributes);

        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.Complex, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.Macromolecule, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.MMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.SimpleChem, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.SMultimer, tgt);
        }
        set = new HashSet<IShapeObjectType>();
        set.addAll(Arrays.asList(new IShapeObjectType[] { this.Tag,
                this.Interface }));
        for (IShapeObjectType tgt : set) {
            this.EquivalenceArc.getLinkConnectionRules().addConnection(
                    this.UnspecEntity, tgt);
        }

    }

    public LinkObjectType getEquivalenceArc() {
        return this.EquivalenceArc;
    }

    private IPropertyDefinition getPropGOTerm() {
        IPropertyDefinition GOTerm = new PlainTextPropertyDefinition("GO term",
                " ", false, true);
        return GOTerm;
    }

    // private IPropertyDefinition getPropSBOTerm(){
    // IPropertyDefinition SBOTerm=new
    // TextPropertyDefinition("SBO term"," ",false,true);
    // return SBOTerm;
    // }
    private IPropertyDefinition getPropLabel() {
        IPropertyDefinition Label = new FormattedTextPropertyDefinition(
                "Label", " ", true, true);
        return Label;
    }

    private IPropertyDefinition getPropStoich() {
        IPropertyDefinition Stoich = new PlainTextPropertyDefinition("STOICH",
                " ", true, true);
        return Stoich;
    }

   
    public boolean containsLinkObjectType(int uniqueId) {
        return false;
    }

   
    public boolean containsObjectType(int uniqueId) {
        boolean retVal = this.shapeSet.containsKey(uniqueId)
            || this.linkSet.containsKey(uniqueId) || this.rmo.getUniqueId() == uniqueId;
        return retVal;
    }

   
    public boolean containsShapeObjectType(int uniqueId) {
        return this.shapeSet.containsKey(uniqueId);
    }

   
    public ILinkObjectType getLinkObjectType(int uniqueId) {
        return this.linkSet.get(uniqueId);
    }

   
    public IObjectType getObjectType(int uniqueId) {
        IObjectType retVal = this.shapeSet.get(uniqueId);
        if(retVal == null) {
            retVal = this.linkSet.get(uniqueId);
            if(retVal == null && this.rmo.getUniqueId() == uniqueId) {
                retVal = this.rmo;
            }
        }
        return retVal;
    }

   
    public IRootObjectType getRootObjectType() {
        return this.rmo;
    }

   
    public IShapeObjectType getShapeObjectType(int uniqueId) {
        return this.shapeSet.get(uniqueId);
    }

   
    public Iterator<ILinkObjectType> linkTypeIterator() {
        return this.linkSet.values().iterator();
    }
    
   
    public Iterator<IObjectType> objectTypeIterator() {
        final SortedSet<IObjectType> retVal = new TreeSet<IObjectType>(this.shapeSet.values());
        retVal.addAll(this.linkSet.values());
        retVal.add(this.rmo);
        return retVal.iterator();
    }

   
    public Iterator<IShapeObjectType> shapeTypeIterator() {
        return this.shapeSet.values().iterator();
    }

   
    public INotationSubsystem getNotationSubsystem() {
        return this.serviceProvider;
    }

}

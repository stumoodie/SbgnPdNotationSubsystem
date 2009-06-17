package org.pathwayeditor.notations.sbgnpd.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Collection;
import java.util.Set;

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
import org.pathwayeditor.notationsubsystem.toolkit.definition.FormattedTextPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.LinkTerminusDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.NumberPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.PlainTextPropertyDefinition;
import org.pathwayeditor.notationsubsystem.toolkit.definition.RootObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.ShapeObjectType;
import org.pathwayeditor.notationsubsystem.toolkit.definition.TextPropertyDefinition;

public class SbgnPdNotationSyntaxService implements INotationSyntaxService {
  private static final int NUM_ROOT_OTS = 1;
  private final INotation context;
  private final Map <Integer, IShapeObjectType> shapes = new HashMap<Integer, IShapeObjectType>(); 
  private final Map <Integer, ILinkObjectType> links = new HashMap<Integer, ILinkObjectType>();
//  private final Set <IPropertyDefinition> propSet=new HashSet<IPropertyDefinition>();
  
  private RootObjectType rmo;
  //shapes
  private ShapeObjectType State;
  private ShapeObjectType UnitOfInf;
  private ShapeObjectType Compartment;
  private ShapeObjectType Complex;
  private ShapeObjectType GeneticUnit;
  private ShapeObjectType Macromolecule;
  private ShapeObjectType SimpleChem;
  private ShapeObjectType UnspecEntity;
  private ShapeObjectType Sink;
  private ShapeObjectType Source;
  private ShapeObjectType Perturbation;
  private ShapeObjectType Observable;
  private ShapeObjectType Module;
  private ShapeObjectType Port;
  private ShapeObjectType Tag;
  private ShapeObjectType Process;
  private ShapeObjectType OmittedProcess;
  private ShapeObjectType UncertainProcess;
  private ShapeObjectType Association;
  private ShapeObjectType Dissociation;
  private ShapeObjectType AndGate;
  private ShapeObjectType OrGate;
  private ShapeObjectType NotGate;

  //links
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

	private static IPropertyDefinition reassignVal(IPropertyDefinition prop,String val,boolean isEdit,boolean isVis){
		if( prop instanceof TextPropertyDefinition) return reassignVal((TextPropertyDefinition) prop,val,isEdit,isVis);
		if( prop instanceof FormattedTextPropertyDefinition) return reassignVal((FormattedTextPropertyDefinition) prop,val,isEdit,isVis);
		if( prop instanceof NumberPropertyDefinition) return reassignVal((NumberPropertyDefinition) prop,val,isEdit,isVis);
		return prop;
	}
	
	private static PlainTextPropertyDefinition reassignVal(TextPropertyDefinition prop,String val,boolean isEdit,boolean isVis){
		PlainTextPropertyDefinition newP=new PlainTextPropertyDefinition(prop.getName(),val,(prop.isVisualisable() | isVis),(prop.isEditable()&isEdit));
  //  if(newP.isVisualisable())newP.setAppearance(prop.getAppearance());
		return newP;
	}
	
	private static FormattedTextPropertyDefinition reassignVal(FormattedTextPropertyDefinition prop,String val,boolean isEdit,boolean isVis){
		FormattedTextPropertyDefinition newP=new FormattedTextPropertyDefinition(prop.getName(),val,(prop.isVisualisable() | isVis),(prop.isEditable()&isEdit));
//		if(newP.isVisualisable())newP.setAppearance(prop.getAppearance());
		return newP;
	}
	
	private static NumberPropertyDefinition reassignVal(NumberPropertyDefinition prop,String val,boolean isEdit,boolean isVis){
		NumberPropertyDefinition newP=new NumberPropertyDefinition(prop.getName(), new BigDecimal(val),(prop.isVisualisable() | isVis),(prop.isEditable()&isEdit));
 //   if(newP.isVisualisable())newP.setAppearance(prop.getAppearance());
		return newP;
	}
	

	public SbgnPdNotationSyntaxService(INotationSubsystem serviceProvider) {
		this.serviceProvider=serviceProvider;
		this.context = serviceProvider.getNotation();
		//"SBGN-PD"
		//"SBGN process diagram language context"
		//1_0_0
		createRMO();
	//shapes
	this.State= new ShapeObjectType(this,10, "State");
	createState();
	this.UnitOfInf= new ShapeObjectType(this,11, "UnitOfInf");
	createUnitOfInf();
	this.Compartment= new ShapeObjectType(this,13, "Compartment");
	createCompartment();
	this.Complex= new ShapeObjectType(this,14, "Complex");
	createComplex();
	this.GeneticUnit= new ShapeObjectType(this,15, "GeneticUnit");
	createGeneticUnit();
	this.Macromolecule= new ShapeObjectType(this,16, "Macromolecule");
	createMacromolecule();
	this.SimpleChem= new ShapeObjectType(this,18, "SimpleChem");
	createSimpleChem();
	this.UnspecEntity= new ShapeObjectType(this,110, "UnspecEntity");
	createUnspecEntity();
	this.Sink= new ShapeObjectType(this,111, "Sink");
	createSink();
	this.Source= new ShapeObjectType(this,112, "Source");
	createSource();
	this.Perturbation= new ShapeObjectType(this,113, "Perturbation");
	createPerturbation();
	this.Observable= new ShapeObjectType(this,114, "Observable");
	createObservable();
	this.Module= new ShapeObjectType(this,115, "Module");
	createModule();
	this.Port= new ShapeObjectType(this,116, "Port");
	createPort();
	this.Tag= new ShapeObjectType(this,117, "Tag");
	createTag();
	this.Process= new ShapeObjectType(this,118, "Process");
	createProcess();
	this.OmittedProcess= new ShapeObjectType(this,119, "OmittedProcess");
	createOmittedProcess();
	this.UncertainProcess= new ShapeObjectType(this,120, "UncertainProcess");
	createUncertainProcess();
	this.Association= new ShapeObjectType(this,121, "Association");
	createAssociation();
	this.Dissociation= new ShapeObjectType(this,122, "Dissociation");
	createDissociation();
	this.AndGate= new ShapeObjectType(this,123, "AndGate");
	createAndGate();
	this.OrGate= new ShapeObjectType(this,124, "OrGate");
	createOrGate();
	this.NotGate= new ShapeObjectType(this,125, "NotGate");
	createNotGate();

		defineParentingRMO();
	//shapes parenting
		defineParentingState();
		defineParentingUnitOfInf();
		defineParentingCompartment();
		defineParentingComplex();
		defineParentingGeneticUnit();
		defineParentingMacromolecule();
		defineParentingSimpleChem();
		defineParentingUnspecEntity();
		defineParentingSink();
		defineParentingSource();
		defineParentingPerturbation();
		defineParentingObservable();
		defineParentingModule();
		defineParentingPort();
		defineParentingTag();
		defineParentingProcess();
		defineParentingOmittedProcess();
		defineParentingUncertainProcess();
		defineParentingAssociation();
		defineParentingDissociation();
		defineParentingAndGate();
		defineParentingOrGate();
		defineParentingNotGate();

	//links
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

	//shape set
		this.shapes.put(this.State.getUniqueId(), this.State);
		this.shapes.put(this.UnitOfInf.getUniqueId(), this.UnitOfInf);
		this.shapes.put(this.Compartment.getUniqueId(), this.Compartment);
		this.shapes.put(this.Complex.getUniqueId(), this.Complex);
		this.shapes.put(this.GeneticUnit.getUniqueId(), this.GeneticUnit);
		this.shapes.put(this.Macromolecule.getUniqueId(), this.Macromolecule);
		this.shapes.put(this.SimpleChem.getUniqueId(), this.SimpleChem);
		this.shapes.put(this.UnspecEntity.getUniqueId(), this.UnspecEntity);
		this.shapes.put(this.Sink.getUniqueId(), this.Sink);
		this.shapes.put(this.Source.getUniqueId(), this.Source);
		this.shapes.put(this.Perturbation.getUniqueId(), this.Perturbation);
		this.shapes.put(this.Observable.getUniqueId(), this.Observable);
		this.shapes.put(this.Module.getUniqueId(), this.Module);
		this.shapes.put(this.Port.getUniqueId(), this.Port);
		this.shapes.put(this.Tag.getUniqueId(), this.Tag);
		this.shapes.put(this.Process.getUniqueId(), this.Process);
		this.shapes.put(this.OmittedProcess.getUniqueId(), this.OmittedProcess);
		this.shapes.put(this.UncertainProcess.getUniqueId(), this.UncertainProcess);
		this.shapes.put(this.Association.getUniqueId(), this.Association);
		this.shapes.put(this.Dissociation.getUniqueId(), this.Dissociation);
		this.shapes.put(this.AndGate.getUniqueId(), this.AndGate);
		this.shapes.put(this.OrGate.getUniqueId(), this.OrGate);
		this.shapes.put(this.NotGate.getUniqueId(), this.NotGate);

	//link set
		this.links.put(this.Consumption.getUniqueId(), this.Consumption);
		this.links.put(this.Production.getUniqueId(), this.Production);
		this.links.put(this.Modulation.getUniqueId(), this.Modulation);
		this.links.put(this.Stimulation.getUniqueId(), this.Stimulation);
		this.links.put(this.Catalysis.getUniqueId(), this.Catalysis);
		this.links.put(this.Inhibition.getUniqueId(), this.Inhibition);
		this.links.put(this.Trigger.getUniqueId(), this.Trigger);
		this.links.put(this.LogicArc.getUniqueId(), this.LogicArc);
		this.links.put(this.EquivalenceArc.getUniqueId(), this.EquivalenceArc);
		
	}

  public INotationSubsystem getNotationSubsystem() {
    return serviceProvider;
  }
  
  public INotation getNotation() {
    return this.context;
  }

  public Iterator<ILinkObjectType> linkTypeIterator() {
    return this.links.values().iterator();
  }

  public IRootObjectType getRootObjectType() {
    return this.rmo;
  }

  public Iterator<IShapeObjectType> shapeTypeIterator() {
    return this.shapes.values().iterator();
  }
  
  
  public Iterator<IObjectType> objectTypeIterator(){
    Set<IObjectType> retVal = new HashSet<IObjectType>(this.shapes.values());
    retVal.addAll(this.links.values());
    retVal.add(this.rmo);
    return retVal.iterator();
  }
  
  public boolean containsLinkObjectType(int uniqueId) {
    return this.links.containsKey(uniqueId);
  }

  public boolean containsObjectType(int uniqueId) {
    boolean retVal = this.links.containsKey(uniqueId);
    if(!retVal){
      retVal = this.shapes.containsKey(uniqueId);
    }
    if(!retVal){
      retVal = this.rmo.getUniqueId() == uniqueId;
    }
    return retVal;
  }

  public boolean containsShapeObjectType(int uniqueId) {
    return this.shapes.containsKey(uniqueId);
  }

  public ILinkObjectType getLinkObjectType(int uniqueId) {
    return this.links.get(uniqueId);
  }

  public IObjectType getObjectType(int uniqueId) {
    IObjectType retVal = this.links.get(uniqueId);
    if(retVal == null){
      retVal = this.shapes.get(uniqueId);
    }
    if(retVal == null){
      retVal = (this.rmo.getUniqueId() == uniqueId) ? this.rmo : null;
    }
    if(retVal == null){
      throw new IllegalArgumentException("The unique Id was not present in this notation subsystem");
    }
    return retVal;
  }

  public IShapeObjectType getShapeObjectType(int uniqueId) {
    return this.shapes.get(uniqueId);
  }

  private <T extends IObjectType> T findObjectTypeByName(Collection<? extends T> otSet, String name){
    T retVal = null;
    for(T val : otSet){
      if(val.getName().equals(name)){
        retVal = val;
        break;
      }
    }
    return retVal;
  }
  
  public ILinkObjectType findLinkObjectTypeByName(String name) {
    return findObjectTypeByName(this.links.values(), name);
  }

  public IShapeObjectType findShapeObjectTypeByName(String name) {
    return findObjectTypeByName(this.shapes.values(), name);
  }

  public int numLinkObjectTypes() {
    return this.links.size();
  }

  public int numShapeObjectTypes() {
    return this.shapes.size();
  }

  public int numObjectTypes(){
    return this.numLinkObjectTypes() + this.numShapeObjectTypes() + NUM_ROOT_OTS;
  }

		private void createRMO(){
			this.rmo= new RootObjectType(0, "Root Object", "ROOT_OBJECT", this);
		}
		private void defineParentingRMO(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf, this.Compartment, this.Complex, this.GeneticUnit, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink, this.Source, this.Perturbation, this.Observable, this.Module, this.Port, this.Tag, this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.AndGate, this.OrGate, this.NotGate}));
			set.removeAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf}));
			for (IShapeObjectType child : set) {
			  this.rmo.getParentingRules().addChild(child);
			}

		}

	private void createState(){
	this.State.setDescription("State variable value");//ment to be TypeDescription rather
	this.State.getDefaultAttributes().setName("State");
	this.State.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.State.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.State.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.State.getDefaultAttributes().setLineWidth(1);
	this.State.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.State.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.State.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{25,25};
			this.State.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.State.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.State.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.State.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.State.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.State.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.State.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.State.getDefaultAttributes().setLineColourEditable(true);
	this.State.setEditableAttributes(editableAttributes);
	this.State.getDefaultAttributes().setUrl("");
	}

		private void defineParentingState(){
			this.State.getParentingRules().clear();
		}

		public ShapeObjectType getState(){
			return this.State;
		}
	private void createUnitOfInf(){
	this.UnitOfInf.setDescription("Auxiliary information box");//ment to be TypeDescription rather
	this.UnitOfInf.getDefaultAttributes().setName("UnitOfInf");
	this.UnitOfInf.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.UnitOfInf.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.UnitOfInf.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.UnitOfInf.getDefaultAttributes().setLineWidth(1);
	this.UnitOfInf.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.UnitOfInf.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.UnitOfInf.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);		int[] s=new int[]{65,25};
			this.UnitOfInf.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.UnitOfInf.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.UnitOfInf.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.UnitOfInf.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.UnitOfInf.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.UnitOfInf.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.UnitOfInf.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.UnitOfInf.getDefaultAttributes().setLineColourEditable(true);
	this.UnitOfInf.setEditableAttributes(editableAttributes);
	this.UnitOfInf.getDefaultAttributes().setUrl("");
	}

		private void defineParentingUnitOfInf(){
			this.UnitOfInf.getParentingRules().clear();
		}

		public ShapeObjectType getUnitOfInf(){
			return this.UnitOfInf;
		}

	private void createCompartment(){
	this.Compartment.setDescription("Functional compartment");//ment to be TypeDescription rather
	this.Compartment.getDefaultAttributes().setName("Compartment");
	this.Compartment.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Compartment.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Compartment.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Compartment.getDefaultAttributes().setLineWidth(3);
	this.Compartment.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Compartment.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Compartment.getDefaultAttributes().setShapeType(PrimitiveShapeType.IRREGULAR_ROUNDED_RECTANGLE);		int[] s=new int[]{200,200};
			this.Compartment.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Compartment.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Compartment.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Compartment.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Compartment.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Compartment.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Compartment.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Compartment.getDefaultAttributes().setLineColourEditable(true);
	this.Compartment.setEditableAttributes(editableAttributes);
	this.Compartment.getDefaultAttributes().setUrl("");
	IPropertyDefinition GOTerm=reassignVal(getPropGOTerm()," ",true,false);
	Compartment.getDefaultAttributes().addPropertyDefinition(GOTerm);
	  	
	 	this.Compartment.getDefaultAttributes().setUrl(" ");
	 
	}

		private void defineParentingCompartment(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf, this.Compartment, this.Complex, this.GeneticUnit, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink, this.Source, this.Perturbation, this.Observable, this.Module, this.Port, this.Tag, this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.AndGate, this.OrGate, this.NotGate}));
			set.removeAll(Arrays.asList(new IShapeObjectType[]{this.State}));
			for (IShapeObjectType child : set) {
			  this.Compartment.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getCompartment(){
			return this.Compartment;
		}
	private void createComplex(){
	this.Complex.setDescription("SBGN complex");//ment to be TypeDescription rather
	this.Complex.getDefaultAttributes().setName("Complex");
	this.Complex.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Complex.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Complex.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Complex.getDefaultAttributes().setLineWidth(1);
	this.Complex.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Complex.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Complex.getDefaultAttributes().setShapeType(PrimitiveShapeType.IRREGULAR_OCTAGON);		int[] s=new int[]{120,80};
			this.Complex.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Complex.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Complex.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Complex.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Complex.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Complex.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Complex.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Complex.getDefaultAttributes().setLineColourEditable(true);
	this.Complex.setEditableAttributes(editableAttributes);
	this.Complex.getDefaultAttributes().setUrl("");
	 	
	 	this.Complex.getDefaultAttributes().setUrl(" ");
	 
	}

		private void defineParentingComplex(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf, this.Macromolecule, this.SimpleChem, this.Complex}));
			for (IShapeObjectType child : set) {
			  this.Complex.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getComplex(){
			return this.Complex;
		}
	private void createGeneticUnit(){
	this.GeneticUnit.setDescription("Unit of genetical information");//ment to be TypeDescription rather
	this.GeneticUnit.getDefaultAttributes().setName("Genetic unit");
	this.GeneticUnit.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.GeneticUnit.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.GeneticUnit.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.GeneticUnit.getDefaultAttributes().setLineWidth(1);
	this.GeneticUnit.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.GeneticUnit.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.GeneticUnit.getDefaultAttributes().setShapeType(PrimitiveShapeType.ROUNDED_RECTANGLE);		int[] s=new int[]{60,40};
			this.GeneticUnit.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.GeneticUnit.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.GeneticUnit.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.GeneticUnit.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.GeneticUnit.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.GeneticUnit.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.GeneticUnit.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.GeneticUnit.getDefaultAttributes().setLineColourEditable(true);
	this.GeneticUnit.setEditableAttributes(editableAttributes);
	this.GeneticUnit.getDefaultAttributes().setUrl("");
	}

		private void defineParentingGeneticUnit(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf}));
			for (IShapeObjectType child : set) {
			  this.GeneticUnit.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getGeneticUnit(){
			return this.GeneticUnit;
		}
	private void createMacromolecule(){
	this.Macromolecule.setDescription("Macromolecule");//ment to be TypeDescription rather
	this.Macromolecule.getDefaultAttributes().setName("Macromolecule");
	this.Macromolecule.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Macromolecule.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Macromolecule.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Macromolecule.getDefaultAttributes().setLineWidth(1);
	this.Macromolecule.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Macromolecule.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Macromolecule.getDefaultAttributes().setShapeType(PrimitiveShapeType.MACROMOLECULE);		int[] s=new int[]{60,40};
			this.Macromolecule.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Macromolecule.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Macromolecule.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Macromolecule.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Macromolecule.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Macromolecule.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Macromolecule.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Macromolecule.getDefaultAttributes().setLineColourEditable(true);
	this.Macromolecule.setEditableAttributes(editableAttributes);
	this.Macromolecule.getDefaultAttributes().setUrl("");
	IPropertyDefinition cardinalityProp=new NumberPropertyDefinition("Cardinality", new BigDecimal(1.0),false,true);
	this.Macromolecule.getDefaultAttributes().addPropertyDefinition(cardinalityProp);
	IPropertyDefinition nameProp=new PlainTextPropertyDefinition("Name", "aMacromol",true,true);
	this.Macromolecule.getDefaultAttributes().addPropertyDefinition(nameProp);
	IPropertyDefinition SBOTerm=new PlainTextPropertyDefinition("SBO"," ",false,true);
	this.Macromolecule.getDefaultAttributes().addPropertyDefinition(SBOTerm);
	 
	}

		private void defineParentingMacromolecule(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.State, this.UnitOfInf}));
			for (IShapeObjectType child : set) {
			  this.Macromolecule.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getMacromolecule(){
			return this.Macromolecule;
		}

	private void createSimpleChem(){
	this.SimpleChem.setDescription("Simple chemical");//ment to be TypeDescription rather
	this.SimpleChem.getDefaultAttributes().setName(" ");
	this.SimpleChem.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.SimpleChem.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.SimpleChem.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.SimpleChem.getDefaultAttributes().setLineWidth(1);
	this.SimpleChem.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.SimpleChem.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.SimpleChem.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{30,30};
			this.SimpleChem.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.SimpleChem.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.SimpleChem.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.SimpleChem.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.SimpleChem.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.SimpleChem.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.SimpleChem.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.SimpleChem.getDefaultAttributes().setLineColourEditable(true);
	this.SimpleChem.setEditableAttributes(editableAttributes);
	this.SimpleChem.getDefaultAttributes().setUrl("");
	IPropertyDefinition Label=reassignVal(getPropLabel(),"Simple chemical",true,false);
	SimpleChem.getDefaultAttributes().addPropertyDefinition(Label);
	  	
	 	this.SimpleChem.getDefaultAttributes().setName("Compound");
	 
	}

		private void defineParentingSimpleChem(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{}));
			for (IShapeObjectType child : set) {
			  this.SimpleChem.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getSimpleChem(){
			return this.SimpleChem;
		}
	private void createUnspecEntity(){
	this.UnspecEntity.setDescription("Unspecified entity");//ment to be TypeDescription rather
	this.UnspecEntity.getDefaultAttributes().setName("Unspecified entity");
	this.UnspecEntity.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.UnspecEntity.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.UnspecEntity.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.UnspecEntity.getDefaultAttributes().setLineWidth(1);
	this.UnspecEntity.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.UnspecEntity.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.UnspecEntity.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{60,40};
			this.UnspecEntity.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.UnspecEntity.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.UnspecEntity.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.UnspecEntity.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.UnspecEntity.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.UnspecEntity.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.UnspecEntity.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.UnspecEntity.getDefaultAttributes().setLineColourEditable(true);
	this.UnspecEntity.setEditableAttributes(editableAttributes);
	this.UnspecEntity.getDefaultAttributes().setUrl("");
	}

		private void defineParentingUnspecEntity(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{}));
			for (IShapeObjectType child : set) {
			  this.UnspecEntity.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getUnspecEntity(){
			return this.UnspecEntity;
		}
	private void createSink(){
	this.Sink.setDescription("Sink");//ment to be TypeDescription rather
	this.Sink.getDefaultAttributes().setName("Sink");
	this.Sink.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Sink.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Sink.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Sink.getDefaultAttributes().setLineWidth(1);
	this.Sink.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Sink.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Sink.getDefaultAttributes().setShapeType(PrimitiveShapeType.EMPTY_SET);		int[] s=new int[]{30,30};
			this.Sink.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Sink.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Sink.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Sink.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Sink.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Sink.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Sink.getDefaultAttributes().setLineColourEditable(true);
	this.Sink.setEditableAttributes(editableAttributes);
	this.Sink.getDefaultAttributes().setUrl("");
	}

		private void defineParentingSink(){
			this.Sink.getParentingRules().clear();
		}

		public ShapeObjectType getSink(){
			return this.Sink;
		}
	private void createSource(){
	this.Source.setDescription("Source");//ment to be TypeDescription rather
	this.Source.getDefaultAttributes().setName("Source");
	this.Source.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Source.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Source.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Source.getDefaultAttributes().setLineWidth(1);
	this.Source.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Source.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Source.getDefaultAttributes().setShapeType(PrimitiveShapeType.EMPTY_SET);		int[] s=new int[]{30,30};
			this.Source.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Source.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Source.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Source.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Source.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Source.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Source.getDefaultAttributes().setLineColourEditable(true);
	this.Source.setEditableAttributes(editableAttributes);
	this.Source.getDefaultAttributes().setUrl("");
	}

		private void defineParentingSource(){
			this.Source.getParentingRules().clear();
		}

		public ShapeObjectType getSource(){
			return this.Source;
		}
	private void createPerturbation(){
	this.Perturbation.setDescription("Perturbation");//ment to be TypeDescription rather
	this.Perturbation.getDefaultAttributes().setName("Perturbation");
	this.Perturbation.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Perturbation.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Perturbation.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Perturbation.getDefaultAttributes().setLineWidth(1);
	this.Perturbation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Perturbation.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Perturbation.getDefaultAttributes().setShapeType(PrimitiveShapeType.XSHAPE);		int[] s=new int[]{80,60};
			this.Perturbation.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Perturbation.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Perturbation.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Perturbation.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Perturbation.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Perturbation.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Perturbation.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Perturbation.getDefaultAttributes().setLineColourEditable(true);
	this.Perturbation.setEditableAttributes(editableAttributes);
	this.Perturbation.getDefaultAttributes().setUrl("");
	}

		private void defineParentingPerturbation(){
			this.Perturbation.getParentingRules().clear();
		}

		public ShapeObjectType getPerturbation(){
			return this.Perturbation;
		}
	private void createObservable(){
	this.Observable.setDescription("Observable");//ment to be TypeDescription rather
	this.Observable.getDefaultAttributes().setName("Observable");
	this.Observable.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Observable.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Observable.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Observable.getDefaultAttributes().setLineWidth(1);
	this.Observable.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Observable.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Observable.getDefaultAttributes().setShapeType(PrimitiveShapeType.HEXAGON);		int[] s=new int[]{80,60};
			this.Observable.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Observable.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Observable.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Observable.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Observable.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Observable.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Observable.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Observable.getDefaultAttributes().setLineColourEditable(true);
	this.Observable.setEditableAttributes(editableAttributes);
	this.Observable.getDefaultAttributes().setUrl("");
	}

		private void defineParentingObservable(){
			this.Observable.getParentingRules().clear();
		}

		public ShapeObjectType getObservable(){
			return this.Observable;
		}
	private void createModule(){
	this.Module.setDescription("collapsed part of diagram");//ment to be TypeDescription rather
	this.Module.getDefaultAttributes().setName("Submap");
	this.Module.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Module.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Module.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Module.getDefaultAttributes().setLineWidth(1);
	this.Module.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Module.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Module.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);		int[] s=new int[]{120,120};
			this.Module.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Module.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Module.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Module.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Module.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Module.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Module.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Module.getDefaultAttributes().setLineColourEditable(true);
	this.Module.setEditableAttributes(editableAttributes);
	this.Module.getDefaultAttributes().setUrl("");
	}

		private void defineParentingModule(){
			HashSet<IShapeObjectType> set=new HashSet<IShapeObjectType>();
			set.addAll(Arrays.asList(new IShapeObjectType[]{this.Port, this.UnitOfInf}));
			for (IShapeObjectType child : set) {
			  this.Module.getParentingRules().addChild(child);
			}

		}

		public ShapeObjectType getModule(){
			return this.Module;
		}
	private void createPort(){
	this.Port.setDescription("Interface to the submap");//ment to be TypeDescription rather
	this.Port.getDefaultAttributes().setName("SubmapPort");
	this.Port.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Port.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Port.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Port.getDefaultAttributes().setLineWidth(1);
	this.Port.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Port.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Port.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);		int[] s=new int[]{20,35};
			this.Port.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Port.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Port.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Port.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Port.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Port.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Port.getDefaultAttributes().setLineColourEditable(true);
	this.Port.setEditableAttributes(editableAttributes);
	this.Port.getDefaultAttributes().setUrl("");
	}

		private void defineParentingPort(){
			this.Port.getParentingRules().clear();
		}

		public ShapeObjectType getPort(){
			return this.Port;
		}
	private void createTag(){
	this.Tag.setDescription("Mark node to be interface to submap");//ment to be TypeDescription rather
	this.Tag.getDefaultAttributes().setName("Tag");
	this.Tag.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Tag.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Tag.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Tag.getDefaultAttributes().setLineWidth(1);
	this.Tag.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Tag.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Tag.getDefaultAttributes().setShapeType(PrimitiveShapeType.RH_SIGN_ARROW);		int[] s=new int[]{40,20};
			this.Tag.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{255,255,255};
	this.Tag.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Tag.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Tag.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Tag.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Tag.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Tag.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Tag.getDefaultAttributes().setLineColourEditable(true);
	this.Tag.setEditableAttributes(editableAttributes);
	this.Tag.getDefaultAttributes().setUrl("");
	}

		private void defineParentingTag(){
			this.Tag.getParentingRules().clear();
		}

		public ShapeObjectType getTag(){
			return this.Tag;
		}
	private void createProcess(){
	this.Process.setDescription("Process node");//ment to be TypeDescription rather
	this.Process.getDefaultAttributes().setName("Process");
	this.Process.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);
	this.Process.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Process.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Process.getDefaultAttributes().setLineWidth(1);
	this.Process.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Process.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Process.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);		int[] s=new int[]{45,30};
			this.Process.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Process.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Process.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Process.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Process.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Process.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Process.getDefaultAttributes().setLineColourEditable(true);
	this.Process.setEditableAttributes(editableAttributes);
	this.Process.getDefaultAttributes().setUrl("");
	 	
	 	this.Process.getDefaultAttributes().setName(" ");
	 
	}

		private void defineParentingProcess(){
			this.Process.getParentingRules().clear();
		}

		public ShapeObjectType getProcess(){
			return this.Process;
		}
	private void createOmittedProcess(){
	this.OmittedProcess.setDescription("omitted process");//ment to be TypeDescription rather
	this.OmittedProcess.getDefaultAttributes().setName("Omitted process");
	this.OmittedProcess.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);
	this.OmittedProcess.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.OmittedProcess.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.OmittedProcess.getDefaultAttributes().setLineWidth(1);
	this.OmittedProcess.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.OmittedProcess.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.OmittedProcess.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);		int[] s=new int[]{45,30};
			this.OmittedProcess.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.OmittedProcess.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.OmittedProcess.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.OmittedProcess.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.OmittedProcess.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.OmittedProcess.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.OmittedProcess.getDefaultAttributes().setLineColourEditable(true);
	this.OmittedProcess.setEditableAttributes(editableAttributes);
	this.OmittedProcess.getDefaultAttributes().setUrl("");
	 	
	 	this.OmittedProcess.getDefaultAttributes().setName("//");
	 
	}

		private void defineParentingOmittedProcess(){
			this.OmittedProcess.getParentingRules().clear();
		}

		public ShapeObjectType getOmittedProcess(){
			return this.OmittedProcess;
		}
	private void createUncertainProcess(){
	this.UncertainProcess.setDescription("Uncertain process");//ment to be TypeDescription rather
	this.UncertainProcess.getDefaultAttributes().setName("Uncertain process");
	this.UncertainProcess.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);
	this.UncertainProcess.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.UncertainProcess.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.UncertainProcess.getDefaultAttributes().setLineWidth(1);
	this.UncertainProcess.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.UncertainProcess.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.UncertainProcess.getDefaultAttributes().setShapeType(PrimitiveShapeType.PROCESS);		int[] s=new int[]{45,30};
			this.UncertainProcess.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.UncertainProcess.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.UncertainProcess.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.UncertainProcess.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.UncertainProcess.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.UncertainProcess.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.UncertainProcess.getDefaultAttributes().setLineColourEditable(true);
	this.UncertainProcess.setEditableAttributes(editableAttributes);
	this.UncertainProcess.getDefaultAttributes().setUrl("");
	 	
	 	this.UncertainProcess.getDefaultAttributes().setName("?");
	 
	}

		private void defineParentingUncertainProcess(){
			this.UncertainProcess.getParentingRules().clear();
		}

		public ShapeObjectType getUncertainProcess(){
			return this.UncertainProcess;
		}
	private void createAssociation(){
	this.Association.setDescription("Association");//ment to be TypeDescription rather
	this.Association.getDefaultAttributes().setName("Association");
	this.Association.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Association.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Association.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Association.getDefaultAttributes().setLineWidth(1);
	this.Association.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Association.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Association.getDefaultAttributes().setShapeType(PrimitiveShapeType.ASSOCIATION);		int[] s=new int[]{20,20};
			this.Association.getDefaultAttributes().setSize(new Size(s[0],s[1]));int[] c=new int[]{0,0,0};
	this.Association.getDefaultAttributes().setFillColour(new RGB(c[0],c[1],c[2]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Association.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Association.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Association.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Association.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Association.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Association.getDefaultAttributes().setLineColourEditable(true);
	this.Association.setEditableAttributes(editableAttributes);
	this.Association.getDefaultAttributes().setUrl("");
	 	
	 	this.Association.getDefaultAttributes().setName(" ");
	 
	}

		private void defineParentingAssociation(){
			this.Association.getParentingRules().clear();
		}

		public ShapeObjectType getAssociation(){
			return this.Association;
		}
	private void createDissociation(){
	this.Dissociation.setDescription("Dissociation");//ment to be TypeDescription rather
	this.Dissociation.getDefaultAttributes().setName("Dissociation");
	this.Dissociation.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.Dissociation.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.Dissociation.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.Dissociation.getDefaultAttributes().setLineWidth(1);
	this.Dissociation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Dissociation.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Dissociation.getDefaultAttributes().setShapeType(PrimitiveShapeType.DISSOCIATION);		int[] s=new int[]{20,20};
			this.Dissociation.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.Dissociation.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.Dissociation.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.Dissociation.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.Dissociation.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.Dissociation.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.Dissociation.getDefaultAttributes().setLineColourEditable(true);
	this.Dissociation.setEditableAttributes(editableAttributes);
	this.Dissociation.getDefaultAttributes().setUrl("");
	 	
	 	this.Dissociation.getDefaultAttributes().setName(" ");
	 
	}

		private void defineParentingDissociation(){
			this.Dissociation.getParentingRules().clear();
		}

		public ShapeObjectType getDissociation(){
			return this.Dissociation;
		}
	private void createAndGate(){
	this.AndGate.setDescription("AndGate");//ment to be TypeDescription rather
	this.AndGate.getDefaultAttributes().setName("AND gate");
	this.AndGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.AndGate.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.AndGate.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.AndGate.getDefaultAttributes().setLineWidth(1);
	this.AndGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.AndGate.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.AndGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{40,40};
			this.AndGate.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.AndGate.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.AndGate.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.AndGate.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.AndGate.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.AndGate.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.AndGate.getDefaultAttributes().setLineColourEditable(true);
	this.AndGate.setEditableAttributes(editableAttributes);
	this.AndGate.getDefaultAttributes().setUrl("");
	 	
	 	this.AndGate.getDefaultAttributes().setName("AND");
	 
	}

		private void defineParentingAndGate(){
			this.AndGate.getParentingRules().clear();
		}

		public ShapeObjectType getAndGate(){
			return this.AndGate;
		}
	private void createOrGate(){
	this.OrGate.setDescription("OR logic Gate");//ment to be TypeDescription rather
	this.OrGate.getDefaultAttributes().setName("OR gate");
	this.OrGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.OrGate.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.OrGate.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.OrGate.getDefaultAttributes().setLineWidth(1);
	this.OrGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.OrGate.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.OrGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{40,40};
			this.OrGate.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.OrGate.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.OrGate.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.OrGate.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.OrGate.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.OrGate.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.OrGate.getDefaultAttributes().setLineColourEditable(true);
	this.OrGate.setEditableAttributes(editableAttributes);
	this.OrGate.getDefaultAttributes().setUrl("");
	 	
	 	this.OrGate.getDefaultAttributes().setName("OR");
	 
	}

		private void defineParentingOrGate(){
			this.OrGate.getParentingRules().clear();
		}

		public ShapeObjectType getOrGate(){
			return this.OrGate;
		}
	private void createNotGate(){
	this.NotGate.setDescription("NOT logic Gate");//ment to be TypeDescription rather
	this.NotGate.getDefaultAttributes().setName("NOT gate");
	this.NotGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.RECTANGLE);
	this.NotGate.getDefaultAttributes().setFillColour(new RGB(255,255,255));
	this.NotGate.getDefaultAttributes().setSize(new Size(20,20));
	int[] lc=new int[]{0,0,0};
	this.NotGate.getDefaultAttributes().setLineWidth(1);
	this.NotGate.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.NotGate.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.NotGate.getDefaultAttributes().setShapeType(PrimitiveShapeType.ELLIPSE);		int[] s=new int[]{40,40};
			this.NotGate.getDefaultAttributes().setSize(new Size(s[0],s[1]));

	EnumSet<EditableShapeAttributes> editableAttributes = EnumSet.noneOf(EditableShapeAttributes.class);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.FILL_COLOUR);
	}
	//this.NotGate.getDefaultAttributes().setFillEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_TYPE);
	}
	//this.NotGate.setPrimitiveShapeTypeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.SHAPE_SIZE);
	}
	//this.NotGate.setSizeEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_STYLE);
	}
	//this.NotGate.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_WIDTH);
	}
	//this.NotGate.getDefaultAttributes().setLineWidthEditable(true);
	if(true){
	    editableAttributes.add(EditableShapeAttributes.LINE_COLOUR);
	}
	//this.NotGate.getDefaultAttributes().setLineColourEditable(true);
	this.NotGate.setEditableAttributes(editableAttributes);
	this.NotGate.getDefaultAttributes().setUrl("");
	 	
	 	this.NotGate.getDefaultAttributes().setName("NOT");
	 
	}

		private void defineParentingNotGate(){
			this.NotGate.getParentingRules().clear();
		}

		public ShapeObjectType getNotGate(){
			return this.NotGate;
		}

	
	private void createConsumption(){
	Set<IShapeObjectType> set=null;
	this.Consumption.setDescription("Consumption is the arc used to represent the fact that an entity only affects a process, but is not affected by it");
	int[] lc=new int[]{0,0,0};
	this.Consumption.getDefaultAttributes().setLineWidth(1);
	this.Consumption.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Consumption.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Consumption.getDefaultAttributes().setName("Consumption Link");
	this.Consumption.getDefaultAttributes().setDescription("");
	this.Consumption.getDefaultAttributes().setDetailedDescription("");
	this.Consumption.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Consumption.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Consumption.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Consumption.getDefaultAttributes().setLineWidthEditable(true);
	this.Consumption.setEditableAttributes(editableAttributes);

	this.Consumption.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Consumption.getLinkSource();
	//LinkEndDefinition tport=this.Consumption.getLinkTarget();
	LinkTerminusDefinition sport=this.Consumption.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Consumption.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)0);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	tport.getDefaultAttributes().setEndSize(new Size(8,8));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Consumption.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Consumption.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Consumption.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Consumption.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Consumption.getLinkConnectionRules().addConnection(this.Source, tgt);
	}

	}

	public LinkObjectType getConsumption(){
		return this.Consumption;
	}
	private void createProduction(){
	Set<IShapeObjectType> set=null;
	this.Production.setDescription("Production is the arc used to represent the fact that an entity is produced by a process.");
	int[] lc=new int[]{0,0,0};
	this.Production.getDefaultAttributes().setLineWidth(1);
	this.Production.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Production.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Production.getDefaultAttributes().setName("Production Link");
	this.Production.getDefaultAttributes().setDescription("");
	this.Production.getDefaultAttributes().setDetailedDescription("");
	this.Production.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Production.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Production.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Production.getDefaultAttributes().setLineWidthEditable(true);
	this.Production.setEditableAttributes(editableAttributes);

	this.Production.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Production.getLinkSource();
	//LinkEndDefinition tport=this.Production.getLinkTarget();
	LinkTerminusDefinition sport=this.Production.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Production.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)0);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.TRIANGLE);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Complex, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink}));
	for (IShapeObjectType tgt : set) {
	  this.Production.getLinkConnectionRules().addConnection(this.Process, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Complex, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink}));
	for (IShapeObjectType tgt : set) {
	  this.Production.getLinkConnectionRules().addConnection(this.OmittedProcess, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Complex, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink}));
	for (IShapeObjectType tgt : set) {
	  this.Production.getLinkConnectionRules().addConnection(this.UncertainProcess, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Complex, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink}));
	for (IShapeObjectType tgt : set) {
	  this.Production.getLinkConnectionRules().addConnection(this.Association, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Complex, this.Macromolecule, this.SimpleChem, this.UnspecEntity, this.Sink}));
	for (IShapeObjectType tgt : set) {
	  this.Production.getLinkConnectionRules().addConnection(this.Dissociation, tgt);
	}

	}

	public LinkObjectType getProduction(){
		return this.Production;
	}
	private void createModulation(){
	Set<IShapeObjectType> set=null;
	this.Modulation.setDescription("A modulation affects the flux of a process represented by the target transition.");
	int[] lc=new int[]{0,0,0};
	this.Modulation.getDefaultAttributes().setLineWidth(1);
	this.Modulation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Modulation.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Modulation.getDefaultAttributes().setName("Modulation Link");
	this.Modulation.getDefaultAttributes().setDescription("");
	this.Modulation.getDefaultAttributes().setDetailedDescription("");
	this.Modulation.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Modulation.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Modulation.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Modulation.getDefaultAttributes().setLineWidthEditable(true);
	this.Modulation.setEditableAttributes(editableAttributes);

	this.Modulation.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Modulation.getLinkSource();
	//LinkEndDefinition tport=this.Modulation.getLinkTarget();
	LinkTerminusDefinition sport=this.Modulation.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Modulation.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.EMPTY_DIAMOND);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Modulation.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getModulation(){
		return this.Modulation;
	}
	private void createStimulation(){
	Set<IShapeObjectType> set=null;
	this.Stimulation.setDescription("A stimulation affects positively the flux of a process represented by the target transition.");
	int[] lc=new int[]{0,0,0};
	this.Stimulation.getDefaultAttributes().setLineWidth(1);
	this.Stimulation.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Stimulation.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Stimulation.getDefaultAttributes().setName("Stimulation Link");
	this.Stimulation.getDefaultAttributes().setDescription("");
	this.Stimulation.getDefaultAttributes().setDetailedDescription("");
	this.Stimulation.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Stimulation.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Stimulation.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Stimulation.getDefaultAttributes().setLineWidthEditable(true);
	this.Stimulation.setEditableAttributes(editableAttributes);

	this.Stimulation.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Stimulation.getLinkSource();
	//LinkEndDefinition tport=this.Stimulation.getLinkTarget();
	LinkTerminusDefinition sport=this.Stimulation.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Stimulation.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.EMPTY_TRIANGLE);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation, this.Observable}));
	for (IShapeObjectType tgt : set) {
	  this.Stimulation.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getStimulation(){
		return this.Stimulation;
	}
	private void createCatalysis(){
	Set<IShapeObjectType> set=null;
	this.Catalysis.setDescription("A catalysis is a particular case of stimulation.");
	int[] lc=new int[]{0,0,0};
	this.Catalysis.getDefaultAttributes().setLineWidth(1);
	this.Catalysis.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Catalysis.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Catalysis.getDefaultAttributes().setName("Catalysis Link");
	this.Catalysis.getDefaultAttributes().setDescription("");
	this.Catalysis.getDefaultAttributes().setDetailedDescription("");
	this.Catalysis.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Catalysis.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Catalysis.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Catalysis.getDefaultAttributes().setLineWidthEditable(true);
	this.Catalysis.setEditableAttributes(editableAttributes);

	this.Catalysis.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Catalysis.getLinkSource();
	//LinkEndDefinition tport=this.Catalysis.getLinkTarget();
	LinkTerminusDefinition sport=this.Catalysis.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Catalysis.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)10);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.EMPTY_CIRCLE);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Catalysis.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getCatalysis(){
		return this.Catalysis;
	}
	private void createInhibition(){
	Set<IShapeObjectType> set=null;
	this.Inhibition.setDescription("An inhibition affects negatively the flux of a process represented by the target transition.");
	int[] lc=new int[]{0,0,0};
	this.Inhibition.getDefaultAttributes().setLineWidth(1);
	this.Inhibition.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Inhibition.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Inhibition.getDefaultAttributes().setName("Inhibition Link");
	this.Inhibition.getDefaultAttributes().setDescription("");
	this.Inhibition.getDefaultAttributes().setDetailedDescription("");
	this.Inhibition.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Inhibition.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Inhibition.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Inhibition.getDefaultAttributes().setLineWidthEditable(true);
	this.Inhibition.setEditableAttributes(editableAttributes);

	this.Inhibition.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Inhibition.getLinkSource();
	//LinkEndDefinition tport=this.Inhibition.getLinkTarget();
	LinkTerminusDefinition sport=this.Inhibition.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Inhibition.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.BAR);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Inhibition.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getInhibition(){
		return this.Inhibition;
	}
	private void createTrigger(){
	Set<IShapeObjectType> set=null;
	this.Trigger.setDescription("A trigger effect, or absolute stimulation, is a stimulation that is necessary for a process to take place.");
	int[] lc=new int[]{0,0,0};
	this.Trigger.getDefaultAttributes().setLineWidth(1);
	this.Trigger.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.Trigger.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.Trigger.getDefaultAttributes().setName("Trigger Link");
	this.Trigger.getDefaultAttributes().setDescription("");
	this.Trigger.getDefaultAttributes().setDetailedDescription("");
	this.Trigger.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.Trigger.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.Trigger.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.Trigger.getDefaultAttributes().setLineWidthEditable(true);
	this.Trigger.setEditableAttributes(editableAttributes);

	this.Trigger.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.Trigger.getLinkSource();
	//LinkEndDefinition tport=this.Trigger.getLinkTarget();
	LinkTerminusDefinition sport=this.Trigger.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.Trigger.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.TRIANGLE_BAR);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);
	IPropertyDefinition Stoich=reassignVal(getPropStoich(),"1",true,false);
	tport.getDefaultAttributes().addPropertyDefinition(Stoich);
	 
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Process, this.OmittedProcess, this.UncertainProcess, this.Association, this.Dissociation}));
	for (IShapeObjectType tgt : set) {
	  this.Trigger.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getTrigger(){
		return this.Trigger;
	}
	private void createLogicArc(){
	Set<IShapeObjectType> set=null;
	this.LogicArc.setDescription("Logic arc is the arc used to represent the fact that an entity influences outcome of logic operator.");
	int[] lc=new int[]{0,0,0};
	this.LogicArc.getDefaultAttributes().setLineWidth(1);
	this.LogicArc.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.LogicArc.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.LogicArc.getDefaultAttributes().setName("Logic arc");
	this.LogicArc.getDefaultAttributes().setDescription("");
	this.LogicArc.getDefaultAttributes().setDetailedDescription("");
	this.LogicArc.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.LogicArc.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.LogicArc.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.LogicArc.getDefaultAttributes().setLineWidthEditable(true);
	this.LogicArc.setEditableAttributes(editableAttributes);

	this.LogicArc.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.LogicArc.getLinkSource();
	//LinkEndDefinition tport=this.LogicArc.getLinkTarget();
	LinkTerminusDefinition sport=this.LogicArc.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.LogicArc.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)0);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.TRIANGLE_BAR);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);

	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.AndGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.OrGate, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.AndGate, this.OrGate, this.NotGate}));
	for (IShapeObjectType tgt : set) {
	  this.LogicArc.getLinkConnectionRules().addConnection(this.NotGate, tgt);
	}

	}

	public LinkObjectType getLogicArc(){
		return this.LogicArc;
	}
	private void createEquivalenceArc(){
	Set<IShapeObjectType> set=null;
	this.EquivalenceArc.setDescription("Equivalence Arc is the arc used to represent the fact that all entities marked by the tag are equivalent.");
	int[] lc=new int[]{0,0,0};
	this.EquivalenceArc.getDefaultAttributes().setLineWidth(1);
	this.EquivalenceArc.getDefaultAttributes().setLineStyle(LineStyle.SOLID);
	this.EquivalenceArc.getDefaultAttributes().setLineColour(new RGB(lc[0],lc[1],lc[2]));
	this.EquivalenceArc.getDefaultAttributes().setName("Equivalence Arc");
	this.EquivalenceArc.getDefaultAttributes().setDescription("");
	this.EquivalenceArc.getDefaultAttributes().setDetailedDescription("");
	this.EquivalenceArc.getDefaultAttributes().setRouter(ConnectionRouter.SHORTEST_PATH);
	EnumSet<LinkEditableAttributes> editableAttributes = EnumSet.noneOf(LinkEditableAttributes.class);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.COLOUR);
	}
	//this.EquivalenceArc.getDefaultAttributes().setLineColourEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_STYLE);
	}
	//this.EquivalenceArc.getDefaultAttributes().setLineStyleEditable(true);
	if(true){
	  editableAttributes.add(LinkEditableAttributes.LINE_WIDTH);
	}
	//this.EquivalenceArc.getDefaultAttributes().setLineWidthEditable(true);
	this.EquivalenceArc.setEditableAttributes(editableAttributes);

	this.EquivalenceArc.getDefaultAttributes().setUrl("");
	//LinkEndDefinition sport=this.EquivalenceArc.getLinkSource();
	//LinkEndDefinition tport=this.EquivalenceArc.getLinkTarget();
	LinkTerminusDefinition sport=this.EquivalenceArc.getSourceTerminusDefinition();
	LinkTerminusDefinition tport=this.EquivalenceArc.getTargetTerminusDefinition();
	sport.getDefaultAttributes().setGap((short)5);
	sport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 8,8);
	sport.getDefaultAttributes().setEndSize(new Size(8,8));
	sport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	sport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] csport=new int[]{255,255,255};
	sport.getDefaultAttributes().setTermColour(new RGB(csport[0],csport[1],csport[2]));
	//sport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editablesportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//sport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editablesportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//sport.getDefaultAttributes().setColourEditable(true);
	sport.setEditableAttributes(editablesportAttributes);
	tport.getDefaultAttributes().setGap((short)5);
	tport.getDefaultAttributes().setEndDecoratorType(LinkEndDecoratorShape.NONE);//, 5,5);
	tport.getDefaultAttributes().setEndSize(new Size(5,5));
	tport.getDefaultAttributes().setTermDecoratorType(PrimitiveShapeType.RECTANGLE);
	tport.getDefaultAttributes().setTermSize(new Size(0,0));
	int[] ctport=new int[]{255,255,255};
	tport.getDefaultAttributes().setTermColour(new RGB(ctport[0],ctport[1],ctport[2]));
	//tport.getDefaultAttributes().setLineProperties(0, LineStyle.SOLID);
	EnumSet<LinkTermEditableAttributes> editabletportAttributes = EnumSet.of(LinkTermEditableAttributes.END_SIZE, LinkTermEditableAttributes.OFFSET,
	                  LinkTermEditableAttributes.TERM_DECORATOR_TYPE, LinkTermEditableAttributes.TERM_SIZE);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.END_DECORATOR_TYPE);
	}
	//tport.getDefaultAttributes().setShapeTypeEditable(true);
	if(true){
	  editabletportAttributes.add(LinkTermEditableAttributes.TERM_COLOUR);
	}
	//tport.getDefaultAttributes().setColourEditable(true);
	tport.setEditableAttributes(editabletportAttributes);

	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Tag, this.Port}));
	for (IShapeObjectType tgt : set) {
	  this.EquivalenceArc.getLinkConnectionRules().addConnection(this.Complex, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Tag, this.Port}));
	for (IShapeObjectType tgt : set) {
	  this.EquivalenceArc.getLinkConnectionRules().addConnection(this.Macromolecule, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Tag, this.Port}));
	for (IShapeObjectType tgt : set) {
	  this.EquivalenceArc.getLinkConnectionRules().addConnection(this.SimpleChem, tgt);
	}
	set=new HashSet<IShapeObjectType>();
	set.addAll(Arrays.asList(new IShapeObjectType[]{this.Tag, this.Port}));
	for (IShapeObjectType tgt : set) {
	  this.EquivalenceArc.getLinkConnectionRules().addConnection(this.UnspecEntity, tgt);
	}

	}

	public LinkObjectType getEquivalenceArc(){
		return this.EquivalenceArc;
	}
	

	private IPropertyDefinition getPropGOTerm(){
		IPropertyDefinition GOTerm=new PlainTextPropertyDefinition("GO term"," ",false,true);
		return GOTerm;
	}
	private IPropertyDefinition getPropSBOTerm(){
		IPropertyDefinition SBOTerm=new PlainTextPropertyDefinition("SBO"," ",false,true);
		return SBOTerm;
	}
	private IPropertyDefinition getPropLabel(){
		IPropertyDefinition Label=new FormattedTextPropertyDefinition("Label"," ",true,true);
		return Label;
	}
	private IPropertyDefinition getPropStoich(){
		IPropertyDefinition Stoich=new PlainTextPropertyDefinition("STOICH"," ",true,true);
		return Stoich;
	}


}

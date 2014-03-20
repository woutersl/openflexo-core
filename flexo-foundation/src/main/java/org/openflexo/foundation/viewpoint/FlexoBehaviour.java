/*
 * (c) Copyright 2010-2011 AgileBirds
 *
 * This file is part of OpenFlexo.
 *
 * OpenFlexo is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenFlexo is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenFlexo. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openflexo.foundation.viewpoint;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingModel;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.antar.binding.Function;
import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.viewpoint.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.viewpoint.binding.PatternRoleBindingVariable;
import org.openflexo.foundation.viewpoint.editionaction.AssignableAction;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.DeserializationFinalizer;
import org.openflexo.model.annotations.Finder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.Import;
import org.openflexo.model.annotations.Imports;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.toolbox.ChainedCollection;
import org.openflexo.toolbox.StringUtils;

/**
 * An FlexoBehaviour represents a behavioural feature attached to an FlexoConcept
 * 
 * @author sylvain
 * 
 */
@ModelEntity(isAbstract = true)
@ImplementationClass(FlexoBehaviour.FlexoBehaviourImpl.class)
@Imports({ @Import(ActionScheme.class), @Import(DeletionScheme.class), @Import(NavigationScheme.class),
		@Import(SynchronizationScheme.class), @Import(CreationScheme.class), @Import(CloningScheme.class) })
public interface FlexoBehaviour extends FlexoBehaviourObject, ActionContainer, Function {

	public static final String THIS = "this";
	public static final String VIRTUAL_MODEL_INSTANCE = "virtualModelInstance";

	@PropertyIdentifier(type = FlexoConcept.class)
	public static final String FLEXO_CONCEPT_KEY = "flexoConcept";
	@PropertyIdentifier(type = String.class)
	public static final String NAME_KEY = "name";
	@PropertyIdentifier(type = String.class)
	public static final String LABEL_KEY = "label";
	@PropertyIdentifier(type = boolean.class)
	public static final String SKIP_CONFIRMATION_PANEL_KEY = "skipConfirmationPanel";
	@PropertyIdentifier(type = boolean.class)
	public static final String DEFINE_POPUP_DEFAULT_SIZE_KEY = "definePopupDefaultSize";
	@PropertyIdentifier(type = int.class)
	public static final String WIDTH_KEY = "width";
	@PropertyIdentifier(type = int.class)
	public static final String HEIGHT_KEY = "height";
	@PropertyIdentifier(type = String.class)
	public static final String DESCRIPTION_KEY = "description";
	@PropertyIdentifier(type = Vector.class)
	public static final String PARAMETERS_KEY = "parameters";

	@Override
	@Getter(value = FLEXO_CONCEPT_KEY, inverse = FlexoConcept.EDITION_SCHEMES_KEY)
	public FlexoConcept getFlexoConcept();

	@Setter(FLEXO_CONCEPT_KEY)
	public void setFlexoConcept(FlexoConcept flexoConcept);

	@Override
	@Getter(value = NAME_KEY)
	@XMLAttribute
	public String getName();

	@Override
	@Setter(NAME_KEY)
	public void setName(String name);

	@Getter(value = LABEL_KEY)
	@XMLAttribute
	public String getLabel();

	@Setter(LABEL_KEY)
	public void setLabel(String label);

	@Getter(value = SKIP_CONFIRMATION_PANEL_KEY, defaultValue = "false")
	@XMLAttribute
	public boolean getSkipConfirmationPanel();

	@Setter(SKIP_CONFIRMATION_PANEL_KEY)
	public void setSkipConfirmationPanel(boolean skipConfirmationPanel);

	@Getter(value = DEFINE_POPUP_DEFAULT_SIZE_KEY, defaultValue = "false")
	@XMLAttribute
	public boolean getDefinePopupDefaultSize();

	@Setter(DEFINE_POPUP_DEFAULT_SIZE_KEY)
	public void setDefinePopupDefaultSize(boolean definePopupDefaultSize);

	@Getter(value = WIDTH_KEY, defaultValue = "0")
	@XMLAttribute
	public int getWidth();

	@Setter(WIDTH_KEY)
	public void setWidth(int width);

	@Getter(value = HEIGHT_KEY, defaultValue = "0")
	@XMLAttribute
	public int getHeight();

	@Setter(HEIGHT_KEY)
	public void setHeight(int height);

	@Override
	@Getter(value = DESCRIPTION_KEY)
	@XMLElement
	public String getDescription();

	@Override
	@Setter(DESCRIPTION_KEY)
	public void setDescription(String description);

	@Getter(value = PARAMETERS_KEY, cardinality = Cardinality.LIST, inverse = FlexoBehaviourParameter.FLEXO_BEHAVIOUR_SCHEME_KEY)
	@XMLElement
	public List<FlexoBehaviourParameter> getParameters();

	@Setter(PARAMETERS_KEY)
	public void setParameters(List<FlexoBehaviourParameter> parameters);

	@Adder(PARAMETERS_KEY)
	public void addToParameters(FlexoBehaviourParameter aParameter);

	@Remover(PARAMETERS_KEY)
	public void removeFromParameters(FlexoBehaviourParameter aParameter);

	@Finder(collection = PARAMETERS_KEY, attribute = FlexoBehaviourParameter.NAME_KEY)
	public FlexoBehaviourParameter getParameter(String name);

	@DeserializationFinalizer
	public void finalizeEditionSchemeDeserialization();

	public void updateBindingModels();

	public FlexoBehaviourType getFlexoBehaviourType();

	public FlexoBehaviourActionType getFlexoBehaviourActionType();

	public FlexoBehaviourParametersType getFlexoBehaviourParametersType();

	public FlexoBehaviourParametersValuesType getFlexoBehaviourParametersValuesType();

	public FlexoBehaviourParameters getFlexoBehaviourParameters();

	public String getSignature();

	@Override
	public List<FlexoBehaviourParameter> getArguments();

	public String getAvailableParameterName(String baseName);

	public void parameterFirst(FlexoBehaviourParameter p);

	public void parameterUp(FlexoBehaviourParameter p);

	public void parameterDown(FlexoBehaviourParameter p);

	public void parameterLast(FlexoBehaviourParameter p);

	public static abstract class FlexoBehaviourImpl extends FlexoBehaviourObjectImpl implements FlexoBehaviour {

		protected BindingModel _bindingModel;

		//
		protected static final Logger logger = FlexoLogger.getLogger(FlexoBehaviour.class.getPackage().getName());

		private String name;
		private String label;
		private String description;
		// private Vector<EditionAction<?, ?>> actions;
		// private Vector<FlexoBehaviourParameter> parameters;
		private boolean skipConfirmationPanel = false;

		// private FlexoConcept _flexoConcept;

		private FlexoBehaviourParameters flexoBehaviourParameters;

		private boolean definePopupDefaultSize = false;
		private int width = 800;
		private int height = 600;

		private final FlexoBehaviourType flexoBehaviourType = new FlexoBehaviourType(this);
		private final FlexoBehaviourActionType flexoBehaviourActionType = new FlexoBehaviourActionType(this);
		private final FlexoBehaviourParametersType flexoBehaviourParametersType = new FlexoBehaviourParametersType(this);
		private final FlexoBehaviourParametersValuesType flexoBehaviourParametersValuesType = new FlexoBehaviourParametersValuesType(this);

		/**
		 * Stores a chained collections of objects which are involved in validation
		 */
		private final ChainedCollection<ViewPointObject> validableObjects = null;

		@Override
		public FlexoBehaviourType getFlexoBehaviourType() {
			return flexoBehaviourType;
		}

		@Override
		public FlexoBehaviourActionType getFlexoBehaviourActionType() {
			return flexoBehaviourActionType;
		}

		@Override
		public FlexoBehaviourParametersType getFlexoBehaviourParametersType() {
			return flexoBehaviourParametersType;
		}

		@Override
		public FlexoBehaviourParametersValuesType getFlexoBehaviourParametersValuesType() {
			return flexoBehaviourParametersValuesType;
		}

		@Override
		public FlexoBehaviourParameters getFlexoBehaviourParameters() {
			if (flexoBehaviourParameters == null && getVirtualModelFactory() != null) {
				flexoBehaviourParameters = getVirtualModelFactory().newFlexoBehaviourParameters(this);
			}
			return flexoBehaviourParameters;
		}

		@Override
		public String getStringRepresentation() {
			return (getFlexoConcept() != null ? getFlexoConcept().getStringRepresentation() : "null") + "." + getName();
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			out.append(getClass().getSimpleName() + " " + getName() + "(" + getParametersFMLRepresentation(context) + ") {", context);
			out.append(StringUtils.LINE_SEPARATOR, context);
			for (EditionAction action : getActions()) {
				out.append(action.getFMLRepresentation(context), context, 1);
				out.append(StringUtils.LINE_SEPARATOR, context);
			}
			out.append("}", context);
			out.append(StringUtils.LINE_SEPARATOR, context);
			return out.toString();
		}

		protected String getParametersFMLRepresentation(FMLRepresentationContext context) {
			if (getParameters().size() > 0) {
				StringBuffer sb = new StringBuffer();
				boolean isFirst = true;
				for (FlexoBehaviourParameter p : getParameters()) {
					sb.append((isFirst ? "" : ", ") + TypeUtils.simpleRepresentation(p.getType()) + " " + p.getName());
					isFirst = false;
				}
				return sb.toString();
			}
			return "";
		}

		/**
		 * Return the URI of the {@link NamedViewPointObject}<br>
		 * The convention for URI are following: <viewpoint_uri>/<virtual_model_name>#<flexo_concept_name>.<edition_scheme_name> <br>
		 * eg<br>
		 * http://www.mydomain.org/MyViewPoint/MyVirtualModel#MyFlexoConcept.MyEditionScheme
		 * 
		 * @return String representing unique URI of this object
		 */
		@Override
		public String getURI() {
			return getFlexoConcept().getURI() + "." + getName();
		}

		@Override
		public FlexoBehaviourImpl getFlexoBehaviour() {
			return this;
		}

		@Override
		public String getLabel() {
			if (label == null || StringUtils.isEmpty(label) || label.equals(name)) {
				return getName();
			}
			return label;
		}

		@Override
		public void setLabel(String label) {
			this.label = label;
		}

		/*@Override
		public FlexoConcept getFlexoConcept() {
			return _flexoConcept;
		}*/

		@Override
		public void setFlexoConcept(FlexoConcept flexoConcept) {
			performSuperSetter(FLEXO_CONCEPT_KEY, flexoConcept);
			// _flexoConcept = flexoConcept;
			updateBindingModels();
		}

		/*@Override
		public Vector<EditionAction<?, ?>> getActions() {
			return actions;
		}

		@Override
		public void setActions(Vector<EditionAction<?, ?>> actions) {
			this.actions = actions;
			setChanged();
			notifyObservers();
		}

		@Override
		public void addToActions(EditionAction<?, ?> action) {
			// action.setScheme(this);
			action.setActionContainer(this);
			actions.add(action);
			setChanged();
			notifyObservers();
			notifyChange("actions", null, actions);
		}

		@Override
		public void removeFromActions(EditionAction<?, ?> action) {
			// action.setScheme(null);
			action.setActionContainer(null);
			actions.remove(action);
			setChanged();
			notifyObservers();
			notifyChange("actions", null, actions);
		}*/

		/*@Override
		public int getIndex(EditionAction action) {
			return actions.indexOf(action);
		}

		@Override
		public void insertActionAtIndex(EditionAction action, int index) {
			// action.setScheme(this);
			action.setActionContainer(this);
			actions.insertElementAt(action, index);
			setChanged();
			notifyObservers();
			notifyChange("actions", null, actions);
		}

		@Override
		public void actionFirst(EditionAction a) {
			actions.remove(a);
			actions.insertElementAt(a, 0);
			setChanged();
			notifyChange("actions", null, actions);
		}

		@Override
		public void actionUp(EditionAction a) {
			int index = actions.indexOf(a);
			if (index > 0) {
				actions.remove(a);
				actions.insertElementAt(a, index - 1);
				setChanged();
				notifyChange("actions", null, actions);
			}
		}

		@Override
		public void actionDown(EditionAction a) {
			int index = actions.indexOf(a);
			if (index > -1) {
				actions.remove(a);
				actions.insertElementAt(a, index + 1);
				setChanged();
				notifyChange("actions", null, actions);
			}
		}

		@Override
		public void actionLast(EditionAction a) {
			actions.remove(a);
			actions.add(a);
			setChanged();
			notifyChange("actions", null, actions);
		}
		*/

		/*	@Override
			public Vector<FlexoBehaviourParameter> getParameters() {
				return parameters;
			}*/

		@Override
		public void setParameters(List<FlexoBehaviourParameter> someParameters) {
			performSuperSetter(PARAMETERS_KEY, someParameters);
			updateBindingModels();
		}

		@Override
		public void addToParameters(FlexoBehaviourParameter parameter) {
			performSuperAdder(PARAMETERS_KEY, parameter);
			updateBindingModels();
			for (FlexoBehaviourParameter p : getParameters()) {
				p.notifyBindingModelChanged();
			}
		}

		@Override
		public void removeFromParameters(FlexoBehaviourParameter parameter) {
			performSuperRemover(PARAMETERS_KEY, parameter);
			updateBindingModels();
		}

		@Override
		public void parameterFirst(FlexoBehaviourParameter p) {
			System.out.println("parameterFirst()");
			getParameters().remove(p);
			getParameters().add(0, p);
			setChanged();
			notifyObservers(new DataModification("parameters", null, getParameters()));
		}

		@Override
		public void parameterUp(FlexoBehaviourParameter p) {
			System.out.println("parameterUp()");
			int index = getParameters().indexOf(p);
			if (index > 0) {
				getParameters().remove(p);
				getParameters().add(index - 1, p);
				setChanged();
				notifyObservers(new DataModification("parameters", null, getParameters()));
			}
		}

		@Override
		public void parameterDown(FlexoBehaviourParameter p) {
			System.out.println("parameterDown()");
			int index = getParameters().indexOf(p);
			if (index > -1) {
				getParameters().remove(p);
				getParameters().add(index + 1, p);
				setChanged();
				notifyObservers(new DataModification("parameters", null, getParameters()));
			}
		}

		@Override
		public void parameterLast(FlexoBehaviourParameter p) {
			System.out.println("parameterLast()");
			getParameters().remove(p);
			getParameters().add(p);
			setChanged();
			notifyObservers(new DataModification("parameters", null, getParameters()));
		}

		/*public FlexoBehaviourParameter getParameter(String name) {
			if (name == null) {
				return null;
			}
			for (FlexoBehaviourParameter p : parameters) {
				if (name.equals(p.getName())) {
					return p;
				}
			}
			return null;
		}*/

		@Override
		public VirtualModel getVirtualModel() {
			if (getFlexoConcept() != null && getFlexoConcept().getVirtualModel() != null) {
				return getFlexoConcept().getVirtualModel();
			}
			if (getFlexoConcept() instanceof VirtualModel) {
				return (VirtualModel) getFlexoConcept();
			}
			return null;
		}

		/*
		@Override
		public AddShape createAddShapeAction() {
			AddShape newAction = new AddShape(null);
			if (getFlexoConcept().getDefaultShapePatternRole() != null) {
				newAction.setAssignation(new ViewPointDataBinding(getFlexoConcept().getDefaultShapePatternRole().getPatternRoleName()));
			}
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddClass createAddClassAction() {
			AddClass newAction = new AddClass(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddIndividual createAddIndividualAction() {
			AddIndividual newAction = new AddIndividual(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddObjectPropertyStatement createAddObjectPropertyStatementAction() {
			AddObjectPropertyStatement newAction = new AddObjectPropertyStatement(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddDataPropertyStatement createAddDataPropertyStatementAction() {
			AddDataPropertyStatement newAction = new AddDataPropertyStatement(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddIsAStatement createAddIsAPropertyAction() {
			AddIsAStatement newAction = new AddIsAStatement(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddRestrictionStatement createAddRestrictionAction() {
			AddRestrictionStatement newAction = new AddRestrictionStatement(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddConnector createAddConnectorAction() {
			AddConnector newAction = new AddConnector(null);
			if (getFlexoConcept().getDefaultConnectorPatternRole() != null) {
				newAction.setAssignation(new ViewPointDataBinding(getFlexoConcept().getDefaultConnectorPatternRole().getPatternRoleName()));
			}
			addToActions(newAction);
			return newAction;
		}

		@Override
		public DeclarePatternRole createDeclarePatternRoleAction() {
			DeclarePatternRole newAction = new DeclarePatternRole(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public GraphicalAction createGraphicalAction() {
			GraphicalAction newAction = new GraphicalAction(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public CreateDiagram createAddDiagramAction() {
			CreateDiagram newAction = new CreateDiagram(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public AddFlexoConcept createAddFlexoConceptAction() {
			AddFlexoConcept newAction = new AddFlexoConcept(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public ConditionalAction createConditionalAction() {
			ConditionalAction newAction = new ConditionalAction(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public IterationAction createIterationAction() {
			IterationAction newAction = new IterationAction(null);
			addToActions(newAction);
			return newAction;
		}

		public CloneShape createCloneShapeAction() {
			CloneShape newAction = new CloneShape(null);
			if (getFlexoConcept().getDefaultShapePatternRole() != null) {
				newAction.setAssignation(new ViewPointDataBinding(getFlexoConcept().getDefaultShapePatternRole().getPatternRoleName()));
			}
			addToActions(newAction);
			return newAction;
		}

		public CloneConnector createCloneConnectorAction() {
			CloneConnector newAction = new CloneConnector(null);
			if (getFlexoConcept().getDefaultConnectorPatternRole() != null) {
				newAction.setAssignation(new ViewPointDataBinding(getFlexoConcept().getDefaultConnectorPatternRole().getPatternRoleName()));
			}
			addToActions(newAction);
			return newAction;
		}

		public CloneIndividual createCloneIndividualAction() {
			CloneIndividual newAction = new CloneIndividual(null);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public DeleteAction createDeleteAction() {
			DeleteAction newAction = new DeleteAction(null);
			addToActions(newAction);
			return newAction;
		}*/

		/**
		 * Creates a new {@link EditionAction} of supplied class, and add it at the end of action list<br>
		 * Delegates creation to model slot
		 * 
		 * @return newly created {@link EditionAction}
		 */
		@Override
		public <A extends EditionAction<?, ?>> A createAction(Class<A> actionClass, ModelSlot<?> modelSlot) {
			A newAction = modelSlot.createAction(actionClass);
			addToActions(newAction);
			return newAction;
		}

		@Override
		public EditionAction deleteAction(EditionAction anAction) {
			removeFromActions(anAction);
			anAction.delete();
			return anAction;
		}

		@Override
		public void finalizeEditionSchemeDeserialization() {
			updateBindingModels();
		}

		@Override
		public boolean getSkipConfirmationPanel() {
			return skipConfirmationPanel;
		}

		@Override
		public void setSkipConfirmationPanel(boolean skipConfirmationPanel) {
			this.skipConfirmationPanel = skipConfirmationPanel;
		}

		@Override
		public BindingModel getBindingModel() {
			if (isRebuildingBindingModel) {
				return _bindingModel;
			}
			if (_bindingModel == null) {
				createBindingModel();
			}
			return _bindingModel;
		}

		@Override
		public BindingModel getInferedBindingModel() {
			return getBindingModel();
		}

		@Override
		public void updateBindingModels() {
			if (isDeserializing()) {
				return;
			}
			/*if (getName().equals("synchronization")) {
				System.out.println("******* updateBindingModels() for " + this + " " + getName() + " and ep=" + getFlexoConcept());
			}*/
			logger.fine("updateBindingModels()");
			_bindingModel = null;
			createBindingModel();
			rebuildActionsBindingModel();
			recursivelyUpdateInferedBindingModels(this);
		}

		private void recursivelyUpdateInferedBindingModels(ActionContainer container) {
			/*if (getName().equals("synchronization")) {
				System.out.println("    > recursivelyUpdateInferedBindingModels for " + container + " bindingModel=" + getBindingModel());
			}*/
			for (EditionAction action : container.getActions()) {
				action.rebuildInferedBindingModel();
				if (action instanceof ActionContainer) {
					recursivelyUpdateInferedBindingModels((ActionContainer) action);
				}
			}
		}

		protected void rebuildActionsBindingModel() {
			for (EditionAction action : getActions()) {
				action.rebuildInferedBindingModel();
			}
		}

		private boolean isRebuildingBindingModel = false;

		private final void createBindingModel() {
			_bindingModel = new BindingModel();
			isRebuildingBindingModel = true;
			_bindingModel.addToBindingVariables(new BindingVariable("parameters", getFlexoBehaviourParametersValuesType()));
			_bindingModel.addToBindingVariables(new BindingVariable("parametersDefinitions", getFlexoBehaviourParametersType()));
			// _bindingModel.addToBindingVariables(new EditionSchemeParametersBindingVariable(this));
			// _bindingModel.addToBindingVariables(new EditionSchemeParameterListPathElement(this, null));
			appendContextualBindingVariables(_bindingModel);
			if (getFlexoConcept() != null) {
				for (final FlexoRole role : getFlexoConcept().getFlexoRoles()) {
					_bindingModel.addToBindingVariables(new PatternRoleBindingVariable(role));
				}
			}
			for (final EditionAction a : getActions()) {
				if (a instanceof AssignableAction && ((AssignableAction) a).getIsVariableDeclaration()) {
					_bindingModel.addToBindingVariables(new BindingVariable(((AssignableAction) a).getVariableName(),
							((AssignableAction) a).getAssignableType(), true) {
						@Override
						public Type getType() {
							return ((AssignableAction) a).getAssignableType();
						}
					});
				}
			}
			notifyBindingModelChanged();
			isRebuildingBindingModel = false;
		}

		protected void appendContextualBindingVariables(BindingModel bindingModel) {
			// Si flexo concept est un diagram spec alors rajouter la varialble diagram
			// AprÃ¨s faudra voir au runtime;
			if (getFlexoConcept() != null) {
				bindingModel.addToBindingVariables(new BindingVariable(FlexoBehaviour.THIS, FlexoConceptInstanceType
						.getFlexoConceptInstanceType(getFlexoConcept())));
				/*if (getFlexoConcept().getVirtualModel() instanceof DiagramSpecification) {
					bindingModel.addToBindingVariables(new BindingVariable(DiagramEditionScheme.DIAGRAM, FlexoConceptInstanceType
							.getFlexoConceptInstanceType(getFlexoConcept().getVirtualModel())));
				} 
				if(getFlexoConcept() instanceof DiagramSpecification){
					bindingModel.addToBindingVariables(new BindingVariable(DiagramEditionScheme.DIAGRAM, FlexoConceptInstanceType
							.getFlexoConceptInstanceType(getFlexoConcept())));
					bindingModel.addToBindingVariables(new BindingVariable(DiagramEditionScheme.TOP_LEVEL, DiagramRootPane.class));
				}
				else {
					bindingModel.addToBindingVariables(new BindingVariable(FlexoBehaviour.VIRTUAL_MODEL_INSTANCE, FlexoConceptInstanceType
							.getFlexoConceptInstanceType(getFlexoConcept().getVirtualModel())));
				}*/
			}
			// if (this instanceof DiagramEditionScheme) {
			/*if (getFlexoConcept() != null && getFlexoConcept().getVirtualModel() instanceof DiagramSpecification) {
				bindingModel.addToBindingVariables(new BindingVariable(DiagramEditionScheme.TOP_LEVEL, DiagramRootPane.class));
			}*/
		}

		@Override
		public void variableAdded(AssignableAction action) {
			updateBindingModels();
		}

		@Override
		public boolean getDefinePopupDefaultSize() {
			return definePopupDefaultSize;
		}

		@Override
		public void setDefinePopupDefaultSize(boolean definePopupDefaultSize) {
			this.definePopupDefaultSize = definePopupDefaultSize;
		}

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public void setWidth(int width) {
			this.width = width;
		}

		@Override
		public int getHeight() {
			return height;
		}

		@Override
		public void setHeight(int height) {
			this.height = height;
		}

		@Override
		public Type getReturnType() {
			return Void.TYPE;
		}

		@Override
		public List<FlexoBehaviourParameter> getArguments() {
			return getParameters();
		}

		private String editionSchemeSignature = null;

		@Override
		public String getSignature() {
			if (editionSchemeSignature == null) {
				StringBuffer signature = new StringBuffer();
				signature.append(getName());
				signature.append("(");
				signature.append(getParameterListAsString(false));
				signature.append(")");
				editionSchemeSignature = signature.toString();
			}
			return editionSchemeSignature;
		}

		private String getParameterListAsString(boolean fullyQualified) {
			StringBuffer returned = new StringBuffer();
			boolean isFirst = true;
			for (FlexoBehaviourParameter param : getParameters()) {
				returned.append((isFirst ? "" : ",")
						+ (fullyQualified ? TypeUtils.fullQualifiedRepresentation(param.getType()) : TypeUtils.simpleRepresentation(param
								.getType())));
				isFirst = false;
			}
			return returned.toString();
		}

		@Override
		public String getAvailableParameterName(String baseName) {
			String testName = baseName;
			int index = 2;
			while (getParameter(testName) != null) {
				testName = baseName + index;
				index++;
			}
			return testName;
		}

	}
}
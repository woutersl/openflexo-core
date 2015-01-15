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
package org.openflexo.foundation.fml.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.Bindable;
import org.openflexo.antar.binding.BindingFactory;
import org.openflexo.antar.binding.BindingModel;
import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.action.NotImplementedException;
import org.openflexo.foundation.fml.FMLModelFactory;
import org.openflexo.foundation.fml.FMLObject;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.fml.controlgraph.ConditionalAction;
import org.openflexo.foundation.fml.controlgraph.DefaultFMLControlGraphOwner;
import org.openflexo.foundation.fml.controlgraph.FMLControlGraph;
import org.openflexo.foundation.fml.controlgraph.IterationAction;
import org.openflexo.foundation.fml.editionaction.AddToListAction;
import org.openflexo.foundation.fml.editionaction.AssignableAction;
import org.openflexo.foundation.fml.editionaction.AssignationAction;
import org.openflexo.foundation.fml.editionaction.DeclarationAction;
import org.openflexo.foundation.fml.editionaction.DeleteAction;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.fml.editionaction.ExpressionAction;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.editionaction.RemoveFromListAction;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.editionaction.AddFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.MatchFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.SelectFlexoConceptInstance;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;

public class CreateEditionAction extends FlexoAction<CreateEditionAction, FMLControlGraph, FMLObject> implements Bindable,
		PropertyChangeListener {

	private static final Logger logger = Logger.getLogger(CreateEditionAction.class.getPackage().getName());

	public static FlexoActionType<CreateEditionAction, FMLControlGraph, FMLObject> actionType = new FlexoActionType<CreateEditionAction, FMLControlGraph, FMLObject>(
			"create_edition_action", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateEditionAction makeNewAction(FMLControlGraph focusedObject, Vector<FMLObject> globalSelection, FlexoEditor editor) {
			return new CreateEditionAction(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FMLControlGraph object, Vector<FMLObject> globalSelection) {
			return object != null;
		}

		@Override
		public boolean isEnabledForSelection(FMLControlGraph object, Vector<FMLObject> globalSelection) {
			return object != null;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateEditionAction.actionType, FMLControlGraph.class);
	}

	private ModelSlot<?> modelSlot;
	private Class<? extends EditionAction> editionActionClass;
	private Class<? extends FetchRequest<?, ?>> fetchRequestClass;

	private EditionAction newEditionAction;

	private final List<Class<? extends EditionAction>> availableActions;
	private final List<Class<? extends FetchRequest<?, ?>>> availableFetchRequests;

	private final HashMap<Class<? extends EditionAction>, TechnologyAdapter> editionActionForTechnologyAdapterMap;
	private final HashMap<Class<? extends EditionAction>, EditionAction> editionActionMap;

	private void addToAvailableActions(Class<? extends EditionAction> availableActionClass, TechnologyAdapter ta) {
		availableActions.add(availableActionClass);
		editionActionForTechnologyAdapterMap.put(availableActionClass, ta);
		if (FetchRequest.class.isAssignableFrom(availableActionClass)) {
			availableFetchRequests.add((Class<FetchRequest<?, ?>>) availableActionClass);
		}
	}

	CreateEditionAction(FMLControlGraph focusedObject, Vector<FMLObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);

		availableActions = new ArrayList<Class<? extends EditionAction>>();
		availableFetchRequests = new ArrayList<Class<? extends FetchRequest<?, ?>>>();
		editionActionForTechnologyAdapterMap = new HashMap<Class<? extends EditionAction>, TechnologyAdapter>();
		editionActionMap = new HashMap<Class<? extends EditionAction>, EditionAction>();

		// availableActions.add(AssignationAction.class);
		// availableActions.add(DeclarationAction.class);

		FMLTechnologyAdapter fmlTA = getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FMLTechnologyAdapter.class);
		addToAvailableActions(ConditionalAction.class, fmlTA);
		addToAvailableActions(IterationAction.class, fmlTA);
		addToAvailableActions(ExpressionAction.class, fmlTA);
		addToAvailableActions(AddToListAction.class, fmlTA);
		addToAvailableActions(RemoveFromListAction.class, fmlTA);
		addToAvailableActions(AddFlexoConceptInstance.class, fmlTA);
		addToAvailableActions(MatchFlexoConceptInstance.class, fmlTA);
		addToAvailableActions(SelectFlexoConceptInstance.class, fmlTA);
		addToAvailableActions(DeleteAction.class, fmlTA);

		if (getFocusedObject().getOwner().getOwningVirtualModel() != null) {
			for (ModelSlot<?> ms : getFocusedObject().getOwner().getOwningVirtualModel().getModelSlots()) {
				for (Class<? extends TechnologySpecificAction<?, ?>> eaClass : ms.getAvailableEditionActionTypes()) {
					addToAvailableActions(eaClass, ms.getModelSlotTechnologyAdapter());
				}
			}
		}

	}

	public List<Class<? extends EditionAction>> getAvailableActionClasses() {
		return availableActions;
	}

	public List<Class<? extends FetchRequest<?, ?>>> getAvailableFetchRequestClasses() {
		return availableFetchRequests;
	}

	@Override
	protected void doAction(Object context) throws NotImplementedException, InvalidParameterException {
		logger.info("Add edition action, modelSlot=" + modelSlot + " editionActionClass=" + editionActionClass);

		newEditionAction = null;
		EditionAction baseEditionAction = getBaseEditionAction();

		if (baseEditionAction instanceof AssignableAction) {
			if (getAssignation() != null && getAssignation().isSet()) {
				AssignationAction<?> newAssignationAction = getFocusedObject().getFMLModelFactory().newAssignationAction();
				newAssignationAction.setAssignableAction((AssignableAction) baseEditionAction);
				newAssignationAction.setAssignation((DataBinding) getAssignation());
				newEditionAction = newAssignationAction;
			} else if (getDeclarationVariableName() != null) {
				DeclarationAction<?> newDeclarationAction = getFocusedObject().getFMLModelFactory().newDeclarationAction();
				newDeclarationAction.setAssignableAction((AssignableAction) baseEditionAction);
				newDeclarationAction.setVariableName(getDeclarationVariableName());
				newEditionAction = newDeclarationAction;
			}
		}
		if (newEditionAction == null) {
			newEditionAction = baseEditionAction;
		}

		if (newEditionAction != null) {
			getFocusedObject().sequentiallyAppend(newEditionAction);
		}

		else {
			throw new InvalidParameterException("cannot build EditionAction for " + editionActionClass);
		}

	}

	public TechnologyAdapter getTechnologyAdapter(Class<? extends EditionAction> editionActionClass) {
		TechnologyAdapter returned = editionActionForTechnologyAdapterMap.get(editionActionClass);
		if (returned != null) {
			return returned;
		}
		FMLTechnologyAdapter fmlTA = getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FMLTechnologyAdapter.class);
		return fmlTA;
	}

	public Class<? extends EditionAction> getEditionActionClass() {
		if (editionActionClass == null) {
			setEditionActionClass(ExpressionAction.class);
		}
		return editionActionClass;
	}

	public void setEditionActionClass(Class<? extends EditionAction> editionActionClass) {
		if ((editionActionClass == null && this.editionActionClass != null)
				|| (editionActionClass != null && !editionActionClass.equals(this.editionActionClass))) {
			Class<? extends EditionAction> oldValue = this.editionActionClass;
			this.editionActionClass = editionActionClass;
			getPropertyChangeSupport().firePropertyChange("editionActionClass", oldValue, editionActionClass);
			// baseEditionAction = makeEditionAction();
			getPropertyChangeSupport().firePropertyChange("baseEditionAction", oldValue, editionActionClass);
			getPropertyChangeSupport().firePropertyChange("isAssignableAction", !isAssignableAction(), isAssignableAction());
			getPropertyChangeSupport().firePropertyChange("isIterationAction", !isIterationAction(), isIterationAction());
			getPropertyChangeSupport().firePropertyChange("modelSlot", getModelSlot() != null ? null : true, getModelSlot());
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
		}
	}

	public Class<? extends FetchRequest<?, ?>> getFetchRequestClass() {
		return fetchRequestClass;
	}

	public void setFetchRequestClass(Class<? extends FetchRequest<?, ?>> fetchRequestClass) {
		if ((fetchRequestClass == null && this.fetchRequestClass != null)
				|| (fetchRequestClass != null && !fetchRequestClass.equals(this.fetchRequestClass))) {
			Class<? extends FetchRequest<?, ?>> oldValue = this.fetchRequestClass;
			this.fetchRequestClass = fetchRequestClass;
			getPropertyChangeSupport().firePropertyChange("fetchRequestClass", oldValue, fetchRequestClass);
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
		}
	}

	public EditionAction getBaseEditionAction() {
		EditionAction returned = editionActionMap.get(getEditionActionClass());
		if (returned == null) {
			returned = makeEditionAction();
			editionActionMap.put(editionActionClass, returned);
			returned.getPropertyChangeSupport().addPropertyChangeListener(this);
		}
		return returned;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof EditionAction) {
			getPropertyChangeSupport().firePropertyChange("declarationVariableName", null, getDeclarationVariableName());
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getDeclarationVariableName());
		}
	}

	public String getStringRepresentation() {
		EditionAction baseEditionAction = getBaseEditionAction();

		if (baseEditionAction instanceof AssignableAction) {
			if (isAssignation()) {
				return getAssignation() + " = " + baseEditionAction.getStringRepresentation();
			} else if (isVariableDeclaration()) {
				return TypeUtils.simpleRepresentation(((AssignableAction) baseEditionAction).getAssignableType()) + " "
						+ getDeclarationVariableName() + " = " + baseEditionAction.getStringRepresentation();
			} else {
				return baseEditionAction.getStringRepresentation();
			}
		}
		return "?";
	}

	@Override
	public boolean delete() {
		for (EditionAction ea : editionActionMap.values()) {
			ea.getPropertyChangeSupport().removePropertyChangeListener(this);
			ea.delete();
		}
		editionActionMap.clear();
		editionActionForTechnologyAdapterMap.clear();
		availableActions.clear();
		return super.delete();
	}

	public EditionAction getNewEditionAction() {
		return newEditionAction;
	}

	@Override
	public boolean isValid() {
		if (getEditionActionClass() == null) {
			return false;
		}
		return true;

		/*switch (actionChoice) {
		case BuiltInAction:
			if (builtInActionClass == null) {
				validityMessage = NO_ACTION_TYPE_SELECTED;
				return false;
			}
			return true;
		case ControlAction:
			if (controlActionClass == null) {
				validityMessage = NO_ACTION_TYPE_SELECTED;
				return false;
			}
			return true;
		case ModelSlotSpecificAction:
			if (modelSlot == null) {
				validityMessage = NO_MODEL_SLOT;
				return false;
			}
			if (modelSlotSpecificActionClass == null) {
				validityMessage = NO_ACTION_TYPE_SELECTED;
				return false;
			}
			return true;
		case RequestAction:
			if (modelSlot == null) {
				validityMessage = NO_MODEL_SLOT;
				return false;
			}
			if (requestActionClass == null) {
				validityMessage = NO_ACTION_TYPE_SELECTED;
				return false;
			}
			return true;

		default:
			return false;
		}*/

	}

	private DefaultFMLControlGraphOwner owner;

	private EditionAction makeEditionAction() {
		EditionAction returned = null;
		FMLModelFactory factory = getFocusedObject().getFMLModelFactory();

		if (editionActionClass == null) {
			logger.warning("Unexpected " + editionActionClass);
			return null;
		}
		if (org.openflexo.foundation.fml.editionaction.AssignationAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newAssignationAction();
		} else if (org.openflexo.foundation.fml.editionaction.ExpressionAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newExpressionAction();
		} else if (org.openflexo.foundation.fml.editionaction.AddToListAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newAddToListAction();
		} else if (org.openflexo.foundation.fml.editionaction.RemoveFromListAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newRemoveFromListAction();
		} else if (AddFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newAddFlexoConceptInstance();
		} else if (MatchFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newMatchFlexoConceptInstance();
		} else if (SelectFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newSelectFlexoConceptInstance();
		} else if (DeleteAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newDeleteAction();
		} else if (ConditionalAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newConditionalAction();
		} else if (IterationAction.class.isAssignableFrom(editionActionClass)) {
			returned = factory.newIterationAction();
		} else if (FetchRequest.class.isAssignableFrom(editionActionClass) && getModelSlot() != null) {
			returned = getModelSlot().makeFetchRequest((Class<FetchRequest<?, ?>>) editionActionClass);
		} else if (TechnologySpecificAction.class.isAssignableFrom(editionActionClass) && getModelSlot() != null) {
			returned = getModelSlot().makeEditionAction((Class<TechnologySpecificAction<?, ?>>) editionActionClass);
		}

		if (TechnologySpecificAction.class.isAssignableFrom(editionActionClass) && getModelSlot() != null) {
			((TechnologySpecificAction) returned).setModelSlot(modelSlot);
		}

		if (returned != null) {

			owner = factory.newInstance(DefaultFMLControlGraphOwner.class);
			owner.setConceptObject(getFocusedObject());
			returned.setOwner(owner);

			return returned;
		}

		logger.warning("Cannot build EditionAction " + editionActionClass);
		return null;

	}

	public List<ModelSlot<?>> getAvailableModelSlotsForSelectedAction() {
		List<ModelSlot<?>> returned = new ArrayList<ModelSlot<?>>();
		if (getFocusedObject().getOwner().getOwningVirtualModel() != null) {
			for (ModelSlot<?> ms : getFocusedObject().getOwner().getOwningVirtualModel().getModelSlots()) {
				if (ms.getAvailableEditionActionTypes().contains(getEditionActionClass())) {
					returned.add(ms);
				}
			}
		}
		return returned;
	}

	public ModelSlot<?> getModelSlot() {
		List<ModelSlot<?>> availableMS = getAvailableModelSlotsForSelectedAction();
		if (modelSlot == null) {
			if (availableMS.size() > 0) {
				return getAvailableModelSlotsForSelectedAction().get(0);
			}
		}
		if (modelSlot != null && !availableMS.contains(modelSlot)) {
			if (availableMS.size() > 0) {
				modelSlot = getAvailableModelSlotsForSelectedAction().get(0);
			} else {
				modelSlot = null;
			}
		}
		return modelSlot;
	}

	public void setModelSlot(ModelSlot<?> modelSlot) {
		if ((modelSlot == null && this.modelSlot != null) || (modelSlot != null && !modelSlot.equals(this.modelSlot))) {
			ModelSlot<?> oldValue = this.modelSlot;
			this.modelSlot = modelSlot;
			getPropertyChangeSupport().firePropertyChange("modelSlot", oldValue, modelSlot);
		}
	}

	/*public Class<? extends EditionAction> getBuiltInActionClass() {
		return builtInActionClass;
	}

	public void setBuiltInActionClass(Class<? extends EditionAction> builtInActionClass) {
		this.builtInActionClass = builtInActionClass;
	}

	public Class<? extends EditionAction> getControlActionClass() {
		return controlActionClass;
	}

	public void setControlActionClass(Class<? extends ControlStructureAction> controlActionClass) {
		this.controlActionClass = controlActionClass;
	}

	public Class<? extends EditionAction> getModelSlotSpecificActionClass() {
		return modelSlotSpecificActionClass;
	}

	public void setModelSlotSpecificActionClass(Class<? extends TechnologySpecificAction<?, ?>> modelSlotSpecificActionClass) {
		this.modelSlotSpecificActionClass = modelSlotSpecificActionClass;
	}

	public Class<? extends FetchRequest<?, ?>> getRequestActionClass() {
		return requestActionClass;
	}

	public void setRequestActionClass(Class<? extends FetchRequest<?, ?>> requestActionClass) {
		this.requestActionClass = requestActionClass;
	}*/

	private DataBinding<?> assignation = null;
	private String declarationVariableName = null;

	private Type getAssignableType() {
		return Object.class;
	}

	public DataBinding<?> getAssignation() {
		if (assignation == null) {

			assignation = new DataBinding<Object>(this, Object.class, DataBinding.BindingDefinitionType.GET_SET) {
				@Override
				public Type getDeclaredType() {
					return getAssignableType();
				}
			};
			assignation.setDeclaredType(getAssignableType());
			assignation.setBindingName("assignation");
			assignation.setMandatory(true);

		}
		assignation.setDeclaredType(getAssignableType());
		return assignation;
	}

	public void setAssignation(DataBinding<?> assignation) {
		if (assignation != null) {
			this.assignation = new DataBinding<Object>(assignation.toString(), this, Object.class,
					DataBinding.BindingDefinitionType.GET_SET) {
				@Override
				public Type getDeclaredType() {
					return getAssignableType();
				}
			};
			assignation.setDeclaredType(getAssignableType());
			assignation.setBindingName("assignation");
			assignation.setMandatory(true);
		}
		notifiedBindingChanged(this.assignation);
	}

	private String getDefaultVariableName() {

		String baseName = getBaseVariableName();
		String current = baseName;
		int i = 2;

		while (getFocusedObject().getInferedBindingModel().bindingVariableNamed(current) != null) {
			current = baseName + i;
			i++;
		}

		return current;
	}

	private String getBaseVariableName() {

		if (getBaseEditionAction() instanceof AssignableAction) {
			Type assignableType = ((AssignableAction) getBaseEditionAction()).getAssignableType();
			String typeAsString = TypeUtils.simpleRepresentation(assignableType);
			if (assignableType instanceof Class) {
				if (typeAsString.startsWith("a") || typeAsString.startsWith("e") || typeAsString.startsWith("i")
						|| typeAsString.startsWith("o") || typeAsString.startsWith("u")) {
					return "an" + typeAsString.substring(0, 1).toUpperCase() + typeAsString.substring(1);
				} else {
					return "a" + typeAsString.substring(0, 1).toUpperCase() + typeAsString.substring(1);
				}
			}
		}

		return "variable";
	}

	public String getDeclarationVariableName() {
		if (declarationVariableName == null) {
			return getDefaultVariableName();
		}
		return declarationVariableName;
	}

	public void setDeclarationVariableName(String declarationVariableName) {
		if ((declarationVariableName == null && this.declarationVariableName != null)
				|| (declarationVariableName != null && !declarationVariableName.equals(this.declarationVariableName))) {
			String oldValue = this.declarationVariableName;
			this.declarationVariableName = declarationVariableName;
			getPropertyChangeSupport().firePropertyChange("declarationVariableName", oldValue, declarationVariableName);
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
		}
	}

	@Override
	public BindingFactory getBindingFactory() {
		return getFocusedObject().getBindingFactory();
	}

	@Override
	public BindingModel getBindingModel() {
		// System.out.println("prout pour " + getFocusedObject().getStringRepresentation());
		// System.out.println("je retourne " + getFocusedObject().getInferedBindingModel());
		return getFocusedObject().getInferedBindingModel();

		/*System.out.println("prout pour " + getFocusedObject().getStringRepresentation());
		// return getFocusedObject().getOwner().getBaseBindingModel(getBaseEditionAction());
		if (getFocusedObject() != null && getFocusedObject().getOwner() != null) {
			FMLControlGraphOwner owner = getFocusedObject().getOwner();
			if (owner instanceof FMLControlGraph) {
				System.out.println("je retourne pour " + owner + " " + ((FMLControlGraph) owner).getInferedBindingModel());
				return ((FMLControlGraph) owner).getInferedBindingModel();
			}
			return owner.getBindingModel();
		}
		return null;*/
	}

	@Override
	public void notifiedBindingChanged(org.openflexo.antar.binding.DataBinding<?> dataBinding) {
		getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
	}

	@Override
	public void notifiedBindingDecoded(org.openflexo.antar.binding.DataBinding<?> dataBinding) {
		// TODO
	}

	public boolean isAssignableAction() {
		return getBaseEditionAction() instanceof AssignableAction;
	}

	public boolean isIterationAction() {
		return getBaseEditionAction() instanceof IterationAction;
	}

	public boolean isIterationExpressionAction() {
		return getBaseEditionAction() instanceof IterationAction
				&& ((IterationAction) getBaseEditionAction()).getIterationAction() instanceof ExpressionAction;
	}

	public boolean isVariableDeclaration() {
		return isVariableDeclaration;
	}

	public void setVariableDeclaration(boolean isVariableDeclaration) {
		if (isVariableDeclaration != this.isVariableDeclaration) {
			boolean oldValue = this.isVariableDeclaration;
			this.isVariableDeclaration = isVariableDeclaration;
			getPropertyChangeSupport().firePropertyChange("isVariableDeclaration", oldValue, isVariableDeclaration);
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
		}
	}

	private boolean isVariableDeclaration = false;
	private boolean isAssignation = false;

	public boolean isAssignation() {
		return isAssignation;
	}

	public void setAssignation(boolean isAssignation) {
		if (isAssignation != this.isAssignation) {
			boolean oldValue = this.isAssignation;
			this.isAssignation = isAssignation;
			getPropertyChangeSupport().firePropertyChange("isAssignation", oldValue, isAssignation);
			getPropertyChangeSupport().firePropertyChange("stringRepresentation", null, getStringRepresentation());
		}
	}

}

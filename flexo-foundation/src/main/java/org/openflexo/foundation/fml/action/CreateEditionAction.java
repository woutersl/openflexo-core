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

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.action.NotImplementedException;
import org.openflexo.foundation.fml.ActionContainer;
import org.openflexo.foundation.fml.FlexoBehaviour;
import org.openflexo.foundation.fml.FlexoBehaviourObject;
import org.openflexo.foundation.fml.FMLObject;
import org.openflexo.foundation.fml.VirtualModelModelFactory;
import org.openflexo.foundation.fml.editionaction.ConditionalAction;
import org.openflexo.foundation.fml.editionaction.DeleteAction;
import org.openflexo.foundation.fml.editionaction.EditionAction;
import org.openflexo.foundation.fml.editionaction.FetchRequest;
import org.openflexo.foundation.fml.editionaction.FetchRequestIterationAction;
import org.openflexo.foundation.fml.editionaction.IterationAction;
import org.openflexo.foundation.fml.rt.editionaction.AddFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.MatchFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.SelectFlexoConceptInstance;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.localization.FlexoLocalization;

public class CreateEditionAction extends FlexoAction<CreateEditionAction, FlexoBehaviourObject, FMLObject> {

	private static final Logger logger = Logger.getLogger(CreateEditionAction.class.getPackage().getName());

	public static FlexoActionType<CreateEditionAction, FlexoBehaviourObject, FMLObject> actionType = new FlexoActionType<CreateEditionAction, FlexoBehaviourObject, FMLObject>(
			"create_edition_action", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateEditionAction makeNewAction(FlexoBehaviourObject focusedObject, Vector<FMLObject> globalSelection,
				FlexoEditor editor) {
			return new CreateEditionAction(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FlexoBehaviourObject object, Vector<FMLObject> globalSelection) {
			return object != null;
		}

		@Override
		public boolean isEnabledForSelection(FlexoBehaviourObject object, Vector<FMLObject> globalSelection) {
			return object != null;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateEditionAction.actionType, FlexoBehaviour.class);
		FlexoObjectImpl.addActionForClass(CreateEditionAction.actionType, EditionAction.class);
	}

	public static enum CreateEditionActionChoice {
		BuiltInAction, ModelSlotSpecificAction, RequestAction, ControlAction
	}

	public static enum LayoutChoice {
		InsertAfter, InsertBefore, InsertInside;
	}

	public String description;
	public CreateEditionActionChoice actionChoice = CreateEditionActionChoice.BuiltInAction;
	private LayoutChoice layoutChoice;
	private ModelSlot modelSlot;
	private Class<? extends EditionAction> builtInActionClass;
	private Class<? extends EditionAction> controlActionClass;
	private Class<? extends EditionAction> modelSlotSpecificActionClass;
	private Class<? extends FetchRequest> requestActionClass;

	private EditionAction newEditionAction;

	private final List<Class<? extends EditionAction>> builtInActions;
	private final List<Class<? extends EditionAction>> controlActions;

	CreateEditionAction(FlexoBehaviourObject focusedObject, Vector<FMLObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);

		builtInActions = new ArrayList<Class<? extends EditionAction>>();
		builtInActions.add(org.openflexo.foundation.fml.editionaction.AssignationAction.class);
		builtInActions.add(org.openflexo.foundation.fml.editionaction.AddToListAction.class);
		builtInActions.add(org.openflexo.foundation.fml.editionaction.RemoveFromListAction.class);
		builtInActions.add(org.openflexo.foundation.fml.editionaction.ExecutionAction.class);
		builtInActions.add(org.openflexo.foundation.fml.editionaction.DeclareFlexoRole.class);
		builtInActions.add(org.openflexo.foundation.fml.rt.editionaction.AddFlexoConceptInstance.class);
		builtInActions.add(org.openflexo.foundation.fml.rt.editionaction.MatchFlexoConceptInstance.class);
		builtInActions.add(org.openflexo.foundation.fml.rt.editionaction.SelectFlexoConceptInstance.class);
		builtInActions.add(DeleteAction.class);

		controlActions = new ArrayList<Class<? extends EditionAction>>();
		controlActions.add(ConditionalAction.class);
		controlActions.add(IterationAction.class);
		controlActions.add(FetchRequestIterationAction.class);

		// If the model slot is empty, then now it is the currentVirtualModel that is referenced
		/*
		System.out.println("focusedObject=" + focusedObject);
		System.out.println("focusedObject.getVirtualModel()=" + focusedObject.getVirtualModel());
		if (modelSlot == null && !focusedObject.getVirtualModel().getModelSlots().isEmpty()) {
			modelSlot = focusedObject.getVirtualModel().getModelSlots().get(0);
		}
		*/
	}

	public List<Class<? extends EditionAction>> getBuiltInActions() {
		return builtInActions;
	}

	public List<Class<? extends EditionAction>> getControlActions() {
		return controlActions;
	}

	public List<Class<? extends EditionAction>> getModelSlotSpecificActions() {
		if (modelSlot != null) {
			return modelSlot.getAvailableEditionActionTypes();
		}
		else {
			// TODO : when modelSlot is null, return AvailableEditionActionTypes for VirtualModel
		}
		return null;
	}

	public List<Class<? extends EditionAction>> getRequestActions() {
		if (modelSlot != null) {
			return modelSlot.getAvailableFetchRequestActionTypes();
		}
		return null;
	}

	@Override
	protected void doAction(Object context) throws NotImplementedException, InvalidParameterException {
		logger.info("Add edition action, modelSlot=" + modelSlot + " actionChoice=" + actionChoice);

		newEditionAction = makeEditionAction();

		if (newEditionAction != null) {
			if ((getFocusedObject() instanceof ActionContainer) && (getLayoutChoice() == LayoutChoice.InsertInside)) {
				((ActionContainer) getFocusedObject()).addToActions(newEditionAction);
			}
			else if (getFocusedObject() instanceof EditionAction) {
				ActionContainer container = ((EditionAction) getFocusedObject()).getActionContainer();
				int index = container.getIndex((EditionAction) getFocusedObject());
				if (getLayoutChoice() == LayoutChoice.InsertAfter) {
					container.insertActionAtIndex(newEditionAction, index + 1);
				}
				else {
					container.insertActionAtIndex(newEditionAction, index);
				}
			}
		}

		else {
			throw new InvalidParameterException("cannot build EditionAction");
		}

	}

	public EditionAction getNewEditionAction() {
		return newEditionAction;
	}

	private String validityMessage = NO_ACTION_TYPE_SELECTED;

	private static final String NO_MODEL_SLOT = FlexoLocalization.localizedForKey("please_choose_a_model_slot");
	private static final String NO_ACTION_TYPE_SELECTED = FlexoLocalization.localizedForKey("please_select_an_action_type");

	public String getValidityMessage() {
		return validityMessage;
	}

	@Override
	public boolean isValid() {
		switch (actionChoice) {
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
		}

	}

	private EditionAction makeEditionAction() {
		EditionAction returned;
		VirtualModelModelFactory factory = getFocusedObject().getVirtualModelFactory();
		switch (actionChoice) {
			case BuiltInAction:
				if (builtInActionClass == null) {
					logger.warning("Unexpected " + builtInActionClass);
					return null;
				}
				if (org.openflexo.foundation.fml.editionaction.AssignationAction.class.isAssignableFrom(builtInActionClass)) {
					return factory.newAssignationAction();
				}
				else if (org.openflexo.foundation.fml.editionaction.AddToListAction.class.isAssignableFrom(builtInActionClass)) {
					return factory.newAddToListAction();
				}
				else if (org.openflexo.foundation.fml.editionaction.RemoveFromListAction.class.isAssignableFrom(builtInActionClass)) {
					return factory.newRemoveFromListAction();
				}
				else if (org.openflexo.foundation.fml.editionaction.ExecutionAction.class.isAssignableFrom(builtInActionClass)) {
					return factory.newExecutionAction();
				}
				else if (org.openflexo.foundation.fml.editionaction.DeclareFlexoRole.class.isAssignableFrom(builtInActionClass)) {
					return factory.newDeclareFlexoRole();
				}
				else if (AddFlexoConceptInstance.class.isAssignableFrom(builtInActionClass)) {
					return factory.newAddFlexoConceptInstance();
				}
				else if (MatchFlexoConceptInstance.class.isAssignableFrom(builtInActionClass)) {
					return factory.newMatchFlexoConceptInstance();
				}
				else if (SelectFlexoConceptInstance.class.isAssignableFrom(builtInActionClass)) {
					return factory.newSelectFlexoConceptInstance();
				}
				else if (DeleteAction.class.isAssignableFrom(builtInActionClass)) {
					return factory.newDeleteAction();
				}
				else {
					logger.warning("Unexpected " + builtInActionClass);
					return null;
				}
			case ControlAction:
				if (controlActionClass == null) {
					logger.warning("Unexpected " + controlActionClass);
					return null;
				}
				if (ConditionalAction.class.isAssignableFrom(controlActionClass)) {
					return factory.newConditionalAction();
				}
				else if (IterationAction.class.isAssignableFrom(controlActionClass)) {
					return factory.newIterationAction();
				}
				else if (FetchRequestIterationAction.class.isAssignableFrom(controlActionClass) && requestActionClass != null
				/*&& modelSlot != null*/) {
					returned = factory.newFetchRequestIterationAction();
					FetchRequest request = null;
					if (modelSlot != null) {
						request = modelSlot.makeFetchRequest(requestActionClass);
						request.setModelSlot(modelSlot);
						returned.setModelSlot(modelSlot);
					}
					else if (SelectFlexoConceptInstance.class.isAssignableFrom(requestActionClass)) {
						request = factory.newSelectFlexoConceptInstanceAction();
					}
					if (request != null) {
						((FetchRequestIterationAction) returned).setFetchRequest(request);
					}
					return returned;
				}
				else {
					logger.warning("Unexpected " + controlActionClass);
					return null;
				}
			case ModelSlotSpecificAction:
				if (modelSlotSpecificActionClass != null && modelSlot != null) {
					returned = modelSlot.makeEditionAction(modelSlotSpecificActionClass);
					returned.setModelSlot(modelSlot);
					return returned;
				}
				break;
			case RequestAction:
				if (SelectFlexoConceptInstance.class.isAssignableFrom(requestActionClass)) {
					return factory.newSelectFlexoConceptInstanceAction();
				}
				else if (requestActionClass != null && modelSlot != null) {
					returned = modelSlot.makeFetchRequest(requestActionClass);
					returned.setModelSlot(modelSlot);
					return returned;
				}

			default:
				break;
		}

		logger.warning("Cannot build EditionAction");
		return null;

	}

	public LayoutChoice getLayoutChoice() {
		if (layoutChoice == null) {
			if (getFocusedObject() instanceof ActionContainer) {
				return LayoutChoice.InsertInside;
			}
			return LayoutChoice.InsertAfter;
		}
		return layoutChoice;
	}

	public void setLayoutChoice(LayoutChoice layoutChoice) {
		this.layoutChoice = layoutChoice;
	}

	public ModelSlot getModelSlot() {
		return modelSlot;
	}

	public void setModelSlot(ModelSlot modelSlot) {
		this.modelSlot = modelSlot;
	}

	public Class<? extends EditionAction> getBuiltInActionClass() {
		return builtInActionClass;
	}

	public void setBuiltInActionClass(Class<? extends EditionAction> builtInActionClass) {
		this.builtInActionClass = builtInActionClass;
	}

	public Class<? extends EditionAction> getControlActionClass() {
		return controlActionClass;
	}

	public void setControlActionClass(Class<? extends EditionAction> controlActionClass) {
		this.controlActionClass = controlActionClass;
	}

	public Class<? extends EditionAction> getModelSlotSpecificActionClass() {
		return modelSlotSpecificActionClass;
	}

	public void setModelSlotSpecificActionClass(Class<? extends EditionAction> modelSlotSpecificActionClass) {
		this.modelSlotSpecificActionClass = modelSlotSpecificActionClass;
	}

	public Class<? extends FetchRequest> getRequestActionClass() {
		return requestActionClass;
	}

	public void setRequestActionClass(Class<? extends FetchRequest> requestActionClass) {
		this.requestActionClass = requestActionClass;
	}

}
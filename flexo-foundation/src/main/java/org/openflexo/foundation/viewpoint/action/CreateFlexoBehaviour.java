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
package org.openflexo.foundation.viewpoint.action;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.action.NotImplementedException;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.viewpoint.ActionScheme;
import org.openflexo.foundation.viewpoint.CloningScheme;
import org.openflexo.foundation.viewpoint.CreationScheme;
import org.openflexo.foundation.viewpoint.DeletionScheme;
import org.openflexo.foundation.viewpoint.FlexoBehaviour;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.foundation.viewpoint.FlexoConceptBehaviouralFacet;
import org.openflexo.foundation.viewpoint.FlexoConceptObject;
import org.openflexo.foundation.viewpoint.SynchronizationScheme;
import org.openflexo.foundation.viewpoint.ViewPointObject;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.VirtualModelModelFactory;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.toolbox.StringUtils;

// TODO: rename as CreateFlexoBehaviour
public class CreateFlexoBehaviour extends FlexoAction<CreateFlexoBehaviour, FlexoConceptObject, ViewPointObject> {

	private static final Logger logger = Logger.getLogger(CreateFlexoBehaviour.class.getPackage().getName());

	public static FlexoActionType<CreateFlexoBehaviour, FlexoConceptObject, ViewPointObject> actionType = new FlexoActionType<CreateFlexoBehaviour, FlexoConceptObject, ViewPointObject>(
			"create_flexo_behaviour", FlexoActionType.newMenu, FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public CreateFlexoBehaviour makeNewAction(FlexoConceptObject focusedObject, Vector<ViewPointObject> globalSelection,
				FlexoEditor editor) {
			return new CreateFlexoBehaviour(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FlexoConceptObject object, Vector<ViewPointObject> globalSelection) {
			return object != null;
		}

		@Override
		public boolean isEnabledForSelection(FlexoConceptObject object, Vector<ViewPointObject> globalSelection) {
			return object != null;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(CreateFlexoBehaviour.actionType, FlexoConcept.class);
		FlexoObjectImpl.addActionForClass(CreateFlexoBehaviour.actionType, FlexoConceptBehaviouralFacet.class);
	}

	/*public static enum CreateEditionSchemeChoice {
		BuiltInAction, ModelSlotSpecificBehaviour
	}*/

	private String flexoBehaviourName;
	private String description;
	private Class<? extends FlexoBehaviour> flexoBehaviourClass;
	private final HashMap<Class<? extends FlexoBehaviour>, TechnologyAdapter> behaviourClassMap;

	private List<Class<? extends FlexoBehaviour>> behaviours;

	private FlexoBehaviour newFlexoBehaviour;

	CreateFlexoBehaviour(FlexoConceptObject focusedObject, Vector<ViewPointObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);

		behaviourClassMap = new HashMap<Class<? extends FlexoBehaviour>, TechnologyAdapter>();

		if (focusedObject instanceof VirtualModel) {
			addVirtualModelFlexoBehaviours((VirtualModel) focusedObject);
		} else if (focusedObject instanceof FlexoConcept) {
			addFlexoConceptFlexoBehaviours((FlexoConcept) focusedObject);
		}
	}

	private void addVirtualModelFlexoBehaviours(VirtualModel virtualModel) {
		behaviourClassMap.put(ActionScheme.class, virtualModel.getTechnologyAdapter());
		behaviourClassMap.put(CloningScheme.class, virtualModel.getTechnologyAdapter());
		behaviourClassMap.put(CreationScheme.class, virtualModel.getTechnologyAdapter());
		behaviourClassMap.put(DeletionScheme.class, virtualModel.getTechnologyAdapter());
		behaviourClassMap.put(SynchronizationScheme.class, virtualModel.getTechnologyAdapter());
		for (ModelSlot<?> ms : virtualModel.getModelSlots()) {
			List<Class<? extends FlexoBehaviour>> msBehaviours = ms.getAvailableFlexoBehaviourTypes();
			for (Class<? extends FlexoBehaviour> behaviour : msBehaviours) {
				if (!behaviourClassMap.containsKey(behaviour)) {
					behaviourClassMap.put(behaviour, ms.getTechnologyAdapter());
				}
			}
		}
	}

	private void addFlexoConceptFlexoBehaviours(FlexoConcept flexoConcept) {
		behaviourClassMap.put(ActionScheme.class, flexoConcept.getVirtualModel().getTechnologyAdapter());
		behaviourClassMap.put(CloningScheme.class, flexoConcept.getVirtualModel().getTechnologyAdapter());
		behaviourClassMap.put(CreationScheme.class, flexoConcept.getVirtualModel().getTechnologyAdapter());
		behaviourClassMap.put(DeletionScheme.class, flexoConcept.getVirtualModel().getTechnologyAdapter());
		for (ModelSlot<?> ms : flexoConcept.getVirtualModel().getModelSlots()) {
			List<Class<? extends FlexoBehaviour>> msBehaviours = ms.getAvailableFlexoBehaviourTypes();
			for (Class<? extends FlexoBehaviour> behaviour : msBehaviours) {
				if (!behaviourClassMap.containsKey(behaviour)) {
					behaviourClassMap.put(behaviour, ms.getTechnologyAdapter());
				}
			}
		}
	}

	public TechnologyAdapter getBehaviourTechnologyAdapter(Class<? extends FlexoBehaviour> behaviourClass) {
		return behaviourClassMap.get(behaviourClass);
	}

	public List<Class<? extends FlexoBehaviour>> getBehaviours() {
		if (behaviours == null) {
			behaviours = new ArrayList<Class<? extends FlexoBehaviour>>();
			for (Class<? extends FlexoBehaviour> mapKey : behaviourClassMap.keySet()) {
				behaviours.add(mapKey);
			}
		}
		return behaviours;
	}

	/*public List<Class<? extends FlexoBehaviour>> getModelSlotSpecificBehaviours() {
		if (modelSlot != null && !(modelSlot instanceof VirtualModelModelSlot)) {
			return modelSlot.getAvailableFlexoBehaviourTypes();
		}
		return null;
	}*/

	public FlexoConcept getFlexoConcept() {
		if (getFocusedObject() != null) {
			return getFocusedObject().getFlexoConcept();
		}
		return null;
	}

	public String getFlexoBehaviourName() {
		if (StringUtils.isEmpty(flexoBehaviourName) && flexoBehaviourClass != null) {
			return getFlexoConcept().getAvailableEditionSchemeName(flexoBehaviourClass.getSimpleName());
		}
		return flexoBehaviourName;
	}

	public void setFlexoBehaviourName(String flexoBehaviourName) {
		this.flexoBehaviourName = flexoBehaviourName;
	}

	@Override
	protected void doAction(Object context) throws NotImplementedException, InvalidParameterException {
		logger.info("Add flexo behaviour, name=" + getFlexoBehaviourName() + " type=" + flexoBehaviourClass);

		if (flexoBehaviourClass != null) {

			VirtualModelModelFactory factory = getFocusedObject().getVirtualModelFactory();
			newFlexoBehaviour = factory.newInstance(flexoBehaviourClass);
			newFlexoBehaviour.setName(getFlexoBehaviourName());
			newFlexoBehaviour.setFlexoConcept(getFlexoConcept());
			getFlexoConcept().addToFlexoBehaviours(newFlexoBehaviour);
		} else {
			throw new InvalidParameterException("flexoBehaviourClass is null");
		}

	}

	public FlexoBehaviour getNewFlexoBehaviour() {
		return newFlexoBehaviour;
	}

	private String errorMessage = EMPTY_NAME;

	private static final String DUPLICATED_NAME = FlexoLocalization.localizedForKey("this_name_is_already_used_please_choose_an_other_one");
	private static final String EMPTY_NAME = FlexoLocalization.localizedForKey("flexo_behaviour_must_have_an_non_empty_and_unique_name");
	private static final String EMPTY_FLEXO_BEHAVIOUR_TYPE = FlexoLocalization.localizedForKey("a_flexo_behaviour_type_must_be_selected");

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public boolean isValid() {
		if (getFlexoBehaviourName() == null) {
			errorMessage = EMPTY_NAME;
			return false;
		} else if (getFlexoConcept().getFlexoBehaviour(getFlexoBehaviourName()) != null) {
			errorMessage = DUPLICATED_NAME;
			return false;
		} else if (flexoBehaviourClass == null) {
			errorMessage = EMPTY_FLEXO_BEHAVIOUR_TYPE;
			return false;
		} else {
			errorMessage = "";
			return true;
		}
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		boolean wasValid = isValid();
		this.description = description;
		getPropertyChangeSupport().firePropertyChange("description", null, description);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}

	public Class<? extends FlexoBehaviour> getFlexoBehaviourClass() {
		return flexoBehaviourClass;
	}

	public void setFlexoBehaviourClass(Class<? extends FlexoBehaviour> flexoBehaviourClass) {
		boolean wasValid = isValid();
		this.flexoBehaviourClass = flexoBehaviourClass;
		getPropertyChangeSupport().firePropertyChange("flexoBehaviourClass", null, flexoBehaviourClass);
		getPropertyChangeSupport().firePropertyChange("isValid", wasValid, isValid());
		getPropertyChangeSupport().firePropertyChange("errorMessage", null, getErrorMessage());
	}
}
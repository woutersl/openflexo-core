/*
 * (c) Copyright 2010-2012 AgileBirds
 * (c) Copyright 2012-2013 Openflexo
 *
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
package org.openflexo.foundation.view.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingEvaluationContext;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.antar.binding.SettableBindingEvaluationContext;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlot;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.TypeAwareModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.VirtualModelInstanceObject;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.foundation.viewpoint.FlexoBehaviour;
import org.openflexo.foundation.viewpoint.FlexoBehaviourParameter;
import org.openflexo.foundation.viewpoint.ListParameter;
import org.openflexo.foundation.viewpoint.URIParameter;
import org.openflexo.foundation.viewpoint.editionaction.AssignableAction;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction;
import org.openflexo.toolbox.StringUtils;

/**
 * This abstract class is the root class for all actions which can be performed at conceptual or design level, generally on a view tool
 * (such as a diagram).<br>
 * An {@link FlexoBehaviourAction} represents the execution (in the "instances" world) of an {@link FlexoBehaviour}.<br>
 * To be used and executed on Openflexo platform, it is wrapped in a {@link FlexoAction}.<br>
 * 
 * 
 * @author sylvain
 * 
 * @param <A>
 */
public abstract class FlexoBehaviourAction<A extends FlexoBehaviourAction<A, FB, O>, FB extends FlexoBehaviour, O extends VirtualModelInstanceObject>
		extends FlexoAction<A, O, VirtualModelInstanceObject> implements SettableBindingEvaluationContext {

	private static final Logger logger = Logger.getLogger(FlexoBehaviourAction.class.getPackage().getName());

	protected Hashtable<String, Object> variables;
	protected ParameterValues parameterValues;
	protected Hashtable<ListParameter, List> parameterListValues;

	public boolean escapeParameterRetrievingWhenValid = true;

	public FlexoBehaviourAction(FlexoActionType<A, O, VirtualModelInstanceObject> actionType, O focusedObject,
			Vector<VirtualModelInstanceObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
		variables = new Hashtable<String, Object>();
		parameterValues = new ParameterValues();
		parameterListValues = new Hashtable<ListParameter, List>();
	}

	/**
	 * Compute and store default parameters, and return a flag indicating if all parameters declared as "mandatory" could be successfully
	 * filled
	 * 
	 * @return
	 */
	// TODO: we must order this if dependancies are not resolved using basic sequence
	public boolean retrieveDefaultParameters() {
		boolean returned = true;
		FlexoBehaviour flexoBehaviour = getEditionScheme();
		logger.info("BEGIN retrieveDefaultParameters() for " + flexoBehaviour);
		for (final FlexoBehaviourParameter parameter : flexoBehaviour.getParameters()) {
			Object defaultValue = parameter.getDefaultValue(this);
			logger.info("Parameter " + parameter.getName() + " default value = " + defaultValue);
			if (defaultValue != null) {
				parameterValues.put(parameter, defaultValue);
			}
			if (parameter instanceof ListParameter) {
				List list = (List) ((ListParameter) parameter).getList(this);
				parameterListValues.put((ListParameter) parameter, list);
			}
			logger.info("Parameter " + parameter.getName() + " valid=" + parameter.isValid(this, defaultValue));
			if (!parameter.isValid(this, defaultValue)) {
				logger.info("Parameter " + parameter + " is not valid for value " + defaultValue);
				returned = false;
			}
		}
		logger.info("END retrieveDefaultParameters() for " + flexoBehaviour);
		return returned;
	}

	/**
	 * Return a flag indicating if all parameters declared as "mandatory" have been set
	 * 
	 * @return
	 */
	public boolean areRequiredParametersSetAndValid() {
		FlexoBehaviour flexoBehaviour = getEditionScheme();
		for (final FlexoBehaviourParameter parameter : flexoBehaviour.getParameters()) {
			if (!parameter.isValid(this, parameterValues.get(parameter))) {
				return false;
			}
		}
		return true;
	}

	public FlexoProject getProject() {
		if (getFocusedObject() != null) {
			return getFocusedObject().getProject();
		}
		return null;
	}

	public FlexoConcept getFlexoConcept() {
		if (getEditionScheme() != null) {
			return getEditionScheme().getFlexoConcept();
		}
		return null;
	}

	/**
	 * Calling this method will register a new variable in the run-time context provided by this {@link FlexoBehaviourAction} instance in the
	 * context of its implementation of {@link BindingEvaluationContext}.<br>
	 * Variable is initialized with supplied name and value
	 * 
	 * @param variableName
	 * @param value
	 */
	public void declareVariable(String variableName, Object value) {
		variables.put(variableName, value);
	}

	/**
	 * Calling this method will dereference variable identified by supplied name
	 * 
	 * @param variableName
	 */
	public void dereferenceVariable(String variableName) {
		variables.remove(variableName);
	}

	/*public String getStringParameter(String parameterName) {
		System.out.println("OK, on me demande le parametre " + parameterName + ", je retourne " + parameterValues.get(parameterName));
		return (String) parameterValues.get(parameterName);
	}

	public String getURIParameter(String parameterName) {
		System.out.println("OK, on me demande l'uri " + parameterName + ", je retourned " + parameterValues.get(parameterName));
		return (String) parameterValues.get(parameterName);
	}*/

	/*public Hashtable<FlexoBehaviourParameter, Object> getParameterValues() {
		return parameterValues;
	}*/

	public Object getParameterValue(FlexoBehaviourParameter parameter) {
		/*System.out.println("On me demande la valeur du parametre " + parameter.getName() + " a priori c'est "
				+ parameterValues.get(parameter));*/
		if (parameter instanceof URIParameter) {
			if (parameterValues.get(parameter) == null || parameterValues.get(parameter) instanceof String
					&& StringUtils.isEmpty((String) parameterValues.get(parameter))) {
				return ((URIParameter) parameter).getDefaultValue(this);
			}
		}
		return parameterValues.get(parameter);
	}

	public void setParameterValue(FlexoBehaviourParameter parameter, Object value) {
		System.out.println("setParameterValue " + value + " for parameter " + parameter.getName());
		parameterValues.put(parameter, value);
		/*for (FlexoBehaviourParameter p : getEditionScheme().getParameters()) {
			if (p instanceof URIParameter) {
				// System.out.println("Hop, je recalcule l'uri, ici");
			}
		}*/
	}

	public List getParameterListValue(ListParameter parameter) {
		/*System.out.println("On me demande la valeur du parametre " + parameter.getName() + " a priori c'est "
				+ parameterValues.get(parameter));*/
		return parameterListValues.get(parameter);
	}

	public abstract FB getEditionScheme();

	public VirtualModelInstance getVirtualModelInstance() {
		return retrieveVirtualModelInstance();
	}

	public abstract VirtualModelInstance retrieveVirtualModelInstance();

	/**
	 * Return the {@link FlexoConceptInstance} on which this {@link FlexoBehaviour} is applied.<br>
	 * An {@link FlexoBehaviourAction} may concern an existing {@link FlexoConceptInstance} or may also refer to an
	 * {@link FlexoConceptInstance} instance to be created (if related {@link FlexoBehaviour} is a {@link CreationSchemeAction}).
	 * 
	 * @return
	 */
	public abstract FlexoConceptInstance getFlexoConceptInstance();

	/**
	 * This is the internal code performing execution of the control graph of {@link EditionAction} defined to be the execution control
	 * graph of related {@link FlexoBehaviour}<br>
	 * Recursively invoke {@link #performAction(EditionAction, Hashtable)}
	 */
	protected void applyEditionActions() {

		for (FlexoBehaviourParameter param : parameterValues.keySet()) {
			logger.info("Parameter " + param.getName() + " value=" + parameterValues.get(param));
		}

		Hashtable<EditionAction, Object> performedActions = new Hashtable<EditionAction, Object>();

		// Perform actions
		for (EditionAction action : getEditionScheme().getActions()) {
			if (action.evaluateCondition(this)) {
				performAction(action, performedActions);
			}
			// Otherwise, we just ignore the action
		}

		// Finalize actions
		for (EditionAction action : performedActions.keySet()) {
			action.finalizePerformAction(this, performedActions.get(action));
		}

		// Hack to be removed (implements model resources as FlexoResource interface !!!)
		/*System.out.println("HACK !!!!!!! saving the models...");
		for (ModelSlotInstance<?, ?> instance : getFlexoConceptInstance().getVirtualModelInstance().getModelSlotInstances()) {
			System.out.println("Slot " + instance + " model " + instance.getModel());
			if (instance.getModel() instanceof FlexoFileResource) {
				try {
					((FlexoFileResource) instance.getModel()).save(null);
				} catch (SaveResourceException e) {
					e.printStackTrace();
				}
			}
		}*/

	}

	/**
	 * This is the internal code performing execution of a single {@link EditionAction} defined to be part of the execution control graph of
	 * related {@link FlexoBehaviour}<br>
	 */
	protected Object performAction(EditionAction action, Hashtable<EditionAction, Object> performedActions) {
		Object assignedObject = action.performAction(this);

		if (assignedObject != null) {
			performedActions.put(action, assignedObject);
		}

		if (assignedObject != null && action instanceof AssignableAction) {
			AssignableAction assignableAction = (AssignableAction) action;
			if (assignableAction.getIsVariableDeclaration()) {
				System.out.println("Setting variable " + assignableAction.getVariableName() + " with value " + assignedObject + " of "
						+ (assignedObject != null ? assignedObject.getClass() : "null"));
				declareVariable(assignableAction.getVariableName(), assignedObject);
			}
			if (assignableAction.getAssignation().isSet() && assignableAction.getAssignation().isValid()) {
				try {
					assignableAction.getAssignation().setBindingValue(assignedObject, this);
				} catch (Exception e) {
					logger.warning("Unexpected assignation issue, " + assignableAction.getAssignation() + " object=" + assignedObject
							+ " exception: " + e);
					e.printStackTrace();
				}
			}
		}

		return assignedObject;
	}

	@Override
	public Object getValue(BindingVariable variable) {
		if (variable.getVariableName().equals("parameters")) {
			return getParametersValues();
		} else if (variable.getVariableName().equals("parametersDefinitions")) {
			return getEditionScheme().getParameters();
		} else if (variable.getVariableName().equals(FlexoBehaviour.VIRTUAL_MODEL_INSTANCE)) {
			return getVirtualModelInstance();
		} /*else if (variable.getVariableName().equals(DiagramEditionScheme.DIAGRAM)) {
			return getVirtualModelInstance();
			} else if (variable.getVariableName().equals(DiagramEditionScheme.TOP_LEVEL)) {
			if (getVirtualModelInstance() instanceof Diagram) {
				return ((Diagram) getVirtualModelInstance()).getRootPane();
			}
			}*/

		if (getEditionScheme().getVirtualModel().handleVariable(variable)) {
			return getVirtualModelInstance().getValueForVariable(variable);
		}

		if (variables.get(variable.getVariableName()) != null) {
			return variables.get(variable.getVariableName());
		}

		logger.warning("Unexpected variable requested in FlexoBehaviourAction: " + variable + " of " + variable.getClass());
		return null;
	}

	@Override
	public void setValue(Object value, BindingVariable variable) {
		if (variables.get(variable.getVariableName()) != null) {
			variables.put(variable.getVariableName(), value);
			return;
		}
		logger.warning("Unexpected variable requested in settable context in FlexoBehaviourAction: " + variable + " of "
				+ variable.getClass());
	}

	/*	public GraphicalRepresentation getOverridingGraphicalRepresentation(GraphicalElementPatternRole patternRole) {
			// return overridenGraphicalRepresentations.get(patternRole);
			// TODO temporary desactivate overriden GR
			return null;
		}*/

	public ParameterValues getParametersValues() {
		return parameterValues;
	}

	public boolean parameterValueChanged() {
		setChanged();
		notifyObservers(new DataModification(PARAMETER_VALUE_CHANGED, null, getParametersValues()));
		return true;
	}

	public static final String PARAMETER_VALUE_CHANGED = "parameterValueChanged";

	public class ParameterValues extends Hashtable<FlexoBehaviourParameter, Object> {

		@Override
		public synchronized Object put(FlexoBehaviourParameter parameter, Object value) {
			Object returned = super.put(parameter, value);
			for (FlexoBehaviourParameter p : parameter.getFlexoBehaviour().getParameters()) {
				if (p != parameter && p instanceof URIParameter && ((URIParameter) p).getModelSlot() instanceof TypeAwareModelSlot) {
					URIParameter uriParam = (URIParameter) p;
					TypeAwareModelSlot modelSlot = uriParam.getModelSlot();
					String newURI;
					try {
						newURI = uriParam.getBaseURI().getBindingValue(FlexoBehaviourAction.this);

						newURI = modelSlot.generateUniqueURIName((TypeAwareModelSlotInstance) getVirtualModelInstance()
								.getModelSlotInstance(modelSlot), newURI);
						logger.info("Generated new URI " + newURI + " for " + getVirtualModelInstance().getModelSlotInstance(modelSlot));
						// NPE Protection
						if (newURI != null) {
							super.put(uriParam, newURI);
						}
					} catch (TypeMismatchException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NullReferenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			parameterValueChanged();
			return returned;
		}
	}

	public String retrieveFullURI(FlexoBehaviourParameter parameter) {
		if (parameter instanceof URIParameter) {
			URIParameter uriParam = (URIParameter) parameter;
			if (uriParam.getModelSlot() instanceof TypeAwareModelSlot) {
				TypeAwareModelSlot modelSlot = uriParam.getModelSlot();
				return modelSlot.generateUniqueURI((TypeAwareModelSlotInstance) getVirtualModelInstance().getModelSlotInstance(modelSlot),
						(String) getParameterValue(parameter));
			}
		}
		return "Invalid URI";
	}
}
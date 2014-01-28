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
package org.openflexo.foundation.viewpoint.editionaction;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingEvaluationContext;
import org.openflexo.antar.binding.BindingModel;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.DataBinding.BindingDefinitionType;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.viewpoint.PatternRole;
import org.openflexo.foundation.viewpoint.ViewPointObject;
import org.openflexo.foundation.viewpoint.ViewPointObject.BindingMustBeValid;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction.EditionActionImpl;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.toolbox.StringUtils;

/**
 * Abstract class representing an EditionAction with the particularity of returning a value which can be assigned
 * 
 * @author sylvain
 * 
 */
@ModelEntity(isAbstract = true)
@ImplementationClass(AssignableAction.AssignableActionImpl.class)
public abstract interface AssignableAction<MS extends ModelSlot<?>, T> extends EditionAction<MS, T> {

	@PropertyIdentifier(type = String.class)
	public static final String VARIABLE_NAME_KEY = "variableName";
	@PropertyIdentifier(type = DataBinding.class)
	public static final String ASSIGNATION_KEY = "assignation";

	@Getter(value = VARIABLE_NAME_KEY)
	@XMLAttribute
	public String getVariableName();

	@Setter(VARIABLE_NAME_KEY)
	public void setVariableName(String variableName);

	@Getter(value = ASSIGNATION_KEY)
	@XMLAttribute
	public DataBinding<?> getAssignation();

	@Setter(ASSIGNATION_KEY)
	public void setAssignation(DataBinding<?> assignation);

	public boolean getIsVariableDeclaration();

	public void setIsVariableDeclaration(boolean flag);

	public PatternRole<?> getPatternRole();

	public Type getAssignableType();

	public static abstract class AssignableActionImpl<MS extends ModelSlot<?>, T> extends EditionActionImpl<MS, T> implements
			AssignableAction<MS, T> {

		private static final Logger logger = Logger.getLogger(AssignableAction.class.getPackage().getName());

		private DataBinding<Object> assignation;

		private String variableName = null;

		public AssignableActionImpl() {
			super();
		}

		public boolean isAssignationRequired() {
			return false;
		}

		/*@Override
		public abstract EditionActionType getEditionActionType();*/

		@Override
		public abstract Type getAssignableType();

		@Override
		public DataBinding<Object> getAssignation() {
			if (assignation == null) {
				if (StringUtils.isNotEmpty(variableName)) {
					updateVariableAssignation();
				} else {
					assignation = new DataBinding<Object>(this, Object.class, DataBinding.BindingDefinitionType.GET_SET) {
						@Override
						public Type getDeclaredType() {
							return getAssignableType();
						}
					};
					assignation.setDeclaredType(getAssignableType());
					assignation.setBindingName("assignation");
					assignation.setMandatory(isAssignationRequired());
				}
			}
			assignation.setDeclaredType(getAssignableType());
			return assignation;
		}

		@Override
		public void setAssignation(DataBinding<?> assignation) {
			if (assignation != null) {
				this.assignation = new DataBinding<Object>(assignation.toString(), this, Object.class,
						DataBinding.BindingDefinitionType.GET_SET) {
					@Override
					public Type getDeclaredType() {
						return getAssignableType();
					}
				};
				/*assignation.setOwner(this);
				assignation.setBindingName("assignation");
				assignation.setDeclaredType(getAssignableType());
				assignation.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET_SET);
				assignation.setMandatory(isAssignationRequired());*/
			}
			// this.assignation = assignation;
			notifiedBindingChanged(this.assignation);
		}

		@Override
		public PatternRole<?> getPatternRole() {
			if (getEditionPattern() == null) {
				return null;
			}
			if (assignation != null) {
				return getEditionPattern().getPatternRole(assignation.toString());
			}
			return null;
		}

		@Override
		public String getStringRepresentation() {
			return getClass().getSimpleName()
					+ (StringUtils.isNotEmpty(getAssignation().toString()) ? " (" + getAssignation().toString() + ")" : "");
		}

		@Override
		public String getVariableName() {
			return variableName;
		}

		@Override
		public void setVariableName(String variableName) {
			if (!FlexoObjectImpl.areSameValue(variableName, this.variableName)) {
				this.variableName = variableName;
				if (StringUtils.isNotEmpty(variableName)) {
					updateVariableAssignation();
				}
				if (getActionContainer() != null) {
					getActionContainer().variableAdded(this);
				}
			}
		}

		@Override
		public boolean getIsVariableDeclaration() {
			return StringUtils.isNotEmpty(getVariableName());
		}

		@Override
		public void setIsVariableDeclaration(boolean flag) {
			if (flag) {
				if (StringUtils.isEmpty(getVariableName())) {
					setVariableName("newVariable");
				}
			} else {
				if (StringUtils.isNotEmpty(getVariableName())) {
					setVariableName(null);
					getAssignation().reset();
				}
			}
		}

		protected void updateVariableAssignation() {
			assignation = new DataBinding<Object>(getVariableName(), this, getAssignableType(), BindingDefinitionType.GET_SET);
		}

		@Override
		public void finalizePerformAction(org.openflexo.foundation.view.action.EditionSchemeAction action, T initialContext) {
			/*if (getIsVariableDeclaration()) {
				System.out.println("Setting variable " + getVariableName() + " with " + initialContext);
				action.declareVariable(getVariableName(), initialContext);
			}*/
		}

		@Override
		protected final BindingModel buildInferedBindingModel() {
			BindingModel returned = super.buildInferedBindingModel();
			if (getIsVariableDeclaration()) {
				returned.addToBindingVariables(new BindingVariable(getVariableName(), getAssignableType()) {
					@Override
					public Object getBindingValue(Object target, BindingEvaluationContext context) {
						logger.info("What should i return for " + getVariableName() + " ? target " + target + " context=" + context);
						return super.getBindingValue(target, context);
					}

					@Override
					public Type getType() {
						return getAssignableType();
					}
				});
			}
			return returned;
		}

	}

	public static class AssignationBindingMustBeValid extends BindingMustBeValid<AssignableAction> {
		public AssignationBindingMustBeValid() {
			super("'assign'_binding_is_not_valid", AssignableAction.class);
		}

		@Override
		public DataBinding<Object> getBinding(AssignableAction object) {
			return object.getAssignation();
		}

	}

}

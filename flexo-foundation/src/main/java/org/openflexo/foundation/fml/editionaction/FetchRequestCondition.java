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
package org.openflexo.foundation.fml.editionaction;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import org.openflexo.antar.binding.BindingEvaluationContext;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.DataBinding.BindingDefinitionType;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptObject;
import org.openflexo.foundation.fml.binding.FetchRequestConditionBindingModel;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

/**
 * An FlexoConceptConstraint represents a structural constraint attached to an FlexoConcept
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(FetchRequestCondition.FetchRequestConditionImpl.class)
@XMLElement(xmlTag = "Condition")
public interface FetchRequestCondition extends FlexoConceptObject {

	public static final String SELECTED = "selected";

	@PropertyIdentifier(type = FetchRequest.class)
	public static final String ACTION_KEY = "action";

	@PropertyIdentifier(type = DataBinding.class)
	public static final String CONDITION_KEY = "condition";

	@Getter(value = ACTION_KEY /*, inverse = FetchRequest.CONDITIONS_KEY*/)
	public FetchRequest<?, ?> getAction();

	@Setter(ACTION_KEY)
	public void setAction(FetchRequest<?, ?> action);

	@Getter(value = CONDITION_KEY)
	@XMLAttribute
	public DataBinding<Boolean> getCondition();

	@Setter(CONDITION_KEY)
	public void setCondition(DataBinding<Boolean> condition);

	public boolean evaluateCondition(final Object proposedFetchResult, final FlexoBehaviourAction action);

	public static abstract class FetchRequestConditionImpl extends FlexoConceptObjectImpl implements FetchRequestCondition {

		protected static final Logger logger = FlexoLogger.getLogger(FetchRequestCondition.class.getPackage().getName());

		// private FetchRequest fetchRequest;
		private DataBinding<Boolean> condition;

		public FetchRequestConditionImpl() {
			super();
		}

		@Override
		public FlexoConcept getFlexoConcept() {
			if (getAction() != null) {
				return getAction().getFlexoConcept();
			}
			return null;
		}

		private FetchRequestConditionBindingModel bindingModel;

		@Override
		public FetchRequestConditionBindingModel getBindingModel() {
			if (bindingModel == null) {
				bindingModel = new FetchRequestConditionBindingModel(this);
			}
			return bindingModel;
		}

		@Override
		public String getURI() {
			return getAction().getURI() + "/Constraints_" + Integer.toHexString(hashCode());
		}

		@Override
		public DataBinding<Boolean> getCondition() {
			if (condition == null) {
				condition = new DataBinding<Boolean>(this, Boolean.class, BindingDefinitionType.GET);
				condition.setBindingName("condition");
			}
			return condition;
		}

		@Override
		public void setCondition(DataBinding<Boolean> condition) {
			if (condition != null) {
				condition.setOwner(this);
				condition.setBindingName("condition");
				condition.setDeclaredType(Boolean.class);
				condition.setBindingDefinitionType(BindingDefinitionType.GET);
			}
			this.condition = condition;
		}

		@Override
		public boolean evaluateCondition(final Object proposedFetchResult, final FlexoBehaviourAction action) {
			Boolean returned = null;
			try {
				returned = condition.getBindingValue(new BindingEvaluationContext() {
					@Override
					public Object getValue(BindingVariable variable) {
						if (variable.getVariableName().equals(SELECTED)) {
							return proposedFetchResult;
						}
						return action.getValue(variable);
					}
				});
			} catch (TypeMismatchException e) {
				e.printStackTrace();
			} catch (NullReferenceException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returned == null) {
				return false;
			}
			return returned;
		}
	}

	@DefineValidationRule
	public static class ConditionBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<FetchRequestCondition> {
		public ConditionBindingIsRequiredAndMustBeValid() {
			super("'condition'_binding_is_not_valid", FetchRequestCondition.class);
		}

		@Override
		public DataBinding<Boolean> getBinding(FetchRequestCondition object) {
			return object.getCondition();
		}
	}

}
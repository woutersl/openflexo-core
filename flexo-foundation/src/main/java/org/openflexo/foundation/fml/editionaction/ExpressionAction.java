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

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.toolbox.StringUtils;

/**
 * An {@link EditionAction} which can be represented as an expression
 * 
 * @author sylvain
 * 
 */
@FIBPanel("Fib/FML/ExpressionActionPanel.fib")
@ModelEntity
@ImplementationClass(ExpressionAction.ExpressionActionImpl.class)
@XMLElement
public interface ExpressionAction<T> extends AssignableAction<T> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String EXPRESSION_KEY = "expression";

	@Getter(value = EXPRESSION_KEY)
	@XMLAttribute
	public DataBinding<T> getExpression();

	@Setter(EXPRESSION_KEY)
	public void setExpression(DataBinding<T> expression);

	@Override
	public Type getAssignableType();

	public static abstract class ExpressionActionImpl<T> extends AssignableActionImpl<T> implements ExpressionAction<T> {

		private static final Logger logger = Logger.getLogger(ExpressionAction.class.getPackage().getName());

		private DataBinding<T> expression;

		@Override
		public Type getAssignableType() {
			if (getExpression() != null && getExpression().isSet() && getExpression().isValid()) {
				return getExpression().getAnalyzedType();
			}
			return Object.class;
		}

		@Override
		public DataBinding<T> getExpression() {
			if (expression == null) {
				expression = new DataBinding<T>(this, Object.class, DataBinding.BindingDefinitionType.GET);
				expression.setBindingName("expression");
				expression.setMandatory(true);
			}
			return expression;
		}

		@Override
		public void setExpression(DataBinding<T> expression) {
			if (expression != null) {
				this.expression = new DataBinding<T>(expression.toString(), this, Object.class, DataBinding.BindingDefinitionType.GET);
				expression.setBindingName("expression");
				expression.setMandatory(true);
			}
			notifiedBindingChanged(expression);
		}

		@Override
		public String getStringRepresentation() {
			return getImplementedInterface().getSimpleName()
					+ (StringUtils.isNotEmpty(getExpression().toString()) ? " (" + getExpression().toString() + ")" : "");
		}

		@Override
		public T execute(FlexoBehaviourAction<?, ?, ?> action) throws FlexoException {
			try {
				return getExpression().getBindingValue(action);
			} catch (Exception e) {
				e.printStackTrace();
				throw new FlexoException(e);
			}
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			return getExpression().toString();
		}

	}

	@DefineValidationRule
	public static class ExpressionBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<ExpressionAction> {
		public ExpressionBindingIsRequiredAndMustBeValid() {
			super("'expression'_binding_is_required_and_must_be_valid", ExpressionAction.class);
		}

		@Override
		public DataBinding<Object> getBinding(ExpressionAction object) {
			return object.getExpression();
		}

	}

}
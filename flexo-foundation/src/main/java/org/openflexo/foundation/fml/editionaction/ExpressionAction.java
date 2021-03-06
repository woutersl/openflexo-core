/**
 * 
 * Copyright (c) 2015, Openflexo
 * 
 * This file is part of Flexo-foundation, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.foundation.fml.editionaction;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.connie.DataBinding;
import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

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
			return getHeaderContext() + (getExpression() != null ? getExpression().toString() : "") + ";";
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

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
import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.DataBinding.BindingDefinitionType;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

@FIBPanel("Fib/FML/ExecutionActionPanel.fib")
@ModelEntity
@ImplementationClass(ExecutionAction.ExecutionActionImpl.class)
@XMLElement
public interface ExecutionAction<MS extends ModelSlot<?>> extends AssignableAction<MS, FlexoObject> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String EXECUTION_KEY = "execution";

	@Getter(value = EXECUTION_KEY)
	@XMLAttribute
	public DataBinding<?> getExecution();

	@Setter(EXECUTION_KEY)
	public void setExecution(DataBinding<?> execution);

	public static abstract class ExecutionActionImpl<MS extends ModelSlot<?>> extends AssignableActionImpl<MS, FlexoObject> implements
			ExecutionAction<MS> {

		private static final Logger logger = Logger.getLogger(ExecutionAction.class.getPackage().getName());

		private DataBinding<?> execution;

		public ExecutionActionImpl() {
			super();
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			out.append((getAssignation().isSet() ? getAssignation().toString() + " = " : "") + getExecution().toString() + ";", context);
			return out.toString();
		}

		@Override
		public DataBinding<?> getExecution() {
			if (execution == null) {
				execution = new DataBinding<Object>(this, Object.class, BindingDefinitionType.EXECUTE);
				execution.setBindingName("execution");
			}
			return execution;
		}

		@Override
		public void setExecution(DataBinding<?> execution) {
			if (execution != null) {
				execution.setOwner(this);
				execution.setBindingName("execution");
				execution.setDeclaredType(Object.class);
				execution.setBindingDefinitionType(BindingDefinitionType.EXECUTE);
			}
			this.execution = execution;
		}

		@Override
		public Type getAssignableType() {
			if (getExecution().isSet() && getExecution().isValid()) {
				return getExecution().getAnalyzedType();
			}
			return Object.class;
		}

		@Override
		public FlexoObject performAction(FlexoBehaviourAction action) {
			try {
				getExecution().execute(action);
			} catch (TypeMismatchException e) {
				e.printStackTrace();
			} catch (NullReferenceException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void notifiedBindingChanged(DataBinding<?> dataBinding) {
			if (dataBinding == getExecution()) {
			}
			super.notifiedBindingChanged(dataBinding);
		}

	}

	@DefineValidationRule
	public static class ExecutionBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<ExecutionAction> {
		public ExecutionBindingIsRequiredAndMustBeValid() {
			super("'execution'_binding_is_not_valid", ExecutionAction.class);
		}

		@Override
		public DataBinding<Object> getBinding(ExecutionAction object) {
			return object.getExecution();
		}
	}
}
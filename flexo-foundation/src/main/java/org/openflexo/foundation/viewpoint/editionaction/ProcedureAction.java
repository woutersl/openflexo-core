/*
 * (c) Copyright 2010-2012 AgileBirds
 * (c) Copyright 2012-2013 Openflexo
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

import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.view.action.EditionSchemeAction;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;

@FIBPanel("Fib/VPM/ProcedureActionPanel.fib")
@ModelEntity(isAbstract = true)
@ImplementationClass(ProcedureAction.ProcedureActionImpl.class)
@Deprecated
// I think this class is not necessary ???
public interface ProcedureAction<MS extends ModelSlot<?>, T> extends EditionAction<MS, T> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String PARAMETER_KEY = "parameter";

	@Getter(value = PARAMETER_KEY)
	@XMLAttribute
	public DataBinding<Object> getParameter();

	@Setter(value = PARAMETER_KEY)
	public void setParameter(DataBinding<Object> param);

	public abstract class ProcedureActionImpl<MS extends ModelSlot<?>, T> extends EditionActionImpl<MS, T> implements
			ProcedureAction<MS, T> {

		private static final Logger logger = Logger.getLogger(ProcedureAction.class.getPackage().getName());

		private DataBinding<Object> parameter;

		@Override
		public DataBinding<Object> getParameter() {

			if (parameter == null) {
				parameter = new DataBinding<Object>(this, Object.class, DataBinding.BindingDefinitionType.GET);
				parameter.setBindingName("parameter");
			}
			return parameter;

		}

		@Override
		public void setParameter(DataBinding<Object> paramIndivBinding) {

			if (paramIndivBinding != null) {
				paramIndivBinding.setOwner(this);
				paramIndivBinding.setDeclaredType(Object.class);
				paramIndivBinding.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				paramIndivBinding.setBindingName("parameter");
			}

			this.parameter = paramIndivBinding;

		}

		@Override
		public void notifiedBindingChanged(DataBinding<?> dataBinding) {
			if (dataBinding == getParameter()) {
			}
			super.notifiedBindingChanged(dataBinding);
		}

		public static class ExecutionBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<ProcedureAction> {
			public ExecutionBindingIsRequiredAndMustBeValid() {
				super("'parameter'_binding_is_not_valid", ProcedureAction.class);
			}

			@Override
			public DataBinding<Object> getBinding(ProcedureAction object) {
				return object.getParameter();
			}

		}

		@Override
		public void finalizePerformAction(EditionSchemeAction action, T initialContext) {
			// TODO Auto-generated method stub

		}

	}

}
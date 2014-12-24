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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.DataBinding.BindingDefinitionType;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.binding.IterationActionBindingModel;
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

@FIBPanel("Fib/FML/IterationActionPanel.fib")
@ModelEntity
@ImplementationClass(IterationAction.IterationActionImpl.class)
@XMLElement
public interface IterationAction extends ControlStructureAction {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String ITERATION_KEY = "iteration";
	@PropertyIdentifier(type = String.class)
	public static final String ITERATOR_NAME_KEY = "iteratorName";

	@Getter(value = ITERATION_KEY)
	@XMLAttribute
	public DataBinding<List<?>> getIteration();

	@Setter(ITERATION_KEY)
	public void setIteration(DataBinding<List<?>> iteration);

	@Getter(value = ITERATOR_NAME_KEY)
	@XMLAttribute
	public String getIteratorName();

	@Setter(ITERATOR_NAME_KEY)
	public void setIteratorName(String iteratorName);

	public Type getItemType();

	public static abstract class IterationActionImpl extends ControlStructureActionImpl implements IterationAction {

		private static final Logger logger = Logger.getLogger(IterationAction.class.getPackage().getName());

		private String iteratorName = "item";

		public IterationActionImpl() {
			super();
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			out.append("for (" + getIteratorName() + " in " + getIteration().toString(), context);
			out.append(") {", context);
			out.append(StringUtils.LINE_SEPARATOR, context);
			for (EditionAction action : getActions()) {
				out.append(action.getFMLRepresentation(context), context, 1);
				out.append(StringUtils.LINE_SEPARATOR, context);
			}

			out.append("}", context);
			return out.toString();
		}

		private DataBinding<List<?>> iteration;

		@Override
		public DataBinding<List<?>> getIteration() {
			if (iteration == null) {
				iteration = new DataBinding<List<?>>(this, List.class, BindingDefinitionType.GET);
			}
			return iteration;
		}

		@Override
		public void setIteration(DataBinding<List<?>> iteration) {
			if (iteration != null) {
				iteration.setOwner(this);
				iteration.setBindingName("iteration");
				iteration.setDeclaredType(List.class);
				iteration.setBindingDefinitionType(BindingDefinitionType.GET);
			}
			this.iteration = iteration;
			// rebuildInferedBindingModel();
		}

		/*@Override
		public void notifiedBindingChanged(DataBinding<?> binding) {
			super.notifiedBindingChanged(binding);
			if (binding == iteration) {
				rebuildInferedBindingModel();
			}
		}*/

		@Override
		public String getIteratorName() {
			return iteratorName;
		}

		@Override
		public void setIteratorName(String iteratorName) {
			if (!this.iteratorName.equals(iteratorName)) {
				String oldValue = this.iteratorName;
				this.iteratorName = iteratorName;
				// rebuildInferedBindingModel();
				getPropertyChangeSupport().firePropertyChange(ITERATOR_NAME_KEY, oldValue, iteratorName);
			}
		}

		@Override
		public Type getItemType() {
			if (iteration != null && iteration.isSet()) {
				Type accessedType = iteration.getAnalyzedType();
				if (accessedType instanceof ParameterizedType && ((ParameterizedType) accessedType).getActualTypeArguments().length > 0) {
					return ((ParameterizedType) accessedType).getActualTypeArguments()[0];
				}
			}
			return Object.class;
		}

		/*@Override
		protected BindingModel buildInferedBindingModel() {
			BindingModel returned = super.buildInferedBindingModel();
			returned.addToBindingVariables(new BindingVariable(getIteratorName(), getItemType()) {
				@Override
				public Object getBindingValue(Object target, BindingEvaluationContext context) {
					logger.info("What should i return for " + getIteratorName() + " ? target " + target + " context=" + context);
					return super.getBindingValue(target, context);
				}

				@Override
				public Type getType() {
					return getItemType();
				}
			});
			return returned;
		}*/

		public List<?> evaluateIteration(FlexoBehaviourAction action) {
			if (getIteration().isValid()) {
				try {
					return getIteration().getBindingValue(action);
				} catch (TypeMismatchException e) {
					e.printStackTrace();
				} catch (NullReferenceException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		public Object performAction(FlexoBehaviourAction action) {
			List<?> items = evaluateIteration(action);
			if (items != null) {
				for (Object item : items) {
					System.out.println("> working with " + getIteratorName() + "=" + item);
					action.declareVariable(getIteratorName(), item);
					performBatchOfActions(getActions(), action);
				}
			}
			action.dereferenceVariable(getIteratorName());
			return null;
		}

		@Override
		public String getStringRepresentation() {
			if (getIteration().isSet() && getIteration().isValid()) {
				return getIteratorName() + " : " + getIteration();
			}
			return super.getStringRepresentation();
		}

		@Override
		protected IterationActionBindingModel makeControlGraphBindingModel() {
			return new IterationActionBindingModel(this);
		}

		/*@Override
		protected IterationActionBindingModel makeBindingModel() {
			return new IterationActionBindingModel(this);
		}

		@Override
		public ControlStructureActionBindingModel getControlGraphBindingModel() {
			// TODO Auto-generated method stub
			return super.getControlGraphBindingModel();
		}*/
	}

	@DefineValidationRule
	public static class IterationBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<IterationAction> {
		public IterationBindingIsRequiredAndMustBeValid() {
			super("'iteration'_binding_is_not_valid", IterationAction.class);
		}

		@Override
		public DataBinding<List<?>> getBinding(IterationAction object) {
			return object.getIteration();
		}

	}

}
/*
 * (c) Copyright 2010-2011 AgileBirds
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
package org.openflexo.foundation.fml.rt.editionaction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.fml.CreationScheme;
import org.openflexo.foundation.fml.FlexoBehaviourParameter;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptInstanceRole;
import org.openflexo.foundation.fml.FlexoConceptInstanceType;
import org.openflexo.foundation.fml.URIParameter;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.fml.editionaction.AssignableAction;
import org.openflexo.foundation.fml.editionaction.AssignableAction.AssignableActionImpl;
import org.openflexo.foundation.fml.rt.FMLRTModelSlot;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.CreationSchemeAction;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.CloningStrategy;
import org.openflexo.model.annotations.CloningStrategy.StrategyType;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Embedded;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.validation.CompoundIssue;
import org.openflexo.model.validation.ValidationError;
import org.openflexo.model.validation.ValidationIssue;
import org.openflexo.model.validation.ValidationRule;

/**
 * This action is used to explicitely instanciate a new {@link FlexoConceptInstance} in a given {@link VirtualModelInstance} with some
 * parameters
 * 
 * @author sylvain
 * 
 * @param <M>
 * @param <MM>
 */

@FIBPanel("Fib/FML/AddFlexoConceptInstancePanel.fib")
@ModelEntity
@ImplementationClass(AddFlexoConceptInstance.AddFlexoConceptInstanceImpl.class)
@XMLElement
public interface AddFlexoConceptInstance extends AssignableAction<FMLRTModelSlot, FlexoConceptInstance> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String VIRTUAL_MODEL_INSTANCE_KEY = "virtualModelInstance";
	@PropertyIdentifier(type = String.class)
	public static final String CREATION_SCHEME_URI_KEY = "creationSchemeURI";
	@PropertyIdentifier(type = List.class)
	public static final String PARAMETERS_KEY = "parameters";

	@Getter(value = VIRTUAL_MODEL_INSTANCE_KEY)
	@XMLAttribute
	public DataBinding<VirtualModelInstance> getVirtualModelInstance();

	@Setter(VIRTUAL_MODEL_INSTANCE_KEY)
	public void setVirtualModelInstance(DataBinding<VirtualModelInstance> virtualModelInstance);

	@Getter(value = CREATION_SCHEME_URI_KEY)
	@XMLAttribute
	public String _getCreationSchemeURI();

	@Setter(CREATION_SCHEME_URI_KEY)
	public void _setCreationSchemeURI(String creationSchemeURI);

	public CreationScheme getCreationScheme();

	public void setCreationScheme(CreationScheme creationScheme);

	@Getter(value = PARAMETERS_KEY, cardinality = Cardinality.LIST, inverse = AddFlexoConceptInstanceParameter.ACTION_KEY)
	@XMLElement
	@Embedded
	@CloningStrategy(StrategyType.CLONE)
	public List<AddFlexoConceptInstanceParameter> getParameters();

	@Setter(PARAMETERS_KEY)
	public void setParameters(List<AddFlexoConceptInstanceParameter> parameters);

	@Adder(PARAMETERS_KEY)
	public void addToParameters(AddFlexoConceptInstanceParameter aParameter);

	@Remover(PARAMETERS_KEY)
	public void removeFromParameters(AddFlexoConceptInstanceParameter aParameter);

	public FlexoConcept getFlexoConceptType();

	public void setFlexoConceptType(FlexoConcept flexoConceptType);

	public static abstract class AddFlexoConceptInstanceImpl extends AssignableActionImpl<FMLRTModelSlot, FlexoConceptInstance>
			implements AddFlexoConceptInstance {

		static final Logger logger = Logger.getLogger(AddFlexoConceptInstance.class.getPackage().getName());

		private FlexoConcept flexoConceptType;
		private CreationScheme creationScheme;
		private String _creationSchemeURI;
		private Vector<AddFlexoConceptInstanceParameter> parameters = new Vector<AddFlexoConceptInstanceParameter>();

		public AddFlexoConceptInstanceImpl() {
			super();
		}

		public VirtualModelInstance getVirtualModelInstance(FlexoBehaviourAction action) {
			try {
				// System.out.println("getVirtualModelInstance() with " + getVirtualModelInstance());
				// System.out.println("Valid=" + getVirtualModelInstance().isValid() + " " +
				// getVirtualModelInstance().invalidBindingReason());
				// System.out.println("returned: " + getVirtualModelInstance().getBindingValue(action));
				return getVirtualModelInstance().getBindingValue(action);
			} catch (TypeMismatchException e) {
				e.printStackTrace();
			} catch (NullReferenceException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		}

		private DataBinding<VirtualModelInstance> virtualModelInstance;

		@Override
		public DataBinding<VirtualModelInstance> getVirtualModelInstance() {
			if (virtualModelInstance == null) {
				virtualModelInstance = new DataBinding<VirtualModelInstance>(this, VirtualModelInstance.class,
						DataBinding.BindingDefinitionType.GET);
				virtualModelInstance.setBindingName("virtualModelInstance");
			}
			return virtualModelInstance;
		}

		@Override
		public void setVirtualModelInstance(DataBinding<VirtualModelInstance> aVirtualModelInstance) {
			if (aVirtualModelInstance != null) {
				aVirtualModelInstance.setOwner(this);
				aVirtualModelInstance.setBindingName("virtualModelInstance");
				aVirtualModelInstance.setDeclaredType(VirtualModelInstance.class);
				aVirtualModelInstance.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
			}
			this.virtualModelInstance = aVirtualModelInstance;
		}

		@Override
		public FlexoConcept getFlexoConceptType() {
			if (getCreationScheme() != null) {
				return getCreationScheme().getFlexoConcept();
			}
			return flexoConceptType;
		}

		@Override
		public void setFlexoConceptType(FlexoConcept flexoConceptType) {
			this.flexoConceptType = flexoConceptType;
			if (getCreationScheme() != null && getCreationScheme().getFlexoConcept() != flexoConceptType) {
				setCreationScheme(null);
			}
		}

		@Override
		public String _getCreationSchemeURI() {
			if (getCreationScheme() != null) {
				return getCreationScheme().getURI();
			}
			return _creationSchemeURI;
		}

		@Override
		public void _setCreationSchemeURI(String uri) {
			if (getViewPointLibrary() != null) {
				creationScheme = (CreationScheme) getViewPointLibrary().getFlexoBehaviour(uri);
			}
			_creationSchemeURI = uri;
		}

		@Override
		public CreationScheme getCreationScheme() {
			if (creationScheme == null && _creationSchemeURI != null && getViewPointLibrary() != null) {
				creationScheme = (CreationScheme) getViewPointLibrary().getFlexoBehaviour(_creationSchemeURI);
			}
			if (creationScheme == null && getFlexoRole() instanceof FlexoConceptInstanceRole) {
				creationScheme = ((FlexoConceptInstanceRole) getFlexoRole()).getCreationScheme();
			}
			return creationScheme;
		}

		@Override
		public void setCreationScheme(CreationScheme creationScheme) {
			this.creationScheme = creationScheme;
			if (creationScheme != null) {
				_creationSchemeURI = creationScheme.getURI();
			}
		}

		// private Vector<AddFlexoConceptInstanceParameter> parameters = new Vector<AddFlexoConceptInstanceParameter>();

		@Override
		public List<AddFlexoConceptInstanceParameter> getParameters() {
			// Comment this because of an infinite loop with updateParameters() method
			updateParameters();
			return parameters;
		}

		public void setParameters(Vector<AddFlexoConceptInstanceParameter> parameters) {
			this.parameters = parameters;
		}

		@Override
		public void addToParameters(AddFlexoConceptInstanceParameter parameter) {
			parameter.setAction(this);
			parameters.add(parameter);
		}

		@Override
		public void removeFromParameters(AddFlexoConceptInstanceParameter parameter) {
			parameter.setAction(null);
			parameters.remove(parameter);
		}

		public AddFlexoConceptInstanceParameter getParameter(FlexoBehaviourParameter p) {
			for (AddFlexoConceptInstanceParameter addEPParam : parameters) {
				if (addEPParam.getParam() == p) {
					return addEPParam;
				}
			}
			return null;
		}

		private void updateParameters() {
			List<AddFlexoConceptInstanceParameter> parametersToRemove = new ArrayList<AddFlexoConceptInstanceParameter>(parameters);
			if (getCreationScheme() != null) {
				for (FlexoBehaviourParameter p : getCreationScheme().getParameters()) {
					AddFlexoConceptInstanceParameter existingParam = getParameter(p);
					if (existingParam != null) {
						parametersToRemove.remove(existingParam);
					} else {
						if (getVirtualModelFactory() != null) {
							addToParameters(getVirtualModelFactory().newAddFlexoConceptInstanceParameter(p));
						}
					}
				}
			}
			for (AddFlexoConceptInstanceParameter removeThis : parametersToRemove) {
				removeFromParameters(removeThis);
			}
		}

		@Override
		public FlexoConceptInstance performAction(FlexoBehaviourAction action) {
			logger.info("Perform performAddFlexoConceptInstance " + action);
			VirtualModelInstance vmInstance = getVirtualModelInstance(action);
			logger.info("VirtualModelInstance: " + vmInstance);
			CreationSchemeAction creationSchemeAction = CreationSchemeAction.actionType.makeNewEmbeddedAction(vmInstance, null, action);
			creationSchemeAction.setVirtualModelInstance(vmInstance);
			creationSchemeAction.setCreationScheme(getCreationScheme());
			for (AddFlexoConceptInstanceParameter p : getParameters()) {
				FlexoBehaviourParameter param = p.getParam();
				Object value = p.evaluateParameterValue(action);
				logger.info("For parameter " + param + " value is " + value);
				if (value != null) {
					creationSchemeAction.setParameterValue(p.getParam(), p.evaluateParameterValue(action));
				}
			}
			creationSchemeAction.doAction();
			if (creationSchemeAction.hasActionExecutionSucceeded()) {
				logger.info("Successfully performed performAddFlexoConcept " + action);
				return creationSchemeAction.getFlexoConceptInstance();
			}
			return null;
		}

		/*@Override
		public Type getAssignableType() {
			return FlexoConceptInstanceType.getFlexoConceptInstanceType(getFlexoConceptType());
		}*/

		@Override
		public Type getAssignableType() {
			if (getViewPoint() != null) {
				return FlexoConceptInstanceType.getFlexoConceptInstanceType(getFlexoConceptType());
			} else {
				return FlexoConceptInstanceType.UNDEFINED_FLEXO_CONCEPT_INSTANCE_TYPE;
			}
			// NPE Protection
			/*ViewPoint vp = this.getViewPoint();
			if (vp != null) {
				return vp.getInstanceType(getFlexoConceptType());
			} else {
				logger.warning("Adding FlexoConcept Instance in a null ViewPoint !");
				return null;
			}*/
		}

	}

	@DefineValidationRule
	public static class AddFlexoConceptInstanceMustAddressACreationScheme extends
			ValidationRule<AddFlexoConceptInstanceMustAddressACreationScheme, AddFlexoConceptInstance> {
		public AddFlexoConceptInstanceMustAddressACreationScheme() {
			super(AddFlexoConceptInstance.class, "add_flexo_concept_action_must_address_a_valid_creation_scheme");
		}

		@Override
		public ValidationIssue<AddFlexoConceptInstanceMustAddressACreationScheme, AddFlexoConceptInstance> applyValidation(
				AddFlexoConceptInstance action) {
			if (action.getCreationScheme() == null) {
				if (action.getFlexoConceptType() == null) {
					return new ValidationError<AddFlexoConceptInstanceMustAddressACreationScheme, AddFlexoConceptInstance>(this, action,
							"add_flexo_concept_action_doesn't_define_any_flexo_concept");
				} else {
					return new ValidationError<AddFlexoConceptInstanceMustAddressACreationScheme, AddFlexoConceptInstance>(this, action,
							"add_flexo_concept_action_doesn't_define_any_creation_scheme");
				}
			}
			return null;
		}
	}

	@DefineValidationRule
	public static class AddFlexoConceptInstanceParametersMustBeValid extends
			ValidationRule<AddFlexoConceptInstanceParametersMustBeValid, AddFlexoConceptInstance> {

		public AddFlexoConceptInstanceParametersMustBeValid() {
			super(AddFlexoConceptInstance.class, "add_flexo_concept_parameters_must_be_valid");
		}

		@Override
		public ValidationIssue<AddFlexoConceptInstanceParametersMustBeValid, AddFlexoConceptInstance> applyValidation(
				AddFlexoConceptInstance action) {
			if (action.getCreationScheme() != null) {
				Vector<ValidationIssue<AddFlexoConceptInstanceParametersMustBeValid, AddFlexoConceptInstance>> issues = new Vector<ValidationIssue<AddFlexoConceptInstanceParametersMustBeValid, AddFlexoConceptInstance>>();
				for (AddFlexoConceptInstanceParameter p : action.getParameters()) {

					FlexoBehaviourParameter param = p.getParam();
					if (param.getIsRequired()) {
						if (p.getValue() == null || !p.getValue().isSet()) {
							DataBinding<String> uri = ((URIParameter) param).getBaseURI();
							if (param instanceof URIParameter && uri.isSet() && uri.isValid()) {
								// Special case, we will find a way to manage this
							} else {
								issues.add(new ValidationError(this, action, "parameter_s_value_is_not_defined: " + param.getName()));
							}
						} else if (!p.getValue().isValid()) {
							AddFlexoConceptInstanceImpl.logger.info("Binding NOT valid: " + p.getValue() + " for " + p.getName()
									+ " object=" + p.getAction().getStringRepresentation() + ". Reason: "
									+ p.getValue().invalidBindingReason());
							issues.add(new ValidationError(this, action, "parameter_s_value_is_not_valid: " + param.getName()));
						}
					}
				}
				if (issues.size() == 0) {
					return null;
				} else if (issues.size() == 1) {
					return issues.firstElement();
				} else {
					return new CompoundIssue<AddFlexoConceptInstanceParametersMustBeValid, AddFlexoConceptInstance>(action, issues);
				}
			}
			return null;
		}
	}

	@DefineValidationRule
	public static class VirtualModelInstanceBindingIsRequiredAndMustBeValid extends
			BindingIsRequiredAndMustBeValid<AddFlexoConceptInstance> {
		public VirtualModelInstanceBindingIsRequiredAndMustBeValid() {
			super("'virtual_model_instance'_binding_is_not_valid", AddFlexoConceptInstance.class);
		}

		@Override
		public DataBinding<VirtualModelInstance> getBinding(AddFlexoConceptInstance object) {
			return object.getVirtualModelInstance();
		}

	}

}
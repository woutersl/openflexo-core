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
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.foundation.fml.ClassRole;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.SubClassOfClass;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlot;
import org.openflexo.model.annotations.DefineValidationRule;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.validation.FixProposal;
import org.openflexo.model.validation.ValidationError;
import org.openflexo.model.validation.ValidationIssue;
import org.openflexo.model.validation.ValidationRule;
import org.openflexo.toolbox.StringUtils;

@FIBPanel("Fib/FML/AddClassPanel.fib")
@ModelEntity(isAbstract = true)
@ImplementationClass(AddClass.AddClassImpl.class)
public abstract interface AddClass<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyClass> extends AddConcept<MS, T> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String CLASS_NAME_KEY = "className";
	@PropertyIdentifier(type = String.class)
	public static final String ONTOLOGY_CLASS_URI_KEY = "ontologyClassURI";
	@PropertyIdentifier(type = TypeAwareModelSlot.class)
	public static final String MODEL_SLOT_KEY = "modelSlot";

	@Getter(value = CLASS_NAME_KEY)
	@XMLAttribute(xmlTag = "newClassName")
	public DataBinding<String> getClassName();

	@Setter(CLASS_NAME_KEY)
	public void setClassName(DataBinding<String> className);

	@Getter(value = ONTOLOGY_CLASS_URI_KEY)
	@XMLAttribute
	public String _getOntologyClassURI();

	@Setter(ONTOLOGY_CLASS_URI_KEY)
	public void _setOntologyClassURI(String ontologyClassURI);

	public IFlexoOntologyClass getOntologyClass();

	@Override
	@Getter(value = MODEL_SLOT_KEY)
	@XMLAttribute
	public MS getModelSlot();

	@Override
	@Setter(MODEL_SLOT_KEY)
	public void setModelSlot(MS modelSlot);

	public static abstract class AddClassImpl<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyClass> extends
			AddConceptImpl<MS, T> implements AddClass<MS, T> {

		private static final Logger logger = Logger.getLogger(AddClass.class.getPackage().getName());

		private String ontologyClassURI = null;

		private DataBinding<String> className;

		public AddClassImpl() {
			super();
		}

		@Override
		public ClassRole getFlexoRole() {
			FlexoRole superFlexoRole = super.getFlexoRole();
			if (superFlexoRole instanceof ClassRole) {
				return (ClassRole) superFlexoRole;
			} else if (superFlexoRole != null) {
				// logger.warning("Unexpected pattern role of type " +
				// superPatternRole.getClass().getSimpleName());
				return null;
			}
			return null;
		}

		@Override
		public IFlexoOntologyClass getOntologyClass() {
			if (StringUtils.isNotEmpty(ontologyClassURI)) {
				return getVirtualModel().getOntologyClass(ontologyClassURI);
			} else {
				if (getFlexoRole() instanceof ClassRole) {
					return getFlexoRole().getOntologicType();
				}
			}
			return null;
		}

		public abstract Class<T> getOntologyClassClass();

		@Override
		public void setOntologyClass(IFlexoOntologyClass ontologyClass) {
			if (ontologyClass != null) {
				if (getFlexoRole() instanceof ClassRole) {
					if (getFlexoRole().getOntologicType().isSuperConceptOf(ontologyClass)) {
						ontologyClassURI = ontologyClass.getURI();
					} else {
						getFlexoRole().setOntologicType(ontologyClass);
					}
				} else {
					ontologyClassURI = ontologyClass.getURI();
				}
			} else {
				ontologyClassURI = null;
			}
		}

		@Override
		public String _getOntologyClassURI() {
			if (getOntologyClass() != null) {
				if (getFlexoRole() instanceof ClassRole && getFlexoRole().getOntologicType() == getOntologyClass()) {
					// No need to store an overriding type, just use default
					// provided by pattern role
					return null;
				}
				return getOntologyClass().getURI();
			}
			return ontologyClassURI;
		}

		@Override
		public void _setOntologyClassURI(String ontologyClassURI) {
			this.ontologyClassURI = ontologyClassURI;
		}

		@Override
		public DataBinding<String> getClassName() {
			if (className == null) {
				className = new DataBinding<String>(this, String.class, DataBinding.BindingDefinitionType.GET);
				className.setBindingName("className");
			}
			return className;
		}

		@Override
		public void setClassName(DataBinding<String> className) {
			if (className != null) {
				className.setOwner(this);
				className.setDeclaredType(String.class);
				className.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				className.setBindingName("className");
			}
			this.className = className;
		}

		@Override
		public Type getAssignableType() {
			if (getOntologyClass() == null) {
				return IFlexoOntologyClass.class;
			}
			return SubClassOfClass.getSubClassOfClass(getOntologyClass());
		}

	}

	@DefineValidationRule
	public static class AddClassActionMustDefineAnOntologyClass extends ValidationRule<AddClassActionMustDefineAnOntologyClass, AddClass> {
		public AddClassActionMustDefineAnOntologyClass() {
			super(AddClass.class, "add_individual_action_must_define_an_ontology_class");
		}

		@Override
		public ValidationIssue<AddClassActionMustDefineAnOntologyClass, AddClass> applyValidation(AddClass action) {
			if (action.getOntologyClass() == null) {
				Vector<FixProposal<AddClassActionMustDefineAnOntologyClass, AddClass>> v = new Vector<FixProposal<AddClassActionMustDefineAnOntologyClass, AddClass>>();
				for (ClassRole pr : action.getFlexoConcept().getClassRoles()) {
					v.add(new SetsPatternRole(pr));
				}
				return new ValidationError<AddClassActionMustDefineAnOntologyClass, AddClass>(this, action,
						"add_individual_action_does_not_define_any_ontology_class", v);
			}
			return null;
		}

		protected static class SetsPatternRole extends FixProposal<AddClassActionMustDefineAnOntologyClass, AddClass> {

			private final ClassRole patternRole;

			public SetsPatternRole(ClassRole patternRole) {
				super("assign_action_to_pattern_role_($patternRole.patternRoleName)");
				this.patternRole = patternRole;
			}

			public ClassRole getPatternRole() {
				return patternRole;
			}

			@Override
			protected void fixAction() {
				AddClass<?, ?> action = getValidable();
				action.setAssignation(new DataBinding<Object>(patternRole.getRoleName()));
			}

		}
	}

	@DefineValidationRule
	public static class URIBindingIsRequiredAndMustBeValid extends BindingIsRequiredAndMustBeValid<AddClass> {
		public URIBindingIsRequiredAndMustBeValid() {
			super("'uri'_binding_is_required_and_must_be_valid", AddClass.class);
		}

		@Override
		public DataBinding<String> getBinding(AddClass object) {
			return object.getClassName();
		}

	}

}
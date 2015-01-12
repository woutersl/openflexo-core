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
package org.openflexo.foundation.fml.editionaction;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.IndividualRole;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.IFlexoOntologyIndividual;
import org.openflexo.foundation.ontology.IndividualOfClass;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlot;
import org.openflexo.logging.FlexoLogger;
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
import org.openflexo.model.validation.FixProposal;
import org.openflexo.model.validation.ValidationError;
import org.openflexo.model.validation.ValidationIssue;
import org.openflexo.model.validation.ValidationRule;
import org.openflexo.toolbox.StringUtils;

@FIBPanel("Fib/FML/AddIndividualPanel.fib")
@ModelEntity(isAbstract = true)
@ImplementationClass(AddIndividual.AddIndividualImpl.class)
public abstract interface AddIndividual<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyIndividual<?>> extends
		AddConcept<MS, T> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String INDIVIDUAL_NAME_KEY = "individualName";
	@PropertyIdentifier(type = Vector.class)
	public static final String DATA_ASSERTIONS_KEY = "dataAssertions";
	@PropertyIdentifier(type = Vector.class)
	public static final String OBJECT_ASSERTIONS_KEY = "objectAssertions";
	@PropertyIdentifier(type = String.class)
	public static final String ONTOLOGY_CLASS_URI_KEY = "ontologyClassURI";
	@PropertyIdentifier(type = TypeAwareModelSlot.class)
	public static final String MODEL_SLOT_KEY = "modelSlot";

	@Getter(value = INDIVIDUAL_NAME_KEY)
	@XMLAttribute
	public DataBinding<String> getIndividualName();

	@Setter(INDIVIDUAL_NAME_KEY)
	public void setIndividualName(DataBinding<String> individualName);

	@Getter(value = DATA_ASSERTIONS_KEY, cardinality = Cardinality.LIST, inverse = DataPropertyAssertion.ACTION_KEY)
	@XMLElement(xmlTag = "DataPropertyAssertion")
	@Embedded
	@CloningStrategy(StrategyType.CLONE)
	public List<DataPropertyAssertion> getDataAssertions();

	@Setter(DATA_ASSERTIONS_KEY)
	public void setDataAssertions(List<DataPropertyAssertion> dataAssertions);

	@Adder(DATA_ASSERTIONS_KEY)
	public void addToDataAssertions(DataPropertyAssertion aDataAssertion);

	@Remover(DATA_ASSERTIONS_KEY)
	public void removeFromDataAssertions(DataPropertyAssertion aDataAssertion);

	@Getter(value = OBJECT_ASSERTIONS_KEY, cardinality = Cardinality.LIST, inverse = ObjectPropertyAssertion.ACTION_KEY)
	@XMLElement(xmlTag = "ObjectPropertyAssertion")
	@Embedded
	@CloningStrategy(StrategyType.CLONE)
	public List<ObjectPropertyAssertion> getObjectAssertions();

	@Setter(OBJECT_ASSERTIONS_KEY)
	public void setObjectAssertions(List<ObjectPropertyAssertion> objectAssertions);

	@Adder(OBJECT_ASSERTIONS_KEY)
	public void addToObjectAssertions(ObjectPropertyAssertion aObjectAssertion);

	@Remover(OBJECT_ASSERTIONS_KEY)
	public void removeFromObjectAssertions(ObjectPropertyAssertion aObjectAssertion);

	@Getter(value = ONTOLOGY_CLASS_URI_KEY)
	@XMLAttribute
	public String _getOntologyClassURI();

	@Setter(ONTOLOGY_CLASS_URI_KEY)
	public void _setOntologyClassURI(String ontologyClassURI);

	public IFlexoOntologyClass getOntologyClass();

	public void setOntologyClass(IFlexoOntologyClass ontologyClass);

	@Override
	@Getter(value = MODEL_SLOT_KEY)
	@XMLElement
	public MS getModelSlot();

	@Override
	@Setter(MODEL_SLOT_KEY)
	public void setModelSlot(MS modelSlot);

	public ObjectPropertyAssertion createObjectPropertyAssertion();

	public ObjectPropertyAssertion deleteObjectPropertyAssertion(ObjectPropertyAssertion assertion);

	public DataPropertyAssertion createDataPropertyAssertion();

	public DataPropertyAssertion deleteDataPropertyAssertion(DataPropertyAssertion assertion);

	public static abstract class AddIndividualImpl<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyIndividual<?>> extends
			AddConceptImpl<MS, T> implements AddIndividual<MS, T> {

		protected static final Logger logger = FlexoLogger.getLogger(AddIndividual.class.getPackage().getName());

		protected String ontologyClassURI = null;

		private DataBinding<String> individualName;

		/*@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			if (getAssignation().isSet()) {
				out.append(getAssignation().toString() + " = (", context);
			}
			out.append(getClass().getSimpleName() + (getOntologyClass() != null ? " conformTo " + getOntologyClass().getName() : "")
					+ " from " + (getModelSlot() != null ? getModelSlot().getName() : "") + " {" + StringUtils.LINE_SEPARATOR, context);
			out.append(getAssertionsFMLRepresentation(context), context);
			out.append("}", context);
			if (getAssignation().isSet()) {
				out.append(")", context);
			}
			return out.toString();
		}*/

		protected String getAssertionsFMLRepresentation(FMLRepresentationContext context) {
			if (getDataAssertions().size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (DataPropertyAssertion a : getDataAssertions()) {
					if (a.getOntologyProperty() != null) {
						sb.append("  " + a.getOntologyProperty().getName() + " = " + a.getValue().toString() + ";"
								+ StringUtils.LINE_SEPARATOR);
					}
				}
				return sb.toString();
			}
			if (getObjectAssertions().size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (ObjectPropertyAssertion a : getObjectAssertions()) {
					sb.append("  " + a.getOntologyProperty().getName() + " = " + a.getObject().toString() + ";"
							+ StringUtils.LINE_SEPARATOR);
				}
				return sb.toString();
			}
			return null;
		}

		public abstract Class<T> getOntologyIndividualClass();

		@Override
		public IndividualRole<T> getFlexoRole() {
			return (IndividualRole<T>) super.getFlexoRole();
		}

		public IFlexoOntologyClass<?> getType() {
			return getOntologyClass();
		}

		public void setType(IFlexoOntologyClass<?> type) {
			setOntologyClass(type);
		}

		@Override
		public IFlexoOntologyClass getOntologyClass() {
			// System.out.println("AddIndividual: ontologyClassURI=" + ontologyClassURI);
			if (StringUtils.isNotEmpty(ontologyClassURI) && getOwningVirtualModel() != null) {
				return getOwningVirtualModel().getOntologyClass(ontologyClassURI);
			} else {
				if (getFlexoRole() != null) {
					// System.out.println("Je reponds avec le pattern role " + getPatternRole());
					IFlexoOntologyClass<?> t = getFlexoRole().getOntologicType();
					setOntologyClass(t);
					return t;
				}
			}
			// System.out.println("Je reponds null");
			return null;
		}

		@Override
		public void setOntologyClass(IFlexoOntologyClass ontologyClass) {
			if (ontologyClass != null) {
				if (getFlexoRole() instanceof IndividualRole) {
					if (getFlexoRole().getOntologicType() != null) {
						if (getFlexoRole().getOntologicType().isSuperConceptOf(ontologyClass)) {
						} else {
							getFlexoRole().setOntologicType(ontologyClass);
						}
					} else {
						getFlexoRole().setOntologicType(ontologyClass);
					}
				}
				ontologyClassURI = ontologyClass.getURI();
			} else {
				ontologyClassURI = null;
			}
			// System.out.println("ontologyClassURI=" + ontologyClassURI);
		}

		@Override
		public String _getOntologyClassURI() {
			if (getOntologyClass() != null) {
				if (getFlexoRole() instanceof IndividualRole && getFlexoRole().getOntologicType() == getOntologyClass()) {
					// No need to store an overriding type, just use default provided by pattern role
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
		public DataPropertyAssertion createDataPropertyAssertion() {
			DataPropertyAssertion newDataPropertyAssertion = getFMLModelFactory().newDataPropertyAssertion();
			addToDataAssertions(newDataPropertyAssertion);
			return newDataPropertyAssertion;
		}

		@Override
		public DataPropertyAssertion deleteDataPropertyAssertion(DataPropertyAssertion assertion) {
			removeFromDataAssertions(assertion);
			assertion.delete();
			return assertion;
		}

		@Override
		public ObjectPropertyAssertion createObjectPropertyAssertion() {
			ObjectPropertyAssertion newObjectPropertyAssertion = getFMLModelFactory().newObjectPropertyAssertion();
			addToObjectAssertions(newObjectPropertyAssertion);
			return newObjectPropertyAssertion;
		}

		@Override
		public ObjectPropertyAssertion deleteObjectPropertyAssertion(ObjectPropertyAssertion assertion) {
			removeFromObjectAssertions(assertion);
			assertion.delete();
			return assertion;
		}

		@Override
		public DataBinding<String> getIndividualName() {
			if (individualName == null) {
				individualName = new DataBinding<String>(this, String.class, DataBinding.BindingDefinitionType.GET);
				individualName.setBindingName("individualName");
			}
			return individualName;
		}

		@Override
		public void setIndividualName(DataBinding<String> individualName) {
			if (individualName != null) {
				individualName.setOwner(this);
				individualName.setDeclaredType(String.class);
				individualName.setBindingDefinitionType(DataBinding.BindingDefinitionType.GET);
				individualName.setBindingName("individualName");
			}
			this.individualName = individualName;
		}

		@Override
		public Type getAssignableType() {
			if (getOntologyClass() == null) {
				return IFlexoOntologyIndividual.class;
			}
			return IndividualOfClass.getIndividualOfClass(getOntologyClass());
		}

	}

	@DefineValidationRule
	public static class AddIndividualActionMustDefineAnOntologyClass extends
			ValidationRule<AddIndividualActionMustDefineAnOntologyClass, AddIndividual> {
		public AddIndividualActionMustDefineAnOntologyClass() {
			super(AddIndividual.class, "add_individual_action_must_define_an_ontology_class");
		}

		@Override
		public ValidationIssue<AddIndividualActionMustDefineAnOntologyClass, AddIndividual> applyValidation(AddIndividual action) {
			if (action.getOntologyClass() == null && action.getOwner() instanceof AssignationAction) {
				Vector<FixProposal<AddIndividualActionMustDefineAnOntologyClass, AddIndividual>> v = new Vector<FixProposal<AddIndividualActionMustDefineAnOntologyClass, AddIndividual>>();
				for (IndividualRole pr : action.getFlexoConcept().getIndividualRoles()) {
					v.add(new SetsFlexoRole(pr));
				}
				return new ValidationError<AddIndividualActionMustDefineAnOntologyClass, AddIndividual>(this, action,
						"add_individual_action_does_not_define_any_ontology_class", v);
			}
			return null;
		}

		protected static class SetsFlexoRole extends FixProposal<AddIndividualActionMustDefineAnOntologyClass, AddIndividual> {

			private final IndividualRole flexoRole;

			public SetsFlexoRole(IndividualRole flexoRole) {
				super("assign_action_to_flexo_role_($flexoRole.flexoRoleName)");
				this.flexoRole = flexoRole;
			}

			public IndividualRole getFlexoRole() {
				return flexoRole;
			}

			@Override
			protected void fixAction() {
				AddIndividual<?, ?> action = getValidable();
				((AssignationAction) action.getOwner()).setAssignation(new DataBinding<Object>(flexoRole.getRoleName()));
			}

		}
	}

}

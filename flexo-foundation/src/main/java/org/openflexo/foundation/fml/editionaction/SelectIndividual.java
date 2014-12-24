/*
 * (c) Copyright 2010-2012 AgileBirds
 * (c) Copyright 2013 Openflexo
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

import java.util.logging.Logger;

import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.fml.annotations.FIBPanel;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.IFlexoOntologyIndividual;
import org.openflexo.foundation.ontology.IndividualOfClass;
import org.openflexo.foundation.technologyadapter.FlexoMetaModel;
import org.openflexo.foundation.technologyadapter.TypeAwareModelSlot;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.toolbox.StringUtils;

/**
 * Generic {@link FetchRequest} allowing to retrieve a selection of some individuals matching some conditions and a given type.<br>
 * This action is technology-specific and must be redefined in a given technology
 * 
 * @author sylvain
 * 
 * @param <M>
 * @param <MM>
 * @param <T>
 */
@FIBPanel("Fib/FML/SelectIndividualPanel.fib")
@ModelEntity(isAbstract = true)
@ImplementationClass(SelectIndividual.SelectIndividualImpl.class)
public abstract interface SelectIndividual<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyIndividual> extends
		FetchRequest<MS, T> {

	@PropertyIdentifier(type = String.class)
	public static final String ONTOLOGY_CLASS_URI_KEY = "ontologyClassURI";

	@Getter(value = ONTOLOGY_CLASS_URI_KEY)
	@XMLAttribute
	public String _getOntologyClassURI();

	@Setter(ONTOLOGY_CLASS_URI_KEY)
	public void _setOntologyClassURI(String ontologyClassURI);

	public IFlexoOntologyClass getType();

	public void setType(IFlexoOntologyClass ontologyClass);

	public FlexoMetaModel getMetaModelData();

	public static abstract class SelectIndividualImpl<MS extends TypeAwareModelSlot<?, ?>, T extends IFlexoOntologyIndividual> extends
			FetchRequestImpl<MS, T> implements SelectIndividual<MS, T> {

		protected static final Logger logger = FlexoLogger.getLogger(SelectIndividual.class.getPackage().getName());

		private String typeURI = null;

		public SelectIndividualImpl() {
			super();
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			if (getAssignation().isSet()) {
				out.append(getAssignation().toString() + " = (", context);
			}
			out.append(getImplementedInterface().getSimpleName() + (getModelSlot() != null ? " from " + getModelSlot().getName() : " ")
					+ (getType() != null ? " as " + getType().getName() : "")
					+ (getConditions().size() > 0 ? " " + getWhereClausesFMLRepresentation(context) : ""), context);
			if (getAssignation().isSet()) {
				out.append(")", context);
			}
			return out.toString();
		}

		@Override
		public IndividualOfClass getFetchedType() {
			return IndividualOfClass.getIndividualOfClass(getType());
		}

		@Override
		public IFlexoOntologyClass getType() {
			if (StringUtils.isNotEmpty(typeURI) && getModelSlot() != null && getModelSlot().getMetaModelResource() != null
					&& getModelSlot().getMetaModelResource().getMetaModelData() != null) {
				return (IFlexoOntologyClass) getModelSlot().getMetaModelResource().getMetaModelData().getObject(typeURI);
			}
			return null;
		}

		@Override
		public void setType(IFlexoOntologyClass ontologyClass) {
			if (ontologyClass != null) {
				typeURI = ontologyClass.getURI();
			} else {
				typeURI = null;
			}
		}

		@Override
		public FlexoMetaModel getMetaModelData() {
			/*if (StringUtils.isNotEmpty(typeURI) && getModelSlot() != null && getModelSlot().getMetaModelResource() != null
					&& getModelSlot().getMetaModelResource().getMetaModelData() != null) {
				return getModelSlot().getMetaModelResource().getMetaModelData();
			}*/
			if (getModelSlot() != null && getModelSlot().getMetaModelResource() != null
					&& getModelSlot().getMetaModelResource().getMetaModelData() != null) {
				return getModelSlot().getMetaModelResource().getMetaModelData();
			}
			return null;
		}

		@Override
		public String _getOntologyClassURI() {
			if (getType() != null) {
				return getType().getURI();
			}
			return typeURI;
		}

		@Override
		public void _setOntologyClassURI(String ontologyClassURI) {
			this.typeURI = ontologyClassURI;
		}

		/*@Override
		public String getStringRepresentation() {
			return getClass().getSimpleName() + (getType() != null ? " : " + getType().getName() : "")
					+ (StringUtils.isNotEmpty(getAssignation().toString()) ? " (" + getAssignation().toString() + ")" : "");
		}*/

		@Override
		public String getStringRepresentation() {
			return getImplementedInterface().getSimpleName() + (getType() != null ? " : " + getType().getName() : "")
					+ (StringUtils.isNotEmpty(getAssignation().toString()) ? " (" + getAssignation().toString() + ")" : "");
		}
	}
}
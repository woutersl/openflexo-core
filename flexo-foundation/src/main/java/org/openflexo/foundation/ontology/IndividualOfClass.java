/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
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

package org.openflexo.foundation.ontology;

import java.lang.reflect.Type;

import org.openflexo.connie.type.CustomTypeFactory;
import org.openflexo.foundation.fml.TechnologyAdapterTypeFactory;
import org.openflexo.foundation.fml.TechnologySpecificType;
import org.openflexo.foundation.fml.rt.FMLRTTechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.utils.FlexoObjectReference;
import org.openflexo.foundation.utils.FlexoObjectReference.ReferenceOwner;

public class IndividualOfClass<TA extends TechnologyAdapter> implements TechnologySpecificType<TA> {

	/**
	 * Factory for IndividualOfClass instances
	 * 
	 * @author sylvain
	 * 
	 */
	public static class IndividualOfClassTypeFactory extends TechnologyAdapterTypeFactory<IndividualOfClass<?>> implements ReferenceOwner {

		public IndividualOfClassTypeFactory(FMLRTTechnologyAdapter technologyAdapter) {
			super(technologyAdapter);
		}

		@Override
		public IndividualOfClass<?> makeCustomType(String configuration) {

			FlexoObjectReference<IFlexoOntologyClass<?>> reference = new FlexoObjectReference<IFlexoOntologyClass<?>>(configuration, this);

			IFlexoOntologyClass<?> ontologyClass = reference.getObject();

			if (ontologyClass != null) {
				return getIndividualOfClass(ontologyClass);
			}
			return null;
		}

		@Override
		public void configureFactory(IndividualOfClass<?> type) {
			// TODO Auto-generated method stub
		}

		@Override
		public void notifyObjectLoaded(FlexoObjectReference<?> reference) {
		}

		@Override
		public void objectCantBeFound(FlexoObjectReference<?> reference) {
		}

		@Override
		public void objectDeleted(FlexoObjectReference<?> reference) {
		}

		@Override
		public void objectSerializationIdChanged(FlexoObjectReference<?> reference) {
		}

	}

	public static <TA extends TechnologyAdapter> IndividualOfClass<TA> getIndividualOfClass(IFlexoOntologyClass<TA> anOntologyClass) {
		if (anOntologyClass == null) {
			return null;
		}
		return anOntologyClass.getTechnologyAdapter().getTechnologyContextManager().getIndividualOfClass(anOntologyClass);
	}

	private final IFlexoOntologyClass<TA> ontologyClass;

	public IndividualOfClass(IFlexoOntologyClass<TA> anOntologyClass) {
		this.ontologyClass = anOntologyClass;
	}

	public IFlexoOntologyClass<TA> getOntologyClass() {
		if (ontologyClass != null) {
			return ontologyClass;
		}
		return null;
	}

	@Override
	public Class<?> getBaseClass() {
		return IFlexoOntologyIndividual.class;
	}

	@Override
	public boolean isTypeAssignableFrom(Type aType, boolean permissive) {
		// System.out.println("isTypeAssignableFrom " + aType + " (i am a " + this + ")");
		if (aType instanceof IndividualOfClass) {
			return ontologyClass.isSuperConceptOf(((IndividualOfClass<TA>) aType).getOntologyClass());
		}
		return false;
	}

	@Override
	public String simpleRepresentation() {
		return getClass().getSimpleName() + "(" + (ontologyClass != null ? ontologyClass.getName() : "") + ")";
	}

	@Override
	public String fullQualifiedRepresentation() {
		return getClass().getName() + "(" + getSerializationRepresentation() + ")";
	}

	@Override
	public String getSerializationRepresentation() {
		return new FlexoObjectReference<IFlexoOntologyClass<TA>>(ontologyClass).getStringRepresentation();
	}

	@Override
	public TA getSpecificTechnologyAdapter() {
		if (getOntologyClass() != null) {
			return getOntologyClass().getTechnologyAdapter();
		}
		return null;
	}

	@Override
	public boolean isResolved() {
		return ontologyClass != null;
	}

	@Override
	public void resolve(CustomTypeFactory<?> factory) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ontologyClass == null) ? 0 : ontologyClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndividualOfClass<?> other = (IndividualOfClass<?>) obj;
		if (ontologyClass == null) {
			if (other.ontologyClass != null)
				return false;
		} else if (!ontologyClass.equals(other.ontologyClass))
			return false;
		return true;
	}

}

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
package org.openflexo.foundation.fml;

import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;

/**
 * Represents an object which is part of the model of an FlexoConcept
 * 
 * @author sylvain
 * 
 */
@ModelEntity(isAbstract = true)
@ImplementationClass(FlexoConceptObject.FlexoConceptObjectImpl.class)
public interface FlexoConceptObject extends NamedFMLObject {

	public VirtualModelModelFactory getVirtualModelFactory();

	public FlexoConcept getFlexoConcept();

	@Override
	public ViewPoint getViewPoint();

	public VirtualModel getVirtualModel();

	@Override
	public String getStringRepresentation();

	@Override
	public String getFMLRepresentation(FMLRepresentationContext context);

	public abstract class FlexoConceptObjectImpl extends NamedFMLObjectImpl implements FlexoConceptObject {

		@Override
		public VirtualModelModelFactory getVirtualModelFactory() {
			if (getVirtualModel() != null) {
				return getVirtualModel().getVirtualModelFactory();
			}
			return null;
		}

		@Override
		public abstract FlexoConcept getFlexoConcept();

		@Override
		public ViewPoint getViewPoint() {
			if (getVirtualModel() != null) {
				return getVirtualModel().getViewPoint();
			}
			if (getFlexoConcept() != null && getFlexoConcept() != this) {
				return getFlexoConcept().getViewPoint();
			}
			return null;

		}

		@Override
		public VirtualModel getResourceData() {
			return getVirtualModel();
		}

		@Override
		public VirtualModel getVirtualModel() {
			if (getFlexoConcept() != null) {
				return getFlexoConcept().getVirtualModel();
			}
			return null;
		}

		@Override
		public String getStringRepresentation() {
			return (getVirtualModel() != null ? getVirtualModel().getStringRepresentation() : "null") + "#"
					+ (getFlexoConcept() != null ? getFlexoConcept().getName() : "null") + "." + getClass().getSimpleName();
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			return "<not_implemented:" + getStringRepresentation() + ">";
		}

		@Override
		public void setChanged() {
			super.setChanged();
			if (getVirtualModel() != null) {
				getVirtualModel().setIsModified();
			}
		}

	}
}
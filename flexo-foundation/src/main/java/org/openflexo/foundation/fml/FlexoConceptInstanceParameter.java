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

import java.lang.reflect.Type;
import java.util.List;

import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.fml.rt.FMLRTModelSlot;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(FlexoConceptInstanceParameter.FlexoConceptInstanceParameterImpl.class)
@XMLElement
public interface FlexoConceptInstanceParameter extends InnerModelSlotParameter<FMLRTModelSlot> {

	@PropertyIdentifier(type = String.class)
	public static final String FLEXO_CONCEPT_TYPE_URI_KEY = "flexoConceptTypeURI";

	@Getter(value = FLEXO_CONCEPT_TYPE_URI_KEY)
	@XMLAttribute
	public String _getFlexoConceptTypeURI();

	@Setter(FLEXO_CONCEPT_TYPE_URI_KEY)
	public void _setFlexoConceptTypeURI(String flexoConceptTypeURI);

	public FlexoConcept getFlexoConceptType();

	public void setFlexoConceptType(FlexoConcept flexoConceptType);

	public VirtualModel getModelSlotVirtualModel();

	public static abstract class FlexoConceptInstanceParameterImpl extends InnerModelSlotParameterImpl<FMLRTModelSlot> implements
			FlexoConceptInstanceParameter {

		private FlexoConcept flexoConceptType;
		private String flexoConceptTypeURI;

		public FlexoConceptInstanceParameterImpl() {
			super();
		}

		@Override
		public Type getType() {
			if (getFlexoConceptType() != null) {
				return getViewPoint().getInstanceType(getFlexoConceptType());
			}
			return FlexoConceptInstance.class;
		}

		@Override
		public WidgetType getWidget() {
			return WidgetType.FLEXO_CONCEPT;
		}

		@Override
		public String _getFlexoConceptTypeURI() {
			if (flexoConceptType != null) {
				return flexoConceptType.getURI();
			}
			return flexoConceptTypeURI;
		}

		@Override
		public void _setFlexoConceptTypeURI(String flexoConceptURI) {
			this.flexoConceptTypeURI = flexoConceptURI;
		}

		@Override
		public FlexoConcept getFlexoConceptType() {
			if (flexoConceptType == null && flexoConceptTypeURI != null) {
				flexoConceptType = getViewPoint().getFlexoConcept(flexoConceptTypeURI);
			}
			return flexoConceptType;
		}

		@Override
		public void setFlexoConceptType(FlexoConcept flexoConceptType) {
			if (flexoConceptType != this.flexoConceptType) {
				FlexoConcept oldValue = this.flexoConceptType;
				this.flexoConceptType = flexoConceptType;
				/*for (FlexoBehaviour s : getFlexoConcept().getFlexoBehaviours()) {
					s.updateBindingModels();
				}*/
				getPropertyChangeSupport().firePropertyChange("flexoConceptType", oldValue, flexoConceptType);
			}
		}

		@Override
		public void setModelSlot(FMLRTModelSlot modelSlot) {
			super.setModelSlot(modelSlot);
			setChanged();
			notifyObservers(new DataModification("modelSlotVirtualModel", null, modelSlot));
		}

		@Override
		public VirtualModel getModelSlotVirtualModel() {
			if (getModelSlot() != null && getModelSlot().getVirtualModelResource() != null) {
				return getModelSlot().getVirtualModelResource().getVirtualModel();
			}
			return null;
		}

		@Override
		public FMLRTModelSlot getModelSlot() {
			if (super.getModelSlot() instanceof FMLRTModelSlot) {
				FMLRTModelSlot returned = super.getModelSlot();
				if (returned == null) {
					if (getVirtualModel() != null && getVirtualModel().getModelSlots(FMLRTModelSlot.class).size() > 0) {
						return getVirtualModel().getModelSlots(FMLRTModelSlot.class).get(0);
					}
				}
				return returned;
			}
			return null;
		}

		@Override
		public List<FMLRTModelSlot> getAccessibleModelSlots() {
			return getVirtualModel().getModelSlots(FMLRTModelSlot.class);
		}

	}
}
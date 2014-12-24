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

package org.openflexo.foundation.fml.rt;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptInstanceRole;
import org.openflexo.foundation.fml.FlexoConceptInstanceType;
import org.openflexo.foundation.fml.FlexoRole;
import org.openflexo.foundation.fml.PrimitiveRole;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.rm.VirtualModelResource;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.ModelSlotInstanceConfiguration;
import org.openflexo.foundation.fml.rt.editionaction.AddFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.DeleteFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.SelectFlexoConceptInstance;
import org.openflexo.foundation.technologyadapter.DeclareEditionAction;
import org.openflexo.foundation.technologyadapter.DeclareEditionActions;
import org.openflexo.foundation.technologyadapter.DeclareFetchRequest;
import org.openflexo.foundation.technologyadapter.DeclareFetchRequests;
import org.openflexo.foundation.technologyadapter.DeclarePatternRole;
import org.openflexo.foundation.technologyadapter.DeclarePatternRoles;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.toolbox.StringUtils;

/**
 * Implementation of the ModelSlot for a FML VirtualModel
 * 
 * @author sylvain, christophe
 * 
 */
@DeclarePatternRoles({ // All pattern roles available through this model slot
@DeclarePatternRole(FML = "FlexoConceptInstanceRole", flexoRoleClass = FlexoConceptInstanceRole.class), // FlexoConceptInstance
		@DeclarePatternRole(FML = "PrimitiveRole", flexoRoleClass = PrimitiveRole.class) // PrimitiveRole
})
@DeclareEditionActions({ // All edition actions available through this model slot
@DeclareEditionAction(FML = "AddFlexoConceptInstance", editionActionClass = AddFlexoConceptInstance.class),
		@DeclareEditionAction(FML = "DeleteFlexoConceptInstance", editionActionClass = DeleteFlexoConceptInstance.class) })
@DeclareFetchRequests({ // All requests available through this model slot
@DeclareFetchRequest(FML = "SelectFlexoConceptInstance", fetchRequestClass = SelectFlexoConceptInstance.class) })
@ModelEntity
@ImplementationClass(FMLRTModelSlot.FMLRTModelSlotImpl.class)
@XMLElement
public interface FMLRTModelSlot extends ModelSlot<VirtualModelInstance> {

	@PropertyIdentifier(type = String.class)
	public static final String VIRTUAL_MODEL_URI_KEY = "virtualModelURI";

	@Getter(value = VIRTUAL_MODEL_URI_KEY)
	@XMLAttribute
	public String getVirtualModelURI();

	@Setter(VIRTUAL_MODEL_URI_KEY)
	public void setVirtualModelURI(String virtualModelURI);

	public VirtualModelResource getVirtualModelResource();

	public void setVirtualModelResource(VirtualModelResource virtualModelResource);

	public VirtualModel getAddressedVirtualModel();

	public void setAddressedVirtualModel(VirtualModel aVirtualModel);

	// public boolean isReflexiveModelSlot();

	public FlexoConceptInstanceRole makeFlexoConceptInstanceRole(FlexoConcept flexoConcept);

	public static abstract class FMLRTModelSlotImpl extends ModelSlotImpl<VirtualModelInstance> implements FMLRTModelSlot {

		private static final Logger logger = Logger.getLogger(FMLRTModelSlot.class.getPackage().getName());

		@Override
		public String getStringRepresentation() {
			return "FMLRTModelSlot";
		}

		@Override
		public Class getTechnologyAdapterClass() {
			return FMLTechnologyAdapter.class;
		}

		@Override
		public FlexoConceptInstanceRole makeFlexoConceptInstanceRole(FlexoConcept flexoConcept) {
			FlexoConceptInstanceRole returned = makeFlexoRole(FlexoConceptInstanceRole.class);
			returned.setFlexoConceptType(flexoConcept);
			returned.setModelSlot(this);
			return returned;
		}

		@Override
		public <PR extends FlexoRole<?>> String defaultFlexoRoleName(Class<PR> patternRoleClass) {
			if (FlexoConceptInstanceRole.class.isAssignableFrom(patternRoleClass)) {
				return "flexoConceptInstance";
			} else if (PrimitiveRole.class.isAssignableFrom(patternRoleClass)) {
				return "primitive";
			}
			logger.warning("Unexpected pattern role: " + patternRoleClass.getName());
			return null;
		}

		@Override
		public ModelSlotInstanceConfiguration<? extends FMLRTModelSlot, VirtualModelInstance> createConfiguration(
				CreateVirtualModelInstance action) {
			return new FMLRTModelSlotInstanceConfiguration(this, action);
		}

		private VirtualModelResource virtualModelResource;
		private String virtualModelURI;

		@Override
		public VirtualModelResource getVirtualModelResource() {
			if (virtualModelResource == null && StringUtils.isNotEmpty(virtualModelURI) && getViewPoint() != null) {
				if (getViewPoint().getVirtualModelNamed(virtualModelURI) != null) {
					virtualModelResource = (VirtualModelResource) getViewPoint().getVirtualModelNamed(virtualModelURI).getResource();
					logger.info("Looked-up " + virtualModelResource);
				}
			}
			return virtualModelResource;
		}

		@Override
		public void setVirtualModelResource(VirtualModelResource virtualModelResource) {
			this.virtualModelResource = virtualModelResource;
		}

		@Override
		public Type getType() {
			return FlexoConceptInstanceType.getFlexoConceptInstanceType(getAddressedVirtualModel());
		}

		@Override
		public String getPreciseType() {
			return "Virtual Model";
		};

		@Override
		public String getVirtualModelURI() {
			if (virtualModelResource != null) {
				return virtualModelResource.getURI();
			}
			return virtualModelURI;
		}

		@Override
		public void setVirtualModelURI(String metaModelURI) {
			this.virtualModelURI = metaModelURI;
		}

		/**
		 * Return adressed virtual model (the virtual model this model slot specifically adresses, not the one in which it is defined)
		 * 
		 * @return
		 */
		@Override
		public VirtualModel getAddressedVirtualModel() {
			if (getViewPoint() != null && StringUtils.isNotEmpty(getVirtualModelURI())) {
				return getViewPoint().getVirtualModelNamed(getVirtualModelURI());
			}
			return null;
		}

		@Override
		public void setAddressedVirtualModel(VirtualModel aVirtualModel) {
			this.virtualModelURI = aVirtualModel.getURI();
		}

		/**
		 * Return flag indicating if this model slot is the reflexive model slot for virtual model container
		 * 
		 * @return
		 */
		/*@Override
		public boolean isReflexiveModelSlot() {
			return getName() != null && getName().equals(VirtualModel.REFLEXIVE_MODEL_SLOT_NAME)
					&& getVirtualModelResource() == getVirtualModel().getResource();
		}*/

		/**
		 * 
		 * @param msInstance
		 * @param o
		 * @return URI as String
		 */
		@Override
		public String getURIForObject(ModelSlotInstance msInstance, Object o) {
			logger.warning("This method should be refined by child classes");
			return null;
		}

		/**
		 * @param msInstance
		 * @param objectURI
		 * @return the Object
		 */
		@Override
		public Object retrieveObjectWithURI(ModelSlotInstance msInstance, String objectURI) {
			logger.warning("This method should be refined by child classes");
			return null;
		}

		@Override
		public String getModelSlotDescription() {
			return "Virtual Model conform to " + getVirtualModelURI() /*+ (isReflexiveModelSlot() ? " [reflexive]" : "")*/;
		}

	}
}
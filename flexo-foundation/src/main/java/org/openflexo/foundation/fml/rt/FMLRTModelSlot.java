/**
 * 
 * Copyright (c) 2014-2015, Openflexo
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
import org.openflexo.foundation.fml.annotations.DeclareEditionActions;
import org.openflexo.foundation.fml.annotations.DeclareFetchRequests;
import org.openflexo.foundation.fml.annotations.DeclareFlexoRoles;
import org.openflexo.foundation.fml.annotations.FML;
import org.openflexo.foundation.fml.rm.VirtualModelResource;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.ModelSlotInstanceConfiguration;
import org.openflexo.foundation.fml.rt.editionaction.AddFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.DeleteFlexoConceptInstance;
import org.openflexo.foundation.fml.rt.editionaction.SelectFlexoConceptInstance;
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
@DeclareFlexoRoles({ FlexoConceptInstanceRole.class, PrimitiveRole.class })
@DeclareEditionActions({ AddFlexoConceptInstance.class, DeleteFlexoConceptInstance.class })
@DeclareFetchRequests({ SelectFlexoConceptInstance.class })
@ModelEntity
@ImplementationClass(FMLRTModelSlot.FMLRTModelSlotImpl.class)
@XMLElement
@FML("FMLRTModelSlot")
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
			logger.warning("Unexpected pattern property: " + patternRoleClass.getName());
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
		public String getTypeDescription() {
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
			notifyResultingTypeChanged();
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

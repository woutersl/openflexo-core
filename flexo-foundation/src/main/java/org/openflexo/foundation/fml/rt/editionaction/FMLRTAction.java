/**
 * 
 * Copyright (c) 2015, Openflexo
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

package org.openflexo.foundation.fml.rt.editionaction;

import java.util.logging.Logger;

import org.openflexo.connie.DataBinding;
import org.openflexo.foundation.fml.editionaction.TechnologySpecificAction;
import org.openflexo.foundation.fml.rt.FMLRTModelSlot;
import org.openflexo.foundation.fml.rt.ViewObject;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;

/**
 * This action is used to handle data inside a {@link VirtualModelInstance}
 * 
 * @author sylvain
 * 
 */

@ModelEntity(isAbstract = true)
@ImplementationClass(FMLRTAction.FMLRTActionImpl.class)
public interface FMLRTAction<T extends ViewObject> extends TechnologySpecificAction<FMLRTModelSlot, T> {

	@PropertyIdentifier(type = DataBinding.class)
	public static final String VIRTUAL_MODEL_INSTANCE_KEY = "virtualModelInstance";

	@Getter(value = VIRTUAL_MODEL_INSTANCE_KEY)
	@XMLAttribute
	public DataBinding<VirtualModelInstance> getVirtualModelInstance();

	@Setter(VIRTUAL_MODEL_INSTANCE_KEY)
	public void setVirtualModelInstance(DataBinding<VirtualModelInstance> virtualModelInstance);

	public static abstract class FMLRTActionImpl<T extends ViewObject> extends TechnologySpecificActionImpl<FMLRTModelSlot, T> implements
			FMLRTAction<T> {

		static final Logger logger = Logger.getLogger(FMLRTAction.class.getPackage().getName());

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

	}

}

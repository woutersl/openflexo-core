/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Flexodiagram, a component of the software infrastructure 
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

package org.openflexo.foundation.fml.rt.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.action.FlexoGUIAction;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;

public class OpenVirtualModelInstance extends FlexoGUIAction<OpenVirtualModelInstance, VirtualModelInstance, FlexoObject> {

	private static final Logger logger = Logger.getLogger(OpenVirtualModelInstance.class.getPackage().getName());

	public static FlexoActionType<OpenVirtualModelInstance, VirtualModelInstance, FlexoObject> actionType = new FlexoActionType<OpenVirtualModelInstance, VirtualModelInstance, FlexoObject>(
			"open_virtual_model_instance", FlexoActionType.defaultGroup, FlexoActionType.NORMAL_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public OpenVirtualModelInstance makeNewAction(VirtualModelInstance focusedObject, Vector<FlexoObject> globalSelection,
				FlexoEditor editor) {
			return new OpenVirtualModelInstance(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(VirtualModelInstance virtualModelInstance, Vector<FlexoObject> globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(VirtualModelInstance view, Vector<FlexoObject> globalSelection) {
			return true;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(OpenVirtualModelInstance.actionType, VirtualModelInstance.class);
	}

	OpenVirtualModelInstance(VirtualModelInstance focusedObject, Vector<FlexoObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

}
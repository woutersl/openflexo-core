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

package org.openflexo.foundation.fml.rt.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.FlexoBehaviour;
import org.openflexo.foundation.fml.NavigationScheme;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstanceObject;

public class NavigationSchemeAction extends FlexoBehaviourAction<NavigationSchemeAction, NavigationScheme, FlexoConceptInstance> {

	private static final Logger logger = Logger.getLogger(NavigationSchemeAction.class.getPackage().getName());

	private final NavigationSchemeActionType actionType;

	public NavigationSchemeAction(NavigationSchemeActionType actionType, FlexoConceptInstance focusedObject,
			Vector<VirtualModelInstanceObject> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
		this.actionType = actionType;
	}

	public NavigationScheme getNavigationScheme() {
		if (actionType != null) {
			return actionType.getNavigationScheme();
		}
		return null;
	}

	/**
	 * Return the {@link FlexoConceptInstance} on which this {@link FlexoBehaviour} is applied.<br>
	 * We want to navigate to this {@link FlexoConceptInstance}
	 * 
	 * @return
	 */
	@Override
	public FlexoConceptInstance getFlexoConceptInstance() {
		if (actionType != null) {
			return actionType.getFlexoConceptInstance();
		}
		return null;
	}

	@Override
	public NavigationScheme getFlexoBehaviour() {
		return getNavigationScheme();
	}

	@Override
	protected void doAction(Object context) throws FlexoException {
		logger.info("Perform navigation " + actionType);

		if (evaluateCondition()) {
			// If target diagram is not existant, we must create it
			if (getTargetObject() == null) {
				applyEditionActions();
			}
		}
	}

	public boolean evaluateCondition() {
		if (getNavigationScheme() == null) {
			logger.warning("No navigation scheme. Please investigate !");
			return false;
		}
		return getNavigationScheme().evaluateCondition(actionType.getFlexoConceptInstance());
	}

	public FlexoObject getTargetObject() {
		if (getNavigationScheme() == null) {
			logger.warning("No navigation scheme. Please investigate !");
			return null;
		}
		return getNavigationScheme().evaluateTargetObject(actionType.getFlexoConceptInstance());
	}

	@Override
	public VirtualModelInstance retrieveVirtualModelInstance() {
		/*if (getFocusedObject() instanceof DiagramElement<?>) {
			return ((DiagramElement<?>) getFocusedObject()).getDiagram();
		}*/
		return getFlexoConceptInstance().getVirtualModelInstance();
	}

}

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
package org.openflexo.foundation.view.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.view.VirtualModelInstanceObject;
import org.openflexo.foundation.viewpoint.EditionScheme;
import org.openflexo.foundation.viewpoint.NavigationScheme;

public class NavigationSchemeAction extends EditionSchemeAction<NavigationSchemeAction, NavigationScheme, FlexoConceptInstance> {

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
	 * Return the {@link FlexoConceptInstance} on which this {@link EditionScheme} is applied.<br>
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
	public NavigationScheme getEditionScheme() {
		return getNavigationScheme();
	}

	@Override
	protected void doAction(Object context) {
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

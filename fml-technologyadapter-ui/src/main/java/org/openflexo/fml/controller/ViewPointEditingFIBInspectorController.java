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
package org.openflexo.fml.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.fib.FIBLibrary;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObservable;
import org.openflexo.foundation.FlexoProperty;
import org.openflexo.foundation.InnerResourceData;
import org.openflexo.foundation.action.AddFlexoProperty;
import org.openflexo.foundation.resource.ResourceLoaded;
import org.openflexo.inspector.FIBInspector;
import org.openflexo.inspector.ModuleInspectorController;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.rm.Resource;

/**
 * Represents the controller of a FIBInspector (FIBComponent) in the context of Swing graphical inspection
 * 
 * @author sylvain
 * 
 */
public class ViewPointEditingFIBInspectorController extends ViewPointEditingFIBController {

	private static final Logger logger = FlexoLogger.getLogger(ViewPointEditingFIBInspectorController.class.getPackage().getName());

	public ViewPointEditingFIBInspectorController(FIBComponent component) {
		super(component);
	}

	public boolean displayInspectorTabForContext(String context) {
		if (getFlexoController() != null) {
			return getFlexoController().displayInspectorTabForContext(context);
		} else {
			return true;
		}
	}

	@Override
	public Object getValue(BindingVariable variable) {
		/*if (variable instanceof FlexoConceptInstanceBindingVariable) {
			if (getDataObject() instanceof FlexoObject) {
				List<FlexoObjectReference<FlexoConceptInstance>> refs = ((FlexoObject) getDataObject()).getFlexoConceptReferences();
				if (refs != null && ((FlexoConceptInstanceBindingVariable) variable).getIndex() < refs.size()) {
					return refs.get(((FlexoConceptInstanceBindingVariable) variable).getIndex()).getObject();
				}
			}
		}*/
		return super.getValue(variable);
	}

	@Override
	protected void openFIBEditor(FIBComponent component, final MouseEvent event) {
		if (component instanceof FIBInspector) {
			JPopupMenu popup = new JPopupMenu();
			FIBInspector current = (FIBInspector) component;
			while (current != null) {
				Resource inspectorResource = current.getResource();
				if (!inspectorResource.isReadOnly()) {
					JMenuItem menuItem = new JMenuItem(inspectorResource.getRelativePath());
					// We dont use existing inspector which is already
					// aggregated !!!
					final FIBInspector inspectorToOpen = (FIBInspector) FIBLibrary.instance().retrieveFIBComponent(inspectorResource,
							false, ModuleInspectorController.INSPECTOR_FACTORY);
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ViewPointEditingFIBInspectorController.super.openFIBEditor(inspectorToOpen, event);
						}
					});
					popup.add(menuItem);
					current = current.getSuperInspector();
				}
				popup.show(event.getComponent(), event.getX(), event.getY());
			}
		}
	}

	@Override
	public void update(FlexoObservable o, DataModification dataModification) {
		super.update(o, dataModification);
		if (dataModification instanceof ResourceLoaded) {
			// System.out.println("Detected resource being loaded !");
		}
	}

	public void addCustomProperty(FlexoObject object) {
		if (object instanceof InnerResourceData) {
			System.out.println("Creating property for object " + object);
			AddFlexoProperty action = AddFlexoProperty.actionType.makeNewAction(object, null, getEditor());
			action.doAction();
		}
	}

	public void removeCustomProperty(FlexoProperty property) {
		System.out.println("Deleting property " + property + " for object " + property.getOwner());
		property.delete();
	}

}
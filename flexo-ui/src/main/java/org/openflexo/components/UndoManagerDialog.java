/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
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

package org.openflexo.components;

import java.awt.Window;
import java.util.logging.Logger;

import org.openflexo.fib.FIBLibrary;
import org.openflexo.fib.controller.FIBDialog;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.model.undo.UndoManager;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;

/**
 * Dialog allowing to show UndoManager informations
 * 
 * @author sylvain
 * 
 */
@SuppressWarnings("serial")
public class UndoManagerDialog extends FIBDialog<UndoManager> {

	static final Logger logger = Logger.getLogger(UndoManagerDialog.class.getPackage().getName());

	public static final Resource UNDO_MANAGER_FIB = ResourceLocator.locateResource("Fib/UndoManager.fib");

	private static UndoManagerDialog dialog;

	public static UndoManagerDialog getUndoManagerDialog(FlexoServiceManager serviceManager, Window parent) {
		if (dialog == null) {
			dialog = new UndoManagerDialog(serviceManager, parent);
		}

		return dialog;
	}

	public static void showUndoManagerDialog(FlexoServiceManager serviceManager, Window parent) {
		System.out.println("showUndoManagerDialog with " + serviceManager);

		if (dialog == null) {
			dialog = getUndoManagerDialog(serviceManager, parent);
		}
		dialog.showDialog();
	}

	public UndoManagerDialog(FlexoServiceManager serviceManager, Window parent) {

		super(FIBLibrary.instance().retrieveFIBComponent(UNDO_MANAGER_FIB, true), serviceManager.getEditingContext().getUndoManager(),
				parent, false, FlexoLocalization.getMainLocalizer());

		setTitle("Undo Manager");

	}

}

/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
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

package org.openflexo.action;

import java.util.EventObject;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;

import org.openflexo.components.ProjectChooserComponent;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProjectObject;
import org.openflexo.foundation.action.FlexoActionFinalizer;
import org.openflexo.foundation.action.FlexoActionInitializer;
import org.openflexo.foundation.action.FlexoExceptionHandler;
import org.openflexo.foundation.action.ImportProject;
import org.openflexo.foundation.resource.ProjectImportLoopException;
import org.openflexo.foundation.task.FlexoTask.TaskStatus;
import org.openflexo.foundation.utils.ProjectInitializerException;
import org.openflexo.foundation.utils.ProjectLoadingCancelledException;
import org.openflexo.icon.IconLibrary;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.project.LoadProjectTask;
import org.openflexo.view.FlexoFrame;
import org.openflexo.view.controller.ActionInitializer;
import org.openflexo.view.controller.ControllerActionInitializer;
import org.openflexo.view.controller.FlexoController;

public class ImportProjectInitializer extends ActionInitializer<ImportProject, FlexoProjectObject, FlexoProjectObject> {

	private static final Logger logger = Logger.getLogger(ControllerActionInitializer.class.getPackage().getName());

	public ImportProjectInitializer(ControllerActionInitializer actionInitializer) {
		super(ImportProject.actionType, actionInitializer);
	}

	@Override
	protected FlexoExceptionHandler<ImportProject> getDefaultExceptionHandler() {
		return new FlexoExceptionHandler<ImportProject>() {

			@Override
			public boolean handleException(FlexoException exception, ImportProject action) {
				if (action.getThrownException() instanceof ProjectImportLoopException) {
					FlexoController.notify(FlexoLocalization.localizedForKey("project_already_imported") + " "
							+ action.getProjectToImport().getDisplayName());
				}
				return true;
			}
		};
	}

	@Override
	protected FlexoActionFinalizer<ImportProject> getDefaultFinalizer() {
		return new FlexoActionFinalizer<ImportProject>() {
			@Override
			public boolean run(EventObject event, ImportProject action) {
				if (action.hasActionExecutionSucceeded()) {
					FlexoController.notify(FlexoLocalization.localizedForKey("successfully_imported_project") + " "
							+ action.getProjectToImport().getDisplayName());
				}
				return true;
			}
		};
	}

	@Override
	protected FlexoActionInitializer<ImportProject> getDefaultInitializer() {
		return new FlexoActionInitializer<ImportProject>() {
			@Override
			public boolean run(EventObject e, ImportProject action) {
				if (action.getProjectToImport() != null) {
					return true;
				}
				ProjectChooserComponent chooser = new ProjectChooserComponent(FlexoFrame.getActiveFrame(), getController()
						.getApplicationContext()) {
				};
				while (true) {
					if (chooser.showOpenDialog() == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null) {
						FlexoEditor editor = null;

						LoadProjectTask loadProject = getController().getApplicationContext().getProjectLoader()
								.loadProject(chooser.getSelectedFile(), true);
						getController().getApplicationContext().getTaskManager().waitTask(loadProject);
						if (loadProject.getTaskStatus() == TaskStatus.EXCEPTION_THROWN) {
							if (loadProject.getThrownException() instanceof ProjectLoadingCancelledException) {
								loadProject.getThrownException().printStackTrace();
								// User chose not to load this project
								return false;
							} else if (loadProject.getThrownException() instanceof ProjectInitializerException) {
								loadProject.getThrownException().printStackTrace();
								// Failed to load the project
								FlexoController.notify(FlexoLocalization.localizedForKey("could_not_open_project_located_at")
										+ chooser.getSelectedFile().getAbsolutePath());
							}
						}

						editor = loadProject.getFlexoEditor();

						if (editor == null) {
							return false;
						}

						String reason = action.getImportingProject().canImportProject(editor.getProject());
						if (reason == null) {
							action.setProjectToImport(editor.getProject());
							return true;
						} else {
							FlexoController.notify(reason);
						}
					} else {
						// User chose "Cancel"
						return false;
					}
				}
			}
		};
	}

	@Override
	protected Icon getEnabledIcon() {
		return IconLibrary.IMPORT_ICON;
	}

}

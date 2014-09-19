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
package org.openflexo.view.menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.openflexo.FlexoCst;
import org.openflexo.components.NewProjectComponent;
import org.openflexo.components.OpenProjectComponent;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.nature.ProjectNature;
import org.openflexo.foundation.resource.SaveResourceExceptionList;
import org.openflexo.foundation.utils.OperationCancelledException;
import org.openflexo.foundation.utils.ProjectInitializerException;
import org.openflexo.foundation.utils.ProjectLoadingCancelledException;
import org.openflexo.icon.IconLibrary;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.NatureSpecificModule;
import org.openflexo.module.ProjectLoader;
import org.openflexo.print.PrintManagingController;
import org.openflexo.toolbox.ToolBox;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.ControllerModel;

/**
 * 'File' menu
 * 
 * @author sguerin
 */
public class FileMenu extends FlexoMenu {

	static final Logger logger = Logger.getLogger(FileMenu.class.getPackage().getName());

	public JMenu recentProjectMenu;

	public JMenu exportMenu;

	public JMenu importMenu;

	protected FlexoController _controller;

	protected FileMenu(FlexoController controller) {
		super("file", controller);
		_controller = controller;

		add(new SaveItem());
		addSeparator();

		add(new NewProjectItem());
		add(new OpenProjectItem());
		add(recentProjectMenu = new JMenu());
		recentProjectMenu.setText(FlexoLocalization.localizedForKey("recent_projects", recentProjectMenu));
		add(new ImportProjectMenuItem());
		add(new SaveProjectItem());
		add(new SaveAllProjectItem());
		add(new SaveAsProjectItem());
		// TODO: repair reload project. this includes to also support close project.
		// add(reloadProjectItem = new ReloadProjectItem());
		addSeparator();

		if (addImportItems()) {
			add(importMenu);
		}
		if (addExportItems()) {
			add(exportMenu);
		}
		if (importMenu != null || exportMenu != null) {
			addSeparator();
		}

		addSpecificItems();

		add(new InspectProjectItem());
		if (controller instanceof PrintManagingController) {
			addSeparator();
			addPrintItems();
			add(new PageSetUpItem());
		}
		addSeparator();

		add(new QuitItem());

		updateRecentProjectMenu();
	}

	public void updateRecentProjectMenu() {
		if (recentProjectMenu != null) {
			recentProjectMenu.removeAll();
			if (getController().getApplicationContext().getGeneralPreferences() != null) {
				for (File f : getController().getApplicationContext().getGeneralPreferences().getLastOpenedProjects()) {
					recentProjectMenu.add(new ProjectItem(f));
				}
			}
		}
	}

	public void addToExportItems(FlexoMenuItem exportItem) {
		if (exportMenu == null) {
			exportMenu = new JMenu();
			exportMenu.setText(FlexoLocalization.localizedForKey("export", exportMenu));
		}
		exportMenu.add(exportItem);
	}

	public void addToImportItems(FlexoMenuItem importItem) {
		if (importMenu == null) {
			importMenu = new JMenu();
			importMenu.setText(FlexoLocalization.localizedForKey("import", importMenu));
		}
		importMenu.add(importItem);
	}

	protected boolean addExportItems() {
		return false;
	}

	protected boolean addImportItems() {
		return false;
	}

	public void addSpecificItems() {
		// No specific item here, please override this method when required
	}

	public void addPrintItems() {
		// No specific item here, please override this method when required
	}

	public class SaveItem extends FlexoMenuItem {

		public SaveItem() {
			super(new SaveAction(), "save", KeyStroke.getKeyStroke(KeyEvent.VK_S, FlexoCst.META_MASK), getController(), true);
			setIcon(IconLibrary.SAVE_ICON);
		}

	}

	public class SaveAction extends AbstractAction implements PropertyChangeListener {
		public SaveAction() {
			super();
			if (getController() != null) {
				manager.addListener(ControllerModel.CURRENT_EDITOR, this, getController().getControllerModel());
			}
			updateEnability();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			// System.out.println("SaveProjectAction");

			if (getController() == null) {
				return;
			}
			getController().reviewModifiedResources();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (getController() != null) {
				if (evt.getSource() == getController().getControllerModel()) {
					if (ControllerModel.CURRENT_EDITOR.equals(evt.getPropertyName())) {
						updateEnability();
					}
				}
			}
		}

		private void updateEnability() {
			setEnabled(getController() != null);
		}
	}

	public void quit() {
		try {
			getModuleLoader().quit(true);
		} catch (OperationCancelledException e) {
			// User pressed cancel.
			if (logger.isLoggable(Level.FINEST)) {
				logger.log(Level.FINEST, "Cancelled saving", e);
			}
		}
	}

	public class NewProjectItem extends FlexoMenuItem {

		public NewProjectItem() {
			super(new NewProjectAction(), "new_project", KeyStroke.getKeyStroke(KeyEvent.VK_N, FlexoCst.META_MASK), getController(), true);
			setIcon(IconLibrary.NEW_ICON);
		}

	}

	public class NewProjectAction extends AbstractAction {
		public NewProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File projectDirectory = NewProjectComponent.getProjectDirectory(getController().getApplicationContext());
			if (projectDirectory != null) {
				try {
					if (getController().getModule().getModule() instanceof NatureSpecificModule) {
						ProjectNature<?, ?> nature = getController().getApplicationContext().getProjectNatureService()
								.getProjectNature(((NatureSpecificModule) getController().getModule().getModule()).getNatureClass());
						getProjectLoader().newProject(projectDirectory, nature);
					} else {
						getProjectLoader().newProject(projectDirectory);
					}
				} catch (ProjectInitializerException e) {
					e.printStackTrace();
					FlexoController.notify(e.getMessage());
				}
			}
		}
	}

	public class OpenProjectItem extends FlexoMenuItem {

		public OpenProjectItem() {
			super(new OpenProjectAction(), "open_project", KeyStroke.getKeyStroke(KeyEvent.VK_O, FlexoCst.META_MASK), getController(), true);
			setIcon(IconLibrary.OPEN_ICON);
		}
	}

	public class OpenProjectAction extends AbstractAction {
		public OpenProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			File projectDirectory = OpenProjectComponent.getProjectDirectory(getController().getApplicationContext());
			if (projectDirectory != null) {
				try {
					getProjectLoader().loadProject(projectDirectory);
				} catch (ProjectLoadingCancelledException e) {
				} catch (ProjectInitializerException e) {
					e.printStackTrace();
					FlexoController.notify(FlexoLocalization.localizedForKey("could_not_open_project_located_at")
							+ projectDirectory.getAbsolutePath());
				}
			}
		}
	}

	public class ProjectItem extends FlexoMenuItem {

		/**
		 *
		 */
		public ProjectItem(File project) {
			super(new RecentProjectAction(project), project.getName(), null, getController(), false);
		}

	}

	public class RecentProjectAction extends AbstractAction {
		private final File projectDirectory;

		public RecentProjectAction(File projectDirectory) {
			super();
			this.projectDirectory = projectDirectory;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				getProjectLoader().loadProject(projectDirectory);
			} catch (ProjectLoadingCancelledException e) {
			} catch (ProjectInitializerException e) {
				e.printStackTrace();
				FlexoController.notify(FlexoLocalization.localizedForKey("could_not_open_project_located_at")
						+ projectDirectory.getAbsolutePath());
			}
		}
	}

	public class ImportProjectMenuItem extends FlexoMenuItem {

		public ImportProjectMenuItem() {
			super(new ImportProjectAction(), "import_project", null, getController(), true);
			setIcon(IconLibrary.IMPORT_ICON);
		}

	}

	public class ImportProjectAction extends AbstractAction {
		public ImportProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			FlexoEditor editor = getController().getEditor();
			if (editor != null) {
				logger.warning("Please implement this");
				// TODO
				// editor.performActionType(ImportProject.actionType, editor.getProject(), null, e);
			}
		}

	}

	public class SaveProjectItem extends FlexoMenuItem {

		public SaveProjectItem() {
			super(new SaveProjectAction(), "save_current_project", null, getController(), true);
			setIcon(IconLibrary.SAVE_ICON);
		}

	}

	public class SaveProjectAction extends AbstractAction implements PropertyChangeListener {
		public SaveProjectAction() {
			super();
			if (getController() != null) {
				manager.addListener(ControllerModel.CURRENT_EDITOR, this, getController().getControllerModel());
			}
			updateEnability();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			// System.out.println("SaveProjectAction");

			if (getController() == null || getController().getProject() == null) {
				return;
			}
			if (getController().getProject().hasUnsavedResources()) {
				Cursor c = getController().getFlexoFrame().getCursor();
				FileMenu.this._controller.getFlexoFrame().setCursor(Cursor.WAIT_CURSOR);
				try {
					getProjectLoader().saveProjects(Arrays.asList(getController().getProject()));
				} catch (SaveResourceExceptionList e) {
					e.printStackTrace();
					FlexoController.showError(FlexoLocalization.localizedForKey("errors_during_saving"),
							FlexoLocalization.localizedForKey("errors_during_saving"));
				} finally {
					getController().getFlexoFrame().setCursor(c);
				}
			}
			getController().reviewModifiedResources();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (getController() != null) {
				if (evt.getSource() == getController().getControllerModel()) {
					if (ControllerModel.CURRENT_EDITOR.equals(evt.getPropertyName())) {
						updateEnability();
					}
				}
			}
		}

		private void updateEnability() {
			setEnabled(getController() != null && getController().getProject() != null);
		}
	}

	public class SaveAsProjectItem extends FlexoMenuItem {

		public SaveAsProjectItem() {
			super(new SaveAsProjectAction(), "save_current_project_as", null, getController(), true);
			setIcon(IconLibrary.SAVE_AS_ICON);
		}

	}

	public class SaveAsProjectAction extends AbstractAction {
		public SaveAsProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			getProjectLoader().saveAsProject(getController().getProject());
		}

	}

	public class SaveAllProjectItem extends FlexoMenuItem {

		public SaveAllProjectItem() {
			super(new SaveAllProjectAction(), "save_all_project", KeyStroke.getKeyStroke(KeyEvent.VK_S, FlexoCst.META_MASK
					| KeyEvent.SHIFT_MASK), getController(), true);
			setIcon(IconLibrary.SAVE_ALL_ICON);
		}

	}

	public class SaveAllProjectAction extends AbstractAction implements PropertyChangeListener {
		public SaveAllProjectAction() {
			super();
			if (getProjectLoader() != null) {
				manager.addListener(ProjectLoader.ROOT_PROJECTS, this, getProjectLoader());
			}
			updateEnability();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (getProjectLoader().someProjectsAreModified()) {
				Cursor c = getController().getFlexoFrame().getCursor();
				FileMenu.this._controller.getFlexoFrame().setCursor(Cursor.WAIT_CURSOR);
				try {
					getProjectLoader().saveAllProjects();
				} catch (SaveResourceExceptionList e) {
					e.printStackTrace();
					FlexoController.showError(FlexoLocalization.localizedForKey("errors_during_saving"),
							FlexoLocalization.localizedForKey("errors_during_saving"));
				} finally {
					getController().getFlexoFrame().setCursor(c);
				}
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (getProjectLoader() != null) {
				if (evt.getSource() == getProjectLoader()) {
					if (ProjectLoader.ROOT_PROJECTS.equals(evt.getPropertyName())) {
						updateEnability();
					}
				}
			}
		}

		private void updateEnability() {
			setEnabled(getProjectLoader().getRootProjects().size() > 0);
		}
	}

	protected ProjectLoader getProjectLoader() {
		return getController().getProjectLoader();
	}

	// ==========================================================================
	// ============================= ReloadProject =============================
	// ==========================================================================

	public class ReloadProjectItem extends FlexoMenuItem {

		public ReloadProjectItem() {
			super(new ReloadProjectAction(), "reload_project", null, getController(), true);
		}

	}

	public class ReloadProjectAction extends AbstractAction {
		public ReloadProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				getProjectLoader().reloadProject(getController().getProject());
			} catch (ProjectLoadingCancelledException e) {

			} catch (ProjectInitializerException e) {
				e.printStackTrace();
				FlexoController.notify(FlexoLocalization.localizedForKey("could_not_open_project_located_at")
						+ e.getProjectDirectory().getAbsolutePath());
			}
		}

	}

	// ==========================================================================
	// ============================= InspectProject
	// =============================
	// ==========================================================================

	public class InspectProjectItem extends FlexoMenuItem {

		public InspectProjectItem() {
			super(new InspectProjectAction(), "inspect_project", null, getController(), true);
			setIcon(IconLibrary.INSPECT_ICON);
		}

	}

	public class InspectProjectAction extends AbstractAction {
		public InspectProjectAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// FlexoModule.getActiveModule().getFlexoController().showInspector();
			// FlexoModule.getActiveModule().getFlexoController().setCurrentInspectedObject(FlexoModule.getActiveModule().getFlexoController().getProject());
			FlexoController controller = getController();
			controller.getSelectionManager().setSelectedObject(controller.getProject());
			controller.showInspector();
			/*
			 * int state = controller.getInspectorWindow().getExtendedState(); state &= ~Frame.ICONIFIED;
			 * controller.getInspectorWindow().setExtendedState(state);
			 */
		}
	}

	// ==========================================================================
	// ============================= Quit Flexo
	// =================================
	// ==========================================================================

	public class QuitItem extends FlexoMenuItem {

		public QuitItem() {
			super(new QuitAction(), "quit", ToolBox.getPLATFORM() == ToolBox.WINDOWS ? KeyStroke.getKeyStroke(KeyEvent.VK_F4,
					InputEvent.ALT_MASK) : KeyStroke.getKeyStroke(KeyEvent.VK_Q, FlexoCst.META_MASK), getController(), true);
		}

	}

	public class QuitAction extends AbstractAction {
		public QuitAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			quit();
		}

	}

	// ==========================================================================
	// ============================= PageSetUP ================================
	// ==========================================================================

	public class PageSetUpItem extends FlexoMenuItem {

		public PageSetUpItem() {
			super(new PageSetUpAction(), "page_setup", null, getController(), true);
		}

	}

	public class PageSetUpAction extends AbstractAction {
		public PageSetUpAction() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			((PrintManagingController) _controller).getPrintManager().pageSetup();
		}
	}

}

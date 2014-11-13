package org.openflexo.project;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.openflexo.ApplicationContext;
import org.openflexo.components.ProjectChooserComponent;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.FlexoProject.FlexoProjectReferenceLoader;
import org.openflexo.foundation.FlexoService;
import org.openflexo.foundation.FlexoServiceImpl;
import org.openflexo.foundation.resource.FileFlexoIODelegate;
import org.openflexo.foundation.resource.FlexoProjectReference;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.task.FlexoTask.TaskStatus;
import org.openflexo.foundation.utils.ProjectInitializerException;
import org.openflexo.foundation.utils.ProjectLoadingCancelledException;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.module.ModuleLoader;
import org.openflexo.view.FlexoFrame;
import org.openflexo.view.controller.FlexoController;

public class InteractiveFlexoProjectReferenceLoader extends FlexoServiceImpl implements FlexoProjectReferenceLoader {

	private static final Logger logger = Logger.getLogger(ModuleLoader.class.getPackage().getName());

	public InteractiveFlexoProjectReferenceLoader() {
	}

	@Override
	public ApplicationContext getServiceManager() {
		return (ApplicationContext) super.getServiceManager();
	}

	@Override
	public FlexoProject loadProject(FlexoProjectReference ref, boolean silentlyOnly) {
		boolean retrievedFromResourceCenter = false;

		FlexoResource<FlexoProject> retrievedResource = getServiceManager().getInformationSpace().getResource(ref.getURI(),
				ref.getVersion(), FlexoProject.class);

		File selectedFile = null;

		if (retrievedResource.getFlexoIODelegate() instanceof FileFlexoIODelegate) {
			selectedFile = ((FileFlexoIODelegate) (retrievedResource.getFlexoIODelegate())).getFile();
			retrievedFromResourceCenter = true;
		}
		ProjectChooserComponent projectChooser = null;
		while (true) {
			if (selectedFile == null || !selectedFile.exists()) {
				if (silentlyOnly) {
					return null;
				}
				if (projectChooser == null) {
					projectChooser = new ProjectChooserComponent(FlexoFrame.getActiveFrame(), getServiceManager()) {
					};
					projectChooser.setOpenMode();
					projectChooser.setTitle(FlexoLocalization.localizedForKey("locate_project") + " " + ref.getName() + " "
							+ ref.getVersion());
				}
				int ret = projectChooser.showOpenDialog();
				if (ret == JFileChooser.APPROVE_OPTION) {
					selectedFile = projectChooser.getSelectedFile();
					retrievedFromResourceCenter = false;
				} else {
					return null;
				}
			}
			boolean openedProject = getServiceManager().getProjectLoader().hasEditorForProjectDirectory(selectedFile);
			if (!openedProject && silentlyOnly) {
				return null;
			}
			FlexoEditor editor = null;
			LoadProjectTask loadProject = getServiceManager().getProjectLoader().loadProject(selectedFile, true);
			getServiceManager().getTaskManager().waitTask(loadProject);
			if (loadProject.getTaskStatus() == TaskStatus.FINISHED) {
				editor = loadProject.getFlexoEditor();
			} else if (loadProject.getTaskStatus() == TaskStatus.EXCEPTION_THROWN) {
				if (loadProject.getThrownException() instanceof ProjectLoadingCancelledException) {
					return null;
				} else if (loadProject.getThrownException() instanceof ProjectInitializerException) {
					loadProject.getThrownException().printStackTrace();
					if (!retrievedFromResourceCenter) {
						FlexoController.notify(FlexoLocalization.localizedForKey("could_not_load_project_at") + " "
								+ selectedFile.getAbsolutePath());
						selectedFile = null;
					}
				}
			}
			FlexoProject project = editor.getProject();
			if (project.getProjectURI().equals(ref.getURI())) {
				// Project URI do match
				boolean versionEqual = project.getVersion() == null && ref.getVersion() == null || project.getVersion() != null
						&& project.getVersion().equals(ref.getVersion());

				if (versionEqual) {
					return project;
				} else {
					boolean ok = FlexoController.confirm(FlexoLocalization.localizedForKey("project_version_do_not_match") + ". "
							+ project.getVersion() + " " + FlexoLocalization.localizedForKey("was_found")
							+ FlexoLocalization.localizedForKey("but") + " " + ref.getVersion() + " "
							+ FlexoLocalization.localizedForKey("was_expected") + "\n"
							+ FlexoLocalization.localizedForKey("would_you_like_to_switch_to_version:") + " " + project.getVersion());
					if (ok) {
						return project;
					} else if (!openedProject) {
						getServiceManager().getProjectLoader().closeProject(project);
					}
				}
			} else {
				if (retrievedFromResourceCenter) {
					selectedFile = null;
					continue;
				}
				FlexoController.notify(FlexoLocalization.localizedForKey("project_uri_do_not_match") + ".\n"
						+ FlexoLocalization.localizedForKey("uri") + " " + project.getProjectURI() + " "
						+ FlexoLocalization.localizedForKey("was_found") + "\n" + FlexoLocalization.localizedForKey("but") + " " + ref
						+ " " + FlexoLocalization.localizedForKey("was_expected"));
			}
			selectedFile = null;
		}
	}

	@Override
	public void receiveNotification(FlexoService caller, ServiceNotification notification) {
		logger.fine("FlexoProjectReferenceLoader service received notification " + notification + " from " + caller);
	}

	@Override
	public void initialize() {
	}

}
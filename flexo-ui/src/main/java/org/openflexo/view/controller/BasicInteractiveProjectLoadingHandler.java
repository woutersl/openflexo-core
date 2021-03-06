/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
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

package org.openflexo.view.controller;

import java.io.File;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.utils.FlexoProgress;
import org.openflexo.foundation.utils.ProjectLoadingCancelledException;
import org.openflexo.icon.IconLibrary;
import org.openflexo.localization.FlexoLocalization;

public class BasicInteractiveProjectLoadingHandler extends InteractiveProjectLoadingHandler {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BasicInteractiveProjectLoadingHandler.class.getPackage().getName());

	private boolean alreadyAnswer = false;
	private boolean convertProject = false;
	private final File _projectDirectory;

	public BasicInteractiveProjectLoadingHandler(File projectDirectory) {
		super();
		_projectDirectory = projectDirectory;
	}

	private boolean askForProjectConversion() throws ProjectLoadingCancelledException {
		String CONVERT = FlexoLocalization.localizedForKey("convert_project");
		String DONT_CONVERT = FlexoLocalization.localizedForKey("don't_convert_project");
		String CANCEL = FlexoLocalization.localizedForKey("cancel");
		int choice = FlexoController.selectOption(
				"<html><center>"
						+ IconLibrary.UNFIXABLE_WARNING_ICON.getHTMLImg()
						+ "<b>&nbsp;"
						+ FlexoLocalization.localizedForKey("warning")
						+ "</b></center><br>"
						+ "<center>"
						+ _projectDirectory.getName()
						+ "</center><br>"
						+ FlexoLocalization.localizedForKey("this_project_seems_to_have_been_created_with_an_older_version_of_flexo")
						+ "<br>"
						+ FlexoLocalization
								.localizedForKey("would_you_like_to_convert_entire_project_to_new_version_of_flexo_(recommanded)")
						+ "<br></html>", CONVERT, CONVERT, DONT_CONVERT, CANCEL);

		if (choice == 0) { // CONVERT
			alreadyAnswer = true;
			return true;
		} else if (choice == 1) { // DONT_CONVERT
			alreadyAnswer = true;
			return false;
		} else {
			throw new ProjectLoadingCancelledException();
		}
	}

	@Override
	public boolean upgradeResourceToLatestVersion(FlexoResource<?> resource) throws ProjectLoadingCancelledException {
		if (isPerformingAutomaticConversion()) {
			return true;
		}

		if (!alreadyAnswer) {
			convertProject = askForProjectConversion();
		}
		return convertProject;

	}

	@Override
	public boolean useOlderMappingWhenLoadingFailure(FlexoResource<?> resource) throws ProjectLoadingCancelledException {
		return true;
	}

	@Override
	public boolean loadAndConvertAllOldResourcesToLatestVersion(FlexoProject project, FlexoProgress progress)
			throws ProjectLoadingCancelledException {
		Vector<ResourceToConvert> resourcesToConvert = searchResourcesToConvert(project);

		if (resourcesToConvert.size() > 0) {

			if (!alreadyAnswer) {
				convertProject = askForProjectConversion();
			}

			if (convertProject) {
				performConversion(project, resourcesToConvert, progress);
				return true;
			}
		}

		return false;
	}

}

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

package org.openflexo.drm.action;

import java.io.File;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.drm.DocItemFolder;
import org.openflexo.drm.DocResourceCenter;
import org.openflexo.drm.DocResourceManager;
import org.openflexo.drm.DocSubmissionReport;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;

public class ImportDocSubmissionReport extends FlexoAction<ImportDocSubmissionReport, FlexoObject, FlexoObject> {

	private static final Logger logger = Logger.getLogger(ImportDocSubmissionReport.class.getPackage().getName());

	public static FlexoActionType actionType = new FlexoActionType("import_doc_submission_report", FlexoActionType.defaultGroup,
			FlexoActionType.NORMAL_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public FlexoAction makeNewAction(FlexoObject focusedObject, Vector globalSelection, FlexoEditor editor) {
			return new ImportDocSubmissionReport(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FlexoObject object, Vector globalSelection) {
			return isEnabledForSelection(object, globalSelection);
		}

		@Override
		public boolean isEnabledForSelection(FlexoObject object, Vector globalSelection) {
			return object != null && object instanceof DocItemFolder && ((DocItemFolder) object).isRootFolder();
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(actionType, DocItemFolder.class);
	}

	ImportDocSubmissionReport(FlexoObject focusedObject, Vector globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	private File _docSubmissionReportFile;
	private Vector _actionsToImport; // null if all actions must be imported

	@Override
	protected void doAction(Object context) {
		logger.info("ImportDocSubmissionReport");
		if (getDocSubmissionReport() != null) {
			DocResourceManager drm = getEditor().getServiceManager().getService(DocResourceManager.class);
			drm.importDocSubmissionReport(getDocSubmissionReport(), getActionsToImport());
		}
	}

	public File getDocSubmissionReportFile() {
		return _docSubmissionReportFile;
	}

	public void setDocSubmissionReportFile(File docSubmissionReportFile) {
		_docSubmissionReportFile = docSubmissionReportFile;
	}

	private DocSubmissionReport _docSubmissionReport;

	public DocSubmissionReport getDocSubmissionReport() {
		if (_docSubmissionReport == null) {
			if (getDocSubmissionReportFile() != null) {
				DocResourceManager drm = getEditor().getServiceManager().getService(DocResourceManager.class);
				DocResourceCenter drc = drm.getDocResourceCenter();
				// TODO
				// _docSubmissionReport = DocSubmissionReport.load(drc, getDocSubmissionReportFile());
			}
		}
		return _docSubmissionReport;
	}

	public Vector getActionsToImport() {
		return _actionsToImport;
	}

	public void setActionsToImport(Vector actionsToImport) {
		_actionsToImport = actionsToImport;
	}

}

/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
 * 
 * This file is part of Flexodocresourcemanager, a component of the software infrastructure 
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

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.drm.DocItemFolder;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;

public class CreateDocItemFolder extends FlexoAction {

	private static final Logger logger = Logger.getLogger(CreateDocItemFolder.class.getPackage().getName());

	public static FlexoActionType actionType = new FlexoActionType("create_new_folder", FlexoActionType.newMenu,
			FlexoActionType.defaultGroup, FlexoActionType.ADD_ACTION_TYPE) {

		/**
		 * Factory method
		 */
		@Override
		public FlexoAction makeNewAction(FlexoObject focusedObject, Vector globalSelection, FlexoEditor editor) {
			return new CreateDocItemFolder(focusedObject, globalSelection, editor);
		}

		@Override
		public boolean isVisibleForSelection(FlexoObject object, Vector globalSelection) {
			return true;
		}

		@Override
		public boolean isEnabledForSelection(FlexoObject object, Vector globalSelection) {
			return object != null && object instanceof DocItemFolder;
		}

	};

	static {
		FlexoObjectImpl.addActionForClass(actionType, DocItemFolder.class);
	}

	private DocItemFolder _parentDocItemFolder;
	private String _newItemFolderIdentifier;
	private String _newItemFolderDescription;
	private DocItemFolder _newDocItemFolder;

	CreateDocItemFolder(FlexoObject focusedObject, Vector globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	protected void doAction(Object context) {
		logger.info("CreateDocItemFolder");
		if (getParentDocItemFolder() != null) {
			_newDocItemFolder = DocItemFolder.createDocItemFolder(getNewItemFolderIdentifier(), getNewItemFolderDescription(),
					getParentDocItemFolder(), getParentDocItemFolder().getDocResourceCenter());

		}
	}

	public String getNewItemFolderIdentifier() {
		return _newItemFolderIdentifier;
	}

	public void setNewItemFolderIdentifier(String anIdentifier) {
		_newItemFolderIdentifier = anIdentifier;
	}

	public String getNewItemFolderDescription() {
		return _newItemFolderDescription;
	}

	public void setNewItemFolderDescription(String newItemDescription) {
		_newItemFolderDescription = newItemDescription;
	}

	public DocItemFolder getParentDocItemFolder() {
		if (_parentDocItemFolder == null) {
			if (getFocusedObject() != null && getFocusedObject() instanceof DocItemFolder) {
				_parentDocItemFolder = (DocItemFolder) getFocusedObject();
			}
		}
		return _parentDocItemFolder;
	}

	public DocItemFolder getNewDocItemFolder() {
		return _newDocItemFolder;
	}

}

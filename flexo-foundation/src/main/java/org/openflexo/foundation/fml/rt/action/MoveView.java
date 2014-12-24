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
package org.openflexo.foundation.fml.rt.action;

import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.action.FlexoAction;
import org.openflexo.foundation.action.FlexoActionType;
import org.openflexo.foundation.fml.rt.View;
import org.openflexo.foundation.fml.rt.rm.ViewResource;
import org.openflexo.foundation.resource.RepositoryFolder;

public class MoveView extends FlexoAction<MoveView, View, View> {

	private static final Logger logger = Logger.getLogger(MoveView.class.getPackage().getName());

	public static final FlexoActionType<MoveView, View, View> actionType = new FlexoActionType<MoveView, View, View>("move_view") {

		@Override
		public boolean isEnabledForSelection(View object, Vector<View> globalSelection) {
			return true;
		}

		@Override
		public boolean isVisibleForSelection(View object, Vector<View> globalSelection) {
			return false;
		}

		@Override
		public MoveView makeNewAction(View focusedObject, Vector<View> globalSelection, FlexoEditor editor) {
			return new MoveView(focusedObject, globalSelection, editor);
		}

	};

	private RepositoryFolder<ViewResource> folder;

	static {
		FlexoObjectImpl.addActionForClass(actionType, View.class);
	}

	protected MoveView(View focusedObject, Vector<View> globalSelection, FlexoEditor editor) {
		super(actionType, focusedObject, globalSelection, editor);
	}

	@Override
	protected void doAction(Object context) throws FlexoException {
		if (getFolder() == null) {
			logger.warning("Cannot move: null folder");
			return;
		}
		for (View v : getGlobalSelection()) {
			moveToFolder(v, folder);
		}
	}

	private void moveToFolder(View v, RepositoryFolder<ViewResource> folder) {
		RepositoryFolder<ViewResource> oldFolder = v.getFolder();
		v.getViewLibrary().moveResource((ViewResource) v.getResource(), oldFolder, folder);
	}

	public RepositoryFolder<ViewResource> getFolder() {
		return folder;
	};

	public void setFolder(RepositoryFolder<ViewResource> folder) {
		this.folder = folder;
	}
}
/*
 * (c) Copyright 2013-2014 Openflexo
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
package org.openflexo.vpm.controller;

import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.action.PasteAction.PasteHandler;
import org.openflexo.foundation.viewpoint.FlexoBehaviour;
import org.openflexo.foundation.viewpoint.FlexoBehaviourObject;
import org.openflexo.model.factory.Clipboard;
import org.openflexo.toolbox.StringUtils;

/**
 * Paste Handler suitable for pasting something into a FlexoBehaviourParameters
 * 
 * @author sylvain
 * 
 */
public class FlexoBehaviourParameterPasteHandler implements PasteHandler<FlexoBehaviourObject> {

	private static final Logger logger = Logger.getLogger(FlexoBehaviourParameterPasteHandler.class.getPackage().getName());

	public static final String COPY_SUFFIX = "-copy";

	@Override
	public FlexoBehaviourObject retrievePastingContext(FlexoObject focusedObject, List<FlexoObject> globalSelection, Clipboard clipboard) {

		// Wrong focused type
		if (!(focusedObject instanceof FlexoBehaviourObject)) {
			return null;
		}
		// Paste on a flexo behaviour
		else if (focusedObject instanceof FlexoBehaviour) {
			return (FlexoBehaviour)focusedObject;
		}
		// Paste on flexo behaviour parameters
		return ((FlexoBehaviourObject) focusedObject).getFlexoBehaviour();
	}

	@Override
	public void prepareClipboardForPasting(Clipboard clipboard, FlexoBehaviourObject pastingContext) {

		// Translating names
		if (clipboard.isSingleObject()) {
			if (clipboard.getSingleContents() instanceof FlexoBehaviourObject) {
				translateName((FlexoBehaviourObject) clipboard.getSingleContents());
			}
		} else {
			for (Object o : clipboard.getMultipleContents()) {
				if (o instanceof FlexoBehaviour) {
					translateName((FlexoBehaviourObject) o);
				}
			}
		}
	}

	private String translateName(FlexoBehaviourObject object) {
		String oldName = object.getName();
		if (StringUtils.isEmpty(oldName)) {
			return null;
		}
		String newName;
		if (oldName.endsWith(COPY_SUFFIX)) {
			newName = oldName + "2";
		} else if (oldName.contains(COPY_SUFFIX)) {
			try {
				int currentIndex = Integer.parseInt(oldName.substring(oldName.lastIndexOf(COPY_SUFFIX) + COPY_SUFFIX.length()));
				newName = oldName.substring(0, oldName.lastIndexOf(COPY_SUFFIX)) + COPY_SUFFIX + (currentIndex + 1);
			} catch (NumberFormatException e) {
				logger.warning("Could not parse as int " + oldName.substring(oldName.lastIndexOf(COPY_SUFFIX)));
				newName = oldName + COPY_SUFFIX;
			}
		} else {
			newName = oldName + COPY_SUFFIX;
		}
		System.out.println("translating name from " + oldName + " to " + newName);
		object.setName(newName);
		return newName;
	}

}

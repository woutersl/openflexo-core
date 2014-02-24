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
package org.openflexo.dataimporter;

import java.io.File;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProject;

public interface DataImporter {

	/**
	 * This is the generic importing method. Return an object representing all new imported data (might be what you want depending on kind
	 * of importer....)
	 * 
	 * @param project
	 * @param importedFile
	 * @return
	 */
	public Object importInProject(FlexoProject project, File importedFile, Object[] parameters) throws FlexoException;
}

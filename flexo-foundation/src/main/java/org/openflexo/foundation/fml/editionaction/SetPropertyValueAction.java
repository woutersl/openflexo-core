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
package org.openflexo.foundation.fml.editionaction;

import java.lang.reflect.Type;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.foundation.ontology.IFlexoOntologyStructuralProperty;
import org.openflexo.foundation.technologyadapter.ModelSlot;

/**
 * Interface implemented by all {@link EditionAction} setting a value to an object (interface used to share GUI)
 * 
 * @author sylvain
 * 
 */
public interface SetPropertyValueAction {

	public Type getSubjectType();

	public DataBinding<?> getSubject();

	public void setSubject(DataBinding<?> subject);

	public IFlexoOntologyStructuralProperty getProperty();

	public void setProperty(IFlexoOntologyStructuralProperty aProperty);

	public ModelSlot getModelSlot();

	public DataBinding<Object> getAssignation();

	public DataBinding<Boolean> getConditional();

}
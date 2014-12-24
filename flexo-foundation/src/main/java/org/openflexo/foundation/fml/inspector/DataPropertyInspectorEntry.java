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
package org.openflexo.foundation.fml.inspector;

import org.openflexo.foundation.ontology.BuiltInDataType;
import org.openflexo.foundation.ontology.IFlexoOntologyDataProperty;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

/**
 * Represents an inspector entry for an ontology object property
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(DataPropertyInspectorEntry.DataPropertyInspectorEntryImpl.class)
@XMLElement(xmlTag = "DataProperty")
public interface DataPropertyInspectorEntry extends PropertyInspectorEntry {

	@PropertyIdentifier(type = BuiltInDataType.class)
	public static final String DATA_TYPE_KEY = "dataType";

	@Getter(value = DATA_TYPE_KEY)
	@XMLAttribute
	public BuiltInDataType getDataType();

	@Setter(DATA_TYPE_KEY)
	public void setDataType(BuiltInDataType dataType);

	public static abstract class DataPropertyInspectorEntryImpl extends PropertyInspectorEntryImpl implements DataPropertyInspectorEntry {

		private BuiltInDataType dataType;

		public DataPropertyInspectorEntryImpl() {
			super();
		}

		@Override
		public Class getDefaultDataClass() {
			return IFlexoOntologyDataProperty.class;
		}

		@Override
		public String getWidgetName() {
			return "DataPropertySelector";
		}

		@Override
		public BuiltInDataType getDataType() {
			return dataType;
		}

		@Override
		public void setDataType(BuiltInDataType dataType) {
			this.dataType = dataType;
		}

	}
}
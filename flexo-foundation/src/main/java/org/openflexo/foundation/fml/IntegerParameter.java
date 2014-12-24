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
package org.openflexo.foundation.fml;

import java.lang.reflect.Type;

import org.openflexo.antar.binding.DataBinding;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(IntegerParameter.IntegerParameterImpl.class)
@XMLElement
public interface IntegerParameter extends FlexoBehaviourParameter {

	public static abstract class IntegerParameterImpl extends FlexoBehaviourParameterImpl implements IntegerParameter {

		public IntegerParameterImpl() {
			super();
			setDefaultValue(new DataBinding<String>("0"));
		}

		@Override
		public Type getType() {
			return Integer.class;
		};

		@Override
		public WidgetType getWidget() {
			return WidgetType.INTEGER;
		}

	}

}
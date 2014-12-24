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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import org.openflexo.antar.binding.BindingEvaluationContext;
import org.openflexo.antar.binding.DataBinding;
import org.openflexo.antar.binding.DataBinding.BindingDefinitionType;
import org.openflexo.antar.expr.NullReferenceException;
import org.openflexo.antar.expr.TypeMismatchException;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.IFlexoOntologyObjectProperty;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;

@ModelEntity
@ImplementationClass(ObjectPropertyParameter.ObjectPropertyParameterImpl.class)
@XMLElement
public interface ObjectPropertyParameter extends PropertyParameter {

	@PropertyIdentifier(type = String.class)
	public static final String RANGE_URI_KEY = "rangeURI";
	@PropertyIdentifier(type = DataBinding.class)
	public static final String RANGE_VALUE_KEY = "rangeValue";

	@Getter(value = RANGE_URI_KEY)
	@XMLAttribute(xmlTag = "range")
	public String _getRangeURI();

	@Setter(RANGE_URI_KEY)
	public void _setRangeURI(String rangeURI);

	@Getter(value = RANGE_VALUE_KEY)
	@XMLAttribute
	public DataBinding<IFlexoOntologyClass> getRangeValue();

	@Setter(RANGE_VALUE_KEY)
	public void setRangeValue(DataBinding<IFlexoOntologyClass> rangeValue);

	public IFlexoOntologyClass getRange();

	public void setRange(IFlexoOntologyClass c);

	public boolean getIsDynamicRangeValue();

	public void setIsDynamicRangeValue(boolean isDynamic);

	public IFlexoOntologyClass evaluateRangeValue(BindingEvaluationContext parameterRetriever);

	public static abstract class ObjectPropertyParameterImpl extends PropertyParameterImpl implements ObjectPropertyParameter {

		private String rangeURI;
		private DataBinding<IFlexoOntologyClass> rangeValue;
		private boolean isDynamicRangeValueSet = false;

		public ObjectPropertyParameterImpl() {
			super();
		}

		@Override
		public Type getType() {
			return IFlexoOntologyObjectProperty.class;
		};

		@Override
		public WidgetType getWidget() {
			return WidgetType.OBJECT_PROPERTY;
		}

		@Override
		public String _getRangeURI() {
			return rangeURI;
		}

		@Override
		public void _setRangeURI(String rangeURI) {
			this.rangeURI = rangeURI;
		}

		@Override
		public IFlexoOntologyClass getRange() {
			return getVirtualModel().getOntologyClass(_getRangeURI());
		}

		@Override
		public void setRange(IFlexoOntologyClass c) {
			_setRangeURI(c != null ? c.getURI() : null);
		}

		@Override
		public DataBinding<IFlexoOntologyClass> getRangeValue() {
			if (rangeValue == null) {
				rangeValue = new DataBinding<IFlexoOntologyClass>(this, IFlexoOntologyClass.class, BindingDefinitionType.GET);
				rangeValue.setBindingName("rangeValue");
			}
			return rangeValue;
		}

		@Override
		public void setRangeValue(DataBinding<IFlexoOntologyClass> rangeValue) {
			if (rangeValue != null) {
				rangeValue.setOwner(this);
				rangeValue.setBindingName("rangeValue");
				rangeValue.setDeclaredType(IFlexoOntologyClass.class);
				rangeValue.setBindingDefinitionType(BindingDefinitionType.GET);
			}
			this.rangeValue = rangeValue;
		}

		@Override
		public boolean getIsDynamicRangeValue() {
			return getRangeValue().isSet() || isDynamicRangeValueSet;
		}

		@Override
		public void setIsDynamicRangeValue(boolean isDynamic) {
			if (isDynamic) {
				isDynamicRangeValueSet = true;
			} else {
				rangeValue = null;
				isDynamicRangeValueSet = false;
			}
		}

		@Override
		public IFlexoOntologyClass evaluateRangeValue(BindingEvaluationContext parameterRetriever) {
			if (getRangeValue().isValid()) {
				try {
					return getRangeValue().getBindingValue(parameterRetriever);
				} catch (TypeMismatchException e) {
					e.printStackTrace();
				} catch (NullReferenceException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}
}
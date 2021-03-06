/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Flexo-foundation, a component of the software infrastructure 
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

package org.openflexo.foundation.fml.binding;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Type;

import org.openflexo.connie.BindingModel;
import org.openflexo.connie.BindingVariable;
import org.openflexo.foundation.fml.FlexoConceptInstanceType;
import org.openflexo.foundation.fml.inspector.FlexoConceptInspector;

/**
 * This is the {@link BindingModel} exposed by the FlexoConceptInstance formatter This {@link BindingModel} is based on ActionContainer's
 * {@link BindingModel}
 * 
 * @author sylvain
 * 
 */
public class FlexoConceptFormatterBindingModel extends BindingModel {

	private final FlexoConceptInspector conceptInspector;

	public FlexoConceptFormatterBindingModel(FlexoConceptInspector conceptInspector) {
		super(conceptInspector.getFlexoConcept() != null ? conceptInspector.getFlexoConcept().getBindingModel() : null);
		this.conceptInspector = conceptInspector;
		if (conceptInspector != null && conceptInspector.getPropertyChangeSupport() != null) {
			conceptInspector.getPropertyChangeSupport().addPropertyChangeListener(this);
		}
		addToBindingVariables(new BindingVariable(FlexoConceptInspector.FORMATTER_INSTANCE_PROPERTY,
				FlexoConceptInstanceType.getFlexoConceptInstanceType(conceptInspector.getFlexoConcept())) {
			@Override
			public Type getType() {
				return FlexoConceptInstanceType.getFlexoConceptInstanceType(getConceptInspector().getFlexoConcept());
			}
		});
	}

	/**
	 * Delete this {@link BindingModel}
	 */
	@Override
	public void delete() {
		if (conceptInspector != null && conceptInspector.getPropertyChangeSupport() != null) {
			conceptInspector.getPropertyChangeSupport().removePropertyChangeListener(this);
		}
		super.delete();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getSource() == conceptInspector) {
			if (evt.getPropertyName().equals(FlexoConceptInspector.FLEXO_CONCEPT_KEY)) {
				setBaseBindingModel(conceptInspector.getFlexoConcept() != null ? conceptInspector.getFlexoConcept().getBindingModel()
						: null);
			}
		}
	}

	public FlexoConceptInspector getConceptInspector() {
		return conceptInspector;
	}
}

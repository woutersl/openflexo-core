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

import org.openflexo.connie.BindingModel;
import org.openflexo.foundation.fml.editionaction.AbstractAssertion;

/**
 * This is the {@link BindingModel} exposed by a {@link AbstractAssertion}<br>
 * 
 * @author sylvain
 * 
 */
public class AbstractAssertionBindingModel extends BindingModel {

	private final AbstractAssertion abstractAssertion;

	public AbstractAssertionBindingModel(AbstractAssertion abstractAssertion) {
		super(abstractAssertion.getAction() != null ? abstractAssertion.getAction().getBindingModel() : null);
		this.abstractAssertion = abstractAssertion;
		if (abstractAssertion != null && abstractAssertion.getPropertyChangeSupport() != null) {
			abstractAssertion.getPropertyChangeSupport().addPropertyChangeListener(this);
		}
	}

	/**
	 * Delete this {@link BindingModel}
	 */
	@Override
	public void delete() {
		if (abstractAssertion != null && abstractAssertion.getPropertyChangeSupport() != null) {
			abstractAssertion.getPropertyChangeSupport().removePropertyChangeListener(this);
		}
		super.delete();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getSource() == abstractAssertion) {
			if (evt.getPropertyName().equals(AbstractAssertion.ACTION_KEY)) {
				setBaseBindingModel(abstractAssertion.getAction() != null ? abstractAssertion.getAction().getBindingModel() : null);
			}
		}
	}

	public AbstractAssertion getAbstractAssertion() {
		return abstractAssertion;
	}
}

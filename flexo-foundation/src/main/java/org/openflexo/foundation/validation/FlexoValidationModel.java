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

package org.openflexo.foundation.validation;

import org.openflexo.connie.BindingEvaluator;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.localization.LocalizedDelegate;
import org.openflexo.model.ModelContext;
import org.openflexo.model.validation.Validable;
import org.openflexo.model.validation.ValidationModel;

/**
 * This is the ValidationModel managed in Openflexo context
 * 
 * @author sylvain
 * 
 */
@SuppressWarnings("serial")
public class FlexoValidationModel extends ValidationModel {

	private final LocalizedDelegate validationLocalization;

	public FlexoValidationModel(ModelContext modelContext, LocalizedDelegate validationLocalization) {
		super(modelContext);
		this.validationLocalization = validationLocalization;
	}

	/**
	 * Return a boolean indicating if validation of supplied object must be notified
	 * 
	 * @param next
	 * @return a boolean
	 */
	@Override
	protected boolean shouldNotifyValidation(Validable next) {
		return true;
	}

	/**
	 * Overrides fixAutomaticallyIfOneFixProposal
	 * 
	 * @see org.openflexo.model.validation.ValidationModel#fixAutomaticallyIfOneFixProposal()
	 */
	@Override
	public boolean fixAutomaticallyIfOneFixProposal() {
		return false;
	}

	@Override
	public String localizedInContext(String key, Object context) {
		String localized = validationLocalization.getLocalizedForKeyAndLanguage(key, FlexoLocalization.getCurrentLanguage(), true);
		if (localized.contains("($")) {
			String asBindingExpression = asBindingExpression(localized);
			try {
				return (String) BindingEvaluator.evaluateBinding(asBindingExpression, context);
			} catch (Exception e) {
				e.printStackTrace();
				return localized;
			}
		}
		return localized;
	}

}

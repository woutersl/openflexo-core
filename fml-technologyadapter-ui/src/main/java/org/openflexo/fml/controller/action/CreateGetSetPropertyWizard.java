/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Fml-technologyadapter-ui, a component of the software infrastructure 
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

package org.openflexo.fml.controller.action;

import java.util.logging.Logger;

import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.fml.action.CreateGetSetProperty;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.view.controller.FlexoController;

public class CreateGetSetPropertyWizard extends AbstractCreateFlexoPropertyWizard<CreateGetSetProperty> {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CreateGetSetPropertyWizard.class.getPackage().getName());

	public CreateGetSetPropertyWizard(CreateGetSetProperty action, FlexoController controller) {
		super(action, controller);
	}

	@Override
	protected DescribeGetSetProperty makeDescriptionStep() {
		return new DescribeGetSetProperty();
	}

	@Override
	public String getWizardTitle() {
		return FlexoLocalization.localizedForKey("create_get_set_property");
	}

	@Override
	public DescribeGetSetProperty getDescribeProperty() {
		return (DescribeGetSetProperty) super.getDescribeProperty();
	}

	/**
	 * This step is used to set new property parameters
	 * 
	 * @author sylvain
	 * 
	 */
	@FIBPanel("Fib/Wizard/CreateFMLElement/DescribeGetSetProperty.fib")
	public class DescribeGetSetProperty extends DescribeProperty {

		@Override
		public CreateGetSetProperty getAction() {
			return super.getAction();
		}

		@Override
		public String getTitle() {
			return FlexoLocalization.localizedForKey("describe_get_set_property");
		}

		@Override
		public boolean isValid() {

			if (!super.isValid()) {
				return false;
			}

			return true;
		}

	}

}

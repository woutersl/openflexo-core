/**
 * 
 * Copyright (c) 2014-2015, Openflexo
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

package org.openflexo.fml.fib;

import org.junit.Test;
import org.openflexo.fib.utils.GenericFIBTestCase;
import org.openflexo.rm.FileResourceImpl;
import org.openflexo.rm.ResourceLocator;

public class TestFMLWizardFibs extends GenericFIBTestCase {

	public static void main(String[] args) {
		System.out.println(generateFIBTestCaseClass(((FileResourceImpl) ResourceLocator.locateResource("Fib/Wizard")).getFile(),
				"Fib/Wizard/"));
	}

	@Test
	public void testChooseEditionActionClass() {
		validateFIB("Fib/Wizard/CreateFMLElement/ChooseEditionActionClass.fib");
	}

	@Test
	public void testConfigureAdditionalStepsForNewFlexoConcept() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureAdditionalStepsForNewFlexoConcept.fib");
	}

	@Test
	public void testConfigureFlexoBehaviourParameters() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureFlexoBehaviourParameters.fib");
	}

	@Test
	public void testConfigureFreeModelSlot() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureFreeModelSlot.fib");
	}

	@Test
	public void testConfigureModelSlots() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureModelSlots.fib");
	}

	@Test
	public void testConfigureTypeAwareModelSlot() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureTypeAwareModelSlot.fib");
	}

	@Test
	public void testConfigureVirtualModelModelSlot() {
		validateFIB("Fib/Wizard/CreateFMLElement/ConfigureVirtualModelModelSlot.fib");
	}

	@Test
	public void testDescribeAbstractProperty() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeAbstractProperty.fib");
	}

	@Test
	public void testDescribeExpressionProperty() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeExpressionProperty.fib");
	}

	@Test
	public void testDescribeFlexoBehaviour() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeFlexoBehaviour.fib");
	}

	@Test
	public void testDescribeFlexoConcept() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeFlexoConcept.fib");
	}

	@Test
	public void testDescribeFlexoRole() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeFlexoRole.fib");
	}

	@Test
	public void testDescribeGetSetProperty() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeGetSetProperty.fib");
	}

	@Test
	public void testDescribeModelSlot() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeModelSlot.fib");
	}

	@Test
	public void testDescribeViewPoint() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeViewPoint.fib");
	}

	@Test
	public void testDescribeVirtualModel() {
		validateFIB("Fib/Wizard/CreateFMLElement/DescribeVirtualModel.fib");
	}

	@Test
	public void testDescribeNewParentConcepts() {
		validateFIB("Fib/Wizard/FMLAction/DescribeNewParentConcepts.fib");
	}

}

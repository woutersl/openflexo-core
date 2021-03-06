/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
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

package org.openflexo.fib;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.OpenflexoProjectAtRunTimeTestCaseWithGUI;
import org.openflexo.components.ReviewUnsavedDialog;
import org.openflexo.fib.testutils.FIBDialogGraphicalContextDelegate;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * Test the ReviewUnsavedDialog widget
 * 
 * @author sylvain
 * 
 */
@RunWith(OrderedRunner.class)
public class TestReviewUnsavedDialog extends OpenflexoProjectAtRunTimeTestCaseWithGUI {

	private static FIBDialogGraphicalContextDelegate gcDelegate;

	private static FlexoEditor editor;
	private static FlexoProject project;

	@Test
	@TestOrder(1)
	public void testCreateProject() {
		editor = createProject("TestProject");
		project = editor.getProject();
		System.out.println("Created project " + project.getProjectDirectory());
		assertTrue(project.getProjectDirectory().exists());
		assertTrue(project.getProjectDataResource().getFlexoIODelegate().exists());
	}

	@Test
	@TestOrder(2)
	public void testInstanciateWidget() {
		ReviewUnsavedDialog dialog = new ReviewUnsavedDialog(serviceManager.getResourceManager());
		log("instanciated " + dialog);
		gcDelegate = new FIBDialogGraphicalContextDelegate(dialog, ReviewUnsavedDialog.FIB_FILE_NAME);
	}

	/*
	 * @Test public void test1CreateComponent() {
	 * 
	 * component = new FIBPanel(); component.setLayout(Layout.twocols);
	 * component.setDataClass(Family.class);
	 * 
	 * firstNameLabel = new FIBLabel("first_name");
	 * component.addToSubComponents(firstNameLabel, new
	 * TwoColsLayoutConstraints(TwoColsLayoutLocation.left, false, false));
	 * firstNameTF = new FIBTextField(); firstNameTF.setData(new
	 * DataBinding<String>("data.father.firstName", firstNameTF, String.class,
	 * BindingDefinitionType.GET_SET));
	 * component.addToSubComponents(firstNameTF, new
	 * TwoColsLayoutConstraints(TwoColsLayoutLocation.right, false, false));
	 * assertTrue(firstNameTF.getData().isValid());
	 * 
	 * lastNameLabel = new FIBLabel("last_name");
	 * component.addToSubComponents(lastNameLabel, new
	 * TwoColsLayoutConstraints(TwoColsLayoutLocation.left, false, false));
	 * lastNameTF = new FIBTextField(); lastNameTF.setData(new
	 * DataBinding<String>("data.father.lastName", lastNameTF, String.class,
	 * BindingDefinitionType.GET_SET)); component.addToSubComponents(lastNameTF,
	 * new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, false, false));
	 * assertTrue(lastNameTF.getData().isValid());
	 * 
	 * fullNameLabel = new FIBLabel("full_name");
	 * component.addToSubComponents(fullNameLabel, new
	 * TwoColsLayoutConstraints(TwoColsLayoutLocation.left, false, false));
	 * fullNameTF = new FIBTextField(); fullNameTF.setData(new
	 * DataBinding<String>("data.father.firstName + ' ' + data.father.lastName",
	 * fullNameTF, String.class, BindingDefinitionType.GET));
	 * component.addToSubComponents(fullNameTF, new
	 * TwoColsLayoutConstraints(TwoColsLayoutLocation.right, false, false));
	 * assertTrue(fullNameTF.getData().isValid());
	 * 
	 * }
	 * 
	 * @Test public void test2InstanciateComponent() { controller =
	 * FIBController.instanciateController(component,
	 * FlexoLocalization.getMainLocalizer()); assertNotNull(controller); family1
	 * = new Family(); controller.setDataObject(family1);
	 * controller.buildView();
	 * 
	 * gcDelegate.addTab("Test2", controller);
	 * 
	 * assertNotNull(controller.getRootView());
	 * assertNotNull(controller.viewForComponent(firstNameLabel));
	 * assertNotNull(controller.viewForComponent(firstNameTF));
	 * assertNotNull(controller.viewForComponent(lastNameLabel));
	 * assertNotNull(controller.viewForComponent(lastNameTF));
	 * assertNotNull(controller.viewForComponent(fullNameLabel));
	 * assertNotNull(controller.viewForComponent(fullNameTF));
	 * 
	 * // controller.viewForComponent(firstNameTF).update();
	 * 
	 * assertEquals("Robert",
	 * controller.viewForComponent(firstNameTF).getData());
	 * assertEquals("Smith", controller.viewForComponent(lastNameTF).getData());
	 * assertEquals("Robert Smith",
	 * controller.viewForComponent(fullNameTF).getData()); }
	 * 
	 * @Test public void test3ModifyValueInModel() {
	 * family1.getFather().setFirstName("Roger");
	 * family1.getFather().setLastName("Rabbit"); assertEquals("Roger",
	 * controller.viewForComponent(firstNameTF).getData());
	 * assertEquals("Rabbit",
	 * controller.viewForComponent(lastNameTF).getData());
	 * assertEquals("Roger Rabbit",
	 * controller.viewForComponent(fullNameTF).getData()); }
	 * 
	 * @Test public void test4ModifyValueInWidget() { FIBTextFieldWidget w1 =
	 * (FIBTextFieldWidget) controller.viewForComponent(firstNameTF);
	 * FIBTextFieldWidget w2 = (FIBTextFieldWidget)
	 * controller.viewForComponent(lastNameTF); FIBTextFieldWidget w3 =
	 * (FIBTextFieldWidget) controller.viewForComponent(fullNameTF);
	 * 
	 * w1.getDynamicJComponent().setText("James");
	 * w2.getDynamicJComponent().setText("Dean");
	 * 
	 * assertEquals("James", family1.getFather().getFirstName());
	 * assertEquals("Dean", family1.getFather().getLastName());
	 * assertEquals("James Dean",
	 * controller.viewForComponent(fullNameTF).getData());
	 * assertEquals("James Dean", w3.getDynamicJComponent().getText());
	 * 
	 * }
	 * 
	 * @Test public void test5InstanciateComponent() {
	 * component.setDataClass(Family.class); FIBController controller =
	 * FIBController.instanciateController(component,
	 * FlexoLocalization.getMainLocalizer()); assertNotNull(controller);
	 * System.out.println("controller=" + controller); controller.buildView();
	 * family2 = new Family(); controller.setDataObject(family2);
	 * 
	 * gcDelegate.addTab("Test3", controller);
	 * 
	 * assertNotNull(controller.getRootView());
	 * assertNotNull(controller.viewForComponent(firstNameLabel));
	 * assertNotNull(controller.viewForComponent(firstNameTF));
	 * assertNotNull(controller.viewForComponent(lastNameLabel));
	 * assertNotNull(controller.viewForComponent(lastNameTF));
	 * assertNotNull(controller.viewForComponent(fullNameLabel));
	 * assertNotNull(controller.viewForComponent(fullNameTF));
	 * 
	 * assertEquals("Robert",
	 * controller.viewForComponent(firstNameTF).getData());
	 * assertEquals("Smith", controller.viewForComponent(lastNameTF).getData());
	 * assertEquals("Robert Smith",
	 * controller.viewForComponent(fullNameTF).getData()); }
	 */

	@Before
	public void setUp() {
		if (gcDelegate != null) {
			gcDelegate.setUp();
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (gcDelegate != null) {
			gcDelegate.tearDown();
		}
		super.tearDown();
	}

}

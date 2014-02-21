package org.openflexo.fib.widget;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.fib.testutils.GraphicalContextDelegate;
import org.openflexo.fib.utils.OpenflexoFIBTestCase;
import org.openflexo.fib.view.widget.DefaultFIBCustomComponent;
import org.openflexo.foundation.DefaultFlexoEditor;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.viewpoint.FlexoConcept;
import org.openflexo.foundation.viewpoint.PrimitivePatternRole;
import org.openflexo.foundation.viewpoint.PrimitivePatternRole.PrimitiveType;
import org.openflexo.foundation.viewpoint.ViewPoint;
import org.openflexo.foundation.viewpoint.ViewPointLibrary;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.action.CreatePatternRole;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;
import org.openflexo.toolbox.FileResource;

/**
 * Test FlexoConceptPanel fib
 * 
 * @author sylvain
 * 
 */
@RunWith(OrderedRunner.class)
public class TestFlexoConceptPanel extends OpenflexoFIBTestCase {

	private static GraphicalContextDelegate gcDelegate;

	private static FileResource fibFile;

	static FlexoEditor editor;

	static FlexoConcept flexoConceptA;
	static FlexoConcept flexoConceptB;
	static FlexoConcept flexoConceptC;
	static FlexoConcept flexoConceptD;
	static FlexoConcept flexoConceptE;

	@BeforeClass
	public static void setupClass() {
		instanciateTestServiceManager();
		initGUI();
	}

	@Test
	@TestOrder(1)
	public void testLoadWidget() {

		fibFile = new FileResource("Fib/VPM/FlexoConceptPanel.fib");
		assertTrue(fibFile.exists());
	}

	@Test
	@TestOrder(2)
	public void testValidateWidget() {

		validateFIB(fibFile);
	}

	@Test
	@TestOrder(3)
	public void loadConcepts() {

		ViewPointLibrary vpLib = serviceManager.getViewPointLibrary();
		assertNotNull(vpLib);
		ViewPoint viewPoint = vpLib.getViewPoint("http://openflexo.org/test/TestViewPoint1");
		assertNotNull(viewPoint);
		VirtualModel virtualModel = viewPoint.getVirtualModelNamed("TestVirtualModel");
		assertNotNull(virtualModel);

		flexoConceptA = virtualModel.getFlexoConcept("FlexoConceptA");
		System.out.println("flexoConceptA=" + flexoConceptA);
		assertNotNull(flexoConceptA);

		flexoConceptB = virtualModel.getFlexoConcept("FlexoConceptB");
		System.out.println("flexoConceptB=" + flexoConceptB);
		assertNotNull(flexoConceptB);

		flexoConceptC = virtualModel.getFlexoConcept("FlexoConceptC");
		System.out.println("flexoConceptC=" + flexoConceptC);
		assertNotNull(flexoConceptC);

		flexoConceptD = virtualModel.getFlexoConcept("FlexoConceptD");
		System.out.println("flexoConceptD=" + flexoConceptD);
		assertNotNull(flexoConceptD);

		flexoConceptE = virtualModel.getFlexoConcept("FlexoConceptE");
		System.out.println("flexoConceptE=" + flexoConceptE);
		assertNotNull(flexoConceptE);

		editor = new DefaultFlexoEditor(null, serviceManager);
		assertNotNull(editor);

		CreatePatternRole createPR1 = CreatePatternRole.actionType.makeNewAction(flexoConceptA, null, editor);
		createPR1.setPatternRoleName("aString");
		createPR1.patternRoleClass = PrimitivePatternRole.class;
		createPR1.primitiveType = PrimitiveType.String;
		createPR1.doAction();

		CreatePatternRole createPR2 = CreatePatternRole.actionType.makeNewAction(flexoConceptA, null, editor);
		createPR2.setPatternRoleName("aBoolean");
		createPR2.patternRoleClass = PrimitivePatternRole.class;
		createPR2.primitiveType = PrimitiveType.Boolean;
		createPR2.doAction();

		CreatePatternRole createPR3 = CreatePatternRole.actionType.makeNewAction(flexoConceptA, null, editor);
		createPR3.setPatternRoleName("anInteger");
		createPR3.patternRoleClass = PrimitivePatternRole.class;
		createPR3.primitiveType = PrimitiveType.Integer;
		createPR3.doAction();

	}

	@Test
	@TestOrder(4)
	public void testInstanciateWidgetForConceptA() {

		DefaultFIBCustomComponent<FlexoConcept> widget = instanciateFIB(fibFile, flexoConceptA, FlexoConcept.class);

		gcDelegate.addTab("FlexoConceptA", widget.getController());
	}

	@Test
	@TestOrder(5)
	public void testInstanciateWidgetForConceptB() {

		DefaultFIBCustomComponent<FlexoConcept> widget = instanciateFIB(fibFile, flexoConceptB, FlexoConcept.class);

		gcDelegate.addTab("FlexoConceptB", widget.getController());
	}

	@Test
	@TestOrder(6)
	public void testInstanciateWidgetForConceptC() {

		DefaultFIBCustomComponent<FlexoConcept> widget = instanciateFIB(fibFile, flexoConceptC, FlexoConcept.class);

		gcDelegate.addTab("FlexoConceptC", widget.getController());
	}

	@Test
	@TestOrder(7)
	public void testInstanciateWidgetForConceptD() {

		DefaultFIBCustomComponent<FlexoConcept> widget = instanciateFIB(fibFile, flexoConceptD, FlexoConcept.class);

		gcDelegate.addTab("FlexoConceptD", widget.getController());
	}

	@Test
	@TestOrder(8)
	public void testInstanciateWidgetForConceptE() {

		DefaultFIBCustomComponent<FlexoConcept> widget = instanciateFIB(fibFile, flexoConceptE, FlexoConcept.class);

		gcDelegate.addTab("FlexoConceptE", widget.getController());
	}

	public static void initGUI() {
		gcDelegate = new GraphicalContextDelegate(TestFlexoConceptPanel.class.getSimpleName());
	}

	@AfterClass
	public static void waitGUI() {
		gcDelegate.waitGUI();
	}

	@Before
	public void setUp() {
		gcDelegate.setUp();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		gcDelegate.tearDown();
	}

}
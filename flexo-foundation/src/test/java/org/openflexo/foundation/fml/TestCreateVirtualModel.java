/**
 * 
 * Copyright (c) 2014-2015, Openflexo
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

package org.openflexo.foundation.fml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.fml.ViewPoint.ViewPointImpl;
import org.openflexo.foundation.fml.VirtualModel.VirtualModelImpl;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.fml.rm.VirtualModelResource;
import org.openflexo.foundation.resource.FileSystemBasedResourceCenter;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;
import org.openflexo.toolbox.FileUtils;

/**
 * This unit test is intented to test VirtualModel creation facilities
 * 
 * TODO: write test with FlexoAction primitives only
 * 
 * @author sylvain
 * 
 */
@RunWith(OrderedRunner.class)
public class TestCreateVirtualModel extends OpenflexoTestCase {

	public static final String VIEWPOINT_NAME = "TestViewPoint";
	public static final String VIEWPOINT_URI = "http://openflexo.org/test/TestViewPoint";
	public static final String VIRTUAL_MODEL_NAME = "TestVirtualModel";

	static ViewPoint newViewPoint;
	static ViewPointResource newViewPointResource;

	/**
	 * Test the VP creation
	 */
	@Test
	@TestOrder(1)
	public void testCreateViewPoint() {
		instanciateTestServiceManager();
		System.out.println("ResourceCenter= " + resourceCenter);
		newViewPoint = ViewPointImpl.newViewPoint(VIEWPOINT_NAME, VIEWPOINT_URI, resourceCenter.getDirectory(),
				serviceManager.getViewPointLibrary());
		newViewPointResource = (ViewPointResource) newViewPoint.getResource();
		// assertTrue(newViewPointResource.getDirectory().exists());
		// assertTrue(newViewPointResource.getFile().exists());
		assertTrue(newViewPointResource.getDirectory() != null);
		assertTrue(newViewPointResource.getFlexoIODelegate().exists());

		assertEquals(newViewPoint, newViewPoint.getViewPoint());
		assertEquals(newViewPoint, newViewPoint.getVirtualModel());
		assertEquals(null, newViewPoint.getOwningVirtualModel());
		assertEquals(newViewPoint, newViewPoint.getFlexoConcept());
		assertEquals(newViewPoint, newViewPoint.getResourceData());

	}

	/**
	 * Test the VirtualModel creation
	 */
	@Test
	@TestOrder(2)
	public void testCreateVirtualModel() throws SaveResourceException {
		AbstractVirtualModel<?> newVirtualModel = VirtualModelImpl.newVirtualModel(VIRTUAL_MODEL_NAME, newViewPoint);
		assertTrue(ResourceLocator.retrieveResourceAsFile(((VirtualModelResource) newVirtualModel.getResource()).getDirectory()).exists());
		assertTrue(((VirtualModelResource) newVirtualModel.getResource()).getFlexoIODelegate().exists());

		assertEquals(newViewPoint, newVirtualModel.getViewPoint());
		assertEquals(newVirtualModel, newVirtualModel.getVirtualModel());
		// assertEquals(null, newVirtualModel.getOwningVirtualModel());

		if (newVirtualModel instanceof ViewPoint) {
			assertEquals(null, newVirtualModel.getOwningVirtualModel());
		}
		else {
			assertNotNull(newVirtualModel.getOwningVirtualModel());
		}

		assertEquals(newVirtualModel, newVirtualModel.getFlexoConcept());
		assertEquals(newVirtualModel, newVirtualModel.getResourceData());

	}

	/**
	 * Reload the ViewPoint<br>
	 * We first re-init a full ServiceManager, and copy the just created ViewPoint<br>
	 * The goal is to let the FileSystem monitoring system detects the new directory and instantiate ViewPoint
	 */
	@Test
	@TestOrder(3)
	public void testReloadViewPoint() {

		log("testReloadViewPoint()");

		instanciateTestServiceManager();
		File directory = ResourceLocator.retrieveResourceAsFile(newViewPointResource.getDirectory());
		File newDirectory = new File(((FileSystemBasedResourceCenter) resourceCenter).getDirectory(), directory.getName());
		newDirectory.mkdirs();

		try {
			FileUtils.copyContentDirToDir(directory, newDirectory);
			// We wait here for the thread monitoring ResourceCenters to detect new files
			Thread.sleep(3000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ViewPointResource retrievedVPResource = serviceManager.getViewPointLibrary().getViewPointResource(VIEWPOINT_URI);
		assertNotNull(retrievedVPResource);

		ViewPoint reloadedViewPoint = retrievedVPResource.getViewPoint();
		assertEquals(reloadedViewPoint, reloadedViewPoint.getViewPoint());
		assertEquals(reloadedViewPoint, reloadedViewPoint.getVirtualModel());
		assertEquals(null, reloadedViewPoint.getOwningVirtualModel());
		assertEquals(reloadedViewPoint, reloadedViewPoint.getFlexoConcept());
		assertEquals(reloadedViewPoint, reloadedViewPoint.getResourceData());

		AbstractVirtualModel<?> reloadedVirtualModel = reloadedViewPoint.getVirtualModelNamed(VIRTUAL_MODEL_NAME);
		assertNotNull(reloadedVirtualModel);

		assertEquals(reloadedViewPoint, reloadedVirtualModel.getViewPoint());
		assertEquals(reloadedVirtualModel, reloadedVirtualModel.getVirtualModel());

		if (reloadedVirtualModel instanceof ViewPoint) {
			assertEquals(null, reloadedVirtualModel.getOwningVirtualModel());
		}
		else {
			assertNotNull(reloadedVirtualModel.getOwningVirtualModel());
		}

		assertEquals(reloadedVirtualModel, reloadedVirtualModel.getFlexoConcept());
		assertEquals(reloadedVirtualModel, reloadedVirtualModel.getResourceData());

	}

}

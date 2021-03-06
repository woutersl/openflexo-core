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

package org.openflexo.foundation.fml.rt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.ViewPoint.ViewPointImpl;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.action.CreateVirtualModel;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.fml.rm.VirtualModelResource;
import org.openflexo.foundation.fml.rt.action.CreateBasicVirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.CreateView;
import org.openflexo.foundation.fml.rt.rm.ViewResource;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.test.OrderedRunner;
import org.openflexo.test.TestOrder;

/**
 * This unit test is intented to test View creation facilities with a ViewPoint created on the fly
 * 
 * @author sylvain
 * 
 */
@RunWith(OrderedRunner.class)
public class TestCreateVirtualModelInstance extends OpenflexoProjectAtRunTimeTestCase {

	private static ViewPoint newViewPoint;
	private static VirtualModel newVirtualModel;
	private static FlexoEditor editor;
	private static FlexoProject project;
	private static View newView;
	private static VirtualModelInstance newVirtualModelInstance;

	/**
	 * Instantiate a ViewPoint with a VirtualModel
	 * 
	 * @throws SaveResourceException
	 */
	@Test
	@TestOrder(1)
	public void testCreateViewPoint() throws SaveResourceException {

		log("testCreateViewPoint()");

		instanciateTestServiceManager();
		System.out.println("ResourceCenter= " + resourceCenter);
		newViewPoint = ViewPointImpl.newViewPoint("TestViewPoint", "http://openflexo.org/test/TestViewPoint",
				resourceCenter.getDirectory(), serviceManager.getViewPointLibrary());
		assertNotNull(newViewPoint);
		assertNotNull(newViewPoint.getResource());
		// assertTrue(((ViewPointResource) newViewPoint.getResource()).getDirectory().exists());
		// assertTrue(((ViewPointResource) newViewPoint.getResource()).getFile().exists());
		assertTrue(((ViewPointResource) newViewPoint.getResource()).getDirectory() != null);
		assertTrue(((ViewPointResource) newViewPoint.getResource()).getFlexoIODelegate().exists());

		CreateVirtualModel action = CreateVirtualModel.actionType.makeNewAction(newViewPoint, null, editor);
		action.setNewVirtualModelName("TestVirtualModel");
		action.doAction();
		assertTrue(action.hasActionExecutionSucceeded());
		newVirtualModel = action.getNewVirtualModel();
		// newVirtualModel = VirtualModelImpl.newVirtualModel("TestVirtualModel", newViewPoint);
		assertTrue(ResourceLocator.retrieveResourceAsFile(((VirtualModelResource) newVirtualModel.getResource()).getDirectory()).exists());
		assertTrue(((VirtualModelResource) newVirtualModel.getResource()).getFlexoIODelegate().exists());
	}

	@Test
	@TestOrder(2)
	public void testCreateProject() {

		log("testCreateProject()");

		editor = createProject("TestProject");
		project = editor.getProject();
		System.out.println("Created project " + project.getProjectDirectory());
		assertTrue(project.getProjectDirectory().exists());
		assertTrue(project.getProjectDataResource().getFlexoIODelegate().exists());
	}

	/**
	 * Instantiate in project a View conform to the ViewPoint
	 */
	@Test
	@TestOrder(3)
	public void testCreateView() {

		log("testCreateView()");

		CreateView action = CreateView.actionType.makeNewAction(project.getViewLibrary().getRootFolder(), null, editor);
		action.setNewViewName("MyView");
		action.setNewViewTitle("Test creation of a new view");
		action.setViewpointResource((ViewPointResource) newViewPoint.getResource());
		action.doAction();
		assertTrue(action.hasActionExecutionSucceeded());
		newView = action.getNewView();
		assertNotNull(newView);
		assertNotNull(newView.getResource());
		try {
			newView.getResource().save(null);
		} catch (SaveResourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// assertTrue(((ViewResource) newView.getResource()).getDirectory().exists());
		// assertTrue(((ViewResource) newView.getResource()).getFile().exists());
		assertTrue(((ViewResource) newView.getResource()).getDirectory() != null);
		assertTrue(((ViewResource) newView.getResource()).getFlexoIODelegate().exists());

		assertNotNull(project.getResource(newView.getURI()));
		assertNotNull(project.getViewLibrary().getResource(newView.getURI()));

	}

	/**
	 * Instantiate in project a VirtualModelInstance conform to the VirtualModel
	 */
	@Test
	@TestOrder(4)
	public void testCreateVirtualModelInstance() {

		log("testCreateVirtualModelInstance()");

		CreateBasicVirtualModelInstance action = CreateBasicVirtualModelInstance.actionType.makeNewAction(newView, null, editor);
		action.setNewVirtualModelInstanceName("MyVirtualModelInstance");
		action.setNewVirtualModelInstanceTitle("Test creation of a new VirtualModelInstance");
		action.setVirtualModel(newVirtualModel);
		action.doAction();
		assertTrue(action.hasActionExecutionSucceeded());
		newVirtualModelInstance = action.getNewVirtualModelInstance();
		assertNotNull(newVirtualModelInstance);
		assertNotNull(newVirtualModelInstance.getResource());
		// assertTrue(((ViewResource) newView.getResource()).getDirectory().exists());
		// assertTrue(((ViewResource) newView.getResource()).getFile().exists());
		assertTrue(((ViewResource) newView.getResource()).getDirectory() != null);
		assertTrue(((ViewResource) newView.getResource()).getFlexoIODelegate().exists());

		// Not relevant anymore since reflexive model slot has disappeared from 1.7.0-beta to 1.7.0 version
		// assertEquals(1, newVirtualModelInstance.getModelSlotInstances().size());
		// VirtualModelModelSlotInstance reflexiveMSInstance = (VirtualModelModelSlotInstance)
		// newVirtualModelInstance.getModelSlotInstances()
		// .get(0);
		// assertNotNull(reflexiveMSInstance);
		// assertEquals(newVirtualModelInstance, reflexiveMSInstance.getAccessedResourceData());

		assertEquals(newVirtualModel, newVirtualModelInstance.getFlexoConcept());
		assertEquals(newVirtualModel, newVirtualModelInstance.getVirtualModel());
	}

	/**
	 * Reload the project
	 * 
	 * @throws FlexoException
	 * @throws ResourceLoadingCancelledException
	 * @throws FileNotFoundException
	 */
	@Test
	@TestOrder(5)
	public void testReloadProject() throws FileNotFoundException, ResourceLoadingCancelledException, FlexoException {

		log("testReloadProject()");

		FlexoProject oldProject = project;
		instanciateTestServiceManager();
		editor = reloadProject(project.getDirectory());
		project = editor.getProject();
		assertNotSame(oldProject, project);
		assertNotNull(editor);
		assertNotNull(project);
		ViewResource newViewResource = project.getViewLibrary().getView(newView.getURI());
		assertNotNull(newViewResource);
		assertNull(newViewResource.getLoadedResourceData());
		newViewResource.loadResourceData(null);
		assertNotNull(newViewResource.getLoadedResourceData());
		newView = newViewResource.getLoadedResourceData();

		System.out.println("All resources=" + project.getAllResources());
		assertNotNull(project.getResource(newView.getURI()));

	}

}

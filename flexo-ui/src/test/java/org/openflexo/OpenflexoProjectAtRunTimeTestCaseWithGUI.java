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
package org.openflexo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoProjectAtRunTimeTestCase;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.logging.FlexoLoggingManager;

/**
 * Provides a JUnit 4 generic environment of Openflexo-core with a project at run-time for testing purposes in graphics environment
 * 
 */
public abstract class OpenflexoProjectAtRunTimeTestCaseWithGUI extends OpenflexoProjectAtRunTimeTestCase {

	private static final Logger logger = FlexoLogger.getLogger(OpenflexoProjectAtRunTimeTestCaseWithGUI.class.getPackage().getName());

	protected static DirectoryResourceCenter resourceCenter;
	protected static ApplicationContext serviceManager;

	static {
		try {
			FlexoLoggingManager.initialize(-1, true, null, Level.WARNING, null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void tearDownClass() {

	}

	protected static ApplicationContext instanciateTestServiceManager() {
		return instanciateTestServiceManager(false);
	}

	protected static ApplicationContext instanciateTestServiceManager(final boolean generateCompoundTestResourceCenter) {
		serviceManager = new TestApplicationContext(generateCompoundTestResourceCenter);
		resourceCenter = (DirectoryResourceCenter) serviceManager.getResourceCenterService().getResourceCenters().get(0);
		return serviceManager;
	}

	protected static FlexoServiceManager getFlexoServiceManager() {
		return serviceManager;
	}

	@Override
	protected FlexoEditor createProject(String projectName) {
		if (serviceManager == null) {
			serviceManager = instanciateTestServiceManager();
		}
		return createProject(projectName, serviceManager);
	}

}
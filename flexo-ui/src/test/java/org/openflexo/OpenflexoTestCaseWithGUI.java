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

package org.openflexo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.OpenflexoTestCase;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.logging.FlexoLoggingManager;

/**
 * Provides a JUnit 4 generic environment of Openflexo-core for testing purposes in graphics environment
 * 
 */
public abstract class OpenflexoTestCaseWithGUI extends OpenflexoTestCase {

	private static final Logger logger = FlexoLogger.getLogger(OpenflexoTestCaseWithGUI.class.getPackage().getName());

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
	public synchronized static void tearDownClass() {
		if (serviceManager != null) {
			FlexoResourceCenterService RCService = serviceManager.getResourceCenterService();
			List<FlexoResourceCenter> listRC = RCService.getResourceCenters();
			for (FlexoResourceCenter rc : listRC) {
				if (rc instanceof DirectoryResourceCenter) {
					File RCDirectory = ((DirectoryResourceCenter) rc).getDirectory();
					RCDirectory.deleteOnExit();
				}
			}
		}
	}

	protected static FlexoServiceManager instanciateTestServiceManager() {
		Flexo.isDev = true;
		return instanciateTestServiceManager(false);
	}

	protected static FlexoServiceManager instanciateTestServiceManager(final boolean generateCompoundTestResourceCenter) {
		serviceManager = new TestApplicationContext(generateCompoundTestResourceCenter);
		resourceCenter = (DirectoryResourceCenter) serviceManager.getResourceCenterService().getResourceCenters().get(0);
		return serviceManager;
	}

	protected static FlexoServiceManager getFlexoServiceManager() {
		return serviceManager;
	}

}

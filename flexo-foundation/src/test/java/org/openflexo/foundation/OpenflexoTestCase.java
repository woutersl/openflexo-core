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

package org.openflexo.foundation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.AssertionFailedError;

import org.junit.After;
import org.junit.AfterClass;
import org.openflexo.foundation.fml.FMLObject;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.resource.DefaultResourceCenterService;
import org.openflexo.foundation.resource.DirectoryResourceCenter;
import org.openflexo.foundation.resource.DirectoryResourceCenter.DirectoryResourceCenterEntry;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter.ResourceCenterEntry;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.kvc.KeyValueLibrary;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.logging.FlexoLoggingManager;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.model.validation.ValidationError;
import org.openflexo.model.validation.ValidationReport;
import org.openflexo.rm.ClasspathResourceLocatorImpl;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.toolbox.FileUtils;

/**
 * Provides a JUnit 4 generic environment of Openflexo-core for testing purposes
 * 
 */
public abstract class OpenflexoTestCase {

	private static final Logger logger = FlexoLogger.getLogger(OpenflexoTestCase.class.getPackage().getName());

	protected static DirectoryResourceCenter resourceCenter;
	protected static FlexoServiceManager serviceManager;

	protected static File testResourceCenterDirectory;

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
		if (testResourceCenterDirectory != null) {
			FileUtils.deleteDir(testResourceCenterDirectory);
		}
		resourceCenter = null;
		serviceManager = null;

	}

	public static class FlexoTestEditor extends DefaultFlexoEditor {
		public FlexoTestEditor(FlexoProject project, FlexoServiceManager sm) {
			super(project, sm);
		}

	}

	public File getResource(String resourceRelativeName) {
		File retval = new File("src/test/resources", resourceRelativeName);
		if (retval.exists()) {
			return retval;
		}
		retval = new File("../flexofoundation/src/test/resources", resourceRelativeName);
		if (retval.exists()) {
			return retval;
		}
		retval = new File("tmp/tests/FlexoResources/", resourceRelativeName);
		if (retval.exists()) {
			return retval;
		} else if (logger.isLoggable(Level.WARNING)) {
			logger.warning("Could not find resource " + resourceRelativeName);
		}
		return null;
	}

	protected static FlexoServiceManager instanciateTestServiceManager() {
		return instanciateTestServiceManager(false);
	}

	protected static FlexoServiceManager instanciateTestServiceManager(final boolean generateCompoundTestResourceCenter) {
		serviceManager = new DefaultFlexoServiceManager() {

			@Override
			protected FlexoEditor createApplicationEditor() {
				return new FlexoTestEditor(null, this);
			}

			@Override
			protected FlexoResourceCenterService createResourceCenterService() {
				try {
					File tempFile = File.createTempFile("Temp", "");
					testResourceCenterDirectory = new File(tempFile.getParentFile(), tempFile.getName() + "TestResourceCenter");
					testResourceCenterDirectory.mkdirs();

					System.out.println("Creating TestResourceCenter [compound: " + generateCompoundTestResourceCenter + "] "
							+ testResourceCenterDirectory);
					System.out.println("***************** WARNING WARNING WARNING ************************");

					if (generateCompoundTestResourceCenter) {

						ClasspathResourceLocatorImpl locator = new ClasspathResourceLocatorImpl();

						List<Resource> toto = locator.locateAllResources("TestResourceCenter");
						for (Resource tstRC : toto) {
							System.out.println(tstRC.toString());
							FileUtils.copyResourceToDir(tstRC, testResourceCenterDirectory);
						}
					} else {

						Resource tstRC = ResourceLocator.locateResource("TestResourceCenter");
						System.out.println("Copied from " + tstRC);
						FileUtils.copyResourceToDir(tstRC, testResourceCenterDirectory);
					}

					FlexoResourceCenterService rcService = DefaultResourceCenterService.getNewInstance();
					rcService.addToResourceCenters(resourceCenter = new DirectoryResourceCenter(testResourceCenterDirectory));
					System.out.println("Copied TestResourceCenter to " + testResourceCenterDirectory);

					// ici il y a des truc a voir

					return rcService;
				} catch (IOException e) {
					e.printStackTrace();
					fail();
					return null;
				}

			}

		};
		return serviceManager;
	}

	protected static FlexoResourceCenterService getNewResourceCenter(String name) {
		try {
			ModelFactory factory = new ModelFactory(DirectoryResourceCenterEntry.class);
			DirectoryResourceCenterEntry entry = factory.newInstance(DirectoryResourceCenterEntry.class);
			entry.setDirectory(FileUtils.createTempDirectory(name, "ResourceCenter"));
			List<ResourceCenterEntry<?>> rcList = new ArrayList<ResourceCenterEntry<?>>();
			rcList.add(entry);
			return DefaultResourceCenterService.getNewInstance(rcList);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
			fail();
		}
		return null;
	}

	protected static FlexoServiceManager getFlexoServiceManager() {
		return serviceManager;
	}

	protected void assertNotModified(FlexoResource resource) {
		try {
			if (resource.isLoaded()) {
				assertFalse("Resource " + resource.getURI() + " should not be modfied", resource.getLoadedResourceData().isModified());
			} else {
				fail("Resource " + resource.getURI() + " should not be modified but is not even loaded");
			}
		} catch (AssertionFailedError e) {
			logger.warning("RESOURCE status problem: " + resource + " MUST be NOT modified");
			throw e;
		}
	}

	protected void assertModified(FlexoResource resource) {
		try {
			if (resource.isLoaded()) {
				assertTrue("Resource " + resource.getURI() + " should be modified", resource.getLoadedResourceData().isModified());
			} else {
				fail("Resource " + resource.getURI() + " should be modified but is not even loaded");
			}
		} catch (AssertionFailedError e) {
			logger.warning("RESOURCE status problem: " + resource + " MUST be modified");
			throw e;
		}
	}

	protected void assertNotLoaded(FlexoResource resource) {
		try {
			assertFalse("Resource " + resource.getURI() + " should not be loaded", resource.isLoaded());
		} catch (AssertionFailedError e) {
			logger.warning("RESOURCE status problem: " + resource + " MUST be NOT loaded");
			throw e;
		}
	}

	protected void assertLoaded(FlexoResource resource) {
		try {
			assertTrue("Resource " + resource.getURI() + " should be loaded", resource.isLoaded());
		} catch (AssertionFailedError e) {
			logger.warning("RESOURCE status problem: " + resource + " MUST be loaded");
			throw e;
		}
	}

	protected void log(String step) {
		logger.info("\n******************************************************************************\n" + step
				+ "\n******************************************************************************\n");
	}

	/**
	 * Assert this is the same list, doesn't care about order
	 * 
	 * @param aList
	 * @param objects
	 * @throws AssertionFailedError
	 */
	public static <T> void assertSameList(Collection<? extends T> aList, T... objects) throws AssertionFailedError {
		Set<T> set1 = new HashSet<T>(aList);
		Set<T> set2 = new HashSet<T>();
		for (T o : objects) {
			set2.add(o);
		}
		if (!set1.equals(set2)) {
			StringBuffer message = new StringBuffer();
			for (T o : set1) {
				if (!set2.contains(o)) {
					message.append(" Extra: " + o);
				}
			}
			for (T o : set2) {
				if (!set1.contains(o)) {
					message.append(" Missing: " + o);
				}
			}
			throw new AssertionFailedError("AssertionFailedError when comparing lists, expected: " + set1 + " but was " + set2
					+ " Details = " + message);
		}
	}

	@After
	public void tearDown() throws Exception {
		KeyValueLibrary.clearCache();
	}

	protected void assertViewPointIsValid(ViewPoint vp) {
		assertObjectIsValid(vp);
	}

	protected void assertObjectIsValid(FMLObject object) {
		assertEquals(0, validate(object).getErrorsCount());
	}

	protected ValidationReport validate(FMLObject object) {

		try {
			ValidationReport report = object.getViewPointLibrary().getViewPointValidationModel().validate(object);

			for (ValidationError error : report.getErrors()) {
				System.out.println("Found error: " + error + " details=" + error.getDetailedInformations());
				/*if (error.getValidationRule() instanceof BindingIsRequiredAndMustBeValid) {
					BindingIsRequiredAndMustBeValid rule = (BindingIsRequiredAndMustBeValid) error.getValidationRule();
					System.out.println("Details: " + rule.retrieveIssueDetails((FMLObject) error.getValidable()));
					}*/
			}

			return report;

		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Interrupted");
			return null;
		}
	}

}

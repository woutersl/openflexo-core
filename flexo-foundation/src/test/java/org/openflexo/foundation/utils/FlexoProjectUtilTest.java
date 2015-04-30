/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
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

package org.openflexo.foundation.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.openflexo.toolbox.FlexoVersion;

public class FlexoProjectUtilTest extends TestCase {

	public void testIsProjectOpenableWhenThereIsNoVersionFile() {
		try {
			assertTrue("Project with no version file must openable.", FlexoProjectUtil.isProjectOpenable(getDirectoryPrjWitoutVersion()));
		} catch (UnreadableProjectException e) {
			fail("UnreadableProjectException can be thrown when testing the project version.");
		}
	}

	public void testGetVersionWhenThereIsNoVersionFile() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getDirectoryPrjWitoutVersion());
		assertNull("FlexoVersion must be null when there is no version file.", version);
	}

	public void testCurrentFlexoVersionIsSmallerThanLastVersionWhenThereIsNoVersionFile() {
		assertFalse("No version file in a prj must be considered as not smaller than last version. "
				+ "(probably need to be fixed, but this test is conservative.)",
				FlexoProjectUtil.currentFlexoVersionIsSmallerThanLastVersion(getDirectoryPrjWitoutVersion()));
	}

	public void testIsProjectNotOpenable113() {
		try {
			FlexoProjectUtil.isProjectOpenable(getPrjDirectory113());
			fail("UnreadableProjectException must be thrown when testing openability of a prj 1.1.3.");
		} catch (UnreadableProjectException e) {
			// that's expected
		}
	}

	public void testGetVersion113() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getPrjDirectory113());
		assertNotNull("FlexoVersion cannot be null for version 1.1.3", version);
		assertVersion(1, 1, 3, false, false, version);
	}

	public void test999IsSmallerThanLastVersion() {
		assertTrue("Current version must be smaller than 9.9.9 (note that it may change when flexo will reach major 9) ",
				FlexoProjectUtil.currentFlexoVersionIsSmallerThanLastVersion(getPrjDirectory999()));
	}

	public void testIsProjectNotOpenable125() {
		try {
			FlexoProjectUtil.isProjectOpenable(getPrjDirectory125());
			fail("UnreadableProjectException must be thrown when testing openability of a prj 1.2.5.");
		} catch (UnreadableProjectException e) {
			// that's expected
		}
	}

	public void testGetVersion125() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getPrjDirectory125());
		assertNotNull("FlexoVersion cannot be null for version 1.2.5", version);
		assertVersion(1, 2, 5, false, false, version);
	}

	public void testIsProjectOpenable13() {
		try {
			assertTrue("Project 1.3 must be openable.", FlexoProjectUtil.isProjectOpenable(getPrjDirectory13()));
		} catch (UnreadableProjectException e) {
			fail("UnreadableProjectException can be thrown when testing openability.");
		}
	}

	public void testGetVersion13() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getPrjDirectory13());
		assertNotNull("FlexoVersion cannot be null for version 1.3", version);
		assertVersion(1, 3, 0, false, false, version);
	}

	private void assertVersion(int major, int minor, int patch, boolean isAlpha, boolean isBeta, FlexoVersion version) {
		assertEquals("Major version missmatch.", major, version.major);
		assertEquals("Minor version missmatch.", minor, version.minor);
		assertEquals("Patch version missmatch.", patch, version.patch);
		assertEquals("Is alpha version missmatch.", isAlpha, version.isAlpha);
		assertEquals("Is beta version missmatch.", isBeta, version.isBeta);
	}

	public void testIsProjectOpenable14() {
		try {
			assertTrue("Project 1.4 must be openable.", FlexoProjectUtil.isProjectOpenable(getPrjDirectory14()));
		} catch (UnreadableProjectException e) {
			fail("UnreadableProjectException can be thrown when testing openability.");
		}
	}

	public void testGetVersion14() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getPrjDirectory14());
		assertNotNull("FlexoVersion cannot be null for version 1.4", version);
		assertVersion(1, 4, 0, false, false, version);
	}

	public void testGetVersion999() {
		FlexoVersion version = FlexoProjectUtil.getVersion(getPrjDirectory999());
		assertNotNull("FlexoVersion cannot be null for version 9.9.9", version);
		assertVersion(9, 9, 9, false, false, version);
	}

	public void testVersion999IsNotOpenable() {
		try {
			FlexoProjectUtil.isProjectOpenable(getPrjDirectory999());
			fail("Version 9.9.9 cannot be openable with current Flexo Release. (note that it can change whenever Flexo will reach major "
					+ "9");
		} catch (UnreadableProjectException e) {
			// that's expected
		}
	}

	private File getDirectoryPrjWitoutVersion() {
		return createPrjDirectoryWithoutVersionFile("ProjectWithoutVersionFile");
	}

	private File getPrjDirectory113() {
		return createPrjDirectoryWithVersionContent("1.1.3", "Project113");
	}

	private File getPrjDirectory125() {
		return createPrjDirectoryWithVersionContent("1.2.5", "Project125");
	}

	private File getPrjDirectory13() {
		return createPrjDirectoryWithVersionContent("1.3", "Project13");
	}

	private File getPrjDirectory14() {
		return createPrjDirectoryWithVersionContent("1.4", "Project14");
	}

	private File getPrjDirectory144() {
		return createPrjDirectoryWithVersionContent("1.4.4", "Project144");
	}

	private File getPrjDirectory999() {
		return createPrjDirectoryWithVersionContent("9.9.9", "Project999");
	}

	private File getPrjDirectoryInvalidVersionFile() {
		return createPrjDirectoryWithVersionContent("a.b.c", "ProjectInvalidVersionFile");
	}

	private File createPrjDirectoryWithVersionContent(String versionContent, String prjName) {
		File projectDirectory = createPrjDirectoryWithoutVersionFile(prjName);
		File versionFile = new File(projectDirectory, ".version");
		try {
			versionFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(versionFile);
			fos.write(versionContent.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Cannot create .version file: " + e.getMessage());
		}
		return projectDirectory;
	}

	private File createPrjDirectoryWithoutVersionFile(String prjName) {
		File projectDirectory = null;
		try {
			File tempFile = File.createTempFile(prjName, "");
			projectDirectory = new File(tempFile.getParentFile(), tempFile.getName() + ".prj");
			tempFile.delete();
		} catch (IOException e) {
			fail("Cannot create tmp prjDirectory");
		}

		projectDirectory.mkdirs();
		return projectDirectory;
	}
}

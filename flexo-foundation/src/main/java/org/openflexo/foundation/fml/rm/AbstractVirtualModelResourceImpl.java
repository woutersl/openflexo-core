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

package org.openflexo.foundation.fml.rm;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.fml.AbstractVirtualModel;
import org.openflexo.foundation.fml.FMLModelFactory;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.resource.CannotRenameException;
import org.openflexo.foundation.resource.DirectoryBasedFlexoIODelegate;
import org.openflexo.foundation.resource.FileFlexoIODelegate;
import org.openflexo.foundation.resource.InJarFlexoIODelegate;
import org.openflexo.foundation.resource.PamelaResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.model.factory.AccessibleProxyObject;
import org.openflexo.rm.BasicResourceImpl;
import org.openflexo.rm.ClasspathResourceLocatorImpl;
import org.openflexo.rm.FileSystemResourceLocatorImpl;
import org.openflexo.rm.InJarResourceImpl;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;

public abstract class AbstractVirtualModelResourceImpl<VM extends AbstractVirtualModel<VM>> extends PamelaResourceImpl<VM, FMLModelFactory>
		implements AbstractVirtualModelResource<VM>, AccessibleProxyObject {

	static final Logger logger = Logger.getLogger(AbstractVirtualModelResourceImpl.class.getPackage().getName());

	@Override
	public void setName(String aName) throws CannotRenameException {
		String oldName = getName();
		super.setName(aName);
		if (getLoadedResourceData() != null && getLoadedResourceData().getPropertyChangeSupport() != null) {
			getLoadedResourceData().getPropertyChangeSupport().firePropertyChange(FlexoConcept.NAME_KEY, oldName, aName);
		}
	}

	@Override
	public FMLTechnologyAdapter getTechnologyAdapter() {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FMLTechnologyAdapter.class);
		}
		return null;
	}

	/**
	 * Return virtual model stored by this resource<br>
	 * Load the resource data when unloaded
	 */
	@Override
	public VM getVirtualModel() {
		try {
			return getResourceData(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
		} catch (FlexoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void stopDeserializing() {
		// NPE Protection and warning
		VM data = getLoadedResourceData();
		if (data == null) {
			logger.warning("INVESTIGATE: NO DATA has been derserialized from VirtualModelResource - " + this.getURI());
		} else {
			for (FlexoConcept fc : data.getFlexoConcepts()) {
				fc.finalizeDeserialization();
			}
		}
		super.stopDeserializing();
	}

	@Override
	public boolean delete(Object... context) {
		if (super.delete(context)) {
			if (getServiceManager() != null) {
				getServiceManager().getResourceManager().addToFilesToDelete(ResourceLocator.retrieveResourceAsFile(getDirectory()));
			}
			return true;
		}
		return false;
	}

	@Override
	public Resource getDirectory() {
		if (getFlexoIODelegate() instanceof FileFlexoIODelegate) {
			String parentPath = getDirectoryPath();
			if (ResourceLocator.locateResource(parentPath) == null) {
				FileSystemResourceLocatorImpl.appendDirectoryToFileSystemResourceLocator(parentPath);
			}
			return ResourceLocator.locateResource(parentPath);
		} else if (getFlexoIODelegate() instanceof InJarFlexoIODelegate) {
			InJarResourceImpl resource = ((InJarFlexoIODelegate) getFlexoIODelegate()).getInJarResource();
			String parentPath = FilenameUtils.getFullPath(resource.getRelativePath());
			BasicResourceImpl parent = (BasicResourceImpl) ((ClasspathResourceLocatorImpl) (resource.getLocator())).getJarResourcesList()
					.get(parentPath);
			return parent;
		}
		return null;
	}

	public String getDirectoryPath() {
		if (getFlexoIODelegate() instanceof DirectoryBasedFlexoIODelegate) {
			return ((DirectoryBasedFlexoIODelegate) getFlexoIODelegate()).getDirectory().getAbsolutePath();
		} else if (getFlexoIODelegate() instanceof FileFlexoIODelegate) {
			return ((FileFlexoIODelegate) getFlexoIODelegate()).getFile().getParentFile().getAbsolutePath();
		}
		return null;
	}
}
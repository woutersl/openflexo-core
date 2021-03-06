/**
 * 
 * Copyright (c) 2013-2015, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
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

package org.openflexo.foundation.resource;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoObject.FlexoObjectImpl;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.utils.FlexoObjectReference;
import org.openflexo.model.factory.AccessibleProxyObject;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocatorDelegate;
import org.openflexo.toolbox.IProgress;

/**
 * Default implementation for {@link FlexoResource}<br>
 * Note that this default implementation extends {@link FlexoObject}: all resources in Openflexo model are instances of {@link FlexoObject}
 * 
 * Very first draft for implementation, only implements get/load scheme
 * 
 * @param <RD>
 *            the type of the resource data reference by this resource
 * @author Sylvain
 * 
 */
public abstract class FlexoResourceImpl<RD extends ResourceData<RD>> extends FlexoObjectImpl implements FlexoResource<RD> {

	static final Logger logger = Logger.getLogger(FlexoResourceImpl.class.getPackage().getName());

	private FlexoServiceManager serviceManager = null;
	private boolean isLoading = false;
	protected RD resourceData = null;

	/**
	 * Return flag indicating if this resource is loaded
	 * 
	 * @return
	 */
	@Override
	public boolean isLoaded() {
		return resourceData != null;
	}

	/**
	 * Return flag indicating if this resource is loadable<br>
	 * By default, a resource is always loadable, then this method always returns true if IO delegate exists
	 * 
	 * @return
	 */
	@Override
	public boolean isLoadable() {
		// By default, a resource is always loadable, then this method always returns true if IO delegate exists
		return getFlexoIODelegate() != null && getFlexoIODelegate().exists();
	}

	/**
	 * Returns the &quot;real&quot; resource data of this resource. This may cause the loading of the resource data.
	 * 
	 * @param progress
	 *            a progress monitor in case the resource data is not immediately available.
	 * @return the resource data.
	 * @throws ResourceLoadingCancelledException
	 */
	@Override
	public synchronized RD getResourceData(IProgress progress) throws ResourceLoadingCancelledException, ResourceLoadingCancelledException,
			FileNotFoundException, FlexoException {

		if (isLoading()) {
			// logger.warning("trying to load a resource data from itself, please investigate");
			return resourceData;
		}
		if (resourceData == null && isLoadable()) {
			// The resourceData is null, we try to load it
			setLoading(true);
			resourceData = loadResourceData(progress);
			setLoading(false);
			// That's fine, resource is loaded, now let's notify the loading of the resources
			notifyResourceLoaded();
		}
		return resourceData;
	}

	/**
	 * Return flag indicating if this resource is being loaded
	 * 
	 * @return
	 */
	public boolean isLoading() {
		return isLoading;
	}

	/**
	 * Set a flag indicating if this resource is being loaded
	 * 
	 * @return
	 */
	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	/**
	 * Returns the &quot;real&quot; resource data of this resource, asserting resource data is already loaded. If the resource is not
	 * loaded, do not load the data, and return null
	 * 
	 * @return the resource data.
	 */
	@Override
	public RD getLoadedResourceData() {
		return resourceData;
	}

	/**
	 * Programmatically sets {@link ResourceData} for this resource<br>
	 * The resource is then notified that it has been loaded
	 * 
	 * @param resourceData
	 */
	@Override
	public void setResourceData(RD resourceData) {
		this.resourceData = resourceData;
		notifyResourceLoaded();
	}

	/**
	 * Called to notify that a resource has successfully been loaded
	 */
	@Override
	public void notifyResourceLoaded() {
		logger.fine("notifyResourceLoaded(), resource=" + this);

		ResourceLoaded notification = new ResourceLoaded(this, resourceData);
		setChanged();
		notifyObservers(notification);
		// Also notify that the contents of the resource may also have changed
		setChanged();
		notifyObservers(new DataModification("contents", null, getContents()));
		if (getServiceManager() != null) {
			getServiceManager().notify(getServiceManager().getResourceManager(), notification);
		}
		else {
			logger.warning("Resource " + this + " does not refer to any ServiceManager. Please investigate...");
		}
	}

	/**
	 * Called to notify that a resource has successfully been saved
	 */
	@Override
	public void notifyResourceSaved() {
		logger.fine("notifyResourceSaved(), resource=" + this);

		ResourceSaved notification = new ResourceSaved(this, resourceData);
		setChanged();
		notifyObservers(notification);
		getServiceManager().notify(getServiceManager().getResourceManager(), notification);
	}

	/**
	 * Called to notify that a resource has been modified
	 */
	@Override
	public void notifyResourceModified() {
		// logger.info("notifyResourceModified(), resource=" + this);

		ResourceModified notification = new ResourceModified(this, resourceData);
		setChanged();
		notifyObservers(notification);
		getServiceManager().notify(getServiceManager().getResourceManager(), notification);

	}

	/**
	 * Called to notify that a resource has been added to contents<br>
	 * TODO: integrate this in setContents() when this interface will extends {@link AccessibleProxyObject}
	 * 
	 * @param resource
	 *            : resource being added
	 */
	@Override
	public void notifyContentsAdded(FlexoResource<?> resource) {
		logger.fine("notifyContentsAdded(), resource=" + this);

		ContentsAdded notification = new ContentsAdded(this, resource);
		setChanged();
		notifyObservers(notification);
		getServiceManager().notify(getServiceManager().getResourceManager(), notification);
	}

	/**
	 * Called to notify that a resource has been remove to contents<br>
	 * TODO: integrate this in setContents() when this interface will extends {@link AccessibleProxyObject}
	 * 
	 * @param resource
	 *            : resource being removed
	 */
	@Override
	public void notifyContentsRemoved(FlexoResource<?> resource) {
		logger.fine("notifyContentsRemoved(), resource=" + this);

		ContentsRemoved notification = new ContentsRemoved(this, resource);
		setChanged();
		notifyObservers(notification);
		getServiceManager().notify(getServiceManager().getResourceManager(), notification);
	}

	@Override
	public void notifyResourceStatusChanged() {
	}

	@Override
	public final String toString() {
		return getClass().getSimpleName() + "." + getURI() + "." + getVersion() + "." + getRevision();
	}

	/**
	 * Returns a list of resources of supplied type contained by this resource.
	 * 
	 * @return the list of contained resources.
	 */
	@Override
	public <R extends FlexoResource<?>> List<R> getContents(Class<R> resourceClass) {
		ArrayList<R> returned = new ArrayList<R>();
		for (FlexoResource<?> r : getContents()) {
			if (resourceClass.isAssignableFrom(r.getClass())) {
				returned.add((R) r);
			}
		}
		return returned;
	}

	/**
	 * Returns the resource identified in supplied URI, asserting that is resource is of supplied type
	 * 
	 * @return
	 */
	@Override
	public <R extends FlexoResource<?>> R getContentWithURI(Class<R> resourceClass, String resourceURI) {
		for (R r : getContents(resourceClass)) {
			if (r != null && r.getURI().equals(resourceURI)) {
				return r;
			}
		}
		return null;
	}

	@Override
	public FlexoServiceManager getServiceManager() {
		return serviceManager;
	}

	/**
	 * Sets and register the service manager<br>
	 * Also (VERY IMPORTANT) register the resource in the FlexoEditingContext !!!
	 */
	@Override
	public void setServiceManager(FlexoServiceManager serviceManager) {
		this.serviceManager = serviceManager;
		if (getServiceManager() != null) {
			getServiceManager().getResourceManager().registerResource(this);
		}
	}

	/**
	 * Indicates whether this resource can be edited or not. Returns <code>true</code> if the resource cannot be edited, else returns
	 * <code>false</code>.<br>
	 * This is here the default implementation, always returned false;
	 * 
	 * @return <code>true</code> if the resource cannot be edited, else returns <code>false</code>.
	 */
	@Override
	public boolean isReadOnly() {
		return false;
	}

	/**
	 * Delete this resource<br>
	 * Contents of this resource are deleted, and resource data is unloaded
	 */
	@Override
	public boolean delete(Object... context) {
		if (isReadOnly()) {
			logger.warning("Delete requested for READ-ONLY resource " + this);
			return false;
		}
		else {
			logger.info("Deleting resource " + this);
			if (getContainer() != null) {
				FlexoResource<?> container = getContainer();
				container.removeFromContents(this);
				container.notifyContentsRemoved(this);
			}
			for (org.openflexo.foundation.resource.FlexoResource<?> r : new ArrayList<org.openflexo.foundation.resource.FlexoResource<?>>(
					getContents())) {
				r.delete();
			}

			if (isLoaded()) {
				unloadResourceData();
			}

			// Handle Flexo IO delegate deletion
			getFlexoIODelegate().delete();

			performSuperDelete(context);

			return true;
		}
	}

	/**
	 * Delete (dereference) resource data if resource data is loaded<br>
	 * Also delete the resource data
	 */
	@Override
	public void unloadResourceData() {
		if (isLoaded()) {
			resourceData.delete();
			resourceData = null;
		}
	}

	@Override
	public final boolean isModified() {
		return isLoaded() && getLoadedResourceData().isModified();
	}

	@Override
	public void notifyObjectLoaded(FlexoObjectReference<?> reference) {
		logger.warning("TODO: implement this");
	}

	@Override
	public void objectCantBeFound(FlexoObjectReference<?> reference) {
		logger.warning("TODO: implement this");
	}

	@Override
	public void objectSerializationIdChanged(FlexoObjectReference<?> reference) {
		setChanged();
	}

	@Override
	public void objectDeleted(FlexoObjectReference<?> reference) {
		logger.warning("TODO: implement this");
	}

	// TODO: check this
	@Override
	public void setContainer(Resource resource) {
		if (resource instanceof FlexoResource) {
			setContainer((FlexoResource<?>) resource);
		}
	}

	// TODO: check this
	@Override
	public List<? extends Resource> getContents(Pattern pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: check this
	@Override
	public ResourceLocatorDelegate getLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: check this
	@Override
	public InputStream openInputStream() {
		if (getFlexoIODelegate() instanceof FlexoIOStreamDelegate) {
			return ((FlexoIOStreamDelegate) getFlexoIODelegate()).getInputStream();
		}
		return null;
	}

	// TODO: check this
	@Override
	public OutputStream openOutputStream() {
		if (getFlexoIODelegate() instanceof FlexoIOStreamDelegate) {
			return ((FlexoIOStreamDelegate) getFlexoIODelegate()).getOutputStream();
		}
		return null;
	}

	// TODO: check this
	@Override
	public final String getRelativePath() {
		return null;
	}

	// TODO: check this
	@Override
	public String makePathRelativeToString(String pathRelative) {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO: check this
	@Override
	public boolean isContainer() {
		return true;
	}
}

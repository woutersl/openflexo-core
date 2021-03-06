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
import java.util.Date;
import java.util.List;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.utils.FlexoObjectReference.ReferenceOwner;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.rm.Resource;
import org.openflexo.toolbox.FlexoVersion;
import org.openflexo.toolbox.IProgress;

/**
 * A FlexoResource is a resource that can be managed by OpenFlexo.
 * 
 * @param <RD>
 *            the type of the resource data reference by this resource
 * @author Guillaume, Sylvain
 * 
 */
@ModelEntity(isAbstract = true)
@ImplementationClass(FlexoResourceImpl.class)
public interface FlexoResource<RD extends ResourceData<RD>> extends FlexoObject, ReferenceOwner, Resource {

	public static final String NAME = "name";
	public static final String URI = "URI";
	public static final String VERSION = "version";
	public static final String REVISION = "revision";
	public static final String CONTAINER = "container";
	public static final String CONTENTS = "contents";
	public static final String DEPENDENCIES = "dependencies";
	public static final String LAST_UPDATE = "lastUpdate";
	public static final String SERVICE_MANAGER = "serviceManager";
	public static final String FLEXO_IO_DELEGATE = "flexoIODelegate";

	/**
	 * Returns the name of this resource. The name of the resource is a displayable name that the end-user will understand. There are no
	 * restrictions on this value. Restrictions are resource implementation dependent.
	 * 
	 * @return the name of this resource.
	 */
	@Getter(NAME)
	@XMLAttribute()
	public String getName();

	/**
	 * Sets the name of this resource
	 * 
	 * @param aName
	 */
	@Setter(NAME)
	public void setName(String aName);

	/**
	 * Returns the unique resource identifier of this resource. A URI is unique in the whole universe and clearly and uniquely identifies
	 * this resource.
	 * 
	 * @return the unique resource identifier of this resource
	 */
	@Override
	@Getter(URI)
	@XMLAttribute()
	public String getURI();

	/**
	 * Sets the unique resource identifier of this resource.
	 * 
	 * @param anURI
	 */
	@Override
	@Setter(URI)
	public void setURI(String anURI);

	/**
	 * Returns a displayable version that the end-user will understand.
	 * 
	 * @return a displayable version that the end-user will understand.
	 */
	@Getter(value = VERSION, isStringConvertable = true)
	@XMLAttribute
	public FlexoVersion getVersion();

	/**
	 * Sets version for this resource.
	 * 
	 * @param anURI
	 */
	@Setter(VERSION)
	public void setVersion(FlexoVersion aVersion);

	/**
	 * Returns the date where the resource was updated for the last time.<br>
	 * This timestamp references the date where the last serialization was performed (not the date where the resource data in memory was
	 * updated for the last time).<br>
	 * (For a file resource, this date is the diskLastModified date)
	 * 
	 * @return date
	 */
	@Override
	@Getter(value = LAST_UPDATE, isStringConvertable = true)
	@XMLAttribute
	public Date getLastUpdate();

	/**
	 * Sets the date where the resource was updated for the last time.<br>
	 * This timestamp references the date where the last serialization was performed (not the date where the resource data in memory was
	 * updated for the last time).<br>
	 * 
	 * @param date
	 */
	@Setter(LAST_UPDATE)
	public void setLastUpdate(Date aDate);

	/**
	 * Returns the revision of this resource. Each resource should ensure that upon each time it is edited, the revision number is
	 * incremented. If a merge of two different revisions of the same resource must be made, the resulting revision should be the greatest
	 * revision of the merged resource incremented.
	 * 
	 * @return the revision of this resource.
	 */
	@Getter(REVISION)
	@XMLAttribute
	public Long getRevision();

	/**
	 * Returns the FlexoServiceManager where this resource is registered
	 * 
	 * @return the name of this resource.
	 */
	@Override
	@Getter(value = SERVICE_MANAGER, ignoreType = true)
	public FlexoServiceManager getServiceManager();

	/**
	 * Sets the FlexoServiceManager where this resource is registered
	 * 
	 * @param aName
	 */
	@Setter(SERVICE_MANAGER)
	public void setServiceManager(FlexoServiceManager serviceManager);

	/**
	 * Returns the class of the resource data held by this resource.
	 * 
	 * @return the class of the resource data.
	 */
	public Class<RD> getResourceDataClass();

	/**
	 * Indicates whether this resource can be edited or not. Returns <code>true</code> if the resource cannot be edited, else returns
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if the resource cannot be edited, else returns <code>false</code>.
	 */
	@Override
	public boolean isReadOnly();

	/**
	 * Returns the resource in which this resource is contained.
	 * 
	 * @return the container of this resource.
	 */
	@Override
	@Getter(value = CONTAINER, inverse = CONTENTS)
	public FlexoResource<?> getContainer();

	/**
	 * Sets the resource in which this resource is contained.
	 * 
	 * 
	 * @param resource
	 */
	@Setter(CONTAINER)
	public void setContainer(FlexoResource<?> resource);

	/**
	 * Returns a list of resources contained by this resource.
	 * 
	 * @return the list of contained resources.
	 */
	@Override
	@Getter(value = CONTENTS, cardinality = Cardinality.LIST, inverse = CONTAINER)
	public List<FlexoResource<?>> getContents();

	/**
	 * Adds a resource to the contents.
	 * 
	 * @param resource
	 *            the resource to add
	 */
	@Adder(CONTENTS)
	public void addToContents(FlexoResource<?> resource);

	/**
	 * Removes a resource from the contents.
	 * 
	 * @param resource
	 *            the resource to remove
	 */
	@Remover(CONTENTS)
	public void removeFromContents(FlexoResource<?> resource);

	/**
	 * Returns a list of resources of supplied type contained by this resource.
	 * 
	 * @return the list of contained resources.
	 */
	public <R extends FlexoResource<?>> List<R> getContents(Class<R> resourceClass);

	/**
	 * Returns the resource identified in supplied URI, asserting that is resource is of supplied type
	 * 
	 * @return
	 */
	public <R extends FlexoResource<?>> R getContentWithURI(Class<R> resourceClass, String resourceURI);

	/**
	 * Returns a list of resources required by this resource.
	 * 
	 * @return a list of resources required by this resource.
	 */
	@Getter(value = DEPENDENCIES, cardinality = Cardinality.LIST)
	public List<FlexoResource<?>> getDependencies();

	/**
	 * Adds a resource to the dependencies.
	 * 
	 * @param resource
	 *            the resource to add
	 */
	@Adder(DEPENDENCIES)
	public void addToDependencies(FlexoResource<?> resource);

	/**
	 * Removes a resource from the dependencies.
	 * 
	 * @param resource
	 *            the resource to remove
	 */
	@Remover(DEPENDENCIES)
	public void removeFromDependencies(FlexoResource<?> resource);

	@Getter(value = FLEXO_IO_DELEGATE, inverse = FlexoIODelegate.FLEXO_RESOURCE)
	public FlexoIODelegate<?> getFlexoIODelegate();

	@Setter(FLEXO_IO_DELEGATE)
	public void setFlexoIODelegate(FlexoIODelegate<?> delegate);

	/**
	 * Return flag indicating if this resource is loaded
	 * 
	 * @return
	 */
	public boolean isLoaded();

	/**
	 * Return flag indicating if this resource is loadable
	 * 
	 * @return
	 */
	public boolean isLoadable();

	/**
	 * Returns the &quot;real&quot; resource data of this resource, asserting resource data is already loaded. If the resource is not
	 * loaded, do not load the data, and return null
	 * 
	 * @return the resource data.
	 */
	public RD getLoadedResourceData();

	/**
	 * Returns the &quot;real&quot; resource data of this resource. This may cause the loading of the resource data.
	 * 
	 * @param progress
	 *            a progress monitor in case the resource data is not immediately available.
	 * @return the resource data.
	 * @throws ResourceLoadingCancelledException
	 */
	public RD getResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException;

	/**
	 * Sets {@link ResourceData} for this resource
	 * 
	 * @param resourceData
	 */
	public void setResourceData(RD resourceData);

	/**
	 * Load the &quot;real&quot; load resource data of this resource.
	 * 
	 * @param progress
	 *            a progress monitor in case the resource data is not immediately available.
	 * @return the resource data.
	 * @throws ResourceLoadingCancelledException
	 * @throws ResourceDependencyLoopException
	 * @throws FileNotFoundException
	 * @throws FlexoException
	 */
	public RD loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, FileNotFoundException, FlexoException;

	/**
	 * Delete (dereference) resource data if resource data is loaded<br>
	 * Also delete the resource data
	 */
	public void unloadResourceData();

	/**
	 * Save the &quot;real&quot; resource data of this resource.
	 * 
	 * @throws SaveResourceException
	 */
	public void save(IProgress progress) throws SaveResourceException;

	/**
	 * This method updates the resource.
	 */
	/*public FlexoResourceTree update() throws ResourceDependencyLoopException, LoadResourceException, FileNotFoundException,
			ProjectLoadingCancelledException, FlexoException;*/

	/**
	 * Called to notify that a resource has successfully been loaded
	 */
	public void notifyResourceLoaded();

	/**
	 * Called to notify that a resource has successfully been saved
	 */
	public void notifyResourceSaved();

	/**
	 * Called to notify that a resource has been modified
	 */
	public void notifyResourceModified();

	public void notifyResourceStatusChanged();

	/**
	 * Called to notify that a resource has been added to contents
	 * 
	 * @param resource
	 *            : resource being added
	 */
	public void notifyContentsAdded(FlexoResource<?> resource);

	/**
	 * Called to notify that a resource has been remove to contents<br>
	 * 
	 * @param resource
	 *            : resource being removed
	 */
	public void notifyContentsRemoved(FlexoResource<?> resource);

	/**
	 * Delete this resource<br>
	 * Also delete the resource data of this resource, when instantiated
	 * 
	 * @return flag indicating if deletion has successfully been performed
	 */
	@Override
	public boolean delete(Object... context);

	/**
	 * Return a flag indicating if this resource was deleted
	 * 
	 * @return
	 */
	@Override
	public boolean isDeleted();

	// public Date getLastUpdate();
}

/**
 * 
 * Copyright (c) 2013-2014, Openflexo
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

package org.openflexo.foundation.technologyadapter;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.FlexoService;
import org.openflexo.foundation.FlexoServiceImpl;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterAdded;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterRemoved;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.resource.ResourceRepository;
import org.openflexo.toolbox.FlexoVersion;

/**
 * The {@link InformationSpace} is a {@link FlexoService} providing access to modelling elements from their original technological context.<br>
 * The information space is obtained through two services from the {@link FlexoServiceManager}, and results from the merging of the
 * {@link FlexoResourceCenterService} and the {@link ProjectNatureService}.<br>
 * For each {@link FlexoResourceCenter} and for each {@link TechnologyAdapter}, a repository of {@link FlexoModel} and
 * {@link FlexoMetaModel} are managed.
 * 
 * @author sylvain
 * 
 */
public class InformationSpace extends FlexoServiceImpl {

	public InformationSpace() {
	}

	@Override
	public void initialize() {
	}

	public List<TechnologyAdapter> getTechnologyAdapters() {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getTechnologyAdapters();
		}
		return null;
	}

	@Override
	public void receiveNotification(FlexoService caller, ServiceNotification notification) {
		if (notification instanceof ResourceCenterAdded) {
			setChanged();
			notifyObservers(new DataModification(null, ((ResourceCenterAdded) notification).getAddedResourceCenter()));
		}
		if (notification instanceof ResourceCenterRemoved) {
			setChanged();
			notifyObservers(new DataModification(((ResourceCenterRemoved) notification).getRemovedResourceCenter(), null));
		}
		super.receiveNotification(caller, notification);
	}

	/**
	 * Return the list of all non-empty {@link ResourceRepository} discovered in this {@link InformationSpace}, related to technology as
	 * supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	public List<ResourceRepository<?>> getAllRepositories(TechnologyAdapter technologyAdapter) {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getAllRepositories(technologyAdapter);
		}
		return null;
	}

	/**
	 * Return the list of all non-empty {@link ResourceRepository} discovered in the scope of {@link FlexoServiceManager} which may give
	 * access to some instance of supplied resource data class, related to technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	public <RD extends ResourceData<RD>> List<ResourceRepository<? extends FlexoResource<RD>>> getAllRepositories(
			TechnologyAdapter technologyAdapter, Class<RD> resourceDataClass) {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getAllRepositories(technologyAdapter, resourceDataClass);
		}
		return null;
	}

	/**
	 * Returns the resource identified by the given <code>uri</code>
	 * 
	 * @param uri
	 *            the URI of the resource
	 * @return the resource with the given <code>uri</code>, or null if it cannot be found.
	 */
	public @Nullable
	FlexoResource<?> getResource(@Nonnull String uri, FlexoVersion version) {
		if (getServiceManager() != null) {
			for (FlexoResourceCenter<?> rc : getServiceManager().getResourceCenterService().getResourceCenters()) {
				FlexoResource<?> res = rc.retrieveResource(uri, null);
				if (res != null) {
					return res;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a typed resource identified by the given <code>uri</code>
	 * 
	 * @param uri
	 *            the URI of the resource
	 * @param type
	 *            the type of the resource data reference by the resource to retrieve. The implementation is responsible to make the
	 *            appropriate type verifications.
	 * @return the resource with the given <code>uri</code>, or null if it cannot be found.
	 */
	public @Nullable
	<T extends ResourceData<T>> FlexoResource<T> getResource(@Nonnull String uri, FlexoVersion version, @Nonnull Class<T> type) {
		// TODO: a better type checking would be better
		return (FlexoResource<T>) getResource(uri, version);
	}

	// TODO: also handle version parameter
	public FlexoMetaModelResource<?, ?, ?> getMetaModelWithURI(String uri) {
		for (TechnologyAdapter ta : getServiceManager().getTechnologyAdapterService().getTechnologyAdapters()) {
			FlexoMetaModelResource<?, ?, ?> returned = getMetaModelWithURI(uri, ta);
			if (returned != null) {
				return returned;
			}
		}
		return null;
	}

	// TODO: also handle version parameter
	public FlexoMetaModelResource<?, ?, ?> getMetaModelWithURI(String uri, TechnologyAdapter technologyAdapter) {
		if (technologyAdapter != null && technologyAdapter.getTechnologyContextManager() != null) {
			return (FlexoMetaModelResource<?, ?, ?>) technologyAdapter.getTechnologyContextManager().getResourceWithURI(uri);
		}
		return null;
	}

	// TODO: also handle version parameter
	public FlexoModelResource<?, ?, ?, ?> getModelWithURI(String uri) {
		for (TechnologyAdapter ta : getServiceManager().getTechnologyAdapterService().getTechnologyAdapters()) {
			FlexoModelResource<?, ?, ?, ?> returned = getModelWithURI(uri, ta);
			if (returned != null) {
				return returned;
			}
		}
		return null;
	}

	// TODO: also handle version parameter
	public FlexoModelResource<?, ?, ?, ?> getModelWithURI(String uri, TechnologyAdapter technologyAdapter) {
		if (technologyAdapter == null) {
			logger.warning("Unexpected null " + technologyAdapter);
			return null;
		} else if (technologyAdapter.getTechnologyContextManager() == null) {
			// logger.warning("Unexpected null technologyContextManager for " + technologyAdapter);
			return null;
		}
		return (FlexoModelResource<?, ?, ?, ?>) technologyAdapter.getTechnologyContextManager().getResourceWithURI(uri);
	}

	/**
	 * Return the list of all non-empty {@link ModelRepository} discoverable in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	public List<ModelRepository<?, ?, ?, ?, ?>> getAllModelRepositories(TechnologyAdapter technologyAdapter) {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getAllModelRepositories(technologyAdapter);
		}
		return null;
	}

	/**
	 * Return the list of all non-empty {@link MetaModelRepository} discoverable in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	public List<MetaModelRepository<?, ?, ?, ?>> getAllMetaModelRepositories(TechnologyAdapter technologyAdapter) {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getAllMetaModelRepositories(technologyAdapter);
		}
		return null;
	}

	/**
	 * Lookup and return object with supplied URI
	 * 
	 * @param objectURI
	 * @return found {@link FlexoObject}
	 */
	/*public FlexoObject retrieveObjectWithURI(String objectURI) {
		// TODO: to be implemented
		return null;
	}*/

}

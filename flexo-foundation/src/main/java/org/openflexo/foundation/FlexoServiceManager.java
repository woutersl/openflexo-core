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

package org.openflexo.foundation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoProject.FlexoProjectReferenceLoader;
import org.openflexo.foundation.FlexoService.ServiceNotification;
import org.openflexo.foundation.fml.ViewPointLibrary;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ProjectClosed;
import org.openflexo.foundation.resource.ProjectLoaded;
import org.openflexo.foundation.resource.ResourceManager;
import org.openflexo.foundation.task.FlexoTask;
import org.openflexo.foundation.task.FlexoTaskManager;
import org.openflexo.foundation.technologyadapter.FlexoMetaModel;
import org.openflexo.foundation.technologyadapter.FlexoModel;
import org.openflexo.foundation.technologyadapter.InformationSpace;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;

/**
 * Default implementation of a manager of {@link FlexoService}<br>
 * All {@link FlexoService} are registered in the {@link FlexoServiceManager} which broadcast all service events to all services<br>
 * 
 * Please note that this class provides the basic support for Information Space<br>
 * The Information Space is obtained through two services from the {@link FlexoServiceManager}, and results from the merging of the
 * {@link FlexoResourceCenterService} and the {@link ProjectNatureService}.<br>
 * For each {@link FlexoResourceCenter} and for each {@link TechnologyAdapter}, a repository of {@link FlexoModel} and
 * {@link FlexoMetaModel} are managed.
 * 
 * 
 * @author sylvain
 * 
 */
public abstract class FlexoServiceManager {

	protected static final Logger logger = Logger.getLogger(FlexoServiceManager.class.getPackage().getName());

	private final ArrayList<FlexoService> registeredServices;

	public FlexoServiceManager() {
		registeredServices = new ArrayList<FlexoService>();
	}

	/*@Override
	public String getFullyQualifiedName() {
		return "FlexoServiceManager";
	}*/

	/**
	 * Register the supplied service, by adding it in the list of all services managed by this {@link FlexoServiceManager} instance<br>
	 * Notify all already registered services that a new service has been registered, then initialize the service itself.
	 * 
	 * @param service
	 */
	public void registerService(FlexoService service) {
		if (service != null) {
			registeredServices.add(service);
			service.register(this);

			notify(service, new ServiceRegistered());

			service.initialize();
		} else {
			logger.warning("Trying to register null FlexoService");
		}
	}

	public void notify(FlexoService caller, ServiceNotification notification) {
		for (FlexoService s : registeredServices) {
			if (s != caller) {
				s.receiveNotification(caller, notification);
			}
		}
		if (notification instanceof ProjectLoaded) {
			if (!getResourceCenterService().getResourceCenters().contains(((ProjectLoaded) notification).getProject())) {
				resourceCenterAdded(((ProjectLoaded) notification).getProject());
			}
		}
		if (notification instanceof ProjectClosed) {
			if (getResourceCenterService().getResourceCenters().contains(((ProjectClosed) notification).getProject())) {
				resourceCenterRemoved(((ProjectClosed) notification).getProject());
			}
		}
	}

	protected FlexoTask resourceCenterAdded(FlexoResourceCenter<?> resourceCenter) {
		getResourceCenterService().addToResourceCenters(resourceCenter);
		return null;
	}

	protected void resourceCenterRemoved(FlexoResourceCenter<?> resourceCenter) {
		getResourceCenterService().removeFromResourceCenters(resourceCenter);
	}

	public <S extends FlexoService> S getService(Class<S> serviceClass) {
		for (FlexoService s : registeredServices) {
			if (serviceClass.isAssignableFrom(s.getClass())) {
				return (S) s;
			}
		}
		return null;
	}

	public List<FlexoService> getRegisteredServices() {
		return registeredServices;
	}

	public FlexoEditingContext getEditingContext() {
		return getService(FlexoEditingContext.class);
	}

	public TechnologyAdapterService getTechnologyAdapterService() {
		return getService(TechnologyAdapterService.class);
	}

	public FlexoResourceCenterService getResourceCenterService() {
		return getService(FlexoResourceCenterService.class);
	}

	public ProjectNatureService getProjectNatureService() {
		return getService(ProjectNatureService.class);
	}

	public ViewPointLibrary getViewPointLibrary() {
		return getService(ViewPointLibrary.class);
	}

	public InformationSpace getInformationSpace() {
		return getService(InformationSpace.class);
	}

	public FlexoProjectReferenceLoader getProjectReferenceLoader() {
		return getService(FlexoProjectReferenceLoader.class);
	}

	/*public XMLSerializationService getXMLSerializationService() {
		return getService(XMLSerializationService.class);
	}*/

	public ResourceManager getResourceManager() {
		return getService(ResourceManager.class);
	}

	public FlexoTaskManager getTaskManager() {
		return getService(FlexoTaskManager.class);
	}

	public class ServiceRegistered implements ServiceNotification {
	}

	protected abstract FlexoEditingContext createEditingContext();

	protected abstract FlexoEditor createApplicationEditor();

	protected abstract FlexoProjectReferenceLoader createProjectReferenceLoader();

	protected abstract FlexoResourceCenterService createResourceCenterService();

	protected abstract TechnologyAdapterService createTechnologyAdapterService(FlexoResourceCenterService flexoResourceCenterService);

	protected abstract ProjectNatureService createProjectNatureService();

	protected abstract ViewPointLibrary createViewPointLibraryService();

	protected abstract InformationSpace createInformationSpace();

	protected abstract ResourceManager createResourceManager();

	protected abstract FlexoTaskManager createTaskManager();

}

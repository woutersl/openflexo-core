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

import org.openflexo.foundation.FlexoProject.FlexoProjectReferenceLoader;
import org.openflexo.foundation.fml.ViewPointLibrary;
import org.openflexo.foundation.nature.DefaultProjectNatureService;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.remoteresources.FlexoUpdateService;
import org.openflexo.foundation.resource.DefaultResourceCenterService;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ResourceManager;
import org.openflexo.foundation.resource.ResourceRepository;
import org.openflexo.foundation.task.FlexoTaskManager;
import org.openflexo.foundation.task.ThreadPoolFlexoTaskManager;
import org.openflexo.foundation.technologyadapter.DefaultTechnologyAdapterService;
import org.openflexo.foundation.technologyadapter.InformationSpace;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;

/**
 * Default implementation of {@link FlexoServiceManager}
 * 
 * 
 * @author sylvain
 * 
 */
public class DefaultFlexoServiceManager extends FlexoServiceManager {

	public DefaultFlexoServiceManager() {

		FlexoEditingContext editingContext = createEditingContext();
		registerService(editingContext);

		FlexoTaskManager taskManager = createTaskManager();
		registerService(taskManager);

		ResourceManager resourceManager = createResourceManager();
		registerService(resourceManager);
		FlexoProjectReferenceLoader projectReferenceLoader = createProjectReferenceLoader();
		if (projectReferenceLoader != null) {
			registerService(projectReferenceLoader);
		}

		FlexoUpdateService flexoUpdateService = new FlexoUpdateService();
		registerService(flexoUpdateService);
		
		FlexoResourceCenterService resourceCenterService = createResourceCenterService();
		registerService(resourceCenterService);
		
		TechnologyAdapterService technologyAdapterService = createTechnologyAdapterService(resourceCenterService);
		registerService(technologyAdapterService);

		ProjectNatureService projectNatureService = createProjectNatureService();
		registerService(projectNatureService);

		InformationSpace informationSpace = createInformationSpace();
		registerService(informationSpace);

		ViewPointLibrary viewPointLibrary = createViewPointLibraryService();
		registerService(viewPointLibrary);
	}

	@Override
	protected FlexoEditingContext createEditingContext() {
		return FlexoEditingContext.createInstance();
	}

	@Override
	protected ResourceManager createResourceManager() {
		return ResourceManager.createInstance();
	}

	@Override
	protected FlexoResourceCenterService createResourceCenterService() {
		return DefaultResourceCenterService.getNewInstance();
	}

	@Override
	protected TechnologyAdapterService createTechnologyAdapterService(FlexoResourceCenterService resourceCenterService) {
		return DefaultTechnologyAdapterService.getNewInstance(resourceCenterService);
	}

	@Override
	protected ProjectNatureService createProjectNatureService() {
		return DefaultProjectNatureService.getNewInstance();
	}

	@Override
	protected ViewPointLibrary createViewPointLibraryService() {
		return new ViewPointLibrary();
	}

	@Override
	protected InformationSpace createInformationSpace() {
		return new InformationSpace();
	}

	@Override
	protected FlexoTaskManager createTaskManager() {
		return ThreadPoolFlexoTaskManager.createInstance();
	}

	@Override
	protected FlexoProjectReferenceLoader createProjectReferenceLoader() {
		// Please override
		return null;
	}

	@Override
	protected FlexoEditor createApplicationEditor() {
		// Please override
		return null;
	}

	public String debug() {
		StringBuffer sb = new StringBuffer();
		sb.append("**********************************************\n");
		sb.append("FLEXO SERVICE MANAGER: " + getClass() + "\n");
		sb.append("**********************************************\n");
		sb.append("Registered services: " + getRegisteredServices().size() + "\n");
		for (FlexoService s : getRegisteredServices()) {
			sb.append("Service: " + s.getClass().getSimpleName() + "\n");
		}
		if (getTechnologyAdapterService() != null) {
			sb.append("**********************************************\n");
			sb.append("Technology Adapter Service: " + getTechnologyAdapterService().getClass().getSimpleName() + " technology adapters: "
					+ getTechnologyAdapterService().getTechnologyAdapters().size() + "\n");
			for (TechnologyAdapter ta : getTechnologyAdapterService().getTechnologyAdapters()) {
				sb.append("> " + ta.getName() + "\n");
			}
		}
		if (getResourceCenterService() != null) {
			sb.append("**********************************************\n");
			sb.append("Resource Center Service: " + getResourceCenterService().getClass().getSimpleName() + " resource centers: "
					+ getResourceCenterService().getResourceCenters().size() + "\n");
			for (FlexoResourceCenter<?> rc : getResourceCenterService().getResourceCenters()) {
				sb.append("> " + rc.getName() + "\n");
			}
		}
		if (getInformationSpace() != null) {
			sb.append("**********************************************\n");
			sb.append("Information Space\n");
			for (TechnologyAdapter ta : getTechnologyAdapterService().getTechnologyAdapters()) {
				for (ResourceRepository<?> rep : getInformationSpace().getAllRepositories(ta)) {
					System.out.println("Technology adapter: " + ta + " repository: " + rep + "\n");
					for (FlexoResource<?> r : rep.getAllResources()) {
						sb.append("> " + r.getURI() + "\n");
					}
				}
			}
		}
		sb.append("**********************************************");
		return sb.toString();
	}
}

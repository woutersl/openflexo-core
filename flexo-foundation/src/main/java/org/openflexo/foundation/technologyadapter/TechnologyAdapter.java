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
package org.openflexo.foundation.technologyadapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.resource.DirectoryContainerResource;
import org.openflexo.foundation.resource.FlexoFileResource;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.foundation.resource.ResourceRepository;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.VirtualModelModelFactory;

/**
 * This class represents a technology adapter<br>
 * A {@link TechnologyAdapter} is plugin loaded at run-time which defines and implements the required A.P.I used to connect Flexo Modelling
 * Language Virtual Machine to a technology.<br>
 * 
 * Note: this code was partially adapted from Nicolas Daniels (Blue Pimento team)
 * 
 * @author sylvain
 * 
 */
public abstract class TechnologyAdapter {

	private static final Logger logger = Logger.getLogger(TechnologyAdapter.class.getPackage().getName());

	private TechnologyAdapterService technologyAdapterService;

	/**
	 * Return human-understandable name for this technology adapter<br>
	 * Unique id to consider must be the class name
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * Creates a new ModelSlot in the scope of the supplied {@link ViewPoint}
	 * 
	 * @return a new {@link ModelSlot}
	 */
	// public abstract ModelSlot<?> createNewModelSlot(ViewPoint viewPoint);

	/**
	 * Creates a new ModelSlot in the scope of supplied {@link VirtualModel}
	 * 
	 * @return a new {@link ModelSlot}
	 */
	// public abstract ModelSlot<?> createNewModelSlot(VirtualModel
	// virtualModel);

	/**
	 * Return flag indicating if supplied file represents a valid XSD schema
	 * 
	 * @param aMetaModelFile
	 * @param rc
	 * 
	 * @return
	 */
	// public abstract boolean isValidMetaModelFile(File aMetaModelFile,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Retrieve and return URI for supplied meta model file, if supplied file represents a valid meta model
	 * 
	 * @param aMetaModelFile
	 * @param rc
	 *            TODO
	 * @return
	 */
	// public abstract String retrieveMetaModelURI(File aMetaModelFile,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Instantiate new meta model resource stored in supplied meta model file
	 * 
	 * @param aMetaModelFile
	 * @param rc
	 *            TODO
	 * @return
	 */
	// public abstract FlexoMetaModelResource<?, ?>
	// retrieveMetaModelResource(File aMetaModelFile,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Return flag indicating if supplied file represents a valid model conform to supplied meta-model
	 * 
	 * @param aModelFile
	 * @param metaModelResource
	 * @param rc
	 *            TODO
	 * @return
	 */
	// public abstract boolean isValidModelFile(File aModelFile,
	// FlexoMetaModelResource<?, ?> metaModelResource,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Return flag indicating if supplied file represents a valid model<br>
	 * Note that the meta-model is not yet known
	 * 
	 * @param aModelFile
	 * @param metaModelResource
	 * @param rc
	 *            TODO
	 * @return
	 */
	// public abstract boolean isValidModelFile(File aModelFile,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Retrieve and return URI for supplied model file
	 * 
	 * @param aModelFile
	 * @param rc
	 *            TODO
	 * @return
	 */
	// public abstract String retrieveModelURI(File aModelFile,
	// FlexoMetaModelResource<?, ?> metaModelResource,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Instantiate new model resource stored in supplied model file<br>
	 * The metamodel is not yet known, so we have to iterate on all known metamodels of this technology to find one (or many) which is
	 * relevant
	 * 
	 * @return
	 */
	// public abstract FlexoModelResource<?, ?> retrieveModelResource(File
	// aModelFile, TechnologyContextManager technologyContextManager);

	/**
	 * Instantiate new model resource stored in supplied model file, given the conformant metamodel<br>
	 * We assert here that model resource is conform to supplied metamodel, ie we will not try to lookup the metamodel but take the one
	 * which was supplied
	 * 
	 * @return
	 */
	// public abstract FlexoModelResource<?, ?> retrieveModelResource(File
	// aModelFile, FlexoMetaModelResource<?, ?> metaModelResource,
	// TechnologyContextManager technologyContextManager);

	/**
	 * Creates new model conform to the supplied meta model in a FlexoResourceCenter.
	 * 
	 * @param resourceCenter
	 * @param relativePath
	 * @param filename
	 * @param modelUri
	 * @param metaModelResource
	 * @param technologyContextManager
	 * @return
	 */
	/*
	 * public abstract FlexoModelResource<?, ?>
	 * createEmptyModel(FileSystemBasedResourceCenter resourceCenter, String
	 * relativePath, String filename, String modelUri, FlexoMetaModelResource<?,
	 * ?> metaModelResource, TechnologyContextManager technologyContextManager);
	 */

	/**
	 * Creates new model conform to the supplied meta model in a FlexoProject.
	 * 
	 * @param project
	 * @param filename
	 * @param modelUri
	 * @param metaModelResource
	 * @param technologyContextManager
	 * @return
	 */
	/*
	 * public abstract FlexoModelResource<?, ?> createEmptyModel(FlexoProject
	 * project, String filename, String modelUri, FlexoMetaModelResource<?, ?>
	 * metaModelResource, TechnologyContextManager technologyContextManager);
	 */

	/**
	 * Returns applicable {@link ProjectNatureService}
	 * 
	 * @return
	 */
	public TechnologyAdapterService getTechnologyAdapterService() {
		return technologyAdapterService;
	}

	/**
	 * Sets applicable {@link ProjectNatureService}
	 * 
	 * @param technologyAdapterService
	 */
	public void setTechnologyAdapterService(TechnologyAdapterService technologyAdapterService) {
		this.technologyAdapterService = technologyAdapterService;
	}

	/**
	 * Creates and return the {@link TechnologyContextManager} for this technology and for all {@link FlexoResourceCenter} declared in the
	 * scope of {@link FlexoResourceCenterService}
	 * 
	 * @return
	 */
	public abstract TechnologyContextManager createTechnologyContextManager(FlexoResourceCenterService service);

	/**
	 * Return the {@link TechnologyContextManager} for this technology shared by all {@link FlexoResourceCenter} declared in the scope of
	 * {@link FlexoResourceCenterService}
	 * 
	 * @return
	 */
	public TechnologyContextManager getTechnologyContextManager() {
		return getTechnologyAdapterService().getTechnologyContextManager(this);
	}

	/**
	 * Return the technology-specific binding factory
	 * 
	 * @return
	 */
	public abstract TechnologyAdapterBindingFactory getTechnologyAdapterBindingFactory();

	/**
	 * Provides a hook to finalize initialization of a TechnologyAdapter.<br>
	 * This method is called:
	 * <ul>
	 * <li>after all TechnologyAdapter have been loaded</li>
	 * <li>after all {@link FlexoResourceCenter} have been initialized</li>
	 * </ul>
	 */
	public void initialize() {
	}

	/**
	 * Initialize the supplied resource center with the technology<br>
	 * ResourceCenter is scanned, ResourceRepositories are created and new technology-specific resources are build and registered.
	 * 
	 * @param resourceCenter
	 */
	public abstract <I> void initializeResourceCenter(FlexoResourceCenter<I> resourceCenter);

	public abstract <I> boolean isIgnorable(FlexoResourceCenter<I> resourceCenter, I contents);

	public abstract <I> void contentsAdded(FlexoResourceCenter<I> resourceCenter, I contents);

	public abstract <I> void contentsDeleted(FlexoResourceCenter<I> resourceCenter, I contents);

	/**
	 * Provides a hook to detect when a new resource center was added or discovered
	 * 
	 * @param newResourceCenter
	 */
	public void resourceCenterAdded(FlexoResourceCenter newResourceCenter) {
	}

	/**
	 * Provides a hook to detect when a new resource center was removed
	 * 
	 * @param newResourceCenter
	 */
	public void resourceCenterRemoved(FlexoResourceCenter removedResourceCenter) {
	}

	/**
	 * Return model resource identified by its uri. Lookup is performed on all known resource centers.
	 * 
	 * @param modelURI
	 * @return
	 */
	/*
	 * public FlexoModelResource<?, ?> getModelResource(String modelURI) { for
	 * (ModelRepository<?, ?, ?, ?> modelRepository :
	 * getTechnologyAdapterService().getAllModelRepositories(this)) {
	 * FlexoResource<?> r = modelRepository.getResource(modelURI); if (r !=
	 * null) { return (FlexoModelResource<?, ?>) r; } } return null; }
	 */

	/**
	 * Return model resource identified by its uri. Lookup is performed on all known resource centers.
	 * 
	 * @param modelURI
	 * @return
	 */
	/*
	 * public FlexoMetaModelResource<?, ?> getMetaModelResource(String modelURI)
	 * { for (MetaModelRepository<?, ?, ?, ?> mmRepository :
	 * getTechnologyAdapterService().getAllMetaModelRepositories(this)) {
	 * FlexoResource<?> r = mmRepository.getResource(modelURI); if (r != null) {
	 * return (FlexoMetaModelResource<?, ?>) r; } } return null; }
	 */

	// public abstract String getExpectedMetaModelExtension();

	// public abstract String
	// getExpectedModelExtension(FlexoMetaModelResource<?, ?>
	// metaModelResource);

	private List<Class<? extends ModelSlot<?>>> availableModelSlotTypes;

	public List<Class<? extends ModelSlot<?>>> getAvailableModelSlotTypes() {
		if (availableModelSlotTypes == null) {
			availableModelSlotTypes = computeAvailableModelSlotTypes();
		}
		return availableModelSlotTypes;
	}

	private List<Class<? extends ModelSlot<?>>> computeAvailableModelSlotTypes() {
		availableModelSlotTypes = new ArrayList<Class<? extends ModelSlot<?>>>();
		Class<?> cl = getClass();
		if (cl.isAnnotationPresent(DeclareModelSlots.class)) {
			DeclareModelSlots allModelSlots = cl.getAnnotation(DeclareModelSlots.class);
			for (DeclareModelSlot modelSlotDeclaration : allModelSlots.value()) {
				availableModelSlotTypes.add(modelSlotDeclaration.modelSlotClass());
			}
		}
		return availableModelSlotTypes;
	}

	/**
	 * Creates and return a new {@link ModelSlot} of supplied class.<br>
	 * This responsability is delegated to the {@link TechnologyAdapter} which manages with introspection its own {@link ModelSlot} types
	 * 
	 * @param modelSlotClass
	 * @param containerVirtualModel
	 *            the virtual model in which model slot should be created
	 * @return
	 */
	public final <MS extends ModelSlot<?>> MS makeModelSlot(Class<MS> modelSlotClass, VirtualModel containerVirtualModel) {
		VirtualModelModelFactory factory = containerVirtualModel.getVirtualModelFactory();
		MS returned = factory.newInstance(modelSlotClass);
		// containerVirtualModel.addToModelSlots(returned);
		returned.setTechnologyAdapter(this);
		return returned;

	}

	/**
	 * Retrieve (creates it when not existant) folder containing supplied file
	 * 
	 * @param repository
	 * @param aFile
	 * @return
	 */
	protected <R extends FlexoResource<?>> RepositoryFolder<R> retrieveRepositoryFolder(ResourceRepository<R> repository, File aFile) {
		try {
			return repository.getRepositoryFolder(aFile, true);
		} catch (IOException e) {
			e.printStackTrace();
			return repository.getRootFolder();
		}
	}

	/**
	 * Called when a resource has been looked-up by the {@link TechnologyAdapter}
	 * 
	 * @param resource
	 * @param resourceCenter
	 */
	public void referenceResource(FlexoResource<?> resource, FlexoResourceCenter<?> resourceCenter) {
		if (resourceCenter instanceof ResourceRepository && resource instanceof FlexoFileResource) {
			// Also register the resource in the ResourceCenter seen as a ResourceRepository
			try {
				File candidateFile = null;
				if (resource instanceof DirectoryContainerResource) {
					candidateFile = ((DirectoryContainerResource<?>) resource).getDirectory();
				} else {
					candidateFile = ((FlexoFileResource<?>) resource).getFile();
				}
				((ResourceRepository) resourceCenter).registerResource(resource,
						((ResourceRepository<?>) resourceCenter).getRepositoryFolder(candidateFile, true));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Called when a resource has been dereferenced by the {@link TechnologyAdapter}
	 * 
	 * @param resource
	 * @param resourceCenter
	 */
	public void dereferenceResource(FlexoResource<?> resource, FlexoResourceCenter<?> resourceCenter) {
		// TODO
		logger.warning("dereferenceResource() not implemented yet");
	}

	// Override when required
	public void initVirtualModelFactory(VirtualModelModelFactory virtualModelModelFactory) {
	}

	/**
	 * Return the list of all non-empty {@link ResourceRepository} discovered in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	public List<ResourceRepository<?>> getAllRepositories() {
		List<ResourceRepository<?>> returned = new ArrayList<ResourceRepository<?>>();
		for (FlexoResourceCenter<?> rc : getTechnologyAdapterService().getServiceManager().getResourceCenterService().getResourceCenters()) {
			Collection<ResourceRepository<?>> repCollection = rc.getRegistedRepositories(this);
			if (repCollection != null) {
				returned.addAll(repCollection);
			}
		}
		return returned;
	}

}

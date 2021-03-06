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

package org.openflexo.foundation.technologyadapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import org.openflexo.connie.type.CustomType;
import org.openflexo.connie.type.CustomTypeFactory;
import org.openflexo.foundation.FlexoService;
import org.openflexo.foundation.FlexoServiceImpl;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.fml.rt.FMLRTTechnologyAdapter;
import org.openflexo.foundation.nature.ProjectNatureService;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterAdded;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterRemoved;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.resource.ResourceRepository;
import org.openflexo.foundation.task.Progress;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;

/**
 * Default implementation for {@link ProjectNatureService}
 * 
 * @author sylvain
 * 
 */
public abstract class DefaultTechnologyAdapterService extends FlexoServiceImpl implements TechnologyAdapterService {

	private static final Logger logger = Logger.getLogger(DefaultTechnologyAdapterService.class.getPackage().getName());

	private FlexoResourceCenterService flexoResourceCenterService;

	private Map<Class, TechnologyAdapter> loadedAdapters;
	private Map<TechnologyAdapter, TechnologyContextManager> technologyContextManagers;

	public static TechnologyAdapterService getNewInstance(FlexoResourceCenterService resourceCenterService) {
		try {
			ModelFactory factory = new ModelFactory(TechnologyAdapterService.class);
			factory.setImplementingClassForInterface(DefaultTechnologyAdapterService.class, TechnologyAdapterService.class);
			TechnologyAdapterService returned = factory.newInstance(TechnologyAdapterService.class);
			returned.setFlexoResourceCenterService(resourceCenterService);
			// returned.loadAvailableTechnologyAdapters();
			return returned;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load all available technology adapters<br>
	 * Retrieve all {@link TechnologyAdapter} available from classpath. <br>
	 * Map contains the TechnologyAdapter class name as key and the TechnologyAdapter itself as value.
	 * 
	 * @return the retrieved TechnologyModuleDefinition map.
	 */
	public void loadAvailableTechnologyAdapters() {

		// Load all other technology adapters found in the classpath (using java ServiceLoader)
		// (Those TA are found using META-INF informations collected in classpath)
		ServiceLoader<TechnologyAdapter> loader = ServiceLoader.load(TechnologyAdapter.class);

		if (loadedAdapters == null) {

			loadedAdapters = new Hashtable<Class, TechnologyAdapter>();
			technologyContextManagers = new Hashtable<TechnologyAdapter, TechnologyContextManager>();

			logger.info("Loading available technology adapters...");

			// First load the FML technology adapter
			FMLTechnologyAdapter fmlTechnologyAdapter = new FMLTechnologyAdapter();
			registerTechnologyAdapter(fmlTechnologyAdapter);

			// First load the FML@runtime technology adapter
			FMLRTTechnologyAdapter fmlRTTechnologyAdapter = new FMLRTTechnologyAdapter();
			registerTechnologyAdapter(fmlRTTechnologyAdapter);

			// Then the other TA
			Iterator<TechnologyAdapter> iterator = loader.iterator();
			while (iterator.hasNext()) {
				TechnologyAdapter technologyAdapter = iterator.next();
				registerTechnologyAdapter(technologyAdapter);
			}
			logger.info("Loading available technology adapters. Done.");
		} else {
			Iterator<TechnologyAdapter> iterator = loader.iterator();
			while (iterator.hasNext()) {
				TechnologyAdapter technologyAdapter = iterator.next();
				if (!loadedAdapters.containsKey(technologyAdapter.getClass())) {
					registerTechnologyAdapter(technologyAdapter);
				}
				logger.info("Loading available technology adapters. Done.");
			}
		}

	}

	/*public void loadAvailableTechnologyAdapters(ServiceLoader<TechnologyAdapter> loader) {
		if (loadedAdapters != null) {
			Iterator<TechnologyAdapter> iterator = loader.iterator();
			while (iterator.hasNext()) {
				TechnologyAdapter technologyAdapter = iterator.next();
				if(!loadedAdapters.containsKey(technologyAdapter.getClass())){
					registerTechnologyAdapter(technologyAdapter);
				}
			}
			logger.info("Loading available technology adapters. Done.");
		}
	}*/

	private void registerTechnologyAdapter(TechnologyAdapter technologyAdapter) {
		logger.fine("Found " + technologyAdapter);
		technologyAdapter.setTechnologyAdapterService(this);
		TechnologyContextManager tcm = technologyAdapter.createTechnologyContextManager(getFlexoResourceCenterService());
		if (tcm != null) {
			technologyContextManagers.put(technologyAdapter, tcm);
		}
		addToTechnologyAdapters(technologyAdapter);

		logger.info("Load " + technologyAdapter.getName() + " as " + technologyAdapter.getClass());

		if (loadedAdapters.containsKey(technologyAdapter.getClass())) {
			logger.severe("Cannot include TechnologyAdapter with classname '" + technologyAdapter.getClass().getName()
					+ "' because it already exists !!!! A TechnologyAdapter name MUST be unique !");
		} else {
			loadedAdapters.put(technologyAdapter.getClass(), technologyAdapter);
		}
	}

	/**
	 * Return loaded technology adapter mapping supplied class<br>
	 * If adapter is not loaded, return null
	 * 
	 * @param technologyAdapterClass
	 * @return
	 */
	@Override
	public <TA extends TechnologyAdapter> TA getTechnologyAdapter(Class<TA> technologyAdapterClass) {
		return (TA) loadedAdapters.get(technologyAdapterClass);
	}

	/**
	 * Iterates over loaded technology adapters
	 * 
	 * @return
	 */
	public Collection<TechnologyAdapter> getLoadedAdapters() {
		return loadedAdapters.values();
	}

	/**
	 * Return the {@link TechnologyContextManager} for this technology for this technology shared by all {@link FlexoResourceCenter}
	 * declared in the scope of {@link FlexoResourceCenterService}
	 * 
	 * @return
	 */
	@Override
	public TechnologyContextManager getTechnologyContextManager(TechnologyAdapter technologyAdapter) {
		return technologyContextManagers.get(technologyAdapter);
	}

	@Override
	public void receiveNotification(FlexoService caller, ServiceNotification notification) {
		if (caller instanceof FlexoResourceCenterService) {
			if (notification instanceof ResourceCenterAdded) {
				FlexoResourceCenter rc = ((ResourceCenterAdded) notification).getAddedResourceCenter();
				Progress.progress(FlexoLocalization.localizedForKey("initializing") + " " + rc);
				rc.initialize(this);
				for (TechnologyAdapter ta : getTechnologyAdapters()) {
					Progress.progress(FlexoLocalization.localizedForKey("scan_resources_for_technology_adapters") + " " + ta.getName());
					ta.resourceCenterAdded(rc);
				}
			}
			if (notification instanceof ResourceCenterRemoved) {
				FlexoResourceCenter rc = ((ResourceCenterRemoved) notification).getRemovedResourceCenter();
				rc.initialize(this);
				for (TechnologyAdapter ta : getTechnologyAdapters()) {
					ta.resourceCenterRemoved(rc);
				}
			}
		}
	}

	@Override
	public void initialize() {
		loadAvailableTechnologyAdapters();
		for (TechnologyAdapter ta : getTechnologyAdapters()) {
			ta.initialize();
		}
		for (FlexoResourceCenter rc : getFlexoResourceCenterService().getResourceCenters()) {
			rc.initialize(this);
		}
	}

	/*@Override
	public void initialize(ServiceLoader<TechnologyAdapter> loader) {
		loadAvailableTechnologyAdapters(loader);
	}*/

	/**
	 * Return the list of all non-empty {@link ModelRepository} discoverable in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	/*
	 * @Override public <R extends FlexoResource<? extends M>, M extends
	 * FlexoModel<M, MM>, MM extends FlexoMetaModel<MM>, TA extends
	 * TechnologyAdapter<M, MM>> List<ModelRepository<R, M, MM, TA>>
	 * getAllModelRepositories( TA technologyAdapter) { List<ModelRepository<R,
	 * M, MM, TA>> returned = new ArrayList<ModelRepository<R, M, MM, TA>>();
	 * for (FlexoResourceCenter rc :
	 * getFlexoResourceCenterService().getResourceCenters()) {
	 * returned.add((ModelRepository<R, M, MM, TA>)
	 * rc.getModelRepository(technologyAdapter)); } return returned; }
	 */

	/*
	 * @Override public List<ModelRepository<?, ?, ?, ?>>
	 * getAllModelRepositories(TechnologyAdapter technologyAdapter) {
	 * List<ModelRepository<?, ?, ?, ?>> returned = new
	 * ArrayList<ModelRepository<?, ?, ?, ?>>(); for (FlexoResourceCenter rc :
	 * getFlexoResourceCenterService().getResourceCenters()) { if
	 * (rc.getModelRepository(technologyAdapter) != null &&
	 * rc.getModelRepository(technologyAdapter).getSize() > 0) {
	 * logger.fine("Adding ModelRepository for " + technologyAdapter.getName() +
	 * " and RC " + rc); returned.add(rc.getModelRepository(technologyAdapter));
	 * } } return returned; }
	 */

	/**
	 * Return the list of all non-empty {@link MetaModelRepository} discoverable in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	/*
	 * @Override public <R extends FlexoResource<? extends MM>, M extends
	 * FlexoModel<M, MM>, MM extends FlexoMetaModel<MM>, TA extends
	 * TechnologyAdapter<M, MM>> List<MetaModelRepository<R, M, MM, TA>>
	 * getAllMetaModelRepositories( TA technologyAdapter) {
	 * List<MetaModelRepository<R, M, MM, TA>> returned = new
	 * ArrayList<MetaModelRepository<R, M, MM, TA>>(); for (FlexoResourceCenter
	 * rc : getFlexoResourceCenterService().getResourceCenters()) {
	 * returned.add((MetaModelRepository<R, M, MM, TA>)
	 * rc.getMetaModelRepository(technologyAdapter)); } return returned; }
	 */
	/*
	 * @Override public List<MetaModelRepository<?, ?, ?, ?>>
	 * getAllMetaModelRepositories(TechnologyAdapter technologyAdapter) {
	 * List<MetaModelRepository<?, ?, ?, ?>> returned = new
	 * ArrayList<MetaModelRepository<?, ?, ?, ?>>(); for (FlexoResourceCenter rc
	 * : getFlexoResourceCenterService().getResourceCenters()) { if
	 * (rc.getMetaModelRepository(technologyAdapter) != null &&
	 * rc.getMetaModelRepository(technologyAdapter).getSize() > 0) {
	 * logger.fine("Adding MetaModelRepository for " +
	 * technologyAdapter.getName() + " and RC " + rc);
	 * returned.add(rc.getMetaModelRepository(technologyAdapter)); } } return
	 * returned; }
	 */

	/**
	 * Return the list of all non-empty {@link ResourceRepository} discovered in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	@Override
	public List<ResourceRepository<?>> getAllRepositories(TechnologyAdapter technologyAdapter) {
		List<ResourceRepository<?>> returned = new ArrayList<ResourceRepository<?>>();
		for (FlexoResourceCenter<?> rc : getFlexoResourceCenterService().getResourceCenters()) {
			Collection<ResourceRepository<?>> repCollection = rc.getRegistedRepositories(technologyAdapter);
			if (repCollection != null) {
				returned.addAll(repCollection);
			}
		}
		return returned;
	}

	/**
	 * Return the list of all non-empty {@link ResourceRepository} discovered in the scope of {@link FlexoServiceManager} which may give
	 * access to some instance of supplied resource data class, related to technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	@Override
	public <RD extends ResourceData<RD>> List<ResourceRepository<? extends FlexoResource<RD>>> getAllRepositories(
			TechnologyAdapter technologyAdapter, Class<RD> resourceDataClass) {
		List<ResourceRepository<? extends FlexoResource<RD>>> returned = new ArrayList<ResourceRepository<? extends FlexoResource<RD>>>();
		for (FlexoResourceCenter<?> rc : getFlexoResourceCenterService().getResourceCenters()) {
			Collection<ResourceRepository<?>> repCollection = rc.getRegistedRepositories(technologyAdapter);
			if (repCollection != null) {
				for (ResourceRepository<?> rep : repCollection) {
					if (resourceDataClass.isAssignableFrom(rep.getResourceDataClass())) {
						returned.add((ResourceRepository<? extends FlexoResource<RD>>) rep);
					}
				}
			}
		}
		return returned;
	}

	/**
	 * Return the list of all non-empty {@link ModelRepository} discovered in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	@Override
	public List<ModelRepository<?, ?, ?, ?, ?>> getAllModelRepositories(TechnologyAdapter technologyAdapter) {
		List<ModelRepository<?, ?, ?, ?, ?>> returned = new ArrayList<ModelRepository<?, ?, ?, ?, ?>>();
		for (FlexoResourceCenter<?> rc : getFlexoResourceCenterService().getResourceCenters()) {
			Collection<ResourceRepository<?>> repCollection = rc.getRegistedRepositories(technologyAdapter);
			if (repCollection != null) {
				for (ResourceRepository<?> rep : repCollection) {
					if (rep instanceof ModelRepository) {
						returned.add((ModelRepository<?, ?, ?, ?, ?>) rep);
					}
				}
			}
		}
		return returned;
	}

	/**
	 * Return the list of all non-empty {@link MetaModelRepository} discovered in the scope of {@link FlexoServiceManager}, related to
	 * technology as supplied by {@link TechnologyAdapter} parameter
	 * 
	 * @param technologyAdapter
	 * @return
	 */
	@Override
	public List<MetaModelRepository<?, ?, ?, ?>> getAllMetaModelRepositories(TechnologyAdapter technologyAdapter) {
		List<MetaModelRepository<?, ?, ?, ?>> returned = new ArrayList<MetaModelRepository<?, ?, ?, ?>>();
		for (FlexoResourceCenter<?> rc : getFlexoResourceCenterService().getResourceCenters()) {
			Collection<ResourceRepository<?>> repCollection = rc.getRegistedRepositories(technologyAdapter);
			if (repCollection != null) {
				for (ResourceRepository<?> rep : repCollection) {
					if (rep instanceof MetaModelRepository) {
						returned.add((MetaModelRepository<?, ?, ?, ?>) rep);
					}
				}
			}
		}
		return returned;
	}

	private final Map<Class<? extends CustomType>, CustomTypeFactory<?>> customTypeFactories = new HashMap<Class<? extends CustomType>, CustomTypeFactory<?>>();

	/**
	 * Return all {@link CustomType} factories defined for all known technologies
	 * 
	 * @return
	 */
	@Override
	public Map<Class<? extends CustomType>, CustomTypeFactory<?>> getCustomTypeFactories() {
		return customTypeFactories;
	}

	/**
	 * Register CustomTypeFactory
	 * 
	 * @param typeClass
	 * @param factory
	 */
	@Override
	public <T extends CustomType> void registerTypeClass(Class<T> typeClass, CustomTypeFactory<T> factory) {
		// System.out.println("registering " + typeClass + " with " + factory);
		customTypeFactories.put(typeClass, factory);
	}

}

/**
 * 
 * Copyright (c) 2014, Openflexo
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

package org.openflexo.foundation.fml;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.openflexo.foundation.DefaultFlexoObject;
import org.openflexo.foundation.FlexoService;
import org.openflexo.foundation.FlexoServiceManager;
import org.openflexo.foundation.fml.rm.ViewPointResource;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterAdded;
import org.openflexo.foundation.resource.DefaultResourceCenterService.ResourceCenterRemoved;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.validation.Validable;

/**
 * The {@link ViewPointLibrary} manages all references to all {@link ViewPoint} known in a JVM instance.<br>
 * The {@link ViewPointLibrary} is a {@link FlexoService} working in conjunction with a {@link FlexoResourceCenterService}, with
 * synchronization performed through a {@link FlexoServiceManager} (generally this is the ApplicationContext)
 * 
 * @author sylvain
 * 
 */
public class ViewPointLibrary extends DefaultFlexoObject implements FlexoService, Validable {

	private static final Logger logger = Logger.getLogger(ViewPointLibrary.class.getPackage().getName());

	public ViewPointValidationModel viewPointValidationModel;

	private final Map<String, ViewPointResource> map;

	private FlexoServiceManager serviceManager;

	// private XMLMapping viewPointModel_0_1;
	// private XMLMapping viewPointModel_1_0;

	public ViewPointLibrary() {
		super();

		map = new Hashtable<String, ViewPointResource>();

	}

	/**
	 * Retrieve, and return ViewPointResource identified by supplied URI
	 * 
	 * @param viewpointURI
	 * @return
	 */
	public ViewPointResource getViewPointResource(String viewpointURI) {
		return map.get(viewpointURI);
	}

	/**
	 * Retrieve, load and return ViewPoint identified by supplied URI
	 * 
	 * @param viewpointURI
	 * @return
	 */
	public ViewPoint getViewPoint(String viewpointURI) {
		// System.out.println("viewpointURI=" + viewpointURI);
		// System.out.println("res=" + getViewPointResource(viewpointURI));
		/*try {
			getViewPointResource(viewpointURI).loadResourceData(null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceLoadingCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlexoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if (getViewPointResource(viewpointURI) != null) {
			return getViewPointResource(viewpointURI).getViewPoint();
		}
		return null;
	}

	/**
	 * Retrieve, load and return ViewPoint identified by supplied URI
	 * 
	 * @param viewpointURI
	 * @return
	 */
	public VirtualModel getVirtualModel(String virtualModelURI) {
		String viewPointURI = virtualModelURI.substring(0, virtualModelURI.lastIndexOf("/"));
		ViewPoint vp = getViewPoint(viewPointURI);
		if (vp != null) {
			return vp.getVirtualModelNamed(virtualModelURI.substring(virtualModelURI.lastIndexOf("/") + 1));
		}
		logger.warning("Cannot find virtual model:" + virtualModelURI + " (searched in viewpoint " + vp + ")");
		return null;
	}

	/**
	 * Return all viewpoints contained in this library<br>
	 * No consideration is performed on underlying organization structure
	 * 
	 * @return
	 */
	public Collection<ViewPointResource> getViewPoints() {
		return map.values();
	}

	/**
	 * Return all loaded viewpoint in the current library
	 */
	public Collection<ViewPoint> getLoadedViewPoints() {
		Vector<ViewPoint> returned = new Vector<ViewPoint>();
		for (ViewPointResource vpRes : getViewPoints()) {
			if (vpRes.isLoaded()) {
				returned.add(vpRes.getViewPoint());
			}
		}
		return returned;
	}

	/**
	 * Register supplied ViewPointResource in this library
	 * 
	 * @param vpRes
	 * @return
	 */
	public ViewPointResource registerViewPoint(ViewPointResource vpRes) {
		String uri = vpRes.getURI();
		map.put(uri, vpRes);
		setChanged();
		notifyObservers(new ViewPointRegistered(vpRes));
		return vpRes;
	}

	/**
	 * UnRegister supplied ViewPointResource in this library
	 * 
	 * @param vpRes
	 * @return
	 */
	public ViewPointResource unregisterViewPoint(ViewPointResource vpRes) {

		// Unregister the viewpoint resource from the viewpoint library
		for (Iterator<Map.Entry<String, ViewPointResource>> i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry<String, ViewPointResource> entry = i.next();
			if ((entry.getValue().equals(vpRes))) {
				i.remove();
			}
		}

		// Unregister the viewpoint resource from the viewpoint repository
		FMLTechnologyAdapter vmTA = getTechnologyAdapterService().getTechnologyAdapter(FMLTechnologyAdapter.class);
		List<FlexoResourceCenter> resourceCenters = getResourceCenterService().getResourceCenters();
		for (FlexoResourceCenter rc : resourceCenters) {
			ViewPointRepository vprfb = (ViewPointRepository) rc.getRepository(ViewPointRepository.class, vmTA);
			if ((vprfb != null) && (vprfb.getAllResources().contains(vpRes))) {
				vprfb.unregisterResource(vpRes);
			}
		}
		setChanged();
		return vpRes;
	}

	public FlexoConcept getFlexoConcept(String flexoConceptURI) {
		if (flexoConceptURI.indexOf("#") > -1) {
			String virtualModelURI = flexoConceptURI.substring(0, flexoConceptURI.indexOf("#"));
			String flexoConceptName = flexoConceptURI.substring(flexoConceptURI.indexOf("#") + 1);
			VirtualModel vm = getVirtualModel(virtualModelURI);
			if (vm != null) {
				return vm.getFlexoConcept(flexoConceptName);
			}
			logger.warning("Cannot find virtual model " + virtualModelURI + " while searching flexo concept:" + flexoConceptURI + " ("
					+ flexoConceptName + ")");
		}
		logger.warning("Cannot find flexo concept:" + flexoConceptURI);
		return null;
	}

	public FlexoBehaviour getFlexoBehaviour(String editionSchemeURI) {
		if (editionSchemeURI.lastIndexOf(".") > -1) {
			String flexoConceptURI = editionSchemeURI.substring(0, editionSchemeURI.lastIndexOf("."));
			FlexoConcept ep = getFlexoConcept(flexoConceptURI);
			if (ep != null) {
				return ep.getFlexoBehaviour(editionSchemeURI.substring(editionSchemeURI.lastIndexOf(".") + 1));
			}
		}
		logger.warning("Cannot find edition scheme:" + editionSchemeURI);
		return null;
	}

	@Override
	public Collection<ViewPoint> getEmbeddedValidableObjects() {
		return getLoadedViewPoints();
	}

	@Override
	public void receiveNotification(FlexoService caller, ServiceNotification notification) {
		if (caller instanceof FlexoResourceCenterService) {
			if (notification instanceof ResourceCenterAdded) {
				FlexoResourceCenter newRC = ((ResourceCenterAdded) notification).getAddedResourceCenter();
				// A new resource center has just been referenced, initialize it related to viewpoint exploring
				// newRC.initialize(this);

				getPropertyChangeSupport().firePropertyChange("getResourceCenters()", null, newRC);
			}
			if (notification instanceof ResourceCenterRemoved) {
				FlexoResourceCenter newRC = ((ResourceCenterRemoved) notification).getRemovedResourceCenter();
				// A new resource center has just been referenced, initialize it related to viewpoint exploring
				// newRC.initialize(this);

				getPropertyChangeSupport().firePropertyChange("getResourceCenters()", null, newRC);
			}
			/*if (notification instanceof ResourceCenterRemoved) {
				FileSystemBasedResourceCenter newRC = (FileSystemBasedResourceCenter) ((ResourceCenterRemoved) notification)
						.getRemovedResourceCenter();

				// A resource center must be been dereferenced
				ViewPointRepository vpr = newRC.getViewPointRepository();
				for (ViewPointResource vpR : vpr.getAllResources()) {
					if (((FileSystemBasedResourceCenter) vpr.getResourceCenter()).getResource(vpR.getURI()) != null) {
						vpR.unloadResourceData();
						unregisterViewPoint(vpR);
						vpr.unregisterResource(vpR);
					}
				}
				vpr.delete();

			}*/
		}
	}

	@Override
	public void register(FlexoServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	@Override
	public FlexoServiceManager getServiceManager() {
		return serviceManager;
	}

	public FlexoResourceCenterService getResourceCenterService() {
		return getServiceManager().getService(FlexoResourceCenterService.class);
	}

	public TechnologyAdapterService getTechnologyAdapterService() {
		return getServiceManager().getService(TechnologyAdapterService.class);
	}

	public List<FlexoResourceCenter> getResourceCenters() {
		return getResourceCenterService().getResourceCenters();
	}

	@Override
	public void initialize() {
		if (getResourceCenterService() != null) {
			// At initialization, initialize all already existing FlexoResourceCenter with this ViewPointLibrary
			// Some FlexoResourceCenter may have already initialized, the goal is here to register in ViewPointLibrary any ViewPoint already
			// found
			FMLTechnologyAdapter vmTA = getTechnologyAdapterService().getTechnologyAdapter(FMLTechnologyAdapter.class);
			for (FlexoResourceCenter rc : getResourceCenters()) {
				// Register Viewpoint viewpoint resources
				ViewPointRepository vprfb = (ViewPointRepository) rc.getRepository(ViewPointRepository.class, vmTA);
				for (ViewPointResource vpRes : vprfb.getAllResources()) {
					vpRes.setViewPointLibrary(this);
					registerViewPoint(vpRes);
				}
			}
		}
	}

	public ViewPointValidationModel getViewPointValidationModel() {
		if (viewPointValidationModel == null && serviceManager != null) {
			try {
				viewPointValidationModel = new ViewPointValidationModel(serviceManager.getTechnologyAdapterService());
			} catch (ModelDefinitionException e) {
				e.printStackTrace();
			}
		}
		return viewPointValidationModel;
	}

	/*public void delete(ViewPoint viewPoint) {
		logger.info("Remove viewpoint " + viewPoint);
		unregisterViewPoint(viewPoint.getResource());
	}*/

}

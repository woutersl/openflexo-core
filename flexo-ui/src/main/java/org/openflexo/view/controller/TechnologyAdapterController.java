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
package org.openflexo.view.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.foundation.viewpoint.FlexoBehaviour;
import org.openflexo.foundation.viewpoint.FlexoRole;
import org.openflexo.foundation.viewpoint.annotations.FIBPanel;
import org.openflexo.foundation.viewpoint.editionaction.AddFlexoConceptInstance;
import org.openflexo.foundation.viewpoint.editionaction.AddToListAction;
import org.openflexo.foundation.viewpoint.editionaction.ConditionalAction;
import org.openflexo.foundation.viewpoint.editionaction.DeleteAction;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction;
import org.openflexo.foundation.viewpoint.editionaction.FetchRequestIterationAction;
import org.openflexo.foundation.viewpoint.editionaction.IterationAction;
import org.openflexo.foundation.viewpoint.editionaction.MatchFlexoConceptInstance;
import org.openflexo.foundation.viewpoint.editionaction.RemoveFromListAction;
import org.openflexo.foundation.viewpoint.editionaction.SelectFlexoConceptInstance;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.icon.VEIconLibrary;
import org.openflexo.icon.VPMIconLibrary;
import org.openflexo.toolbox.FileResource;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * This class represents a technology-specific controller provided by a {@link TechnologyAdapter}<br>
 * A {@link TechnologyAdapterController} works above conceptual layer provided by a {@link TechnologyAdapter}, and manages all tooling
 * dedicated to technology-specific management of a {@link TechnologyAdapter}<br>
 * This controller makes the bindings between Openflexo controllers/editors layer and the {@link TechnologyAdapter}
 * 
 * @author sylvain
 * 
 */
public abstract class TechnologyAdapterController<TA extends TechnologyAdapter> {

	private static final Logger logger = Logger.getLogger(TechnologyAdapterController.class.getPackage().getName());

	private TechnologyAdapterControllerService technologyAdapterControllerService;

	//private final Map<Class<?>, File> fibPanelsForClasses = new HashMap<Class<?>, File>();

	/**
	 * Returns applicable {@link TechnologyAdapterService}
	 * 
	 * @return
	 */
	public TechnologyAdapterControllerService getTechnologyAdapterControllerService() {
		return technologyAdapterControllerService;
	}

	/**
	 * Sets applicable {@link TechnologyAdapterService}
	 * 
	 * @param technologyAdapterService
	 */
	public void setTechnologyAdapterService(TechnologyAdapterControllerService technologyAdapterControllerService) {
		this.technologyAdapterControllerService = technologyAdapterControllerService;
	}

	/**
	 * Return TechnologyAdapter
	 * 
	 * @return
	 */
	public final TA getTechnologyAdapter() {
		return technologyAdapterControllerService.getServiceManager().getService(TechnologyAdapterService.class)
				.getTechnologyAdapter(getTechnologyAdapterClass());
	}

	/**
	 * Return TechnologyAdapter class
	 * 
	 * @return
	 */
	public abstract Class<TA> getTechnologyAdapterClass();

	public abstract void initializeActions(ControllerActionInitializer actionInitializer);

	public void initialize() {

	}

	/**
	 * Return icon representing underlying technology, required size is 32x32
	 * 
	 * @return
	 */
	public abstract ImageIcon getTechnologyBigIcon();

	/**
	 * Return icon representing underlying technology, required size is 16x16
	 * 
	 * @return
	 */
	public abstract ImageIcon getTechnologyIcon();

	/**
	 * Return icon representing a model of underlying technology
	 * 
	 * @return
	 */
	public abstract ImageIcon getModelIcon();

	/**
	 * Return icon representing a model of underlying technology
	 * 
	 * @return
	 */
	public abstract ImageIcon getMetaModelIcon();

	/**
	 * Return icon representing supplied ontology object
	 * 
	 * @param object
	 * @return
	 */
	public abstract ImageIcon getIconForTechnologyObject(Class<? extends TechnologyObject> objectClass);

	/**
	 * Return icon representing supplied pattern role
	 * 
	 * @param object
	 * @return
	 */
	public abstract ImageIcon getIconForPatternRole(Class<? extends FlexoRole<?>> patternRoleClass);

	/**
	 * Return icon representing supplied edition action
	 * 
	 * @param object
	 * @return
	 */
	public ImageIcon getIconForEditionAction(Class<? extends EditionAction<?, ?>> editionActionClass) {
		if (org.openflexo.foundation.viewpoint.editionaction.DeclarePatternRole.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.DECLARE_PATTERN_ROLE_ICON;
		} else if (org.openflexo.foundation.viewpoint.editionaction.AssignationAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.DECLARE_PATTERN_ROLE_ICON;
		} else if (org.openflexo.foundation.viewpoint.editionaction.ExecutionAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.ACTION_SCHEME_ICON;
		} else if (AddFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(VEIconLibrary.FLEXO_CONCEPT_INSTANCE_ICON, IconLibrary.DUPLICATE);
		} else if (SelectFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(VEIconLibrary.FLEXO_CONCEPT_INSTANCE_ICON, IconLibrary.IMPORT);
		} else if (MatchFlexoConceptInstance.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(VPMIconLibrary.FLEXO_CONCEPT_ICON, IconLibrary.SYNC);
		} else if (AddToListAction.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(VPMIconLibrary.LIST_ICON, IconLibrary.POSITIVE_MARKER);
		} else if (RemoveFromListAction.class.isAssignableFrom(editionActionClass)) {
			return IconFactory.getImageIcon(VPMIconLibrary.LIST_ICON, IconLibrary.NEGATIVE_MARKER);
		} else if (DeleteAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.DELETE_ICON;
		} else if (ConditionalAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.CONDITIONAL_ACTION_ICON;
		} else if (IterationAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.ITERATION_ACTION_ICON;
		} else if (FetchRequestIterationAction.class.isAssignableFrom(editionActionClass)) {
			return VPMIconLibrary.ITERATION_ACTION_ICON;
		}
		return null;

	}

	/**
	 * Return icon representing supplied edition scheme
	 * 
	 * @param object
	 * @return
	 */
	public ImageIcon getIconForFlexoBehaviour(Class<? extends FlexoBehaviour> flexoBehaviourClass) {
		return null;
	}

	public abstract String getWindowTitleforObject(TechnologyObject<TA> object, FlexoController controller);

	/**
	 * Return boolean indicating if this TechnologyAdapter controller service support ModuleView rendering for supplied technology object
	 * 
	 * @param object
	 * @return
	 */
	public abstract boolean hasModuleViewForObject(TechnologyObject<TA> object, FlexoController controller);

	/**
	 * Return a newly created ModuleView for supplied technology object, if this TechnologyAdapter controller service support ModuleView
	 * rendering
	 * 
	 * @param object
	 * @return
	 */
	public abstract ModuleView<?> createModuleViewForObject(TechnologyObject<TA> object, FlexoController controller,
			FlexoPerspective perspective);

	/**
	 * Called when a {@link ModuleView} is about to be displayed
	 * 
	 * @param moduleView
	 * @param controller
	 * @param perspective
	 */
	public void notifyModuleViewDisplayed(ModuleView<?> moduleView, FlexoController controller, FlexoPerspective perspective) {
		System.out.println(">>>>>>> Will display module view for " + moduleView.getRepresentedObject());
	}

	/*public File getFIBPanelForObject(Object anObject) {
		if (anObject != null) {
			return getFIBPanelForClass(anObject.getClass());
		}
		return null;
	}

	public File getFIBPanelForClass(Class<?> aClass) {
		if (aClass == null) {
			return null;
		}
		File returned = fibPanelsForClasses.get(aClass);
		if (returned == null) {
			if (aClass.getAnnotation(FIBPanel.class) != null) {
				File fibPanel = new FileResource(aClass.getAnnotation(FIBPanel.class).value());
				if (fibPanel.exists()) {
					logger.info("Found " + fibPanel);
					fibPanelsForClasses.put(aClass, fibPanel);
					return fibPanel;
				} else {
					logger.warning("Not found " + fibPanel);
					return null;
				}
			}
			if (aClass.getSuperclass() != null) {
				return getFIBPanelForClass(aClass.getSuperclass());
			}
		}
		return returned;
	}*/
	
	
	public File getFIBPanelForObject(Object anObject) {
		if (anObject != null) {
			return getFIBPanelForClass(anObject.getClass());
		}
		return null;
	}

	private final Map<Class<?>, File> fibPanelsForClasses = new HashMap<Class<?>, File>() {
		@Override
		public File get(Object key) {
			if (containsKey(key)) {
				return super.get(key);
			}
			if (key instanceof Class) {
				Class<?> aClass = (Class<?>) key;
				// System.out.println("Searching FIBPanel for " + aClass);
				if (aClass.getAnnotation(FIBPanel.class) != null) {
					// System.out.println("Found annotation " + aClass.getAnnotation(FIBPanel.class));
					File fibPanel = new FileResource(aClass.getAnnotation(FIBPanel.class).value());
					// System.out.println("fibPanelFile=" + fibPanel);
					if (fibPanel.exists()) {
						// logger.info("Found " + fibPanel);
						put(aClass, fibPanel);
						return fibPanel;
					}
				}
				put(aClass, null);
				return null;
			}
			return null;
		}
	};

	public File getFIBPanelForClass(Class<?> aClass) {
		return TypeUtils.objectForClass(aClass, fibPanelsForClasses);
	}


}

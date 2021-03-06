/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
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

package org.openflexo.view;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.fib.FIBLibrary;
import org.openflexo.fib.controller.FIBController;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.fib.model.listener.FIBSelectionListener;
import org.openflexo.fib.view.FIBView;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.GraphicalFlexoObserver;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.rm.Resource;
import org.openflexo.selection.SelectionListener;
import org.openflexo.selection.SelectionManager;
import org.openflexo.view.controller.FlexoController;

/**
 * Default implementation for a FIBView which is synchronized with a {@link SelectionManager}
 * 
 * @author sylvain
 * 
 */
public class SelectionSynchronizedFIBView extends FlexoFIBView implements SelectionListener, GraphicalFlexoObserver, FIBSelectionListener {
	static final Logger logger = Logger.getLogger(SelectionSynchronizedFIBView.class.getPackage().getName());

	public SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, Resource fibResource) {
		this(representedObject, controller, fibResource, false);
		if (controller != null) {
			controller.willLoad(fibResource);
		}
	}

	public SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, Resource fibResource, boolean addScrollBar) {
		this(representedObject, controller, FIBLibrary.instance().retrieveFIBComponent(fibResource), addScrollBar);
	}

	// REMOVED as we should only use Resource everywhere
	/*
		public SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, String fibResourcePath, FlexoProgress progress) {
			this(representedObject, controller, fibResourcePath, false, progress);
		}
		public SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, String fibResourcePath) {
			this(representedObject, controller, fibResourcePath, false, controller != null ? controller.willLoad(fibResourcePath) : null);
		}

		public SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, String fibResourcePath, boolean addScrollBar,
				FlexoProgress progress) {
			this(representedObject, controller, FIBLibrary.instance().retrieveFIBComponent(ResourceLocator.locateResource(fibResourcePath)), addScrollBar, progress);
		}
	 */

	protected SelectionSynchronizedFIBView(Object representedObject, FlexoController controller, FIBComponent fibComponent,
			boolean addScrollBar) {
		super(representedObject, controller, fibComponent, addScrollBar);
		getFIBView().getController().addSelectionListener(this);
		if (controller != null && controller.getSelectionManager() != null) {
			controller.getSelectionManager().addToSelectionListeners(this);
		}
		// Fixed CORE-101 FlexoConceptView does not display FlexoConcept at creation
		// SGU: I don't like this design, but i don't see other solutions unless getting deeply in the code: not enough time yet
		getFIBView().getController().objectAddedToSelection(representedObject);
	}

	@Override
	public void deleteView() {
		FIBView aFibView = getFIBView();
		FIBController aController = null;
		if (aFibView != null) {
			aController = aFibView.getController();
			if (aController != null) {
				aController.removeSelectionListener(this);
			}
		}
		getFlexoController().getSelectionManager().removeFromSelectionListeners(this);
		super.deleteView();
	}

	/**
	 * Adds specified object to selection
	 * 
	 * @param object
	 */
	@Override
	public void fireObjectSelected(FlexoObject object) {
		if (ignoreFiredSelectionEvents) {
			return;
		}
		// logger.info("SELECTED: "+object);
		getFIBView().getController().objectAddedToSelection(getRelevantObject(object));
	}

	/**
	 * Removes specified object from selection
	 * 
	 * @param object
	 */
	@Override
	public void fireObjectDeselected(FlexoObject object) {
		if (ignoreFiredSelectionEvents) {
			return;
		}
		// logger.info("DESELECTED: "+object);
		getFIBView().getController().objectRemovedFromSelection(getRelevantObject(object));
	}

	/**
	 * Clear selection
	 */
	@Override
	public void fireResetSelection() {
		if (ignoreFiredSelectionEvents) {
			return;
		}
		// logger.info("RESET SELECTION");
		getFIBView().getController().selectionCleared();
	}

	/**
	 * Notify that the selection manager is performing a multiple selection
	 */
	@Override
	public void fireBeginMultipleSelection() {
		if (ignoreFiredSelectionEvents) {
			return;
		}
	}

	/**
	 * Notify that the selection manager has finished to perform a multiple selection
	 */
	@Override
	public void fireEndMultipleSelection() {
		if (ignoreFiredSelectionEvents) {
			return;
		}
	}

	public SelectionManager getSelectionManager() {
		if (getFlexoController() != null) {
			return getFlexoController().getSelectionManager();
		}
		return null;
	}

	@Override
	public void selectionChanged(List<Object> selection) {
		if (selection == null) {
			return;
		}
		Vector<FlexoObject> newSelection = new Vector<FlexoObject>();
		for (Object o : selection) {
			if (o instanceof FlexoObject) {
				newSelection.add(getRelevantObject((FlexoObject) o));
			}
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("FlexoFIBView now impose new selection : " + newSelection);
		}
		if (getSelectionManager() != null) {
			ignoreFiredSelectionEvents = true;
			getSelectionManager().setSelectedObjects(newSelection);
			ignoreFiredSelectionEvents = false;
		}
	}

	private boolean ignoreFiredSelectionEvents = false;

	/**
	 * We manage here an indirection with resources: resource data is used instead of resource if resource is loaded
	 * 
	 * @param object
	 * @return
	 */
	private FlexoObject getRelevantObject(FlexoObject object) {
		if (object instanceof FlexoResource<?> && ((FlexoResource<?>) object).isLoaded()) {
			try {
				return (FlexoObject) ((FlexoResource<?>) object).getResourceData(null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ResourceLoadingCancelledException e) {
				e.printStackTrace();
			} catch (FlexoException e) {
				e.printStackTrace();
			}
		}
		return object;
	}
}

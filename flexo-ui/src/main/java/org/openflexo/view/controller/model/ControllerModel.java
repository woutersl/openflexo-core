/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
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

package org.openflexo.view.controller.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;

import org.openflexo.ApplicationContext;
import org.openflexo.foundation.FlexoEditor;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.module.FlexoModule;
import org.openflexo.module.ModuleLoader;
import org.openflexo.project.ProjectLoader;
import org.openflexo.swing.layout.MultiSplitLayout.Node;
import org.openflexo.swing.layout.MultiSplitLayoutTypeAdapterFactory;
import org.openflexo.toolbox.ExtendedSet;
import org.openflexo.toolbox.PropertyChangeListenerRegistrationManager;
import org.openflexo.view.controller.FlexoController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ControllerModel extends ControllerModelObject implements PropertyChangeListener {

	private static final java.util.logging.Logger logger = org.openflexo.logging.FlexoLogger.getLogger(ControllerModel.class.getPackage()
			.getName());

	public static final String RIGHT_VIEW_VISIBLE = "rightViewVisible";
	public static final String LEFT_VIEW_VISIBLE = "leftViewVisible";
	public static final String LOCATIONS = "locations";
	public static final String PERSPECTIVES = "perspectives";
	public static final String EDITORS = "editors";

	public static final String CURRENT_LOCATION = "currentLocation";
	public static final String CURRENT_OBJECT = "currentObject";
	public static final String CURRENT_EDITOR = "currentEditor";
	public static final String CURRENT_PERSPECTIVE = "currentPerspective";

	private static final Location NO_LOCATION = new Location(null, null, null);

	/** Gson instance used to serialize and deserialize layouts of the mainpane multisplitpane */
	private Gson gsonLayout;

	/** Flag that indicates the current state of the left view */
	private boolean leftViewVisible = true;

	/** Flag that indicates the current state of the right view */
	private boolean rightViewVisible = true;

	/** The list of all the available perspectives */
	private final List<FlexoPerspective> perspectives;

	/** List of objects that are represented. This list allows to track observed objects. */
	private final List<FlexoObject> objects;

	/** Set of locations that are opened. This set represents all the currently opened module views. */
	private final ExtendedSet<Location> locations;

	/** The stack of locations that were visited */
	private final Stack<Location> previousHistory;

	/**
	 * The stack of locations that are ahead of the current location. This is only used when the user goes back in history. Whenever the
	 * users bypasses history navigation, this stack is reset
	 */
	private final Stack<Location> nextHistory;

	/** The current location in the history */
	private Location currentLocation = NO_LOCATION;

	/** Internal flag that indicates if we are currently moving forward in the history */
	private boolean isGoingForward = false;

	/** Internal flag that indicates if we are currently moving backward in the history */
	private boolean isGoingBackward = false;

	/** The application context of this controller model */
	private final ApplicationContext context;

	/** The module in which this controller model was created (mainly used to save module specific preferences) */
	private final FlexoModule module;

	/** A property change listener registration manager that keeps track of all the registered {@link PropertyChangeListener} */
	private final PropertyChangeListenerRegistrationManager registrationManager;

	public ControllerModel(ApplicationContext context, FlexoModule module) {
		this.context = context;
		this.module = module;
		registrationManager = new PropertyChangeListenerRegistrationManager();
		if (context.getGeneralPreferences() != null) {
			leftViewVisible = context.getGeneralPreferences().getShowLeftView(module.getShortName());
			rightViewVisible = context.getGeneralPreferences().getShowRightView(module.getShortName());
		}
		registrationManager.new PropertyChangeListenerRegistration(ProjectLoader.PROJECT_OPENED, this, context.getProjectLoader());
		registrationManager.new PropertyChangeListenerRegistration(ProjectLoader.PROJECT_CLOSED, this, context.getProjectLoader());
		objects = new ArrayList<FlexoObject>();
		locations = new ExtendedSet<Location>();
		perspectives = new Vector<FlexoPerspective>();
		previousHistory = new Stack<Location>();
		nextHistory = new Stack<Location>();
	}

	public ModuleLoader getModuleLoader() {
		return context.getModuleLoader();
	}

	public ProjectLoader getProjectLoader() {
		return context.getProjectLoader();
	}

	public FlexoModule getModule() {
		return module;
	}

	@Override
	public void delete() {
		for (FlexoPerspective p : perspectives) {
			p.delete();
		}
		perspectives.clear();
		registrationManager.delete();
		super.delete();
		currentLocation = NO_LOCATION;
	}

	public FlexoController getController() {
		return module.getController();
	}

	/***************
	 * PERSPECTIVE *
	 ***************/

	/**
	 * Returns the current perspective.
	 * 
	 * @return the current perspective
	 */
	public FlexoPerspective getCurrentPerspective() {
		if (currentLocation != null) {
			return currentLocation.getPerspective();
		} else {
			return null;
		}
	}

	public void setCurrentPerspective(FlexoPerspective currentPerspective) {

		// System.out.println("currentLocation=" + currentLocation);
		// System.out.println("currentPerspective=" + currentLocation.getPerspective());
		// System.out.println("currentObject=" + getCurrentObject());
		// System.out.println(">>>>>>>> SWITCHING to " + currentPerspective);

		FlexoObject object = getCurrentObject();
		if (currentPerspective != null) {
			if (object == null || !currentPerspective.hasModuleViewForObject(object)) {
				object = currentPerspective.getDefaultObject(object != null ? object : getCurrentProject());
			}
		}
		setCurrentLocation(getCurrentEditor(), object, currentPerspective);
	}

	public List<FlexoPerspective> getPerspectives() {
		return perspectives;
	}

	public void addToPerspectives(FlexoPerspective perspective) {
		if (perspective == null) {
			return;
		}
		perspectives.add(perspective);
		getPropertyChangeSupport().firePropertyChange(PERSPECTIVES, null, perspective);
		if (getCurrentPerspective() == null) {
			setCurrentPerspective(perspective);
		}
	}

	public void removeFromPerspectives(FlexoPerspective perspective) {
		perspectives.remove(perspective);
		getPropertyChangeSupport().firePropertyChange(PERSPECTIVES, perspective, null);
	}

	/***********
	 * EDITORS *
	 ***********/

	public FlexoEditor getCurrentEditor() {
		// if (requiresProject()) {
		if (currentLocation.getEditor() != null) {
			return currentLocation.getEditor();
		} else {
			return context.getApplicationEditor();
		}
	}

	/*public boolean requiresProject() {
		return module.getModule().requireProject();
	}*/

	public void setCurrentEditor(FlexoEditor currentEditor) {
		if (currentEditor != getCurrentEditor()) {
			if (currentEditor == null || currentEditor.getProject() == null || isSelectableProject(currentEditor.getProject())) {
				Location location = getLastLocationForEditor(currentEditor, null);
				if (location != null && location != NO_LOCATION) {
					setCurrentLocation(location);
				} else {
					FlexoObject object = null;
					if (getCurrentEditor() == null && currentEditor != null && currentEditor.getProject() != null
							&& getCurrentPerspective() != null) {
						object = getCurrentPerspective().getDefaultObject(currentEditor.getProject());
					}
					setCurrentLocation(currentEditor, object, getCurrentPerspective());
				}
			}
		}
	}

	public FlexoProject getCurrentProject() {
		return getCurrentEditor() != null ? getCurrentEditor().getProject() : null;
	}

	public void setCurrentProject(FlexoProject project) {
		setCurrentEditor(project != null ? context.getProjectLoader().getEditorForProject(project) : null);
	}

	public boolean isSelectableProject(FlexoProject project) {
		return context.getProjectLoader().getRootProjects().contains(project);
	}

	/**********************
	 * NAVIGATION HISTORY *
	 **********************/

	public FlexoObject getCurrentObject() {
		return currentLocation != null ? currentLocation.getObject() : null;
	}

	public void setCurrentObject(FlexoObject object) {
		// Little block to change the currentPerspective if the
		// current perspective can't handle this object
		FlexoPerspective perspective = getCurrentPerspective();
		if (object != null && !perspective.hasModuleViewForObject(object)) {
			for (FlexoPerspective p : getPerspectives()) {
				if (p == null) {
					continue;
				}
				if (p.hasModuleViewForObject(object)) {
					perspective = p;
					break;
				}
			}
		}
		setCurrentLocation(getCurrentEditor(), object, perspective);
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location location) {
		if (location != null && location.equals(getCurrentLocation())) {
			return;
		} else if (location != null) {
			setCurrentLocation(location.getEditor(), location.getObject(), location.getPerspective());
		}
	}

	public Set<Location> getLocations() {
		return Collections.unmodifiableSet(locations);
	}

	/**
	 * Adds the specified location to the set of locations if it is not yet in the set. The method returns a location which is equal to the
	 * specified location and which is in the set (ie, if the location was not yet in the set, it returns the specified location, else it
	 * returns the locations that was previously contained).
	 * 
	 * @param location
	 *            the location to add.
	 * @return the location which is contained in the set.
	 */
	public Location addToLocations(Location location) {
		Location existing = locations.get(location);
		if (existing == null) {
			locations.add(existing = location);
			getPropertyChangeSupport().firePropertyChange(LOCATIONS, null, existing = location);
		}
		return existing;
	}

	public boolean removeFromLocations(Location location) {
		boolean removed = locations.remove(location);
		if (removed) {
			if (location != null && location.equals(currentLocation)) {
				setCurrentLocation(getLastLocationForEditor(getCurrentEditor(), getCurrentPerspective()));
			}
			getPropertyChangeSupport().firePropertyChange(LOCATIONS, location, null);
		}
		return removed;
	}

	public void setCurrentLocation(FlexoEditor editor, FlexoObject object, FlexoPerspective perspective) {
		if (isDeleted()) {
			return;
		}
		/*if (editor == null) {
			editor = getCurrentEditor();
		}*/
		if (perspective == null) {
			perspective = getCurrentPerspective();
		}
		if (!isGoingForward && !isGoingBackward) {
			if (currentLocation != null && currentLocation != NO_LOCATION) {
				previousHistory.push(currentLocation);
			}
			nextHistory.clear();
		}
		if (object != null) {
			if (getCurrentObject() != object) {
				if (!objects.contains(object)) {
					registrationManager.new PropertyChangeListenerRegistration(object.getDeletedProperty(), this, object);
					objects.add(object);
				}
			}
		}
		Location old = currentLocation;

		// System.out.println("******* Adding location editor=" + editor + " object=" + object + " perspective=" + perspective);
		currentLocation = addToLocations(new Location(editor, object, perspective));
		notifyLocationChange(old, currentLocation);
	}

	private void notifyLocationChange(Location old, Location newLocation) {

		if (old == null || old.getEditor() != currentLocation.getEditor()) {
			getPropertyChangeSupport()
					.firePropertyChange(CURRENT_EDITOR, old != null ? old.getEditor() : null, currentLocation.getEditor());
		}
		if (old == null || old.getPerspective() != currentLocation.getPerspective()) {
			getPropertyChangeSupport().firePropertyChange(CURRENT_PERSPECTIVE, old != null ? old.getPerspective() : null,
					currentLocation.getPerspective());
		}
		if (old == null || old.getObject() != currentLocation.getObject()) {
			getPropertyChangeSupport()
					.firePropertyChange(CURRENT_OBJECT, old != null ? old.getObject() : null, currentLocation.getObject());
		}
		getPropertyChangeSupport().firePropertyChange(CURRENT_LOCATION, old, currentLocation);
	}

	public Stack<Location> getNextHistory() {
		return nextHistory;
	}

	public Stack<Location> getPreviousHistory() {
		return previousHistory;
	}

	public boolean canGoForward() {
		return nextHistory.size() > 0;
	}

	public boolean canGoBack() {
		return previousHistory.size() > 0;
	}

	public boolean canGoUp() {
		return getCurrentObject() != null && getParent(getCurrentObject()) != null;
	}

	private Location getLastLocationForEditor(FlexoEditor editor, FlexoPerspective perspective) {
		if (perspective != null) {
			for (int i = previousHistory.size() - 1; i > -1; i--) {
				Location location = previousHistory.get(i);
				if ((editor == null || location.getEditor() == editor) && location.getPerspective() == perspective
						&& locations.contains(location)) {
					return location;
				}
			}
			for (Location location : nextHistory) {
				if ((editor == null || location.getEditor() == editor) && location.getPerspective() == perspective
						&& locations.contains(location)) {
					return location;
				}
			}
		}
		for (int i = previousHistory.size() - 1; i > -1; i--) {
			Location location = previousHistory.get(i);
			if (editor == null || location.getEditor() == editor && locations.contains(location)) {
				return location;
			}
		}
		for (Location location : nextHistory) {
			if (editor == null || location.getEditor() == editor && locations.contains(location)) {
				return location;
			}
		}
		return NO_LOCATION;
	}

	private FlexoObject getParent(FlexoObject object) {
		logger.warning("Please reimplement this (getParent(FlexoObject)");
		return null;
	}

	public void historyBack() {
		if (canGoBack()) {
			isGoingBackward = true;
			try {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Back to " + previousHistory.peek());
				}
				nextHistory.push(currentLocation);
				Location nextLocation = previousHistory.pop();
				setCurrentLocation(nextLocation);
			} finally {
				isGoingBackward = false;
			}
		}
	}

	public void historyForward() {
		if (canGoForward()) {
			isGoingForward = true;
			try {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Forward to " + nextHistory.peek());
				}
				previousHistory.push(currentLocation);
				Location nextLocation = nextHistory.pop();
				setCurrentLocation(nextLocation);
			} finally {
				isGoingForward = false;
			}
		}
	}

	public void goUp() {
		if (canGoUp()) {
			setCurrentObject(getParent(getCurrentObject()));
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == getProjectLoader()) {
			if (evt.getPropertyName().equals(ProjectLoader.PROJECT_OPENED)) {
				FlexoProject project = (FlexoProject) evt.getNewValue();
				if (getCurrentPerspective() != null) {
					FlexoObject object = getCurrentPerspective().getDefaultObject(project);
					setCurrentLocation(getProjectLoader().getEditorForProject(project), object, getCurrentPerspective());
				} else {
					setCurrentProject(project);
				}
			} else if (evt.getPropertyName().equals(ProjectLoader.PROJECT_CLOSED)) {
				handleProjectRemoval((FlexoProject) evt.getOldValue());
				setCurrentProject(null);
			}
		} else if (evt.getOldValue() instanceof FlexoObject
				&& evt.getPropertyName().equals(((FlexoObject) evt.getOldValue()).getDeletedProperty())) {
			handleObjectDeletion((FlexoObject) evt.getOldValue());
		}
	}

	private void handleProjectRemoval(FlexoProject removedProject) {
		updateHistoryForProjectRemoval(previousHistory, removedProject);
		updateHistoryForProjectRemoval(nextHistory, removedProject);
		for (Location location : new ArrayList<Location>(locations)) {
			if (location.getEditor() != null && location.getEditor().getProject() == removedProject) {
				removeFromLocations(location);
			}
		}
	}

	private void updateHistoryForProjectRemoval(Stack<Location> history, FlexoProject removedProject) {
		Iterator<Location> i = history.iterator();
		while (i.hasNext()) {
			Location hl = i.next();
			if (hl.getEditor() != null && hl.getEditor().getProject() == removedProject) {
				i.remove();
			}
		}
	}

	private void handleObjectDeletion(FlexoObject deletedObject) {
		while (objects.remove(deletedObject)) {
			;
		}
		updateHistoryForDeletedObject(previousHistory, deletedObject);
		updateHistoryForDeletedObject(nextHistory, deletedObject);
		for (Location location : new ArrayList<Location>(locations)) {
			if (location.getObject() == deletedObject) {
				removeFromLocations(location);
			}
		}
		if (currentLocation != null && currentLocation.getObject() == deletedObject) {
			if (canGoBack()) {
				historyBack();
			} else {
				setCurrentObject(null);
			}
		}
		registrationManager.removeListener(deletedObject.getDeletedProperty(), this, deletedObject);
	}

	private void updateHistoryForDeletedObject(Stack<Location> history, FlexoObject deletedObject) {
		Iterator<Location> i = history.iterator();
		while (i.hasNext()) {
			Location hl = i.next();
			if (hl.getObject() == deletedObject) {
				i.remove();
			}
		}
	}

	/**********
	 * LAYOUT *
	 **********/

	public Node getLayoutForPerspective(FlexoPerspective perspective) {

		String layout = null;
		if (context.getGeneralPreferences() != null) {
			layout = context.getGeneralPreferences().getLayoutFor(getModule().getShortName() + perspective.getName());
		}
		if (layout != null) {
			// System.out.println("Parsing layout: " + layout);
			return getLayoutFromString(layout);
		} else {
			return null;
		}
	}

	private Node getLayoutFromString(String layout) {
		return getGsonLayout().fromJson(layout, Node.class);
	}

	public void setLayoutForPerspective(FlexoPerspective perspective, Node layout) {
		context.getGeneralPreferences().setLayoutFor(getGsonLayout().toJson(layout), getModule().getShortName() + perspective.getName());
		context.getPreferencesService().savePreferences();
	}

	private Gson getGsonLayout() {
		if (gsonLayout == null) {
			GsonBuilder builder = new GsonBuilder().registerTypeAdapterFactory(new MultiSplitLayoutTypeAdapterFactory());
			gsonLayout = builder.create();
		}
		return gsonLayout;
	}

	/**************
	 * VISIBILITY *
	 **************/

	public boolean isLeftViewVisible() {
		return leftViewVisible;
	}

	public void setLeftViewVisible(boolean leftViewVisible) {
		this.leftViewVisible = leftViewVisible;
		context.getGeneralPreferences().setShowLeftView(getModule().getShortName(), leftViewVisible);
		context.getPreferencesService().savePreferences();
		getPropertyChangeSupport().firePropertyChange(LEFT_VIEW_VISIBLE, !leftViewVisible, leftViewVisible);
	}

	public boolean isRightViewVisible() {
		return rightViewVisible;
	}

	public void setRightViewVisible(boolean rightViewVisible) {
		this.rightViewVisible = rightViewVisible;
		context.getGeneralPreferences().setShowRightView(getModule().getShortName(), rightViewVisible);
		context.getPreferencesService().savePreferences();
		getPropertyChangeSupport().firePropertyChange(RIGHT_VIEW_VISIBLE, !rightViewVisible, rightViewVisible);
	}

}

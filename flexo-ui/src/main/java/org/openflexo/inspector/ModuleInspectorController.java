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

package org.openflexo.inspector;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import org.openflexo.components.widget.FIBIndividualSelector;
import org.openflexo.components.widget.FIBPropertySelector;
import org.openflexo.connie.BindingVariable;
import org.openflexo.connie.DataBinding;
import org.openflexo.connie.type.TypeUtils;
import org.openflexo.fib.model.FIBCheckBox;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.fib.model.FIBComponent.HorizontalScrollBarPolicy;
import org.openflexo.fib.model.FIBComponent.VerticalScrollBarPolicy;
import org.openflexo.fib.model.FIBContainer;
import org.openflexo.fib.model.FIBCustom;
import org.openflexo.fib.model.FIBCustom.FIBCustomAssignment;
import org.openflexo.fib.model.FIBLabel;
import org.openflexo.fib.model.FIBModelFactory;
import org.openflexo.fib.model.FIBNumber;
import org.openflexo.fib.model.FIBNumber.NumberType;
import org.openflexo.fib.model.FIBPanel.Layout;
import org.openflexo.fib.model.FIBTab;
import org.openflexo.fib.model.FIBTextArea;
import org.openflexo.fib.model.FIBTextField;
import org.openflexo.fib.model.FIBWidget;
import org.openflexo.fib.model.TwoColsLayoutConstraints;
import org.openflexo.fib.model.TwoColsLayoutConstraints.TwoColsLayoutLocation;
import org.openflexo.fib.utils.FIBInspector;
import org.openflexo.fib.utils.InspectorGroup;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptInstanceType;
import org.openflexo.foundation.fml.ViewPointLocalizedDictionary;
import org.openflexo.foundation.fml.inspector.CheckboxInspectorEntry;
import org.openflexo.foundation.fml.inspector.ClassInspectorEntry;
import org.openflexo.foundation.fml.inspector.DataPropertyInspectorEntry;
import org.openflexo.foundation.fml.inspector.IndividualInspectorEntry;
import org.openflexo.foundation.fml.inspector.InspectorEntry;
import org.openflexo.foundation.fml.inspector.IntegerInspectorEntry;
import org.openflexo.foundation.fml.inspector.ObjectPropertyInspectorEntry;
import org.openflexo.foundation.fml.inspector.PropertyInspectorEntry;
import org.openflexo.foundation.fml.inspector.TextAreaInspectorEntry;
import org.openflexo.foundation.fml.inspector.TextFieldInspectorEntry;
import org.openflexo.foundation.fml.rt.FlexoConceptInstance;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.task.Progress;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.rm.Resource;
import org.openflexo.rm.ResourceLocator;
import org.openflexo.toolbox.StringUtils;
import org.openflexo.view.controller.FlexoController;

/**
 * Represents the controller for all inspectors managed in the context of a module<br>
 * It is connected with one or many FIBInspectorPanels sharing the same selection. In particular, manage the inspector dialog of the module.
 * 
 * @author sylvain
 * 
 */
public class ModuleInspectorController extends Observable implements Observer {

	private static final String CONTROLLER_EDITABLE_BINDING = "controller.flexoController.isEditable(data)";

	static final Logger logger = Logger.getLogger(ModuleInspectorController.class.getPackage().getName());

	private final FIBInspectorDialog inspectorDialog;

	private final FlexoController flexoController;

	private final Map<FlexoConcept, FIBInspector> flexoConceptInspectors;
	// private final Map<Class<?>, FIBInspector> inspectors;

	private FIBInspector currentInspector = null;

	private final InspectorGroup coreInspectorGroup;
	private final List<InspectorGroup> inspectorGroups;

	private Object currentInspectedObject = null;

	/*public static FIBModelFactory INSPECTOR_FACTORY;

	static {
		try {
			INSPECTOR_FACTORY = new FIBModelFactory(FIBInspector.class);
		} catch (ModelDefinitionException e1) {
			e1.printStackTrace();
		}
	}*/

	public ModuleInspectorController(final FlexoController flexoController) {
		this.flexoController = flexoController;

		inspectorGroups = new ArrayList<InspectorGroup>();
		coreInspectorGroup = new InspectorGroup(ResourceLocator.locateResource("Inspectors/COMMON"));
		inspectorGroups.add(coreInspectorGroup);

		flexoConceptInspectors = new Hashtable<FlexoConcept, FIBInspector>();
		inspectorDialog = new FIBInspectorDialog(this);
		Boolean visible = null;
		if (flexoController.getApplicationContext().getGeneralPreferences() != null) {
			visible = flexoController.getApplicationContext().getGeneralPreferences().getInspectorVisible();
		}
		inspectorDialog.setVisible(visible == null || visible);
		inspectorDialog.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				flexoController.getApplicationContext().getGeneralPreferences().setInspectorVisible(true);
				flexoController.getApplicationContext().getPreferencesService().savePreferences();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				flexoController.getApplicationContext().getGeneralPreferences().setInspectorVisible(false);
				flexoController.getApplicationContext().getPreferencesService().savePreferences();
			};
		});

		// Resource inspectorsDir = ResourceLocator.locateResource("Inspectors/COMMON");
		// loadDirectory(inspectorsDir);
	}

	public InspectorGroup getCoreInspectorGroup() {
		return coreInspectorGroup;
	}

	/*private InspectorGroup loadInspectorGroup(Resource inspectorGroupFolder, InspectorGroup... parentInspectorGroups) {
		InspectorGroup returned = new InspectorGroup(inspectorGroupFolder) {
			@Override
			public void progress(Resource f, FIBInspector inspector) {
				super.progress(f, inspector);
				appendVisibleFor(inspector);
				appendEditableCondition(inspector);
				Progress.progress(FlexoLocalization.localizedForKey("loaded_inspector") + " " + inspector.getDataClass().getSimpleName());
			}
		};
		return returned;
	}*/

	public InspectorGroup loadDirectory(Resource inspectorsDirectory, InspectorGroup... parentInspectorGroups) {
		InspectorGroup newInspectorGroup = new InspectorGroup(inspectorsDirectory, parentInspectorGroups) {
			@Override
			public void progress(Resource f, FIBInspector inspector) {
				super.progress(f, inspector);
				appendVisibleFor(inspector);
				appendEditableCondition(inspector);
				Progress.progress(FlexoLocalization.localizedForKey("loaded_inspector") + " " + inspector.getDataClass().getSimpleName());
			}
		};
		inspectorGroups.add(newInspectorGroup);
		return newInspectorGroup;
	}

	public FlexoController getFlexoController() {
		return flexoController;
	}

	/*public void loadDirectory(Resource dir) {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Loading directory: " + dir);
		}
		if (dir != null) {
			for (Resource f : dir.getContents(Pattern.compile(".*[.]inspector"))) {

				logger.fine("Loading: " + f.getURI());
				FIBInspector inspector = (FIBInspector) FIBLibrary.instance().retrieveFIBComponent(f, false, INSPECTOR_FACTORY);
				if (inspector != null) {
					appendVisibleFor(inspector);
					appendEditableCondition(inspector);
					if (inspector.getDataClass() != null) {
						// try {
						inspectors.put(inspector.getDataClass(), inspector);
						if (logger.isLoggable(Level.FINE)) {
							logger.fine("Loaded inspector: " + f.getRelativePath() + " for " + inspector.getDataClass());
						}
						Progress.progress(FlexoLocalization.localizedForKey("loaded_inspector") + " "
								+ inspector.getDataClass().getSimpleName());
					}
				} else {
					logger.warning("Not found: " + f.getURI());
				}
			}

			for (FIBInspector inspector : inspectors.values()) {
				// logger.info("Merging inspector: " + inspector);
				inspector.appendSuperInspectors(this);
			}

			for (FIBInspector inspector : inspectors.values()) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Initialized inspector for " + inspector.getDataClass());
				}
			}

			setChanged();
			notifyObservers(new NewInspectorsLoaded());
		}
	}*/

	private void appendEditableCondition(FIBComponent component) {
		if (component instanceof FIBWidget) {
			FIBWidget widget = (FIBWidget) component;
			DataBinding<Boolean> enable = widget.getEnable();
			if (enable != null && enable.isValid()) {
				widget.setEnable(new DataBinding<Boolean>(enable.toString() + " & " + CONTROLLER_EDITABLE_BINDING));
			} else {
				widget.setEnable(new DataBinding<Boolean>(CONTROLLER_EDITABLE_BINDING));
			}
		} else if (component instanceof FIBContainer) {
			for (FIBComponent child : ((FIBContainer) component).getSubComponents()) {
				appendEditableCondition(child);
			}
		}
	}

	private void appendVisibleFor(FIBComponent component) {
		/*String visibleForParam = component.getParameter("visibleFor");
		if (visibleForParam != null) {
			String[] s = visibleForParam.split("[;,\"]");
			if (s.length > 0) {
				UserType userType = UserType.getCurrentUserType();
				boolean ok = false;
				for (String string : s) {
					ok |= userType.getName().equalsIgnoreCase(string);
					ok |= userType.getIdentifier().equalsIgnoreCase(string);
					if (ok) {
						break;
					}
				}
				if (!ok) {
					component.setVisible(new DataBinding<Boolean>("false"));
				}
			}
		}*/
		if (component instanceof FIBContainer) {
			for (FIBComponent child : ((FIBContainer) component).getSubComponents()) {
				appendVisibleFor(child);
			}
		}
	}

	/**
	 * Return the inspector matching supplied objectClass<br>
	 * The research is performed on all inspector groups declared in this module<br>
	 * In case of multiple possibilities, the most specialized inspector is returned.
	 * 
	 * @param objectClass
	 * @return
	 */
	public FIBInspector inspectorForClass(Class<?> objectClass) {
		if (objectClass == null) {
			return null;
		}

		Map<Class<?>, FIBInspector> potentialInspectors = new HashMap<Class<?>, FIBInspector>();
		for (InspectorGroup inspectorGroup : inspectorGroups) {
			FIBInspector inspector = inspectorGroup.inspectorForClass(objectClass);
			if (inspector != null) {
				potentialInspectors.put(inspector.getDataClass(), inspector);
			}
		}

		if (potentialInspectors.size() == 0) {
			logger.warning("Could not find inspector for " + objectClass);
			return null;
		}

		Class<?> mostSpecializedClass = TypeUtils.getMostSpecializedClass(potentialInspectors.keySet());

		FIBInspector returned = potentialInspectors.get(mostSpecializedClass);

		// System.out.println("Pour la classe " + objectClass + " je retourne:");
		// System.out.println(getFactory().stringRepresentation(returned));

		return returned;
	}

	/**
	 * Return all inspectors matching supplied objectClass<br>
	 * The research is performed on all inspector groups declared in this module<br>
	 * 
	 * @param objectClass
	 * @return
	 */
	public List<FIBInspector> inspectorsForClass(Class<?> objectClass) {
		if (objectClass == null) {
			return null;
		}

		List<FIBInspector> returned = new ArrayList<FIBInspector>();

		for (InspectorGroup inspectorGroup : inspectorGroups) {
			for (FIBInspector inspector : inspectorGroup.inspectorsForClass(objectClass)) {
				if (!returned.contains(inspector)) {
					returned.add(inspector);
				}
			}
		}

		return returned;

	}

	public FIBInspector inspectorForObject(Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof FlexoConceptInstance) {
			return inspectorForFlexoConceptInstance(((FlexoConceptInstance) object).getFlexoConcept());

		}

		return inspectorForClass(object.getClass());
	}

	private FIBInspector inspectorForFlexoConceptInstance(FlexoConcept concept) {
		if (concept == null) {
			return null;
		}
		FIBInspector returned = flexoConceptInspectors.get(concept);
		if (returned != null) {
			return returned;
		} else {
			returned = inspectorForClass(FlexoConceptInstance.class);
			returned = (FIBInspector) returned.cloneObject();
			// FIBTab basicTab = (FIBTab) returned.getTabPanel().getChildAt(0);
			// System.out.println("basicTab=" + basicTab);
			// returned.removeFromSubComponents(basicTab);
			appendFlexoConceptInspectors(concept, returned);
			flexoConceptInspectors.put(concept, returned);
			return returned;
		}
	}

	/*protected FIBInspector inspectorForClass(Class<?> aClass) {
		if (aClass == null) {
			return null;
		}
		FIBInspector returned = inspectors.get(aClass);
		if (returned != null) {
			return returned;
		} else {
			Class<?> superclass = aClass.getSuperclass();
			if (superclass != null) {
				returned = inspectors.get(aClass);
				if (returned != null) {
					return returned;
				} else {
					for (Class<?> superInterface : aClass.getInterfaces()) {
						returned = inspectors.get(superInterface);
						if (returned != null) {
							return returned;
						}
					}
					return inspectorForClass(superclass);
				}
			}
		}
		List<Class<?>> matchingClasses = new ArrayList<Class<?>>();
		for (Class<?> cl : inspectors.keySet()) {
			if (cl.isAssignableFrom(aClass)) {
				matchingClasses.add(cl);
			}
		}
		if (matchingClasses.size() > 0) {
			return inspectors.get(TypeUtils.getMostSpecializedClass(matchingClasses));
		}
		return null;
	}*/

	/*protected Map<Class<?>, FIBInspector> getInspectors() {
		return inspectors;
	}*/

	public FIBInspectorDialog getInspectorDialog() {
		return inspectorDialog;
	}

	public void refreshComponentVisibility() {
		inspectorDialog.getInspectorPanel().refreshComponentVisibility();
	}

	protected void switchToEmptyContent() {
		// logger.info("switchToEmptyContent()");
		currentInspectedObject = null;
		currentInspector = null;
		setChanged();
		notifyObservers(new EmptySelectionActivated());
	}

	private void switchToMultipleSelection() {
		// logger.info("switchToMultipleSelection()");
		currentInspectedObject = null;
		currentInspector = null;
		setChanged();
		notifyObservers(new MultipleSelectionActivated());
	}

	private void switchToInspector(FIBInspector newInspector/*, boolean updateEPTabs*/) {
		currentInspector = newInspector;
		setChanged();
		notifyObservers(new InspectorSwitching(newInspector/*, updateEPTabs*/));
	}

	private void displayObject(Object object) {
		setChanged();
		notifyObservers(new InspectedObjectChanged(object));
	}

	/**
	 * Returns boolean indicating if inspection change
	 * 
	 * @param object
	 * @return
	 */
	public boolean inspectObject(Object object) {
		if (object == currentInspectedObject) {
			return false;
		}

		// logger.info("ModuleInspectorController: inspectObject with " + object);
		// logger.info("currentInspectedObject=" + currentInspectedObject);

		currentInspectedObject = object;

		FIBInspector newInspector = inspectorForObject(object);

		if (newInspector == null) {
			logger.warning("No inspector for " + object);
			switchToEmptyContent();
		} else {
			/*boolean updateEPTabs = false;
			if (object instanceof FlexoConceptInstance) {
				updateEPTabs = newInspector.updateFlexoConceptInstanceInspector((FlexoConceptInstance) object);
			} else if (object instanceof FlexoObject) {
				updateEPTabs = newInspector.updateFlexoObjectInspector((FlexoObject) object);
			}*/
			if (newInspector != currentInspector /*|| updateEPTabs*/) {
				switchToInspector(newInspector/*, updateEPTabs*/);
			}
			displayObject(object);
		}

		return true;
	}

	public void resetInspector() {
		switchToEmptyContent();
	}

	@Override
	public void update(Observable o, Object notification) {
		if (notification instanceof InspectorSelection) {
			InspectorSelection inspectorSelection = (InspectorSelection) notification;
			if (inspectorSelection instanceof EmptySelection) {
				switchToEmptyContent();
			} else if (inspectorSelection instanceof MultipleSelection) {
				switchToMultipleSelection();
			} else if (inspectorSelection instanceof UniqueSelection) {
				inspectObject(((UniqueSelection) inspectorSelection).getInspectedObject());
			}
		}

		// Reforward notification to all in inspector panels
		setChanged();
		notifyObservers(notification);
	}

	public static class NewInspectorsLoaded {

	}

	public static class EmptySelectionActivated {

	}

	public static class MultipleSelectionActivated {

	}

	public static class InspectorSwitching {
		// private final boolean updateEPTabs;
		private final FIBInspector newInspector;

		public InspectorSwitching(FIBInspector newInspector/*, boolean updateEPTabs*/) {
			this.newInspector = newInspector;
			// this.updateEPTabs = updateEPTabs;
		}

		/*public boolean updateEPTabs() {
			return updateEPTabs;
		}*/

		public FIBInspector getNewInspector() {
			return newInspector;
		}
	}

	public static class InspectedObjectChanged {
		private final Object inspectedObject;

		public InspectedObjectChanged(Object inspectedObject) {
			this.inspectedObject = inspectedObject;
		}

		public Object getInspectedObject() {
			return inspectedObject;
		}
	}

	public void delete() {
		inspectorDialog.delete();
		currentInspectedObject = null;
		currentInspector = null;
	}

	public void appendFlexoConceptInspectors(FlexoConcept concept, FIBInspector inspector) {
		FIBTab newTab = makeFIBTab(concept, inspector);
		inspector.getTabPanel().addToSubComponents(newTab, null, 0);

		/*try {
			logger.info("Getting this "
					+ XMLCoder.encodeObjectWithMapping(this, FIBLibrary.getFIBMapping(), StringEncoder.getDefaultInstance()));
		} catch (InvalidObjectSpecificationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccessorInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateSerializationIdentifierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	private FIBTab makeFIBTab(FlexoConcept flexoConcept, FIBInspector inspector) {
		// logger.info("makeFIBTab " + refIndex + " for " + ep);
		FIBTab newTab = createFIBTabForFlexoConcept(flexoConcept);

		// VERY IMPORTANT
		// We MUST here redefine the type of inspected data
		BindingVariable bv = inspector.getBindingModel().bindingVariableNamed("data");
		if (bv != null && flexoConcept != null) {
			bv.setType(FlexoConceptInstanceType.getFlexoConceptInstanceType(flexoConcept));
		}

		appendInspectorEntries(flexoConcept, newTab);
		newTab.finalizeDeserialization();
		return newTab;
	}

	protected FIBTab createFIBTabForFlexoConcept(FlexoConcept ep) {
		// String epIdentifier = getFlexoConceptIdentifierForEPIReference(ep);
		FIBTab newTab = getFactory().newFIBTab();
		newTab.setTitle(ep.getInspector().getInspectorTitle());
		newTab.setLayout(Layout.twocols);
		newTab.setUseScrollBar(true);
		// newTab.setDataClass(FlexoConceptInstance.class);
		// newTab.setData(new DataBinding("data.flexoConceptReferences.get["+refIndex+"].flexoConceptInstance"));
		// newTab.setData(new DataBinding("data.flexoConceptReferences.firstElement.flexoConceptInstance"));
		newTab.setName(ep.getName() + "Panel");
		return newTab;
	}

	private void appendInspectorEntries(FlexoConcept ep, FIBTab newTab) {
		for (FlexoConcept parentEP : ep.getParentFlexoConcepts()) {
			appendInspectorEntries(parentEP, newTab);
		}
		ViewPointLocalizedDictionary localizedDictionary = ep.getViewPoint().getLocalizedDictionary();
		for (final InspectorEntry entry : ep.getInspector().getEntries()) {
			FIBLabel label = getFactory().newFIBLabel();
			String entryLabel = localizedDictionary.getLocalizedForKeyAndLanguage(entry.getLabel(), FlexoLocalization.getCurrentLanguage());
			if (entryLabel == null) {
				entryLabel = entry.getLabel();
			}
			label.setLabel(entryLabel);
			newTab.addToSubComponents(label, new TwoColsLayoutConstraints(TwoColsLayoutLocation.left, false, false));
			FIBWidget widget = makeWidget(entry, newTab);
			widget.setBindingFactory(entry.getBindingFactory());
			widget.setData(new DataBinding<Object>("data." + entry.getData().toString()));
			widget.setReadOnly(entry.getIsReadOnly());
			/*System.out.println("Widget " + widget + " data=" + entry.getData());
			System.out.println("valid:" + entry.getData().isValid());
			System.out.println("reason=" + entry.getData().invalidBindingReason());
			System.out.println("Widget data " + widget.getData());
			System.out.println("valid:" + widget.getData().isValid());
			System.out.println("reason=" + widget.getData().invalidBindingReason());*/
		}
	}

	private FIBWidget makeWidget(final InspectorEntry entry, FIBTab newTab) {
		if (entry instanceof TextFieldInspectorEntry) {
			FIBTextField tf = getFactory().newFIBTextField();
			tf.setValidateOnReturn(true); // Avoid too many ontologies manipulations
			newTab.addToSubComponents(tf, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return tf;
		} else if (entry instanceof TextAreaInspectorEntry) {
			FIBTextArea ta = getFactory().newFIBTextArea();
			ta.setValidateOnReturn(true); // Avoid to many ontologies manipulations
			ta.setUseScrollBar(true);
			ta.setHorizontalScrollbarPolicy(HorizontalScrollBarPolicy.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			ta.setVerticalScrollbarPolicy(VerticalScrollBarPolicy.VERTICAL_SCROLLBAR_AS_NEEDED);
			newTab.addToSubComponents(ta, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, true));
			return ta;
		} else if (entry instanceof CheckboxInspectorEntry) {
			FIBCheckBox cb = getFactory().newFIBCheckBox();
			newTab.addToSubComponents(cb, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return cb;
		} else if (entry instanceof IntegerInspectorEntry) {
			FIBNumber number = getFactory().newFIBNumber();
			number.setNumberType(NumberType.IntegerType);
			newTab.addToSubComponents(number, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return number;
		} else if (entry instanceof IndividualInspectorEntry) {
			IndividualInspectorEntry individualEntry = (IndividualInspectorEntry) entry;
			FIBCustom individualSelector = getFactory().newFIBCustom();
			individualSelector.setComponentClass(FIBIndividualSelector.class);
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			// component.context = xxx
			FIBCustomAssignment projectAssignment = getFactory().newInstance(FIBCustomAssignment.class);
			projectAssignment.setOwner(individualSelector);
			projectAssignment.setVariable(new DataBinding<Object>("component.project"));
			projectAssignment.setValue(new DataBinding<Object>("data.project"));
			projectAssignment.setMandatory(true);
			individualSelector.addToAssignments(projectAssignment);

			/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector, new DataBinding("component.project"),
					new DataBinding("data.project"), true));*/
			/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector,
					new DataBinding("component.contextOntologyURI"), new DataBinding('"' + individualEntry.getViewPoint()
							.getViewpointOntology().getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return entry.getBindingFactory();
						}
					}, true));*/
			// Quick and dirty hack to configure IndividualSelector: refactor this when new binding model will be in use
			IFlexoOntologyClass conceptClass = null;
			if (individualEntry.getIsDynamicConceptValue()) {
				// conceptClass = classEntry.evaluateConceptValue(action);
				// TODO: implement proper scheme with new binding support
				logger.warning("Please implement me !!!!!!!!!");
			} else {
				conceptClass = individualEntry.getConcept();
			}
			if (conceptClass != null) {
				FIBCustomAssignment conceptClassAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				conceptClassAssignment.setOwner(individualSelector);
				conceptClassAssignment.setVariable(new DataBinding<Object>("component.typeURI"));
				conceptClassAssignment.setValue(new DataBinding('"' + conceptClass.getURI() + '"'));
				conceptClassAssignment.setMandatory(true);
				individualSelector.addToAssignments(conceptClassAssignment);
				/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector, new DataBinding("component.typeURI"),
						new DataBinding('"' + conceptClass.getURI() + '"'), true));*/
			}
			if (StringUtils.isNotEmpty(individualEntry.getRenderer())) {
				FIBCustomAssignment rendererAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				rendererAssignment.setOwner(individualSelector);
				rendererAssignment.setVariable(new DataBinding<Object>("component.renderer"));
				rendererAssignment.setValue(new DataBinding('"' + individualEntry.getRenderer() + '"'));
				rendererAssignment.setMandatory(true);
				individualSelector.addToAssignments(rendererAssignment);
				/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector, new DataBinding("component.renderer"),
						new DataBinding('"' + individualEntry.getRenderer() + '"'), true));*/
			}

			newTab.addToSubComponents(individualSelector, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return individualSelector;
		} else if (entry instanceof ClassInspectorEntry) {
			ClassInspectorEntry classEntry = (ClassInspectorEntry) entry;
			FIBCustom classSelector = getFactory().newFIBCustom();
			classSelector.setComponentClass(org.openflexo.components.widget.FIBClassSelector.class);
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			// component.context = xxx
			FIBCustomAssignment projectAssignment = getFactory().newInstance(FIBCustomAssignment.class);
			projectAssignment.setOwner(classSelector);
			projectAssignment.setVariable(new DataBinding<Object>("component.project"));
			projectAssignment.setValue(new DataBinding<Object>("data.project"));
			projectAssignment.setMandatory(true);
			classSelector.addToAssignments(projectAssignment);
			/*classSelector.addToAssignments(new FIBCustomAssignment(classSelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.project"), true));*/
			/*classSelector.addToAssignments(new FIBCustomAssignment(classSelector, new DataBinding("component.contextOntologyURI"),
					new DataBinding('"' + classEntry.getViewPoint().getViewpointOntology().getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return entry.getBindingFactory();
						}
					}, true));*/
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			IFlexoOntologyClass conceptClass = null;
			if (classEntry.getIsDynamicConceptValue()) {
				// conceptClass = classEntry.evaluateConceptValue(action);
				// TODO: implement proper scheme with new binding support
				logger.warning("Please implement me !!!!!!!!!");
			} else {
				conceptClass = classEntry.getConcept();
			}
			if (conceptClass != null) {
				FIBCustomAssignment rootClassAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				rootClassAssignment.setOwner(classSelector);
				rootClassAssignment.setVariable(new DataBinding<Object>("component.rootClassURI"));
				rootClassAssignment.setValue(new DataBinding<Object>('"' + conceptClass.getURI() + '"'));
				rootClassAssignment.setMandatory(true);
				classSelector.addToAssignments(rootClassAssignment);
				/*	classSelector.addToAssignments(new FIBCustomAssignment(classSelector,
							new DataBinding<Object>("component.rootClassURI"), new DataBinding<Object>('"' + conceptClass.getURI() + '"'),
							true));*/
			}
			newTab.addToSubComponents(classSelector, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return classSelector;
		} else if (entry instanceof PropertyInspectorEntry) {
			PropertyInspectorEntry propertyEntry = (PropertyInspectorEntry) entry;
			FIBCustom propertySelector = getFactory().newFIBCustom();
			propertySelector.setComponentClass(FIBPropertySelector.class);
			// Quick and dirty hack to configure FIBPropertySelector: refactor this when new binding model will be in use
			// component.context = xxx
			FIBCustomAssignment projectAssignment = getFactory().newInstance(FIBCustomAssignment.class);
			projectAssignment.setOwner(propertySelector);
			projectAssignment.setVariable(new DataBinding<Object>("component.project"));
			projectAssignment.setValue(new DataBinding<Object>("data.project"));
			projectAssignment.setMandatory(true);
			propertySelector.addToAssignments(projectAssignment);
			/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.project"), true));*/
			/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding("component.contextOntologyURI"),
					new DataBinding('"' + propertyEntry.getViewPoint().getViewpointOntology().getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return entry.getBindingFactory();
						}
					}, true));*/

			// Quick and dirty hack to configure FIBPropertySelector: refactor this when new binding model will be in use
			IFlexoOntologyClass domainClass = null;
			if (propertyEntry.getIsDynamicDomainValue()) {
				// domainClass = propertyEntry.evaluateDomainValue(action);
				// TODO: implement proper scheme with new binding support
				logger.warning("Please implement me !!!!!!!!!");
			} else {
				domainClass = propertyEntry.getDomain();
			}
			if (domainClass != null) {
				FIBCustomAssignment domainClassAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				domainClassAssignment.setOwner(propertySelector);
				domainClassAssignment.setVariable(new DataBinding<Object>("component.domainClassURI"));
				domainClassAssignment.setValue(new DataBinding<Object>('"' + domainClass.getURI() + '"'));
				domainClassAssignment.setMandatory(true);
				propertySelector.addToAssignments(domainClassAssignment);
				/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.domainClassURI"), new DataBinding<Object>('"' + domainClass.getURI() + '"'), true));*/
			}
			if (propertyEntry instanceof ObjectPropertyInspectorEntry) {
				IFlexoOntologyClass rangeClass = null;
				if (propertyEntry.getIsDynamicDomainValue()) {
					// domainClass = propertyEntry.evaluateDomainValue(action);
					// TODO: implement proper scheme with new binding support
					logger.warning("Please implement me !!!!!!!!!");
				} else {
					rangeClass = ((ObjectPropertyInspectorEntry) propertyEntry).getRange();
				}
				if (rangeClass != null) {
					FIBCustomAssignment rangeClassAssignment = getFactory().newInstance(FIBCustomAssignment.class);
					rangeClassAssignment.setOwner(propertySelector);
					rangeClassAssignment.setVariable(new DataBinding<Object>("component.rangeClassURI"));
					rangeClassAssignment.setValue(new DataBinding<Object>('"' + rangeClass.getURI() + '"'));
					rangeClassAssignment.setMandatory(true);
					propertySelector.addToAssignments(rangeClassAssignment);
					/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>(
							"component.rangeClassURI"), new DataBinding<Object>('"' + rangeClass.getURI() + '"'), true));*/
				}
			}
			if (propertyEntry instanceof ObjectPropertyInspectorEntry) {
				FIBCustomAssignment selectDataPropertiesAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				selectDataPropertiesAssignment.setOwner(propertySelector);
				selectDataPropertiesAssignment.setVariable(new DataBinding<Object>("component.selectDataProperties"));
				selectDataPropertiesAssignment.setValue(new DataBinding<Object>("false"));
				selectDataPropertiesAssignment.setMandatory(true);
				propertySelector.addToAssignments(selectDataPropertiesAssignment);
				/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.selectDataProperties"), new DataBinding<Object>("false"), true));*/
			} else if (propertyEntry instanceof DataPropertyInspectorEntry) {
				FIBCustomAssignment selectObjectPropertiesAssignment = getFactory().newInstance(FIBCustomAssignment.class);
				selectObjectPropertiesAssignment.setOwner(propertySelector);
				selectObjectPropertiesAssignment.setVariable(new DataBinding<Object>("component.selectObjectProperties"));
				selectObjectPropertiesAssignment.setValue(new DataBinding<Object>("false"));
				selectObjectPropertiesAssignment.setMandatory(true);
				propertySelector.addToAssignments(selectObjectPropertiesAssignment);
				/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.selectObjectProperties"), new DataBinding<Object>("false"), true));*/
			}

			// Quick and dirty hack to configure PropertySelector: refactor this when new binding model will be in use
			/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding("component.domainClassURI"),
					new DataBinding('"' + ((PropertyInspectorEntry) entry)._getDomainURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return entry.getBindingFactory();
						}
					}, true));*/
			newTab.addToSubComponents(propertySelector, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
			return propertySelector;
		}

		FIBLabel unknown = getFactory().newFIBLabel();
		unknown.setLabel("???");
		newTab.addToSubComponents(unknown, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, true, false));
		return unknown;

	}

	public FIBModelFactory getFactory() {
		return coreInspectorGroup.getFIBModelFactory();
	}
}

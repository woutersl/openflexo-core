/**
 * 
 * Copyright (c) 2014-2015, Openflexo
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

package org.openflexo.view.controller;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Icon;

import org.openflexo.components.widget.FIBIndividualSelector;
import org.openflexo.components.widget.FIBPropertySelector;
import org.openflexo.connie.DataBinding;
import org.openflexo.fib.controller.FIBController.Status;
import org.openflexo.fib.controller.FIBDialog;
import org.openflexo.fib.model.BorderLayoutConstraints;
import org.openflexo.fib.model.BorderLayoutConstraints.BorderLayoutLocation;
import org.openflexo.fib.model.FIBButton;
import org.openflexo.fib.model.FIBCheckBox;
import org.openflexo.fib.model.FIBCheckboxList;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.fib.model.FIBComponent.HorizontalScrollBarPolicy;
import org.openflexo.fib.model.FIBComponent.VerticalScrollBarPolicy;
import org.openflexo.fib.model.FIBCustom;
import org.openflexo.fib.model.FIBLabel;
import org.openflexo.fib.model.FIBLabel.Align;
import org.openflexo.fib.model.FIBModelFactory;
import org.openflexo.fib.model.FIBNumber;
import org.openflexo.fib.model.FIBNumber.NumberType;
import org.openflexo.fib.model.FIBPanel;
import org.openflexo.fib.model.FIBPanel.Border;
import org.openflexo.fib.model.FIBPanel.FlowLayoutAlignment;
import org.openflexo.fib.model.FIBPanel.Layout;
import org.openflexo.fib.model.FIBTextArea;
import org.openflexo.fib.model.FIBTextField;
import org.openflexo.fib.model.FIBWidget;
import org.openflexo.fib.model.GridBagLayoutConstraints;
import org.openflexo.fib.model.GridBagLayoutConstraints.AnchorType;
import org.openflexo.fib.model.GridBagLayoutConstraints.FillType;
import org.openflexo.fib.model.TwoColsLayoutConstraints;
import org.openflexo.fib.model.TwoColsLayoutConstraints.TwoColsLayoutLocation;
import org.openflexo.foundation.fml.CheckboxParameter;
import org.openflexo.foundation.fml.ClassParameter;
import org.openflexo.foundation.fml.DataPropertyParameter;
import org.openflexo.foundation.fml.FlexoBehaviour;
import org.openflexo.foundation.fml.FlexoBehaviourActionType;
import org.openflexo.foundation.fml.FlexoBehaviourParameter;
import org.openflexo.foundation.fml.FlexoConceptInstanceParameter;
import org.openflexo.foundation.fml.IndividualParameter;
import org.openflexo.foundation.fml.IntegerParameter;
import org.openflexo.foundation.fml.ListParameter;
import org.openflexo.foundation.fml.ListParameter.ListType;
import org.openflexo.foundation.fml.ObjectPropertyParameter;
import org.openflexo.foundation.fml.PropertyParameter;
import org.openflexo.foundation.fml.TextAreaParameter;
import org.openflexo.foundation.fml.TextFieldParameter;
import org.openflexo.foundation.fml.URIParameter;
import org.openflexo.foundation.fml.rt.ModelSlotInstance;
import org.openflexo.foundation.fml.rt.TypeAwareModelSlotInstance;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.IFlexoOntologyDataProperty;
import org.openflexo.foundation.ontology.IFlexoOntologyObjectProperty;
import org.openflexo.foundation.ontology.IFlexoOntologyStructuralProperty;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.toolbox.StringUtils;

public class ParametersRetriever<ES extends FlexoBehaviour> {

	private static final Logger logger = Logger.getLogger(ParametersRetriever.class.getPackage().getName());

	private final FlexoBehaviourAction<?, ES, ?> action;

	private static FIBModelFactory fibModelFactory;

	static {
		try {
			fibModelFactory = new FIBModelFactory();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
	}

	public ParametersRetriever(FlexoBehaviourAction<?, ES, ?> action) {
		this.action = action;
		if (action != null) {
			action.retrieveDefaultParameters();
		}
	}

	public boolean isSkipable() {
		boolean successfullyRetrievedDefaultParameters = action.retrieveDefaultParameters();

		if (successfullyRetrievedDefaultParameters && action.getFlexoBehaviour().getSkipConfirmationPanel()) {
			return true;
		}

		return false;
	}

	public boolean retrieveParameters() {

		FIBComponent component = makeFIB(true, true);
		FIBDialog dialog = FIBDialog.instanciateDialog(component, action, null, true, FlexoLocalization.getMainLocalizer());
		if (!action.getFlexoBehaviour().getDefinePopupDefaultSize()) {
			dialog.setMinimumSize(new Dimension(500, 50));
		}
		dialog.showDialog();
		return dialog.getStatus() == Status.VALIDATED;
	}

	private FIBComponent makeWidget(final FlexoBehaviourParameter parameter, FIBPanel panel, int index) {
		if (parameter instanceof TextFieldParameter) {
			FIBTextField tf = fibModelFactory.newFIBTextField();
			tf.setName(parameter.getName() + "TextField");
			return registerWidget(tf, parameter, panel, index);
		} else if (parameter instanceof URIParameter) {
			FIBPanel uriPanel = makeURIPanel((URIParameter) parameter);
			return registerWidget(uriPanel, parameter, panel, index);
		} else if (parameter instanceof TextAreaParameter) {
			FIBTextArea ta = fibModelFactory.newFIBTextArea();
			ta.setName(parameter.getName() + "TextArea");
			ta.setValidateOnReturn(true); // Avoid too many ontologies manipulations
			ta.setUseScrollBar(true);
			ta.setHorizontalScrollbarPolicy(HorizontalScrollBarPolicy.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			ta.setVerticalScrollbarPolicy(VerticalScrollBarPolicy.VERTICAL_SCROLLBAR_AS_NEEDED);
			return registerWidget(ta, parameter, panel, index, true, true);
		} else if (parameter instanceof CheckboxParameter) {
			FIBCheckBox cb = fibModelFactory.newFIBCheckBox();
			cb.setName(parameter.getName() + "CheckBox");
			return registerWidget(cb, parameter, panel, index);
		} else if (parameter instanceof IntegerParameter) {
			FIBNumber number = fibModelFactory.newFIBNumber();
			number.setName(parameter.getName() + "Number");
			number.setNumberType(NumberType.IntegerType);
			return registerWidget(number, parameter, panel, index);
		} else if (parameter instanceof ListParameter) {
			ListParameter listParameter = (ListParameter) parameter;
			FIBCheckboxList cbList = fibModelFactory.newFIBCheckboxList();
			cbList.setName(parameter.getName() + "CheckboxList");
			// TODO: repair this !!!
			logger.warning("This feature is no more implemented, please repair this !!!");
			cbList.setList(new DataBinding<List<?>>("data.parameters." + parameter.getName() + "TODO"));
			if (listParameter.getListType() == ListType.ObjectProperty) {
				cbList.setIteratorClass(IFlexoOntologyObjectProperty.class);
				cbList.setFormat(new DataBinding<String>("object.name + \" (\"+object.domain.name+\")\""));
				cbList.setShowIcon(true);
				cbList.setIcon(new DataBinding<Icon>("controller.iconForObject(object)"));
				cbList.setVGap(-2);
			} else if (listParameter.getListType() == ListType.DataProperty) {
				cbList.setIteratorClass(IFlexoOntologyDataProperty.class);
				cbList.setFormat(new DataBinding<String>("object.name + \" (\"+object.domain.name+\")\""));
				cbList.setShowIcon(true);
				cbList.setIcon(new DataBinding<Icon>("controller.iconForObject(object)"));
				cbList.setVGap(-2);
			} else if (listParameter.getListType() == ListType.Property) {
				cbList.setIteratorClass(IFlexoOntologyStructuralProperty.class);
				cbList.setFormat(new DataBinding<String>("object.name + \" (\"+object.domain.name+\")\""));
				cbList.setShowIcon(true);
				cbList.setIcon(new DataBinding<Icon>("controller.iconForObject(object)"));
				cbList.setVGap(-2);
			}
			cbList.setUseScrollBar(true);
			cbList.setHorizontalScrollbarPolicy(HorizontalScrollBarPolicy.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			cbList.setVerticalScrollbarPolicy(VerticalScrollBarPolicy.VERTICAL_SCROLLBAR_AS_NEEDED);

			return registerWidget(cbList, parameter, panel, index, true, true);
		} else if (parameter instanceof FlexoConceptInstanceParameter) {
			FIBCustom epiSelector = fibModelFactory.newFIBCustom();
			epiSelector.setBindingFactory(parameter.getBindingFactory());
			Class fciSelectorClass;
			try {
				fciSelectorClass = Class.forName("org.openflexo.fml.rt.controller.widget.FIBFlexoConceptInstanceSelector");
				epiSelector.setComponentClass(fciSelectorClass);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			epiSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(epiSelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.editor.project"), true));
			epiSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(epiSelector, new DataBinding<Object>("component.view"),
					new DataBinding<Object>("data.view"), true));
			epiSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(epiSelector, new DataBinding<Object>(
					"component.flexoConcept"), new DataBinding<Object>("data.editionScheme.parametersDefinitions." + parameter.getName()
					+ ".flexoConceptType"), true));
			// extra informations.
			epiSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(epiSelector, new DataBinding<Object>(
					"component.virtualModel"), new DataBinding<Object>("data.editionScheme.parametersDefinitions." + parameter.getName()
					+ ".modelSlotVirtualModel"), true));
			epiSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(epiSelector, new DataBinding<Object>(
					"component.viewpointLibrary"), new DataBinding<Object>("data.serviceManager.viewpointLibrary"), true));

			return registerWidget(epiSelector, parameter, panel, index);
		} else if (parameter instanceof IndividualParameter) {
			FIBCustom individualSelector = fibModelFactory.newFIBCustom();
			individualSelector.setComponentClass(FIBIndividualSelector.class);
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			// component.context = xxx
			/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.project"), true));*/
			/*individualSelector.addToAssignments(new FIBCustomAssignment(individualSelector,
					new DataBinding("component.contextOntologyURI"), new DataBinding('"' + parameter.getViewPoint().getViewpointOntology()
							.getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return parameter.getBindingFactory();
						}
					}, true));*/

			individualSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(individualSelector, new DataBinding<Object>(
					"component.informationSpace"), new DataBinding<Object>("data.project.informationSpace"), true));
			if (action.getVirtualModelInstance() != null) {
				ModelSlotInstance msInstance = action.getVirtualModelInstance().getModelSlotInstance(
						((IndividualParameter) parameter).getModelSlot());
				if (msInstance instanceof TypeAwareModelSlotInstance && ((TypeAwareModelSlotInstance) msInstance).getModel() != null) {
					individualSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(individualSelector, new DataBinding(
							"component.contextOntologyURI"), new DataBinding<Object>('"' + ((TypeAwareModelSlotInstance) msInstance)
							.getModel().getURI() + '"'), true));
				} else {
					logger.warning("No model defined for model slot " + ((IndividualParameter) parameter).getModelSlot());
				}
			} else {
				logger.warning("Inconsistent data: no VirtualModelInstance for action " + action);
			}
			// Quick and dirty hack to configure IndividualSelector: refactor this when new binding model will be in use
			individualSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(individualSelector, new DataBinding<Object>(
					"component.typeURI"), new DataBinding<Object>('"' + ((IndividualParameter) parameter)._getConceptURI() + '"'), true));
			if (StringUtils.isNotEmpty(((IndividualParameter) parameter).getRenderer())) {
				individualSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(individualSelector, new DataBinding<Object>(
						"component.renderer"), new DataBinding<Object>('"' + ((IndividualParameter) parameter).getRenderer() + '"'), true));
			}
			return registerWidget(individualSelector, parameter, panel, index);
		} else if (parameter instanceof ClassParameter) {
			ClassParameter classParameter = (ClassParameter) parameter;
			FIBCustom classSelector = fibModelFactory.newFIBCustom();
			classSelector.setComponentClass(org.openflexo.components.widget.FIBClassSelector.class);
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			// component.context = xxx
			/*classSelector.addToAssignments(new FIBCustomAssignment(classSelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.project"), true));*/
			/*classSelector.addToAssignments(new FIBCustomAssignment(classSelector, new DataBinding("component.contextOntologyURI"),
					new DataBinding('"' + classParameter.getViewPoint().getViewpointOntology().getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return parameter.getBindingFactory();
						}
					}, true));*/
			classSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(classSelector, new DataBinding<Object>(
					"component.informationSpace"), new DataBinding<Object>("data.project.informationSpace"), true));
			if (action.getVirtualModelInstance() != null) {
				ModelSlotInstance msInstance = action.getVirtualModelInstance().getModelSlotInstance(
						((IndividualParameter) parameter).getModelSlot());
				if (msInstance instanceof TypeAwareModelSlotInstance && ((TypeAwareModelSlotInstance) msInstance).getModel() != null) {
					classSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(classSelector, new DataBinding(
							"component.contextOntologyURI"), new DataBinding<Object>('"' + ((TypeAwareModelSlotInstance) msInstance)
							.getModel().getURI() + '"'), true));
				} else {
					logger.warning("No model defined for model slot " + ((IndividualParameter) parameter).getModelSlot());
				}
			} else {
				logger.warning("Inconsistent data: no VirtualModelInstance for action " + action);
			}
			// Quick and dirty hack to configure ClassSelector: refactor this when new binding model will be in use
			IFlexoOntologyClass conceptClass = null;
			if (classParameter.getIsDynamicConceptValue()) {
				conceptClass = classParameter.evaluateConceptValue(action);
			} else {
				conceptClass = classParameter.getConcept();
			}
			if (conceptClass != null) {
				classSelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(classSelector, new DataBinding<Object>(
						"component.rootClassURI"), new DataBinding<Object>('"' + conceptClass.getURI() + '"'), true));
			}
			return registerWidget(classSelector, parameter, panel, index);
		} else if (parameter instanceof PropertyParameter) {
			PropertyParameter propertyParameter = (PropertyParameter) parameter;
			FIBCustom propertySelector = fibModelFactory.newFIBCustom();
			propertySelector.setComponentClass(FIBPropertySelector.class);
			// Quick and dirty hack to configure FIBPropertySelector: refactor this when new binding model will be in use
			// component.context = xxx
			/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding<Object>("component.project"),
					new DataBinding<Object>("data.project"), true));*/
			/*propertySelector.addToAssignments(new FIBCustomAssignment(propertySelector, new DataBinding("component.contextOntologyURI"),
					new DataBinding('"' + propertyParameter.getViewPoint().getViewpointOntology().getURI() + '"') {
						@Override
						public BindingFactory getBindingFactory() {
							return parameter.getBindingFactory();
						}
					}, true));*/
			propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding<Object>(
					"component.informationSpace"), new DataBinding<Object>("data.project.informationSpace"), true));
			if (action.getVirtualModelInstance() != null) {
				ModelSlotInstance msInstance = action.getVirtualModelInstance().getModelSlotInstance(
						((IndividualParameter) parameter).getModelSlot());
				if (msInstance instanceof TypeAwareModelSlotInstance && ((TypeAwareModelSlotInstance) msInstance).getModel() != null) {
					propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding(
							"component.contextOntologyURI"), new DataBinding<Object>('"' + ((TypeAwareModelSlotInstance) msInstance)
							.getModel().getURI() + '"'), true));
				} else {
					logger.warning("No model defined for model slot " + ((IndividualParameter) parameter).getModelSlot());
				}
			} else {
				logger.warning("Inconsistent data: no VirtualModelInstance for action " + action);
			}

			// Quick and dirty hack to configure PropertySelector: refactor this when new binding model will be in use
			IFlexoOntologyClass domainClass = null;
			if (propertyParameter.getIsDynamicDomainValue()) {
				domainClass = propertyParameter.evaluateDomainValue(action);
			} else {
				domainClass = propertyParameter.getDomain();
			}
			// System.out.println("domain class = " + domainClass + " uri=" + domainClass.getURI());
			if (domainClass != null) {
				propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.domainClassURI"), new DataBinding<Object>('"' + domainClass.getURI() + '"'), true));
			}

			if (propertyParameter instanceof ObjectPropertyParameter) {
				IFlexoOntologyClass rangeClass = null;
				if (propertyParameter.getIsDynamicDomainValue()) {
					rangeClass = ((ObjectPropertyParameter) propertyParameter).evaluateRangeValue(action);
				} else {
					rangeClass = ((ObjectPropertyParameter) propertyParameter).getRange();
				}
				// System.out.println("range class = " + rangeClass + " uri=" + rangeClass.getURI());
				if (rangeClass != null) {
					propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding<Object>(
							"component.rangeClassURI"), new DataBinding<Object>('"' + rangeClass.getURI() + '"'), true));
				}
			}

			if (propertyParameter instanceof ObjectPropertyParameter) {
				propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.selectDataProperties"), DataBinding.makeFalseBinding(), true));
			} else if (propertyParameter instanceof DataPropertyParameter) {
				propertySelector.addToAssignments(fibModelFactory.newFIBCustomAssignment(propertySelector, new DataBinding<Object>(
						"component.selectObjectProperties"), DataBinding.makeFalseBinding(), true));
			}
			return registerWidget(propertySelector, parameter, panel, index);
		}

		// Default
		FIBLabel unknown = fibModelFactory.newFIBLabel();
		unknown.setLabel("???");
		registerWidget(unknown, parameter, panel, index);
		return unknown;
	}

	private FIBComponent registerWidget(FIBComponent widget, FlexoBehaviourParameter parameter, FIBPanel panel, int index) {
		return registerWidget(widget, parameter, panel, index, true, false);
	}

	private FIBComponent registerWidget(FIBComponent widget, FlexoBehaviourParameter parameter, FIBPanel panel, int index,
			boolean expandHorizontally, boolean expandVertically) {
		widget.setData(new DataBinding<Object>("data.parameters." + parameter.getName()));
		if (widget instanceof FIBWidget) {
			((FIBWidget) widget).setValueChangedAction(new DataBinding<Object>("controller.parameterValueChanged(data)"));
		}
		panel.addToSubComponents(widget, new TwoColsLayoutConstraints(TwoColsLayoutLocation.right, expandHorizontally, expandVertically),
				index);
		return widget;
	}

	protected FIBComponent makeFIB(boolean addTitle, boolean addControls) {

		if (action == null) {
			return fibModelFactory.newFIBPanel();
		}

		final FlexoBehaviour flexoBehaviour = action.getFlexoBehaviour();

		FIBPanel returned = fibModelFactory.newFIBPanel();
		returned.setDataType(FlexoBehaviourActionType.getFlexoBehaviourActionType(action.getFlexoBehaviour()));
		returned.setBindingFactory(action.getFlexoBehaviour().getBindingFactory());

		returned.setLayout(Layout.twocols);
		returned.setDataClass(action.getClass());
		returned.setBorder(Border.empty);
		returned.setBorderTop(10);
		returned.setBorderBottom(5);
		returned.setBorderRight(10);
		returned.setBorderLeft(10);
		returned.setControllerClass(ParametersRetrieverController.class);

		if (flexoBehaviour.getDefinePopupDefaultSize()) {
			returned.setMinWidth(flexoBehaviour.getWidth());
			returned.setMinHeight(flexoBehaviour.getHeight());
		}

		int index = 0;
		if (addTitle) {
			FIBLabel titleLabel = fibModelFactory.newFIBLabel();
			titleLabel.setAlign(Align.center);
			titleLabel.setLabel(FlexoLocalization.localizedForKey(flexoBehaviour.getOwningVirtualModel().getLocalizedDictionary(),
					flexoBehaviour.getLabel() != null ? flexoBehaviour.getLabel() : flexoBehaviour.getName()));
			returned.addToSubComponents(titleLabel, new TwoColsLayoutConstraints(TwoColsLayoutLocation.center, true, false), 0);
			index++;

			if (StringUtils.isNotEmpty(flexoBehaviour.getDescription())) {
				FIBPanel descriptionPanel = fibModelFactory.newFIBPanel();
				descriptionPanel.setLayout(Layout.twocols);
				descriptionPanel.setBorder(Border.rounded3d);
				descriptionPanel.setLayout(Layout.border);
				descriptionPanel.setBorderTop(10);
				descriptionPanel.setBorderBottom(10);

				FIBLabel descriptionLabel = fibModelFactory.newFIBLabel();
				descriptionLabel.setAlign(Align.center);
				descriptionLabel.setLabel("<html><i>" + flexoBehaviour.getDescription() + "</i></html>");
				descriptionPanel.addToSubComponents(descriptionLabel, new BorderLayoutConstraints(BorderLayoutLocation.center));
				returned.addToSubComponents(descriptionPanel, new TwoColsLayoutConstraints(TwoColsLayoutLocation.center, true, false), 1);
				index++;
			} else {
				((TwoColsLayoutConstraints) titleLabel.getConstraints()).setInsetsBottom(10);
			}
		}

		Hashtable<FlexoBehaviourParameter, FIBComponent> widgets = new Hashtable<FlexoBehaviourParameter, FIBComponent>();
		for (final FlexoBehaviourParameter parameter : flexoBehaviour.getParameters()) {
			FIBLabel label = fibModelFactory.newFIBLabel();
			label.setLabel(parameter.getLabel());
			returned.addToSubComponents(label, new TwoColsLayoutConstraints(TwoColsLayoutLocation.left, false, false), index++);
			FIBComponent widget = makeWidget(parameter, returned, index++);
			widgets.put(parameter, widget);
		}
		for (final FlexoBehaviourParameter parameter : flexoBehaviour.getParameters()) {
			if (parameter instanceof URIParameter) {
				FIBPanel uriPanel = (FIBPanel) widgets.get(parameter);
				List<FlexoBehaviourParameter> dependancies = ((URIParameter) parameter).getDependancies();
				if (dependancies != null) {
					for (FlexoBehaviourParameter dep : dependancies) {
						FIBComponent dependingComponent = widgets.get(dep);
						uriPanel.getComponentNamed("tf").addToExplicitDependancies(fibModelFactory.newFIBDependancy(dependingComponent));
					}
				}
			}
		}

		if (addControls) {
			FIBPanel buttonsPanel = fibModelFactory.newFIBPanel();

			buttonsPanel.setLayout(Layout.flow);
			buttonsPanel.setFlowAlignment(FlowLayoutAlignment.CENTER);
			buttonsPanel.setHGap(0);
			buttonsPanel.setVGap(5);
			buttonsPanel.setBorderTop(5);
			buttonsPanel.setBorder(Border.empty);
			FIBButton validateButton = fibModelFactory.newFIBButton();
			validateButton.setLabel("validate");
			validateButton.setLocalize(true);
			validateButton.setAction(new DataBinding("controller.validateAndDispose()"));
			validateButton.setEnable(new DataBinding<Boolean>("controller.isValidable(data)"));
			for (FIBComponent widget : widgets.values()) {
				validateButton.addToExplicitDependancies(fibModelFactory.newFIBDependancy(widget));
			}
			buttonsPanel.addToSubComponents(validateButton);
			FIBButton cancelButton = fibModelFactory.newFIBButton();
			cancelButton.setLabel("cancel");
			cancelButton.setLocalize(true);
			cancelButton.setAction(new DataBinding("controller.cancelAndDispose()"));
			buttonsPanel.addToSubComponents(cancelButton);

			returned.addToSubComponents(buttonsPanel, new TwoColsLayoutConstraints(TwoColsLayoutLocation.center, true, false), index++);
		}
		/*	try {
				logger.info("Getting this "
						+ XMLCoder.encodeObjectWithMapping(returned, FIBLibrary.getFIBMapping(), StringEncoder.getDefaultInstance()));
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

		return returned;
	}

	public FIBPanel makeURIPanel(final URIParameter parameter) {
		FIBPanel returned = fibModelFactory.newFIBPanel();
		returned.setName(parameter.getName() + "URIPanel");
		returned.setLayout(Layout.gridbag);
		FIBTextField tf = fibModelFactory.newFIBTextField();
		tf.setName("tf");
		FIBLabel uriLabel = fibModelFactory.newFIBLabel("http://xxxxxx.owl");
		uriLabel.setName("uriLabel");
		Font f = uriLabel.retrieveValidFont();
		if (f != null) {
			uriLabel.setFont(f.deriveFont(10f));
		} else {
			uriLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		}
		uriLabel.setData(new DataBinding<Object>("data.retrieveFullURI" + "(data.parametersDefinitions" + "." + parameter.getName() + ")"));
		returned.addToSubComponents(tf, new GridBagLayoutConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
				GridBagConstraints.REMAINDER, 1, 1.0, 0, AnchorType.west, FillType.horizontal, 0, 0, 0, 0, 0, 0));
		returned.addToSubComponents(uriLabel, new GridBagLayoutConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
				GridBagConstraints.REMAINDER, 1, 1.0, 0, AnchorType.west, FillType.horizontal, -3, 0, 0, 0, 0, 0));
		tf.setData(new DataBinding<Object>("data.parameters." + parameter.getName()));
		return returned;
	}

	/*public class URIPanel extends FIBPanel {
	public FIBTextField tf;
	public FIBLabel uriLabel;

	public URIPanel(final URIParameter parameter) {
		super();
		setName(parameter.getName() + "URIPanel");
		setLayout(Layout.gridbag);
		tf = new FIBTextField();
		uriLabel = new FIBLabel("http://xxxxxx.owl");
		Font f = uriLabel.retrieveValidFont();
		if (f != null) {
			uriLabel.setFont(f.deriveFont(10f));
		} else {
			uriLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		}
		uriLabel.setData(new DataBinding<Object>("data.retrieveFullURI" + "(data.parametersDefinitions" + "." + parameter.getName()
				+ ")"));
		addToSubComponents(tf, new GridBagLayoutConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
				GridBagConstraints.REMAINDER, 1, 1.0, 0, AnchorType.west, FillType.horizontal, 0, 0, 0, 0, 0, 0));
		addToSubComponents(uriLabel, new GridBagLayoutConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
				GridBagConstraints.REMAINDER, 1, 1.0, 0, AnchorType.west, FillType.horizontal, -3, 0, 0, 0, 0, 0));
		tf.setData(new DataBinding<Object>("data.parameters." + parameter.getName()));
	}
	}*/

	public static class ParametersRetrieverController extends FlexoFIBController {

		public ParametersRetrieverController(FIBComponent component) {
			super(component);
		}

		public boolean isValidable(FlexoBehaviourAction<?, ?, ?> action) {
			if (action == null) {
				// Called during initialization, don't care
				return false;
			}
			return action.areRequiredParametersSetAndValid();
		}

		public boolean parameterValueChanged(FlexoBehaviourAction<?, ?, ?> action) {
			action.parameterValueChanged();
			return true;
		}
	}
}

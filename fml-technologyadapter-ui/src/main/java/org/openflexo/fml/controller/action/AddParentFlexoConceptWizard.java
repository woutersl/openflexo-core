/**
 * 
 * Copyright (c) 2014-2015, Openflexo
 * 
 * This file is part of Fml-technologyadapter-ui, a component of the software infrastructure 
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

package org.openflexo.fml.controller.action;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.ApplicationContext;
import org.openflexo.components.wizard.FlexoWizard;
import org.openflexo.components.wizard.WizardStep;
import org.openflexo.fib.annotation.FIBPanel;
import org.openflexo.foundation.fml.AbstractVirtualModel;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.action.AbstractCreateFlexoConcept.ParentFlexoConceptEntry;
import org.openflexo.foundation.fml.action.AddParentFlexoConcept;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.icon.FMLIconLibrary;
import org.openflexo.icon.IconFactory;
import org.openflexo.icon.IconLibrary;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.view.controller.FlexoController;

public class AddParentFlexoConceptWizard extends FlexoWizard {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AddParentFlexoConceptWizard.class.getPackage().getName());

	private static final String INCONSISTENT_HIERARCHY = FlexoLocalization.localizedForKey("inconsistent_flexo_concept_hierarchy");

	private DescribeNewParentConcepts describeNewParentConcepts;

	private final AddParentFlexoConcept action;

	private static final Dimension DIMENSIONS = new Dimension(600, 400);

	public AddParentFlexoConcept getAction() {
		return action;
	}

	@Override
	public Dimension getPreferredSize() {
		return DIMENSIONS;
	}

	public AddParentFlexoConceptWizard(AddParentFlexoConcept action, FlexoController controller) {
		super(controller);
		this.action = action;
		addStep(describeNewParentConcepts = new DescribeNewParentConcepts());
	}

	@Override
	public String getWizardTitle() {
		return FlexoLocalization.localizedForKey("define_parent_concepts");
	}

	@Override
	public Image getDefaultPageImage() {
		return IconFactory.getImageIcon(FMLIconLibrary.FLEXO_CONCEPT_BIG_ICON, IconLibrary.NEW_32_32).getImage();
	}

	public DescribeNewParentConcepts getDescribeNewParentConcepts() {
		return describeNewParentConcepts;
	}

	/**
	 * This step is used to set {@link VirtualModel} to be used, as well as name and title of the {@link VirtualModelInstance}
	 * 
	 * @author sylvain
	 *
	 */
	@FIBPanel("Fib/Wizard/FMLAction/DescribeNewParentConcepts.fib")
	public class DescribeNewParentConcepts extends WizardStep implements PropertyChangeListener {

		public DescribeNewParentConcepts() {
			for (ParentFlexoConceptEntry entry : getParentFlexoConceptEntries()) {
				entry.getPropertyChangeSupport().addPropertyChangeListener(this);
			}
		}

		@Override
		public void delete() {
			super.delete();
			for (ParentFlexoConceptEntry entry : getParentFlexoConceptEntries()) {
				entry.getPropertyChangeSupport().removePropertyChangeListener(this);
			}
		}

		public ApplicationContext getServiceManager() {
			return getController().getApplicationContext();
		}

		public AddParentFlexoConcept getAction() {
			return AddParentFlexoConceptWizard.this.getAction();
		}

		@Override
		public String getTitle() {
			return FlexoLocalization.localizedForKey("define_parent_concepts");
		}

		public AbstractVirtualModel<?> getVirtualModel() {
			return getAction().getFocusedObject().getOwningVirtualModel();
		}

		@Override
		public boolean isValid() {

			for (ParentFlexoConceptEntry entry : getParentFlexoConceptEntries()) {
				if (getAction().getFocusedObject().isSuperConceptOf(entry.getParentConcept())) {
					setIssueMessage(INCONSISTENT_HIERARCHY, IssueMessageType.ERROR);
					return false;
				}
			}

			return true;
		}

		public List<ParentFlexoConceptEntry> getParentFlexoConceptEntries() {
			return getAction().getParentFlexoConceptEntries();
		}

		public ParentFlexoConceptEntry newParentFlexoConceptEntry() {
			ParentFlexoConceptEntry returned = getAction().newParentFlexoConceptEntry();
			getPropertyChangeSupport().firePropertyChange("parentFlexoConceptEntries", null, returned);
			returned.getPropertyChangeSupport().addPropertyChangeListener(this);
			checkValidity();
			return returned;
		}

		public void deleteParentFlexoConceptEntry(ParentFlexoConceptEntry parentFlexoConceptEntryToDelete) {
			getAction().deleteParentFlexoConceptEntry(parentFlexoConceptEntryToDelete);
			getPropertyChangeSupport().firePropertyChange("parentFlexoConceptEntries", parentFlexoConceptEntryToDelete, null);
			parentFlexoConceptEntryToDelete.getPropertyChangeSupport().removePropertyChangeListener(this);
			checkValidity();
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getSource() instanceof ParentFlexoConceptEntry) {
				checkValidity();
			}
		}
	}

}

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
package org.openflexo.icon;

import java.lang.reflect.Type;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.antar.binding.TypeUtils;
import org.openflexo.foundation.technologyadapter.ModelSlot;
import org.openflexo.foundation.technologyadapter.TechnologyObject;
import org.openflexo.foundation.viewpoint.ActionScheme;
import org.openflexo.foundation.viewpoint.CloningScheme;
import org.openflexo.foundation.viewpoint.CreationScheme;
import org.openflexo.foundation.viewpoint.DeletionScheme;
import org.openflexo.foundation.viewpoint.EditionPattern;
import org.openflexo.foundation.viewpoint.EditionPatternConstraint;
import org.openflexo.foundation.viewpoint.EditionPatternInstancePatternRole;
import org.openflexo.foundation.viewpoint.EditionScheme;
import org.openflexo.foundation.viewpoint.EditionSchemeParameter;
import org.openflexo.foundation.viewpoint.LocalizedDictionary;
import org.openflexo.foundation.viewpoint.NavigationScheme;
import org.openflexo.foundation.viewpoint.OntologicObjectPatternRole;
import org.openflexo.foundation.viewpoint.PatternRole;
import org.openflexo.foundation.viewpoint.PrimitivePatternRole;
import org.openflexo.foundation.viewpoint.SynchronizationScheme;
import org.openflexo.foundation.viewpoint.TechnologySpecificEditionScheme;
import org.openflexo.foundation.viewpoint.ViewPoint;
import org.openflexo.foundation.viewpoint.ViewPointObject;
import org.openflexo.foundation.viewpoint.VirtualModel;
import org.openflexo.foundation.viewpoint.editionaction.AddEditionPatternInstance;
import org.openflexo.foundation.viewpoint.editionaction.AddToListAction;
import org.openflexo.foundation.viewpoint.editionaction.AssignationAction;
import org.openflexo.foundation.viewpoint.editionaction.ConditionalAction;
import org.openflexo.foundation.viewpoint.editionaction.DataPropertyAssertion;
import org.openflexo.foundation.viewpoint.editionaction.DeclarePatternRole;
import org.openflexo.foundation.viewpoint.editionaction.DeleteAction;
import org.openflexo.foundation.viewpoint.editionaction.EditionAction;
import org.openflexo.foundation.viewpoint.editionaction.ExecutionAction;
import org.openflexo.foundation.viewpoint.editionaction.FetchRequestCondition;
import org.openflexo.foundation.viewpoint.editionaction.FetchRequestIterationAction;
import org.openflexo.foundation.viewpoint.editionaction.IterationAction;
import org.openflexo.foundation.viewpoint.editionaction.MatchEditionPatternInstance;
import org.openflexo.foundation.viewpoint.editionaction.MatchingCriteria;
import org.openflexo.foundation.viewpoint.editionaction.ObjectPropertyAssertion;
import org.openflexo.foundation.viewpoint.editionaction.RemoveFromListAction;
import org.openflexo.foundation.viewpoint.editionaction.SelectEditionPatternInstance;
import org.openflexo.foundation.viewpoint.inspector.CheckboxInspectorEntry;
import org.openflexo.foundation.viewpoint.inspector.EditionPatternInspector;
import org.openflexo.foundation.viewpoint.inspector.FloatInspectorEntry;
import org.openflexo.foundation.viewpoint.inspector.InspectorEntry;
import org.openflexo.foundation.viewpoint.inspector.IntegerInspectorEntry;
import org.openflexo.foundation.viewpoint.inspector.TextAreaInspectorEntry;
import org.openflexo.foundation.viewpoint.inspector.TextFieldInspectorEntry;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.foundation.viewpoint.rm.VirtualModelResource;
import org.openflexo.toolbox.ImageIconResource;
import org.openflexo.view.controller.TechnologyAdapterController;

/**
 * Utility class containing all icons used in context of VPMModule
 * 
 * @author sylvain
 * 
 */
public class VPMIconLibrary extends IconLibrary {

	private static final Logger logger = Logger.getLogger(VPMIconLibrary.class.getPackage().getName());

	// Module icons
	public static final ImageIcon VPM_SMALL_ICON = new ImageIconResource("Icons/VPM/module-vpm-16.png");
	public static final ImageIcon VPM_MEDIUM_ICON = new ImageIconResource("Icons/VPM/module-vpm-32.png");
	public static final ImageIcon VPM_MEDIUM_ICON_WITH_HOVER = new ImageIconResource("Icons/VPM/module-vpm-hover-32.png");
	public static final ImageIcon VPM_BIG_ICON = new ImageIconResource("Icons/VPM/module-vpm-hover-64.png");

	// Perspective icons
	public static final ImageIcon VPM_VPE_ACTIVE_ICON = new ImageIconResource("Icons/VPM/viewpoint-perspective.png");
	// public static final ImageIcon VPM_VPE_SELECTED_ICON = new ImageIconResource("Icons/VPM/viewpoint-perspective-hover.png");
	public static final ImageIcon VPM_OP_ACTIVE_ICON = new ImageIconResource("Icons/VPM/ontology-perspective.png");
	// public static final ImageIcon VPM_OP_SELECTED_ICON = new ImageIconResource("Icons/VPM/ontology-perspective-hover.png");

	// Editor icons
	public static final ImageIcon NO_HIERARCHY_MODE_ICON = new ImageIconResource("Icons/VPM/NoHierarchyViewMode.gif");
	public static final ImageIcon PARTIAL_HIERARCHY_MODE_ICON = new ImageIconResource("Icons/VPM/PartialHierarchyViewMode.gif");
	public static final ImageIcon FULL_HIERARCHY_MODE_ICON = new ImageIconResource("Icons/VPM/FullHierarchyViewMode.gif");

	// Model icons
	public static final ImageIconResource VIEWPOINT_LIBRARY_ICON = new ImageIconResource("Icons/Model/VPM/ViewPointLibrary.png");
	public static final ImageIconResource VIEWPOINT_ICON = new ImageIconResource("Icons/Model/VPM/ViewPoint.png");
	public static final ImageIconResource MODEL_SLOT_ICON = new ImageIconResource("Icons/Model/VPM/ModelSlot.png");
	public static final ImageIconResource EDITION_PATTERN_ICON = new ImageIconResource("Icons/Model/VPM/EditionPattern.png");
	public static final ImageIconResource ACTION_SCHEME_ICON = new ImageIconResource("Icons/Model/VPM/ActionSchemeIcon.png");
	public static final ImageIconResource SYNCHRONIZATION_SCHEME_ICON = new ImageIconResource(
			"Icons/Model/VPM/SynchronizationSchemeIcon.png");
	public static final ImageIconResource CLONING_SCHEME_ICON = new ImageIconResource("Icons/Model/VPM/CloningSchemeIcon.png");
	public static final ImageIconResource CREATION_SCHEME_ICON = new ImageIconResource("Icons/Model/VPM/CreationSchemeIcon.png");
	public static final ImageIconResource DELETION_SCHEME_ICON = new ImageIconResource("Icons/Model/VPM/DeletionSchemeIcon.png");
	public static final ImageIconResource NAVIGATION_SCHEME_ICON = new ImageIconResource("Icons/Model/VPM/NavigationSchemeIcon.png");
	public static final ImageIconResource EDITION_PATTERN_PARAMETER_ICON = new ImageIconResource("Icons/Model/VPM/ParameterIcon.png");
	public static final ImageIconResource EDITION_PATTERN_ACTION_ICON = new ImageIconResource("Icons/Model/VPM/ActionIcon.png");
	public static final ImageIconResource LOCALIZATION_ICON = new ImageIconResource("Icons/Model/VPM/LocalizationIcon.png");
	public static final ImageIconResource UNKNOWN_ICON = new ImageIconResource("Icons/Model/VPM/UnknownIcon.gif");
	public static final ImageIconResource VIRTUAL_MODEL_ICON = new ImageIconResource("Icons/Model/VPM/VirtualModel.png");
	public static final ImageIconResource DECLARE_PATTERN_ROLE_ICON = new ImageIconResource("Icons/Model/VPM/DeclarePatternRoleIcon.png");
	public static final ImageIconResource CONDITIONAL_ACTION_ICON = new ImageIconResource("Icons/Model/VPM/ConditionalActionIcon.png");
	public static final ImageIconResource ITERATION_ACTION_ICON = new ImageIconResource("Icons/Model/VPM/IterationActionIcon.png");
	public static final ImageIconResource CONSTRAINT_ICON = new ImageIconResource("Icons/Model/VPM/ConstraintIcon.png");

	public static final ImageIconResource CHECKBOX_ICON = new ImageIconResource("Icons/Model/VPM/CheckBoxIcon.png");
	public static final ImageIconResource LIST_ICON = new ImageIconResource("Icons/Model/VPM/ListIcon.png");
	public static final ImageIconResource SPINNER_ICON = new ImageIconResource("Icons/Model/VPM/SpinnerIcon.png");
	public static final ImageIconResource TEXT_AREA_ICON = new ImageIconResource("Icons/Model/VPM/TextAreaIcon.png");
	public static final ImageIconResource TEXT_FIELD_ICON = new ImageIconResource("Icons/Model/VPM/TextFieldIcon.png");

	public static final IconMarker MODEL_SLOT_ICON_MARKER = new IconMarker(new ImageIconResource("Icons/Model/VPM/ModelSlot.png"), 2, 6);

	public static ImageIcon iconForObject(ViewPointObject object) {

		if (object instanceof PatternRole && ((PatternRole) object).getModelSlot() != null) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((PatternRole) object).getModelSlot()
					.getTechnologyAdapter());
			if (tac != null) {
				return tac.getIconForPatternRole((Class<? extends PatternRole<?>>) object.getClass());
			}
		}
		if (object instanceof ViewPoint) {
			return VIEWPOINT_ICON;
		} else if (object instanceof VirtualModel) {
			return VIRTUAL_MODEL_ICON;
		} else if (object instanceof ModelSlot) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((ModelSlot) object).getTechnologyAdapter());
			if (tac != null) {
				return IconFactory.getImageIcon(tac.getTechnologyIcon(), MODEL_SLOT_ICON_MARKER);
			}
			return MODEL_SLOT_ICON;
		} else if (object instanceof EditionPatternInspector) {
			return INSPECT_ICON;
		} else if (object instanceof EditionPatternConstraint) {
			return CONSTRAINT_ICON;
		} else if (object instanceof FetchRequestCondition) {
			return CONSTRAINT_ICON;
		} else if (object instanceof MatchingCriteria) {
			return CONSTRAINT_ICON;
		} else if (object instanceof DataPropertyAssertion) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((DataPropertyAssertion) object).getAction().getModelSlot()
					.getTechnologyAdapter());
			if (tac != null && ((DataPropertyAssertion) object).getOntologyProperty() != null) {
				return tac.getIconForTechnologyObject(((DataPropertyAssertion) object).getOntologyProperty().getClass());
			}
			return null;
		} else if (object instanceof ObjectPropertyAssertion) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((ObjectPropertyAssertion) object).getAction()
					.getModelSlot().getTechnologyAdapter());
			if (tac != null && ((ObjectPropertyAssertion) object).getOntologyProperty() != null) {
				return tac.getIconForTechnologyObject(((ObjectPropertyAssertion) object).getOntologyProperty().getClass());
			}
			return null;
		} else if (object instanceof EditionAction) {
			if (object instanceof AddEditionPatternInstance) {
				return IconFactory.getImageIcon(EDITION_PATTERN_ICON, IconLibrary.DUPLICATE);
			} else if (object instanceof SelectEditionPatternInstance) {
				return IconFactory.getImageIcon(EDITION_PATTERN_ICON, IconLibrary.IMPORT);
			} else if (object instanceof MatchEditionPatternInstance) {
				return IconFactory.getImageIcon(EDITION_PATTERN_ICON, IconLibrary.SYNC);
			} else if (object instanceof AddToListAction) {
				return IconFactory.getImageIcon(LIST_ICON, IconLibrary.POSITIVE_MARKER);
			} else if (object instanceof RemoveFromListAction) {
				return IconFactory.getImageIcon(LIST_ICON, IconLibrary.NEGATIVE_MARKER);
			} else if (object instanceof DeclarePatternRole) {
				return DECLARE_PATTERN_ROLE_ICON;
			} else if (object instanceof AssignationAction) {
				return DECLARE_PATTERN_ROLE_ICON;
			} else if (object instanceof ExecutionAction) {
				return ACTION_SCHEME_ICON;
			} else if (object instanceof ConditionalAction) {
				return CONDITIONAL_ACTION_ICON;
			} else if (object instanceof IterationAction) {
				return ITERATION_ACTION_ICON;
			} else if (object instanceof FetchRequestIterationAction) {
				return ITERATION_ACTION_ICON;
			} else if (object instanceof DeleteAction) {
				PatternRole pr = ((DeleteAction) object).getPatternRole();
				if (pr != null) {
					ImageIcon baseIcon = iconForObject(pr);
					return IconFactory.getImageIcon(baseIcon, DELETE);
				}
				return DELETE_ICON;
			} else if (((EditionAction) object).getModelSlot() != null) {
				TechnologyAdapterController<?> tac = getTechnologyAdapterController(((EditionAction) object).getModelSlot()
						.getTechnologyAdapter());
				if (tac != null) {
					ImageIcon returned = tac.getIconForEditionAction((Class<? extends EditionAction<?, ?>>) object.getClass());
					if (returned != null) {
						return returned;
					} else {
						return tac.getTechnologyIcon();
					}
				}
			}
			return UNKNOWN_ICON;
		} else if (object instanceof EditionPattern) {
			return EDITION_PATTERN_ICON;
		} else if (object instanceof EditionSchemeParameter) {
			return EDITION_PATTERN_PARAMETER_ICON;
		} else if (object instanceof EditionScheme) {
			if (object instanceof ActionScheme) {
				return ACTION_SCHEME_ICON;
			} else if (object instanceof SynchronizationScheme) {
				return IconFactory.getImageIcon(VIRTUAL_MODEL_ICON, IconLibrary.SYNC);
			} else if (object instanceof CloningScheme) {
				return CLONING_SCHEME_ICON;
			} else if (object instanceof CreationScheme) {
				return CREATION_SCHEME_ICON;
			} else if (object instanceof NavigationScheme) {
				return NAVIGATION_SCHEME_ICON;
			} else if (object instanceof DeletionScheme) {
				return DELETION_SCHEME_ICON;
			} else if (object instanceof TechnologySpecificEditionScheme) {
				TechnologyAdapterController<?> tac = getTechnologyAdapterController(((TechnologySpecificEditionScheme) object)
						.getTechnologyAdapter());
				if (tac != null) {
					ImageIcon returned = tac.getIconForEditionAction((Class<? extends EditionAction<?, ?>>) object.getClass());
					if (returned != null) {
						return returned;
					} else {
						return tac.getTechnologyIcon();
					}
				}
			}
		} else if (object instanceof EditionPatternInstancePatternRole) {
			return EDITION_PATTERN_ICON;
		} else if (object instanceof PrimitivePatternRole) {
			return UNKNOWN_ICON;
		} else if (object instanceof OntologicObjectPatternRole && ((OntologicObjectPatternRole<?>) object).getModelSlot() != null) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((OntologicObjectPatternRole<?>) object).getModelSlot()
					.getTechnologyAdapter());
			if (tac != null) {
				Type accessedType = ((OntologicObjectPatternRole<?>) object).getType();
				Class accessedTypeBaseClass = TypeUtils.getBaseClass(accessedType);
				return tac.getIconForTechnologyObject(accessedTypeBaseClass);
			}
		} else if (object instanceof LocalizedDictionary) {
			return LOCALIZATION_ICON;
		} else if (object instanceof InspectorEntry) {
			if (object instanceof CheckboxInspectorEntry) {
				return CHECKBOX_ICON;
			} else if (object instanceof FloatInspectorEntry) {
				return SPINNER_ICON;
			} else if (object instanceof IntegerInspectorEntry) {
				return SPINNER_ICON;
			} else if (object instanceof TextAreaInspectorEntry) {
				return TEXT_AREA_ICON;
			} else if (object instanceof TextFieldInspectorEntry) {
				return TEXT_FIELD_ICON;
			}
		} else if (object instanceof TechnologyObject) {
			TechnologyAdapterController<?> tac = getTechnologyAdapterController(((TechnologyObject) object).getTechnologyAdapter());
			if (tac != null) {
				return tac.getIconForTechnologyObject(((TechnologyObject) object).getClass());
			}
			return null;
		}
		logger.warning("No icon for " + object.getClass());
		return UNKNOWN_ICON;
	}

	public static ImageIcon iconForObject(ViewPointResource object) {
		return VIEWPOINT_ICON;
	}

	public static ImageIcon iconForObject(VirtualModelResource object) {
		return VIRTUAL_MODEL_ICON;
	}

}

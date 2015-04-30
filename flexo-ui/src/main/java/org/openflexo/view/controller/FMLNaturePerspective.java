/**
 * 
 * Copyright (c) 2014, Openflexo
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

import java.util.logging.Logger;

import javax.swing.ImageIcon;

import org.openflexo.components.widget.FIBTechnologyBrowser;
import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.fml.FMLTechnologyAdapter;
import org.openflexo.foundation.fml.FlexoConcept;
import org.openflexo.foundation.fml.FlexoConceptNature;
import org.openflexo.foundation.fml.ViewPoint;
import org.openflexo.foundation.fml.ViewPointNature;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.VirtualModelNature;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;
import org.openflexo.view.ModuleView;

/**
 * A perspective representing all the resources interpretable by a {@link FMLTechnologyAdapter} according to specific natures.<br>
 * Those natures are handled by a specific technology adapter
 * 
 * @author sylvain
 * 
 * @param <TA>
 */
public abstract class FMLNaturePerspective extends TechnologyPerspective<FMLTechnologyAdapter> {

	static final Logger logger = Logger.getLogger(FMLNaturePerspective.class.getPackage().getName());

	private final TechnologyAdapter handlingTechnologyAdapter;
	private final ViewPointNature viewpointNature;
	private final VirtualModelNature virtualModelNature;
	private final FlexoConceptNature flexoConceptNature;

	public FMLNaturePerspective(ViewPointNature viewpointNature, VirtualModelNature virtualModelNature,
			FlexoConceptNature flexoConceptNature, FMLTechnologyAdapter fmlRTtechnologyAdapter,
			TechnologyAdapter handlingTechnologyAdapter, FlexoController controller) {
		super(fmlRTtechnologyAdapter, controller);
		this.handlingTechnologyAdapter = handlingTechnologyAdapter;
		this.viewpointNature = viewpointNature;
		this.virtualModelNature = virtualModelNature;
		this.flexoConceptNature = flexoConceptNature;
	}

	/**
	 * Return the technology adapter handling specific natures
	 * 
	 * @return
	 */
	public TechnologyAdapter getHandlingTechnologyAdapter() {
		return handlingTechnologyAdapter;
	}

	/**
	 * Return the technology adapter controller handling specific natures
	 * 
	 * @return
	 */
	public TechnologyAdapterController<?> getHandlingTechnologyAdapterController() {
		TechnologyAdapterControllerService tacService = getController().getApplicationContext().getTechnologyAdapterControllerService();
		return tacService.getTechnologyAdapterController(getHandlingTechnologyAdapter());
	}

	/**
	 * Internally called to make technology browser<br>
	 * Instead of creating a browser for each perspective, we try to share the same instance
	 * 
	 * @return
	 */
	@Override
	protected final FIBTechnologyBrowser<FMLTechnologyAdapter> makeTechnologyBrowser() {
		FIBTechnologyBrowser<FMLTechnologyAdapter> returned = getController().getSharedFMLBrowser();
		return returned;
	}

	public ViewPointNature getViewpointNature() {
		return viewpointNature;
	}

	public VirtualModelNature getVirtualModelNature() {
		return virtualModelNature;
	}

	public FlexoConceptNature getFlexoConceptNature() {
		return flexoConceptNature;
	}

	/**
	 * Overrides getIcon
	 * 
	 * @see org.openflexo.view.controller.model.FlexoPerspective#getActiveIcon()
	 */
	@Override
	public ImageIcon getActiveIcon() {
		return getController().iconForObject(getHandlingTechnologyAdapter());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean hasModuleViewForObject(FlexoObject object) {
		if (object instanceof ViewPoint && viewpointNature != null && viewpointNature.hasNature((ViewPoint) object)) {
			return true;
		}
		if (object instanceof VirtualModel && virtualModelNature != null && virtualModelNature.hasNature((VirtualModel) object)) {
			return true;
		}
		if (object instanceof FlexoConcept && flexoConceptNature != null && flexoConceptNature.hasNature((FlexoConcept) object)) {
			return true;
		}
		return false;
	}

	@Override
	public final ModuleView<?> createModuleViewForObject(FlexoObject object) {

		if (object instanceof ViewPoint) {
			return createModuleViewForViewPoint((ViewPoint) object);
		}
		if (object instanceof VirtualModel) {
			return createModuleViewForVirtualModel((VirtualModel) object);
		}
		if (object instanceof FlexoConcept) {
			return createModuleViewForFlexoConcept((FlexoConcept) object);
		}
		return super.createModuleViewForObject(object);
	}

	protected abstract ModuleView<ViewPoint> createModuleViewForViewPoint(ViewPoint viewpoint);

	protected abstract ModuleView<VirtualModel> createModuleViewForVirtualModel(VirtualModel virtualModel);

	protected abstract ModuleView<FlexoConcept> createModuleViewForFlexoConcept(FlexoConcept flexoConcept);

}
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
package org.openflexo.fml.rt.controller.view;

import org.openflexo.components.widget.CommonFIB;
import org.openflexo.fib.model.listener.FIBMouseClickListener;
import org.openflexo.fib.view.FIBView;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.view.FIBModuleView;
import org.openflexo.view.ModuleView;
import org.openflexo.view.controller.FlexoController;
import org.openflexo.view.controller.model.FlexoPerspective;

/**
 * This is the {@link ModuleView} representing a {@link VirtualModelInstance}
 * 
 * @author sguerin
 * 
 */
public class VirtualModelInstanceView extends FIBModuleView<VirtualModelInstance> implements FIBMouseClickListener {

	private final FlexoPerspective perspective;

	public VirtualModelInstanceView(VirtualModelInstance vmInstance, FlexoController controller, FlexoPerspective perspective) {
		super(vmInstance, controller, CommonFIB.VIRTUAL_MODEL_INSTANCE_VIEW_FIB);
		this.perspective = perspective;
	}

	@Override
	public FlexoPerspective getPerspective() {
		return perspective;
	}

	@Override
	public void mouseClicked(FIBView<?, ?, ?> view, int clickCount) {
		System.out.println("mouseClicked with " + view + " and " + clickCount);
		/*if (data instanceof FIBTableDynamicModel && ((FIBTableDynamicModel) data).selected instanceof FlexoModelObject && clickCount == 2) {
			FlexoObject o = (FlexoObject) ((FIBTableDynamicModel) data).selected;
			if (o instanceof ViewPoint || o instanceof FlexoConcept || o instanceof ExampleDiagram || o instanceof DiagramPalette) {
				getFlexoController().selectAndFocusObject(o);
			}
		}*/
	}

}
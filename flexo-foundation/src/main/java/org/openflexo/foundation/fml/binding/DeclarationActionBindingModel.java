/*
  * (c) Copyright 2014-2015 Openflexo
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
package org.openflexo.foundation.fml.binding;

import org.openflexo.antar.binding.BindingModel;
import org.openflexo.foundation.fml.editionaction.DeclarationAction;

/**
 * This is the {@link BindingModel} exposed by a {@link DeclarationAction}<br>
 * 
 * @author sylvain
 * 
 */
public class DeclarationActionBindingModel extends EditionActionBindingModel {

	private final DeclarationBindingVariable declarationBindingVariable;

	public DeclarationActionBindingModel(DeclarationAction<?> editionAction) {
		super(editionAction);
		declarationBindingVariable = new DeclarationBindingVariable(editionAction);
		addToBindingVariables(declarationBindingVariable);
	}

	@Override
	public DeclarationAction<?> getEditionAction() {
		return (DeclarationAction<?>) super.getEditionAction();
	}

}
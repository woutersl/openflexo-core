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
package org.openflexo.foundation.resource;

import org.openflexo.foundation.DataModification;
import org.openflexo.foundation.FlexoService.ServiceNotification;

/**
 * This {@link DataModification} is thrown when a {@link FlexoResource} has just been loaded
 * 
 * @author sylvain
 * 
 */
public class ResourceLoaded extends DataModification implements ServiceNotification {

	/**
	 * @param resource
	 */
	public <RD extends ResourceData<RD>> ResourceLoaded(FlexoResource<RD> resource, RD resourceData) {
		super("isLoaded", null, resourceData);
	}

}

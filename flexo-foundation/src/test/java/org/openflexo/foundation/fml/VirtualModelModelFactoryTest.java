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
package org.openflexo.foundation.fml;

import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.logging.Logger;

import org.junit.Test;
import org.openflexo.foundation.fml.VirtualModelModelFactory;
import org.openflexo.foundation.technologyadapter.DefaultTechnologyAdapterService;
import org.openflexo.foundation.technologyadapter.TechnologyAdapterService;
import org.openflexo.logging.FlexoLogger;
import org.openflexo.model.ModelContext;
import org.openflexo.model.ModelEntity;
import org.openflexo.model.exceptions.ModelDefinitionException;

/**
 * Test instanciation of VirtualModelModelFactory<br>
 * Here the model factory is instanciated with no TechnologyAdapters
 * 
 */
public class VirtualModelModelFactoryTest {

	private static final Logger logger = FlexoLogger.getLogger(VirtualModelModelFactoryTest.class.getPackage().getName());

	@Test
	public void testInstantiateVirtualModelModelFactory() {
		try {
			System.out.println("Instanciating ViewPointModelFactory");
			TechnologyAdapterService taService = DefaultTechnologyAdapterService.getNewInstance(null);
			VirtualModelModelFactory factory = new VirtualModelModelFactory(null, null, taService);
			ModelContext modelContext = factory.getModelContext();
			for (Iterator<ModelEntity> it = modelContext.getEntities(); it.hasNext();) {
				ModelEntity e = it.next();
				System.out.println("> Found " + e.getImplementedInterface());
			}
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
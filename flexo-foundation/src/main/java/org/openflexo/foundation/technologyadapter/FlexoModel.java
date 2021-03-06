/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Flexo-foundation, a component of the software infrastructure 
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

package org.openflexo.foundation.technologyadapter;

import org.openflexo.foundation.resource.ResourceData;

/**
 * This interface is implemented by all objects considered in Flexo Modelling Language as a model<br>
 * 
 * A model should be seen here as a very higher level than the classical vision of MOF. A model here is any kind of modelling element. It
 * covers all the kind of data or knowleges encoded in a particular technology. It can be a classical model (such as UML, EMF, MOF-like
 * modelling, or a file, or a database, a spreadsheet, a diagram, a document.<br>
 * 
 * A {@link FlexoModel} is conform to a {@link FlexoMetaModel}.
 * 
 * Its access it made available by the notion of {@link ModelSlot} provided by a {@link TechnologyAdapter} dedicated to a particular
 * technological space (a technology).
 * 
 * @author sylvain
 * 
 * @param <MM>
 */
public interface FlexoModel<M extends FlexoModel<M, MM>, MM extends FlexoMetaModel<MM>> extends /*StorageResourceData<M>,*/ResourceData<M> {
	/**
	 * Meta Model.
	 * 
	 * @return
	 */
	public MM getMetaModel();

	/**
	 * URI of Model.
	 * 
	 * @return
	 */
	public String getURI();

	public Object getObject(String objectURI);

	/**
	 * Return the {@link TechnologyAdapter} of technical space where related FlexoOntology exists
	 * 
	 * @return
	 */
	public TechnologyAdapter getTechnologyAdapter();

}

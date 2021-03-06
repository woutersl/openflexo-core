/**
 * 
 * Copyright (c) 2015, Openflexo
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

package org.openflexo.foundation.fml.controlgraph;

import java.util.ArrayList;
import java.util.List;

import org.openflexo.connie.BindingModel;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.fml.FMLRepresentationContext;
import org.openflexo.foundation.fml.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.fml.editionaction.AssignableAction;
import org.openflexo.foundation.fml.rt.action.FlexoBehaviourAction;
import org.openflexo.model.annotations.CloningStrategy;
import org.openflexo.model.annotations.CloningStrategy.StrategyType;
import org.openflexo.model.annotations.Embedded;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.toolbox.StringUtils;

/**
 * Encodes a sequence as a sequential definition of two control graphs
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(Sequence.SequenceImpl.class)
@XMLElement
public interface Sequence extends FMLControlGraph, FMLControlGraphOwner {

	@PropertyIdentifier(type = FMLControlGraph.class)
	public static final String CONTROL_GRAPH1_KEY = "controlGraph1";
	@PropertyIdentifier(type = FMLControlGraph.class)
	public static final String CONTROL_GRAPH2_KEY = "controlGraph2";

	@Getter(value = CONTROL_GRAPH1_KEY, inverse = FMLControlGraph.OWNER_KEY)
	@Embedded
	@CloningStrategy(StrategyType.CLONE)
	@XMLElement(context = "ControlGraph1_")
	public FMLControlGraph getControlGraph1();

	@Setter(CONTROL_GRAPH1_KEY)
	public void setControlGraph1(FMLControlGraph aControlGraph);

	@Getter(value = CONTROL_GRAPH2_KEY, inverse = FMLControlGraph.OWNER_KEY)
	@Embedded
	@CloningStrategy(StrategyType.CLONE)
	@XMLElement(context = "ControlGraph2_")
	public FMLControlGraph getControlGraph2();

	@Setter(CONTROL_GRAPH2_KEY)
	public void setControlGraph2(FMLControlGraph aControlGraph);

	/**
	 * When this sequence represents a sequence of more than two control graphs, resulting structure is a sequence of sequence of
	 * sequence... This method allows to retrieve a flattened list of all chained control graphs
	 * 
	 * @return a flattened list of all chained control graphs
	 */
	@Override
	public List<FMLControlGraph> getFlattenedSequence();

	public static abstract class SequenceImpl extends FMLControlGraphImpl implements Sequence {

		@Override
		public String getURI() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setControlGraph1(FMLControlGraph aControlGraph) {
			if (aControlGraph != null) {
				aControlGraph.setOwnerContext(CONTROL_GRAPH1_KEY);
			}
			performSuperSetter(CONTROL_GRAPH1_KEY, aControlGraph);
		}

		@Override
		public void setControlGraph2(FMLControlGraph aControlGraph) {
			if (aControlGraph != null) {
				aControlGraph.setOwnerContext(CONTROL_GRAPH2_KEY);
			}
			performSuperSetter(CONTROL_GRAPH2_KEY, aControlGraph);
		}

		@Override
		public void sequentiallyAppend(FMLControlGraph controlGraph) {

			getControlGraph2().sequentiallyAppend(controlGraph);
			getOwner().controlGraphChanged(this);
		}

		@Override
		public void reduce() {
			// We first store actual owning context
			FMLControlGraphOwner owner = getOwner();
			String ownerContext = getOwnerContext();

			// We reduce each control graphs
			if (getControlGraph1() instanceof FMLControlGraphOwner) {
				((FMLControlGraphOwner) getControlGraph1()).reduce();
			}
			if (getControlGraph2() instanceof FMLControlGraphOwner) {
				((FMLControlGraphOwner) getControlGraph2()).reduce();
			}

			if (getControlGraph1() instanceof EmptyControlGraph) {
				replaceWith(getControlGraph2(), owner, ownerContext);
			} else if (getControlGraph2() instanceof EmptyControlGraph) {
				replaceWith(getControlGraph1(), owner, ownerContext);
			}
		}

		@Override
		public FMLControlGraph getControlGraph(String ownerContext) {
			if (CONTROL_GRAPH1_KEY.equals(ownerContext)) {
				return getControlGraph1();
			} else if (CONTROL_GRAPH2_KEY.equals(ownerContext)) {
				return getControlGraph2();
			}
			return null;
		}

		@Override
		public void setControlGraph(FMLControlGraph controlGraph, String ownerContext) {

			if (CONTROL_GRAPH1_KEY.equals(ownerContext)) {
				setControlGraph1(controlGraph);
			} else if (CONTROL_GRAPH2_KEY.equals(ownerContext)) {
				setControlGraph2(controlGraph);
			}
		}

		@Override
		public BindingModel getBaseBindingModel(FMLControlGraph controlGraph) {
			if (controlGraph == getControlGraph1()) {
				return getBindingModel();
			} else if (controlGraph == getControlGraph2()) {
				// If control graph 1 declares a new variable, this variable should be added
				// to context of control graph 2 binding model
				if (getControlGraph1() instanceof AssignableAction) {
					return getControlGraph1().getInferedBindingModel();
				} else {
					return getBindingModel();
				}

				// return getControlGraph1().getInferedBindingModel();
			}
			return null;
		}

		@Override
		public List<FMLControlGraph> getFlattenedSequence() {
			List<FMLControlGraph> returned = new ArrayList<FMLControlGraph>();
			if (getControlGraph1() instanceof Sequence) {
				returned.addAll(((Sequence) getControlGraph1()).getFlattenedSequence());
			} else {
				returned.add(getControlGraph1());
			}
			if (getControlGraph2() instanceof Sequence) {
				returned.addAll(((Sequence) getControlGraph2()).getFlattenedSequence());
			} else {
				returned.add(getControlGraph2());
			}
			return returned;
		}

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);
			boolean isFirst = true;
			for (FMLControlGraph cg : getFlattenedSequence()) {
				if (!isFirst) {
					out.append(StringUtils.LINE_SEPARATOR, context);
				}
				out.append(cg.getFMLRepresentation(context), context);
				isFirst = false;
			}
			return out.toString();
		}

		@Override
		public Object execute(FlexoBehaviourAction<?, ?, ?> action) throws FlexoException {
			getControlGraph1().execute(action);
			getControlGraph2().execute(action);
			return null;
		}

		@Override
		public void setOwner(FMLControlGraphOwner owner) {
			performSuperSetter(OWNER_KEY, owner);
			if (getControlGraph1() != null) {

				// System.out.println("WAS: " + getControlGraph1().getInferedBindingModel());
				// resetInferedBindingModel();
				// getControlGraph1().resetInferedBindingModel();
				// System.out.println("NOW: " + getControlGraph1().getInferedBindingModel());
				getControlGraph1().getBindingModel().setBaseBindingModel(getBaseBindingModel(getControlGraph1()));
				// getControlGraph1().getBindingModel().setBaseBindingModel(getInferedBindingModel());
			}
			if (getControlGraph2() != null) {
				getControlGraph2().getBindingModel().setBaseBindingModel(getBaseBindingModel(getControlGraph2()));
				// getControlGraph2().getBindingModel().setBaseBindingModel(getInferedBindingModel());

				/*if (getBaseBindingModel(getControlGraph2()) != getInferedBindingModel()) {
					System.out.println("Ya un pb la !!!!");

					if (getBaseBindingModel(getControlGraph2()) == getControlGraph1().getInferedBindingModel()) {
						System.out.println("c'est bien ca, c'est un " + getControlGraph1().getInferedBindingModel().getClass());
						System.out.println("Base BM = " + getControlGraph1().getInferedBindingModel().getBaseBindingModel());
					}

					// System.exit(-1);
				}*/
			}
		}
	}
}

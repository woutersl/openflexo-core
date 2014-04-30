/*
 * (c) Copyright 2013-2014 Openflexo
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
package org.openflexo.vpm.controller;

import java.awt.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoObject;
import org.openflexo.foundation.action.PasteAction.DefaultPastingContext;
import org.openflexo.foundation.action.PasteAction.PasteHandler;
import org.openflexo.foundation.action.PasteAction.PastingContext;
import org.openflexo.foundation.resource.PamelaResource;
import org.openflexo.foundation.view.ActorReference;
import org.openflexo.foundation.view.FlexoConceptInstance;
import org.openflexo.foundation.view.ModelSlotInstance;
import org.openflexo.foundation.view.VirtualModelInstance;
import org.openflexo.foundation.viewpoint.FlexoRole.RoleCloningStrategy;
import org.openflexo.foundation.viewpoint.PrimitiveRole;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.exceptions.ModelExecutionException;
import org.openflexo.model.factory.Clipboard;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.toolbox.HasPropertyChangeSupport;

/**
 * Paste Handler suitable for pasting something into a VirtualModelInstance
 * 
 * @author sylvain
 * 
 */
public class VirtualModelInstancePasteHandler implements PasteHandler<VirtualModelInstance> {

	private static final Logger logger = Logger.getLogger(VirtualModelInstancePasteHandler.class.getPackage().getName());

	public static final String COPY_SUFFIX = "-copy";

	@Override
	public boolean declarePolymorphicPastingContexts() {
		return true;
	}

	@Override
	public PastingContext<VirtualModelInstance> retrievePastingContext(FlexoObject focusedObject, List<FlexoObject> globalSelection,
			Clipboard clipboard, Event event) {

		if (focusedObject instanceof VirtualModelInstance) {
			return new DefaultPastingContext<VirtualModelInstance>((VirtualModelInstance) focusedObject, event);

		}

		if (focusedObject instanceof FlexoConceptInstance) {
			return new DefaultPastingContext<VirtualModelInstance>(((FlexoConceptInstance) focusedObject).getVirtualModelInstance(), event);

		}

		return null;
	}

	@Override
	public void prepareClipboardForPasting(Clipboard clipboard, PastingContext<VirtualModelInstance> pastingContext) {

		System.out.println("********** prepareClipboardForPasting in " + pastingContext);

		// First put all FCI in a list
		List<FlexoConceptInstance> fciList = new ArrayList<FlexoConceptInstance>();

		if (clipboard.isSingleObject()) {
			if (clipboard.getSingleContents() instanceof FlexoConceptInstance) {
				fciList.add((FlexoConceptInstance) clipboard.getSingleContents());
			}
		} else {
			for (Object o : clipboard.getMultipleContents()) {
				if (o instanceof FlexoConceptInstance) {
					fciList.add((FlexoConceptInstance) o);
				}
			}
		}

		try {

			for (ModelSlotInstance<?, ?> msi : pastingContext.getPastingPointHolder().getModelSlotInstances()) {
				System.out.println("Voyons pour le model slot " + msi);
				if (msi.getAccessedResourceData() != null && msi.getAccessedResourceData().getResource() instanceof PamelaResource) {
					System.out.println("Hop, je m'occupe de la resource " + msi.getResource());
					PamelaResource resource = (PamelaResource) msi.getAccessedResourceData().getResource();
					ModelFactory factory = resource.getFactory();
					List<Object> objectsToClone = new ArrayList<Object>();
					for (FlexoConceptInstance fci : fciList) {
						for (ActorReference<?> actor : fci.getActors()) {
							if (actor.getFlexoRole().getModelSlot() == msi.getModelSlot()
									&& actor.getFlexoRole().getCloningStrategy() == RoleCloningStrategy.Clone
									&& (!(actor.getFlexoRole() instanceof PrimitiveRole))) {
								objectsToClone.add(actor.getModellingElement());
							}
						}
					}
					System.out.println("Pour la resource " + msi.getResource());
					System.out.println("Je dois cloner tous ces objects:");
					for (Object o : objectsToClone) {
						System.out.println("> " + o);
					}

					if (objectsToClone.size() > 0) {
						// Some objects needs to be cloned in this model slot
						Clipboard modelSlotClipboard = factory.copy(objectsToClone.toArray());

						System.out.println("Pour la resource " + msi.getResource());
						System.out.println("" + modelSlotClipboard.debug());

						Object pastingPointHolder = getModelSlotSpecificPastingPointHolder(msi, pastingContext);

						if (factory.isPastable(modelSlotClipboard, pastingPointHolder)) {

							Map<Object, Object> copiedObjects = new HashMap<Object, Object>();
							Object copy = factory.paste(modelSlotClipboard, pastingPointHolder);
							if (modelSlotClipboard.isSingleObject()) {
								copiedObjects.put(modelSlotClipboard.getOriginalContents()[0], copy);
								System.out.println("Pour " + modelSlotClipboard.getOriginalContents()[0] + " j'ai maintenant " + copy);
							}

							// Now replace all ActorReferences !!!
							for (FlexoConceptInstance fci : fciList) {
								for (ActorReference actor : fci.getActors()) {
									if (actor.getFlexoRole().getModelSlot() == msi.getModelSlot()
											&& actor.getFlexoRole().getCloningStrategy() == RoleCloningStrategy.Clone
											&& (!(actor.getFlexoRole() instanceof PrimitiveRole))) {
										System.out.println("Maintenant, j'essaie de remplacer " + actor.getModellingElement());
										System.out.println("Par: " + copiedObjects.get(actor.getModellingElement()));
										actor.setModellingElement(copiedObjects.get(actor.getModellingElement()));
									}
								}
							}

						}
					}

				} else {
					if (msi.getAccessedResourceData() != null && !(msi.getAccessedResourceData().getResource() instanceof PamelaResource)) {
						logger.severe("Unexpected resource " + msi.getAccessedResourceData().getResource());
					}
				}
			}

		} catch (ModelExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModelDefinitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void finalizePasting(Clipboard clipboard, PastingContext<VirtualModelInstance> pastingContext) {

		System.out.println("Trying to notify.........");

		for (ModelSlotInstance<?, ?> msi : pastingContext.getPastingPointHolder().getModelSlotInstances()) {
			Object pastingPointHolder = getModelSlotSpecificPastingPointHolder(msi, pastingContext);

			// Well, not easy to understand here
			// Some modelling elements may have been added to the resource, but they were added BEFORE federation.
			// Thus, Model/View modelling paradigm may have lead some view to be in sync with a non-federated context
			// We renotify again to be sure that all view might react to federation activation
			if (pastingPointHolder instanceof HasPropertyChangeSupport) {
				System.out.println("notify " + pastingPointHolder);
				((HasPropertyChangeSupport) pastingPointHolder).getPropertyChangeSupport().firePropertyChange("invalidate", null,
						pastingPointHolder);
			}
		}

		System.out.println("Done notify.........");
	}

	public Object getModelSlotSpecificPastingPointHolder(ModelSlotInstance<?, ?> modelSlotInstance, PastingContext<?> pastingContext) {
		return pastingContext.getPastingPointHolder();
	}
}
/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2011-2012, AgileBirds
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

package org.openflexo.foundation.utils;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.FlexoProjectObject;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.logging.FlexoLogger;

/**
 * @author gpolet Created on 3 oct. 2005
 */
public class FlexoObjectIDManager {

	private final FlexoProject project;

	private static final Logger logger = FlexoLogger.getLogger(FlexoObjectIDManager.class.getPackage().toString());

	private List<FlexoProjectObject> badObjects;
	private Map<Long, FlexoProjectObject> used;

	/**
	 * 
	 */
	public FlexoObjectIDManager(FlexoProject project) {
		this.project = project;
	}

	public List<FlexoProjectObject> checkProject(boolean fixProblems) {

		// First load all unloaded resources
		for (FlexoResource<?> r : project.getResource().getContents()) {
			try {
				r.getResourceData(null);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceLoadingCancelledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FlexoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Iterate on all objects to validate
		used = new Hashtable<Long, FlexoProjectObject>();
		badObjects = new Vector<FlexoProjectObject>();
		Vector<FlexoProjectObject> objectsToUnregister = new Vector<FlexoProjectObject>();

		// TODO: implement this
		logger.warning("Not implemented yet");

		/*for (FlexoProjectObject object : new Vector<FlexoProjectObject>(project.getAllRegisteredObjects())) {
			if (object.getProject() == project) {
				if (object.getXMLResourceData() == null) {
					continue;
				}
				if (object.getXMLResourceData().getFlexoResource() == null) {
					continue;
				}
				if (object.getXMLResourceData().getFlexoResource().getResourceData() == object.getXMLResourceData()) {
					testAndSetID(object, fixProblems);
				} else {
					if (logger.isLoggable(Level.WARNING)) {
						logger.warning("This object " + object + " is registered but should not!");
					}
					objectsToUnregister.add(object);
				}
			}
		}

		for (FlexoProjectObject obj : objectsToUnregister) {
			project.unregister(obj);
		}*/

		/*for (FlexoProjectObject obj : badObjects) {
			if (obj instanceof FlexoXMLSerializableObject) {
				((FlexoXMLSerializableObject)obj).getXMLResourceData().setIsModified();
			}
		}*/

		if (logger.isLoggable(Level.INFO)) {
			logger.info("Found " + badObjects.size() + " objects that have an incorrect flexoID");
		}
		return badObjects;
	}

	private void testAndSetID(FlexoProjectObject o, boolean fixProblems) {
		/*if (o.isCreatedByCloning()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("An object was found with status beingCloned " + o.getXMLRepresentation());
			}
		}*/
		if (o.getFlexoID() < 0 || o.getFlexoID() > project.getLastUniqueID()) {
			// The next line is commented because if there are issues with flexoID, it is likely that the XML encoding of the object used to
			// retrieve the XML representation will also fail, since it also works with the flexoID
			// This is the kind of message we get: "org.openflexo.xmlcode.DuplicateSerializationIdentifierException: Found the same
			// identifier 'GUI_-1' for different objects: class org.openflexo.foundation.wkf.ws.OutPort has the same serialization
			// identifier than class org.openflexo.foundation.wkf.edge.InternalMessageOutEdge
			// I was serializing 'IncomingInternalMessageOutEdge' on entity org.openflexo.foundation.wkf.edge.InternalMessageOutEdge (at
			// org.openflexo.xmlcode.XMLCoder.buildNewElementFrom(XMLCoder.java:1397))"
			/*if (logger.isLoggable(Level.WARNING))
			    logger.warning("Found an object with an invalid ID: " + o.getXMLRepresentation());*/
			if (fixProblems) {
				o.setFlexoID(o.getProject().getNewFlexoID());
			}
			badObjects.add(o);
		}
		FlexoProjectObject old = used.put(new Long(o.getFlexoID()), o);
		if (old != null && old != o) {
			long newID = o.getProject().getNewFlexoID();
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Found two different objects with the same flexoID:" + o.getFlexoID() + " Object1: "
						+ old.getClass().getName() + "[" + old.toString() + "]" + " and Object2:" + o.getClass().getName() + "["
						+ o.toString() + "] Replace id with " + newID);
			}
			if (fixProblems) {
				o.setFlexoID(newID);
			}
			badObjects.add(o);
		}
	}
}

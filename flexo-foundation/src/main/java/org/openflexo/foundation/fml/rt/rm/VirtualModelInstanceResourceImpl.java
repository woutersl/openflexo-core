/**
 * 
 * Copyright (c) 2014, Openflexo
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

package org.openflexo.foundation.fml.rt.rm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.IOFlexoException;
import org.openflexo.foundation.InconsistentDataException;
import org.openflexo.foundation.InvalidModelDefinitionException;
import org.openflexo.foundation.InvalidXMLException;
import org.openflexo.foundation.fml.VirtualModel;
import org.openflexo.foundation.fml.rm.VirtualModelResource;
import org.openflexo.foundation.fml.rt.FMLRTTechnologyAdapter;
import org.openflexo.foundation.fml.rt.View;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstanceModelFactory;
import org.openflexo.foundation.resource.FileFlexoIODelegate;
import org.openflexo.foundation.resource.FileFlexoIODelegate.FileFlexoIODelegateImpl;
import org.openflexo.foundation.resource.FlexoFileNotFoundException;
import org.openflexo.foundation.resource.PamelaResourceImpl;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.foundation.utils.XMLUtils;
import org.openflexo.model.ModelContextLibrary;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.AccessibleProxyObject;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.toolbox.IProgress;
import org.openflexo.toolbox.StringUtils;

/**
 * Default implementation for {@link VirtualModelInstanceResource}
 * 
 * 
 * @author Sylvain
 * 
 */
public abstract class VirtualModelInstanceResourceImpl extends PamelaResourceImpl<VirtualModelInstance, VirtualModelInstanceModelFactory>
		implements VirtualModelInstanceResource, AccessibleProxyObject {

	static final Logger logger = Logger.getLogger(VirtualModelInstanceResourceImpl.class.getPackage().getName());

	/*private static VirtualModelInstanceModelFactory VIRTUAL_MODEL_INSTANCE_FACTORY;

	static {
		try {
			VIRTUAL_MODEL_INSTANCE_FACTORY = new VirtualModelInstanceModelFactory();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
	}*/

	public static VirtualModelInstanceResource makeVirtualModelInstanceResource(String name, VirtualModel virtualModel, View view) {
		try {
			ModelFactory factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class,
					VirtualModelInstanceResource.class));
			VirtualModelInstanceResourceImpl returned = (VirtualModelInstanceResourceImpl) factory
					.newInstance(VirtualModelInstanceResource.class);
			String baseName = name;

			FileFlexoIODelegate delegate = (FileFlexoIODelegate) ((ViewResource) view.getResource()).getFlexoIODelegate();

			File xmlFile = new File(delegate.getFile().getParentFile(), baseName + VirtualModelInstanceResource.VIRTUAL_MODEL_SUFFIX);
			returned.setFlexoIODelegate(FileFlexoIODelegateImpl.makeFileFlexoIODelegate(xmlFile, factory));
			returned.setProject(view.getProject());
			returned.setFactory(new VirtualModelInstanceModelFactory(returned, view.getProject().getServiceManager().getEditingContext(),
					view.getProject().getServiceManager().getTechnologyAdapterService()));
			returned.setName(name);
			returned.setURI(view.getResource().getURI() + "/" + baseName);
			returned.setVirtualModelResource((VirtualModelResource) virtualModel.getResource());
			returned.setServiceManager(view.getProject().getServiceManager());
			view.getResource().addToContents(returned);
			view.getResource().notifyContentsAdded(returned);
			return returned;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static VirtualModelInstanceResource retrieveVirtualModelInstanceResource(File virtualModelInstanceFile, ViewResource viewResource) {
		try {
			ModelFactory factory = new ModelFactory(ModelContextLibrary.getCompoundModelContext(FileFlexoIODelegate.class,
					VirtualModelInstanceResource.class));
			VirtualModelInstanceResourceImpl returned = (VirtualModelInstanceResourceImpl) factory
					.newInstance(VirtualModelInstanceResource.class);
			String baseName = virtualModelInstanceFile.getName().substring(0,
					virtualModelInstanceFile.getName().length() - VirtualModelInstanceResource.VIRTUAL_MODEL_SUFFIX.length());

			FileFlexoIODelegate delegate = (FileFlexoIODelegate) (viewResource.getFlexoIODelegate());

			File xmlFile = new File(delegate.getFile().getParentFile(), baseName + VirtualModelInstanceResource.VIRTUAL_MODEL_SUFFIX);
			FileFlexoIODelegate fileIODelegate = factory.newInstance(FileFlexoIODelegate.class);
			returned.setFlexoIODelegate(fileIODelegate);
			fileIODelegate.setFile(xmlFile);
			returned.setProject(viewResource.getProject());
			returned.setFactory(new VirtualModelInstanceModelFactory(returned, viewResource.getProject().getServiceManager()
					.getEditingContext(), viewResource.getProject().getServiceManager().getTechnologyAdapterService()));
			returned.setName(baseName);
			returned.setURI(viewResource.getURI() + "/" + baseName);
			VirtualModelInstanceInfo vmiInfo = findVirtualModelInstanceInfo(xmlFile, "VirtualModelInstance");
			if (vmiInfo == null) {
				// Unable to retrieve infos, just abort
				return null;
			}

			if (StringUtils.isNotEmpty(vmiInfo.virtualModelURI)) {
				if (viewResource != null && viewResource.getViewPoint() != null
						&& viewResource.getViewPoint().getVirtualModelNamed(vmiInfo.virtualModelURI) != null) {
					returned.setVirtualModelResource((VirtualModelResource) viewResource.getViewPoint()
							.getVirtualModelNamed(vmiInfo.virtualModelURI).getResource());
				}
			}
			viewResource.addToContents(returned);
			viewResource.notifyContentsAdded(returned);
			returned.setServiceManager(viewResource.getProject().getServiceManager());
			return returned;
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public VirtualModelInstance getVirtualModelInstance() {
		try {
			return getResourceData(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ResourceLoadingCancelledException e) {
			e.printStackTrace();
		} catch (FlexoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Class<VirtualModelInstance> getResourceDataClass() {
		return VirtualModelInstance.class;
	}

	protected static class VirtualModelInstanceInfo {
		public String virtualModelURI;
		public String name;
	}

	protected static VirtualModelInstanceInfo findVirtualModelInstanceInfo(File virtualModelInstanceFile, String searchedRootXMLTag) {
		Document document;
		try {
			logger.fine("Try to find infos for " + virtualModelInstanceFile);

			String baseName = virtualModelInstanceFile.getName().substring(0,
					virtualModelInstanceFile.getName().length() - VirtualModelInstanceResource.VIRTUAL_MODEL_SUFFIX.length());

			if (virtualModelInstanceFile.exists()) {
				document = XMLUtils.readXMLFile(virtualModelInstanceFile);
				Element root = XMLUtils.getElement(document, searchedRootXMLTag);
				if (root != null) {
					VirtualModelInstanceInfo returned = new VirtualModelInstanceInfo();
					returned.name = baseName;
					Iterator<Attribute> it = root.getAttributes().iterator();
					while (it.hasNext()) {
						Attribute at = it.next();
						if (at.getName().equals("virtualModelURI")) {
							logger.fine("Returned " + at.getValue());
							returned.virtualModelURI = at.getValue();
						}
					}
					return returned;
				}
			} else {
				logger.warning("Cannot find file: " + virtualModelInstanceFile.getAbsolutePath());
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.fine("Returned null");
		return null;
	}

	@Override
	public VirtualModelInstance loadResourceData(IProgress progress) throws FlexoFileNotFoundException, IOFlexoException,
			InvalidXMLException, InconsistentDataException, InvalidModelDefinitionException {
		VirtualModelInstance returned = super.loadResourceData(progress);
		// We notify a deserialization start on ViewPoint AND VirtualModel, to avoid addToVirtualModel() and setViewPoint() to notify
		// UndoManager
		boolean containerWasDeserializing = getContainer().isDeserializing();
		if (!containerWasDeserializing) {
			getContainer().startDeserializing();
		}
		startDeserializing();
		getContainer().getView().addToVirtualModelInstances(returned);
		returned.clearIsModified();
		/*if (returned.isSynchronizable()) {
			returned.synchronize(null);
		}*/
		// And, we notify a deserialization stop
		stopDeserializing();
		if (!containerWasDeserializing) {
			getContainer().stopDeserializing();
		}

		/*if (!getContainer().isDeserializing()) {
			if (getLoadedResourceData() != null && getLoadedResourceData().isSynchronizable()) {
				getLoadedResourceData().synchronize(null);
			}
		}*/

		return returned;
	}

	@Override
	public void setLoading(boolean isLoading) {
		super.setLoading(isLoading);
		// Just after the loading occurs, apply synchronization.
		if (!isLoading()) {
			if (getLoadedResourceData() != null && getLoadedResourceData().isSynchronizable()) {
				getLoadedResourceData().synchronize(null);
			}
		}
	}

	@Override
	public FMLRTTechnologyAdapter getTechnologyAdapter() {
		if (getServiceManager() != null) {
			return getServiceManager().getTechnologyAdapterService().getTechnologyAdapter(FMLRTTechnologyAdapter.class);
		}
		return null;
	}

	@Override
	public ViewResource getContainer() {
		return (ViewResource) performSuperGetter(CONTAINER);
	}
}

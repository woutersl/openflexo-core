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

package org.openflexo.foundation.technologyadapter;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoException;
import org.openflexo.foundation.fml.rt.FreeModelSlotInstance;
import org.openflexo.foundation.fml.rt.View;
import org.openflexo.foundation.fml.rt.VirtualModelInstance;
import org.openflexo.foundation.fml.rt.VirtualModelInstanceModelFactory;
import org.openflexo.foundation.fml.rt.action.CreateVirtualModelInstance;
import org.openflexo.foundation.fml.rt.action.ModelSlotInstanceConfiguration;
import org.openflexo.foundation.resource.FlexoResourceCenter;
import org.openflexo.foundation.resource.FlexoResourceCenterService;
import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.resource.ResourceLoadingCancelledException;
import org.openflexo.localization.FlexoLocalization;
import org.openflexo.toolbox.StringUtils;

/**
 * This class is used to stored the configuration of a {@link FreeModelSlot} which has to be instantiated
 * 
 * 
 * @author sylvain, vincent
 * 
 */
public class FreeModelSlotInstanceConfiguration<RD extends ResourceData<RD> & TechnologyObject<?>, MS extends FreeModelSlot<RD>> extends
		ModelSlotInstanceConfiguration<MS, RD> {

	private static final Logger logger = Logger.getLogger(FreeModelSlotInstanceConfiguration.class.getPackage().getName());

	protected List<ModelSlotInstanceConfigurationOption> options;

	private FlexoResourceCenter<?> resourceCenter;
	private TechnologyAdapterResource<RD, ?> resource;
	private String resourceUri;
	private String relativePath;
	private String filename;

	protected FreeModelSlotInstanceConfiguration(MS ms, CreateVirtualModelInstance<?> action) {
		super(ms, action);
		FlexoResourceCenterService rcService = action.getFocusedObject().getViewPoint().getViewPointLibrary().getServiceManager()
				.getResourceCenterService();
		if (rcService.getResourceCenters().size() > 0) {
			resourceCenter = rcService.getResourceCenters().get(0);
		}
		options = new ArrayList<ModelSlotInstanceConfiguration.ModelSlotInstanceConfigurationOption>();
		options.add(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
		options.add(DefaultModelSlotInstanceConfigurationOption.CreatePrivateNewResource);
		options.add(DefaultModelSlotInstanceConfigurationOption.CreateSharedNewResource);
		if (!ms.getIsRequired()) {
			options.add(DefaultModelSlotInstanceConfigurationOption.LeaveEmpty);
		}
		setOption(DefaultModelSlotInstanceConfigurationOption.SelectExistingResource);
	}

	/*@Override
	public void setOption(org.openflexo.foundation.fml.rt.action.ModelSlotInstanceConfiguration.ModelSlotInstanceConfigurationOption option) {
		super.setOption(option);
		if (option == DefaultModelSlotInstanceConfigurationOption.SelectExistingResource) {
			resourceUri = null;
			relativePath = null;
			filename = null;
		}
	}*/

	@Override
	public List<ModelSlotInstanceConfigurationOption> getAvailableOptions() {
		return options;
	}

	@Override
	public FreeModelSlotInstance<RD, MS> createModelSlotInstance(VirtualModelInstance vmInstance, View view) {
		VirtualModelInstanceModelFactory factory = vmInstance.getFactory();
		FreeModelSlotInstance<RD, MS> returned = factory.newInstance(FreeModelSlotInstance.class);
		returned.setModelSlot(getModelSlot());
		returned.setVirtualModelInstance(vmInstance);
		configureModelSlotInstance(returned);
		return returned;
	}

	protected FreeModelSlotInstance<RD, MS> configureModelSlotInstance(FreeModelSlotInstance<RD, MS> msInstance) {
		try {
			if (getOption() == DefaultModelSlotInstanceConfigurationOption.SelectExistingResource) {
				if (resource != null) {
					msInstance.setAccessedResourceData(getResource().getResourceData(null));
				} else {
					logger.warning("No resource for model slot " + getModelSlot());
				}
			} else if (getOption() == DefaultModelSlotInstanceConfigurationOption.CreatePrivateNewResource) {
				resource = createProjectSpecificEmptyResource(msInstance, getModelSlot());
				System.out.println("***** modelResource = " + resource);
				if (resource != null) {
					msInstance.setAccessedResourceData(getResource().getResourceData(null));
				} else {
					logger.warning("Could not create ProjectSpecificEmtpyResource for model slot " + getModelSlot());
				}
			}/* else if (getOption() == DefaultModelSlotInstanceConfigurationOption.CreateSharedNewResource) {
				// resource = createSharedEmptyResource(msInstance, getModelSlot());
				if (resource != null) {
					msInstance.setResourceData(getResource().getResourceData(null));
				}
				} else {
				logger.warning("Could not create SharedEmptyResource for model slot " + getModelSlot());
				}*/
			return msInstance;
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
		return null;
	}

	private TechnologyAdapterResource<RD, ?> createProjectSpecificEmptyResource(FreeModelSlotInstance<RD, MS> msInstance, MS modelSlot) {
		return modelSlot.createProjectSpecificEmptyResource(getView(msInstance), getFilename(), getResourceUri());
	}

	private TechnologyAdapterResource<RD, ?> createSharedEmptyResource(FreeModelSlotInstance<RD, MS> msInstance, MS modelSlot) {
		return modelSlot.createSharedEmptyResource(getResourceCenter(), getRelativePath(), getFilename(), getResourceUri());
	}

	private View getView(FreeModelSlotInstance<RD, MS> msInstance) {
		View view = null;
		if (msInstance.getView() != null) {
			view = msInstance.getView();
		} else if (msInstance.getVirtualModelInstance() != null && msInstance.getVirtualModelInstance().getView() != null) {
			view = msInstance.getVirtualModelInstance().getView();
		}
		return view;
	}

	public FlexoResourceCenter<?> getResourceCenter() {
		return resourceCenter;
	}

	public void setResourceCenter(FlexoResourceCenter<?> resourceCenter) {
		if (resourceCenter == null || !resourceCenter.equals(this.resourceCenter)) {
			FlexoResourceCenter<?> oldValue = this.resourceCenter;
			this.resourceCenter = resourceCenter;
			getPropertyChangeSupport().firePropertyChange("resourceCenter", oldValue, resourceCenter);
		}
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		if (!resourceUri.equals(this.resourceUri)) {
			String oldValue = this.resourceUri;
			this.resourceUri = resourceUri;
			getPropertyChangeSupport().firePropertyChange("resourceUri", oldValue, resourceUri);
		}
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		if (!relativePath.equals(this.relativePath)) {
			String oldValue = this.relativePath;
			this.relativePath = relativePath;
			getPropertyChangeSupport().firePropertyChange("relativePath", oldValue, relativePath);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		System.out.println("******** setFileName with " + filename);
		if (!filename.equals(this.filename)) {
			String oldValue = this.filename;
			this.filename = filename;
			getPropertyChangeSupport().firePropertyChange("filename", oldValue, filename);
		}
	}

	@Override
	public TechnologyAdapterResource<RD, ?> getResource() {
		return resource;
	}

	public void setResource(TechnologyAdapterResource<RD, ?> resource) {
		System.out.println("Hop, on set la resource a " + resource);
		System.out.println("filename=" + getFilename());
		if (resource != this.resource) {
			TechnologyAdapterResource<RD, ?> oldValue = this.resource;
			this.resource = resource;
			getPropertyChangeSupport().firePropertyChange("resource", oldValue, resource);
		}
		System.out.println("filename=" + getFilename());
	}

	@Override
	public boolean isValidConfiguration() {
		if (!super.isValidConfiguration()) {
			return false;
		}
		if (getOption() == DefaultModelSlotInstanceConfigurationOption.SelectExistingResource) {
			if (getResource() == null) {
				setErrorMessage(FlexoLocalization.localizedForKey("no_resource_selected"));
				return false;
			}
			return true;
		} else if (getOption() == DefaultModelSlotInstanceConfigurationOption.CreatePrivateNewResource) {
			if (StringUtils.isEmpty(getResourceUri())) {
				setErrorMessage(FlexoLocalization.localizedForKey("please_supply_valid_uri"));
				return false;
			}
			try {
				new URL(getResourceUri());
			} catch (MalformedURLException e) {
				setErrorMessage(FlexoLocalization.localizedForKey("malformed_uri"));
				return false;
			}
			if (StringUtils.isEmpty(getRelativePath())) {
				setErrorMessage(FlexoLocalization.localizedForKey("please_supply_valid_relative_path"));
				return false;
			}
			return checkValidFileName();
		} else if (getOption() == DefaultModelSlotInstanceConfigurationOption.CreateSharedNewResource) {
			if (getResourceCenter() == null) {
				setErrorMessage(FlexoLocalization.localizedForKey("please_select_a_resource_center"));
				return false;
			}
			if (StringUtils.isEmpty(getResourceUri())) {
				setErrorMessage(FlexoLocalization.localizedForKey("please_supply_valid_uri"));
				return false;
			}
			try {
				new URL(getResourceUri());
			} catch (MalformedURLException e) {
				setErrorMessage(FlexoLocalization.localizedForKey("malformed_uri"));
				return false;
			}
			if (StringUtils.isEmpty(getRelativePath())) {
				setErrorMessage(FlexoLocalization.localizedForKey("please_supply_valid_relative_path"));
				return false;
			}
			return checkValidFileName();
		} else if (getOption() == DefaultModelSlotInstanceConfigurationOption.LeaveEmpty) {
			if (getModelSlot().getIsRequired()) {
				setErrorMessage(FlexoLocalization.localizedForKey("resource_is_required"));
				return false;
			}
			return true;
		}
		return false;
	}

	protected boolean checkValidFileName() {
		if (StringUtils.isEmpty(getFilename())) {
			setErrorMessage(FlexoLocalization.localizedForKey("please_supply_valid_file_name"));
			return false;
		}
		return true;
	}
}

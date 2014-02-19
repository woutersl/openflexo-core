/*
 * (c) Copyright 2010-2011 AgileBirds
 * (c) Copyright 2012-2013 Openflexo
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
package org.openflexo.foundation.viewpoint;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.openflexo.antar.binding.BindingModel;
import org.openflexo.antar.binding.BindingVariable;
import org.openflexo.foundation.resource.ResourceData;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.foundation.utils.XMLUtils;
import org.openflexo.foundation.validation.ValidationModel;
import org.openflexo.foundation.viewpoint.FMLRepresentationContext.FMLRepresentationOutput;
import org.openflexo.foundation.viewpoint.binding.FlexoConceptBindingFactory;
import org.openflexo.foundation.viewpoint.dm.VirtualModelCreated;
import org.openflexo.foundation.viewpoint.dm.VirtualModelDeleted;
import org.openflexo.foundation.viewpoint.rm.ViewPointResource;
import org.openflexo.foundation.viewpoint.rm.ViewPointResourceImpl;
import org.openflexo.foundation.viewpoint.rm.VirtualModelResource;
import org.openflexo.model.annotations.Adder;
import org.openflexo.model.annotations.Getter;
import org.openflexo.model.annotations.Getter.Cardinality;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.PropertyIdentifier;
import org.openflexo.model.annotations.Remover;
import org.openflexo.model.annotations.Setter;
import org.openflexo.model.annotations.XMLAttribute;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.toolbox.ChainedCollection;
import org.openflexo.toolbox.FlexoVersion;
import org.openflexo.toolbox.StringUtils;

/**
 * In the Openflexo Viewpoint Architecture a {@link ViewPoint} is the metamodel level of model federation.<br>
 * A {@link View} (run-time context of model federation) is conform to a {@link ViewPoint}.<br>
 * 
 * A viewpoint partitions the set preoccupations of the stakeholders so that issues related to such preoccupation subsets can be addressed
 * separately. Viewpoints provide the convention, rules and modelling technologies for constructing, presenting and analysing Views. It can
 * address one or several existing sources of informations (in which we can find models or metamodels).<br>
 * 
 * Viewpoints also propose dedicated tools for presenting and manipulating data in the particular context of some stakeholderâs
 * preoccupations.
 * 
 * An Openflexo View is the instantiation of a particular Viewpoint with its own Objective relevant to some of the preoccupations of the
 * Viewpoint.
 * 
 * A Viewpoint addresses some preoccupations of the real world. A View is defined for a given objective and for a particular stakeholder or
 * observer.
 * 
 * A Viewpoint provides:
 * <ul>
 * <li>model extensions to model information relevant to a given context;</li>
 * <li>manipulation primitives (EditionSchemes) involving one or many models;</li>
 * <li>tools to create and edit models using model manipulation primitives;</li>
 * <li>tools to import existing models;</li>
 * <li>graphical representation of manipulated models, with dedicated graphical editors (diagrams, tabular and textual views).</li>
 * </ul>
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(ViewPoint.ViewPointImpl.class)
@XMLElement(xmlTag = "ViewPoint")
public interface ViewPoint extends NamedViewPointObject, ResourceData<ViewPoint> {

	@PropertyIdentifier(type = String.class)
	public static final String VIEW_POINT_URI_KEY = "viewPointURI";
	@PropertyIdentifier(type = FlexoVersion.class)
	public static final String VERSION_KEY = "version";
	@PropertyIdentifier(type = FlexoVersion.class)
	public static final String MODEL_VERSION_KEY = "modelVersion";
	@PropertyIdentifier(type = LocalizedDictionary.class)
	public static final String LOCALIZED_DICTIONARY_KEY = "localizedDictionary";
	@PropertyIdentifier(type = VirtualModel.class, cardinality = Cardinality.LIST)
	public static final String VIRTUAL_MODELS_KEY = "virtualModels";

	@Getter(value = VIEW_POINT_URI_KEY)
	@XMLAttribute(xmlTag = "uri")
	public String getViewPointURI();

	@Setter(VIEW_POINT_URI_KEY)
	public void setViewPointURI(String viewPointURI);

	@Getter(value = VERSION_KEY, isStringConvertable = true)
	@XMLAttribute
	public FlexoVersion getVersion();

	@Setter(VERSION_KEY)
	public void setVersion(FlexoVersion version);

	@Getter(value = MODEL_VERSION_KEY, isStringConvertable = true)
	@XMLAttribute
	public FlexoVersion getModelVersion();

	@Setter(MODEL_VERSION_KEY)
	public void setModelVersion(FlexoVersion modelVersion);

	@Override
	@Getter(value = LOCALIZED_DICTIONARY_KEY, inverse = LocalizedDictionary.OWNER_KEY)
	public LocalizedDictionary getLocalizedDictionary();

	@Setter(LOCALIZED_DICTIONARY_KEY)
	public void setLocalizedDictionary(LocalizedDictionary localizedDictionary);

	/**
	 * Retrieves the right type given the FlexoConcept
	 */

	public FlexoConceptInstanceType getInstanceType(FlexoConcept anFlexoConcept);

	/**
	 * Return FlexoConcept matching supplied id represented as a string, which could be either the name of FlexoConcept, or its URI
	 * 
	 * @param editionPatternId
	 * @return
	 */
	public FlexoConcept getFlexoConcept(String editionPatternId);

	/**
	 * Return all {@link VirtualModel} defined in this {@link ViewPoint}
	 * 
	 * @return
	 */
	@Getter(value = VIRTUAL_MODELS_KEY, cardinality = Cardinality.LIST, inverse = VirtualModel.VIEW_POINT_KEY, ignoreType = true)
	public List<VirtualModel> getVirtualModels();

	@Setter(VIRTUAL_MODELS_KEY)
	public void setVirtualModels(Vector<VirtualModel> virtualModels);

	@Adder(VIRTUAL_MODELS_KEY)
	public void addToVirtualModels(VirtualModel virtualModel);

	@Remover(VIRTUAL_MODELS_KEY)
	public void removeFromVirtualModels(VirtualModel virtualModel);

	public VirtualModel getVirtualModelNamed(String virtualModelName);

	/**
	 * Default implementation for {@link ViewPoint}
	 * 
	 * @author sylvain
	 * 
	 */
	public static abstract class ViewPointImpl extends NamedViewPointObjectImpl implements ViewPoint {

		private static final Logger logger = Logger.getLogger(ViewPoint.class.getPackage().getName());

		private LocalizedDictionary localizedDictionary;
		private ViewPointLibrary _library;
		// private List<ModelSlot> modelSlots;
		private List<VirtualModel> virtualModels;
		private ViewPointResource resource;
		private BindingModel bindingModel;
		private final FlexoConceptBindingFactory bindingFactory = new FlexoConceptBindingFactory(this);

		// Maps to reference all the FlexoConceptInstanceType, DiagramType, VirtualModelType used in this context

		private final Map<FlexoConcept, FlexoConceptInstanceType> flexoConceptTypesMap = new HashMap<FlexoConcept, FlexoConceptInstanceType>();
		private final Map<FlexoConcept, VirtualModelInstanceType> virtualModelTypesMap = new HashMap<FlexoConcept, VirtualModelInstanceType>();

		/**
		 * Stores a chained collections of objects which are involved in validation
		 */
		private final ChainedCollection<ViewPointObject> validableObjects = null;

		// TODO: move this code to the ViewPointResource
		public static ViewPoint newViewPoint(String baseName, String viewpointURI, File containerDir, ViewPointLibrary library) {
			File viewpointDir = new File(containerDir, baseName + ".viewpoint");
			ViewPointResource vpRes = ViewPointResourceImpl.makeViewPointResource(baseName, viewpointURI, viewpointDir, library);
			ViewPointImpl viewpoint = (ViewPointImpl) vpRes.getFactory().newInstance(ViewPoint.class);
			vpRes.setResourceData(viewpoint);
			viewpoint.setResource(vpRes);
			// And register it to the library
			library.registerViewPoint(vpRes);

			viewpoint.init(baseName, library);
			viewpoint.loadViewpointMetaModels();
			try {
				vpRes.save(null);
			} catch (SaveResourceException e) {
				e.printStackTrace();
			}
			return viewpoint;
		}

		private void loadViewpointMetaModels() {
			logger.warning("loadViewpointMetaModels() : not implemented yet");
		}

		@Override
		public FlexoVersion getModelVersion() {
			if (getResource() != null) {
				return getResource().getModelVersion();
			}
			return null;
		}

		@Override
		public void setModelVersion(FlexoVersion aVersion) {
		}

		// Used during deserialization, do not use it
		/*public ViewPointImpl(ViewPointBuilder builder) {
			super(builder);
			if (builder != null) {
				builder.setViewPoint(this);
				resource = builder.resource;
			}
			// modelSlots = new ArrayList<ModelSlot>();
			virtualModels = new ArrayList<VirtualModel>();
		}*/

		// Used during deserialization, do not use it
		public ViewPointImpl() {
			super();
			virtualModels = new ArrayList<VirtualModel>();
		}

		public void init(String baseName, /* File viewpointDir, File xmlFile,*/ViewPointLibrary library/*, ViewPointFolder folder*/) {
			logger.info("Registering viewpoint " + baseName + " URI=" + getViewPointURI());

			setName(baseName);
			_library = library;

			loadViewpointMetaModels();

			/*for (VirtualModel vm : getVirtualModels()) {
				for (FlexoConcept ep : vm.getEditionPatterns()) {
					for (PatternRole<?> pr : ep.getPatternRoles()) {
						if (pr instanceof ShapePatternRole) {
							((ShapePatternRole) pr).tryToFindAGR();
						}
					}
				}
			}*/
		}

		@Override
		public String getStringRepresentation() {
			return getURI();
		}

		/**
		 * Return the URI of the {@link ViewPoint}<br>
		 * The convention for URI are following: <viewpoint_uri>/<virtual_model_name>#<edition_pattern_name>.<edition_scheme_name> <br>
		 * eg<br>
		 * http://www.mydomain.org/MyViewPoint/MyVirtualModel#MyEditionPattern.MyEditionScheme
		 * 
		 * @return String representing unique URI of this object
		 */
		@Override
		public String getURI() {
			return getViewPointURI();
		}

		@Override
		public String getViewPointURI() {
			if (getResource() != null) {
				return getResource().getURI();
			}
			return null;
		}

		@Override
		public void setViewPointURI(String vpURI) {
			if (vpURI != null) {
				// We prevent ',' so that we can use it as a delimiter in tags.
				vpURI = vpURI.replace(",", "");
			}
			if (getResource() != null) {
				getResource().setURI(vpURI);
			}
		}

		@Override
		public String toString() {
			return "ViewPoint:" + getViewPointURI();
		}

		@Override
		public String getName() {
			if (getResource() != null) {
				return getResource().getName();
			}
			return super.getName();
		}

		@Override
		public void setName(String name) {
			if (requireChange(getName(), name)) {
				if (getResource() != null) {
					getResource().setName(name);
				} else {
					super.setName(name);
				}
			}
		}

		@Override
		public FlexoVersion getVersion() {
			if (getResource() != null) {
				return getResource().getVersion();
			}
			return null;
		}

		@Override
		public void setVersion(FlexoVersion aVersion) {
			if (requireChange(getVersion(), aVersion)) {
				if (getResource() != null) {
					getResource().setVersion(aVersion);
				}
			}
		}

		@Override
		public ViewPointLibrary getViewPointLibrary() {
			return _library;
		}

		@Override
		public ViewPointImpl getViewPoint() {
			return this;
		}

		private boolean isLoading = false;

		/**
		 * Load eventually unloaded VirtualModels<br>
		 * After this call return, we can assert that all {@link VirtualModel} are loaded.
		 */
		private synchronized void loadVirtualModelsWhenUnloaded() {
			if (isLoading) {
				return;
			}
			isLoading = true;
			for (org.openflexo.foundation.resource.FlexoResource<?> r : getResource().getContents()) {
				if (r instanceof VirtualModelResource) {
					((VirtualModelResource) r).getVirtualModel();
				}
			}
			isLoading = false;
		}

		/**
		 * Return all VirtualModel of a given class.<br>
		 * If onlyFinalInstances is set to true, only instances of supplied class (and not specialized classes) are retrieved
		 * 
		 * @return
		 */
		public <VM extends VirtualModel> List<VM> getVirtualModels(Class<VM> virtualModelClass, boolean onlyFinalInstances) {
			List<VM> returned = new ArrayList<VM>();
			for (VirtualModel vm : getVirtualModels()) {
				if (onlyFinalInstances) {
					if (virtualModelClass.equals(vm.getClass())) {
						returned.add((VM) vm);
					}
				} else {
					if (virtualModelClass.isAssignableFrom(vm.getClass())) {
						returned.add((VM) vm);
					}
				}
			}
			return returned;
		}

		/**
		 * Return all "plain" {@link VirtualModel} defined in this {@link ViewPoint} (does NOT return subclasses of {@link VirtualModel})
		 * 
		 * @return
		 */
		public List<VirtualModel> getPlainVirtualModels() {
			return getVirtualModels(VirtualModel.class, true);
		}

		/**
		 * Return all {@link DiagramSpecification} defined in this {@link ViewPoint}
		 * 
		 * @return
		 */
		/*public List<DiagramSpecification> getDiagramSpecifications() {
			return getVirtualModels(DiagramSpecification.class, true);
		}*/

		/**
		 * Return all {@link VirtualModel} defined in this {@link ViewPoint}
		 * 
		 * @return
		 */
		@Override
		public List<VirtualModel> getVirtualModels() {
			loadVirtualModelsWhenUnloaded();
			return virtualModels;
		}

		@Override
		public void setVirtualModels(Vector<VirtualModel> virtualModels) {
			loadVirtualModelsWhenUnloaded();
			this.virtualModels = virtualModels;
		}

		@Override
		public void addToVirtualModels(VirtualModel virtualModel) {
			loadVirtualModelsWhenUnloaded();
			virtualModel.setViewPoint(this);
			virtualModels.add(virtualModel);
			setChanged();
			notifyObservers(new VirtualModelCreated(virtualModel));
		}

		@Override
		public void removeFromVirtualModels(VirtualModel virtualModel) {
			loadVirtualModelsWhenUnloaded();
			virtualModel.setViewPoint(null);
			virtualModels.remove(virtualModel);
			setChanged();
			notifyObservers(new VirtualModelDeleted(virtualModel));
		}

		/**
		 * Return {@link VirtualModel} with supplied name or URI
		 * 
		 * @return
		 */
		@Override
		public VirtualModel getVirtualModelNamed(String virtualModelName) {
			loadVirtualModelsWhenUnloaded();
			for (VirtualModel vm : getVirtualModels()) {
				if (vm.getName().equals(virtualModelName)) {
					return vm;
				}
				if (vm.getURI().equals(virtualModelName)) {
					return vm;
				}
			}
			return null;
		}

		/**
		 * Return {@link DiagramSpecification} with supplied name or URI
		 * 
		 * @return
		 */
		/*public DiagramSpecification getDiagramSpecificationNamed(String diagramSpecificationName) {
			loadVirtualModelsWhenUnloaded();
			for (VirtualModel vm : getVirtualModels()) {
				if (vm instanceof DiagramSpecification) {
					if (vm.getName().equals(diagramSpecificationName)) {
						return (DiagramSpecification) vm;
					}
					if (vm.getURI().equals(diagramSpecificationName)) {
						return (DiagramSpecification) vm;
					}
				}
			}
			return null;
		}*/

		/**
		 * Return FlexoConcept matching supplied id represented as a string, which could be either the name of FlexoConcept, or its URI
		 * 
		 * @param editionPatternId
		 * @return
		 */
		@Override
		public FlexoConcept getFlexoConcept(String editionPatternId) {
			for (VirtualModel vm : getVirtualModels()) {
				FlexoConcept returned = vm.getFlexoConcept(editionPatternId);
				if (returned != null) {
					return returned;
				}
			}
			// logger.warning("Not found FlexoConcept:" + editionPatternId);
			return null;
		}

		// Return a default diagram specification (temporary hack to ensure compatibility with old versions)
		/*@Deprecated
		public DiagramSpecification getDefaultDiagramSpecification() {
			loadVirtualModelsWhenUnloaded();
			for (VirtualModel vm : getVirtualModels()) {
				if (vm instanceof DiagramSpecification) {
					return (DiagramSpecification) vm;
				}
			}
			// OK, lets create a default one
			// DiagramSpecification ds = new DiagramSpecification(this);
			// addToVirtualModels(ds);
			return null;
		}*/

		@Override
		public LocalizedDictionary getLocalizedDictionary() {
			if (localizedDictionary == null) {
				localizedDictionary = getResource().getFactory().newInstance(LocalizedDictionary.class);
				localizedDictionary.setOwner(this);
			}
			return localizedDictionary;
		}

		@Override
		public void setLocalizedDictionary(LocalizedDictionary localizedDictionary) {
			localizedDictionary.setOwner(this);
			this.localizedDictionary = localizedDictionary;
		}

		@Deprecated
		public static String findOntologyImports(File aFile) {
			Document document;
			try {
				logger.fine("Try to find URI for " + aFile);
				document = XMLUtils.readXMLFile(aFile);
				Element root = XMLUtils.getElement(document, "Ontology");
				if (root != null) {
					Element importElement = XMLUtils.getElement(document, "imports");
					if (importElement != null) {
						Iterator it = importElement.getAttributes().iterator();
						while (it.hasNext()) {
							Attribute at = (Attribute) it.next();
							if (at.getName().equals("resource")) {
								// System.out.println("Returned " + at.getValue());
								String returned = at.getValue();
								if (StringUtils.isNotEmpty(returned)) {
									return returned;
								}
							}
						}
					}
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
		public BindingModel getBindingModel() {
			if (bindingModel == null) {
				createBindingModel();
			}
			return bindingModel;
		}

		public void updateBindingModel() {
			logger.fine("updateBindingModel()");
			bindingModel = null;
			createBindingModel();
		}

		private void createBindingModel() {
			bindingModel = new BindingModel();
			for (VirtualModel vm : getVirtualModels()) {
				bindingModel.addToBindingVariables(new BindingVariable(vm.getName(), Object.class));
			}
		}

		@Override
		public FlexoConceptBindingFactory getBindingFactory() {
			return bindingFactory;
		}

		@Override
		public ValidationModel getDefaultValidationModel() {
			return ViewPointLibrary.VALIDATION_MODEL;
		}

		// ==========================================================================
		// ============================== Model Slots ===============================
		// ==========================================================================

		/*public void setModelSlots(List<ModelSlot> modelSlots) {
			this.modelSlots = modelSlots;
		}

		public List<ModelSlot> getModelSlots() {
			return modelSlots;
		}

		public void addToModelSlots(ModelSlot modelSlot) {
			modelSlots.add(modelSlot);
			modelSlot.setViewPoint(this);
			setChanged();
			notifyObservers(new ModelSlotAdded(modelSlot, this));
		}

		public void removeFromModelSlots(ModelSlot modelSlot) {
			modelSlots.remove(modelSlot);
			modelSlot.setViewPoint(null);
			setChanged();
			notifyObservers(new ModelSlotRemoved(modelSlot, this));
		}

		public <MS extends ModelSlot> List<MS> getModelSlots(Class<MS> msType) {
			List<MS> returned = new ArrayList<MS>();
			for (ModelSlot ms : getModelSlots()) {
				if (TypeUtils.isTypeAssignableFrom(msType, ms.getClass())) {
					returned.add((MS) ms);
				}
			}
			return returned;
		}

		public ModelSlot getModelSlot(String modelSlotName) {
			for (ModelSlot ms : getModelSlots()) {
				if (ms.getName().equals(modelSlotName)) {
					return ms;
				}
			}
			return null;
		}

		public List<ModelSlot> getRequiredModelSlots() {
			List<ModelSlot> requiredModelSlots = new ArrayList<ModelSlot>();
			for (ModelSlot modelSlot : getModelSlots()) {
				if (modelSlot.getIsRequired()) {
					requiredModelSlots.add(modelSlot);
				}
			}
			return modelSlots;
		}*/

		@Override
		public String getFMLRepresentation(FMLRepresentationContext context) {
			// Voir du cote de GeneratorFormatter pour formatter tout ca
			FMLRepresentationOutput out = new FMLRepresentationOutput(context);

			/*for (FlexoMetaModelResource<?, ?> mm : getAllMetaModels()) {
				out.append("import " + mm.getURI() + ";", context);
				out.append(StringUtils.LINE_SEPARATOR, context);
			}*/

			out.append("ViewDefinition " + getName() + " uri=\"" + getURI() + "\"", context);
			out.append(" {" + StringUtils.LINE_SEPARATOR, context);

			/*for (ModelSlot modelSlot : getModelSlots()) {
				// if (modelSlot.getMetaModelResource() != null) {
				out.append(modelSlot.getFMLRepresentation(context), context);
				// }
			}*/

			out.append(StringUtils.LINE_SEPARATOR, context);
			for (VirtualModel vm : getVirtualModels()) {
				out.append(vm.getFMLRepresentation(context), context, 1);
				out.append(StringUtils.LINE_SEPARATOR, context, 1);
			}
			out.append("}" + StringUtils.LINE_SEPARATOR, context);
			return out.toString();
		}

		/*@Override
		public Collection<VirtualModel> getEmbeddedValidableObjects() {
			return getVirtualModels();
		}*/

		// Implementation of XMLStorageResourceData

		/*@Override
		public FlexoStorageResource<ViewPoint> getFlexoResource() {
			return (FlexoStorageResource<ViewPoint>) getResource();
		}

		@Override
		public void setFlexoResource(FlexoResource resource) throws DuplicateResourceException {
			setResource(resource);
		}*/

		@Override
		public ViewPointResource getResource() {
			return resource;
		}

		@Override
		public void setResource(org.openflexo.foundation.resource.FlexoResource<ViewPoint> resource) {
			this.resource = (ViewPointResource) resource;
		}

		/*@Override
		public ViewPointImplResource getFlexoXMLFileResource() {
			return getResource();
		}*/

		/*@Override
		public void save() {
			logger.info("Saving ViewPoint to " + getResource().getFile().getAbsolutePath() + "...");

			try {
				getResource().save(null);
			} catch (SaveResourceException e) {
				e.printStackTrace();
			}
		}*/

		@Override
		public boolean delete() {

			logger.info("Deleting ViewPoint " + this);

			// Unregister the viewpoint resource from the viewpoint library
			if (getResource() != null && getViewPointLibrary() != null) {
				getViewPointLibrary().unregisterViewPoint(getResource());
			}

			// Delete viewpoint
			super.delete();

			// Delete observers
			deleteObservers();

			return true;
		}

		/**
		 * Retrieves the right type given the FlexoConcept
		 */
		@Override
		public FlexoConceptInstanceType getInstanceType(FlexoConcept anFlexoConcept) {
			FlexoConceptInstanceType instanceType = null;

			if (anFlexoConcept != null) {
				if (anFlexoConcept instanceof VirtualModel) {
					instanceType = virtualModelTypesMap.get(anFlexoConcept);
					if (instanceType == null) {
						instanceType = new VirtualModelInstanceType((VirtualModel) anFlexoConcept);
						virtualModelTypesMap.put(anFlexoConcept, (VirtualModelInstanceType) instanceType);
					}
				} else {
					instanceType = flexoConceptTypesMap.get(anFlexoConcept);
					if (instanceType == null) {
						instanceType = new FlexoConceptInstanceType(anFlexoConcept);
						flexoConceptTypesMap.put(anFlexoConcept, instanceType);
					}
				}
			}

			return instanceType;
		}

	}
}

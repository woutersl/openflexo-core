package org.openflexo.prefs;

import java.io.File;
import java.io.IOException;

import org.openflexo.AdvancedPrefs;
import org.openflexo.ApplicationContext;
import org.openflexo.GeneralPreferences;
import org.openflexo.foundation.IOFlexoException;
import org.openflexo.foundation.InconsistentDataException;
import org.openflexo.foundation.InvalidModelDefinitionException;
import org.openflexo.foundation.InvalidXMLException;
import org.openflexo.foundation.resource.FlexoFileNotFoundException;
import org.openflexo.foundation.resource.FlexoResource;
import org.openflexo.foundation.resource.PamelaResource;
import org.openflexo.foundation.resource.PamelaResourceImpl;
import org.openflexo.foundation.resource.SaveResourceException;
import org.openflexo.model.annotations.ImplementationClass;
import org.openflexo.model.annotations.ModelEntity;
import org.openflexo.model.annotations.XMLElement;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.model.factory.ModelFactory;
import org.openflexo.toolbox.FileUtils;
import org.openflexo.toolbox.IProgress;

/**
 * This is the {@link FlexoResource} encoding the preferences of the application (see {@link FlexoPreferences})
 * 
 * @author sylvain
 * 
 */
@ModelEntity
@ImplementationClass(FlexoPreferencesResource.FlexoPreferencesResourceImpl.class)
@XMLElement
public interface FlexoPreferencesResource extends PamelaResource<FlexoPreferences, FlexoPreferencesFactory> {

	public FlexoPreferences getFlexoPreferences();

	/**
	 * Default implementation for {@link FlexoPreferencesResource}
	 * 
	 * 
	 * @author Sylvain
	 * 
	 */
	public static abstract class FlexoPreferencesResourceImpl extends PamelaResourceImpl<FlexoPreferences, FlexoPreferencesFactory>
			implements FlexoPreferencesResource {

		private static final String FLEXO_PREFS_FILE_NAME = "Flexo.prefs";

		public static FlexoPreferencesResource makePreferencesResource(ApplicationContext applicationContext,
				FlexoPreferencesFactory factory) {
			try {
				ModelFactory resourceFactory = new ModelFactory(FlexoPreferencesResource.class);
				FlexoPreferencesResourceImpl returned = (FlexoPreferencesResourceImpl) resourceFactory
						.newInstance(FlexoPreferencesResource.class);

				File preferencesFile = new File(FileUtils.getApplicationDataDirectory(), FLEXO_PREFS_FILE_NAME);
				returned.setName("OpenflexoPreferences");
				returned.setURI("http://www.openflexo.org/OpenflexoPreferences");
				returned.setFile(preferencesFile);
				returned.setFactory(factory);
				returned.setServiceManager(applicationContext);

				if (preferencesFile.exists()) {
					returned.loadResourceData(null);
				} else {
					FlexoPreferences newFlexoPreferences = returned.getFactory().newInstance(FlexoPreferences.class);
					returned.setResourceData(newFlexoPreferences);
					returned.save(null);
				}
				return returned;
			} catch (ModelDefinitionException e) {
				e.printStackTrace();
			} catch (FlexoFileNotFoundException e) {
				e.printStackTrace();
			} catch (IOFlexoException e) {
				e.printStackTrace();
			} catch (InvalidXMLException e) {
				e.printStackTrace();
			} catch (InconsistentDataException e) {
				e.printStackTrace();
			} catch (InvalidModelDefinitionException e) {
				e.printStackTrace();
			} catch (SaveResourceException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public FlexoPreferences getFlexoPreferences() {
			return getLoadedResourceData();
		}

		@Override
		public FlexoPreferences loadResourceData(IProgress progress) throws FlexoFileNotFoundException, IOFlexoException,
				InvalidXMLException, InconsistentDataException, InvalidModelDefinitionException {
			try {
				return super.loadResourceData(progress);
			} catch (InvalidXMLException e) {

				System.out.println("On arrive pas a lire le fichier " + getFile());

				try {
					System.out.println(FileUtils.fileContents(getFile()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				System.out.println("On tente de refaire un fichier de preferences" + getFile());

				// Preferences file is not readable, perhaps this is because it is an old version of Openflexo
				// Creates it from scratch

				FlexoPreferences prefs = getFactory().newInstance(FlexoPreferences.class);
				GeneralPreferences generalPrefs = getFactory().newInstance(GeneralPreferences.class);
				prefs.addToContents(generalPrefs);
				AdvancedPrefs advancedPrefs = getFactory().newInstance(AdvancedPrefs.class);
				prefs.addToContents(advancedPrefs);
				setResourceData(prefs);

				System.out.println("build " + getFactory().stringRepresentation(prefs));

				try {
					save(null);
				} catch (SaveResourceException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				try {
					System.out.println(FileUtils.fileContents(getFile()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return prefs;
			}
		}

	}
}

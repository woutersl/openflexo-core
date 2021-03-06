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

package org.openflexo.foundation.resource;

import java.io.File;

/**
 * Default implementation for {@link FlexoXMLFileResource} (a {@link FlexoFileResource} stored in a XML {@link File})
 * 
 * @param <RD>
 *            the type of the resource data reference by this resource
 * @author Sylvain
 * 
 */
public abstract class FlexoXMLFileResourceImpl<RD extends ResourceData<RD>> extends FlexoResourceImpl<RD> implements
		FlexoXMLFileResource<RD> {

	/*private boolean isLoading = false;
	private boolean isConverting = false;
	protected boolean performLoadWithPreviousVersion = true;

	@Override
	public final void save(IProgress progress) throws SaveResourceException {
		if (progress != null) {
			progress.setProgress(FlexoLocalization.localizedForKey("saving") + " " + this.getName());
		}
		if (!isLoaded()) {
			return;
		}
		if (!isDeleted) {
			saveResourceData(true);
			resourceData.clearIsModified(false);
		}

	}

	@Override
	public RD loadResourceData(IProgress progress) throws ResourceLoadingCancelledException, ResourceDependencyLoopException,
			FileNotFoundException, FlexoException {
		if (resourceData != null) {
			// already loaded
			return resourceData;
		}

		if (getXMLSerializationService() == null) {
			throw new FlexoException("XMLSerializationService not registered");
		}

		isLoading = true;
		if (progress != null) {
			progress.setProgress(FlexoLocalization.localizedForKey("loading") + " " + this.getName());
			progress.resetSecondaryProgress(4);
			progress.setProgress(FlexoLocalization.localizedForKey("loading_from_disk"));
		}

		LoadXMLResourceException exception = null;

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Load resource data for " + this);
		}
		if (!getFile().exists()) {
			recoverFile();
			if (!getFile().exists()) {
				if (logger.isLoggable(Level.SEVERE)) {
					logger.severe("File " + getFile().getAbsolutePath() + " does not exist, throwing exception now!");
				}
				throw new FlexoFileNotFoundException(this);
			}
		}
		RD returned = null;

		FlexoVersion[] availableVersions = getXMLSerializationService().getAvailableVersionsForClass(getResourceDataClass());
		FlexoVersion[] availableVersionsNew = new FlexoVersion[availableVersions.length + 1];

		if (getModelVersion() == null) {
			setModelVersion(latestVersion());
		}

		for (int k = 0; k < availableVersions.length; k++) {
			availableVersionsNew[k] = availableVersions[k];
		}
		availableVersionsNew[availableVersions.length] = getModelVersion();
		// int i = availableVersions.length-1;
		int i = availableVersionsNew.length - 1;
		boolean notCorrectelyDeserialized = true;
		FlexoVersion triedVersion = null;
		try {
			while (notCorrectelyDeserialized
					&& (i >= 0 && performLoadWithPreviousVersion || i == availableVersionsNew.length - 1 && !performLoadWithPreviousVersion)) {
				// triedVersion = availableVersions[i];
				triedVersion = availableVersionsNew[i];
				i--;
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("tried version = " + triedVersion.toString());
				}

				XMLSerializationService.ClassModelVersion cmv = getXMLSerializationService().getClassModelVersion(getResourceDataClass(),
						triedVersion);
				if (cmv == null) {
					throw new LoadXMLResourceException(this, "Class model version could not be found for class '"
							+ getResourceDataClass().getName() + "' and version " + triedVersion);
				}
				if (cmv.needsManualConversion) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("This resource " + this + " must be converted from " + triedVersion + " into " + cmv.toVersion);
					}
					if (progress != null) {
						progress.setProgress(FlexoLocalization.localizedForKey("converting from version ") + triedVersion + " "
								+ FlexoLocalization.localizedForKey("to") + " " + cmv.toVersion);
					}
					isConverting = true;
					if (convertResourceFileFromVersionToVersion(triedVersion, cmv.toVersion)) {
						if (logger.isLoggable(Level.INFO)) {
							logger.info("Conversion from " + triedVersion + " into " + cmv.toVersion + " was successfull.");
						}
						// Load again with the new version !
						try {
							// TODO: project should not be used for this but rather the resource. If by any chances, the loading of resource
							// initiates the conversion of another one, that other resource will unset this property on the project and
							// there is
							// a good chance that objects that should not be registered will be registered in the project anyway.
							returned = tryToLoadResourceDataWithVersion(cmv.toVersion);
						} catch (JDOMException e) {
							if (logger.isLoggable(Level.WARNING)) {
								logger.warning("Malformed XML File: " + e.getMessage());
							}
							return null;
						} catch (XMLOperationException e) {
							e.printStackTrace();
							if (e.getCause() != null) {
								e.getCause().printStackTrace();
							}
							if (exception == null) {
								exception = new LoadXMLResourceException(this, e.getCause() != null ? e.getCause().getMessage()
										: e.getMessage());
							}
							exception.addLoadException(e);
							if (logger.isLoggable(Level.SEVERE)) {
								logger.severe("Could not load Resource " + this + ": failed to reload after conversion!");
							}
							throw exception;
						} finally {
							// In case something needs to be done
						}
						triedVersion = cmv.toVersion;
					} else {
						if (logger.isLoggable(Level.WARNING)) {
							logger.warning("Conversion FAILED: succeeding to load Resource " + this + " with model version " + triedVersion
									+ " but this requires a conversion from version " + triedVersion + " to version " + cmv.toVersion
									+ " which seem to be not implemented.");
						}
						return returned;
					}
				}

				if (!triedVersion.equals(latestVersion())) {
					isConverting = true;
				} else {
					isConverting = false;
				}
				try {
					returned = tryToLoadResourceDataWithVersion(triedVersion);
				} catch (JDOMException e) {
					if (logger.isLoggable(Level.WARNING)) {
						logger.warning("Malformed XML File: " + e.getMessage());
					}
					isLoading = false;
					throw new MalformedXMLException(this, e);
				} catch (XMLOperationException e) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("ERROR:" + "tried version = " + triedVersion.toString());
						e.getException().printStackTrace();
						if (e.getException() instanceof AccessorInvocationException) {
							((AccessorInvocationException) e.getException()).getTargetException().printStackTrace();
							if (((AccessorInvocationException) e.getException()).getTargetException() instanceof Error) {
								throw new LoadXMLResourceException(this, e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
							}
						}
					}
					if (exception == null) {
						exception = new LoadXMLResourceException(this, e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
					}
					exception.addLoadException(e);
				} finally {
				}
				if (returned != null) {
					notCorrectelyDeserialized = false;
				}
			}

			if (isDeleted()) {
				return null;
			}

			if (notCorrectelyDeserialized) {
				if (logger.isLoggable(Level.SEVERE)) {
					logger.severe("Could not load Resource " + this + ": no valid XML model found !");
				}
				isLoading = false;
				throw exception;
			} else {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Found a version to load resource " + this);
				}
				setModelVersion(triedVersion);
				XMLSerializationService.ClassModelVersion cmv = getXMLSerializationService().getClassModelVersion(getResourceDataClass(),
						triedVersion);

				boolean convertToLatestVersion = false;
				if (cmv.needsManualConversion || !triedVersion.equals(latestVersion())) {
					convertToLatestVersion = true;
				}

				if (cmv.needsManualConversion && convertToLatestVersion) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("This resource " + this + "must be converted from " + triedVersion + " into " + cmv.toVersion);
					}
					resourceData = returned;
					if (progress != null) {
						progress.setProgress(FlexoLocalization.localizedForKey("converting from version ") + triedVersion + " "
								+ FlexoLocalization.localizedForKey("to") + " " + cmv.toVersion);
					}
					isConverting = true;
					if (convertResourceFileFromVersionToVersion(triedVersion, cmv.toVersion)) {
						if (logger.isLoggable(Level.INFO)) {
							logger.info("Conversion from " + triedVersion + " into " + cmv.toVersion + " was successfull.");
						}
						// Load again with the new version !
						try {
							if (!cmv.toVersion.equals(latestVersion())) {
								isConverting = true;
							} else {
								isConverting = false;
							}
							returned = tryToLoadResourceDataWithVersion(cmv.toVersion);
						} catch (JDOMException e) {
							if (logger.isLoggable(Level.WARNING)) {
								logger.warning("Malformed XML File: " + e.getMessage());
							}
							return null;
						} catch (XMLOperationException e) {
							e.printStackTrace();
							if (e.getCause() != null) {
								e.getCause().printStackTrace();
							}
							if (exception == null) {
								exception = new LoadXMLResourceException(this, e.getCause() != null ? e.getCause().getMessage()
										: e.getMessage());
							}
							exception.addLoadException(e);
							if (logger.isLoggable(Level.SEVERE)) {
								logger.severe("Could not load Resource " + this + ": failed to reload after conversion!");
							}
							throw exception;
						} finally {
						}
						triedVersion = cmv.toVersion;
					} else {
						if (logger.isLoggable(Level.WARNING)) {
							logger.warning("Conversion FAILED: succeeding to load Resource " + this + " with model version " + triedVersion
									+ " but this requires a conversion from version " + triedVersion + " to version " + cmv.toVersion
									+ " which seem to be not implemented.");
						}
						return returned;
					}
				}
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Succeeding to load Resource " + this + " with model version " + triedVersion);
				}
				resourceData = returned;
				if (!triedVersion.equals(latestVersion()) && convertToLatestVersion) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("Converting Resource " + this + " to latest version " + latestVersion());
					}
					if (progress != null) {
						progress.setProgress(FlexoLocalization.localizedForKey("converting from version ") + triedVersion + " "
								+ FlexoLocalization.localizedForKey("to") + " " + latestVersion());
					}
					isConverting = true;
					if (incrementalConversionFromVersionToVersion(triedVersion, latestVersion())) {
						if (logger.isLoggable(Level.INFO)) {
							logger.info("Conversion from " + triedVersion + " into latest version :" + latestVersion() + " was successful.");
						}
						try {
							isConverting = false;
							returned = tryToLoadResourceDataWithVersion(latestVersion());
						} catch (Exception exce) {
							exce.printStackTrace();
						}
						resourceData = returned;
						isLoading = false;
						setModelVersion(latestVersion());
					} else {
						if (logger.isLoggable(Level.WARNING)) {
							logger.warning("Conversion from " + triedVersion + " into latest version : " + latestVersion() + " FAILED.");
						}
					}

					try {
						if (progress != null) {
							progress.setProgress(FlexoLocalization.localizedForKey("saving") + " " + getName());
						}
						save(progress);
					} catch (SaveXMLResourceException e) {
						if (logger.isLoggable(Level.SEVERE)) {
							logger.log(Level.SEVERE, "Could not load Resource " + this + ": failed to convert to new version !", e);
						}
						throw new LoadXMLResourceException(this, e.getMessage());
					} catch (SaveResourcePermissionDeniedException e) {
						if (logger.isLoggable(Level.SEVERE)) {
							logger.log(Level.SEVERE, "Could not load Resource " + this
									+ ": failed to convert to new version because file is read-only !", e);
						}
						throw new LoadXMLResourceException(this, e.getMessage());
					} catch (SaveResourceException e) {
						if (logger.isLoggable(Level.SEVERE)) {
							logger.log(Level.SEVERE, "Could not load Resource " + this + ": failed to convert to new version !", e);
						}
						throw new LoadXMLResourceException(this, e.getMessage());
					}
				} else {
					// _currentVersion has normally been set while converting during saveResourceData()
					setModelVersion(triedVersion);
				}

			}
			resourceData = returned;
			isLoading = false;
			resourceData.setResource(this);
			return returned;

		} finally {
			isLoading = false;
			isConverting = false;
		}
	}

	protected RD tryToLoadResourceDataWithVersion(FlexoVersion version) throws XMLOperationException, JDOMException {
		RD returned = null;
		try {

			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Trying to load " + getResourceDataClass().getName() + " with model version " + version);
			}
			XMLMapping mapping = getXMLSerializationService().getMappingForClassAndVersion(getResourceDataClass(), version);
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Model version " + version + " has been loaded.");
			}

			if (hasBuilder() && mapping.hasBuilderClass()) {
				returned = (RD) XMLDecoder.decodeObjectWithMapping(new FileInputStream(getFile()), mapping, instanciateNewBuilder(),
						getStringEncoder());
			} else {
				returned = (RD) XMLDecoder.decodeObjectWithMapping(new FileInputStream(getFile()), mapping, null, getStringEncoder());
			}
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Succeeded loading " + getResourceDataClass().getName() + " with model version " + version);
			}
			if (returned != null) {
				returned.setResource(this);
			}

			setModelVersion(version);
			return returned;
		} catch (AccessorInvocationException e) {
			if (logger.isLoggable(Level.INFO)) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("FAILED loading " + getResourceDataClass().getName() + " with model version " + version + " Exception: "
							+ e.getTargetException().getMessage());
				}
			}
			if (logger.isLoggable(Level.INFO)) {
				e.getTargetException().printStackTrace();
			}
			// e.printStackTrace();
			throw new XMLOperationException(e, version);
		} catch (Exception e) {
			if (logger.isLoggable(Level.INFO)) {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("FAILED loading " + getResourceDataClass().getName() + " with model version " + version + " Exception: "
							+ e.getMessage());
				}
			}
			if (logger.isLoggable(Level.INFO)) {
				e.printStackTrace();
			}
			// e.printStackTrace();
			throw new XMLOperationException(e, version);
		}
	}

	private boolean incrementalConversionFromVersionToVersion(FlexoVersion fromVersion, FlexoVersion toVersion) {
		FlexoVersion[] v = getXMLSerializationService().getAvailableVersionsForClass(getResourceDataClass());
		int i = 0;
		// Let's find the index of the version in the array
		for (; i < v.length; i++) {
			FlexoVersion version = v[i];
			if (version.equals(fromVersion)) {
				break;
			}
		}
		// We try to convert until toVersion
		for (; i < v.length; i++) {
			if (!v[i].isGreaterThan(toVersion)) {// As long as the current version is smaller than toVersion
				XMLSerializationService.ClassModelVersion cmv = getXMLSerializationService().getClassModelVersion(getResourceDataClass(),
						v[i]);
				if (cmv.needsManualConversion) {
					if (convertResourceFileFromVersionToVersion(v[i], cmv.toVersion)) {
						setModelVersion(cmv.toVersion);
						if (logger.isLoggable(Level.INFO)) {
							logger.info("Successfully converted resource " + this + " from " + v[i] + " to " + cmv.toVersion);
						}
						while (i + 1 < v.length && !v[i + 1].equals(cmv.toVersion)) {
							i++;
						}
						if (i + 1 == v.length || !v[i + 1].equals(cmv.toVersion)) {
							if (logger.isLoggable(Level.WARNING)) {
								logger.warning("This is weird. I tried to convert from " + cmv.version + " to " + cmv.toVersion
										+ " but I can't find that version in the mapping.");
							}
							return false;
						}
					} else {
						if (logger.isLoggable(Level.WARNING)) {
							logger.warning("FAILED to convert resource " + this + " from " + v[i] + " to " + cmv.toVersion);
						}
					}
				} else {
					setModelVersion(cmv.version);
					try {
						logger.info("Trying to save resource " + this + " with model version " + getModelVersion());
						_saveResourceData(getModelVersion(), true);
					} catch (SaveXMLResourceException e) {
						logger.warning("Cound not save with version " + getModelVersion() + " " + e);
						e.printStackTrace();
					}
				}
			} else {
				break;// We are done here
			}
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("Incremental conversion from " + fromVersion + " to " + toVersion + " performed successfully");
		}
		return true;
	}

	protected void saveResourceData(boolean clearIsModified) throws SaveXMLResourceException, SaveResourcePermissionDeniedException {
		System.out.println("Saving " + getFile());
		if (!hasWritePermission()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Permission denied : " + getFile().getAbsolutePath());
			}
			throw new SaveResourcePermissionDeniedException(this);
		}
		if (resourceData != null) {
			_saveResourceData(latestVersion(), clearIsModified);
			if (logger.isLoggable(Level.INFO)) {
				logger.info("Succeeding to save Resource " + this + " : " + getFile().getName() + " version=" + latestVersion()
						+ " with date " + FileUtils.getDiskLastModifiedDate(getFile()));
			}
		}
		if (clearIsModified) {
			try {
				getResourceData(null).clearIsModified(false);// No need to reset the last memory update since it is valid
				notifyResourceSaved();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected synchronized void saveResourceDataWithVersion(FlexoVersion version) throws SaveXMLResourceException,
			SaveResourcePermissionDeniedException {
		if (!hasWritePermission()) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Permission denied : " + getFile().getAbsolutePath());
			}
			throw new SaveResourcePermissionDeniedException(this);
		}
		if (resourceData != null) {
			_saveResourceData(version, true);
			if (logger.isLoggable(Level.INFO)) {
				logger.info("Succeeding to save Resource " + this + " : " + getFile().getName());
			}
		}
		try {
			setModelVersion(version);
			resourceData.clearIsModified(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _saveResourceData(FlexoVersion version, boolean clearIsModified) throws SaveXMLResourceException {
		_saveResourceData(version, new SerializationHandler() {
			@Override
			public void objectWillBeSerialized(XMLSerializable object) {
				if (object instanceof FlexoModelObject) {
					((FlexoModelObject) object).initializeSerialization();
				}
			}

			@Override
			public void objectHasBeenSerialized(XMLSerializable object) {
				if (object instanceof FlexoModelObject) {
					((FlexoModelObject) object).finalizeSerialization();
				}
			}
		}, clearIsModified);
	}

	public static class WillWriteFileOnDiskNotification implements ServiceNotification {
		private final File file;

		public WillWriteFileOnDiskNotification(File file) {
			this.file = file;
		}

		public File getFile() {
			return file;
		}
	}

	private void _saveResourceData(FlexoVersion version, SerializationHandler handler, boolean clearIsModified)
			throws SaveXMLResourceException {
		File temporaryFile = null;
		FileWritingLock lock = willWriteOnDisk();

		if (logger.isLoggable(Level.INFO)) {
			logger.info("Saving resource " + this + " : " + getFile().getName() + " version=" + version);
		}

		try {
			File dir = getFile().getParentFile();
			if (!dir.exists()) {
				getServiceManager().notify(null, new WillWriteFileOnDiskNotification(dir));
				dir.mkdirs();
			}
			getServiceManager().notify(null, new WillWriteFileOnDiskNotification(getFile()));
			// Make local copy
			makeLocalCopy();
			// Using temporary file
			temporaryFile = File.createTempFile("temp", ".xml", dir);
			if (logger.isLoggable(Level.FINE)) {
				logger.finer("Creating temp file " + temporaryFile.getAbsolutePath());
			}
			try {
				performXMLSerialization(version, handler, temporaryFile);
				// Renaming temporary file
				if (logger.isLoggable(Level.FINE)) {
					logger.finer("Renaming temp file " + temporaryFile.getAbsolutePath() + " to " + getFile().getAbsolutePath());
				}
				// temporaryFile.renameTo(getFile());
				postXMLSerialization(version, temporaryFile, lock, clearIsModified);
			} catch (DuplicateSerializationIdentifierException e) {
				if (logger.isLoggable(Level.SEVERE)) {
					logger.log(Level.SEVERE, "Duplicate serialization identifier: " + e.getMessage(), e);
				}
				hasWrittenOnDisk(lock);
				((FlexoXMLSerializable) resourceData).finalizeSerialization();
				throw new SaveXMLResourceException(this, e, version);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (temporaryFile != null) {
				temporaryFile.delete();
			}
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Failed to save resource " + this + " with model version " + version);
			}
			hasWrittenOnDisk(lock);
			((FlexoXMLSerializable) resourceData).finalizeSerialization();
			throw new SaveXMLResourceException(this, e, version);
		}
	}

	private void postXMLSerialization(FlexoVersion version, File temporaryFile, FileWritingLock lock, boolean clearIsModified)
			throws IOException {
		FileUtils.rename(temporaryFile, getFile());
		hasWrittenOnDisk(lock);
		((FlexoXMLSerializable) resourceData).finalizeSerialization();
		setModelVersion(version);
		if (clearIsModified) {
			notifyResourceStatusChanged();
		}
	}

	private void performXMLSerialization(FlexoVersion version, SerializationHandler handler, File temporaryFile)
			throws FileNotFoundException, InvalidObjectSpecificationException, InvalidModelException, AccessorInvocationException,
			DuplicateSerializationIdentifierException, IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(temporaryFile);
			FlexoXMLSerializable dataToSerialize = (FlexoXMLSerializable) resourceData;
			dataToSerialize.initializeSerialization();
			XMLCoder.encodeObjectWithMapping(dataToSerialize,
					getXMLSerializationService().getMappingForClassAndVersion(getResourceDataClass(), version), out, getStringEncoder(),
					handler);
			dataToSerialize.finalizeSerialization();
			out.flush();
			out.close();
			out = null;
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
	}

	public synchronized void revertToReleaseVersion(FlexoVersion releaseVersion) throws SaveXMLResourceException,
			SaveResourcePermissionDeniedException {
		// 1st, be sure that data are loaded
		try {
			loadResourceData(null);
		} catch (FileNotFoundException e) {
			throw new SaveXMLResourceException(this, e, getModelVersion());
		} catch (ResourceLoadingCancelledException e) {
			throw new SaveXMLResourceException(this, e, getModelVersion());
		} catch (ResourceDependencyLoopException e) {
			throw new SaveXMLResourceException(this, e, getModelVersion());
		} catch (FlexoException e) {
			throw new SaveXMLResourceException(this, e, getModelVersion());
		}
		// Then find version
		final FlexoVersion version = getXMLSerializationService().getVersionForClassAndRelease(getResourceDataClass(), releaseVersion);
		// And save to this version
		if (version != null) {
			logger.info("Trying to convert " + this + " from " + latestVersion() + " to " + version);
			setModelVersion(version);
			if (!hasWritePermission()) {
				if (logger.isLoggable(Level.WARNING)) {
					logger.warning("Permission denied : " + getFile().getAbsolutePath());
				}
				throw new SaveResourcePermissionDeniedException(this);
			}
			if (resourceData != null) {
				final XMLMapping currentMapping = getXMLSerializationService().getMappingForClassAndVersion(getResourceDataClass(),
						latestVersion());
				final XMLMapping revertedMapping = getXMLSerializationService().getMappingForClassAndVersion(getResourceDataClass(),
						version);
				_saveResourceData(version, new SerializationHandler() {
					@Override
					public void objectWillBeSerialized(XMLSerializable object) {
						if (object instanceof FlexoModelObject) {
							fillInUnmappedAttributesAsDynamicProperties((FlexoModelObject) object, currentMapping, revertedMapping);
							((FlexoModelObject) object).initializeSerialization();
						}
					}

					@Override
					public void objectHasBeenSerialized(XMLSerializable object) {
						if (object instanceof FlexoModelObject) {
							((FlexoModelObject) object).finalizeSerialization();
						}
					}
				}, true);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Succeeding to save Resource " + this + " : " + getFile().getName());
				}
			}
			setModelVersion(version);
			resourceData.clearIsModified(false);
		}
	}

	private StringEncoder STRING_ENCODER = null;

	@Override
	public StringEncoder getStringEncoder() {
		if (STRING_ENCODER == null) {
			if (this instanceof FlexoProjectResource) {
				STRING_ENCODER = new StringEncoder(super.getStringEncoder(), ((FlexoProjectResource) this).getProject()
						.getObjectReferenceConverter());
			} else {
				STRING_ENCODER = super.getStringEncoder();
			}
		}
		return STRING_ENCODER;
	}

	@Override
	public FlexoVersion latestVersion() {
		if (getXMLSerializationService() != null) {
			return getXMLSerializationService().getLatestVersionForClass(getResourceDataClass());
		}
		return null;
	}

	@Override
	public XMLSerializationService getXMLSerializationService() {
		if (getServiceManager() != null) {
			return getServiceManager().getXMLSerializationService();
		}
		logger.warning("Sorry, XMLSerializationService not registered, cannot proceed");
		return null;
	}

	public void recoverFile() {
		if (getFile() == null) {
			return;
		}
		if (getFile().exists()) {
			return;
		}
		if (getFile().getParentFile().exists()) {
			File[] files = getFile().getParentFile().listFiles();
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (file.getName().equalsIgnoreCase(getFile().getName())) {
					if (logger.isLoggable(Level.WARNING)) {
						logger.warning("Found file " + file.getAbsolutePath() + ". Using it and repairing project as well!");
					}
					setFile(file);
					break;
				}
			}
		}
	}

	protected boolean convertResourceFileFromVersionToVersion(FlexoVersion v1, FlexoVersion v2) {
		if (logger.isLoggable(Level.WARNING)) {
			logger.warning("Unable to find converter for resource " + this + " from version " + v1 + " to version " + v2);
		}
		return false;
	}

	private void makeLocalCopy() throws IOException {
		if (getFile() != null && getFile().exists()) {
			String localCopyName = getFile().getName() + "~";
			File localCopy = new File(getFile().getParentFile(), localCopyName);
			FileUtils.copyFileToFile(getFile(), localCopy);

		}
	}

	@Override
	public boolean hasBuilder() {
		return false;
	}

	private void fillInUnmappedAttributesAsDynamicProperties(FlexoModelObject object, XMLMapping currentMapping, XMLMapping revertedMapping) {
		ModelEntity currentEntity = currentMapping.entityForClass(object.getClass());
		ModelEntity revertedEntity = revertedMapping.entityForClass(object.getClass());
		for (Enumeration<ModelProperty> en = currentEntity.getModelProperties(); en.hasMoreElements();) {
			ModelProperty p = en.nextElement();
			if (p.getIsAttribute()) {
				if (revertedEntity.getModelPropertyWithName(p.getName()) == null) {
					// Found unmapped property
					String value = object.valueForKey(p.getName());
					if (value != null) {
						// logger.info("Object "+object+" found unmapped non-null attribute "+p.getName()+" value="+value);
						object.setDynamicPropertiesForKey(value, p.getName());
					}
				}
			}
		}
	}

	private boolean lastUniqueIDHasBeenSet = false;
	private long lastID;

	public boolean lastUniqueIDHasBeenSet() {
		return lastUniqueIDHasBeenSet;
	}

	@Override
	public long getNewFlexoID() {
		if (lastID < 0) {
			return -1;
		}
		return ++lastID;
	}

	public long getLastID() {
		if (lastUniqueIDHasBeenSet && lastID < 0) {
			lastID = 0;
		}
		return lastID;
	}

	@Override
	public void setLastID(long lastUniqueID) {
		System.out.println(">>>>>>>>>> setLastID with " + lastUniqueID);
		if (lastUniqueID > lastID) {
			lastID = lastUniqueID;
			lastUniqueIDHasBeenSet = true;
			System.out.println("Resource " + this + " lastID is now " + lastID);
		}
	}

	public static Document readXMLFile(File f) throws JDOMException, IOException {
		FileInputStream fio = new FileInputStream(f);
		SAXBuilder parser = new SAXBuilder();
		Document reply = parser.build(fio);
		return reply;
	}

	public static Element getElement(Document document, String name) {
		Iterator it = document.getDescendants(new ElementFilter(name));
		if (it.hasNext()) {
			return (Element) it.next();
		} else {
			return null;
		}
	}

	public static Element getElement(Element from, String name) {
		Iterator it = from.getDescendants(new ElementFilter(name));
		if (it.hasNext()) {
			return (Element) it.next();
		} else {
			return null;
		}
	}*/

}

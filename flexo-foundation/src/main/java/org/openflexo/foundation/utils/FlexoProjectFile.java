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

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openflexo.foundation.FlexoProject;
import org.openflexo.foundation.KVCFlexoObject;
import org.openflexo.foundation.resource.ProjectExternalRepository;
import org.openflexo.toolbox.FileUtils;

/**
 * Represents a file relative to a project
 * 
 * @author sguerin
 * 
 */
public class FlexoProjectFile extends KVCFlexoObject implements Cloneable {

	private static final Logger logger = Logger.getLogger(FlexoProjectFile.class.getPackage().getName());

	protected String relativePath;

	protected FlexoProject project;

	public FlexoProjectFile(File absoluteFile, File aProjectDirectory) {
		super();
		File temp = absoluteFile;
		relativePath = null;
		while (!temp.equals(aProjectDirectory) && temp.getParentFile() != null) {
			if (relativePath == null) {
				relativePath = temp.getName();
			} else {
				relativePath = temp.getName() + "/" + relativePath;
			}
			temp = temp.getParentFile();
		}
		if (temp.getParentFile() == null) {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("File " + absoluteFile.getAbsolutePath() + " is not contained in project " + aProjectDirectory);
				// TODO: try to look into external repository
			}
		}
	}

	public FlexoProjectFile(File absoluteFile, FlexoProject aProject) {
		this(absoluteFile, aProject.getProjectDirectory());
		project = aProject;
	}

	public FlexoProjectFile(String relativePathToProject) {
		super();
		if (relativePathToProject.indexOf(":") > -1) {
			repositoryName = relativePathToProject.substring(0, relativePathToProject.lastIndexOf(":"));
			relativePathToProject = relativePathToProject.substring(relativePathToProject.lastIndexOf(":") + 1);
		}
		relativePath = FileUtils.convertBackslashesToSlash(relativePathToProject);
	}

	public FlexoProjectFile(FlexoProject project, ProjectExternalRepository repository, String relativePathToRepository) {
		this(relativePathToRepository);
		setProject(project);
		setExternalRepository(repository);
	}

	public FlexoProjectFile(FlexoProject project, String relativePathToRepository) {
		this(relativePathToRepository);
		setProject(project);
	}

	private String repositoryName;

	// used as cache since getExternalRepositoryWithKey is very costly.
	private ProjectExternalRepository _externalRep;

	public ProjectExternalRepository getExternalRepository() {
		if (_externalRep != null) {
			return _externalRep;
		}
		if (getProject() != null && repositoryName != null) {
			return _externalRep = getProject().getExternalRepositoryWithKey(repositoryName);
		}
		return null;
	}

	public void setExternalRepository(ProjectExternalRepository repository) {
		this.repositoryName = repository.getIdentifier();
		clearCachedFile();
	}

	public FlexoProject getProject() {
		return project;
	}

	public void setProject(FlexoProject aProject) {
		project = aProject;
		clearCachedFile();
	}

	private File _cachedFile;

	public void clearCachedFile() {
		_cachedFile = null;
	}

	public File getFile() {
		if (_cachedFile != null) {
			return _cachedFile;
		}
		if (project != null) {
			ProjectExternalRepository rep = getExternalRepository();
			if (rep != null) {
				if (rep.getDirectory() != null) {
					return _cachedFile = new File(rep.getDirectory(), relativePath);
				} else {
					if (logger.isLoggable(Level.FINE)) {
						logger.fine("No directory defined for external repository");
					}
					return null;
				}
			} else {
				return _cachedFile = new File(project.getProjectDirectory(), relativePath);
			}
		} else {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Project was not set for this FlexoProjectFile!");
			}
			return null;
		}
	}

	public void setFile(File aFile) {
		_cachedFile = null;
		if (project != null) {
			File target = null;
			if (getExternalRepository() != null) {
				target = getExternalRepository().getDirectory();
			} else {
				target = project.getProjectDirectory();
			}
			File temp = aFile;
			String newRelativePath = null;
			while (!temp.equals(target) && temp.getParentFile() != null) {
				if (newRelativePath == null) {
					newRelativePath = temp.getName();
				} else {
					newRelativePath = temp.getName() + "/" + newRelativePath;
				}
				temp = temp.getParentFile();
			}
			if (temp.getParentFile() == null) {
				if (logger.isLoggable(Level.WARNING)) {
					logger.warning("File " + aFile.getAbsolutePath() + " is not contained in project " + project.getProjectDirectory()
							+ " or repository (target is: " + target.getAbsolutePath() + ")");
				}
			} else {
				relativePath = newRelativePath;
			}
		} else {
			if (logger.isLoggable(Level.WARNING)) {
				logger.warning("Project was not set for this FlexoProjectFile!");
			}
		}
	}

	public File getFile(File directory) {
		return new File(directory, relativePath);
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = FileUtils.convertBackslashesToSlash(relativePath);
	}

	@Override
	public String toString() {
		return getStringRepresentation();
	}

	public String getStringRepresentation() {
		ProjectExternalRepository rep = getExternalRepository();
		if (rep != null) {
			return rep.getIdentifier() + ":" + getRelativePath();
		}
		return getRelativePath();
	}

	public boolean nameIsValid() {
		return FileUtils.isStringValidForFileName(relativePath);
	}

	public void fixName() {
		if (!nameIsValid() && relativePath != null) {
			relativePath = FileUtils.getValidFileName(relativePath);
		}
	}

	@Override
	public Object clone() {
		return new FlexoProjectFile(getProject(), getExternalRepository(), getRelativePath());
	}

	public static boolean isContainedInProject(File aFile, FlexoProject project) {
		return FileUtils.isFileContainedIn(aFile, project.getProjectDirectory());
	}

	public static boolean isContainedInProjectDeclaredExternalRepositories(File aFile, FlexoProject project) {
		for (ProjectExternalRepository externalRepository : project.getExternalRepositories()) {
			if (FileUtils.isFileContainedIn(aFile, externalRepository.getDirectory())) {
				return true;
			}
		}
		return false;
	}

	public static ProjectExternalRepository getProjectExternalRepository(File aFile, FlexoProject project) {
		for (ProjectExternalRepository externalRepository : project.getExternalRepositories()) {
			if (FileUtils.isFileContainedIn(aFile, externalRepository.getDirectory())) {
				return externalRepository;
			}
		}
		return null;
	}

}

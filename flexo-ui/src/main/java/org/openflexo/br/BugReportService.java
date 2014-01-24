package org.openflexo.br;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.openflexo.ApplicationContext;
import org.openflexo.foundation.FlexoServiceImpl;
import org.openflexo.toolbox.FileResource;
import org.openflexo.toolbox.FileUtils;
import org.openflexo.ws.jira.JIRAGson;
import org.openflexo.ws.jira.model.JIRAProject;
import org.openflexo.ws.jira.model.JIRAProjectList;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class BugReportService extends FlexoServiceImpl {

	private static final File PROJECT_FILE = new FileResource("Config/jira_openflexo_project.json");
	private static final String OPENFLEXO_KEY = "OPENFLEXO";

	private File userProjectFile;
	private JIRAProject project;

	@Override
	public ApplicationContext getServiceManager() {
		return (ApplicationContext) super.getServiceManager();
	}

	public BugReportService() {
	}

	public JIRAProject getOpenFlexoProject() {
		return project;
	}

	private void copyOriginalToUserFile() {
		try {
			FileUtils.copyFileToFile(PROJECT_FILE, userProjectFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JIRAProject loadProjectsFromFile(File file) {
		try {
			JIRAProjectList projects = JIRAGson.getInstance().fromJson(new InputStreamReader(new FileInputStream(file)),
					JIRAProjectList.class);
			for (JIRAProject p : projects.getProjects()) {
				if (p.getKey().equals(OPENFLEXO_KEY)) {
					return project = p;
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize() {
		logger.info("Initialized BugReportService");
		userProjectFile = new File(FileUtils.getApplicationDataDirectory(), PROJECT_FILE.getName());
		if (!userProjectFile.exists()) {
			copyOriginalToUserFile();
		}
		try {
			Map<String, String> headers = new HashMap<String, String>();
			if (getServiceManager().getAdvancedPrefs().getBugReportUser() != null
					&& getServiceManager().getAdvancedPrefs().getBugReportUser().trim().length() > 0
					&& getServiceManager().getAdvancedPrefs().getBugReportPassword() != null
					&& getServiceManager().getAdvancedPrefs().getBugReportPassword().trim().length() > 0) {
				try {
					headers.put(
							"Authorization",
							"Basic "
									+ Base64.encodeBase64String((getServiceManager().getAdvancedPrefs().getBugReportUser() + ":" + getServiceManager()
											.getAdvancedPrefs().getBugReportPassword()).getBytes("ISO-8859-1")));
				} catch (UnsupportedEncodingException e) {
				}
				FileUtils.createOrUpdateFileFromURL(new URL(getServiceManager().getAdvancedPrefs().getBugReportUrl()
						+ "/rest/api/2/issue/createmeta?expand=projects.issuetypes.fields&projectKey=" + OPENFLEXO_KEY), userProjectFile,
						headers);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		project = loadProjectsFromFile(userProjectFile);
		if (project == null) {
			copyOriginalToUserFile();
			project = loadProjectsFromFile(PROJECT_FILE);
		}
	}

}

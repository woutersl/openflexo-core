/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Flexo-ui, a component of the software infrastructure 
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

package org.openflexo.ws.jira.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JIRAField<O extends JIRAObject<O>> extends JIRAObject<JIRAField<O>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2969521116466049956L;

	private static final Map<String, Class<? extends JIRAObject<?>>> TYPE_MAPPING = new HashMap<String, Class<? extends JIRAObject<?>>>();

	static {
		TYPE_MAPPING.put("priority", JIRAPriority.class);
		TYPE_MAPPING.put("version", JIRAVersion.class);
		TYPE_MAPPING.put("project", JIRAProject.class);
	}

	public static class JIRASchema extends JIRAObject<JIRASchema> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7907557368657754232L;
		private String type;
		private String system;
		private String items;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSystem() {
			return system;
		}

		public void setSystem(String system) {
			this.system = system;
		}

		public String getItems() {
			return items;
		}

		public void setItems(String items) {
			this.items = items;
		}

		private JIRAObject mutateObject(JIRAObject jiraObject) throws SecurityException, InstantiationException, IllegalAccessException,
				NoSuchFieldException {
			Class<? extends JIRAObject> target = null;
			if (getType().equals("array")) {
				target = TYPE_MAPPING.get(getItems());
			} else {
				target = TYPE_MAPPING.get(getType());
			}
			if (target != null) {
				return jiraObject.mutate(target);
			} else {
				return jiraObject;
			}
		}

		public boolean isSingle() {
			return !isMultiple();
		}

		public boolean isMultiple() {
			return "array".equals(getType());
		}
	}

	private boolean required;
	private JIRASchema schema;
	private String autoCompleteUrl;
	private List<String> operations;

	private List<O> allowedValues;

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getAutoCompleteUrl() {
		return autoCompleteUrl;
	}

	public void setAutoCompleteUrl(String autoCompleteUrl) {
		this.autoCompleteUrl = autoCompleteUrl;
	}

	public List<String> getOperations() {
		return operations;
	}

	public void setOperations(List<String> operations) {
		this.operations = operations;
	}

	public JIRASchema getSchema() {
		return schema;
	}

	public void setSchema(JIRASchema schema) {
		this.schema = schema;
	}

	public List<O> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(List<O> allowedValues) {
		this.allowedValues = allowedValues;
	}

	public <O1 extends JIRAObject<O1>> JIRAField<O1> mutateToFieldOfType(Class<O1> type) throws SecurityException, InstantiationException,
			IllegalAccessException, NoSuchFieldException {
		JIRAField<O1> mutated = new JIRAField<O1>();
		mutated.putAll(this);
		mutated.setAutoCompleteUrl(getAutoCompleteUrl());
		mutated.setId(getId());
		mutated.setKey(getKey());
		mutated.setOperations(getOperations());
		mutated.setRequired(isRequired());
		mutated.setSchema(getSchema());
		mutated.setSelf(getSelf());
		List<O> allowedValues = getAllowedValues();
		if (allowedValues != null) {
			List<O1> list = new ArrayList<O1>(allowedValues.size());
			for (O o : allowedValues) {
				list.add(o.mutate(type));
			}
			mutated.setAllowedValues(list);
		}
		return mutated;
	}

}

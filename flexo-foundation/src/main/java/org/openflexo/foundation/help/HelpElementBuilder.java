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

package org.openflexo.foundation.help;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jdom2.CDATA;
import org.jdom2.Element;
import org.openflexo.foundation.FlexoObject;

public class HelpElementBuilder {

	public static Element getHelpElement(ApplicationHelpEntryPoint helpEntryPoint) {
		Element reply = new Element("HelpEntry");
		reply.setAttribute("flexoID", String.valueOf(helpEntryPoint.getFlexoID()));
		reply.setAttribute("shortLabel", helpEntryPoint.getShortHelpLabel());
		reply.setAttribute("longLabel", helpEntryPoint.getTypedHelpLabel());
		reply.setAttribute("type", helpEntryPoint.getClass().getName());
		/*ScreenshotResource screenshootResource = helpEntryPoint.getProject().getScreenshotResource(
				helpEntryPoint instanceof OperationNode ? ((OperationNode) helpEntryPoint).getAbstractActivityNode()
						: (FlexoModelObject) helpEntryPoint, false);
		if (screenshootResource != null) {
			reply.setAttribute("screenshootName", screenshootResource.getFileName());
		}*/
		String parents = parentIDs(helpEntryPoint);
		if (parents.length() > 0) {
			reply.setAttribute("parents", parents);
		}

		String childs = childIDs(helpEntryPoint);
		if (childs.length() > 0) {
			reply.setAttribute("childs", childs);
		}

		Element d = buildDescriptionElement((FlexoObject) helpEntryPoint);
		if (d != null) {
			reply.addContent(d);
		}

		Element sd = buildSpecificDescriptionElement((FlexoObject) helpEntryPoint);
		if (sd != null) {
			reply.addContent(sd);
		}

		/*if (helpEntryPoint instanceof OperationNode) {
			Element buttons = buildButtonsElement((OperationNode) helpEntryPoint);
			if (buttons != null) {
				reply.addContent(buttons);
			}
		}*/

		return reply;
	}

	/*private static Element buildButtonsElement(OperationNode helpEntryPoint) {
		OperationComponentInstance ci = helpEntryPoint.getComponentInstance();
		if (ci != null && ci.getAllActionButtonPairs().keySet().size() > 0) {
			Element reply = new Element("buttons");
			Iterator<IEHyperlinkWidget> i = ci.getAllActionButtonPairs().keySet().iterator();
			while (i.hasNext()) {
				Element action = new Element("action");
				IEHyperlinkWidget b = i.next();
				ActionNode actionNode = ci.getAllActionButtonPairs().get(b);

				Element d = buildDescriptionElement(actionNode);
				if (d != null) {
					action.addContent(d);
				}

				Element sd = buildSpecificDescriptionElement(actionNode);
				if (sd != null) {
					action.addContent(sd);
				}

				if (b.isCustomButton()) {
					action.setAttribute("type", "Custom");
					action.setAttribute("value", b.getValue() == null ? "undefined" : b.getValue());
				} else if (b.isHyperlink()) {
					action.setAttribute("type", "Hyperlink");
					action.setAttribute("value", b.getValue() == null ? "undefined" : b.getValue());
				} else if (b.isImageButton()) {
					action.setAttribute("type", "Image");
					action.setAttribute("imageName", ((IEButtonWidget) b).getFile().getImageName());
					if (!((IEButtonWidget) b).isImportedImage()) {
						action.setAttribute("imageFramework", "DenaliWebResources");
					}
				}
				reply.addContent(action);
			}
			return reply;
		}
		return null;
	}*/

	private static Element buildSpecificDescriptionElement(FlexoObject o) {
		if (o.getSpecificDescriptions().size() > 0) {
			Element specificDescriptions = new Element("specificDescriptions");
			for (Entry<String, String> e : o.getSpecificDescriptions().entrySet()) {
				String k = e.getKey();
				String help = e.getValue();
				Element element = new Element(k);
				element.addContent(new CDATA(help));
				specificDescriptions.addContent(element);
			}
			return specificDescriptions;
		}
		return null;
	}

	private static Element buildDescriptionElement(FlexoObject o) {
		if (o.hasDescription()) {
			Element description = new Element("description");
			description.addContent(new CDATA(o.getDescription()));
			return description;
		}
		return null;
	}

	private static String parentIDs(ApplicationHelpEntryPoint helpEntryPoint) {
		StringBuffer reply = new StringBuffer("");
		ApplicationHelpEntryPoint parent = helpEntryPoint.getParentHelpEntry();
		while (parent != null) {
			reply.append(parent.getFlexoID());
			reply.append(",");
			parent = parent.getParentHelpEntry();
		}
		if (reply.length() == 0) {
			return "";
		}
		String ids = reply.toString();
		return ids.substring(0, ids.length() - 1);
	}

	private static String childIDs(ApplicationHelpEntryPoint helpEntryPoint) {
		StringBuffer reply = new StringBuffer("");
		List<ApplicationHelpEntryPoint> childs = helpEntryPoint.getChildsHelpObjects();
		Iterator<ApplicationHelpEntryPoint> it = childs.iterator();
		while (it.hasNext()) {
			ApplicationHelpEntryPoint elem = it.next();
			/*if (elem instanceof OperationNode) {
				if (((OperationNode) elem).getComponentInstance() != null) {
					reply.append(((OperationNode) elem).getComponentInstance().getFlexoID());
					reply.append(",");
				}
			} else {*/
			reply.append(elem.getFlexoID());
			reply.append(",");
			// }

		}
		if (reply.length() == 0) {
			return "";
		}
		String ids = reply.toString();
		return ids.substring(0, ids.length() - 1);
	}

}

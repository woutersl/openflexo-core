/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012, THALES SYSTEMES AEROPORTES - All Rights Reserved
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

package org.openflexo.foundation.ontology.util.visitor;

import org.openflexo.foundation.ontology.IFlexoOntologyBehaviouralProperty;
import org.openflexo.foundation.ontology.IFlexoOntologyClabject;
import org.openflexo.foundation.ontology.IFlexoOntologyClass;
import org.openflexo.foundation.ontology.IFlexoOntologyConcept;
import org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor;
import org.openflexo.foundation.ontology.IFlexoOntologyConstraint;
import org.openflexo.foundation.ontology.IFlexoOntologyDataProperty;
import org.openflexo.foundation.ontology.IFlexoOntologyDataPropertyValue;
import org.openflexo.foundation.ontology.IFlexoOntologyDataType;
import org.openflexo.foundation.ontology.IFlexoOntologyFeatureAssociation;
import org.openflexo.foundation.ontology.IFlexoOntologyIndividual;
import org.openflexo.foundation.ontology.IFlexoOntologyObjectProperty;
import org.openflexo.foundation.ontology.IFlexoOntologyObjectPropertyValue;
import org.openflexo.foundation.ontology.IFlexoOntologyPropertyValue;
import org.openflexo.foundation.technologyadapter.TechnologyAdapter;

/**
 * To Sting Visitor for Concepts.
 * 
 * @author gbesancon
 */
public class ToStringVisitor implements IFlexoOntologyConceptVisitor<String> {
	/**
	 * Constructor.
	 */
	public ToStringVisitor() {
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyConstraint)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyConstraint<TA> aConstraint) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyDataType)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyDataType<TA> aDataType) {
		StringBuilder builder = new StringBuilder();
		builder.append("DataType - ");
		builder.append(aDataType.getName());
		return builder.toString();
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyIndividual)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyIndividual<TA> aIndividual) {
		StringBuilder builder = new StringBuilder();
		builder.append("Individual - ");
		builder.append(aIndividual.getName());
		builder.append(" (");
		builder.append(aIndividual.getURI());
		builder.append(") : ");
		if (aIndividual.getTypes().size() != 0) {
			for (IFlexoOntologyClass<TA> aClass : aIndividual.getTypes()) {
				builder.append(aClass.getName());
				builder.append(", ");
			}
			builder.setLength(builder.length() - 2);
		}
		builder.append("\n");
		for (IFlexoOntologyPropertyValue<TA> propertyValue : aIndividual.getPropertyValues()) {
			if (propertyValue instanceof IFlexoOntologyDataPropertyValue) {
				if (((IFlexoOntologyDataPropertyValue<TA>) propertyValue).getValues() != null
						&& ((IFlexoOntologyDataPropertyValue<TA>) propertyValue).getValues().size() != 0) {
					builder.append("\t PropertyValue - (");
					builder.append(((IFlexoOntologyDataPropertyValue<TA>) propertyValue).getDataProperty().getName());
					builder.append(" = ");
					for (Object object : ((IFlexoOntologyDataPropertyValue<TA>) propertyValue).getValues()) {
						builder.append(object);
						builder.append(", ");
					}
					builder.setLength(builder.length() - 2);
					builder.append(")\n");
				}
			} else if (propertyValue instanceof IFlexoOntologyObjectPropertyValue) {
				if (((IFlexoOntologyObjectPropertyValue<TA>) propertyValue).getValues() != null
						&& ((IFlexoOntologyObjectPropertyValue<TA>) propertyValue).getValues().size() != 0) {
					builder.append("\t PropertyValue - (");
					builder.append(((IFlexoOntologyObjectPropertyValue<TA>) propertyValue).getObjectProperty().getName());
					builder.append(" = ");
					for (IFlexoOntologyConcept<TA> concept : ((IFlexoOntologyObjectPropertyValue<TA>) propertyValue).getValues()) {
						builder.append(concept.getName());
						builder.append(", ");
					}
					builder.setLength(builder.length() - 2);
					builder.append(")\n");
				}
			}
		}
		return builder.toString();
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyClass)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyClass<TA> aClass) {
		StringBuilder builder = new StringBuilder();
		builder.append("Class - ");
		builder.append(aClass.getName());
		builder.append(" (");
		builder.append(aClass.getURI());
		builder.append(")");
		if (aClass.getSuperClasses().size() != 0) {
			builder.append(" > ");
			for (IFlexoOntologyClass<TA> aSuperClass : aClass.getSuperClasses()) {
				builder.append(aSuperClass.getName());
				builder.append(", ");
			}
			builder.setLength(builder.length() - 2);
		}
		if (aClass.getStructuralFeatureAssociations().size() != 0) {
			builder.append("\n");
			for (IFlexoOntologyFeatureAssociation<TA> featureAssociation : aClass.getStructuralFeatureAssociations()) {
				builder.append("\t Feature - (");
				builder.append(featureAssociation.getLowerBound() == -1 ? "*" : featureAssociation.getLowerBound());
				builder.append("..");
				builder.append(featureAssociation.getUpperBound() == -1 ? "*" : featureAssociation.getUpperBound());
				builder.append(") : ");
				builder.append(featureAssociation.getFeature().getName());
				builder.append("\n");
			}
			builder.setLength(builder.length() - 1);
		}
		if (aClass.getBehaviouralFeatureAssociations().size() != 0) {
			builder.append("\n");
			for (IFlexoOntologyFeatureAssociation<TA> featureAssociation : aClass.getBehaviouralFeatureAssociations()) {
				builder.append("\t Feature - (");
				builder.append(featureAssociation.getLowerBound() == -1 ? "*" : featureAssociation.getLowerBound());
				builder.append("..");
				builder.append(featureAssociation.getUpperBound() == -1 ? "*" : featureAssociation.getUpperBound());
				builder.append(") : ");
				builder.append(featureAssociation.getFeature().getName());
				builder.append("\n");
			}
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyClabject)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyClabject<TA> aClabject) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyDataProperty)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyDataProperty<TA> aDataProperty) {
		StringBuilder builder = new StringBuilder();
		builder.append("Data Property - ");
		builder.append(aDataProperty.getName());
		builder.append(" (");
		builder.append(aDataProperty.getURI());
		builder.append(")");
		builder.append(" : ");
		builder.append(aDataProperty.getRange().getName());
		return builder.toString();
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyObjectProperty)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyObjectProperty<TA> aObjectProperty) {
		StringBuilder builder = new StringBuilder();
		builder.append("Object Property - ");
		builder.append(aObjectProperty.getName());
		builder.append(" (");
		builder.append(aObjectProperty.getURI());
		builder.append(")");
		builder.append(" : ");
		builder.append(aObjectProperty.getRange().getName());
		return builder.toString();
	}

	/**
	 * Follow the link.
	 * 
	 * @see org.openflexo.foundation.ontology.IFlexoOntologyConceptVisitor#visit(org.openflexo.foundation.ontology.IFlexoOntologyBehaviouralProperty)
	 */
	@Override
	public <TA extends TechnologyAdapter> String visit(IFlexoOntologyBehaviouralProperty<TA> aBehaviouralProperty) {
		// TODO Auto-generated method stub
		return null;
	}
}

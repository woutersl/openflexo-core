/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AFlexoConceptMemberFlexoConceptBodyDeclaration extends PFlexoConceptBodyDeclaration
{
    private PFlexoConceptMemberDeclaration _flexoConceptMemberDeclaration_;

    public AFlexoConceptMemberFlexoConceptBodyDeclaration()
    {
        // Constructor
    }

    public AFlexoConceptMemberFlexoConceptBodyDeclaration(
        @SuppressWarnings("hiding") PFlexoConceptMemberDeclaration _flexoConceptMemberDeclaration_)
    {
        // Constructor
        setFlexoConceptMemberDeclaration(_flexoConceptMemberDeclaration_);

    }

    @Override
    public Object clone()
    {
        return new AFlexoConceptMemberFlexoConceptBodyDeclaration(
            cloneNode(this._flexoConceptMemberDeclaration_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFlexoConceptMemberFlexoConceptBodyDeclaration(this);
    }

    public PFlexoConceptMemberDeclaration getFlexoConceptMemberDeclaration()
    {
        return this._flexoConceptMemberDeclaration_;
    }

    public void setFlexoConceptMemberDeclaration(PFlexoConceptMemberDeclaration node)
    {
        if(this._flexoConceptMemberDeclaration_ != null)
        {
            this._flexoConceptMemberDeclaration_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._flexoConceptMemberDeclaration_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._flexoConceptMemberDeclaration_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._flexoConceptMemberDeclaration_ == child)
        {
            this._flexoConceptMemberDeclaration_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._flexoConceptMemberDeclaration_ == oldChild)
        {
            setFlexoConceptMemberDeclaration((PFlexoConceptMemberDeclaration) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

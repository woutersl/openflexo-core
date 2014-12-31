/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AEmptyFlexoConceptMemberDeclaration extends PFlexoConceptMemberDeclaration
{
    private TSemi _semi_;

    public AEmptyFlexoConceptMemberDeclaration()
    {
        // Constructor
    }

    public AEmptyFlexoConceptMemberDeclaration(
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AEmptyFlexoConceptMemberDeclaration(
            cloneNode(this._semi_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEmptyFlexoConceptMemberDeclaration(this);
    }

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

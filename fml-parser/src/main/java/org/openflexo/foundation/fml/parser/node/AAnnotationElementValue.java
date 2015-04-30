/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAnnotationElementValue extends PElementValue
{
    private PAnnotation _annotation_;

    public AAnnotationElementValue()
    {
        // Constructor
    }

    public AAnnotationElementValue(
        @SuppressWarnings("hiding") PAnnotation _annotation_)
    {
        // Constructor
        setAnnotation(_annotation_);

    }

    @Override
    public Object clone()
    {
        return new AAnnotationElementValue(
            cloneNode(this._annotation_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAnnotationElementValue(this);
    }

    public PAnnotation getAnnotation()
    {
        return this._annotation_;
    }

    public void setAnnotation(PAnnotation node)
    {
        if(this._annotation_ != null)
        {
            this._annotation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._annotation_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._annotation_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._annotation_ == child)
        {
            this._annotation_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._annotation_ == oldChild)
        {
            setAnnotation((PAnnotation) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

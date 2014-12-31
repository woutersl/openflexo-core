/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAbstractModifier extends PModifier
{
    private TAbstract _abstract_;

    public AAbstractModifier()
    {
        // Constructor
    }

    public AAbstractModifier(
        @SuppressWarnings("hiding") TAbstract _abstract_)
    {
        // Constructor
        setAbstract(_abstract_);

    }

    @Override
    public Object clone()
    {
        return new AAbstractModifier(
            cloneNode(this._abstract_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAbstractModifier(this);
    }

    public TAbstract getAbstract()
    {
        return this._abstract_;
    }

    public void setAbstract(TAbstract node)
    {
        if(this._abstract_ != null)
        {
            this._abstract_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._abstract_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._abstract_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._abstract_ == child)
        {
            this._abstract_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._abstract_ == oldChild)
        {
            setAbstract((TAbstract) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ATypeParameter extends PTypeParameter
{
    private TIdentifier _identifier_;
    private PTypeBound _typeBound_;

    public ATypeParameter()
    {
        // Constructor
    }

    public ATypeParameter(
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") PTypeBound _typeBound_)
    {
        // Constructor
        setIdentifier(_identifier_);

        setTypeBound(_typeBound_);

    }

    @Override
    public Object clone()
    {
        return new ATypeParameter(
            cloneNode(this._identifier_),
            cloneNode(this._typeBound_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATypeParameter(this);
    }

    public TIdentifier getIdentifier()
    {
        return this._identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this._identifier_ != null)
        {
            this._identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier_ = node;
    }

    public PTypeBound getTypeBound()
    {
        return this._typeBound_;
    }

    public void setTypeBound(PTypeBound node)
    {
        if(this._typeBound_ != null)
        {
            this._typeBound_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeBound_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._identifier_)
            + toString(this._typeBound_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._typeBound_ == child)
        {
            this._typeBound_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        if(this._typeBound_ == oldChild)
        {
            setTypeBound((PTypeBound) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ADefaultValue extends PDefaultValue
{
    private TDefault _default_;
    private PElementValue _elementValue_;

    public ADefaultValue()
    {
        // Constructor
    }

    public ADefaultValue(
        @SuppressWarnings("hiding") TDefault _default_,
        @SuppressWarnings("hiding") PElementValue _elementValue_)
    {
        // Constructor
        setDefault(_default_);

        setElementValue(_elementValue_);

    }

    @Override
    public Object clone()
    {
        return new ADefaultValue(
            cloneNode(this._default_),
            cloneNode(this._elementValue_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADefaultValue(this);
    }

    public TDefault getDefault()
    {
        return this._default_;
    }

    public void setDefault(TDefault node)
    {
        if(this._default_ != null)
        {
            this._default_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._default_ = node;
    }

    public PElementValue getElementValue()
    {
        return this._elementValue_;
    }

    public void setElementValue(PElementValue node)
    {
        if(this._elementValue_ != null)
        {
            this._elementValue_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elementValue_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._default_)
            + toString(this._elementValue_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._default_ == child)
        {
            this._default_ = null;
            return;
        }

        if(this._elementValue_ == child)
        {
            this._elementValue_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._default_ == oldChild)
        {
            setDefault((TDefault) newChild);
            return;
        }

        if(this._elementValue_ == oldChild)
        {
            setElementValue((PElementValue) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

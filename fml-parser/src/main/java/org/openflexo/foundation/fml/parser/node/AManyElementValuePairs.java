/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyElementValuePairs extends PElementValuePairs
{
    private PElementValuePairs _elementValuePairs_;
    private TComma _comma_;
    private PElementValuePair _elementValuePair_;

    public AManyElementValuePairs()
    {
        // Constructor
    }

    public AManyElementValuePairs(
        @SuppressWarnings("hiding") PElementValuePairs _elementValuePairs_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PElementValuePair _elementValuePair_)
    {
        // Constructor
        setElementValuePairs(_elementValuePairs_);

        setComma(_comma_);

        setElementValuePair(_elementValuePair_);

    }

    @Override
    public Object clone()
    {
        return new AManyElementValuePairs(
            cloneNode(this._elementValuePairs_),
            cloneNode(this._comma_),
            cloneNode(this._elementValuePair_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyElementValuePairs(this);
    }

    public PElementValuePairs getElementValuePairs()
    {
        return this._elementValuePairs_;
    }

    public void setElementValuePairs(PElementValuePairs node)
    {
        if(this._elementValuePairs_ != null)
        {
            this._elementValuePairs_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elementValuePairs_ = node;
    }

    public TComma getComma()
    {
        return this._comma_;
    }

    public void setComma(TComma node)
    {
        if(this._comma_ != null)
        {
            this._comma_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comma_ = node;
    }

    public PElementValuePair getElementValuePair()
    {
        return this._elementValuePair_;
    }

    public void setElementValuePair(PElementValuePair node)
    {
        if(this._elementValuePair_ != null)
        {
            this._elementValuePair_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elementValuePair_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._elementValuePairs_)
            + toString(this._comma_)
            + toString(this._elementValuePair_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._elementValuePairs_ == child)
        {
            this._elementValuePairs_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._elementValuePair_ == child)
        {
            this._elementValuePair_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._elementValuePairs_ == oldChild)
        {
            setElementValuePairs((PElementValuePairs) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._elementValuePair_ == oldChild)
        {
            setElementValuePair((PElementValuePair) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

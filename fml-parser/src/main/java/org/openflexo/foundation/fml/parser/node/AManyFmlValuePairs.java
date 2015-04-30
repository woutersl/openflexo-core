/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyFmlValuePairs extends PFmlValuePairs
{
    private PFmlValuePairs _fmlValuePairs_;
    private TComma _comma_;
    private PFmlValuePair _fmlValuePair_;

    public AManyFmlValuePairs()
    {
        // Constructor
    }

    public AManyFmlValuePairs(
        @SuppressWarnings("hiding") PFmlValuePairs _fmlValuePairs_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PFmlValuePair _fmlValuePair_)
    {
        // Constructor
        setFmlValuePairs(_fmlValuePairs_);

        setComma(_comma_);

        setFmlValuePair(_fmlValuePair_);

    }

    @Override
    public Object clone()
    {
        return new AManyFmlValuePairs(
            cloneNode(this._fmlValuePairs_),
            cloneNode(this._comma_),
            cloneNode(this._fmlValuePair_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyFmlValuePairs(this);
    }

    public PFmlValuePairs getFmlValuePairs()
    {
        return this._fmlValuePairs_;
    }

    public void setFmlValuePairs(PFmlValuePairs node)
    {
        if(this._fmlValuePairs_ != null)
        {
            this._fmlValuePairs_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fmlValuePairs_ = node;
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

    public PFmlValuePair getFmlValuePair()
    {
        return this._fmlValuePair_;
    }

    public void setFmlValuePair(PFmlValuePair node)
    {
        if(this._fmlValuePair_ != null)
        {
            this._fmlValuePair_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fmlValuePair_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._fmlValuePairs_)
            + toString(this._comma_)
            + toString(this._fmlValuePair_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._fmlValuePairs_ == child)
        {
            this._fmlValuePairs_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._fmlValuePair_ == child)
        {
            this._fmlValuePair_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._fmlValuePairs_ == oldChild)
        {
            setFmlValuePairs((PFmlValuePairs) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._fmlValuePair_ == oldChild)
        {
            setFmlValuePair((PFmlValuePair) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

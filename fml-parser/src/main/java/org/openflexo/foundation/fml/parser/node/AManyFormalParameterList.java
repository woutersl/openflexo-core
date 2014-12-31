/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyFormalParameterList extends PFormalParameterList
{
    private PFormalParameters _formalParameters_;
    private TComma _comma_;
    private PLastFormalParameter _lastFormalParameter_;

    public AManyFormalParameterList()
    {
        // Constructor
    }

    public AManyFormalParameterList(
        @SuppressWarnings("hiding") PFormalParameters _formalParameters_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PLastFormalParameter _lastFormalParameter_)
    {
        // Constructor
        setFormalParameters(_formalParameters_);

        setComma(_comma_);

        setLastFormalParameter(_lastFormalParameter_);

    }

    @Override
    public Object clone()
    {
        return new AManyFormalParameterList(
            cloneNode(this._formalParameters_),
            cloneNode(this._comma_),
            cloneNode(this._lastFormalParameter_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyFormalParameterList(this);
    }

    public PFormalParameters getFormalParameters()
    {
        return this._formalParameters_;
    }

    public void setFormalParameters(PFormalParameters node)
    {
        if(this._formalParameters_ != null)
        {
            this._formalParameters_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._formalParameters_ = node;
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

    public PLastFormalParameter getLastFormalParameter()
    {
        return this._lastFormalParameter_;
    }

    public void setLastFormalParameter(PLastFormalParameter node)
    {
        if(this._lastFormalParameter_ != null)
        {
            this._lastFormalParameter_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lastFormalParameter_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._formalParameters_)
            + toString(this._comma_)
            + toString(this._lastFormalParameter_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._formalParameters_ == child)
        {
            this._formalParameters_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._lastFormalParameter_ == child)
        {
            this._lastFormalParameter_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._formalParameters_ == oldChild)
        {
            setFormalParameters((PFormalParameters) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._lastFormalParameter_ == oldChild)
        {
            setLastFormalParameter((PLastFormalParameter) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

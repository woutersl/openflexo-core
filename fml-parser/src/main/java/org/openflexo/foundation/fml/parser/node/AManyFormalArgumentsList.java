/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyFormalArgumentsList extends PFormalArgumentsList
{
    private PFormalArgumentsList _formalArgumentsList_;
    private TComma _comma_;
    private PFormalArgument _formalArgument_;

    public AManyFormalArgumentsList()
    {
        // Constructor
    }

    public AManyFormalArgumentsList(
        @SuppressWarnings("hiding") PFormalArgumentsList _formalArgumentsList_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PFormalArgument _formalArgument_)
    {
        // Constructor
        setFormalArgumentsList(_formalArgumentsList_);

        setComma(_comma_);

        setFormalArgument(_formalArgument_);

    }

    @Override
    public Object clone()
    {
        return new AManyFormalArgumentsList(
            cloneNode(this._formalArgumentsList_),
            cloneNode(this._comma_),
            cloneNode(this._formalArgument_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyFormalArgumentsList(this);
    }

    public PFormalArgumentsList getFormalArgumentsList()
    {
        return this._formalArgumentsList_;
    }

    public void setFormalArgumentsList(PFormalArgumentsList node)
    {
        if(this._formalArgumentsList_ != null)
        {
            this._formalArgumentsList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._formalArgumentsList_ = node;
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

    public PFormalArgument getFormalArgument()
    {
        return this._formalArgument_;
    }

    public void setFormalArgument(PFormalArgument node)
    {
        if(this._formalArgument_ != null)
        {
            this._formalArgument_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._formalArgument_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._formalArgumentsList_)
            + toString(this._comma_)
            + toString(this._formalArgument_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._formalArgumentsList_ == child)
        {
            this._formalArgumentsList_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._formalArgument_ == child)
        {
            this._formalArgument_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._formalArgumentsList_ == oldChild)
        {
            setFormalArgumentsList((PFormalArgumentsList) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._formalArgument_ == oldChild)
        {
            setFormalArgument((PFormalArgument) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

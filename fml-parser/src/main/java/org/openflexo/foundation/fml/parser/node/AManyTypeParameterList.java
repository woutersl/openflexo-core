/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyTypeParameterList extends PTypeParameterList
{
    private PTypeParameterList _typeParameterList_;
    private TComma _comma_;
    private PTypeParameter _typeParameter_;

    public AManyTypeParameterList()
    {
        // Constructor
    }

    public AManyTypeParameterList(
        @SuppressWarnings("hiding") PTypeParameterList _typeParameterList_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PTypeParameter _typeParameter_)
    {
        // Constructor
        setTypeParameterList(_typeParameterList_);

        setComma(_comma_);

        setTypeParameter(_typeParameter_);

    }

    @Override
    public Object clone()
    {
        return new AManyTypeParameterList(
            cloneNode(this._typeParameterList_),
            cloneNode(this._comma_),
            cloneNode(this._typeParameter_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyTypeParameterList(this);
    }

    public PTypeParameterList getTypeParameterList()
    {
        return this._typeParameterList_;
    }

    public void setTypeParameterList(PTypeParameterList node)
    {
        if(this._typeParameterList_ != null)
        {
            this._typeParameterList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeParameterList_ = node;
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

    public PTypeParameter getTypeParameter()
    {
        return this._typeParameter_;
    }

    public void setTypeParameter(PTypeParameter node)
    {
        if(this._typeParameter_ != null)
        {
            this._typeParameter_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeParameter_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._typeParameterList_)
            + toString(this._comma_)
            + toString(this._typeParameter_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._typeParameterList_ == child)
        {
            this._typeParameterList_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._typeParameter_ == child)
        {
            this._typeParameter_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._typeParameterList_ == oldChild)
        {
            setTypeParameterList((PTypeParameterList) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._typeParameter_ == oldChild)
        {
            setTypeParameter((PTypeParameter) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

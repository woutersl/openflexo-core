/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AUshrTypeParameters extends PTypeParameters
{
    private TLt _lt_;
    private PTypeParameterListUshr _typeParameterListUshr_;
    private TUshr _ushr_;

    public AUshrTypeParameters()
    {
        // Constructor
    }

    public AUshrTypeParameters(
        @SuppressWarnings("hiding") TLt _lt_,
        @SuppressWarnings("hiding") PTypeParameterListUshr _typeParameterListUshr_,
        @SuppressWarnings("hiding") TUshr _ushr_)
    {
        // Constructor
        setLt(_lt_);

        setTypeParameterListUshr(_typeParameterListUshr_);

        setUshr(_ushr_);

    }

    @Override
    public Object clone()
    {
        return new AUshrTypeParameters(
            cloneNode(this._lt_),
            cloneNode(this._typeParameterListUshr_),
            cloneNode(this._ushr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUshrTypeParameters(this);
    }

    public TLt getLt()
    {
        return this._lt_;
    }

    public void setLt(TLt node)
    {
        if(this._lt_ != null)
        {
            this._lt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lt_ = node;
    }

    public PTypeParameterListUshr getTypeParameterListUshr()
    {
        return this._typeParameterListUshr_;
    }

    public void setTypeParameterListUshr(PTypeParameterListUshr node)
    {
        if(this._typeParameterListUshr_ != null)
        {
            this._typeParameterListUshr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeParameterListUshr_ = node;
    }

    public TUshr getUshr()
    {
        return this._ushr_;
    }

    public void setUshr(TUshr node)
    {
        if(this._ushr_ != null)
        {
            this._ushr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ushr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lt_)
            + toString(this._typeParameterListUshr_)
            + toString(this._ushr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lt_ == child)
        {
            this._lt_ = null;
            return;
        }

        if(this._typeParameterListUshr_ == child)
        {
            this._typeParameterListUshr_ = null;
            return;
        }

        if(this._ushr_ == child)
        {
            this._ushr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lt_ == oldChild)
        {
            setLt((TLt) newChild);
            return;
        }

        if(this._typeParameterListUshr_ == oldChild)
        {
            setTypeParameterListUshr((PTypeParameterListUshr) newChild);
            return;
        }

        if(this._ushr_ == oldChild)
        {
            setUshr((TUshr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
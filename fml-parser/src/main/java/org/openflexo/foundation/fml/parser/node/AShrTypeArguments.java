/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AShrTypeArguments extends PTypeArguments
{
    private TLt _lt_;
    private PActualTypeArgumentListShr _actualTypeArgumentListShr_;
    private TShr _shr_;

    public AShrTypeArguments()
    {
        // Constructor
    }

    public AShrTypeArguments(
        @SuppressWarnings("hiding") TLt _lt_,
        @SuppressWarnings("hiding") PActualTypeArgumentListShr _actualTypeArgumentListShr_,
        @SuppressWarnings("hiding") TShr _shr_)
    {
        // Constructor
        setLt(_lt_);

        setActualTypeArgumentListShr(_actualTypeArgumentListShr_);

        setShr(_shr_);

    }

    @Override
    public Object clone()
    {
        return new AShrTypeArguments(
            cloneNode(this._lt_),
            cloneNode(this._actualTypeArgumentListShr_),
            cloneNode(this._shr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShrTypeArguments(this);
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

    public PActualTypeArgumentListShr getActualTypeArgumentListShr()
    {
        return this._actualTypeArgumentListShr_;
    }

    public void setActualTypeArgumentListShr(PActualTypeArgumentListShr node)
    {
        if(this._actualTypeArgumentListShr_ != null)
        {
            this._actualTypeArgumentListShr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._actualTypeArgumentListShr_ = node;
    }

    public TShr getShr()
    {
        return this._shr_;
    }

    public void setShr(TShr node)
    {
        if(this._shr_ != null)
        {
            this._shr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._shr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lt_)
            + toString(this._actualTypeArgumentListShr_)
            + toString(this._shr_);
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

        if(this._actualTypeArgumentListShr_ == child)
        {
            this._actualTypeArgumentListShr_ = null;
            return;
        }

        if(this._shr_ == child)
        {
            this._shr_ = null;
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

        if(this._actualTypeArgumentListShr_ == oldChild)
        {
            setActualTypeArgumentListShr((PActualTypeArgumentListShr) newChild);
            return;
        }

        if(this._shr_ == oldChild)
        {
            setShr((TShr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

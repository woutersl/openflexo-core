/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AShrNonWildTypeArguments extends PNonWildTypeArguments
{
    private TLt _lt_;
    private PReferenceTypeListShr _referenceTypeListShr_;
    private TShr _shr_;

    public AShrNonWildTypeArguments()
    {
        // Constructor
    }

    public AShrNonWildTypeArguments(
        @SuppressWarnings("hiding") TLt _lt_,
        @SuppressWarnings("hiding") PReferenceTypeListShr _referenceTypeListShr_,
        @SuppressWarnings("hiding") TShr _shr_)
    {
        // Constructor
        setLt(_lt_);

        setReferenceTypeListShr(_referenceTypeListShr_);

        setShr(_shr_);

    }

    @Override
    public Object clone()
    {
        return new AShrNonWildTypeArguments(
            cloneNode(this._lt_),
            cloneNode(this._referenceTypeListShr_),
            cloneNode(this._shr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShrNonWildTypeArguments(this);
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

    public PReferenceTypeListShr getReferenceTypeListShr()
    {
        return this._referenceTypeListShr_;
    }

    public void setReferenceTypeListShr(PReferenceTypeListShr node)
    {
        if(this._referenceTypeListShr_ != null)
        {
            this._referenceTypeListShr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._referenceTypeListShr_ = node;
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
            + toString(this._referenceTypeListShr_)
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

        if(this._referenceTypeListShr_ == child)
        {
            this._referenceTypeListShr_ = null;
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

        if(this._referenceTypeListShr_ == oldChild)
        {
            setReferenceTypeListShr((PReferenceTypeListShr) newChild);
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

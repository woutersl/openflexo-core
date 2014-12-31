/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ANeqEqReEqualityExpression extends PEqualityExpression
{
    private PEqualityExpression _equalityExpression_;
    private TNeq _neq_;
    private PRelationalExpression _relationalExpression_;

    public ANeqEqReEqualityExpression()
    {
        // Constructor
    }

    public ANeqEqReEqualityExpression(
        @SuppressWarnings("hiding") PEqualityExpression _equalityExpression_,
        @SuppressWarnings("hiding") TNeq _neq_,
        @SuppressWarnings("hiding") PRelationalExpression _relationalExpression_)
    {
        // Constructor
        setEqualityExpression(_equalityExpression_);

        setNeq(_neq_);

        setRelationalExpression(_relationalExpression_);

    }

    @Override
    public Object clone()
    {
        return new ANeqEqReEqualityExpression(
            cloneNode(this._equalityExpression_),
            cloneNode(this._neq_),
            cloneNode(this._relationalExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANeqEqReEqualityExpression(this);
    }

    public PEqualityExpression getEqualityExpression()
    {
        return this._equalityExpression_;
    }

    public void setEqualityExpression(PEqualityExpression node)
    {
        if(this._equalityExpression_ != null)
        {
            this._equalityExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._equalityExpression_ = node;
    }

    public TNeq getNeq()
    {
        return this._neq_;
    }

    public void setNeq(TNeq node)
    {
        if(this._neq_ != null)
        {
            this._neq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._neq_ = node;
    }

    public PRelationalExpression getRelationalExpression()
    {
        return this._relationalExpression_;
    }

    public void setRelationalExpression(PRelationalExpression node)
    {
        if(this._relationalExpression_ != null)
        {
            this._relationalExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._relationalExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._equalityExpression_)
            + toString(this._neq_)
            + toString(this._relationalExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._equalityExpression_ == child)
        {
            this._equalityExpression_ = null;
            return;
        }

        if(this._neq_ == child)
        {
            this._neq_ = null;
            return;
        }

        if(this._relationalExpression_ == child)
        {
            this._relationalExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._equalityExpression_ == oldChild)
        {
            setEqualityExpression((PEqualityExpression) newChild);
            return;
        }

        if(this._neq_ == oldChild)
        {
            setNeq((TNeq) newChild);
            return;
        }

        if(this._relationalExpression_ == oldChild)
        {
            setRelationalExpression((PRelationalExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

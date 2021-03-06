/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAmpAnEqAndExpression extends PAndExpression
{
    private PAndExpression _andExpression_;
    private TAmp _amp_;
    private PEqualityExpression _equalityExpression_;

    public AAmpAnEqAndExpression()
    {
        // Constructor
    }

    public AAmpAnEqAndExpression(
        @SuppressWarnings("hiding") PAndExpression _andExpression_,
        @SuppressWarnings("hiding") TAmp _amp_,
        @SuppressWarnings("hiding") PEqualityExpression _equalityExpression_)
    {
        // Constructor
        setAndExpression(_andExpression_);

        setAmp(_amp_);

        setEqualityExpression(_equalityExpression_);

    }

    @Override
    public Object clone()
    {
        return new AAmpAnEqAndExpression(
            cloneNode(this._andExpression_),
            cloneNode(this._amp_),
            cloneNode(this._equalityExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAmpAnEqAndExpression(this);
    }

    public PAndExpression getAndExpression()
    {
        return this._andExpression_;
    }

    public void setAndExpression(PAndExpression node)
    {
        if(this._andExpression_ != null)
        {
            this._andExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._andExpression_ = node;
    }

    public TAmp getAmp()
    {
        return this._amp_;
    }

    public void setAmp(TAmp node)
    {
        if(this._amp_ != null)
        {
            this._amp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._amp_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._andExpression_)
            + toString(this._amp_)
            + toString(this._equalityExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._andExpression_ == child)
        {
            this._andExpression_ = null;
            return;
        }

        if(this._amp_ == child)
        {
            this._amp_ = null;
            return;
        }

        if(this._equalityExpression_ == child)
        {
            this._equalityExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._andExpression_ == oldChild)
        {
            setAndExpression((PAndExpression) newChild);
            return;
        }

        if(this._amp_ == oldChild)
        {
            setAmp((TAmp) newChild);
            return;
        }

        if(this._equalityExpression_ == oldChild)
        {
            setEqualityExpression((PEqualityExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

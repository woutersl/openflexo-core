/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AShlShAdShiftExpression extends PShiftExpression
{
    private PShiftExpression _shiftExpression_;
    private TShl _shl_;
    private PAdditiveExpression _additiveExpression_;

    public AShlShAdShiftExpression()
    {
        // Constructor
    }

    public AShlShAdShiftExpression(
        @SuppressWarnings("hiding") PShiftExpression _shiftExpression_,
        @SuppressWarnings("hiding") TShl _shl_,
        @SuppressWarnings("hiding") PAdditiveExpression _additiveExpression_)
    {
        // Constructor
        setShiftExpression(_shiftExpression_);

        setShl(_shl_);

        setAdditiveExpression(_additiveExpression_);

    }

    @Override
    public Object clone()
    {
        return new AShlShAdShiftExpression(
            cloneNode(this._shiftExpression_),
            cloneNode(this._shl_),
            cloneNode(this._additiveExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAShlShAdShiftExpression(this);
    }

    public PShiftExpression getShiftExpression()
    {
        return this._shiftExpression_;
    }

    public void setShiftExpression(PShiftExpression node)
    {
        if(this._shiftExpression_ != null)
        {
            this._shiftExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._shiftExpression_ = node;
    }

    public TShl getShl()
    {
        return this._shl_;
    }

    public void setShl(TShl node)
    {
        if(this._shl_ != null)
        {
            this._shl_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._shl_ = node;
    }

    public PAdditiveExpression getAdditiveExpression()
    {
        return this._additiveExpression_;
    }

    public void setAdditiveExpression(PAdditiveExpression node)
    {
        if(this._additiveExpression_ != null)
        {
            this._additiveExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._additiveExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._shiftExpression_)
            + toString(this._shl_)
            + toString(this._additiveExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._shiftExpression_ == child)
        {
            this._shiftExpression_ = null;
            return;
        }

        if(this._shl_ == child)
        {
            this._shl_ = null;
            return;
        }

        if(this._additiveExpression_ == child)
        {
            this._additiveExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._shiftExpression_ == oldChild)
        {
            setShiftExpression((PShiftExpression) newChild);
            return;
        }

        if(this._shl_ == oldChild)
        {
            setShl((TShl) newChild);
            return;
        }

        if(this._additiveExpression_ == oldChild)
        {
            setAdditiveExpression((PAdditiveExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

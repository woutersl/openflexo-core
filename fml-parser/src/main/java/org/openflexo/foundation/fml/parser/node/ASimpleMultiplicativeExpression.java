/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ASimpleMultiplicativeExpression extends PMultiplicativeExpression
{
    private PUnaryExpression _unaryExpression_;

    public ASimpleMultiplicativeExpression()
    {
        // Constructor
    }

    public ASimpleMultiplicativeExpression(
        @SuppressWarnings("hiding") PUnaryExpression _unaryExpression_)
    {
        // Constructor
        setUnaryExpression(_unaryExpression_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleMultiplicativeExpression(
            cloneNode(this._unaryExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleMultiplicativeExpression(this);
    }

    public PUnaryExpression getUnaryExpression()
    {
        return this._unaryExpression_;
    }

    public void setUnaryExpression(PUnaryExpression node)
    {
        if(this._unaryExpression_ != null)
        {
            this._unaryExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._unaryExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._unaryExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._unaryExpression_ == child)
        {
            this._unaryExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._unaryExpression_ == oldChild)
        {
            setUnaryExpression((PUnaryExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

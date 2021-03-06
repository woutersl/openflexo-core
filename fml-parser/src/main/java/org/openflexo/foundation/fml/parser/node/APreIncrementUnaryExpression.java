/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class APreIncrementUnaryExpression extends PUnaryExpression
{
    private PPreIncrementExpression _preIncrementExpression_;

    public APreIncrementUnaryExpression()
    {
        // Constructor
    }

    public APreIncrementUnaryExpression(
        @SuppressWarnings("hiding") PPreIncrementExpression _preIncrementExpression_)
    {
        // Constructor
        setPreIncrementExpression(_preIncrementExpression_);

    }

    @Override
    public Object clone()
    {
        return new APreIncrementUnaryExpression(
            cloneNode(this._preIncrementExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPreIncrementUnaryExpression(this);
    }

    public PPreIncrementExpression getPreIncrementExpression()
    {
        return this._preIncrementExpression_;
    }

    public void setPreIncrementExpression(PPreIncrementExpression node)
    {
        if(this._preIncrementExpression_ != null)
        {
            this._preIncrementExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._preIncrementExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._preIncrementExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._preIncrementExpression_ == child)
        {
            this._preIncrementExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._preIncrementExpression_ == oldChild)
        {
            setPreIncrementExpression((PPreIncrementExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

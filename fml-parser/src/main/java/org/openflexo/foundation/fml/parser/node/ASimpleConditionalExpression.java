/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ASimpleConditionalExpression extends PConditionalExpression
{
    private PConditionalOrExpression _conditionalOrExpression_;

    public ASimpleConditionalExpression()
    {
        // Constructor
    }

    public ASimpleConditionalExpression(
        @SuppressWarnings("hiding") PConditionalOrExpression _conditionalOrExpression_)
    {
        // Constructor
        setConditionalOrExpression(_conditionalOrExpression_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleConditionalExpression(
            cloneNode(this._conditionalOrExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleConditionalExpression(this);
    }

    public PConditionalOrExpression getConditionalOrExpression()
    {
        return this._conditionalOrExpression_;
    }

    public void setConditionalOrExpression(PConditionalOrExpression node)
    {
        if(this._conditionalOrExpression_ != null)
        {
            this._conditionalOrExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conditionalOrExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._conditionalOrExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._conditionalOrExpression_ == child)
        {
            this._conditionalOrExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._conditionalOrExpression_ == oldChild)
        {
            setConditionalOrExpression((PConditionalOrExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

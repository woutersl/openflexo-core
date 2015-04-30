/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ASimpleConditionalOrExpression extends PConditionalOrExpression
{
    private PConditionalAndExpression _conditionalAndExpression_;

    public ASimpleConditionalOrExpression()
    {
        // Constructor
    }

    public ASimpleConditionalOrExpression(
        @SuppressWarnings("hiding") PConditionalAndExpression _conditionalAndExpression_)
    {
        // Constructor
        setConditionalAndExpression(_conditionalAndExpression_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleConditionalOrExpression(
            cloneNode(this._conditionalAndExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleConditionalOrExpression(this);
    }

    public PConditionalAndExpression getConditionalAndExpression()
    {
        return this._conditionalAndExpression_;
    }

    public void setConditionalAndExpression(PConditionalAndExpression node)
    {
        if(this._conditionalAndExpression_ != null)
        {
            this._conditionalAndExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conditionalAndExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._conditionalAndExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._conditionalAndExpression_ == child)
        {
            this._conditionalAndExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._conditionalAndExpression_ == oldChild)
        {
            setConditionalAndExpression((PConditionalAndExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

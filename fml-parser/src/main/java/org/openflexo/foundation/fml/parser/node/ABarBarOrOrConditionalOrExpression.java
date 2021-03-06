/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ABarBarOrOrConditionalOrExpression extends PConditionalOrExpression
{
    private PConditionalOrExpression _conditionalOrExpression_;
    private TBarBar _barBar_;
    private PConditionalAndExpression _conditionalAndExpression_;

    public ABarBarOrOrConditionalOrExpression()
    {
        // Constructor
    }

    public ABarBarOrOrConditionalOrExpression(
        @SuppressWarnings("hiding") PConditionalOrExpression _conditionalOrExpression_,
        @SuppressWarnings("hiding") TBarBar _barBar_,
        @SuppressWarnings("hiding") PConditionalAndExpression _conditionalAndExpression_)
    {
        // Constructor
        setConditionalOrExpression(_conditionalOrExpression_);

        setBarBar(_barBar_);

        setConditionalAndExpression(_conditionalAndExpression_);

    }

    @Override
    public Object clone()
    {
        return new ABarBarOrOrConditionalOrExpression(
            cloneNode(this._conditionalOrExpression_),
            cloneNode(this._barBar_),
            cloneNode(this._conditionalAndExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABarBarOrOrConditionalOrExpression(this);
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

    public TBarBar getBarBar()
    {
        return this._barBar_;
    }

    public void setBarBar(TBarBar node)
    {
        if(this._barBar_ != null)
        {
            this._barBar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._barBar_ = node;
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
            + toString(this._conditionalOrExpression_)
            + toString(this._barBar_)
            + toString(this._conditionalAndExpression_);
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

        if(this._barBar_ == child)
        {
            this._barBar_ = null;
            return;
        }

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
        if(this._conditionalOrExpression_ == oldChild)
        {
            setConditionalOrExpression((PConditionalOrExpression) newChild);
            return;
        }

        if(this._barBar_ == oldChild)
        {
            setBarBar((TBarBar) newChild);
            return;
        }

        if(this._conditionalAndExpression_ == oldChild)
        {
            setConditionalAndExpression((PConditionalAndExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

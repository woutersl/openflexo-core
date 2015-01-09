/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AQmarkOrExOrConditionalExpression extends PConditionalExpression
{
    private PConditionalOrExpression _conditionalOrExpression_;
    private TQmark _qmark_;
    private PExpression _expression_;
    private TColon _colon_;
    private PConditionalExpression _conditionalExpression_;

    public AQmarkOrExOrConditionalExpression()
    {
        // Constructor
    }

    public AQmarkOrExOrConditionalExpression(
        @SuppressWarnings("hiding") PConditionalOrExpression _conditionalOrExpression_,
        @SuppressWarnings("hiding") TQmark _qmark_,
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") PConditionalExpression _conditionalExpression_)
    {
        // Constructor
        setConditionalOrExpression(_conditionalOrExpression_);

        setQmark(_qmark_);

        setExpression(_expression_);

        setColon(_colon_);

        setConditionalExpression(_conditionalExpression_);

    }

    @Override
    public Object clone()
    {
        return new AQmarkOrExOrConditionalExpression(
            cloneNode(this._conditionalOrExpression_),
            cloneNode(this._qmark_),
            cloneNode(this._expression_),
            cloneNode(this._colon_),
            cloneNode(this._conditionalExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAQmarkOrExOrConditionalExpression(this);
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

    public TQmark getQmark()
    {
        return this._qmark_;
    }

    public void setQmark(TQmark node)
    {
        if(this._qmark_ != null)
        {
            this._qmark_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._qmark_ = node;
    }

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public PConditionalExpression getConditionalExpression()
    {
        return this._conditionalExpression_;
    }

    public void setConditionalExpression(PConditionalExpression node)
    {
        if(this._conditionalExpression_ != null)
        {
            this._conditionalExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conditionalExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._conditionalOrExpression_)
            + toString(this._qmark_)
            + toString(this._expression_)
            + toString(this._colon_)
            + toString(this._conditionalExpression_);
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

        if(this._qmark_ == child)
        {
            this._qmark_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._conditionalExpression_ == child)
        {
            this._conditionalExpression_ = null;
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

        if(this._qmark_ == oldChild)
        {
            setQmark((TQmark) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._conditionalExpression_ == oldChild)
        {
            setConditionalExpression((PConditionalExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
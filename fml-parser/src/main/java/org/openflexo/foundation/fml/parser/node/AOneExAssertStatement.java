/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AOneExAssertStatement extends PAssertStatement
{
    private TAssert _assert_;
    private PExpression _expression_;
    private TSemi _semi_;

    public AOneExAssertStatement()
    {
        // Constructor
    }

    public AOneExAssertStatement(
        @SuppressWarnings("hiding") TAssert _assert_,
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setAssert(_assert_);

        setExpression(_expression_);

        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AOneExAssertStatement(
            cloneNode(this._assert_),
            cloneNode(this._expression_),
            cloneNode(this._semi_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOneExAssertStatement(this);
    }

    public TAssert getAssert()
    {
        return this._assert_;
    }

    public void setAssert(TAssert node)
    {
        if(this._assert_ != null)
        {
            this._assert_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assert_ = node;
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

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._assert_)
            + toString(this._expression_)
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._assert_ == child)
        {
            this._assert_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._assert_ == oldChild)
        {
            setAssert((TAssert) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
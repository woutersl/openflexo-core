/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AExpressionPostDecrementExpression extends PPostDecrementExpression
{
    private PPostfixExpression _postfixExpression_;
    private TMinusMinus _minusMinus_;

    public AExpressionPostDecrementExpression()
    {
        // Constructor
    }

    public AExpressionPostDecrementExpression(
        @SuppressWarnings("hiding") PPostfixExpression _postfixExpression_,
        @SuppressWarnings("hiding") TMinusMinus _minusMinus_)
    {
        // Constructor
        setPostfixExpression(_postfixExpression_);

        setMinusMinus(_minusMinus_);

    }

    @Override
    public Object clone()
    {
        return new AExpressionPostDecrementExpression(
            cloneNode(this._postfixExpression_),
            cloneNode(this._minusMinus_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExpressionPostDecrementExpression(this);
    }

    public PPostfixExpression getPostfixExpression()
    {
        return this._postfixExpression_;
    }

    public void setPostfixExpression(PPostfixExpression node)
    {
        if(this._postfixExpression_ != null)
        {
            this._postfixExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._postfixExpression_ = node;
    }

    public TMinusMinus getMinusMinus()
    {
        return this._minusMinus_;
    }

    public void setMinusMinus(TMinusMinus node)
    {
        if(this._minusMinus_ != null)
        {
            this._minusMinus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._minusMinus_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._postfixExpression_)
            + toString(this._minusMinus_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._postfixExpression_ == child)
        {
            this._postfixExpression_ = null;
            return;
        }

        if(this._minusMinus_ == child)
        {
            this._minusMinus_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._postfixExpression_ == oldChild)
        {
            setPostfixExpression((PPostfixExpression) newChild);
            return;
        }

        if(this._minusMinus_ == oldChild)
        {
            setMinusMinus((TMinusMinus) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
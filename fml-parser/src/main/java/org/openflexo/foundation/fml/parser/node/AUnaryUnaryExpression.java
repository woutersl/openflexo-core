/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AUnaryUnaryExpression extends PUnaryExpression
{
    private PUnaryExpressionNotPlusMinus _unaryExpressionNotPlusMinus_;

    public AUnaryUnaryExpression()
    {
        // Constructor
    }

    public AUnaryUnaryExpression(
        @SuppressWarnings("hiding") PUnaryExpressionNotPlusMinus _unaryExpressionNotPlusMinus_)
    {
        // Constructor
        setUnaryExpressionNotPlusMinus(_unaryExpressionNotPlusMinus_);

    }

    @Override
    public Object clone()
    {
        return new AUnaryUnaryExpression(
            cloneNode(this._unaryExpressionNotPlusMinus_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUnaryUnaryExpression(this);
    }

    public PUnaryExpressionNotPlusMinus getUnaryExpressionNotPlusMinus()
    {
        return this._unaryExpressionNotPlusMinus_;
    }

    public void setUnaryExpressionNotPlusMinus(PUnaryExpressionNotPlusMinus node)
    {
        if(this._unaryExpressionNotPlusMinus_ != null)
        {
            this._unaryExpressionNotPlusMinus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._unaryExpressionNotPlusMinus_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._unaryExpressionNotPlusMinus_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._unaryExpressionNotPlusMinus_ == child)
        {
            this._unaryExpressionNotPlusMinus_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._unaryExpressionNotPlusMinus_ == oldChild)
        {
            setUnaryExpressionNotPlusMinus((PUnaryExpressionNotPlusMinus) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AOneStatementExpressionList extends PStatementExpressionList
{
    private PStatementExpression _statementExpression_;

    public AOneStatementExpressionList()
    {
        // Constructor
    }

    public AOneStatementExpressionList(
        @SuppressWarnings("hiding") PStatementExpression _statementExpression_)
    {
        // Constructor
        setStatementExpression(_statementExpression_);

    }

    @Override
    public Object clone()
    {
        return new AOneStatementExpressionList(
            cloneNode(this._statementExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOneStatementExpressionList(this);
    }

    public PStatementExpression getStatementExpression()
    {
        return this._statementExpression_;
    }

    public void setStatementExpression(PStatementExpression node)
    {
        if(this._statementExpression_ != null)
        {
            this._statementExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statementExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._statementExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._statementExpression_ == child)
        {
            this._statementExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._statementExpression_ == oldChild)
        {
            setStatementExpression((PStatementExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ADoStatementStatementWithoutTrailingSubstatement extends PStatementWithoutTrailingSubstatement
{
    private PDoStatement _doStatement_;

    public ADoStatementStatementWithoutTrailingSubstatement()
    {
        // Constructor
    }

    public ADoStatementStatementWithoutTrailingSubstatement(
        @SuppressWarnings("hiding") PDoStatement _doStatement_)
    {
        // Constructor
        setDoStatement(_doStatement_);

    }

    @Override
    public Object clone()
    {
        return new ADoStatementStatementWithoutTrailingSubstatement(
            cloneNode(this._doStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADoStatementStatementWithoutTrailingSubstatement(this);
    }

    public PDoStatement getDoStatement()
    {
        return this._doStatement_;
    }

    public void setDoStatement(PDoStatement node)
    {
        if(this._doStatement_ != null)
        {
            this._doStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._doStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._doStatement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._doStatement_ == child)
        {
            this._doStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._doStatement_ == oldChild)
        {
            setDoStatement((PDoStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
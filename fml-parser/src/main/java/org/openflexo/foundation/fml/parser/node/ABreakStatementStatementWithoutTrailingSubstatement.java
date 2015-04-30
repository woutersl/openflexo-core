/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ABreakStatementStatementWithoutTrailingSubstatement extends PStatementWithoutTrailingSubstatement
{
    private PBreakStatement _breakStatement_;

    public ABreakStatementStatementWithoutTrailingSubstatement()
    {
        // Constructor
    }

    public ABreakStatementStatementWithoutTrailingSubstatement(
        @SuppressWarnings("hiding") PBreakStatement _breakStatement_)
    {
        // Constructor
        setBreakStatement(_breakStatement_);

    }

    @Override
    public Object clone()
    {
        return new ABreakStatementStatementWithoutTrailingSubstatement(
            cloneNode(this._breakStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABreakStatementStatementWithoutTrailingSubstatement(this);
    }

    public PBreakStatement getBreakStatement()
    {
        return this._breakStatement_;
    }

    public void setBreakStatement(PBreakStatement node)
    {
        if(this._breakStatement_ != null)
        {
            this._breakStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._breakStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._breakStatement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._breakStatement_ == child)
        {
            this._breakStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._breakStatement_ == oldChild)
        {
            setBreakStatement((PBreakStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

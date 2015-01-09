/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ATryStatementStatementWithoutTrailingSubstatement extends PStatementWithoutTrailingSubstatement
{
    private PTryStatement _tryStatement_;

    public ATryStatementStatementWithoutTrailingSubstatement()
    {
        // Constructor
    }

    public ATryStatementStatementWithoutTrailingSubstatement(
        @SuppressWarnings("hiding") PTryStatement _tryStatement_)
    {
        // Constructor
        setTryStatement(_tryStatement_);

    }

    @Override
    public Object clone()
    {
        return new ATryStatementStatementWithoutTrailingSubstatement(
            cloneNode(this._tryStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATryStatementStatementWithoutTrailingSubstatement(this);
    }

    public PTryStatement getTryStatement()
    {
        return this._tryStatement_;
    }

    public void setTryStatement(PTryStatement node)
    {
        if(this._tryStatement_ != null)
        {
            this._tryStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._tryStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._tryStatement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._tryStatement_ == child)
        {
            this._tryStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._tryStatement_ == oldChild)
        {
            setTryStatement((PTryStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
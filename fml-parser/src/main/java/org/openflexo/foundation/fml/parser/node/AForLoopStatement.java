/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AForLoopStatement extends PStatement
{
    private PForStatement _forStatement_;

    public AForLoopStatement()
    {
        // Constructor
    }

    public AForLoopStatement(
        @SuppressWarnings("hiding") PForStatement _forStatement_)
    {
        // Constructor
        setForStatement(_forStatement_);

    }

    @Override
    public Object clone()
    {
        return new AForLoopStatement(
            cloneNode(this._forStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAForLoopStatement(this);
    }

    public PForStatement getForStatement()
    {
        return this._forStatement_;
    }

    public void setForStatement(PForStatement node)
    {
        if(this._forStatement_ != null)
        {
            this._forStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._forStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._forStatement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._forStatement_ == child)
        {
            this._forStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._forStatement_ == oldChild)
        {
            setForStatement((PForStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

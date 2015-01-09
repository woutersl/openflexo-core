/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAssignAssignmentOperator extends PAssignmentOperator
{
    private TAssign _assign_;

    public AAssignAssignmentOperator()
    {
        // Constructor
    }

    public AAssignAssignmentOperator(
        @SuppressWarnings("hiding") TAssign _assign_)
    {
        // Constructor
        setAssign(_assign_);

    }

    @Override
    public Object clone()
    {
        return new AAssignAssignmentOperator(
            cloneNode(this._assign_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAssignAssignmentOperator(this);
    }

    public TAssign getAssign()
    {
        return this._assign_;
    }

    public void setAssign(TAssign node)
    {
        if(this._assign_ != null)
        {
            this._assign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assign_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._assign_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._assign_ == child)
        {
            this._assign_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._assign_ == oldChild)
        {
            setAssign((TAssign) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
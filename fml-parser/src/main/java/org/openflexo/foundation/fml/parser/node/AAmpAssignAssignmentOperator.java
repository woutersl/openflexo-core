/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAmpAssignAssignmentOperator extends PAssignmentOperator
{
    private TAmpAssign _ampAssign_;

    public AAmpAssignAssignmentOperator()
    {
        // Constructor
    }

    public AAmpAssignAssignmentOperator(
        @SuppressWarnings("hiding") TAmpAssign _ampAssign_)
    {
        // Constructor
        setAmpAssign(_ampAssign_);

    }

    @Override
    public Object clone()
    {
        return new AAmpAssignAssignmentOperator(
            cloneNode(this._ampAssign_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAmpAssignAssignmentOperator(this);
    }

    public TAmpAssign getAmpAssign()
    {
        return this._ampAssign_;
    }

    public void setAmpAssign(TAmpAssign node)
    {
        if(this._ampAssign_ != null)
        {
            this._ampAssign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ampAssign_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._ampAssign_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._ampAssign_ == child)
        {
            this._ampAssign_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._ampAssign_ == oldChild)
        {
            setAmpAssign((TAmpAssign) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

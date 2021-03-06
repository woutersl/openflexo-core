/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ASlashAssignAssignmentOperator extends PAssignmentOperator
{
    private TSlashAssign _slashAssign_;

    public ASlashAssignAssignmentOperator()
    {
        // Constructor
    }

    public ASlashAssignAssignmentOperator(
        @SuppressWarnings("hiding") TSlashAssign _slashAssign_)
    {
        // Constructor
        setSlashAssign(_slashAssign_);

    }

    @Override
    public Object clone()
    {
        return new ASlashAssignAssignmentOperator(
            cloneNode(this._slashAssign_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASlashAssignAssignmentOperator(this);
    }

    public TSlashAssign getSlashAssign()
    {
        return this._slashAssign_;
    }

    public void setSlashAssign(TSlashAssign node)
    {
        if(this._slashAssign_ != null)
        {
            this._slashAssign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._slashAssign_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._slashAssign_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._slashAssign_ == child)
        {
            this._slashAssign_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._slashAssign_ == oldChild)
        {
            setSlashAssign((TSlashAssign) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

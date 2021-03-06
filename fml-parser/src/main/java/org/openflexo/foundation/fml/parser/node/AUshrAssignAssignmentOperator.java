/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AUshrAssignAssignmentOperator extends PAssignmentOperator
{
    private TUshrAssign _ushrAssign_;

    public AUshrAssignAssignmentOperator()
    {
        // Constructor
    }

    public AUshrAssignAssignmentOperator(
        @SuppressWarnings("hiding") TUshrAssign _ushrAssign_)
    {
        // Constructor
        setUshrAssign(_ushrAssign_);

    }

    @Override
    public Object clone()
    {
        return new AUshrAssignAssignmentOperator(
            cloneNode(this._ushrAssign_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAUshrAssignAssignmentOperator(this);
    }

    public TUshrAssign getUshrAssign()
    {
        return this._ushrAssign_;
    }

    public void setUshrAssign(TUshrAssign node)
    {
        if(this._ushrAssign_ != null)
        {
            this._ushrAssign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ushrAssign_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._ushrAssign_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._ushrAssign_ == child)
        {
            this._ushrAssign_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._ushrAssign_ == oldChild)
        {
            setUshrAssign((TUshrAssign) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

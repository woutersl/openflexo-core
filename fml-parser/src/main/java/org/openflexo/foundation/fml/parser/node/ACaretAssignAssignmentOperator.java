/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ACaretAssignAssignmentOperator extends PAssignmentOperator
{
    private TCaretAssign _caretAssign_;

    public ACaretAssignAssignmentOperator()
    {
        // Constructor
    }

    public ACaretAssignAssignmentOperator(
        @SuppressWarnings("hiding") TCaretAssign _caretAssign_)
    {
        // Constructor
        setCaretAssign(_caretAssign_);

    }

    @Override
    public Object clone()
    {
        return new ACaretAssignAssignmentOperator(
            cloneNode(this._caretAssign_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACaretAssignAssignmentOperator(this);
    }

    public TCaretAssign getCaretAssign()
    {
        return this._caretAssign_;
    }

    public void setCaretAssign(TCaretAssign node)
    {
        if(this._caretAssign_ != null)
        {
            this._caretAssign_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._caretAssign_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._caretAssign_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._caretAssign_ == child)
        {
            this._caretAssign_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._caretAssign_ == oldChild)
        {
            setCaretAssign((TCaretAssign) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
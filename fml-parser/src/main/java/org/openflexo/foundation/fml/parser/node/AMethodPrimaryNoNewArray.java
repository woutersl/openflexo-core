/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AMethodPrimaryNoNewArray extends PPrimaryNoNewArray
{
    private PMethodInvocation _methodInvocation_;

    public AMethodPrimaryNoNewArray()
    {
        // Constructor
    }

    public AMethodPrimaryNoNewArray(
        @SuppressWarnings("hiding") PMethodInvocation _methodInvocation_)
    {
        // Constructor
        setMethodInvocation(_methodInvocation_);

    }

    @Override
    public Object clone()
    {
        return new AMethodPrimaryNoNewArray(
            cloneNode(this._methodInvocation_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMethodPrimaryNoNewArray(this);
    }

    public PMethodInvocation getMethodInvocation()
    {
        return this._methodInvocation_;
    }

    public void setMethodInvocation(PMethodInvocation node)
    {
        if(this._methodInvocation_ != null)
        {
            this._methodInvocation_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._methodInvocation_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._methodInvocation_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._methodInvocation_ == child)
        {
            this._methodInvocation_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._methodInvocation_ == oldChild)
        {
            setMethodInvocation((PMethodInvocation) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

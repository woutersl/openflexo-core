/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ABooleanPrimitiveType extends PPrimitiveType
{
    private TBoolean _boolean_;

    public ABooleanPrimitiveType()
    {
        // Constructor
    }

    public ABooleanPrimitiveType(
        @SuppressWarnings("hiding") TBoolean _boolean_)
    {
        // Constructor
        setBoolean(_boolean_);

    }

    @Override
    public Object clone()
    {
        return new ABooleanPrimitiveType(
            cloneNode(this._boolean_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABooleanPrimitiveType(this);
    }

    public TBoolean getBoolean()
    {
        return this._boolean_;
    }

    public void setBoolean(TBoolean node)
    {
        if(this._boolean_ != null)
        {
            this._boolean_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._boolean_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._boolean_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._boolean_ == child)
        {
            this._boolean_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._boolean_ == oldChild)
        {
            setBoolean((TBoolean) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

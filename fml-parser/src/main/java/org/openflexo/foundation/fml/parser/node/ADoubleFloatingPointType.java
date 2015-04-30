/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ADoubleFloatingPointType extends PFloatingPointType
{
    private TDouble _double_;

    public ADoubleFloatingPointType()
    {
        // Constructor
    }

    public ADoubleFloatingPointType(
        @SuppressWarnings("hiding") TDouble _double_)
    {
        // Constructor
        setDouble(_double_);

    }

    @Override
    public Object clone()
    {
        return new ADoubleFloatingPointType(
            cloneNode(this._double_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADoubleFloatingPointType(this);
    }

    public TDouble getDouble()
    {
        return this._double_;
    }

    public void setDouble(TDouble node)
    {
        if(this._double_ != null)
        {
            this._double_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._double_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._double_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._double_ == child)
        {
            this._double_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._double_ == oldChild)
        {
            setDouble((TDouble) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

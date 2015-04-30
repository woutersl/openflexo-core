/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AOneActualTypeArgumentList extends PActualTypeArgumentList
{
    private PActualTypeArgument _actualTypeArgument_;

    public AOneActualTypeArgumentList()
    {
        // Constructor
    }

    public AOneActualTypeArgumentList(
        @SuppressWarnings("hiding") PActualTypeArgument _actualTypeArgument_)
    {
        // Constructor
        setActualTypeArgument(_actualTypeArgument_);

    }

    @Override
    public Object clone()
    {
        return new AOneActualTypeArgumentList(
            cloneNode(this._actualTypeArgument_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOneActualTypeArgumentList(this);
    }

    public PActualTypeArgument getActualTypeArgument()
    {
        return this._actualTypeArgument_;
    }

    public void setActualTypeArgument(PActualTypeArgument node)
    {
        if(this._actualTypeArgument_ != null)
        {
            this._actualTypeArgument_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._actualTypeArgument_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._actualTypeArgument_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._actualTypeArgument_ == child)
        {
            this._actualTypeArgument_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._actualTypeArgument_ == oldChild)
        {
            setActualTypeArgument((PActualTypeArgument) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

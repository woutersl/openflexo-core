/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AStaticInitializer extends PStaticInitializer
{
    private TStatic _static_;
    private PBlock _block_;

    public AStaticInitializer()
    {
        // Constructor
    }

    public AStaticInitializer(
        @SuppressWarnings("hiding") TStatic _static_,
        @SuppressWarnings("hiding") PBlock _block_)
    {
        // Constructor
        setStatic(_static_);

        setBlock(_block_);

    }

    @Override
    public Object clone()
    {
        return new AStaticInitializer(
            cloneNode(this._static_),
            cloneNode(this._block_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStaticInitializer(this);
    }

    public TStatic getStatic()
    {
        return this._static_;
    }

    public void setStatic(TStatic node)
    {
        if(this._static_ != null)
        {
            this._static_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._static_ = node;
    }

    public PBlock getBlock()
    {
        return this._block_;
    }

    public void setBlock(PBlock node)
    {
        if(this._block_ != null)
        {
            this._block_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._block_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._static_)
            + toString(this._block_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._static_ == child)
        {
            this._static_ = null;
            return;
        }

        if(this._block_ == child)
        {
            this._block_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._static_ == oldChild)
        {
            setStatic((TStatic) newChild);
            return;
        }

        if(this._block_ == oldChild)
        {
            setBlock((PBlock) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

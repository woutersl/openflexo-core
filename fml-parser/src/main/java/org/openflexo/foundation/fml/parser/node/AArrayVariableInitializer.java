/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AArrayVariableInitializer extends PVariableInitializer
{
    private PArrayInitializer _arrayInitializer_;

    public AArrayVariableInitializer()
    {
        // Constructor
    }

    public AArrayVariableInitializer(
        @SuppressWarnings("hiding") PArrayInitializer _arrayInitializer_)
    {
        // Constructor
        setArrayInitializer(_arrayInitializer_);

    }

    @Override
    public Object clone()
    {
        return new AArrayVariableInitializer(
            cloneNode(this._arrayInitializer_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArrayVariableInitializer(this);
    }

    public PArrayInitializer getArrayInitializer()
    {
        return this._arrayInitializer_;
    }

    public void setArrayInitializer(PArrayInitializer node)
    {
        if(this._arrayInitializer_ != null)
        {
            this._arrayInitializer_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._arrayInitializer_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._arrayInitializer_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._arrayInitializer_ == child)
        {
            this._arrayInitializer_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._arrayInitializer_ == oldChild)
        {
            setArrayInitializer((PArrayInitializer) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
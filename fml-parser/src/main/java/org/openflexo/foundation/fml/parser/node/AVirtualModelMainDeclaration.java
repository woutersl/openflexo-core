/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AVirtualModelMainDeclaration extends PMainDeclaration
{
    private PVirtualModelDeclaration _virtualModelDeclaration_;

    public AVirtualModelMainDeclaration()
    {
        // Constructor
    }

    public AVirtualModelMainDeclaration(
        @SuppressWarnings("hiding") PVirtualModelDeclaration _virtualModelDeclaration_)
    {
        // Constructor
        setVirtualModelDeclaration(_virtualModelDeclaration_);

    }

    @Override
    public Object clone()
    {
        return new AVirtualModelMainDeclaration(
            cloneNode(this._virtualModelDeclaration_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVirtualModelMainDeclaration(this);
    }

    public PVirtualModelDeclaration getVirtualModelDeclaration()
    {
        return this._virtualModelDeclaration_;
    }

    public void setVirtualModelDeclaration(PVirtualModelDeclaration node)
    {
        if(this._virtualModelDeclaration_ != null)
        {
            this._virtualModelDeclaration_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._virtualModelDeclaration_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._virtualModelDeclaration_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._virtualModelDeclaration_ == child)
        {
            this._virtualModelDeclaration_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._virtualModelDeclaration_ == oldChild)
        {
            setVirtualModelDeclaration((PVirtualModelDeclaration) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

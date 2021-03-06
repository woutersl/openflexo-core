/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AModelSlotMemberVirtualModelBodyDeclaration extends PVirtualModelBodyDeclaration
{
    private PModelSlotDeclaration _modelSlotDeclaration_;

    public AModelSlotMemberVirtualModelBodyDeclaration()
    {
        // Constructor
    }

    public AModelSlotMemberVirtualModelBodyDeclaration(
        @SuppressWarnings("hiding") PModelSlotDeclaration _modelSlotDeclaration_)
    {
        // Constructor
        setModelSlotDeclaration(_modelSlotDeclaration_);

    }

    @Override
    public Object clone()
    {
        return new AModelSlotMemberVirtualModelBodyDeclaration(
            cloneNode(this._modelSlotDeclaration_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAModelSlotMemberVirtualModelBodyDeclaration(this);
    }

    public PModelSlotDeclaration getModelSlotDeclaration()
    {
        return this._modelSlotDeclaration_;
    }

    public void setModelSlotDeclaration(PModelSlotDeclaration node)
    {
        if(this._modelSlotDeclaration_ != null)
        {
            this._modelSlotDeclaration_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._modelSlotDeclaration_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._modelSlotDeclaration_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._modelSlotDeclaration_ == child)
        {
            this._modelSlotDeclaration_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._modelSlotDeclaration_ == oldChild)
        {
            setModelSlotDeclaration((PModelSlotDeclaration) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

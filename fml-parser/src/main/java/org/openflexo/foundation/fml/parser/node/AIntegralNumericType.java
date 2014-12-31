/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AIntegralNumericType extends PNumericType
{
    private PIntegralType _integralType_;

    public AIntegralNumericType()
    {
        // Constructor
    }

    public AIntegralNumericType(
        @SuppressWarnings("hiding") PIntegralType _integralType_)
    {
        // Constructor
        setIntegralType(_integralType_);

    }

    @Override
    public Object clone()
    {
        return new AIntegralNumericType(
            cloneNode(this._integralType_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIntegralNumericType(this);
    }

    public PIntegralType getIntegralType()
    {
        return this._integralType_;
    }

    public void setIntegralType(PIntegralType node)
    {
        if(this._integralType_ != null)
        {
            this._integralType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._integralType_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._integralType_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._integralType_ == child)
        {
            this._integralType_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._integralType_ == oldChild)
        {
            setIntegralType((PIntegralType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

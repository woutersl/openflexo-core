/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class APrimitiveExpressionCastExpression extends PCastExpression
{
    private TLPar _lPar_;
    private PPrimitiveType _primitiveType_;
    private final LinkedList<PDim> _dims_ = new LinkedList<PDim>();
    private TRPar _rPar_;
    private PUnaryExpression _unaryExpression_;

    public APrimitiveExpressionCastExpression()
    {
        // Constructor
    }

    public APrimitiveExpressionCastExpression(
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PPrimitiveType _primitiveType_,
        @SuppressWarnings("hiding") List<PDim> _dims_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PUnaryExpression _unaryExpression_)
    {
        // Constructor
        setLPar(_lPar_);

        setPrimitiveType(_primitiveType_);

        setDims(_dims_);

        setRPar(_rPar_);

        setUnaryExpression(_unaryExpression_);

    }

    @Override
    public Object clone()
    {
        return new APrimitiveExpressionCastExpression(
            cloneNode(this._lPar_),
            cloneNode(this._primitiveType_),
            cloneList(this._dims_),
            cloneNode(this._rPar_),
            cloneNode(this._unaryExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrimitiveExpressionCastExpression(this);
    }

    public TLPar getLPar()
    {
        return this._lPar_;
    }

    public void setLPar(TLPar node)
    {
        if(this._lPar_ != null)
        {
            this._lPar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lPar_ = node;
    }

    public PPrimitiveType getPrimitiveType()
    {
        return this._primitiveType_;
    }

    public void setPrimitiveType(PPrimitiveType node)
    {
        if(this._primitiveType_ != null)
        {
            this._primitiveType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._primitiveType_ = node;
    }

    public LinkedList<PDim> getDims()
    {
        return this._dims_;
    }

    public void setDims(List<PDim> list)
    {
        this._dims_.clear();
        this._dims_.addAll(list);
        for(PDim e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TRPar getRPar()
    {
        return this._rPar_;
    }

    public void setRPar(TRPar node)
    {
        if(this._rPar_ != null)
        {
            this._rPar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._rPar_ = node;
    }

    public PUnaryExpression getUnaryExpression()
    {
        return this._unaryExpression_;
    }

    public void setUnaryExpression(PUnaryExpression node)
    {
        if(this._unaryExpression_ != null)
        {
            this._unaryExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._unaryExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lPar_)
            + toString(this._primitiveType_)
            + toString(this._dims_)
            + toString(this._rPar_)
            + toString(this._unaryExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._lPar_ == child)
        {
            this._lPar_ = null;
            return;
        }

        if(this._primitiveType_ == child)
        {
            this._primitiveType_ = null;
            return;
        }

        if(this._dims_.remove(child))
        {
            return;
        }

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        if(this._unaryExpression_ == child)
        {
            this._unaryExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if(this._primitiveType_ == oldChild)
        {
            setPrimitiveType((PPrimitiveType) newChild);
            return;
        }

        for(ListIterator<PDim> i = this._dims_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PDim) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        if(this._unaryExpression_ == oldChild)
        {
            setUnaryExpression((PUnaryExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

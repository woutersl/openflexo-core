/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AManyPrimitiveReferenceTypeList extends PReferenceTypeList
{
    private PReferenceTypeList _referenceTypeList_;
    private TComma _comma_;
    private PPrimitiveType _primitiveType_;
    private final LinkedList<PDim> _dims_ = new LinkedList<PDim>();

    public AManyPrimitiveReferenceTypeList()
    {
        // Constructor
    }

    public AManyPrimitiveReferenceTypeList(
        @SuppressWarnings("hiding") PReferenceTypeList _referenceTypeList_,
        @SuppressWarnings("hiding") TComma _comma_,
        @SuppressWarnings("hiding") PPrimitiveType _primitiveType_,
        @SuppressWarnings("hiding") List<PDim> _dims_)
    {
        // Constructor
        setReferenceTypeList(_referenceTypeList_);

        setComma(_comma_);

        setPrimitiveType(_primitiveType_);

        setDims(_dims_);

    }

    @Override
    public Object clone()
    {
        return new AManyPrimitiveReferenceTypeList(
            cloneNode(this._referenceTypeList_),
            cloneNode(this._comma_),
            cloneNode(this._primitiveType_),
            cloneList(this._dims_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAManyPrimitiveReferenceTypeList(this);
    }

    public PReferenceTypeList getReferenceTypeList()
    {
        return this._referenceTypeList_;
    }

    public void setReferenceTypeList(PReferenceTypeList node)
    {
        if(this._referenceTypeList_ != null)
        {
            this._referenceTypeList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._referenceTypeList_ = node;
    }

    public TComma getComma()
    {
        return this._comma_;
    }

    public void setComma(TComma node)
    {
        if(this._comma_ != null)
        {
            this._comma_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comma_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._referenceTypeList_)
            + toString(this._comma_)
            + toString(this._primitiveType_)
            + toString(this._dims_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._referenceTypeList_ == child)
        {
            this._referenceTypeList_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
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

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._referenceTypeList_ == oldChild)
        {
            setReferenceTypeList((PReferenceTypeList) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
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

        throw new RuntimeException("Not a child.");
    }
}

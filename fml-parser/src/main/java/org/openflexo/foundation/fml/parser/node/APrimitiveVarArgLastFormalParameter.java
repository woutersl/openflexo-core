/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class APrimitiveVarArgLastFormalParameter extends PLastFormalParameter
{
    private final LinkedList<PModifier> _modifiers_ = new LinkedList<PModifier>();
    private PPrimitiveType _primitiveType_;
    private final LinkedList<PDim> _dims1_ = new LinkedList<PDim>();
    private TDotDotDot _dotDotDot_;
    private TIdentifier _identifier_;
    private final LinkedList<PDim> _dims2_ = new LinkedList<PDim>();

    public APrimitiveVarArgLastFormalParameter()
    {
        // Constructor
    }

    public APrimitiveVarArgLastFormalParameter(
        @SuppressWarnings("hiding") List<PModifier> _modifiers_,
        @SuppressWarnings("hiding") PPrimitiveType _primitiveType_,
        @SuppressWarnings("hiding") List<PDim> _dims1_,
        @SuppressWarnings("hiding") TDotDotDot _dotDotDot_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PDim> _dims2_)
    {
        // Constructor
        setModifiers(_modifiers_);

        setPrimitiveType(_primitiveType_);

        setDims1(_dims1_);

        setDotDotDot(_dotDotDot_);

        setIdentifier(_identifier_);

        setDims2(_dims2_);

    }

    @Override
    public Object clone()
    {
        return new APrimitiveVarArgLastFormalParameter(
            cloneList(this._modifiers_),
            cloneNode(this._primitiveType_),
            cloneList(this._dims1_),
            cloneNode(this._dotDotDot_),
            cloneNode(this._identifier_),
            cloneList(this._dims2_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrimitiveVarArgLastFormalParameter(this);
    }

    public LinkedList<PModifier> getModifiers()
    {
        return this._modifiers_;
    }

    public void setModifiers(List<PModifier> list)
    {
        this._modifiers_.clear();
        this._modifiers_.addAll(list);
        for(PModifier e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
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

    public LinkedList<PDim> getDims1()
    {
        return this._dims1_;
    }

    public void setDims1(List<PDim> list)
    {
        this._dims1_.clear();
        this._dims1_.addAll(list);
        for(PDim e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TDotDotDot getDotDotDot()
    {
        return this._dotDotDot_;
    }

    public void setDotDotDot(TDotDotDot node)
    {
        if(this._dotDotDot_ != null)
        {
            this._dotDotDot_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dotDotDot_ = node;
    }

    public TIdentifier getIdentifier()
    {
        return this._identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this._identifier_ != null)
        {
            this._identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier_ = node;
    }

    public LinkedList<PDim> getDims2()
    {
        return this._dims2_;
    }

    public void setDims2(List<PDim> list)
    {
        this._dims2_.clear();
        this._dims2_.addAll(list);
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
            + toString(this._modifiers_)
            + toString(this._primitiveType_)
            + toString(this._dims1_)
            + toString(this._dotDotDot_)
            + toString(this._identifier_)
            + toString(this._dims2_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._modifiers_.remove(child))
        {
            return;
        }

        if(this._primitiveType_ == child)
        {
            this._primitiveType_ = null;
            return;
        }

        if(this._dims1_.remove(child))
        {
            return;
        }

        if(this._dotDotDot_ == child)
        {
            this._dotDotDot_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._dims2_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PModifier> i = this._modifiers_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PModifier) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._primitiveType_ == oldChild)
        {
            setPrimitiveType((PPrimitiveType) newChild);
            return;
        }

        for(ListIterator<PDim> i = this._dims1_.listIterator(); i.hasNext();)
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

        if(this._dotDotDot_ == oldChild)
        {
            setDotDotDot((TDotDotDot) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        for(ListIterator<PDim> i = this._dims2_.listIterator(); i.hasNext();)
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
/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class APrimitiveLocalVariableDeclaration extends PLocalVariableDeclaration
{
    private final LinkedList<PModifier> _modifiers_ = new LinkedList<PModifier>();
    private PPrimitiveType _primitiveType_;
    private final LinkedList<PDim> _dims_ = new LinkedList<PDim>();
    private PVariableDeclarators _variableDeclarators_;

    public APrimitiveLocalVariableDeclaration()
    {
        // Constructor
    }

    public APrimitiveLocalVariableDeclaration(
        @SuppressWarnings("hiding") List<PModifier> _modifiers_,
        @SuppressWarnings("hiding") PPrimitiveType _primitiveType_,
        @SuppressWarnings("hiding") List<PDim> _dims_,
        @SuppressWarnings("hiding") PVariableDeclarators _variableDeclarators_)
    {
        // Constructor
        setModifiers(_modifiers_);

        setPrimitiveType(_primitiveType_);

        setDims(_dims_);

        setVariableDeclarators(_variableDeclarators_);

    }

    @Override
    public Object clone()
    {
        return new APrimitiveLocalVariableDeclaration(
            cloneList(this._modifiers_),
            cloneNode(this._primitiveType_),
            cloneList(this._dims_),
            cloneNode(this._variableDeclarators_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrimitiveLocalVariableDeclaration(this);
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

    public PVariableDeclarators getVariableDeclarators()
    {
        return this._variableDeclarators_;
    }

    public void setVariableDeclarators(PVariableDeclarators node)
    {
        if(this._variableDeclarators_ != null)
        {
            this._variableDeclarators_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._variableDeclarators_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._modifiers_)
            + toString(this._primitiveType_)
            + toString(this._dims_)
            + toString(this._variableDeclarators_);
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

        if(this._dims_.remove(child))
        {
            return;
        }

        if(this._variableDeclarators_ == child)
        {
            this._variableDeclarators_ = null;
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

        if(this._variableDeclarators_ == oldChild)
        {
            setVariableDeclarators((PVariableDeclarators) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
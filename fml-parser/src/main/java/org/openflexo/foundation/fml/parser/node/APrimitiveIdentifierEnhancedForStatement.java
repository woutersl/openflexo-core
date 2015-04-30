/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class APrimitiveIdentifierEnhancedForStatement extends PEnhancedForStatement
{
    private TFor _for_;
    private TLPar _lPar_;
    private final LinkedList<PModifier> _modifiers_ = new LinkedList<PModifier>();
    private PPrimitiveType _primitiveType_;
    private final LinkedList<PDim> _dims_ = new LinkedList<PDim>();
    private TIdentifier _identifier1_;
    private TColon _colon_;
    private TIdentifier _identifier2_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();
    private TRPar _rPar_;
    private PStatement _statement_;

    public APrimitiveIdentifierEnhancedForStatement()
    {
        // Constructor
    }

    public APrimitiveIdentifierEnhancedForStatement(
        @SuppressWarnings("hiding") TFor _for_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") List<PModifier> _modifiers_,
        @SuppressWarnings("hiding") PPrimitiveType _primitiveType_,
        @SuppressWarnings("hiding") List<PDim> _dims_,
        @SuppressWarnings("hiding") TIdentifier _identifier1_,
        @SuppressWarnings("hiding") TColon _colon_,
        @SuppressWarnings("hiding") TIdentifier _identifier2_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PStatement _statement_)
    {
        // Constructor
        setFor(_for_);

        setLPar(_lPar_);

        setModifiers(_modifiers_);

        setPrimitiveType(_primitiveType_);

        setDims(_dims_);

        setIdentifier1(_identifier1_);

        setColon(_colon_);

        setIdentifier2(_identifier2_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

        setRPar(_rPar_);

        setStatement(_statement_);

    }

    @Override
    public Object clone()
    {
        return new APrimitiveIdentifierEnhancedForStatement(
            cloneNode(this._for_),
            cloneNode(this._lPar_),
            cloneList(this._modifiers_),
            cloneNode(this._primitiveType_),
            cloneList(this._dims_),
            cloneNode(this._identifier1_),
            cloneNode(this._colon_),
            cloneNode(this._identifier2_),
            cloneList(this._additionalIdentifiers_),
            cloneNode(this._rPar_),
            cloneNode(this._statement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPrimitiveIdentifierEnhancedForStatement(this);
    }

    public TFor getFor()
    {
        return this._for_;
    }

    public void setFor(TFor node)
    {
        if(this._for_ != null)
        {
            this._for_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._for_ = node;
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

    public TIdentifier getIdentifier1()
    {
        return this._identifier1_;
    }

    public void setIdentifier1(TIdentifier node)
    {
        if(this._identifier1_ != null)
        {
            this._identifier1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier1_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public TIdentifier getIdentifier2()
    {
        return this._identifier2_;
    }

    public void setIdentifier2(TIdentifier node)
    {
        if(this._identifier2_ != null)
        {
            this._identifier2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier2_ = node;
    }

    public LinkedList<PAdditionalIdentifier> getAdditionalIdentifiers()
    {
        return this._additionalIdentifiers_;
    }

    public void setAdditionalIdentifiers(List<PAdditionalIdentifier> list)
    {
        this._additionalIdentifiers_.clear();
        this._additionalIdentifiers_.addAll(list);
        for(PAdditionalIdentifier e : list)
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

    public PStatement getStatement()
    {
        return this._statement_;
    }

    public void setStatement(PStatement node)
    {
        if(this._statement_ != null)
        {
            this._statement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._for_)
            + toString(this._lPar_)
            + toString(this._modifiers_)
            + toString(this._primitiveType_)
            + toString(this._dims_)
            + toString(this._identifier1_)
            + toString(this._colon_)
            + toString(this._identifier2_)
            + toString(this._additionalIdentifiers_)
            + toString(this._rPar_)
            + toString(this._statement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._for_ == child)
        {
            this._for_ = null;
            return;
        }

        if(this._lPar_ == child)
        {
            this._lPar_ = null;
            return;
        }

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

        if(this._identifier1_ == child)
        {
            this._identifier1_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._identifier2_ == child)
        {
            this._identifier2_ = null;
            return;
        }

        if(this._additionalIdentifiers_.remove(child))
        {
            return;
        }

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        if(this._statement_ == child)
        {
            this._statement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._for_ == oldChild)
        {
            setFor((TFor) newChild);
            return;
        }

        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

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

        if(this._identifier1_ == oldChild)
        {
            setIdentifier1((TIdentifier) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._identifier2_ == oldChild)
        {
            setIdentifier2((TIdentifier) newChild);
            return;
        }

        for(ListIterator<PAdditionalIdentifier> i = this._additionalIdentifiers_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAdditionalIdentifier) newChild);
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

        if(this._statement_ == oldChild)
        {
            setStatement((PStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

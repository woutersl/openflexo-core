/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ABoundTypeBoundUshr extends PTypeBoundUshr
{
    private TExtends _extends_;
    private TIdentifier _identifier_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();
    private final LinkedList<PTypeComponent> _typeComponents_ = new LinkedList<PTypeComponent>();
    private PTypeArguments _typeArguments_;
    private final LinkedList<PAdditionalBound> _additionalBounds_ = new LinkedList<PAdditionalBound>();
    private PAdditionalBoundUshrNoGtGt _additionalBoundUshrNoGtGt_;

    public ABoundTypeBoundUshr()
    {
        // Constructor
    }

    public ABoundTypeBoundUshr(
        @SuppressWarnings("hiding") TExtends _extends_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_,
        @SuppressWarnings("hiding") List<PTypeComponent> _typeComponents_,
        @SuppressWarnings("hiding") PTypeArguments _typeArguments_,
        @SuppressWarnings("hiding") List<PAdditionalBound> _additionalBounds_,
        @SuppressWarnings("hiding") PAdditionalBoundUshrNoGtGt _additionalBoundUshrNoGtGt_)
    {
        // Constructor
        setExtends(_extends_);

        setIdentifier(_identifier_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

        setTypeComponents(_typeComponents_);

        setTypeArguments(_typeArguments_);

        setAdditionalBounds(_additionalBounds_);

        setAdditionalBoundUshrNoGtGt(_additionalBoundUshrNoGtGt_);

    }

    @Override
    public Object clone()
    {
        return new ABoundTypeBoundUshr(
            cloneNode(this._extends_),
            cloneNode(this._identifier_),
            cloneList(this._additionalIdentifiers_),
            cloneList(this._typeComponents_),
            cloneNode(this._typeArguments_),
            cloneList(this._additionalBounds_),
            cloneNode(this._additionalBoundUshrNoGtGt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABoundTypeBoundUshr(this);
    }

    public TExtends getExtends()
    {
        return this._extends_;
    }

    public void setExtends(TExtends node)
    {
        if(this._extends_ != null)
        {
            this._extends_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._extends_ = node;
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

    public LinkedList<PTypeComponent> getTypeComponents()
    {
        return this._typeComponents_;
    }

    public void setTypeComponents(List<PTypeComponent> list)
    {
        this._typeComponents_.clear();
        this._typeComponents_.addAll(list);
        for(PTypeComponent e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public PTypeArguments getTypeArguments()
    {
        return this._typeArguments_;
    }

    public void setTypeArguments(PTypeArguments node)
    {
        if(this._typeArguments_ != null)
        {
            this._typeArguments_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeArguments_ = node;
    }

    public LinkedList<PAdditionalBound> getAdditionalBounds()
    {
        return this._additionalBounds_;
    }

    public void setAdditionalBounds(List<PAdditionalBound> list)
    {
        this._additionalBounds_.clear();
        this._additionalBounds_.addAll(list);
        for(PAdditionalBound e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public PAdditionalBoundUshrNoGtGt getAdditionalBoundUshrNoGtGt()
    {
        return this._additionalBoundUshrNoGtGt_;
    }

    public void setAdditionalBoundUshrNoGtGt(PAdditionalBoundUshrNoGtGt node)
    {
        if(this._additionalBoundUshrNoGtGt_ != null)
        {
            this._additionalBoundUshrNoGtGt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._additionalBoundUshrNoGtGt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._extends_)
            + toString(this._identifier_)
            + toString(this._additionalIdentifiers_)
            + toString(this._typeComponents_)
            + toString(this._typeArguments_)
            + toString(this._additionalBounds_)
            + toString(this._additionalBoundUshrNoGtGt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._extends_ == child)
        {
            this._extends_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._additionalIdentifiers_.remove(child))
        {
            return;
        }

        if(this._typeComponents_.remove(child))
        {
            return;
        }

        if(this._typeArguments_ == child)
        {
            this._typeArguments_ = null;
            return;
        }

        if(this._additionalBounds_.remove(child))
        {
            return;
        }

        if(this._additionalBoundUshrNoGtGt_ == child)
        {
            this._additionalBoundUshrNoGtGt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._extends_ == oldChild)
        {
            setExtends((TExtends) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
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

        for(ListIterator<PTypeComponent> i = this._typeComponents_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PTypeComponent) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._typeArguments_ == oldChild)
        {
            setTypeArguments((PTypeArguments) newChild);
            return;
        }

        for(ListIterator<PAdditionalBound> i = this._additionalBounds_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAdditionalBound) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._additionalBoundUshrNoGtGt_ == oldChild)
        {
            setAdditionalBoundUshrNoGtGt((PAdditionalBoundUshrNoGtGt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

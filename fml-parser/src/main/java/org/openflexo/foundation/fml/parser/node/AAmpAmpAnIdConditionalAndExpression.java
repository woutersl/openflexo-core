/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAmpAmpAnIdConditionalAndExpression extends PConditionalAndExpression
{
    private PConditionalAndExpression _conditionalAndExpression_;
    private TAmpAmp _ampAmp_;
    private TIdentifier _identifier_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();

    public AAmpAmpAnIdConditionalAndExpression()
    {
        // Constructor
    }

    public AAmpAmpAnIdConditionalAndExpression(
        @SuppressWarnings("hiding") PConditionalAndExpression _conditionalAndExpression_,
        @SuppressWarnings("hiding") TAmpAmp _ampAmp_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_)
    {
        // Constructor
        setConditionalAndExpression(_conditionalAndExpression_);

        setAmpAmp(_ampAmp_);

        setIdentifier(_identifier_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

    }

    @Override
    public Object clone()
    {
        return new AAmpAmpAnIdConditionalAndExpression(
            cloneNode(this._conditionalAndExpression_),
            cloneNode(this._ampAmp_),
            cloneNode(this._identifier_),
            cloneList(this._additionalIdentifiers_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAmpAmpAnIdConditionalAndExpression(this);
    }

    public PConditionalAndExpression getConditionalAndExpression()
    {
        return this._conditionalAndExpression_;
    }

    public void setConditionalAndExpression(PConditionalAndExpression node)
    {
        if(this._conditionalAndExpression_ != null)
        {
            this._conditionalAndExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._conditionalAndExpression_ = node;
    }

    public TAmpAmp getAmpAmp()
    {
        return this._ampAmp_;
    }

    public void setAmpAmp(TAmpAmp node)
    {
        if(this._ampAmp_ != null)
        {
            this._ampAmp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ampAmp_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._conditionalAndExpression_)
            + toString(this._ampAmp_)
            + toString(this._identifier_)
            + toString(this._additionalIdentifiers_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._conditionalAndExpression_ == child)
        {
            this._conditionalAndExpression_ = null;
            return;
        }

        if(this._ampAmp_ == child)
        {
            this._ampAmp_ = null;
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

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._conditionalAndExpression_ == oldChild)
        {
            setConditionalAndExpression((PConditionalAndExpression) newChild);
            return;
        }

        if(this._ampAmp_ == oldChild)
        {
            setAmpAmp((TAmpAmp) newChild);
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

        throw new RuntimeException("Not a child.");
    }
}

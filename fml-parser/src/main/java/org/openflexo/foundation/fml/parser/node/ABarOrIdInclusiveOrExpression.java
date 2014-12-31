/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class ABarOrIdInclusiveOrExpression extends PInclusiveOrExpression
{
    private PInclusiveOrExpression _inclusiveOrExpression_;
    private TBar _bar_;
    private TIdentifier _identifier_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();

    public ABarOrIdInclusiveOrExpression()
    {
        // Constructor
    }

    public ABarOrIdInclusiveOrExpression(
        @SuppressWarnings("hiding") PInclusiveOrExpression _inclusiveOrExpression_,
        @SuppressWarnings("hiding") TBar _bar_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_)
    {
        // Constructor
        setInclusiveOrExpression(_inclusiveOrExpression_);

        setBar(_bar_);

        setIdentifier(_identifier_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

    }

    @Override
    public Object clone()
    {
        return new ABarOrIdInclusiveOrExpression(
            cloneNode(this._inclusiveOrExpression_),
            cloneNode(this._bar_),
            cloneNode(this._identifier_),
            cloneList(this._additionalIdentifiers_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABarOrIdInclusiveOrExpression(this);
    }

    public PInclusiveOrExpression getInclusiveOrExpression()
    {
        return this._inclusiveOrExpression_;
    }

    public void setInclusiveOrExpression(PInclusiveOrExpression node)
    {
        if(this._inclusiveOrExpression_ != null)
        {
            this._inclusiveOrExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._inclusiveOrExpression_ = node;
    }

    public TBar getBar()
    {
        return this._bar_;
    }

    public void setBar(TBar node)
    {
        if(this._bar_ != null)
        {
            this._bar_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._bar_ = node;
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
            + toString(this._inclusiveOrExpression_)
            + toString(this._bar_)
            + toString(this._identifier_)
            + toString(this._additionalIdentifiers_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._inclusiveOrExpression_ == child)
        {
            this._inclusiveOrExpression_ = null;
            return;
        }

        if(this._bar_ == child)
        {
            this._bar_ = null;
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
        if(this._inclusiveOrExpression_ == oldChild)
        {
            setInclusiveOrExpression((PInclusiveOrExpression) newChild);
            return;
        }

        if(this._bar_ == oldChild)
        {
            setBar((TBar) newChild);
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

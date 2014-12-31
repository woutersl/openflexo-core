/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AIdentifierWhileStatementNoShortIf extends PWhileStatementNoShortIf
{
    private TWhile _while_;
    private TLPar _lPar_;
    private TIdentifier _identifier_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();
    private TRPar _rPar_;
    private PStatementNoShortIf _statementNoShortIf_;

    public AIdentifierWhileStatementNoShortIf()
    {
        // Constructor
    }

    public AIdentifierWhileStatementNoShortIf(
        @SuppressWarnings("hiding") TWhile _while_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PStatementNoShortIf _statementNoShortIf_)
    {
        // Constructor
        setWhile(_while_);

        setLPar(_lPar_);

        setIdentifier(_identifier_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

        setRPar(_rPar_);

        setStatementNoShortIf(_statementNoShortIf_);

    }

    @Override
    public Object clone()
    {
        return new AIdentifierWhileStatementNoShortIf(
            cloneNode(this._while_),
            cloneNode(this._lPar_),
            cloneNode(this._identifier_),
            cloneList(this._additionalIdentifiers_),
            cloneNode(this._rPar_),
            cloneNode(this._statementNoShortIf_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIdentifierWhileStatementNoShortIf(this);
    }

    public TWhile getWhile()
    {
        return this._while_;
    }

    public void setWhile(TWhile node)
    {
        if(this._while_ != null)
        {
            this._while_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._while_ = node;
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

    public PStatementNoShortIf getStatementNoShortIf()
    {
        return this._statementNoShortIf_;
    }

    public void setStatementNoShortIf(PStatementNoShortIf node)
    {
        if(this._statementNoShortIf_ != null)
        {
            this._statementNoShortIf_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statementNoShortIf_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._while_)
            + toString(this._lPar_)
            + toString(this._identifier_)
            + toString(this._additionalIdentifiers_)
            + toString(this._rPar_)
            + toString(this._statementNoShortIf_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._while_ == child)
        {
            this._while_ = null;
            return;
        }

        if(this._lPar_ == child)
        {
            this._lPar_ = null;
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

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        if(this._statementNoShortIf_ == child)
        {
            this._statementNoShortIf_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._while_ == oldChild)
        {
            setWhile((TWhile) newChild);
            return;
        }

        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
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

        if(this._rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        if(this._statementNoShortIf_ == oldChild)
        {
            setStatementNoShortIf((PStatementNoShortIf) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

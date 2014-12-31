/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AIdentifierIfThenElseStatementNoShortIf extends PIfThenElseStatementNoShortIf
{
    private TIf _if_;
    private TLPar _lPar_;
    private TIdentifier _identifier_;
    private final LinkedList<PAdditionalIdentifier> _additionalIdentifiers_ = new LinkedList<PAdditionalIdentifier>();
    private TRPar _rPar_;
    private PStatementNoShortIf _statementNoShortIf1_;
    private TElse _else_;
    private PStatementNoShortIf _statementNoShortIf2_;

    public AIdentifierIfThenElseStatementNoShortIf()
    {
        // Constructor
    }

    public AIdentifierIfThenElseStatementNoShortIf(
        @SuppressWarnings("hiding") TIf _if_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") List<PAdditionalIdentifier> _additionalIdentifiers_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PStatementNoShortIf _statementNoShortIf1_,
        @SuppressWarnings("hiding") TElse _else_,
        @SuppressWarnings("hiding") PStatementNoShortIf _statementNoShortIf2_)
    {
        // Constructor
        setIf(_if_);

        setLPar(_lPar_);

        setIdentifier(_identifier_);

        setAdditionalIdentifiers(_additionalIdentifiers_);

        setRPar(_rPar_);

        setStatementNoShortIf1(_statementNoShortIf1_);

        setElse(_else_);

        setStatementNoShortIf2(_statementNoShortIf2_);

    }

    @Override
    public Object clone()
    {
        return new AIdentifierIfThenElseStatementNoShortIf(
            cloneNode(this._if_),
            cloneNode(this._lPar_),
            cloneNode(this._identifier_),
            cloneList(this._additionalIdentifiers_),
            cloneNode(this._rPar_),
            cloneNode(this._statementNoShortIf1_),
            cloneNode(this._else_),
            cloneNode(this._statementNoShortIf2_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIdentifierIfThenElseStatementNoShortIf(this);
    }

    public TIf getIf()
    {
        return this._if_;
    }

    public void setIf(TIf node)
    {
        if(this._if_ != null)
        {
            this._if_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._if_ = node;
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

    public PStatementNoShortIf getStatementNoShortIf1()
    {
        return this._statementNoShortIf1_;
    }

    public void setStatementNoShortIf1(PStatementNoShortIf node)
    {
        if(this._statementNoShortIf1_ != null)
        {
            this._statementNoShortIf1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statementNoShortIf1_ = node;
    }

    public TElse getElse()
    {
        return this._else_;
    }

    public void setElse(TElse node)
    {
        if(this._else_ != null)
        {
            this._else_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._else_ = node;
    }

    public PStatementNoShortIf getStatementNoShortIf2()
    {
        return this._statementNoShortIf2_;
    }

    public void setStatementNoShortIf2(PStatementNoShortIf node)
    {
        if(this._statementNoShortIf2_ != null)
        {
            this._statementNoShortIf2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statementNoShortIf2_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._if_)
            + toString(this._lPar_)
            + toString(this._identifier_)
            + toString(this._additionalIdentifiers_)
            + toString(this._rPar_)
            + toString(this._statementNoShortIf1_)
            + toString(this._else_)
            + toString(this._statementNoShortIf2_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._if_ == child)
        {
            this._if_ = null;
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

        if(this._statementNoShortIf1_ == child)
        {
            this._statementNoShortIf1_ = null;
            return;
        }

        if(this._else_ == child)
        {
            this._else_ = null;
            return;
        }

        if(this._statementNoShortIf2_ == child)
        {
            this._statementNoShortIf2_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._if_ == oldChild)
        {
            setIf((TIf) newChild);
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

        if(this._statementNoShortIf1_ == oldChild)
        {
            setStatementNoShortIf1((PStatementNoShortIf) newChild);
            return;
        }

        if(this._else_ == oldChild)
        {
            setElse((TElse) newChild);
            return;
        }

        if(this._statementNoShortIf2_ == oldChild)
        {
            setStatementNoShortIf2((PStatementNoShortIf) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

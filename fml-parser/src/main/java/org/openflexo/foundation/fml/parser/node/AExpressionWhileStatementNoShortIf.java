/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AExpressionWhileStatementNoShortIf extends PWhileStatementNoShortIf
{
    private TWhile _while_;
    private TLPar _lPar_;
    private PExpression _expression_;
    private TRPar _rPar_;
    private PStatementNoShortIf _statementNoShortIf_;

    public AExpressionWhileStatementNoShortIf()
    {
        // Constructor
    }

    public AExpressionWhileStatementNoShortIf(
        @SuppressWarnings("hiding") TWhile _while_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") PStatementNoShortIf _statementNoShortIf_)
    {
        // Constructor
        setWhile(_while_);

        setLPar(_lPar_);

        setExpression(_expression_);

        setRPar(_rPar_);

        setStatementNoShortIf(_statementNoShortIf_);

    }

    @Override
    public Object clone()
    {
        return new AExpressionWhileStatementNoShortIf(
            cloneNode(this._while_),
            cloneNode(this._lPar_),
            cloneNode(this._expression_),
            cloneNode(this._rPar_),
            cloneNode(this._statementNoShortIf_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExpressionWhileStatementNoShortIf(this);
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

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
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
            + toString(this._expression_)
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

        if(this._expression_ == child)
        {
            this._expression_ = null;
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

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
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

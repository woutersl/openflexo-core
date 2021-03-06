/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AExpressionAssignment extends PAssignment
{
    private PLeftHandSide _leftHandSide_;
    private PAssignmentOperator _assignmentOperator_;
    private PAssignmentExpression _assignmentExpression_;

    public AExpressionAssignment()
    {
        // Constructor
    }

    public AExpressionAssignment(
        @SuppressWarnings("hiding") PLeftHandSide _leftHandSide_,
        @SuppressWarnings("hiding") PAssignmentOperator _assignmentOperator_,
        @SuppressWarnings("hiding") PAssignmentExpression _assignmentExpression_)
    {
        // Constructor
        setLeftHandSide(_leftHandSide_);

        setAssignmentOperator(_assignmentOperator_);

        setAssignmentExpression(_assignmentExpression_);

    }

    @Override
    public Object clone()
    {
        return new AExpressionAssignment(
            cloneNode(this._leftHandSide_),
            cloneNode(this._assignmentOperator_),
            cloneNode(this._assignmentExpression_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAExpressionAssignment(this);
    }

    public PLeftHandSide getLeftHandSide()
    {
        return this._leftHandSide_;
    }

    public void setLeftHandSide(PLeftHandSide node)
    {
        if(this._leftHandSide_ != null)
        {
            this._leftHandSide_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._leftHandSide_ = node;
    }

    public PAssignmentOperator getAssignmentOperator()
    {
        return this._assignmentOperator_;
    }

    public void setAssignmentOperator(PAssignmentOperator node)
    {
        if(this._assignmentOperator_ != null)
        {
            this._assignmentOperator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assignmentOperator_ = node;
    }

    public PAssignmentExpression getAssignmentExpression()
    {
        return this._assignmentExpression_;
    }

    public void setAssignmentExpression(PAssignmentExpression node)
    {
        if(this._assignmentExpression_ != null)
        {
            this._assignmentExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._assignmentExpression_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._leftHandSide_)
            + toString(this._assignmentOperator_)
            + toString(this._assignmentExpression_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._leftHandSide_ == child)
        {
            this._leftHandSide_ = null;
            return;
        }

        if(this._assignmentOperator_ == child)
        {
            this._assignmentOperator_ = null;
            return;
        }

        if(this._assignmentExpression_ == child)
        {
            this._assignmentExpression_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._leftHandSide_ == oldChild)
        {
            setLeftHandSide((PLeftHandSide) newChild);
            return;
        }

        if(this._assignmentOperator_ == oldChild)
        {
            setAssignmentOperator((PAssignmentOperator) newChild);
            return;
        }

        if(this._assignmentExpression_ == oldChild)
        {
            setAssignmentExpression((PAssignmentExpression) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

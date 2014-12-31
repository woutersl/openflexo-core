/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AWildcardActualTypeArgumentUshrNoGt extends PActualTypeArgumentUshrNoGt
{
    private PWildcardUshrNoGt _wildcardUshrNoGt_;

    public AWildcardActualTypeArgumentUshrNoGt()
    {
        // Constructor
    }

    public AWildcardActualTypeArgumentUshrNoGt(
        @SuppressWarnings("hiding") PWildcardUshrNoGt _wildcardUshrNoGt_)
    {
        // Constructor
        setWildcardUshrNoGt(_wildcardUshrNoGt_);

    }

    @Override
    public Object clone()
    {
        return new AWildcardActualTypeArgumentUshrNoGt(
            cloneNode(this._wildcardUshrNoGt_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAWildcardActualTypeArgumentUshrNoGt(this);
    }

    public PWildcardUshrNoGt getWildcardUshrNoGt()
    {
        return this._wildcardUshrNoGt_;
    }

    public void setWildcardUshrNoGt(PWildcardUshrNoGt node)
    {
        if(this._wildcardUshrNoGt_ != null)
        {
            this._wildcardUshrNoGt_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._wildcardUshrNoGt_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._wildcardUshrNoGt_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._wildcardUshrNoGt_ == child)
        {
            this._wildcardUshrNoGt_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._wildcardUshrNoGt_ == oldChild)
        {
            setWildcardUshrNoGt((PWildcardUshrNoGt) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

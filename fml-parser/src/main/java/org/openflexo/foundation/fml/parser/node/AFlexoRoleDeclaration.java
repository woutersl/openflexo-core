/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import java.util.*;
import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AFlexoRoleDeclaration extends PFlexoRoleDeclaration
{
    private final LinkedList<PAnnotation> _annotations_ = new LinkedList<PAnnotation>();
    private TRole _role_;
    private TIdentifier _identifier_;
    private TAs _as_;
    private TTaIdentifier _taIdentifier_;
    private TLPar _lPar_;
    private PFmlValuePairs _fmlValuePairs_;
    private TRPar _rPar_;
    private TSemi _semi_;

    public AFlexoRoleDeclaration()
    {
        // Constructor
    }

    public AFlexoRoleDeclaration(
        @SuppressWarnings("hiding") List<PAnnotation> _annotations_,
        @SuppressWarnings("hiding") TRole _role_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") TAs _as_,
        @SuppressWarnings("hiding") TTaIdentifier _taIdentifier_,
        @SuppressWarnings("hiding") TLPar _lPar_,
        @SuppressWarnings("hiding") PFmlValuePairs _fmlValuePairs_,
        @SuppressWarnings("hiding") TRPar _rPar_,
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setAnnotations(_annotations_);

        setRole(_role_);

        setIdentifier(_identifier_);

        setAs(_as_);

        setTaIdentifier(_taIdentifier_);

        setLPar(_lPar_);

        setFmlValuePairs(_fmlValuePairs_);

        setRPar(_rPar_);

        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AFlexoRoleDeclaration(
            cloneList(this._annotations_),
            cloneNode(this._role_),
            cloneNode(this._identifier_),
            cloneNode(this._as_),
            cloneNode(this._taIdentifier_),
            cloneNode(this._lPar_),
            cloneNode(this._fmlValuePairs_),
            cloneNode(this._rPar_),
            cloneNode(this._semi_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFlexoRoleDeclaration(this);
    }

    public LinkedList<PAnnotation> getAnnotations()
    {
        return this._annotations_;
    }

    public void setAnnotations(List<PAnnotation> list)
    {
        this._annotations_.clear();
        this._annotations_.addAll(list);
        for(PAnnotation e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    public TRole getRole()
    {
        return this._role_;
    }

    public void setRole(TRole node)
    {
        if(this._role_ != null)
        {
            this._role_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._role_ = node;
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

    public TAs getAs()
    {
        return this._as_;
    }

    public void setAs(TAs node)
    {
        if(this._as_ != null)
        {
            this._as_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._as_ = node;
    }

    public TTaIdentifier getTaIdentifier()
    {
        return this._taIdentifier_;
    }

    public void setTaIdentifier(TTaIdentifier node)
    {
        if(this._taIdentifier_ != null)
        {
            this._taIdentifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._taIdentifier_ = node;
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

    public PFmlValuePairs getFmlValuePairs()
    {
        return this._fmlValuePairs_;
    }

    public void setFmlValuePairs(PFmlValuePairs node)
    {
        if(this._fmlValuePairs_ != null)
        {
            this._fmlValuePairs_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fmlValuePairs_ = node;
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

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._annotations_)
            + toString(this._role_)
            + toString(this._identifier_)
            + toString(this._as_)
            + toString(this._taIdentifier_)
            + toString(this._lPar_)
            + toString(this._fmlValuePairs_)
            + toString(this._rPar_)
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._annotations_.remove(child))
        {
            return;
        }

        if(this._role_ == child)
        {
            this._role_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._as_ == child)
        {
            this._as_ = null;
            return;
        }

        if(this._taIdentifier_ == child)
        {
            this._taIdentifier_ = null;
            return;
        }

        if(this._lPar_ == child)
        {
            this._lPar_ = null;
            return;
        }

        if(this._fmlValuePairs_ == child)
        {
            this._fmlValuePairs_ = null;
            return;
        }

        if(this._rPar_ == child)
        {
            this._rPar_ = null;
            return;
        }

        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        for(ListIterator<PAnnotation> i = this._annotations_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PAnnotation) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._role_ == oldChild)
        {
            setRole((TRole) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        if(this._as_ == oldChild)
        {
            setAs((TAs) newChild);
            return;
        }

        if(this._taIdentifier_ == oldChild)
        {
            setTaIdentifier((TTaIdentifier) newChild);
            return;
        }

        if(this._lPar_ == oldChild)
        {
            setLPar((TLPar) newChild);
            return;
        }

        if(this._fmlValuePairs_ == oldChild)
        {
            setFmlValuePairs((PFmlValuePairs) newChild);
            return;
        }

        if(this._rPar_ == oldChild)
        {
            setRPar((TRPar) newChild);
            return;
        }

        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}

/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class AImportDeclaration extends PImportDeclaration
{
    private TImport _import_;
    private TUrl _url_;
    private TAs _as_;
    private TIdentifier _identifier_;
    private TSemi _semi_;

    public AImportDeclaration()
    {
        // Constructor
    }

    public AImportDeclaration(
        @SuppressWarnings("hiding") TImport _import_,
        @SuppressWarnings("hiding") TUrl _url_,
        @SuppressWarnings("hiding") TAs _as_,
        @SuppressWarnings("hiding") TIdentifier _identifier_,
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setImport(_import_);

        setUrl(_url_);

        setAs(_as_);

        setIdentifier(_identifier_);

        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AImportDeclaration(
            cloneNode(this._import_),
            cloneNode(this._url_),
            cloneNode(this._as_),
            cloneNode(this._identifier_),
            cloneNode(this._semi_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAImportDeclaration(this);
    }

    public TImport getImport()
    {
        return this._import_;
    }

    public void setImport(TImport node)
    {
        if(this._import_ != null)
        {
            this._import_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._import_ = node;
    }

    public TUrl getUrl()
    {
        return this._url_;
    }

    public void setUrl(TUrl node)
    {
        if(this._url_ != null)
        {
            this._url_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._url_ = node;
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
            + toString(this._import_)
            + toString(this._url_)
            + toString(this._as_)
            + toString(this._identifier_)
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._import_ == child)
        {
            this._import_ = null;
            return;
        }

        if(this._url_ == child)
        {
            this._url_ = null;
            return;
        }

        if(this._as_ == child)
        {
            this._as_ = null;
            return;
        }

        if(this._identifier_ == child)
        {
            this._identifier_ = null;
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
        if(this._import_ == oldChild)
        {
            setImport((TImport) newChild);
            return;
        }

        if(this._url_ == oldChild)
        {
            setUrl((TUrl) newChild);
            return;
        }

        if(this._as_ == oldChild)
        {
            setAs((TAs) newChild);
            return;
        }

        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
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

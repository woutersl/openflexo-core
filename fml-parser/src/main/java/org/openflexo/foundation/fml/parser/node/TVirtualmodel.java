/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TVirtualmodel extends Token
{
    public TVirtualmodel()
    {
        super.setText("VirtualModel");
    }

    public TVirtualmodel(int line, int pos)
    {
        super.setText("VirtualModel");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TVirtualmodel(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTVirtualmodel(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TVirtualmodel text.");
    }
}

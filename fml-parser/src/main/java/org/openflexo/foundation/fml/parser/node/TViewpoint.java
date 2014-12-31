/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TViewpoint extends Token
{
    public TViewpoint()
    {
        super.setText("ViewPoint");
    }

    public TViewpoint(int line, int pos)
    {
        super.setText("ViewPoint");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TViewpoint(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTViewpoint(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TViewpoint text.");
    }
}

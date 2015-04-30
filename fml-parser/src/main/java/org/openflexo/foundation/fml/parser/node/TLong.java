/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TLong extends Token
{
    public TLong()
    {
        super.setText("long");
    }

    public TLong(int line, int pos)
    {
        super.setText("long");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TLong(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTLong(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TLong text.");
    }
}

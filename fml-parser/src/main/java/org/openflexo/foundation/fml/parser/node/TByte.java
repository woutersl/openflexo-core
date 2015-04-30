/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TByte extends Token
{
    public TByte()
    {
        super.setText("byte");
    }

    public TByte(int line, int pos)
    {
        super.setText("byte");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TByte(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTByte(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TByte text.");
    }
}

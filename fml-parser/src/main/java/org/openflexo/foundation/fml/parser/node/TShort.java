/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TShort extends Token
{
    public TShort()
    {
        super.setText("short");
    }

    public TShort(int line, int pos)
    {
        super.setText("short");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TShort(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTShort(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TShort text.");
    }
}
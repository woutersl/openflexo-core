/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TMinusAssign extends Token
{
    public TMinusAssign()
    {
        super.setText("-=");
    }

    public TMinusAssign(int line, int pos)
    {
        super.setText("-=");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TMinusAssign(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMinusAssign(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TMinusAssign text.");
    }
}

/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TConst extends Token
{
    public TConst()
    {
        super.setText("const");
    }

    public TConst(int line, int pos)
    {
        super.setText("const");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TConst(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTConst(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TConst text.");
    }
}

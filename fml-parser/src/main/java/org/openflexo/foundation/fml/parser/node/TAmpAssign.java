/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TAmpAssign extends Token
{
    public TAmpAssign()
    {
        super.setText("&=");
    }

    public TAmpAssign(int line, int pos)
    {
        super.setText("&=");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TAmpAssign(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTAmpAssign(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TAmpAssign text.");
    }
}

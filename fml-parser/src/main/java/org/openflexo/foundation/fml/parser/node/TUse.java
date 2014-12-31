/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TUse extends Token
{
    public TUse()
    {
        super.setText("use");
    }

    public TUse(int line, int pos)
    {
        super.setText("use");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TUse(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTUse(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TUse text.");
    }
}

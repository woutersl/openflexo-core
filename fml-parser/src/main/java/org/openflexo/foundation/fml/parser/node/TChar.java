/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TChar extends Token
{
    public TChar()
    {
        super.setText("char");
    }

    public TChar(int line, int pos)
    {
        super.setText("char");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TChar(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTChar(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TChar text.");
    }
}

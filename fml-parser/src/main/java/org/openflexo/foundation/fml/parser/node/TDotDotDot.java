/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TDotDotDot extends Token
{
    public TDotDotDot()
    {
        super.setText("...");
    }

    public TDotDotDot(int line, int pos)
    {
        super.setText("...");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TDotDotDot(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTDotDotDot(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TDotDotDot text.");
    }
}
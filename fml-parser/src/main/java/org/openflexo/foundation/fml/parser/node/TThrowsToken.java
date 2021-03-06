/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TThrowsToken extends Token
{
    public TThrowsToken()
    {
        super.setText("throws");
    }

    public TThrowsToken(int line, int pos)
    {
        super.setText("throws");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TThrowsToken(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTThrowsToken(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TThrowsToken text.");
    }
}

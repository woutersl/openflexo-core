/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TContinue extends Token
{
    public TContinue()
    {
        super.setText("continue");
    }

    public TContinue(int line, int pos)
    {
        super.setText("continue");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TContinue(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTContinue(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TContinue text.");
    }
}
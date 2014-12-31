/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TSuperToken extends Token
{
    public TSuperToken()
    {
        super.setText("super");
    }

    public TSuperToken(int line, int pos)
    {
        super.setText("super");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TSuperToken(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTSuperToken(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TSuperToken text.");
    }
}

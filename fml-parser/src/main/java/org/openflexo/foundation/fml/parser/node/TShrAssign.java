/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TShrAssign extends Token
{
    public TShrAssign()
    {
        super.setText(">>=");
    }

    public TShrAssign(int line, int pos)
    {
        super.setText(">>=");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TShrAssign(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTShrAssign(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TShrAssign text.");
    }
}
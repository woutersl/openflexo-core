/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TPackage extends Token
{
    public TPackage()
    {
        super.setText("package");
    }

    public TPackage(int line, int pos)
    {
        super.setText("package");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TPackage(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTPackage(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TPackage text.");
    }
}

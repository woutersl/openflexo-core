/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.openflexo.foundation.fml.parser.node;

import org.openflexo.foundation.fml.parser.analysis.*;

@SuppressWarnings("nls")
public final class TImport extends Token
{
    public TImport()
    {
        super.setText("import");
    }

    public TImport(int line, int pos)
    {
        super.setText("import");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TImport(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTImport(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TImport text.");
    }
}

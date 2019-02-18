/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.PositionInputStream;

/**
 *
 * @author Wolfgang Reder
 */
public final class EOIEntry extends AbstractJFIFEntry
{

  public static EOIEntry newInstance(PositionInputStream is,
                                     int marker)
  {
    return new EOIEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        is.getPosition() - 2);
  }

  public EOIEntry(SegmentSource source,
                  int marker,
                  long offset)
  {
    super(source,
          marker,
          "EOI",
          0,
          offset,
          null);
  }

  @Override
  public int getPrefixLength()
  {
    return 0;
  }

}

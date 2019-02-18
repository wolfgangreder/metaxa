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
public final class SOIEntry extends AbstractJFIFEntry
{

  public static SOIEntry newInstance(PositionInputStream is,
                                     int marker)
  {
    return new SOIEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        is.getPosition() - 2);
  }

  public SOIEntry(SegmentSource source,
                  int marker,
                  long offset)
  {
    super(source,
          marker,
          "SOI",
          0,
          offset,
          null);
  }

  @Override
  public int getPrefixLength()
  {
    return 2;
  }

}

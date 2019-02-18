/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.PositionInputStream;
import java.io.IOException;

/**
 *
 * @author Wolfgang Reder
 */
public final class DQTEntry extends AbstractJFIFEntry
{

  public static DQTEntry newInstance(PositionInputStream is,
                                     int marker) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = loadShort(is) - 2;
    if (length == -1) {
      return null;
    }
    skipToEndOfEntryLength(is,
                           length);
    return new DQTEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        "DQT",
                        length,
                        offset);
  }

  public DQTEntry(SegmentSource source,
                  int marker,
                  String name,
                  int length,
                  long offset)
  {
    super(source,
          marker,
          name,
          length,
          offset,
          null);
  }

  @Override
  public long getDataOffset()
  {
    return super.getDataOffset(); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getPrefixLength()
  {
    return super.getPrefixLength(); //To change body of generated methods, choose Tools | Templates.
  }

}

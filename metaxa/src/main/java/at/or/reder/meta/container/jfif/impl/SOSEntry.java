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
public final class SOSEntry extends AbstractJFIFEntry
{

  public static SOSEntry newInstance(PositionInputStream is,
                                     int marker) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = skipToEndOfEntry(is);
    return new SOSEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        "SOS",
                        length,
                        offset,
                        null);
  }

  public static int skipToEndOfEntry(PositionInputStream is) throws IOException
  {
    boolean ffDetected;
    int b = 0;
    long length = is.getPosition();
    do {
      ffDetected = b == 0xff;
      b = is.read();
      if (b != -1) {
        b = b & 0xff;
      }
    } while (b != -1 && !(ffDetected && b != 0));
    length = is.getPosition() - length;
    if (b != -1) {
      length -= 2;
      is.setBackBytes(new byte[]{(byte) 0xff, (byte) b});
    }
    return (int) length;
  }

  public SOSEntry(SegmentSource source,
                  int marker,
                  String name,
                  int length,
                  long offset,
                  String extensionName)
  {
    super(source,
          marker,
          name,
          length,
          offset,
          extensionName);
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.jfif.JFIFDirectory;
import at.or.reder.meta.container.jfif.JFIFEntry;
import at.or.reder.meta.container.jfif.JFIFFactory;
import at.or.reder.meta.container.util.PositionInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public class JFIFFactoryImpl implements JFIFFactory
{

  private PositionInputStream openInputStream(URL u) throws IOException
  {
    return new PositionInputStream(u,
                                   u.openStream());
  }

  private JFIFEntry getNextEntry(PositionInputStream is) throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.order(ByteOrder.BIG_ENDIAN);
    int read = is.fillBuffer(2,
                             buffer);
    if (read == 2) {
      int marker = buffer.getShort() & 0xffff;
      JFIFSegments seg = JFIFSegments.valueOf(marker);
      if (seg == null || seg.getFactory() == null) {
        throw new UnsupportedOperationException("Unknown marker " + Integer.toHexString(marker));
      }
      return seg.getFactory().apply(is,
                                    marker);
    }
    return null;
  }

  private boolean readMagic(PositionInputStream is) throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.order(ByteOrder.BIG_ENDIAN);
    int read = is.fillBuffer(2,
                             buffer);
    if (read == 2) {
      int marker = buffer.getShort() & 0xffff;
      return marker == 0xffd8;
    }
    return false;
  }

  @Override
  public JFIFDirectory readDirectory(URL url) throws IOException
  {
    try (PositionInputStream is = openInputStream(url)) {
      List<JFIFEntry> entryList = new ArrayList<>();
      JFIFEntry entry;
      while ((entry = getNextEntry(is)) != null) {
        entryList.add(entry);
      }
      return new JFIFDirectoryImpl(entryList);
    }
  }

}

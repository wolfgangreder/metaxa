/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.jfif.JFIFEntry;
import at.or.reder.meta.container.util.PositionInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
public abstract class AbstractJFIFEntry implements JFIFEntry
{

  private final String extensionName;
  private final int marker;
  private final String name;
  private final int length;
  private final SegmentSource source;
  private final long offset;

  protected AbstractJFIFEntry(SegmentSource source,
                              int marker,
                              String name,
                              int length,
                              long offset,
                              String extensionName)
  {
    this.source = source;
    this.marker = marker;
    this.name = name;
    this.length = length;
    this.offset = offset;
    this.extensionName = extensionName;
  }

  @Override
  public int getMarker()
  {
    return marker;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public int getLength()
  {
    return length;
  }

  @Override
  public String getExtensionName()
  {
    return extensionName;
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    if (source != null) {
      return source.openStream(offset,
                               getLength() + getPrefixLength());
    }
    return null;
  }

  @Override
  public InputStream getDataStream() throws IOException
  {
    if (source != null) {
      return source.openStream(offset + getPrefixLength(),
                               getLength());
    }
    return null;
  }

  @Override
  public long getOffset()
  {
    return offset;
  }

  @Override
  public int compareTo(JFIFEntry o)
  {
    JFIFSegments a = JFIFSegments.valueOf(marker);
    JFIFSegments b = o != null ? JFIFSegments.valueOf(o.getMarker()) : null;
    if (a == b) {
      return 0;
    }
    if (a == null) {
      return 1;
    }
    if (b == null) {
      return -1;
    }
    return a.compareTo(b);
  }

  protected static int loadShort(PositionInputStream is) throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.order(ByteOrder.BIG_ENDIAN);
    int read = is.fillBuffer(2,
                             buffer);
    if (read != 2) {
      return -1;
    }
    int result = buffer.getShort() & 0xffff;
    return result;
  }

  protected static void skipToEndOfEntryLength(PositionInputStream is,
                                               int toSkip) throws IOException
  {
    while (toSkip > 0) {
      toSkip -= is.skip(toSkip);
    }
  }

  @Override
  public int hashCode()
  {
    int hash = 5;
    hash = 41 * hash + Objects.hashCode(this.extensionName);
    hash = 41 * hash + this.marker;
    hash = 41 * hash + (int) (this.offset ^ (this.offset >>> 32));
    return hash;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final AbstractJFIFEntry other = (AbstractJFIFEntry) obj;
    if (this.marker != other.marker) {
      return false;
    }
    if (this.offset != other.offset) {
      return false;
    }
    return Objects.equals(this.extensionName,
                          other.extensionName);
  }

  @Override
  public String toString()
  {
    if (extensionName == null) {
      return name + "(0x" + Integer.toHexString(marker) + ")@0x" + Long.toHexString(offset);
    } else {
      return name + "(0x" + Integer.toHexString(marker) + ") " + extensionName + "@0x" + Long.toHexString(offset);

    }
  }

}

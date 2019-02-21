/*
 * Copyright 2019 Wolfgang Reder.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.or.reder.media.jfif.impl;

import at.or.reder.media.jfif.JFIFMarker;
import at.or.reder.media.jfif.JFIFEntry;
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.io.SegmentSource;
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
    JFIFMarker a = JFIFMarker.valueOf(marker);
    JFIFMarker b = o != null ? JFIFMarker.valueOf(o.getMarker()) : null;
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

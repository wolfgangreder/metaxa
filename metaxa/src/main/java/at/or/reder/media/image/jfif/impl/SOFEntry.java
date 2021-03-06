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
package at.or.reder.media.image.jfif.impl;

import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.io.SegmentSource;
import at.or.reder.media.io.SegmentSourceFactory;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author Wolfgang Reder
 */
public class SOFEntry extends AbstractJFIFEntry
{

  public static SOFEntry newInstance(PositionInputStream is,
                                     int marker,
                                     int sequenceCounter) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = loadShort(is) - 2;
    if (length == -1) {
      return null;
    }
    skipToEndOfEntryLength(is,
                           length);
    return new SOFEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        sequenceCounter,
                        "SOF" + Integer.toHexString(marker & 0xf),
                        length,
                        offset,
                        getSOFName(marker));
  }

  private static String getSOFName(int marker)
  {
    switch (marker) {
      case 0xffc0:
        return "Baseline DCT";
      case 0xffc1:
        return "Extended sequential DCT";
      case 0xffc2:
        return "Progressive DCT";
      case 0xffc3:
        return "Spatial (sequential) lossless";
      case 0xffc5:
        return "Differential sequential DCT";
      case 0xffc6:
        return "Differential progressive DCT";
      case 0xffc7:
        return "Differential spatial";
      case 0xffc8:
        return "reserved";
      case 0xffc9:
        return "Extended Sequential DCT";
      case 0xffca:
        return "Progressive DCT";
      case 0xffcb:
        return "Spatial (sequential) lossless";
      case 0xffcd:
        return "Differential sequential DCT";
      case 0xffce:
        return "Differential progressive DCT";
      case 0xffcf:
        return "Differential spatial";
      default:
        return "";
    }
  }

  public SOFEntry(SegmentSource source,
                  int marker,
                  int sequenceCounter,
                  String name,
                  int length,
                  long offset,
                  String extensionName)
  {
    super(source,
          marker,
          sequenceCounter,
          name,
          length,
          offset,
          extensionName);
  }

  public Dimension getImageDimension() throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate(getLength());
    buffer.order(ByteOrder.BIG_ENDIAN);
    try ( ReadableByteChannel channel = Channels.newChannel(getDataStream())) {
      int read = channel.read(buffer);
      if (read != getLength()) {
        throw new IOException("Cannot read SOFEntry");
      }
      buffer.rewind();
      byte precision = buffer.get();
      int height = buffer.getShort() & 0xffff;
      int width = buffer.getShort() & 0xffff;
      return new Dimension(width,
                           height);
    }
  }

}

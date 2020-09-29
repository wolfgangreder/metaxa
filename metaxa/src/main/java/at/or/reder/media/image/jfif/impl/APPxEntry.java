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

import at.or.reder.media.image.jfif.JFIFMarker;
import at.or.reder.media.io.ByteBufferSegmentSource;
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.io.SegmentSource;
import at.or.reder.media.io.SegmentSourceFactory;
import at.or.reder.media.meta.ImageGeometrie;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Wolfgang Reder
 */
public final class APPxEntry extends AbstractJFIFEntry
{

  public static APPxEntry newAPP0Entry(ImageGeometrie geo)
  {
    ByteBuffer buffer = ByteBuffer.allocate(18);
    buffer.order(ByteOrder.BIG_ENDIAN);
    buffer.putShort((short) JFIFMarker.APP0.getMarker());
    buffer.putShort((short) (buffer.capacity() - 2));
    buffer.put((byte) 0x4a);
    buffer.put((byte) 0x46);
    buffer.put((byte) 0x49);
    buffer.put((byte) 0x46);
    buffer.put((byte) 0);
    buffer.putShort((short) (0x0101));
    switch (geo.getUnit()) {
      case DPI:
        buffer.put((byte) 1);
        break;
      case DPICM:
        buffer.put((byte) 2);
        break;
      case NONE:
      default:
        buffer.put((byte) 0);
        break;
    }
    buffer.putShort((short) geo.getDensityX());
    buffer.putShort((short) geo.getDensityY());
    buffer.putShort((short) 0);
    buffer.rewind();
    return new APPxEntry(JFIFMarker.APP0.getMarker(),
                         1,
                         "APP0",
                         buffer.remaining(),
                         "JFIF",
                         new ByteBufferSegmentSource(buffer),
                         0,
                         0);
  }

  public static APPxEntry newInstance(PositionInputStream is,
                                      int marker,
                                      int inputSequence) throws IOException
  {
    switch (marker) {
      case 0xffe0:
        return newAPP0Entry(is,
                            inputSequence);
      case 0xffe1:
        return newAPPxEntry(is,
                            marker,
                            inputSequence); // Exif,XMP
      case 0xffe2:
        return newAPPxEntry(is,
                            marker,
                            inputSequence); // ICC_PROFILE
      case 0xffed:
        return newAPPxEntry(is,
                            marker,
                            inputSequence); // IPTC
      default:
        return newAPPxEntry(is,
                            marker,
                            inputSequence);
    }
  }

  private static String getExtionsName(PositionInputStream is) throws IOException
  {
    char ch;
    StringBuilder builder = new StringBuilder();
    do {
      ch = (char) is.read();
      if (ch != 0 && ch != -1) {
        builder.append(ch);
      }
    } while (ch != 0 && ch != -1);
    return builder.toString();
  }

  private static APPxEntry newAPP0Entry(PositionInputStream is,
                                        int inputSequence) throws IOException
  {
    long offset = is.getPosition() - 2;
    long relPrefixStart = 2;
    int length = loadShort(is);
    if (length == -1) {
      return null;
    }
    String extName = getExtionsName(is);
    long relPrefixEnd = is.getPosition() - offset;
    int prefixSize = (int) (relPrefixEnd - relPrefixStart);
    length -= prefixSize;
    skipToEndOfEntryLength(is,
                           length);
    return new APPxEntry(0xffe0,
                         inputSequence,
                         "APP0",
                         length,
                         extName,
                         is.getURL(),
                         offset,
                         prefixSize);
  }

  private static String getSegmentName(int marker)
  {
    int tmp = marker - JFIFMarker.APP0.getMarker();
    return "APP" + Integer.toString(tmp);
  }

  private static APPxEntry newAPPxEntry(PositionInputStream is,
                                        int marker,
                                        int inpuSequence) throws IOException
  {
    long offset = is.getPosition() - 2;
    long relPrefixStart = 2;
    int length = loadShort(is);
    if (length == -1) {
      return null;
    }
    String extName = getExtionsName(is);
    if ("Exif".equals(extName)) { // Exif has 2 terminating 0
      is.read();
    }
    long relPrefixEnd = is.getPosition() - offset;
    int prefixSize = (int) (relPrefixEnd - relPrefixStart);
    length -= prefixSize;
    skipToEndOfEntryLength(is,
                           length);
    return new APPxEntry(marker,
                         inpuSequence,
                         getSegmentName(marker),
                         length,
                         extName,
                         is.getURL(),
                         offset,
                         prefixSize);
  }

  private final int prefixSize;

  public APPxEntry(int marker,
                   int inputSequence,
                   String name,
                   int length,
                   String extensionName,
                   URL url,
                   long offset,
                   int prefixSize)
  {
    super(SegmentSourceFactory.instanceOf(url),
          marker,
          inputSequence,
          name,
          length,
          offset,
          extensionName);
    this.prefixSize = prefixSize;
  }

  private APPxEntry(int marker,
                    int inputSequence,
                    String name,
                    int length,
                    String extensionName,
                    SegmentSource source,
                    long offset,
                    int prefixSize)
  {
    super(source,
          marker,
          inputSequence,
          name,
          length,
          offset,
          extensionName);
    this.prefixSize = prefixSize;
  }

  @Override
  public int getPrefixLength()
  {
    return prefixSize + 2;
  }

}

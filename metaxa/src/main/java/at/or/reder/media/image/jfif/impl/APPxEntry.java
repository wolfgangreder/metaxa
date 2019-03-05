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
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.io.SegmentSourceFactory;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public final class APPxEntry extends AbstractJFIFEntry
{

  public static APPxEntry newInstance(PositionInputStream is,
                                      int marker) throws IOException
  {
    switch (marker) {
      case 0xffe0:
        return newAPP0Entry(is);
      case 0xffe1:
        return newAPPxEntry(is,
                            marker); // Exif,XMP
      case 0xffe2:
        return newAPPxEntry(is,
                            marker); // ICC_PROFILE
      case 0xffed:
        return newAPPxEntry(is,
                            marker); // IPTC
      default:
        return newAPPxEntry(is,
                            marker);
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

  private static APPxEntry newAPP0Entry(PositionInputStream is) throws IOException
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
                                        int marker) throws IOException
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
                         getSegmentName(marker),
                         length,
                         extName,
                         is.getURL(),
                         offset,
                         prefixSize);
  }

  private final int prefixSize;

  public APPxEntry(int marker,
                   String name,
                   int length,
                   String extensionName,
                   URL url,
                   long offset,
                   int prefixSize)
  {
    super(SegmentSourceFactory.instanceOf(url),
          marker,
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

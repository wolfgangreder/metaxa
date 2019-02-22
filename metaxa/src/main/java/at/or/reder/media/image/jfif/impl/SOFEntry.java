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

import at.or.reder.media.image.jfif.impl.AbstractJFIFEntry;
import at.or.reder.media.io.SegmentSourceFactory;
import at.or.reder.media.io.SegmentSource;
import at.or.reder.media.io.PositionInputStream;
import java.io.IOException;

/**
 *
 * @author Wolfgang Reder
 */
public class SOFEntry extends AbstractJFIFEntry
{

  public static SOFEntry newInstance(PositionInputStream is,
                                     int marker) throws IOException
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

  @Override
  public int getPrefixLength()
  {
    return super.getPrefixLength(); //To change body of generated methods, choose Tools | Templates.
  }

}

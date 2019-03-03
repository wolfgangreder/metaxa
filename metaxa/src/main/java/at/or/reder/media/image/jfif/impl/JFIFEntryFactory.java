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

import at.or.reder.media.image.jfif.JFIFEntry;
import at.or.reder.media.image.jfif.JFIFMarker;
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.util.IOBiFunction;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
final class JFIFEntryFactory
{

  private static IOBiFunction<PositionInputStream, Integer, JFIFEntry> getFactory(JFIFMarker seg)
  {
    if (seg != null) {
      switch (seg) {
        case APP0:
          return APPxEntry::newInstance;
        case APP1:
          return APPxEntry::newInstance;
        case APP2:
          return APPxEntry::newInstance;
        case COM:
          return null;
        case DAC:
          return null;
        case DHT:
          return DHTEntry::newInstance;
        case DQT:
          return DHTEntry::newInstance;
        case DRI:
          return null;
        case EOI:
          return EOIEntry::newInstance;
        case JPG:
          return null;
        case SOF0:
          return SOFEntry::newInstance;
        case SOF1:
          return SOFEntry::newInstance;
        case SOF10:
          return SOFEntry::newInstance;
        case SOF11:
          return SOFEntry::newInstance;
        case SOF13:
          return SOFEntry::newInstance;
        case SOF14:
          return SOFEntry::newInstance;
        case SOF15:
          return SOFEntry::newInstance;
        case SOF2:
          return SOFEntry::newInstance;
        case SOF3:
          return SOFEntry::newInstance;
        case SOF5:
          return SOFEntry::newInstance;
        case SOF6:
          return SOFEntry::newInstance;
        case SOF7:
          return SOFEntry::newInstance;
        case SOF9:
          return SOFEntry::newInstance;
        case SOI:
          return SOIEntry::newInstance;
        case SOS:
          return SOSEntry::newInstance;
        case APP13:
          return APPxEntry::newInstance;
        case APP14:
          return APPxEntry::newInstance;
      }
    }
    return null;
  }

  private static JFIFEntry getNextEntry(PositionInputStream is) throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.order(ByteOrder.BIG_ENDIAN);
    int read = is.fillBuffer(2,
                             buffer);
    if (read == 2) {
      int marker = buffer.getShort() & 0xffff;
      JFIFMarker seg = JFIFMarker.valueOf(marker);
      IOBiFunction<PositionInputStream, Integer, JFIFEntry> factory = getFactory(seg);
      if (factory == null) {
        throw new UnsupportedOperationException("Unknown marker " + Integer.toHexString(marker));
      }
      return factory.apply(is,
                           marker);
    }
    return null;
  }

  public static List<JFIFEntry> readDirectory(InputStream stream) throws IOException
  {
    try (PositionInputStream is = new PositionInputStream(null,
                                                          stream,
                                                          2)) {
      List<JFIFEntry> entryList = new ArrayList<>();
      JFIFEntry entry;
      while ((entry = getNextEntry(is)) != null) {
        entryList.add(entry);
      }
      return entryList;
    }
  }

  public static List<JFIFEntry> readDirectory(URL url) throws IOException
  {
    try (PositionInputStream is = new PositionInputStream(url,
                                                          url.openStream(),
                                                          2)) {
      List<JFIFEntry> entryList = new ArrayList<>();
      JFIFEntry entry;
      while ((entry = getNextEntry(is)) != null) {
        entryList.add(entry);
      }
      return entryList;
    }
  }

}

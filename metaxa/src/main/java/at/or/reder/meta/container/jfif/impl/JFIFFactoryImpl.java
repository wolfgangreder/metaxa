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
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.jfif.JFIFDirectory;
import at.or.reder.meta.container.jfif.JFIFEntry;
import at.or.reder.meta.container.jfif.JFIFFactory;
import at.or.reder.meta.container.jfif.JFIFMarker;
import at.or.reder.meta.container.util.IOBiFunction;
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
                                   u.openStream(),
                                   2);
  }

  private IOBiFunction<PositionInputStream, Integer, JFIFEntry> getFactory(JFIFMarker seg)
  {
    if (seg != null) {
      switch (seg) {
        case APP0:
          return APPxEntry::newInstance;
        case APP1:
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
      }
    }
    return null;
  }

  private JFIFEntry getNextEntry(PositionInputStream is) throws IOException
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

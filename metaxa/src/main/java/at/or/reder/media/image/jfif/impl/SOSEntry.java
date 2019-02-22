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
    is.setRewindReadEnabled(true);
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
      is.rewind();
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

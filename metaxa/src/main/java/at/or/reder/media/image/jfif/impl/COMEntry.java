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

import static at.or.reder.media.image.jfif.impl.AbstractJFIFEntry.loadShort;
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.io.SegmentSource;
import at.or.reder.media.io.SegmentSourceFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 *
 * @author Wolfgang Reder
 */
public final class COMEntry extends AbstractJFIFEntry
{

  public static COMEntry newInstance(PositionInputStream is,
                                     int marker) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = loadShort(is) - 2;
    if (length == -1) {
      return null;
    }
    skipToEndOfEntryLength(is,
                           length);
    return new COMEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        "COM",
                        length,
                        offset);
  }

  public COMEntry(SegmentSource source,
                  int marker,
                  String name,
                  int length,
                  long offset)
  {
    super(source,
          marker,
          name,
          length,
          offset,
          null);
  }

  public String getComment(Charset charSet) throws IOException
  {
    try (InputStream is = getDataStream();
            InputStreamReader reader = new InputStreamReader(is,
                                                             charSet != null ? charSet : Charset.defaultCharset())) {
      StringBuilder builder = new StringBuilder();
      CharBuffer buffer = CharBuffer.allocate(getLength());
      int read;
      while ((read = reader.read(buffer)) > 0) {
        buffer.rewind();
        buffer.limit(read);
        builder.append(buffer);
      }
      return builder.toString();
    }
  }

}

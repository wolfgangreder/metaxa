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
import java.io.IOException;

/**
 *
 * @author Wolfgang Reder
 */
public final class DQTEntry extends AbstractJFIFEntry
{

  public static DQTEntry newInstance(PositionInputStream is,
                                     int marker,
                                     int inputSequence) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = loadShort(is) - 2;
    if (length == -1) {
      return null;
    }
    skipToEndOfEntryLength(is,
                           length);
    return new DQTEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        inputSequence,
                        "DQT",
                        length,
                        offset);
  }

  public DQTEntry(SegmentSource source,
                  int marker,
                  int inputSequence,
                  String name,
                  int length,
                  long offset)
  {
    super(source,
          marker,
          inputSequence,
          name,
          length,
          offset,
          null);
  }

}

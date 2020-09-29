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
import java.nio.ByteBuffer;

public final class SOIEntry extends AbstractJFIFEntry
{

  public static SOIEntry newInstance()
  {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.put((byte) 0xff);
    buffer.put((byte) 0xd8);
    buffer.rewind();
    return new SOIEntry(new ByteBufferSegmentSource(buffer),
                        JFIFMarker.SOI.getMarker(),
                        0,
                        0);
  }

  public static SOIEntry newInstance(PositionInputStream is,
                                     int marker,
                                     int sequenceCounter)
  {
    return new SOIEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        sequenceCounter,
                        is.getPosition() - 2);
  }

  public SOIEntry(SegmentSource source,
                  int marker,
                  int sequenceCounter,
                  long offset)
  {
    super(source,
          marker,
          sequenceCounter,
          "SOI",
          0,
          offset,
          null);
  }

  @Override
  public int getPrefixLength()
  {
    return 2;
  }

}

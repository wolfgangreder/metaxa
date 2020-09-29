/*
 * Copyright 2020 Wolfgang Reder.
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
package at.or.reder.media.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

public class ByteBufferSegmentSource implements SegmentSource
{

  private final ByteBuffer buffer;

  public ByteBufferSegmentSource(ByteBuffer buffer)
  {
    this.buffer = buffer;
    if (!buffer.hasArray()) {
      throw new IllegalArgumentException("buffer must have a backing array");
    }
  }

  @Override
  public URL getURL()
  {
    return null;
  }

  @Override
  public InputStream openStream(long offset,
                                int size)

  {
    return new ByteArrayInputStream(buffer.array());
  }

}

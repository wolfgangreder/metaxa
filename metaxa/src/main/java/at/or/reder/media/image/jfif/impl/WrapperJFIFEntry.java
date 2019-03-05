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

import at.or.reder.media.MediaChunk;
import at.or.reder.media.image.jfif.JFIFMarker;
import at.or.reder.media.io.ProxyInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Wolfgang Reder
 */
final class WrapperJFIFEntry extends AbstractJFIFEntry
{

  private final MediaChunk dataChunk;

  private static int getLength(JFIFMarker marker,
                               MediaChunk dataChunk)
  {
    return dataChunk.getLength();
  }

  private static long getDataOffset(JFIFMarker marker,
                                    MediaChunk dataChunk,
                                    String extensionName)
  {
    if (dataChunk instanceof AbstractJFIFEntry) {
      return ((AbstractJFIFEntry) dataChunk).getDataOffset();
    } else if (marker == JFIFMarker.APP1) {
      return 5 + extensionName.getBytes(StandardCharsets.UTF_8).length;
    } else {
      return 0;
    }
  }

  public WrapperJFIFEntry(JFIFMarker marker,
                          String extensionName,
                          MediaChunk dataChunk)
  {

    super(null,
          marker.getMarker(),
          marker.getName(),
          getLength(marker,
                    dataChunk),
          getDataOffset(marker,
                        dataChunk,
                        extensionName),
          extensionName);
    this.dataChunk = dataChunk;
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    ByteBuffer buffer = ByteBuffer.allocate((int) getOffset());
    buffer.order(ByteOrder.BIG_ENDIAN);
    short s = (short) getMarker();
    buffer.putShort(s);
    s = (short) (getLength() + getOffset() - 2);
    buffer.putShort(s);
    buffer.put(getExtensionName().getBytes(StandardCharsets.UTF_8));
    buffer.put((byte) 0);
    return new ProxyInputStream(new ByteArrayInputStream(buffer.array()),
                                dataChunk.getInputStream());
  }

  @Override
  public InputStream getDataStream() throws IOException
  {
    return dataChunk.getInputStream();
  }

}

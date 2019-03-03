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

}

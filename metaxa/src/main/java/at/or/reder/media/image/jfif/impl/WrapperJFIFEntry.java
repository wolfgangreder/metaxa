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
import java.io.IOException;
import java.io.InputStream;

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

  }

  private static int getOffset(JFIFMarker marker,
                               String extensionName)
  {

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
          getOffset(marker,
                    extensionName),
          extensionName);
  }

  @Override
  public long getDataOffset()
  {
    return super.getDataOffset(); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public int getPrefixLength()
  {
    return super.getPrefixLength(); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public InputStream getDataStream() throws IOException
  {
    return super.getDataStream(); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    return super.getInputStream(); //To change body of generated methods, choose Tools | Templates.
  }

}

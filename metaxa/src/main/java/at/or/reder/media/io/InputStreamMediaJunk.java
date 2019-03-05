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
package at.or.reder.media.io;

import at.or.reder.media.MediaChunk;
import at.or.reder.media.util.IOSupplier;
import at.or.reder.media.util.MediaUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 *
 * @author Wolfgang Reder
 */
public final class InputStreamMediaJunk implements MediaChunk
{

  private final String name;
  private int length;
  private final String extensionName;
  private final IOSupplier<InputStream> streamProvider;

  public InputStreamMediaJunk(String name,
                              int length,
                              String extensionName,
                              IOSupplier<InputStream> streamProvider)
  {
    this.name = name;
    this.length = length;
    this.extensionName = extensionName;
    this.streamProvider = streamProvider;
  }

  @Override
  public String getName()
  {
    return name;
  }

  @Override
  public int getLength()
  {
    if (length == -1) {
      try (InputStream is = getInputStream()) {
        byte[] tmp = new byte[4096];
        int read;
        length = 0;
        while ((read = is.read(tmp,
                               0,
                               tmp.length)) > 0) {
          length += read;
        }
      } catch (IOException ex) {
        MediaUtils.LOGGER.log(Level.SEVERE,
                              null,
                              ex);
      }
    }
    return length;
  }

  @Override
  public String getExtensionName()
  {
    return extensionName;
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    return streamProvider.get();
  }

  @Override
  public InputStream getDataStream() throws IOException
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}

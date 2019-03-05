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
package at.or.reder.media;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public class DummyChunk implements MediaChunk
{

  private final URL mediaURL;

  public DummyChunk(URL mediaURL)
  {
    this.mediaURL = mediaURL;
  }

  @Override
  public String getName()
  {
    return mediaURL.getFile();
  }

  @Override
  public int getLength()
  {
    if ("file".equals(mediaURL.getProtocol())) {
      File file = new File(mediaURL.getPath());
      if (file.exists()) {
        return (int) file.length();
      }
    }
    return -1;
  }

  @Override
  public String getExtensionName()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public InputStream getInputStream() throws IOException
  {
    return mediaURL.openStream();
  }

  @Override
  public InputStream getDataStream() throws IOException
  {
    return mediaURL.openStream();
  }

}

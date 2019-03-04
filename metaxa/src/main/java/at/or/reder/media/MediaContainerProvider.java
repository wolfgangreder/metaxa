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

import at.or.reder.media.util.MediaUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;

/**
 *
 * @author Wolfgang Reder
 */
public interface MediaContainerProvider
{

  public boolean isValidFor(String mimeType);

  /**
   * Tests is this stream can be processed.
   *
   * @param is inputStream
   * @return {@code true} is this Provider supports this stream.
   * @throws java.io.IOException on IO Error
   */
  public boolean isValidFor(InputStream is) throws IOException;

  public default boolean isValidFor(URL url)
  {
    try (InputStream is = url.openStream()) {
      return isValidFor(is);
    } catch (IOException ex) {
      MediaUtils.LOGGER.log(Level.FINE,
                            null,
                            ex);
    }
    return false;
  }

  public default boolean isValidFor(File file)
  {
    try (FileInputStream fis = new FileInputStream(file)) {
      return isValidFor(fis);
    } catch (IOException ex) {
      MediaUtils.LOGGER.log(Level.FINE,
                            null,
                            ex);
    }
    return false;
  }

  public MediaContainer createContainer(InputStream is) throws IOException;

  public default MediaContainer createContainer(URL url) throws IOException
  {
    try (InputStream is = url.openStream()) {
      return createContainer(is);
    }
  }

  public default MediaContainer createContainer(File file) throws IOException
  {
    return createContainer(file.toURI().toURL());
  }

  public MutableMediaContainer createEmptyContainer();

  public MediaContainer copyContainer(MediaContainer container);

}

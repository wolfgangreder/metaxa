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
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.SizedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolfgang Reder
 */
public final class FileSegmentSource implements SegmentSource
{

  private final Path path;

  public FileSegmentSource(File file)
  {
    this(Objects.requireNonNull(file,
                                "file is null").toPath());
  }

  public FileSegmentSource(Path path)
  {
    this.path = Objects.requireNonNull(path,
                                       "path is null").normalize().toAbsolutePath();
    if (!Files.isRegularFile(path) || !Files.isReadable(path)) {
      throw new IllegalArgumentException("Path " + path.toString() + " is not e regular file, or cannot be read.");
    }
  }

  @Override
  public URL getURL()
  {
    try {
      return path.toUri().toURL();
    } catch (MalformedURLException ex) {
      Logger.getLogger(FileSegmentSource.class.getName()).log(Level.SEVERE,
                                                              null,
                                                              ex);
    }
    return null;
  }

  @Override
  public InputStream openStream(long offset,
                                int size) throws IOException
  {
    InputStream result = new FileInputStream(path.toFile());
    while (offset > 0) {
      offset -= result.skip(offset);
    }
    return new SizedInputStream(result,
                                size);
  }

}

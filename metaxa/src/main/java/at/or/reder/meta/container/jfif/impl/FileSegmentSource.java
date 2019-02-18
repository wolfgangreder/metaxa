/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

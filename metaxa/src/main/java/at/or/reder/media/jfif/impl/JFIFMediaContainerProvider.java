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
package at.or.reder.media.jfif.impl;

import at.or.reder.media.MediaContainer;
import at.or.reder.media.MediaContainerProvider;
import at.or.reder.media.MimeTypes;
import at.or.reder.media.jfif.JFIFEntry;
import at.or.reder.media.util.MediaUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Wolfgang Reder
 */
public final class JFIFMediaContainerProvider implements MediaContainerProvider
{

  private final ImageReaderSpi jpegReaderSpi;

  public JFIFMediaContainerProvider()
  {
    jpegReaderSpi = Objects.requireNonNull(MediaUtils.getReaderSpi("image/jpeg"),
                                           "No JPEG ImageReaderSpi found");
  }

  @Override
  public boolean isValidFor(String mimeType)
  {
    return MimeTypes.IMAGE_JPEG.getMimeType().equals(mimeType);
  }

  @Override
  public boolean isValidFor(InputStream is) throws IOException
  {
    try (ImageInputStream iis = ImageIO.createImageInputStream(is)) {
      return jpegReaderSpi.canDecodeInput(iis);
    }
  }

  @Override
  public MediaContainer createContainer(InputStream is) throws IOException
  {
    List<JFIFEntry> entries = JFIFEntryFactory.readDirectory(is);
    return new JFIFMediaContainerImpl(null,
                                      entries);
  }

  @Override
  public MediaContainer createContainer(URL url) throws IOException
  {
    List<JFIFEntry> entries = JFIFEntryFactory.readDirectory(url);
    return new JFIFMediaContainerImpl(url,
                                      entries);
  }

  @Override
  public void storeToStream(OutputStream os) throws IOException
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}

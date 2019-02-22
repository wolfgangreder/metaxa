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
package at.or.reder.media.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.spi.IIORegistry;
import javax.imageio.spi.ImageReaderSpi;

/**
 *
 * @author Wolfgang Reder
 */
public final class MediaUtils
{

  public static final Logger LOGGER = Logger.getLogger("at.or.reder.media");

  public static String formatLong(long l)
  {
    StringBuilder b = new StringBuilder();
    b.append(Long.toString(l));
    b.append(" (0x");
    String tmp = Long.toHexString(l);
    int i = 0;
//    for (; i < (8 - tmp.length()); ++i) {
//      b.append('0');
////      if (i == 3) {
////        b.append(' ');
////      }
//    }
    for (int j = 0; j < tmp.length(); ++j, ++i) {
      b.append(tmp.charAt(j));
//      if (i == 3) {
//        b.append(' ');
//      }
    }
    b.append(')');
    return b.toString();
  }

  public static ImageReaderSpi getReaderSpi(String mimeType)
  {
    Objects.requireNonNull(mimeType,
                           "mimeType is null");
    MediaUtils.LOGGER.log(Level.FINEST,
                          () -> "getReaderSpi(" + mimeType + ")");
    Iterator<ImageReaderSpi> iter = IIORegistry.getDefaultInstance().getServiceProviders(ImageReaderSpi.class,
                                                                                         true);
    ImageReaderSpi spi = null;
    while (spi == null && iter.hasNext()) {
      ImageReaderSpi tmp = iter.next();
      MediaUtils.LOGGER.log(Level.FINEST,
                            () -> "Testing SPI " + tmp.getDescription(Locale.getDefault()));
      String[] mimeTypes = tmp.getMIMETypes();
      if (mimeTypes != null && mimeTypes.length != 0) {
        for (String mt : mimeTypes) {
          if (mimeType.equals(mt)) {
            spi = tmp;
            MediaUtils.LOGGER.log(Level.FINE,
                                  () -> "Using SPI " + tmp.getDescription(Locale.getDefault()) + " for " + mimeType);
          }
        }
      }
    }
    return spi;
  }

  public static void transferTo(InputStream is,
                                OutputStream os) throws IOException
  {
    byte[] buffer = new byte[8192];
    int read;
    while ((read = is.read(buffer)) > 0) {
      os.write(buffer,
               0,
               read);
    }
  }

  private MediaUtils()
  {
  }

}

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
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.jfif.JFIFEntry;
import at.or.reder.media.jfif.JFIFMediaContainer;
import at.or.reder.media.util.Lookup;
import at.or.reder.media.util.MediaUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Wolfgang Reder
 */
public class JFIFMediaContainerProviderNGTest
{

  public JFIFMediaContainerProviderNGTest()
  {
  }

  @BeforeClass
  public static void setUpClass() throws Exception
  {
  }

  @Test
  public void testConstruction()
  {
    JFIFMediaContainerProvider provider = new JFIFMediaContainerProvider();
  }

  @Test
  public void testLookup()
  {
    List<MediaContainerProvider> providerList = Lookup.lookupAllInstances(MediaContainerProvider.class);
    assertNotNull(providerList);
    assertFalse(providerList.isEmpty());
    MediaContainerProvider provider = providerList.stream().
            filter((c) -> c.isValidFor(MimeTypes.IMAGE_JPEG.getMimeType())).
            findAny().
            orElse(null);
    assertNotNull(provider);
    assertSame(JFIFMediaContainerProvider.class,
               provider.getClass());
  }

  @Test
  public void testIsValidFor_String()
  {
  }

  @Test
  public void testIsValidFor_InputStream()
  {
  }

  @Test
  public void testIsValidFor_URL()
  {
  }

  @Test
  public void testIsValidFor_File()
  {
  }

  @Test
  public void testCreateContainer_InputStream() throws Exception
  {
  }

  @Test
  public void testCreateContainer_URL() throws Exception
  {
    URL u = getClass().getResource("/IMG_0658.JPG");
    MediaContainer container = new JFIFMediaContainerProvider().createContainer(u);
    assertNotNull(container);
    assertTrue(container instanceof JFIFMediaContainer);
    File tmpFile = File.createTempFile("JFIFTest",
                                       ".jpg");
    tmpFile.deleteOnExit();
    try {
      JFIFMediaContainer jfifContainer = (JFIFMediaContainer) container;
      try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
        for (JFIFEntry e : jfifContainer.getJFIFEntries()) {
          try (InputStream is = e.getInputStream()) {
            is.transferTo(fos);
          }
        }
      }
      try (PositionInputStream expected = new PositionInputStream(new BufferedInputStream(u.openStream(),
                                                                                          4096),
                                                                  0);
              PositionInputStream result = new PositionInputStream(new BufferedInputStream(new FileInputStream(tmpFile),
                                                                                           4096),
                                                                   0)) {
        int e = 0;
        int r = 0;
        while (r >= 0 && e >= 0) {
          r = result.read();
          e = expected.read();
          assertEquals("Mismatch at position 0x" + MediaUtils.formatLong(expected.getPosition()),
                       e,
                       r);
        }
        assertEquals(tmpFile.length(),
                     result.getPosition());
        assertEquals(expected.getPosition(),
                     result.getPosition());
      }
    } finally {
      tmpFile.delete();
    }
  }

  @Test
  public void testCreateContainer_File() throws Exception
  {
  }

}

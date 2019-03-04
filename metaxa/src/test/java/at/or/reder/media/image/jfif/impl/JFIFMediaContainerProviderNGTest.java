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

import at.or.reder.media.ContainerItemGroup;
import at.or.reder.media.MediaContainer;
import at.or.reder.media.MediaContainerProvider;
import at.or.reder.media.MimeTypes;
import at.or.reder.media.image.jfif.JFIFEntry;
import at.or.reder.media.image.jfif.JFIFMediaContainer;
import at.or.reder.media.io.PositionInputStream;
import at.or.reder.media.meta.MetadataContainerItem;
import at.or.reder.media.meta.xmp.XMPMetadataContainerItem;
import at.or.reder.media.util.MediaUtils;
import com.adobe.internal.xmp.XMPConst;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.options.PropertyOptions;
import com.adobe.internal.xmp.properties.XMPProperty;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import org.openide.util.Lookup;
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
    Collection<? extends MediaContainerProvider> providerList = Lookup.getDefault().lookupAll(MediaContainerProvider.class);
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
            MediaUtils.transferTo(is,
                                  fos);
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
  public void testCreateContainer_Meta() throws Exception
  {
    URL u = getClass().getResource("/IMG_0658.JPG");
    MediaContainer container = new JFIFMediaContainerProvider().createContainer(u);
    assertNotNull(container);
    assertTrue(container instanceof JFIFMediaContainer);
    List<? extends MetadataContainerItem> metaList = container.getMetadata();
    assertNotNull(metaList);
    assertEquals(1,
                 metaList.size());
    metaList = container.findContainerItem(ContainerItemGroup.METADATA,
                                           XMPMetadataContainerItem.class);
    assertNotNull(metaList);
    assertEquals(1,
                 metaList.size());
    XMPMetadataContainerItem xmpData = container.getContainerItem(ContainerItemGroup.METADATA,
                                                                  XMPMetadataContainerItem.class);
    assertNotNull(xmpData);
    XMPMeta xp = xmpData.getItem(XMPMeta.class);
    assertNotNull(xp);
    String s = xmpData.getItem(String.class);
    assertNotNull(s);
    XMPProperty prop = xp.getProperty(XMPConst.NS_DC,
                                      "subject");

    PropertyOptions opt = prop.getOptions();
    if (opt.isArray()) {
      int arraySize = xp.countArrayItems(XMPConst.NS_DC,
                                         "subject");
      for (int i = 1; i <= arraySize; ++i) {
        XMPProperty item = xp.getArrayItem(XMPConst.NS_DC,
                                           "subject",
                                           i);
        String language = item.getLanguage();
        System.out.println(item);
      }
      System.err.println("subject is array");
    }
  }

}

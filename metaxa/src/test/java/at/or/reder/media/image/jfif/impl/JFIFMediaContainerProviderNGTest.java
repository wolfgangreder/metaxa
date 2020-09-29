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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
  public void testCreateContainer_1() throws Exception
  {
    byte[] soi = new byte[]{(byte) 0xff, (byte) 0xd8};
    byte[] app0 = new byte[]{(byte) 0xFF, (byte) 0xE0, (byte) 0x00, (byte) 0x10, (byte) 0x4A, (byte) 0x46, (byte) 0x49,
                             (byte) 0x46, (byte) 0x00, (byte) 0x01, (byte) 0x01, (byte) 0x01,
                             (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x48, (byte) 0x00, (byte) 0x00};
    byte[] dht_1 = new byte[]{(byte) 0xFF, (byte) 0xC4, (byte) 0x00, (byte) 0x31, (byte) 0x10, (byte) 0x00, (byte) 0x02,
                              (byte) 0x02, (byte) 0x01, (byte) 0x04, (byte) 0x01, (byte) 0x04, (byte) 0x02, (byte) 0x02,
                              (byte) 0x02, (byte) 0x02, (byte) 0x03, (byte) 0x00, (byte) 0x03, (byte) 0x01, (byte) 0x01,
                              (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x11, (byte) 0x03, (byte) 0x04, (byte) 0x10,
                              (byte) 0x12, (byte) 0x21, (byte) 0x31, (byte) 0x13, (byte) 0x20, (byte) 0x22, (byte) 0x41,
                              (byte) 0x05, (byte) 0x14, (byte) 0x30, (byte) 0x32, (byte) 0x23, (byte) 0x40, (byte) 0x06,
                              (byte) 0x42, (byte) 0x15, (byte) 0x33, (byte) 0x50, (byte) 0x24, (byte) 0x34, (byte) 0x35,
                              (byte) 0x43, (byte) 0x44};
    byte[] sos = new byte[]{(byte) 0xff, (byte) 0xda, (byte) 0x49, (byte) 0x0A, (byte) 0xA9, (byte) 0x0A, (byte) 0x19,
                            (byte) 0x0A, (byte) 0xA9, (byte) 0x09, (byte) 0x52, (byte) 0x02, (byte) 0x72, (byte) 0x54,
                            (byte) 0xA9, (byte) 0x10, (byte) 0x46, (byte) 0x45, (byte) 0x92, (byte) 0x44};
    byte[] dht_2 = new byte[]{(byte) 0xFF, (byte) 0xC4, (byte) 0x00, (byte) 0x31, (byte) 0x10, (byte) 0x00, (byte) 0x02,
                              (byte) 0x02, (byte) 0x01, (byte) 0x04, (byte) 0x01, (byte) 0x04, (byte) 0x02, (byte) 0x02,
                              (byte) 0x02, (byte) 0x02, (byte) 0x03, (byte) 0x00, (byte) 0x03, (byte) 0x01, (byte) 0x01,
                              (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x11, (byte) 0x03, (byte) 0x04, (byte) 0x10,
                              (byte) 0x12, (byte) 0x21, (byte) 0x31, (byte) 0x13, (byte) 0x20, (byte) 0x22, (byte) 0x41,
                              (byte) 0x05, (byte) 0x14, (byte) 0x30, (byte) 0x32, (byte) 0x23, (byte) 0x40, (byte) 0x06,
                              (byte) 0x42, (byte) 0x15, (byte) 0x33, (byte) 0x50, (byte) 0x24, (byte) 0x34, (byte) 0x35,
                              (byte) 0x43, (byte) 0x44};
    byte[] eoi = new byte[]{(byte) 0xff, (byte) 0xd9};
//    try ( FileOutputStream fos = new FileOutputStream(
//            "/home/wolfi/projects/metaxa/metaxa/src/test/resources/data_testCreateContainer_1.bin")) {
//      fos.write(data);
//    }
    URL u = getClass().getResource("/data_testCreateContainer_1.bin");
    MediaContainer container = new JFIFMediaContainerProvider().createContainer(u);
    assertNotNull(container);
    assertTrue(container instanceof JFIFMediaContainer);
    JFIFMediaContainer jfifContainer = (JFIFMediaContainer) container;
    int dhtCount = 0;
    for (JFIFEntry e : jfifContainer.getJFIFEntries()) {
      byte[] bufferToTest = null;
      switch (e.getMarker()) {
        case 0xffd8:
          bufferToTest = soi;
          break;
        case 0xffe0:
          bufferToTest = app0;
          break;
        case 0xffc4:
          bufferToTest = (dhtCount++) == 0 ? dht_1 : dht_2;
          break;
        case 0xffda:
          bufferToTest = sos;
          break;
        case 0xffd9:
          bufferToTest = eoi;
          break;
        default:
          fail("unexpected marker 0x" + Integer.toHexString(e.getMarker()));
      }
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      try ( InputStream is = e.getInputStream()) {
        copyStream(is,
                   bos);
      }
      assertEquals(bufferToTest,
                   bos.toByteArray());
    }

  }

  private void copyStream(InputStream is,
                          OutputStream os) throws IOException
  {
    byte[] buffer = new byte[4096];
    int read;
    while ((read = is.read(buffer)) > 0) {
      os.write(buffer,
               0,
               read);
    }
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
      try ( FileOutputStream fos = new FileOutputStream(tmpFile)) {
        for (JFIFEntry e : jfifContainer.getJFIFEntries()) {
          try ( InputStream is = e.getInputStream()) {
            MediaUtils.transferTo(is,
                                  fos);
          }
        }
      }
      try ( PositionInputStream expected = new PositionInputStream(new BufferedInputStream(u.openStream(),
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
    metaList
            = container.findContainerItem(ContainerItemGroup.METADATA,
                                          XMPMetadataContainerItem.class
            );
    assertNotNull(metaList);

    assertEquals(
            1,
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

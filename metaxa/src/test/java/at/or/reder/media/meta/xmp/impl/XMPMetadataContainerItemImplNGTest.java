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
package at.or.reder.media.meta.xmp.impl;

import at.or.reder.media.ContainerItemGroup;
import at.or.reder.media.DummyChunk;
import at.or.reder.media.MediaContainer;
import at.or.reder.media.MediaContainerFactory;
import at.or.reder.media.meta.xmp.XMPMetadataContainerItem;
import com.adobe.internal.xmp.XMPMeta;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static junit.framework.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPMetadataContainerItemImplNGTest
{

  private static URL xmpURL;
  private static URL imageURL;
  private static Path tmpDir;

  public XMPMetadataContainerItemImplNGTest()
  {
  }

  @BeforeClass
  public static void setUpClass() throws Exception
  {
    xmpURL = XMPMetadataContainerItemImplNGTest.class.getResource("/IMG_0658.xmp");
    assertNotNull(xmpURL);
    imageURL = XMPMetadataContainerItemImplNGTest.class.getResource("/IMG_0658.JPG");
    assertNotNull(imageURL);
    Path path = Paths.get(System.getProperty("buildDirectory"),
                          "tmp");
    tmpDir = Files.createDirectories(path).toRealPath();
  }

  @AfterClass
  public static void shutdownClass() throws Exception
  {
  }

  @Test
  public void testGetItemXMP()
  {
    XMPMetadataContainerItemImpl container = new XMPMetadataContainerItemImpl(null,
                                                                              new DummyChunk(xmpURL));
    XMPMeta meta = container.getItem(XMPMeta.class);
    assertNotNull(meta);
    Document doc = container.getItem(Document.class);
    assertNotNull(doc);
    Node node = container.getDOMTree();
    assertNotNull(node);
  }

  @Test
  public void testGetItemImage() throws IOException
  {
    MediaContainer container = MediaContainerFactory.createContainer(imageURL);
    XMPMetadataContainerItem xmpContainer = container.getContainerItem(ContainerItemGroup.METADATA,
                                                                       XMPMetadataContainerItem.class);
    XMPMeta meta = xmpContainer.getItem(XMPMeta.class);
    assertNotNull(meta);
    Document doc = xmpContainer.getItem(Document.class);
    assertNotNull(doc);
    Node node = xmpContainer.getDOMTree();
    assertNotNull(node);
  }

}

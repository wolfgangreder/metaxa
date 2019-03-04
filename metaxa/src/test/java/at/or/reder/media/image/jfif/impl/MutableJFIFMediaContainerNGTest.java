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

import at.or.reder.media.MediaContainer;
import at.or.reder.media.MediaContainerFactory;
import at.or.reder.media.MutableMediaContainer;
import at.or.reder.media.meta.ImageGeometrie;
import at.or.reder.media.meta.MetadataContainerItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Node;

/**
 *
 * @author Wolfgang Reder
 */
public class MutableJFIFMediaContainerNGTest
{

  private static URL imageURL;
  private static Path tmpDir;

  public MutableJFIFMediaContainerNGTest()
  {
  }

  @BeforeClass
  public static void setUpClass() throws Exception
  {
    imageURL = MutableJFIFMediaContainerNGTest.class.getResource("/IMG_0658.JPG");
    assertNotNull(imageURL);
    Path path = Paths.get(System.getProperty("buildDirectory"),
                          "tmp");
    tmpDir = Files.createDirectories(path).toRealPath();
  }

  @Test
  public void testRemoveContent() throws FileNotFoundException, IOException
  {
    MediaContainer container = MediaContainerFactory.createContainer(imageURL);
    MutableMediaContainer mutableContainer = container.createMutable();
    List<? extends MetadataContainerItem> metaItems = mutableContainer.getMetadata();
    assertNotNull(metaItems);
    assertEquals(1,
                 metaItems.size());
    for (MetadataContainerItem metaItem : metaItems) {
      assertTrue("Removing " + metaItem.toString(),
                 mutableContainer.removeContent(metaItem));
      Node xmpNode = metaItem.getDOMTree();
      System.err.println(xmpNode);
    }
    File tmpFile = File.createTempFile("tmpIMG_0658",
                                       ".jpg",
                                       tmpDir.toFile());
    tmpFile.deleteOnExit();
    try {
      try (FileOutputStream fos = new FileOutputStream(tmpFile)) {
        mutableContainer.storeTo(fos);
      }
      MediaContainer resultContainer = MediaContainerFactory.createContainer(tmpFile);
      ImageGeometrie geo = container.getImageGeometrie();
      ImageGeometrie resultGeo = resultContainer.getImageGeometrie();
      assertEquals(geo.getDensityX(),
                   resultGeo.getDensityX());
      assertEquals(geo.getDensityY(),
                   resultGeo.getDensityY());
      assertEquals(geo.getImageHeight(),
                   resultGeo.getImageHeight());
      assertEquals(geo.getImageWidth(),
                   resultGeo.getImageWidth());
      assertEquals(geo.getPixelCount(),
                   resultGeo.getPixelCount());
      assertEquals(geo.getUnit(),
                   resultGeo.getUnit());
      List<? extends MetadataContainerItem> resultMetaItems = resultContainer.getMetadata();
      assertEquals(0,
                   resultMetaItems.size());
    } finally {
      tmpFile.delete();
    }
  }

}

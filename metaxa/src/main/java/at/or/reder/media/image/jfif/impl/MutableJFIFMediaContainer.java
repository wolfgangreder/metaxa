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
import at.or.reder.media.MediaContainerItem;
import at.or.reder.media.MediaContainerProvider;
import at.or.reder.media.MimeTypes;
import at.or.reder.media.MutableMediaContainer;
import at.or.reder.media.image.jfif.JFIFEntry;
import at.or.reder.media.image.jfif.JFIFMediaContainer;
import at.or.reder.media.meta.ImageGeometrie;
import at.or.reder.media.meta.MetadataContainerItem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Wolfgang Reder
 */
final class MutableJFIFMediaContainer implements MutableMediaContainer
{

  private final JFIFMediaContainer jfifContainer;
  private final List<JFIFEntry> originalEntries = new ArrayList<>();
  private final List<JFIFEntry> toDelete = new ArrayList<>();
  private final List<JFIFEntry> toInsert = new ArrayList<>();
  private final List<MetadataContainerItem> metaItems = new ArrayList<>();

  public MutableJFIFMediaContainer(JFIFMediaContainer jfifContainer) throws IOException
  {
    this.jfifContainer = jfifContainer;
    originalEntries.addAll(jfifContainer.getJFIFEntries());
    this.metaItems.addAll(jfifContainer.getMetadata());
  }

  private MutableJFIFMediaContainer(MutableJFIFMediaContainer mc)
  {
    this.jfifContainer = mc.jfifContainer;
    this.originalEntries.addAll(jfifContainer.getJFIFEntries());
    this.toDelete.addAll(mc.toDelete);
    this.toInsert.addAll(mc.toInsert);
  }

  public List<JFIFEntry> getEntries()
  {
    List<JFIFEntry> result = originalEntries.stream().
            filter((e) -> !toDelete.contains(e)).
            collect(Collectors.toList());
    result.addAll(toInsert);
    result.sort(null);
    return result;
  }

  @Override
  public boolean removeContent(MediaContainerItem objectToRemove)
  {
    switch (objectToRemove.getGroup()) {
      case MEDIA:
      case THUMBNAIL:
        return false;
      case METADATA:
        JFIFEntry e = objectToRemove.getItem(JFIFEntry.class);
        if (e != null) {
          return toDelete.add(e);
        }
    }
    return false;
  }

  @Override
  public List<MediaContainerItem> removeAll(ContainerItemGroup representation)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<MetadataContainerItem> removeMetadata()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<BufferedImage> removeThumbnails()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public <C extends MediaContainerItem> C addContent(C objectToAdd)
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MediaContainerProvider getProvider()
  {
    return jfifContainer.getProvider();
  }

  @Override
  public URL getMediaURL()
  {
    return null;
  }

  @Override
  public String getMIMEType()
  {
    return MimeTypes.IMAGE_JPEG.getMimeType();
  }

  @Override
  public <C extends MediaContainerItem> List<? extends C> findContainerItem(ContainerItemGroup representation,
                                                                            Class<C> representationClass) throws IOException
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MutableMediaContainer createMutable()
  {
    return new MutableJFIFMediaContainer(this);
  }

  @Override
  public ImageGeometrie getImageGeometrie()
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}

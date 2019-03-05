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
import static at.or.reder.media.ContainerItemGroup.MEDIA;
import static at.or.reder.media.ContainerItemGroup.METADATA;
import static at.or.reder.media.ContainerItemGroup.THUMBNAIL;
import at.or.reder.media.MediaContainerItem;
import at.or.reder.media.MutableMediaContainer;
import at.or.reder.media.image.jfif.JFIFEntry;
import at.or.reder.media.image.jfif.JFIFMarker;
import at.or.reder.media.image.jfif.JFIFMediaContainer;
import at.or.reder.media.io.InputStreamMediaJunk;
import at.or.reder.media.meta.MetadataContainerItem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 *
 * @author Wolfgang Reder
 */
final class MutableJFIFMediaContainer extends JFIFMediaContainerImpl implements MutableMediaContainer, JFIFMediaContainer
{

  private final JFIFMediaContainer jfifContainer;
  private final List<JFIFEntry> toDelete = new ArrayList<>();
  private final List<JFIFEntry> toInsert = new ArrayList<>();

  public MutableJFIFMediaContainer(JFIFMediaContainer jfifContainer) throws IOException
  {
    super((JFIFMediaContainerProvider) jfifContainer.getProvider(),
          jfifContainer.getMediaURL(),
          jfifContainer.getJFIFEntries(),
          jfifContainer.getMetadata());
    this.jfifContainer = jfifContainer;
  }

  private MutableJFIFMediaContainer(MutableJFIFMediaContainer mc) throws IOException
  {
    super((JFIFMediaContainerProvider) mc.getProvider(),
          mc.getMediaURL(),
          mc.getJFIFEntries(),
          mc.getMetadata());
    this.jfifContainer = mc.jfifContainer;
    this.toDelete.addAll(mc.toDelete);
    this.toInsert.addAll(mc.toInsert);
  }

  @Override
  public List<? extends MetadataContainerItem> getMetadata() throws IOException
  {
    return super.getMetadata(); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<JFIFEntry> getJFIFEntries()
  {
    List<JFIFEntry> result = super.getJFIFEntries().stream().
            filter((e) -> !toDelete.contains(e)).
            collect(Collectors.toList());
    result.addAll(toInsert);
    result.sort(null);
    return result;
  }

  private boolean removeMetadata(MediaContainerItem objectToRemove) throws IOException
  {
    int foundIndex = metaItems.indexOf(objectToRemove);
    boolean result = false;
    if (foundIndex >= 0) {
      MediaContainerItem found = metaItems.get(foundIndex);
      JFIFEntry chunk = found.getItem(JFIFEntry.class);
      if (toDelete.indexOf(chunk) == -1) {
        foundIndex = entries.indexOf(chunk);
        if (foundIndex >= 0) {
          toDelete.add(chunk);
          result = true;
        }
        foundIndex = toInsert.indexOf(chunk);
        if (foundIndex >= 0) {
          toInsert.remove(chunk);
          result = true;
        }
      }
    }
    return result;
  }

  @Override
  public boolean removeContent(MediaContainerItem objectToRemove) throws IOException
  {
    switch (objectToRemove.getGroup()) {
      case MEDIA:
      case THUMBNAIL:
        return false;
      case METADATA:
        return removeMetadata(objectToRemove);
    }
    return false;
  }

  @Override
  public List<MediaContainerItem> removeAll(ContainerItemGroup representation) throws IOException
  {
    List<MediaContainerItem> foundItems = metaItems.stream().
            filter((me) -> me.getGroup() == representation).collect(Collectors.toList());
    ListIterator<MediaContainerItem> iter = foundItems.listIterator();
    while (iter.hasNext()) {
      if (removeContent(iter.next())) {
        iter.remove();
      }
    }
    return foundItems;
  }

  @Override
  public List<MetadataContainerItem> removeMetadata() throws IOException
  {
    List<MediaContainerItem> tmpList = removeAll(ContainerItemGroup.METADATA);
    return tmpList.stream().
            filter((e) -> e instanceof MetadataContainerItem).
            map(MetadataContainerItem.class::cast).
            collect(Collectors.toList());
  }

  @Override
  public List<BufferedImage> removeThumbnails() throws IOException
  {
    List<MediaContainerItem> tmpList = removeAll(ContainerItemGroup.THUMBNAIL);
    return tmpList.stream().
            filter((e) -> e instanceof MetadataContainerItem).
            map(BufferedImage.class::cast).
            collect(Collectors.toList());
  }

  @Override
  public <C extends MediaContainerItem> C addContent(C objectToAdd)
  {
    if (objectToAdd.getGroup() != ContainerItemGroup.METADATA || !MetadataContainerItem.class.isInstance(objectToAdd)) {
      throw new UnsupportedOperationException("Not supported yet.");
    }
    MetadataContainerItem metaItem = MetadataContainerItem.class.cast(objectToAdd);
    WrapperJFIFEntry entry = new WrapperJFIFEntry(JFIFMarker.APP1,
                                                  objectToAdd.getURI(),
                                                  new InputStreamMediaJunk(JFIFMarker.APP1.getName(),
                                                                           -1,
                                                                           objectToAdd.getURI(),
                                                                           () -> objectToAdd.getItem(InputStream.class)));
//    metaItems.add(metaItem);
    toInsert.add(entry);
    return objectToAdd;
  }

}

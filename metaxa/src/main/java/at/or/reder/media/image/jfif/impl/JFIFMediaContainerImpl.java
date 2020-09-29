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
import at.or.reder.media.image.jfif.JFIFMarker;
import at.or.reder.media.image.jfif.JFIFMediaContainer;
import at.or.reder.media.meta.DefaultImageGeometrie;
import at.or.reder.media.meta.ImageGeometrie;
import at.or.reder.media.meta.MetadataContainerItem;
import at.or.reder.media.meta.PixelUnit;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
class JFIFMediaContainerImpl implements JFIFMediaContainer
{

  private final URL url;
  protected final List<JFIFEntry> entries;
  private final JFIFMediaContainerProvider provider;
  protected final List<MetadataContainerItem> metaItems;
  private final ImageGeometrie geo;

  public JFIFMediaContainerImpl(JFIFMediaContainerProvider provider,
                                URL url,
                                Collection<? extends JFIFEntry> entries,
                                Collection<? extends MetadataContainerItem> metaItems) throws IOException
  {
    this.provider = Objects.requireNonNull(provider,
                                           "provider is null");
    this.url = url;
    this.entries = Collections.unmodifiableList(new ArrayList<>(entries));
    if (metaItems == null || metaItems.isEmpty()) {
      this.metaItems = Collections.emptyList();
    } else {
      this.metaItems = Collections.unmodifiableList(new ArrayList<>(metaItems));
    }
    APPxEntry app0 = findEntry(JFIFMarker.APP0,
                               APPxEntry.class);
    SOFEntry sof = findEntry(SOFEntry.class);
    Dimension dim = sof != null ? sof.getImageDimension() : new Dimension(0,
                                                                          0);
    PixelUnit unit = PixelUnit.NONE;
    int xdensity = -1;
    int ydensity = -1;
    if (app0 != null) {
      ByteBuffer buffer = ByteBuffer.allocate(app0.getLength());
      buffer.order(ByteOrder.BIG_ENDIAN);
      try ( ReadableByteChannel ch = Channels.newChannel(app0.getDataStream())) {
        ch.read(buffer);
        buffer.rewind();
        short version = buffer.getShort();
        switch (buffer.get()) {
          case 1:
            unit = PixelUnit.DPI;
            break;
          case 2:
            unit = PixelUnit.DPICM;
            break;
          default:
            unit = PixelUnit.NONE;
        }
        short a = buffer.getShort();
        if (a != -1) {
          xdensity = a & 0xffff;
        }
        a = buffer.getShort();
        if (a != -1) {
          ydensity = a & 0xffff;
        }
      }
    }
    geo = new DefaultImageGeometrie(unit,
                                    xdensity,
                                    ydensity,
                                    dim.width,
                                    dim.height);
  }

  private <C extends JFIFEntry> C findEntry(JFIFMarker marker,
                                            Class<? extends C> clazz)
  {
    return entries.stream().
            filter(clazz::isInstance).
            filter((JFIFEntry e) -> e.getMarker() == marker.getMarker()).
            map(clazz::cast).
            findFirst().
            orElse(null);
  }

  private <C extends JFIFEntry> C findEntry(Class<? extends C> clazz)
  {
    return entries.stream().
            filter(clazz::isInstance).
            map(clazz::cast).
            findFirst().
            orElse(null);
  }

  @Override
  public MediaContainerProvider getProvider()
  {
    return provider;
  }

  @Override
  public URL getMediaURL()
  {
    return url;
  }

  @Override
  public String getMIMEType()
  {
    return MimeTypes.IMAGE_JPEG.getMimeType();
  }

  @Override
  public List<JFIFEntry> getJFIFEntries()
  {
    return entries;
  }

  private <C extends MediaContainerItem> List<C> mediaRepresentation(Class<? extends C> representationClass) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  @SuppressWarnings({"unchecked"})
  private <C extends MetadataContainerItem> List<C> metaRepresentation(Class<? extends MetadataContainerItem> representationClass)
          throws IOException
  {
    List<C> tmp = new ArrayList<>();
    for (MetadataContainerItem i : metaItems) {
      if (representationClass.isInstance(i)) {
        tmp.add((C) i);
      }
    }
    return tmp;
  }

  private <C extends MediaContainerItem> List<C> thumbnailRepresentation(Class<? extends C> representationClass) throws
          IOException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public <C extends MediaContainerItem> List<? extends C> findContainerItem(ContainerItemGroup representation,
                                                                            Class<C> representationClass) throws
          IOException
  {
    switch (representation) {
      case MEDIA:
        return mediaRepresentation(representationClass);
      case METADATA:
        if (MetadataContainerItem.class.isAssignableFrom(representationClass)) {
          Class<? extends MetadataContainerItem> clazz = representationClass.asSubclass(MetadataContainerItem.class);
          return metaRepresentation(clazz);
        }
      case THUMBNAIL:
        return thumbnailRepresentation(representationClass);
      default:
        return Collections.emptyList();
    }
  }

  @Override
  public MutableMediaContainer createMutable() throws IOException
  {
    return new MutableJFIFMediaContainer(this);
  }

  @Override
  public ImageGeometrie getImageGeometrie()
  {
    return geo;
  }

}

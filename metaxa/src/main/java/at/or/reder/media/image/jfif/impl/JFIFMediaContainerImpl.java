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
import at.or.reder.media.image.jfif.JFIFEntry;
import at.or.reder.media.image.jfif.JFIFMediaContainer;
import at.or.reder.media.meta.MetadataContainerItem;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author Wolfgang Reder
 */
final class JFIFMediaContainerImpl implements JFIFMediaContainer
{

  private final URL url;
  private final List<JFIFEntry> entries;
  private final JFIFMediaContainerProvider provider;
  private final List<MetadataContainerItem> metaItems;

  public JFIFMediaContainerImpl(JFIFMediaContainerProvider provider,
                                URL url,
                                Collection<? extends JFIFEntry> entries,
                                Collection<? extends MetadataContainerItem> metaItems)
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

  private <C extends MetadataContainerItem> List<MetadataContainerItem> metaRepresentation(Class<? extends C> representationClass)
          throws IOException
  {
    List<MetadataContainerItem> tmp = metaItems.stream().
            filter(representationClass::isInstance).
            collect(Collectors.toList());
    return tmp;
  }

  private <C extends MediaContainerItem> List<C> thumbnailRepresentation(Class<? extends C> representationClass) throws
          IOException
  {
    throw new UnsupportedOperationException();
  }

  @Override
  public <C extends MediaContainerItem> List<MediaContainerItem> getContainerItem(ContainerItemGroup representation,
                                                                                  Class<? extends C> representationClass) throws
          IOException
  {
    switch (representation) {
      case MEDIA:
        return mediaRepresentation(representationClass);
      case METADATA:
        if (MetadataContainerItem.class.isAssignableFrom(representationClass)) {
          return metaRepresentation(MetadataContainerItem.class.cast(representationClass));
        }
      case THUMBNAIL:
        return thumbnailRepresentation(representationClass);
      default:
        return Collections.emptyList();
    }
  }

}

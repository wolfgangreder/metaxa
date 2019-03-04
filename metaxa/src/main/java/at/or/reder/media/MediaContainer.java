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
package at.or.reder.media;

import at.or.reder.media.meta.ImageGeometrie;
import at.or.reder.media.meta.MetadataContainerItem;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 * Represents a Media Entity wich contains Metadata (e.g. an Image with Exif).
 *
 * @author Wolfgang Reder
 */
public interface MediaContainer
{

  /**
   * Returns the Provider for this class
   *
   * @return providerclass
   */
  public MediaContainerProvider getProvider();

  /**
   * The origin of the media
   *
   * @return URL of the Origin, or {@code null} if not known
   */
  public URL getMediaURL();

  /**
   * Returns the actual MIME Type of this container.
   *
   * @return mimeType of the container
   */
  public String getMIMEType();

  /**
   * Get a representaion of the Media.The Representation can be,for example a {@link java.awt.Image} or a
   * {@link at.or.reder.media.meta.MetadataContainerItem}
   *
   * @param <C> Representation class of item
   * @param itemGroup the kind of item
   * @param representationClass the representing item class
   * @return List of found items, or empty list if nothing is found an empty list is returned
   * @throws IOException on IOError
   */
  public <C extends MediaContainerItem> List<? extends C> findContainerItem(ContainerItemGroup itemGroup,
                                                                            Class<C> representationClass) throws
          IOException;

  public default <C extends MediaContainerItem> C getMainMedia(Class<? extends C> representationClass) throws IOException
  {
    return (C) findContainerItem(ContainerItemGroup.MEDIA,
                                 representationClass).stream().
            findFirst().
            orElse(null);
  }

  public default List<? extends MetadataContainerItem> getMetadata() throws IOException
  {
    return findContainerItem(ContainerItemGroup.METADATA,
                             MetadataContainerItem.class);
  }

  public default <C extends MediaContainerItem> C getContainerItem(ContainerItemGroup itemGroup,
                                                                   Class<C> representationClass) throws IOException
  {
    return findContainerItem(itemGroup,
                             representationClass).stream().
            findFirst().
            orElse(null);
  }

  public MutableMediaContainer createMutable() throws IOException;

  public ImageGeometrie getImageGeometrie();

  public void storeTo(OutputStream os) throws IOException;

}

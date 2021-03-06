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

import at.or.reder.media.meta.MetadataContainerItem;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public interface MutableMediaContainer extends MediaContainer
{

  /**
   * Removes the object from the container.
   *
   * @param objectToRemove objectToRemove
   * @return {@code true} if the object was removed.
   * @throws java.io.IOException on IO Error
   */
  public boolean removeContent(MediaContainerItem objectToRemove) throws IOException;

  /**
   * Removes all items of a given ContainerItemGroup.
   *
   * @param representation itemGroup
   * @return removedItems
   * @throws java.io.IOException
   */
  public List<MediaContainerItem> removeAll(ContainerItemGroup representation) throws IOException;

  /**
   * Removes all Metadata.
   *
   * @return Metadata removed.
   * @throws java.io.IOException
   */
  public List<MetadataContainerItem> removeMetadata() throws IOException;

  public List<BufferedImage> removeThumbnails() throws IOException;

  /**
   * Add a new Item to the container. If only one instance of this item can be stored in the container (e.g. main image in jpeg),
   * the old content is returned.
   *
   * @param <C> Item class
   * @param objectToAdd newItem
   * @return the replaces object
   */
  public <C extends MediaContainerItem> C addContent(C objectToAdd);

  public boolean isStructureFixed();

  public void setStructureFixed(boolean fixed);

}

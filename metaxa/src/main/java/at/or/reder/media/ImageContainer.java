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

import at.or.reder.media.MediaContainer;
import at.or.reder.meta.MetadataContainer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public interface ImageContainer extends MediaContainer
{

  public default BufferedImage getImage() throws IOException
  {
    return getRepresentation(MediaRepresentation.MEDIA,
                             BufferedImage.class).stream().
            findFirst().
            orElse(null);
  }

  public default BufferedImage getThumbnail() throws IOException
  {
    return getRepresentation(MediaRepresentation.THUMBNAIL,
                             BufferedImage.class).stream().
            findFirst().
            orElse(null);
  }

  public default List<MetadataContainer> getMetadataContainer() throws IOException
  {
    return getRepresentation(MediaRepresentation.METADATA,
                             MetadataContainer.class);
  }

}

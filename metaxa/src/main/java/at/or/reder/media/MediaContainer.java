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

import java.io.IOException;
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
   * The origin of the media
   *
   * @return URL of the Origin, or {@code null} if not known
   */
  public URL getMediaURL();

  /**
   *
   * @return
   */
  public String getMIMEType();

  /**
   * Get a representaion of the Media.The Representation can be,for example a {@link java.awt.image.Image} or a {@link at.or.reder.meta.elements.MetaDataContainer}
   *
   * @param <C>
   * @param representation
   * @param representationClass
   * @return
   * @throws IOException
   */
  public <C> List<C> getRepresentation(MediaRepresentation representation,
                                       Class<? extends C> representationClass) throws IOException;

}

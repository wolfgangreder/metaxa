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
package at.or.reder.meta.container;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Wolfgang Reder
 */
public interface ContainerEntry
{

  /**
   * Name of the Entity
   *
   * @return Name of Marker
   */
  public String getName();

  /**
   * Datalength of the Segment. Net length of data (without length,externsionname).
   *
   * @return length
   */
  public int getLength();

  /**
   * Name of the APPx extension (ex. Exif,....)
   *
   * @return extension Name
   */
  public String getExtensionName();

  /**
   * Datastream for the Segment <em>with</em> marker and length.
   *
   * @return Stream with data.
   * @throws java.io.IOException on IOError
   */
  public InputStream getInputStream() throws IOException;

  public InputStream getDataStream() throws IOException;

  /**
   * Offset of Segment with the original stream
   *
   * @return offset
   */
  public long getOffset();

  public long getDataOffset();

  public <C> C getDataRepresentation(Class<? extends C> representationClass) throws IOException;

}

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
package at.or.reder.meta.container.jfif;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Wolfgang Reder
 */
public interface JFIFDirectory
{

  /**
   * List of Entries in the order they have been read.
   *
   * @return list of entries.
   */
  public List<JFIFEntry> getEntries();

  /**
   * List of Entries in the the order they have to written.
   *
   * @return
   */
  public default List<JFIFEntry> getNormalizedEntries()
  {
    return getEntries().stream().
            sorted().
            collect(Collectors.toList());
  }

}

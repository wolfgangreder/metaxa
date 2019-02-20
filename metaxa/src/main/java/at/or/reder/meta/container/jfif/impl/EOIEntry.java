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
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.PositionInputStream;

/**
 *
 * @author Wolfgang Reder
 */
public final class EOIEntry extends AbstractJFIFEntry
{

  public static EOIEntry newInstance(PositionInputStream is,
                                     int marker)
  {
    return new EOIEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        is.getPosition() - 2);
  }

  public EOIEntry(SegmentSource source,
                  int marker,
                  long offset)
  {
    super(source,
          marker,
          "EOI",
          0,
          offset,
          null);
  }

  @Override
  public int getPrefixLength()
  {
    return 0;
  }

}

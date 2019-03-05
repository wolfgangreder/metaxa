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
package at.or.reder.media.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public final class ProxyInputStream extends InputStream
{

  private final List<InputStream> streams;
  private InputStream currentStream;
  private final Iterator<InputStream> iter;

  public ProxyInputStream(List<InputStream> streams)
  {
    this.streams = new ArrayList<>(streams);
    iter = this.streams.iterator();
    currentStream = iter.next();
  }

  public ProxyInputStream(InputStream... streams)
  {
    this(Arrays.asList(streams));
  }

  @Override
  public int read() throws IOException
  {
    int result = currentStream.read();
    while (result == -1 && iter.hasNext()) {
      currentStream = iter.next();
      result = currentStream.read();
    }
    return result;
  }

}

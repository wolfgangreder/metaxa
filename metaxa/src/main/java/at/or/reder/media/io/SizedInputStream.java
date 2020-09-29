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
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
public final class SizedInputStream extends InputStream
{

  private int remaining;
  private final InputStream w;

  public SizedInputStream(InputStream w,
                          int size)
  {
    this.remaining = size;
    this.w = Objects.requireNonNull(w,
                                    "stream is null");
  }

  public int getRemaining()
  {
    return remaining;
  }

  @Override
  public int read() throws IOException
  {
    if (remaining <= 0) {
      return -1;
    }
    int result = w.read();
    if (result >= 0) {
      --remaining;
    }
    return result;
  }

  @Override
  public void close() throws IOException
  {
    w.close();
  }

  @Override
  public int available() throws IOException
  {
    return Math.min(remaining,
                    w.available());
  }

  @Override
  public long skip(long n) throws IOException
  {
    if (remaining <= 0) {
      return 0;
    }
    return w.skip(Math.min(remaining,
                           n));
  }

  @Override
  public int read(byte[] b,
                  int off,
                  int len) throws IOException
  {
    if (remaining <= 0) {
      return -1;
    }
    int result = w.read(b,
                        off,
                        Math.min(remaining,
                                 len));
    remaining -= result;
    return result;
  }

  @Override
  public int read(byte[] b) throws IOException
  {
    if (remaining <= 0) {
      return -1;
    }
    int result = w.read(b,
                        0,
                        Math.min(b.length,
                                 remaining));
    remaining -= result;
    return result;
  }

}

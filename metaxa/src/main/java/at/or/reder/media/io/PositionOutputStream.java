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
import java.io.OutputStream;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
public class PositionOutputStream extends OutputStream
{

  private long position;
  private final OutputStream w;

  public PositionOutputStream(OutputStream w)
  {
    this.w = Objects.requireNonNull(w,
                                    "stream is null");
  }

  public long getPosition()
  {
    return position;
  }

  @Override
  public void write(int b) throws IOException
  {
    position++;
    w.write(b);
  }

  @Override
  public void close() throws IOException
  {
    position = -1;
    w.close();
  }

  @Override
  public void flush() throws IOException
  {
    w.flush();
  }

  @Override
  public void write(byte[] b,
                    int off,
                    int len) throws IOException
  {
    position += len;
    w.write(b,
            off,
            len);
  }

  @Override
  public void write(byte[] b) throws IOException
  {
    position += b.length;
    w.write(b);
  }

  private String formatLong(long l)
  {
    StringBuilder b = new StringBuilder();
    b.append(Long.toString(l));
    b.append(" (0x");
    String tmp = Long.toHexString(l);
    int i = 0;
//    for (; i < (8 - tmp.length()); ++i) {
//      b.append('0');
////      if (i == 3) {
////        b.append(' ');
////      }
//    }
    for (int j = 0; j < tmp.length(); ++j, ++i) {
      b.append(tmp.charAt(j));
//      if (i == 3) {
//        b.append(' ');
//      }
    }
    b.append(')');
    return b.toString();
  }

  @Override
  public String toString()
  {
    return "PositionOutputStream{" + "position=" + formatLong(position) + '}';
  }

}

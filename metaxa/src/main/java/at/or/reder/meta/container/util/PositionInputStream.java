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
package at.or.reder.meta.container.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

/**
 *
 * @author Wolfgang Reder
 */
public final class PositionInputStream extends InputStream
{

  private final URL url;
  private long position;
  private long markedPosition = -1;
  private final InputStream w;
  private final int rewindStep;
  private ByteRingBuffer rewindBuffer;
  private InputStream rewindStream;

  public PositionInputStream(InputStream is,
                             int backStep)
  {
    this(null,
         is,
         backStep);
  }

  public PositionInputStream(URL u,
                             long offset,
                             int backStep) throws IOException
  {
    this(u,
         u.openStream(),
         backStep);
    skip(offset);
  }

  public PositionInputStream(URL u,
                             InputStream w,
                             int backStep)
  {
    this.rewindStep = backStep;
    this.url = u;
    if (w.markSupported()) {
      this.w = w;
    } else {
      this.w = new BufferedInputStream(w,
                                       64 * 1024);
    }
  }

  public URL getURL()
  {
    return url;
  }

  public long getPosition()
  {
    return position;
  }

  public long getMarkedPosition()
  {
    return markedPosition;
  }

  @Override
  public String toString()
  {
    if (markedPosition >= 0) {
      return "PositionInputStream{" + "position=" + MetaUtils.formatLong(position) + ", markedPosition=" + MetaUtils.formatLong(
              markedPosition) + '}';
    } else {
      return "PositionInputStream{" + "position=" + MetaUtils.formatLong(position) + '}';

    }
  }

  public boolean isRewindReadEnabled()
  {
    return rewindBuffer != null;
  }

  public boolean setRewindReadEnabled(boolean en)
  {
    if (rewindBuffer == null && rewindStream == null && en) {
      rewindBuffer = new ByteRingBuffer(rewindStep);
    } else {
      rewindBuffer = null;
    }
    return isRewindReadEnabled();
  }

  public void rewind()
  {
    if (rewindBuffer != null && !rewindBuffer.isEmpty()) {
      rewindStream = rewindBuffer;
    }
    rewindBuffer = null;
  }

  @Override
  public int read() throws IOException
  {
    int result;
    if (rewindStream != null) {
      result = rewindStream.read();
      if (result == -1) {
        rewindStream = null;
      } else {
        return result;
      }
    }
    result = w.read();
    if (result >= 0) {
      if (rewindBuffer != null) {
        rewindBuffer.push((byte) result);
      }
      ++position;
    }
    return result;
  }

  public int fillBuffer(int toRead,
                        ByteBuffer buffer) throws IOException
  {
    buffer.rewind();
    int read = read(buffer.array(),
                    0,
                    toRead);
    if (read != -1) {
      buffer.limit(read);
    } else {
      buffer.limit(0);
    }
    return read;
  }

  @Override
  public boolean markSupported()
  {
    return rewindStream == null && w.markSupported();
  }

  @Override
  public synchronized void reset() throws IOException
  {
    if (!markSupported()) {
      throw new IOException("mark/reset not supported");
    }
    position = markedPosition;
    markedPosition = -1;
    setRewindReadEnabled(false);
    w.reset();
  }

  @Override
  public synchronized void mark(int readlimit)
  {
    if (markSupported()) {
      w.mark(readlimit);
      markedPosition = position;
    }
  }

  @Override
  public void close() throws IOException
  {
    rewindStream = null;
    rewindBuffer = null;
    w.close();
  }

  @Override
  public int available() throws IOException
  {
    int result = rewindStream != null ? rewindStream.available() : 0;
    result += w.available();
    return result;
  }

  @Override
  public long skip(long n) throws IOException
  {
    long s;
    long result = 0;
    if (rewindStream != null) {
      n -= rewindStream.skip(n);
      if (rewindStream.available() == 0) {
        rewindStream = null;
      }
    }
    do {
      s = w.skip(n);
      position += s;
      result += s;
      n -= s;
    } while (n > 0 && s > 0);
    setRewindReadEnabled(false);
    return result;
  }

  @Override
  public int readNBytes(byte[] b,
                        int off,
                        int len) throws IOException
  {
    int result = 0;
    if (rewindStream != null) {
      result = rewindStream.readNBytes(b,
                                       off,
                                       len);
    }
    result = w.readNBytes(b,
                          off + result,
                          len - result);
    position += result;
    if (rewindBuffer != null) {
      rewindBuffer.push(b,
                        off,
                        result);
    }
    return result;
  }

  @Override
  public byte[] readNBytes(int len) throws IOException
  {
    if (rewindStream != null) {
      assert rewindBuffer == null : "backBuffer is not null";
      ByteArrayOutputStream bos = new ByteArrayOutputStream(len);
      byte[] tmp = rewindStream.readNBytes(len);
      bos.write(tmp);
      len -= tmp.length;
      if (rewindStream.available() == 0) {
        rewindStream = null;
      }
      tmp = w.readNBytes(len);
      bos.write(tmp);
      position += len;
      return bos.toByteArray();
    } else {
      byte[] result = w.readNBytes(len);
      if (rewindBuffer != null) {
        rewindBuffer.push(result);
      }
      position += result.length;
      return result;
    }
  }

  @Override
  public byte[] readAllBytes() throws IOException
  {
    if (rewindStream != null) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] tmp = rewindStream.readAllBytes();
      rewindStream = null;
      bos.write(tmp);
      tmp = w.readAllBytes();
      bos.write(tmp);
      return bos.toByteArray();
    } else {
      byte[] result = w.readAllBytes();
      if (rewindBuffer != null) {
        rewindBuffer.push(result);
      }
      position += result.length;
      return result;
    }
  }

  @Override
  public int read(byte[] b,
                  int off,
                  int len) throws IOException
  {
    if (rewindStream != null) {
      int result = rewindStream.read(b,
                                     off,
                                     len);
      if (rewindStream.available() == 0) {
        rewindStream = null;
      }
      return result;
    } else {
      int result = w.read(b,
                          off,
                          len);
      if (rewindBuffer != null) {

      }
      position += result;
      return result;
    }
  }

  @Override
  public int read(byte[] b) throws IOException
  {
    if (rewindStream != null) {
      int result = rewindStream.read(b);
      if (rewindStream.available() == 0) {
        rewindStream = null;
      }
      return result;
    } else {
      int result = w.read(b);
      if (rewindBuffer != null) {
        rewindBuffer.push(b,
                          0,
                          result);
      }
      position += result;
      return result;
    }
  }

}

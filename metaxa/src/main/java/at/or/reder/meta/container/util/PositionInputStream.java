/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
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
  private InputStream backStream;

  public PositionInputStream(InputStream is)
  {
    this(null,
         is);
  }

  public PositionInputStream(URL u,
                             long offset) throws IOException
  {
    this(u,
         u.openStream());
    skip(offset);
  }

  public PositionInputStream(URL u,
                             InputStream w)
  {
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
    if (markedPosition >= 0) {
      return "PositionInputStream{" + "position=" + formatLong(position) + ", markedPosition=" + formatLong(markedPosition) + '}';
    } else {
      return "PositionInputStream{" + "position=" + formatLong(position) + '}';

    }
  }

  public boolean setBackBytes(byte[] b)
  {
    if (markedPosition == -1 && b != null && b.length > 0) {
      backStream = new ByteArrayInputStream(b);
      position -= b.length;
      return true;
    } else {
      backStream = null;
      return false;
    }
  }

  @Override
  public int read() throws IOException
  {
    int result;
    if (backStream != null) {
      result = backStream.read();
      if (result == -1) {
        backStream = null;
      } else {
        return result;
      }
    }
    result = w.read();
    if (result >= 0) {
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
    return backStream == null && w.markSupported();
  }

  @Override
  public synchronized void reset() throws IOException
  {
    if (!markSupported()) {
      throw new IOException("mark/reset not supported");
    }
    position = markedPosition;
    markedPosition = -1;
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
    backStream = null;
    w.close();
  }

  @Override
  public int available() throws IOException
  {
    int result = backStream != null ? backStream.available() : 0;
    result += w.available();
    return result;
  }

  @Override
  public long skip(long n) throws IOException
  {
    long s;
    long result = 0;
    if (backStream != null) {
      n -= backStream.skip(n);
      if (backStream.available() == 0) {
        backStream = null;
      }
    }
    do {
      s = w.skip(n);
      position += s;
      result += s;
      n -= s;
    } while (n > 0 && s > 0);
    return result;
  }

  @Override
  public int readNBytes(byte[] b,
                        int off,
                        int len) throws IOException
  {
    int result = 0;
    if (backStream != null) {
      result = backStream.readNBytes(b,
                                     off,
                                     len);
    }
    result = w.readNBytes(b,
                          off + result,
                          len - result);
    position += result;
    return result;
  }

  @Override
  public byte[] readNBytes(int len) throws IOException
  {
    if (backStream != null) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(len);
      byte[] tmp = backStream.readNBytes(len);
      bos.write(tmp);
      len -= tmp.length;
      if (backStream.available() == 0) {
        backStream = null;
      }
      tmp = w.readNBytes(len);
      bos.write(tmp);
      return bos.toByteArray();
    } else {
      byte[] result = w.readNBytes(len);
      position += result.length;
      return result;
    }
  }

  @Override
  public byte[] readAllBytes() throws IOException
  {
    if (backStream != null) {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] tmp = backStream.readAllBytes();
      backStream = null;
      bos.write(tmp);
      tmp = w.readAllBytes();
      bos.write(tmp);
      return bos.toByteArray();
    } else {
      byte[] result = w.readAllBytes();
      position += result.length;
      return result;
    }
  }

  @Override
  public int read(byte[] b,
                  int off,
                  int len) throws IOException
  {
    if (backStream != null) {
      int result = backStream.read(b,
                                   off,
                                   len);
      if (backStream.available() == 0) {
        backStream = null;
      }
      return result;
    } else {
      int result = w.read(b,
                          off,
                          len);
      position += result;
      return result;
    }
  }

  @Override
  public int read(byte[] b) throws IOException
  {
    if (backStream != null) {
      int result = backStream.read(b);
      if (backStream.available() == 0) {
        backStream = null;
      }
      return result;
    } else {
      int result = w.read(b);
      position += result;
      return result;
    }
  }

}

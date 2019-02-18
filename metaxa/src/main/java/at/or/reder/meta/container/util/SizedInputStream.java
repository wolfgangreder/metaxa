/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.util;

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
  public int readNBytes(byte[] b,
                        int off,
                        int len) throws IOException
  {
    if (remaining <= 0) {
      return 0;
    }
    int read = w.readNBytes(b,
                            off,
                            Math.min(remaining,
                                     len));
    remaining -= read;
    return read;
  }

  @Override
  public byte[] readNBytes(int len) throws IOException
  {
    if (remaining <= 0) {
      return new byte[0];
    }
    byte[] tmp = w.readNBytes(Math.min(remaining,
                                       len));
    remaining -= tmp.length;
    return tmp;
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
    int result = w.read(b);
    remaining -= result;
    return result;
  }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.util;

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

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
package at.or.reder.media.util;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * @author Wolfgang Reder
 */
public final class ByteRingBuffer extends InputStream
{

  private final int maxSize;
  private int size;
  private int end;
  private int start;
  private final byte[] data;

  public ByteRingBuffer(int maxSize)
  {
    if (maxSize < 1) {
      throw new IllegalArgumentException("maxSize<1");
    }
    this.maxSize = maxSize;
    data = new byte[maxSize];
    size = 0;
    end = 0;
    start = 0;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  public int getMaxSize()
  {
    return maxSize;
  }

  public void push(byte c)
  {
    if (size < maxSize) {
      size = size + 1;
    } else {
      start = (start + 1) % maxSize;
    }
    data[end] = c;
    end = (end + 1) % maxSize;
  }

  public void push(byte[] b)
  {
    push(b,
         0,
         b.length);
  }

  public void push(byte[] b,
                   int offset,
                   int length)
  {
    if (offset + length > b.length) {
      throw new IndexOutOfBoundsException();
    }
    if (length <= maxSize) {
      for (int i = 0; i < length; ++i) {
        push(b[i + offset]);
      }
    } else {
      System.arraycopy(b,
                       offset,
                       data,
                       0,
                       maxSize);
      end = 0;
      size = maxSize;
      start = 0;
    }
  }

  public void clear()
  {
    Arrays.fill(data,
                (byte) 0);
    size = 0;
    end = 0;
    start = 0;
  }

  public int getSize()
  {
    return size;
  }

  public int fillByteBuffer(ByteBuffer buffer)
  {
    int result = 0;
    if (size == maxSize) {
      for (; result < maxSize; ++result) {
        buffer.put(data[(end + result) % maxSize]);
      }
    } else {
      for (int i = start; result < size && buffer.hasRemaining(); ++result, i = (i + 1) % maxSize) {
        buffer.put(data[i]);
      }
    }
    return result;
  }

  public byte[] fillArray(byte[] arrayIn)
  {
    byte[] result;
    if (arrayIn != null && arrayIn.length >= size) {
      result = arrayIn;
    } else {
      result = new byte[size];
    }
    if (size == maxSize) {
      for (int i = 0; i < maxSize; ++i) {
        result[i] = data[(end + i) % maxSize];
      }
    } else {
      for (int i = start; i != end; i = (i + 1) % maxSize) {
        result[i] = data[i];
      }
    }
    return result;
  }

  @Override
  public int read()
  {
    int result = -1;
    if (size > 0) {
      result = data[start] & 0xff;
      start = (start + 1) % maxSize;
      --size;
    }
    return result;
  }

  @Override
  public int available()
  {
    return size;
  }

  @Override
  public long skip(long n)
  {
    long result = 0;
    int tmp;
    while ((tmp = read()) != -1 && result < n) {
      ++result;
    }
    return result;
  }

}

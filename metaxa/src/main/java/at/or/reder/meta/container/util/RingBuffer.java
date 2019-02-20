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

import java.util.Arrays;

/**
 *
 * @author Wolfgang Reder
 */
public final class RingBuffer<T>
{

  private final int maxSize;
  private int size;
  private int end;
  private final Object[] data;

  public RingBuffer(int maxSize)
  {
    if (maxSize < 1) {
      throw new IllegalArgumentException("maxSize<1");
    }
    this.maxSize = maxSize;
    data = new Object[maxSize];
    size = 0;
    end = 0;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  public int getMaxSize()
  {
    return maxSize;
  }

  public void push(T c)
  {
    size = Math.min(maxSize,
                    size + 1);
    data[end] = c;
    end = (end + 1) % maxSize;
  }

  public void clear()
  {
    Arrays.fill(data,
                null);
    size = 0;
    end = 0;
  }

  public int getSize()
  {
    return size;
  }

  public T[] fillArray(T[] arrayIn)
  {
    T[] result;
    Class<?> componentClass = arrayIn.getClass().getComponentType();
    if (arrayIn.length >= size) {
      result = arrayIn;
    } else {
      result = (T[]) java.lang.reflect.Array.newInstance(componentClass,
                                                         size);
    }
    if (size == maxSize) {
      for (int i = 0; i < maxSize; ++i) {
        result[i] = (T) componentClass.cast(data[(end + i) % maxSize]);
      }
    } else {
      for (int i = 0; i < end; ++i) {
        result[i] = (T) componentClass.cast(data[i]);
      }
    }
    return result;
  }

}

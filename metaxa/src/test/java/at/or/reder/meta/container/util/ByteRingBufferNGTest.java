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

import java.nio.ByteBuffer;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

/**
 *
 * @author Wolfgang Reder
 */
public class ByteRingBufferNGTest
{

  public ByteRingBufferNGTest()
  {
  }

  @org.testng.annotations.BeforeClass
  public static void setUpClass() throws Exception
  {
  }

  @org.testng.annotations.AfterClass
  public static void tearDownClass() throws Exception
  {
  }

  @org.testng.annotations.BeforeMethod
  public void setUpMethod() throws Exception
  {
  }

  @org.testng.annotations.AfterMethod
  public void tearDownMethod() throws Exception
  {
  }

  /**
   * Test of push method, of class RingBuffer.
   */
  @Test
  public void testPush()
  {
    ByteRingBuffer instance = new ByteRingBuffer(5);
    assertEquals(0,
                 instance.getSize());
    instance.push((byte) 1);
    assertEquals(1,
                 instance.getSize());
    instance.push((byte) 2);
    instance.push((byte) 3);
    instance.push((byte) 4);
    assertEquals(4,
                 instance.getSize());
    instance.push((byte) 5);
    assertEquals(5,
                 instance.getSize());
    instance.push((byte) 6);
    assertEquals(5,
                 instance.getSize());
  }

  /**
   * Test of clear method, of class RingBuffer.
   */
  @Test
  public void testClear()
  {
    ByteRingBuffer instance = new ByteRingBuffer(5);
    assertEquals(0,
                 instance.getSize());
    instance.push((byte) 1);
    assertEquals(1,
                 instance.getSize());
    instance.clear();
    assertEquals(0,
                 instance.getSize());
  }

  /**
   * Test of fillArray method, of class RingBuffer.
   */
  @Test
  public void testFillArray()
  {
    ByteRingBuffer instance = new ByteRingBuffer(5);
    byte[] array = new byte[0];
    byte[] car = instance.fillArray(array);
    assertSame(array,
               car);
    instance.push((byte) 1);
    instance.push((byte) 2);
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new byte[]{1, 2},
                      car);
    array = new byte[instance.getSize()];
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new byte[]{1, 2},
                      car);
    instance.push((byte) 3);
    instance.push((byte) 4);
    instance.push((byte) 5);
    instance.push((byte) 6);
    instance.push((byte) 7);
    instance.push((byte) 8);
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new byte[]{4, 5, 6, 7, 8},
                      car);
  }

  @Test
  public void testFillArray1()
  {
    ByteRingBuffer instance = new ByteRingBuffer(1);
    byte[] array = new byte[0];
    byte[] car = instance.fillArray(array);
    assertSame(array,
               car);
    instance.push((byte) 1);
    instance.push((byte) 2);
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new byte[]{2},
                      car);
    array = new byte[instance.getSize()];
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new byte[]{2},
                      car);
    instance.push((byte) 3);
    instance.push((byte) 4);
    instance.push((byte) 5);
    instance.push((byte) 6);
    instance.push((byte) 7);
    instance.push((byte) 8);
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new byte[]{8},
                      car);
  }

  @Test
  public void testFillBuffer()
  {
    ByteRingBuffer instance = new ByteRingBuffer(5);
    ByteBuffer buffer = ByteBuffer.allocate(10);
    buffer.limit(0);
    int result = instance.fillByteBuffer(buffer);
    assertEquals(0,
                 result);
    instance.push((byte) 1);
    instance.push((byte) 2);
    buffer.clear();
    buffer.limit(2);
    result = instance.fillByteBuffer(buffer);
    assertEquals(2,
                 result);
    buffer.rewind();
    byte[] car = new byte[result];
    buffer.get(car);
    assertArrayEquals(new byte[]{1, 2},
                      car);
    buffer.rewind();
    buffer.limit(1);
    result = instance.fillByteBuffer(buffer);
    assertEquals(1,
                 result);
    buffer.rewind();
    assertEquals((byte) 1,
                 buffer.get());
    instance.push((byte) 2);
    instance.push((byte) 3);
    instance.push((byte) 4);
    instance.push((byte) 5);
    instance.push((byte) 6);
    instance.push((byte) 7);
    buffer.clear();
    result = instance.fillByteBuffer(buffer);
    assertEquals(5,
                 result);
    buffer.limit(buffer.position());
    buffer.rewind();
    byte[] expected = new byte[]{3, 4, 5, 6, 7};
    byte[] filled = new byte[5];
    buffer.get(filled);
    assertArrayEquals(expected,
                      filled);
  }

}

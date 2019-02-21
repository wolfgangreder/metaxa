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

import at.or.reder.media.util.RingBuffer;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.Test;

/**
 *
 * @author Wolfgang Reder
 */
public class RingBufferNGTest
{

  public RingBufferNGTest()
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
    RingBuffer<Character> instance = new RingBuffer<>(5);
    assertEquals(0,
                 instance.getSize());
    instance.push('W');
    assertEquals(1,
                 instance.getSize());
    instance.push('o');
    instance.push('l');
    instance.push('f');
    assertEquals(4,
                 instance.getSize());
    instance.push('g');
    assertEquals(5,
                 instance.getSize());
    instance.push('a');
    assertEquals(5,
                 instance.getSize());
  }

  /**
   * Test of clear method, of class RingBuffer.
   */
  @Test
  public void testClear()
  {
    RingBuffer<Character> instance = new RingBuffer<>(5);
    assertEquals(0,
                 instance.getSize());
    instance.push('W');
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
    RingBuffer<Character> instance = new RingBuffer<>(5);
    Character[] array = new Character[0];
    Character[] car = instance.fillArray(array);
    assertSame(array,
               car);
    instance.push('W');
    instance.push('o');
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new Character[]{'W', 'o'},
                      car);
    array = new Character[instance.getSize()];
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new Character[]{'W', 'o'},
                      car);
    instance.push('l');
    instance.push('f');
    instance.push('g');
    instance.push('a');
    instance.push('n');
    instance.push('g');
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new Character[]{'f', 'g', 'a', 'n', 'g'},
                      car);
  }

  @Test
  public void testFillArray1()
  {
    RingBuffer<Character> instance = new RingBuffer<>(1);
    Character[] array = new Character[0];
    Character[] car = instance.fillArray(array);
    assertSame(array,
               car);
    instance.push('W');
    instance.push('o');
    car = instance.fillArray(array);
    assertNotSame(array,
                  car);
    assertArrayEquals(new Character[]{'o'},
                      car);
    array = new Character[instance.getSize()];
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new Character[]{'o'},
                      car);
    instance.push('l');
    instance.push('f');
    instance.push('g');
    instance.push('a');
    instance.push('n');
    instance.push('g');
    car = instance.fillArray(array);
    assertSame(array,
               car);
    assertArrayEquals(new Character[]{'g'},
                      car);
  }

}

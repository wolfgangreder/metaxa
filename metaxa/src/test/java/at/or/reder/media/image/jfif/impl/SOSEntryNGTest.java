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
package at.or.reder.media.image.jfif.impl;

import at.or.reder.media.io.PositionInputStream;
import java.io.ByteArrayInputStream;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author Wolfgang Reder
 */
public class SOSEntryNGTest
{

  public SOSEntryNGTest()
  {
  }

  @Test
  public void testSkipToEndOfEntry_1() throws Exception
  {
    byte[] buffer = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x00, (byte) 0xff, 0x00, 0x00, 0x06};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      int result = SOSEntry.skipToEndOfEntry(pis);
      assertEquals(buffer.length,
                   result);
      int read = pis.read();
      assertEquals(-1,
                   read);
    }
  }

  @Test
  public void testSkipToEndOfEntry_2() throws Exception
  {
    byte[] buffer = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x00, (byte) 0xff, (byte) 0xc4, 0x00, 0x06};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      int result = SOSEntry.skipToEndOfEntry(pis);
      assertEquals(buffer.length - 4,
                   result);
      int read = pis.read();
      assertEquals((byte) 0xff,
                   (byte) read);
      read = pis.read();
      assertEquals((byte) 0xc4,
                   (byte) read);
    }
  }

  @Test
  public void testSkipToEndOfEntry_3() throws Exception
  {
    byte[] buffer = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x00, (byte) 0xff, (byte) 0x00};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      int result = SOSEntry.skipToEndOfEntry(pis);
      assertEquals(buffer.length,
                   result);
      int read = pis.read();
      assertEquals(-1,
                   read);
    }
  }

  @Test
  public void testSkipToEndOfEntry_4() throws Exception
  {
    byte[] buffer = new byte[]{(byte) 0xff, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x00, (byte) 0xff, (byte) 0x00};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      int result = SOSEntry.skipToEndOfEntry(pis);
      assertEquals(buffer.length,
                   result);
      int read = pis.read();
      assertEquals(-1,
                   read);
    }
  }

  @Test
  public void testSkipToEndOfEntry_5() throws Exception
  {
    byte[] buffer = new byte[]{(byte) 0xff, (byte) 0xc4, 0x01, 0x02, 0x03, 0x04, 0x05, 0x00, (byte) 0xff, (byte) 0x00};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      int result = SOSEntry.skipToEndOfEntry(pis);
      assertEquals(0,
                   result);
      int read = pis.read();
      assertEquals((byte) 0xff,
                   (byte) read);
    }
  }

}

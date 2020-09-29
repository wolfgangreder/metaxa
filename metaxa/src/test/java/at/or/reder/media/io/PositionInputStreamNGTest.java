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

import java.io.ByteArrayInputStream;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

/**
 *
 * @author Wolfgang Reder
 */
public class PositionInputStreamNGTest
{

  @Test
  public void testRewindPosition() throws Exception
  {
    byte[] buffer = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    try ( PositionInputStream pis = new PositionInputStream(new ByteArrayInputStream(buffer),
                                                            2)) {
      pis.setRewindReadEnabled(true);
      int read;
      read = pis.read();
      assertEquals(0x01,
                   (byte) read);
      read = pis.read();
      assertEquals(0x02,
                   (byte) read);
      assertEquals(2,
                   pis.getPosition());
      pis.rewind(true);
      assertEquals(0,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x01,
                   (byte) read);
      assertEquals(1,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x02,
                   (byte) read);
      assertEquals(2,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x03,
                   (byte) read);
      assertEquals(3,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x04,
                   (byte) read);
      assertEquals(4,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x05,
                   (byte) read);
      assertEquals(5,
                   pis.getPosition());
      pis.rewind(true);
      assertEquals(3,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x04,
                   (byte) read);
      assertEquals(4,
                   pis.getPosition());
      read = pis.read();
      assertEquals(0x05,
                   (byte) read);
      assertEquals(5,
                   pis.getPosition());
    }
  }

}

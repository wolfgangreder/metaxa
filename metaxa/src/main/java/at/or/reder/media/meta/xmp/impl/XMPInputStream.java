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
package at.or.reder.media.meta.xmp.impl;

import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.XMPMetaFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPInputStream extends InputStream
{

  private final XMPMeta meta;
  private ByteArrayInputStream strm;

  public XMPInputStream(XMPMeta meta)
  {
    this.meta = Objects.requireNonNull(meta,
                                       "meta is null");
  }

  private boolean checkStream() throws IOException
  {
    if (strm == null) {
      try {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMPMetaFactory.serialize(meta,
                                 bos);
        strm = new ByteArrayInputStream(bos.toByteArray());
      } catch (XMPException ex) {
        throw new IOException(ex);
      }
    }
    return strm != null;
  }

  @Override
  public int read() throws IOException
  {
    if (checkStream()) {
      return strm.read();
    }
    return -1;
  }

}

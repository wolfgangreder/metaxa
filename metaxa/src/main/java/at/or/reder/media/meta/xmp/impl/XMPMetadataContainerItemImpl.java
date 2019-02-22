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

import at.or.reder.media.MediaChunk;
import at.or.reder.media.meta.xmp.XMPMetadataContainerItem;
import at.or.reder.media.util.MediaUtils;
import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.XMPMetaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.logging.Level;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPMetadataContainerItemImpl implements XMPMetadataContainerItem
{

  private SoftReference<XMPMeta> meta;
  private final MediaChunk chunk;

  XMPMetadataContainerItemImpl(MediaChunk chunk)
  {
    this.chunk = chunk;
  }

  private synchronized XMPMeta loadMeta() throws IOException, XMPException
  {
    XMPMeta result = meta != null ? meta.get() : null;
    if (result == null && chunk != null) {
      try (InputStream is = chunk.getDataStream()) {
        result = XMPMetaFactory.parse(is);
      }
    }
    return result;
  }

  @Override
  public <C> C getItem(Class<? extends C> itemClass)
  {
    try {
      if (XMPMeta.class == itemClass) {
        return itemClass.cast(loadMeta());
      } else if (String.class == itemClass) {
        XMPMeta m = loadMeta();
        return itemClass.cast(XMPMetaFactory.serializeToString(m,
                                                               null));
      }
    } catch (IOException | XMPException ex) {
      MediaUtils.LOGGER.log(Level.SEVERE,
                            null,
                            ex);
    }
    return null;
  }

}
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

import at.or.reder.media.meta.MetadataProvider;
import at.or.reder.media.meta.xmp.XMPMetadataContainerItem;
import com.adobe.internal.xmp.XMPConst;
import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.XMPMetaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPMetadataContainerNewItemImpl implements XMPMetadataContainerItem
{

  private final MetadataProvider provider;
  private final XMPMeta meta;

  public XMPMetadataContainerNewItemImpl(MetadataProvider provider,
                                         XMPMeta meta)
  {
    this.provider = provider;
    this.meta = meta;
  }

  @Override
  public MetadataProvider getProvider()
  {
    return provider;
  }

  @Override
  public void storeTo(OutputStream os) throws IOException
  {
    try {
      XMPMetaFactory.serialize(meta,
                               os);
    } catch (XMPException ex) {
      throw new IOException(ex);
    }
  }

  @Override
  public String getURI()
  {
    return XMPConst.NS_XMP;
  }

  private InputStream createInputStream()
  {
    return new XMPInputStream(meta);
  }

  @Override
  public <C> C getItem(Class<? extends C> itemClass)
  {
    if (InputStream.class.isAssignableFrom(itemClass)) {
      return itemClass.cast(createInputStream());
    } else {
      return null;
    }
  }

}

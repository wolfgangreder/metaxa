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
package at.or.reder.meta.container.jfif.impl;

import com.adobe.internal.xmp.XMPConst;
import com.adobe.internal.xmp.XMPException;
import com.adobe.internal.xmp.XMPMeta;
import com.adobe.internal.xmp.XMPMetaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wolfgang Reder
 */
public final class XMPMetaSource implements ExtensionSource<XMPMeta>
{

  public static final String JFIF_EXTENSION_NAME = XMPConst.NS_XMP;//"http://ns.adobe.com/xap/1.0/";

  @Override
  public XMPMeta createExtension(InputStream is) throws IOException
  {
    try {
      return XMPMetaFactory.parse(is);
    } catch (XMPException ex) {
      Logger.getLogger(XMPMetaSource.class.getName()).log(Level.SEVERE,
                                                          null,
                                                          ex);
      throw new IOException(ex);
    }
  }

  @Override
  public Class<? extends XMPMeta> getRepresentaionClass()
  {
    return XMPMeta.class;
  }

}

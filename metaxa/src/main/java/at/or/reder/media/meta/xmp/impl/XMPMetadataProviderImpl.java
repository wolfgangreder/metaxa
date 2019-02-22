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
import at.or.reder.media.meta.MetadataContainerItem;
import at.or.reder.media.meta.MetadataProvider;
import at.or.reder.media.meta.xmp.XMPMetadataProvider;
import com.adobe.internal.xmp.XMPConst;
import java.util.Collections;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Wolfgang Reder
 */
@ServiceProvider(service = MetadataProvider.class)
public final class XMPMetadataProviderImpl implements XMPMetadataProvider
{

  @Override
  public List<MetadataContainerItem> extractItems(MediaChunk item)
  {
    if (XMPConst.NS_XMP.equals(item.getExtensionName())) {

    }
    return Collections.emptyList();
  }

}

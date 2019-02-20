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
package at.or.reder.meta.elements.impl;

import at.or.reder.meta.elements.ImageArea;
import at.or.reder.meta.elements.MetaData;
import com.adobe.internal.xmp.XMPMeta;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPMetaData implements MetaData
{

  private final XMPMeta meta;

  public XMPMetaData(XMPMeta meta)
  {
    this.meta = meta;
  }

  @Override
  public <M> M getRawDataModel(Class<? extends M> metaModel)
  {
    if (metaModel.isAssignableFrom(meta.getClass())) {
      return metaModel.cast(meta);
    }
    return null;
  }

  @Override
  public <C> List<C> getElements(Class<? extends C> infoClass)
  {
    if (ImageArea.class == infoClass) {

    }
    return Collections.emptyList();
  }

}

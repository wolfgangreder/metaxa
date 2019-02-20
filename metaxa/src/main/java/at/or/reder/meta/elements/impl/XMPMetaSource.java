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

import at.or.reder.meta.elements.MetaSource;
import java.util.ResourceBundle;

/**
 *
 * @author Wolfgang Reder
 */
public class XMPMetaSource implements MetaSource
{

  @Override
  public String getName()
  {
    return ResourceBundle.getBundle(XMPMetaSource.class.getName()).getString("XMPMetaSource_name");
  }

  @Override
  public String getLabel()
  {
    return "XMP";
  }

  @Override
  public String getDescription()
  {
    return ResourceBundle.getBundle(XMPMetaSource.class.getName()).getString("XMPMetaSource_desc");
  }

}

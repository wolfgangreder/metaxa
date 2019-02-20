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

module Metaxa {
  exports at.or.reder.meta.container.jfif;
  exports at.or.reder.meta.container.jfif.spi;
  exports at.or.reder.meta.container.util;
  exports at.or.reder.meta.elements;
  exports at.or.reder.meta.elements.spi;

  provides at.or.reder.meta.elements.spi.ImageAreaTypeProvider with at.or.reder.meta.elements.impl.MWGAreaTypeFactory;
  provides at.or.reder.meta.elements.MetaSource with at.or.reder.meta.elements.impl.XMPMetaSource;

  requires java.logging;
  requires xmpcore;
}

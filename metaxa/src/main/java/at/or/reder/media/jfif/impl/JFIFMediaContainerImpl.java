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
package at.or.reder.media.jfif.impl;

import at.or.reder.media.MediaRepresentation;
import at.or.reder.media.MimeTypes;
import at.or.reder.media.jfif.JFIFEntry;
import at.or.reder.media.jfif.JFIFMediaContainer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
final class JFIFMediaContainerImpl implements JFIFMediaContainer
{

  private final URL url;
  private final List<JFIFEntry> entries;

  public JFIFMediaContainerImpl(URL url,
                                List<JFIFEntry> entries)
  {
    this.url = url;
    this.entries = Collections.unmodifiableList(new ArrayList<>(entries));
  }

  @Override
  public URL getMediaURL()
  {
    return url;
  }

  @Override
  public String getMIMEType()
  {
    return MimeTypes.IMAGE_JPEG.getMimeType();
  }

  @Override
  public List<JFIFEntry> getJFIFEntries()
  {
    return entries;
  }

  @Override
  public <C> List<C> getRepresentation(MediaRepresentation representation,
                                       Class<? extends C> representationClass) throws IOException
  {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}

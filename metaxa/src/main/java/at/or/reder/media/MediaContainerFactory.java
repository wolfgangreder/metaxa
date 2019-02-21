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
package at.or.reder.media;

import at.or.reder.media.MediaContainer;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public final class MediaContainerFactory
{

  public static MediaContainer createContainer(URL url) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  public static MediaContainer createContainer(File file) throws IOException
  {
    throw new UnsupportedOperationException();
  }

  private MediaContainerFactory()
  {
  }

}

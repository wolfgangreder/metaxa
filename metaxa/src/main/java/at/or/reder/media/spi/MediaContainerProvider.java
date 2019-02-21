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
package at.or.reder.media.spi;

import at.or.reder.media.MediaContainer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public interface MediaContainerProvider
{

  public boolean isValidFor(String mimeType);

  public boolean isValidFor(InputStream is);

  public boolean isValidFor(URL url);

  public boolean isValidFor(File file);

  public MediaContainer createContainer(InputStream is) throws IOException;

  public MediaContainer createContainer(URL url) throws IOException;

  public MediaContainer createContainer(File file) throws IOException;

}

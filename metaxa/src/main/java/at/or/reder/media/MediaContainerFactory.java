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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Lookup;

/**
 *
 * @author Wolfgang Reder
 */
public final class MediaContainerFactory
{

  private static final List<MediaContainerProvider> providerList = new ArrayList<>();
  private static boolean providerLoaded = false;

  private static void reloadProvider()
  {
    try {
      providerList.clear();
      providerList.addAll(Lookup.getDefault().lookupAll(MediaContainerProvider.class));
    } finally {
      providerLoaded = true;
    }

  }

  public static List<MediaContainerProvider> getInstalledMediaContainerProvider()
  {
    return getInstalledMediaContainerProvider(false);
  }

  public static List<MediaContainerProvider> getInstalledMediaContainerProvider(boolean reload)
  {
    synchronized (providerList) {
      if (reload || !providerLoaded) {
        reloadProvider();
      }
      return new ArrayList<>(providerList);
    }
  }

  private static MediaContainerProvider findProvider(File file)
  {
    for (MediaContainerProvider p : getInstalledMediaContainerProvider()) {
      if (p.isValidFor(file)) {
        return p;
      }
    }
    return null;
  }

  private static MediaContainerProvider findProvider(URL file)
  {
    for (MediaContainerProvider p : getInstalledMediaContainerProvider()) {
      if (p.isValidFor(file)) {
        return p;
      }
    }
    return null;
  }

  public static MediaContainer createContainer(URL url) throws IOException
  {
    MediaContainerProvider provider = findProvider(url);
    if (provider == null) {
      throw new IOException("Cannot find provider");
    }
    return provider.createContainer(url);
  }

  public static MediaContainer createContainer(File file) throws IOException
  {
    MediaContainerProvider provider = findProvider(file);
    if (provider == null) {
      throw new IOException("Cannot find provider");
    }
    return provider.createContainer(file);
  }

  private MediaContainerFactory()
  {
  }

}

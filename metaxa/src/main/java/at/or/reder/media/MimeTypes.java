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

/**
 *
 * @author Wolfgang Reder
 */
public enum MimeTypes
{
  APPLICATION_OCTET_STREAM("application/octet-stream"),
  IMAGE_JPEG("image/jpeg");
  private final String mimeType;

  private MimeTypes(String mt)
  {
    this.mimeType = mt;
  }

  public String getMimeType()
  {
    return mimeType;
  }

  public static MimeTypes valueOfMime(String mt)
  {
    for (MimeTypes m : values()) {
      if (m.mimeType.equals(mt)) {
        return m;
      }
    }
    return APPLICATION_OCTET_STREAM;
  }

}

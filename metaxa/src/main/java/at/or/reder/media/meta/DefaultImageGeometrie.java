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
package at.or.reder.media.meta;

/**
 *
 * @author Wolfgang Reder
 */
public final class DefaultImageGeometrie implements ImageGeometrie
{

  private final PixelUnit unit;
  private final int dpix;
  private final int dpiy;
  private final int width;
  private final int height;

  public DefaultImageGeometrie(PixelUnit unit,
                               int dpix,
                               int dpiy,
                               int width,
                               int height)
  {
    this.unit = unit;
    this.dpix = dpix;
    this.dpiy = dpiy;
    this.width = width;
    this.height = height;
  }

  @Override
  public PixelUnit getUnit()
  {
    return unit;
  }

  @Override
  public int getDensityX()
  {
    return dpix;
  }

  @Override
  public int getDensityY()
  {
    return dpiy;
  }

  @Override
  public int getImageWidth()
  {
    return width;
  }

  @Override
  public int getImageHeight()
  {
    return height;
  }

}

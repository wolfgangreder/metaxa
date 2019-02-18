/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

/**
 *
 * @author Wolfgang Reder
 */
public final class SegmentSourceFactory
{

  public static SegmentSource instanceOf(URL u)
  {
    if ("file".equals(Objects.requireNonNull(u,
                                             "url is null").getProtocol())) {
      return new FileSegmentSource(Paths.get(u.getFile()));
    } else {
      throw new IllegalArgumentException("Unsupported protokoll");
    }
  }

  private SegmentSourceFactory()
  {
  }

}

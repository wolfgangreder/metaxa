/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public interface SegmentSource
{

  public URL getURL();

  public InputStream openStream(long offset,
                                int size) throws IOException;

}

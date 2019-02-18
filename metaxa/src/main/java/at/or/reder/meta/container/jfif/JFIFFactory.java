/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif;

import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public interface JFIFFactory
{

  public JFIFDirectory readDirectory(URL u) throws IOException;

}

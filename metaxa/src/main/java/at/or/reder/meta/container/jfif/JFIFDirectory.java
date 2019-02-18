/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif;

import java.util.List;

/**
 *
 * @author Wolfgang Reder
 */
public interface JFIFDirectory
{

  /**
   * List of Entries in the order they have to be written to the output.
   *
   * @return list of entries.
   */
  public List<JFIFEntry> getEntries();

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.jfif.JFIFDirectory;
import at.or.reder.meta.container.jfif.JFIFEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Wolfgang Reder
 */
public final class JFIFDirectoryImpl implements JFIFDirectory
{

  private final List<JFIFEntry> entries;

  public JFIFDirectoryImpl(Collection<? extends JFIFEntry> entries)
  {
    this.entries = Collections.unmodifiableList(entries.stream().sorted().collect(Collectors.toList()));
  }

  @Override
  public List<JFIFEntry> getEntries()
  {
    return entries;
  }

}

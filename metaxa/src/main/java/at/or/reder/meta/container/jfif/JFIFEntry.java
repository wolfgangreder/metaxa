/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents a JFIF Marker Entry.
 * <pre>
 * Layout of Entry:
 * 0  1  2  3  4
 * ff xx ss ss pp ...         pp dd ....                         dd
 *       |&lt;getPrefixLen()&gt;||&lt;------ getLength() -----&gt;|
 *       |&lt; ss                                             &gt;|
 * ^                             ^
 * |                             |
 * getOffset()                   getDataOffset()
 * </pre>
 *
 * @author Wolfgang Reder
 */
public interface JFIFEntry extends Comparable<JFIFEntry>
{

  /**
   * Marker byte of JFIFEntry (e.g 0xffe0, ....)
   *
   * @return marker byte
   */
  public int getMarker();

  /**
   * Name of the Marker (e.g. APP0,SOI,...)
   *
   * @return Name of Marker
   */
  public String getName();

  /**
   * Datalength of the Segment. Net length of data (without length,externsionname).
   *
   * @return length
   */
  public int getLength();

  /**
   * Name of the APPx extension (ex. Exif,....)
   *
   * @return extension Name
   */
  public String getExtensionName();

  /**
   * Datastream for the Segment <em>with</em> marker and length.
   *
   * @return Stream with data.
   * @throws java.io.IOException on IOError
   */
  public InputStream getInputStream() throws IOException;

  public InputStream getDataStream() throws IOException;

  /**
   * Offset of Segment with the original stream
   *
   * @return offset
   */
  public long getOffset();

  public default long getDataOffset()
  {
    return getOffset() + getPrefixLength();
  }

  public default int getPrefixLength()
  {
    return 4;
  }

  public default <C> C getDataRepresentation(Class<? extends C> representationClass) throws IOException
  {
    return null;
  }

}

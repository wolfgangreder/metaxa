/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.PositionInputStream;
import java.io.IOException;

/**
 *
 * @author Wolfgang Reder
 */
public class SOFEntry extends AbstractJFIFEntry
{

  public static SOFEntry newInstance(PositionInputStream is,
                                     int marker) throws IOException
  {
    long offset = is.getPosition() - 2;
    int length = loadShort(is) - 2;
    if (length == -1) {
      return null;
    }
    skipToEndOfEntryLength(is,
                           length);
    return new SOFEntry(SegmentSourceFactory.instanceOf(is.getURL()),
                        marker,
                        "SOF" + Integer.toHexString(marker & 0xf),
                        length,
                        offset,
                        getSOFName(marker));
  }

  private static String getSOFName(int marker)
  {
    switch (marker) {
      case 0xffc0:
        return "Baseline DCT";
      case 0xffc1:
        return "Extended sequential DCT";
      case 0xffc2:
        return "Progressive DCT";
      case 0xffc3:
        return "Spatial (sequential) lossless";
      case 0xffc5:
        return "Differential sequential DCT";
      case 0xffc6:
        return "Differential progressive DCT";
      case 0xffc7:
        return "Differential spatial";
      case 0xffc8:
        return "reserved";
      case 0xffc9:
        return "Extended Sequential DCT";
      case 0xffca:
        return "Progressive DCT";
      case 0xffcb:
        return "Spatial (sequential) lossless";
      case 0xffcd:
        return "Differential sequential DCT";
      case 0xffce:
        return "Differential progressive DCT";
      case 0xffcf:
        return "Differential spatial";
      default:
        return "";
    }
  }

  public SOFEntry(SegmentSource source,
                  int marker,
                  String name,
                  int length,
                  long offset,
                  String extensionName)
  {
    super(source,
          marker,
          name,
          length,
          offset,
          extensionName);
  }

  @Override
  public int getPrefixLength()
  {
    return super.getPrefixLength(); //To change body of generated methods, choose Tools | Templates.
  }

}

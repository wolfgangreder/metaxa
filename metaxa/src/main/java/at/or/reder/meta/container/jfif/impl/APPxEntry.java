/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.util.PositionInputStream;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Wolfgang Reder
 */
public final class APPxEntry extends AbstractJFIFEntry
{

  public static APPxEntry newInstance(PositionInputStream is,
                                      int marker) throws IOException
  {
    switch (marker) {
      case 0xffe0:
        return newAPP0Entry(is);
      case 0xffe1:
        return newAPP1Entry(is); // Exif,XMP
      case 0xffed:
        return newAPP1Entry(is); // IPTC
      default:
        throw new UnsupportedOperationException("Unexpected APPx Entry " + Integer.toHexString(marker));
    }
  }

  private static String getExtionsName(PositionInputStream is) throws IOException
  {
    char ch;
    StringBuilder builder = new StringBuilder();
    do {
      ch = (char) is.read();
      if (ch != 0 && ch != -1) {
        builder.append(ch);
      }
    } while (ch != 0 && ch != -1);
    return builder.toString();
  }

  private static APPxEntry newAPP0Entry(PositionInputStream is) throws IOException
  {
    long offset = is.getPosition() - 2;
    long relPrefixStart = 2;
    int length = loadShort(is);
    if (length == -1) {
      return null;
    }
    String extName = getExtionsName(is);
    long relPrefixEnd = is.getPosition() - offset;
    int prefixSize = (int) (relPrefixEnd - relPrefixStart);
    length -= prefixSize;
    skipToEndOfEntryLength(is,
                           length);
    return new APPxEntry(0xffe0,
                         "APP0",
                         length,
                         extName,
                         is.getURL(),
                         offset,
                         prefixSize,
                         null);
  }

  private static APPxEntry newAPP1Entry(PositionInputStream is) throws IOException
  {
    long offset = is.getPosition() - 2;
    long relPrefixStart = 2;
    int length = loadShort(is);
    if (length == -1) {
      return null;
    }
    String extName = getExtionsName(is);
    if ("Exif".equals(extName)) { // Exif has 2 terminating 0
      is.read();
    }
    long relPrefixEnd = is.getPosition() - offset;
    int prefixSize = (int) (relPrefixEnd - relPrefixStart);
    length -= prefixSize;
    skipToEndOfEntryLength(is,
                           length);
    ExtensionSource<?> extSource = null;
    if (XMPMetaSource.JFIF_EXTENSION_NAME.equals(extName)) {
      extSource = new XMPMetaSource();
    }
    return new APPxEntry(0xffe0,
                         "APP1",
                         length,
                         extName,
                         is.getURL(),
                         offset,
                         prefixSize,
                         extSource);
  }

  private final int prefixSize;
  private final ExtensionSource<?> extensionFactory;

  public APPxEntry(int marker,
                   String name,
                   int length,
                   String extensionName,
                   URL url,
                   long offset,
                   int prefixSize,
                   ExtensionSource<?> extensionFactory)
  {
    super(SegmentSourceFactory.instanceOf(url),
          marker,
          name,
          length,
          offset,
          extensionName);
    this.prefixSize = prefixSize;
    this.extensionFactory = extensionFactory;
  }

  @Override
  public int getPrefixLength()
  {
    return prefixSize + 2;
  }

  @Override
  public <C> C getDataRepresentation(Class<? extends C> representationClass) throws IOException
  {
    if (extensionFactory != null && representationClass.isAssignableFrom(extensionFactory.getRepresentaionClass())) {
      return representationClass.cast(extensionFactory.createExtension(getDataStream()));
    }
    return null;
  }

}

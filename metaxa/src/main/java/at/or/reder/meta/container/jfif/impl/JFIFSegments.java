/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.or.reder.meta.container.jfif.impl;

import at.or.reder.meta.container.jfif.JFIFEntry;
import at.or.reder.meta.container.util.IOBiFunction;
import at.or.reder.meta.container.util.PositionInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Wolfgang Reder
 */
public enum JFIFSegments
{
  SOI(0xFFD8,
      "SOI",
      "Start Of Image",
      SOIEntry::newInstance),
  APP0(0xFFE0,
       "APP0",
       "JFIF tag",
       APPxEntry::newInstance),
  SOF0(0xFFC0,
       "SOF0",
       "Baseline DCT",
       SOFEntry::newInstance),
  SOF1(0xFFC1,
       "SOF1",
       "Extended sequential DCT",
       SOFEntry::newInstance),
  SOF2(0xFFC2,
       "SOF2",
       "Progressive DCT",
       SOFEntry::newInstance),
  SOF3(0xFFC3,
       "SOF3",
       "Lossless (sequential)",
       SOFEntry::newInstance),
  DHT(0xFFC4,
      "DHT",
      "Definition der Huffman-Tabellen",
      DHTEntry::newInstance),
  SOF5(0xFFC5,
       "SOF5",
       "Differential sequential DCT",
       SOFEntry::newInstance),
  SOF6(0xFFC6,
       "SOF6",
       "Differential progressive DCT",
       SOFEntry::newInstance),
  SOF7(0xFFC7,
       "SOF7",
       "Differential lossless (sequential)",
       SOFEntry::newInstance),
  JPG(0xFFC8,
      "JPG",
      "reserviert für JPEG extensions",
      null),
  SOF9(0xFFC9,
       "SOF9",
       "Extended sequential DCT",
       SOFEntry::newInstance),
  SOF10(0xFFCA,
        "SOF10",
        "Progressive DCT",
        SOFEntry::newInstance),
  SOF11(0xFFCB,
        "SOF11",
        "Lossless (sequential)",
        SOFEntry::newInstance),
  SOF13(0xFFCD,
        "SOF13",
        "Differential sequential DCT",
        SOFEntry::newInstance),
  SOF14(0xFFCE,
        "SOF14",
        "Differential progressive DCT",
        SOFEntry::newInstance),
  SOF15(0xFFCF,
        "SOF15",
        "Differential lossless (sequential)",
        SOFEntry::newInstance),
  DAC(0xFFCC,
      "DAC",
      "Definition der arithmetischen Codierung",
      null),
  DQT(0xFFDB,
      "DQT",
      "Definition der Quantisierungstabellen",
      DQTEntry::newInstance),
  DRI(0xFFDD,
      "DRI",
      "Define Restart Interval",
      null),
  APP1(0xFFE1,
       "APP1",
       "APP1",
       APPxEntry::newInstance),
  COM(0xFFFE,
      "COM",
      "Kommentare",
      null),
  SOS(0xFFDA,
      "SOS",
      "Start of Scan",
      SOSEntry::newInstance),
  EOI(0xFFD9,
      "EOI",
      "End of Image",
      EOIEntry::newInstance);
  private final int marker;
  private final String name;
  private final String descr;
  private final IOBiFunction<PositionInputStream, Integer, JFIFEntry> factory;
  private static final Map<Integer, JFIFSegments> entryMap = new HashMap<>();

  static {
    for (JFIFSegments s : values()) {
      entryMap.put(s.marker,
                   s);
    }
    for (int i = 0xffe1; i < 0xfff0; ++i) {
      entryMap.put(i,
                   APP1);
    }
  }

  private JFIFSegments(int marker,
                       String name,
                       String descr,
                       IOBiFunction<PositionInputStream, Integer, JFIFEntry> factory)
  {
    this.marker = marker;
    this.name = name;
    this.descr = descr;
    this.factory = factory;
  }

  public static final JFIFSegments valueOf(int marker)
  {
    return entryMap.get(marker);
  }

  public int getMarker()
  {
    return marker;
  }

  public String getName()
  {
    return name;
  }

  public String getDescr()
  {
    return descr;
  }

  public IOBiFunction<PositionInputStream, Integer, JFIFEntry> getFactory()
  {
    return factory;
  }

}

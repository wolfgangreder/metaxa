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
package at.or.reder.media.jfif;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Wolfgang Reder
 */
public enum JFIFMarker
{
  SOI(0xFFD8,
      "SOI",
      "Start Of Image"),
  APP0(0xFFE0,
       "APP0",
       "JFIF tag"),
  SOF0(0xFFC0,
       "SOF0",
       "Baseline DCT"),
  SOF1(0xFFC1,
       "SOF1",
       "Extended sequential DCT"),
  SOF2(0xFFC2,
       "SOF2",
       "Progressive DCT"),
  SOF3(0xFFC3,
       "SOF3",
       "Lossless (sequential)"),
  DHT(0xFFC4,
      "DHT",
      "Definition der Huffman-Tabellen"),
  SOF5(0xFFC5,
       "SOF5",
       "Differential sequential DCT"),
  SOF6(0xFFC6,
       "SOF6",
       "Differential progressive DCT"),
  SOF7(0xFFC7,
       "SOF7",
       "Differential lossless (sequential)"),
  JPG(0xFFC8,
      "JPG",
      "reserviert für JPEG extensions"),
  SOF9(0xFFC9,
       "SOF9",
       "Extended sequential DCT"),
  SOF10(0xFFCA,
        "SOF10",
        "Progressive DCT"),
  SOF11(0xFFCB,
        "SOF11",
        "Lossless (sequential)"),
  SOF13(0xFFCD,
        "SOF13",
        "Differential sequential DCT"),
  SOF14(0xFFCE,
        "SOF14",
        "Differential progressive DCT"),
  SOF15(0xFFCF,
        "SOF15",
        "Differential lossless (sequential)"),
  DAC(0xFFCC,
      "DAC",
      "Definition der arithmetischen Codierung"),
  DQT(0xFFDB,
      "DQT",
      "Definition der Quantisierungstabellen"),
  DRI(0xFFDD,
      "DRI",
      "Define Restart Interval"),
  APP1(0xFFE1,
       "APP1",
       "APP1"),
  COM(0xFFFE,
      "COM",
      "Kommentare"),
  SOS(0xFFDA,
      "SOS",
      "Start of Scan"),
  EOI(0xFFD9,
      "EOI",
      "End of Image");
  private final int marker;
  private final String name;
  private final String descr;
  private static final Map<Integer, JFIFMarker> entryMap = new HashMap<>();

  static {
    for (JFIFMarker s : values()) {
      entryMap.put(s.marker,
                   s);
    }
    for (int i = 0xffe1; i < 0xfff0; ++i) {
      entryMap.put(i,
                   APP1);
    }
  }

  private JFIFMarker(int marker,
                     String name,
                     String descr)
  {
    this.marker = marker;
    this.name = name;
    this.descr = descr;
  }

  public static final JFIFMarker valueOf(int marker)
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

}

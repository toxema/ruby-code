package com.yakut.util;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author yakut
 */
public class Setting {

          Properties prop;
          static Setting setting;
          File f;
          List<SettingListener> listeners = new ArrayList<SettingListener>();
          String deger;

          private Setting() {
                    prop = new SortedProperties();
                    f = new File("config.cfg");
                    try {
                              if (!f.exists()) {
                                        f.createNewFile();
                              }
                              prop.load(new FileReader(f));
                    } catch (IOException ex) {
                              JOptionPane.showMessageDialog(null, "Hata\r\n" + ex.getMessage());

                    }
          }
          final static Object lock = new Object();

          public static Setting getSettings() {
                    synchronized (lock) {
                              if (setting == null) {
                                        setting = new Setting();
                              }
                    }
                    return setting;
          }

          public void setBounds(String componentName, Rectangle rect) {
                    getSettings().setProperty("gui." + componentName, rect.x + "," + rect.y + "," + rect.width + "," + rect.height + "");
          }

          public Rectangle getBounds(String componentName) {
                    String bounds[] = getProperty("gui." + componentName, "0,0,400,400").split(",");
                    Rectangle rect = new Rectangle(0, 0, 400, 600);
                    try {
                              rect.setLocation(getInt(bounds[0]), getInt(bounds[1]));
                              rect.setSize(getInt(bounds[2]), getInt(bounds[3]));
                    } catch (Exception e) {
                    }
                    return rect;
          }

          public Color getSkinBackgroundColor() {
                    return getColor("theme.background.color");
          }

          public Color getSkinBackground2Color() {
                    return getColor("theme.background2.color");
          }

          public Color getSkinForegroundColor() {
                    return getColor("theme.foreground.color");
          }

          public Color getSkinForeground2Color() {
                    return getColor("theme.foreground2.color");
          }

          public String getProperty(String anahtar, String varsayilanDeger) {
                    deger = prop.getProperty(anahtar);
                    if (deger == null || deger.isEmpty()) {
                              deger = varsayilanDeger;
                              prop.setProperty(anahtar, deger);
                    }
                    return deger;
          }

          public String getProperty(String anahtar) {
                    return getProperty(anahtar, "");
          }

          //getPropery deki gibi olmayan anahtarı oluşturmaz. 
          // varsa gönderir yoksa null gönderir
          //o değeri oluşturmak için set() komutu kullanılmalıdır
          public String get(String anahtar) {
                    deger = prop.getProperty(anahtar);
                    return deger;
          }

          public void set(String key, String value) {
                    prop.setProperty(key, value);
          }

          public void setProperty(String anahtar, String deger) {
                    prop.setProperty(anahtar, deger);
          }

          public void saveProperties() {
                    try {
                              prop.store(new FileWriter(f), "www.kontrolsis.com");
                    } catch (IOException ex) {
                    }
          }

          public void addListener(SettingListener listener) {
                    listeners.add(listener);
          }

          public void removeListener(SettingListener listener) {
                    listeners.remove(listener);
          }

          public void notifySettingChanged() {
                    for (SettingListener l : listeners) {
                              l.notifySettingChanged();
                    }
          }

          public int getTableRowHeight() {
                    int height;
                    try {
                              height = Integer.parseInt(Setting.getSettings().getProperty("default.table.row.height", "16"));
                    } catch (NumberFormatException e) {
                              Setting.getSettings().setProperty("default.table.row.height", "16");
                              height = 16;
                    }
                    return height;
          }

          public void tableYuksekligiAl(JTable table, String name) {
                    System.out.println(name);
                    String colums[] = Setting.getSettings().getProperty(name, "0 0 0 0 0 0 0 0 0").trim().split(" ");
                    int count = table.getColumnModel().getColumnCount();
                    int son = count > colums.length ? colums.length : count;
                    for (int k = 0; k < son; k++) {
                              try {
                                        table.getColumnModel().getColumn(k).setPreferredWidth(Integer.parseInt(colums[k]));
                              } catch (NumberFormatException e) {
                              }
                    }
          }

          public void tableYuksekligiKaydet(JTable table, String name) {
                    System.out.println(name);
                    String colums = "";
                    int count = table.getColumnModel().getColumnCount();

                    for (int k = 0; k < count; k++) {
                              colums += table.getColumnModel().getColumn(k).getPreferredWidth() + " ";
                    }
                    Setting.getSettings().setProperty(name, colums.trim());

          }

          public int getInt(String property) {
                    return Integer.parseInt(getProperty(property));
          }

          public boolean getBoolean(String property) {
                    return Boolean.getBoolean(getProperty(property));
          }

          public float getFloat(String property) {
                    return Float.parseFloat(getProperty(property));
          }

          public double getDouble(String property) {
                    return Double.parseDouble(getProperty(property));
          }

          public void setColor(String property, Color color) {
                    setProperty(property, "#" + String.format("%06x", color.getRGB() & 0xffffff));
          }

          public Color getColor(String propery) {
                    try {
                              String v = getProperty(propery);
                              if (v.indexOf("#") == 0) {
                                        v = v.substring(1);
                              }
                              return new Color(Integer.parseInt(v, 16));
                    } catch (Exception e) {
                              return null;
                    }
          }
}

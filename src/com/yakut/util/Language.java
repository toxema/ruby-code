package com.yakut.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author yakut
 */
public class Language {

            Properties prop;
            static Language language;
            File f;
            String deger;

            private Language() {
                        this("language.dat");
            }

            private Language(String fileName) {
                        prop = new SortedProperties();
                        f = new File(fileName);
                        try {
                                    if (!f.exists()) {
                                                f.createNewFile();
                                    }
                                    prop.load(new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf8")));
                        } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Hata dil dosyası açılamadı\r\n" + ex.getMessage());
                        }
            }
            final static Object lock = new Object();

            public static Language getLanguage() {
                        return getLanguage("language.dat");
            }

            public static Language getLanguage(String fileName) {
                        synchronized (lock) {
                                    if (language == null) {
                                                language = new Language();
                                    }
                        }
                        return language;
            }

            public static String get(String anahtar) {
                        return Language.getProperty(anahtar);
            }

            public static String get(String anahtar, String varsayilanDeger) {
                        return Language.getProperty(anahtar, varsayilanDeger);
            }

            public static String getProperty(String anahtar) {
                        return Language.getProperty(anahtar, "");
            }

            public static String getProperty(String anahtar, String varsayilanDeger) {
                        String deger = getLanguage().prop.getProperty(anahtar);
                        if (deger == null || deger.isEmpty()) {
                                    deger = varsayilanDeger;
                                    getLanguage().prop.setProperty(anahtar, deger);
                        }
                        return deger;
            }

            public static void set(String anahtar, String deger) {
                        getLanguage().setProperty(anahtar, deger);
            }

            public void setProperty(String anahtar, String deger) {
                        getLanguage().prop.setProperty(anahtar, deger);
            }

            public   void save() {
                        try {
                                   prop.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "utf8")), "www.kontrolsis.com");
                        } catch (IOException ex) {
                                    ExceptionHandler.notifyException("Ayar dosyası kaydedilemedi.", ex);
                        }
            }

     
            public static void main(String[] args) {
                   
                        System.out.println(Language.get("Departman"));
                        System.out.println(Language.get("Bolum", "Devision"));
                        Language.getLanguage().save();
            }
}

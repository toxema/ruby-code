package com.yakut.util;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class Utils {

            public static void stopLogging() {
                        Logger.getRootLogger().setLevel(Level.OFF);
            }

            public static void startLogging() {
                        startLogging(Level.DEBUG);
            }

            public static void startLogging(Level logLevel) {
                        Logger.getRootLogger().setLevel(logLevel);
            }

            public static void show(JComponent component) {
                        JFrame f = new JFrame("Test Frame");
                        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        f.getContentPane().add(component);
                        f.setSize(400, 400);
                        f.setLocationRelativeTo(null);
                        f.setVisible(true);
            }

            public static void enableNimbus() {
                        try {
                                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                                if ("Nimbus".equals(info.getName())) {
                                                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                                            break;
                                                }
                                    }
                        } catch (Exception ex) {
                                    ExceptionHandler.onException("Nimbus yüklenemedi", ex);
                        }
            }
}

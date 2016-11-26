/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;

/** 
 *
 * @author yakut
 */
public class JPAUtil {

            static Logger logger = Logger.getLogger(JPAUtil.class);
            private static EntityManagerFactory fak;//=Persistence.createEntityManagerFactory("OtobusPU");

            public static EntityManagerFactory getEntityManagerFactory() {
                        if (fak == null) {

                                    Map properties = new HashMap();
                                    String dataBasePath = Setting.getSettings().getProperty("database.path", "e:\\Dropbox\\aZone\\data\\data.fdb");
                                    System.out.println("database Path:" + dataBasePath);
                                    String host = Setting.getSettings().getProperty("database.server.ip", "localhost");
                                    System.out.println("host:" + host);
                                    String port = Setting.getSettings().getProperty("database.server.port", "3050");
                                    System.out.println("port:" + port);
                                    properties.put("hibernate.connection.username", "SYSDBA");
                                    properties.put("hibernate.connection.password", "masterkey");
                                    properties.put("hibernate.connection.url", "jdbc:firebirdsql:" + host + "/" + port + ":" + dataBasePath + "?lc_ctype=WIN1254");

                                    try {
                                                fak = Persistence.createEntityManagerFactory("aZonePU", properties);
                                                programOncesiIslemleriCalistir();
                                    } catch (Exception ex) {
                                                ex.printStackTrace();
                                                fak = null;
                                    }

                        }

                        return fak;
            }

            public static void reloadAgain() {
                        fak = null;
            }

            public static void localCalis() {
                        Map properties = new HashMap();
                        String dataBasePath = Setting.getSettings().getProperty("database.path", "e:\\Dropbox\\aZone\\data\\data.fdb");
                        String host = "localhost";
                        String port = Setting.getSettings().getProperty("database.server.port", "3050");
                        properties.put("hibernate.connection.username", "SYSDBA");
                        properties.put("hibernate.connection.password", "masterkey");
                        properties.put("hibernate.connection.url", "jdbc:firebirdsql:" + host + "/" + port + ":" + dataBasePath + "?lc_ctype=WIN1254");

                        try {
                                    fak = Persistence.createEntityManagerFactory("aZonePU", properties);
                                    programOncesiIslemleriCalistir();
                        } catch (Exception ex) {
                                    //   ExceptionHandler.handleException(ex,0);
                                    ex.printStackTrace();
                                    fak = null;
                        }
            }

            public static void programOncesiIslemleriCalistir() {
                        try {
                                    yoklamaProsedurunuYukle();
                                    kacakProsedurunuYukle();
                        } catch (Exception ex) {
                                    logger.error("####################################################");
                                    logger.error("##########   program öncesi işlemlerde hata  ###############");
                                    logger.error(ex);
                                    logger.error("####################################################");
                        }
            }

            public static void kacakProsedurunuYukle() {
                        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
                        EntityTransaction tx = em.getTransaction();
                        tx.begin();
                        boolean hataOlustu = false;
                        try {
                                    logger.info("---------------  Kacak prosedürü  sorgulanıyor    -----------------------");
                                    em.createNativeQuery("select * from kacak('0','0','14.01.2014','14.01.2014',99,99,2)").getResultList();
                                    logger.info("---------------  kacak prosedürü  yüklü    -----------------------");
                        } catch (Exception ex) {
                                    logger.error("------------   kacak  prosedürü hata döndürdü    -----------------", ex);
                                    hataOlustu = true;
                        }
                        if (hataOlustu) {
                                    logger.error("###############   KACAK  PROSEDURU YUKLENECEK  ############");
                                    tx.rollback();
                                    em.close();
                                    Database db = new Database(Setting.getSettings().getProperty("database.path", "e:\\Dropbox\\aZone\\data\\data.fdb"),
                                            Setting.getSettings().getProperty("database.server.ip", "localhost"));
                                    try {
                                                if (db.begin()) {
                                                            InputStream in = JPAUtil.class.getClass().getResourceAsStream("/com/yakut/azone/util/kacak.sql");
                                                            String sql;
                                                            try {
                                                                        Scanner scan = new Scanner(in);
                                                                        sql = "";
                                                                        while (scan.hasNextLine()) {
                                                                                    sql += scan.nextLine() + "\r\n";
                                                                        }
                                                                        logger.debug("kacak prosedürü oluşturuluyor.");
                                                                        logger.debug("-----------------------------------------------------------------------------------------------");
                                                                        db.execute(sql);
                                                                        logger.debug("\r\n-----------------------------------------------------------------------------------------------");
                                                                        db.end();
                                                                        logger.error("###############   KACAK PROSEDURU BASARIYLA YUKLENDI  ############");
                                                            } catch (Exception ex) {
                                                                        logger.error("prosedür yüklenirken hata.", ex);
                                                            }
                                                }
                                    } catch (Exception ex) {
                                                 logger.error("Veri tabanına bağlantı hatası.", ex);
                                    }
                        }
            }

            public static void yoklamaProsedurunuYukle() {
                        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
                        EntityTransaction tx = em.getTransaction();
                        tx.begin();
                        boolean hataOlustu = false;
                        try {
                                    logger.info("---------------  Yoklama prosedürü  sorgulanıyor    -----------------------");
                                    em.createNativeQuery("select * from yoklama('0','0','14.01.2014','14.01.2014',99,99,2)").getResultList();
                                    logger.info("---------------  Yoklama prosedürü  yüklü    -----------------------");
                        } catch (Exception ex) {
                                    logger.error("------------   yoklama prosedürü hata döndürdü    -----------------", ex);
                                    hataOlustu = true;
                        }
                        if (hataOlustu) {
                                    logger.error("###############   YOKLAMA PROSEDURU YUKLENECEK  ############");
                                    tx.rollback();
                                    em.close();
                                    Database db = new Database(Setting.getSettings().getProperty("database.path", "e:\\Dropbox\\aZone\\data\\data.fdb"),
                                            Setting.getSettings().getProperty("database.server.ip", "localhost"));
                                    try {
                                                if (db.begin()) {
                                                            InputStream in = JPAUtil.class.getClass().getResourceAsStream("/com/yakut/azone/util/yoklama.sql");
                                                            String sql;
                                                            try {
                                                                        Scanner scan = new Scanner(in);
                                                                        sql = "";
                                                                        while (scan.hasNextLine()) {
                                                                                    sql += scan.nextLine() + "\r\n";
                                                                        }
                                                                        logger.debug("yoklama prosedürü oluşturuluyor.");
                                                                        logger.debug("-----------------------------------------------------------------------------------------------");
                                                                        db.execute(sql);
                                                                        logger.debug("\r\n-----------------------------------------------------------------------------------------------");
                                                                        db.end();
                                                                        logger.error("###############   YOKLAMA PROSEDURU BASARIYLA YUKLENDI  ############");
                                                            } catch (Exception ex) {
                                                                        logger.error("prosedür yüklenirken hata.", ex);
                                                            }
                                                }
                                    } catch (Exception ex) {
                                                  logger.error("Veri tabanına bağlantı hatası.", ex);
                                    }
                        }
            }
}

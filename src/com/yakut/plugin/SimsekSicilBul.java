/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.plugin;

import com.yakut.util.Database;
import com.yakut.util.FileIO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yakut
 */
public class SimsekSicilBul {

            Database db = new Database();

            public SimsekSicilBul() {
                        db.connect();
            }

            public String q(String t) {
                        return "'" + t + "%'";
            }

            public String getSicil(String ad, String soyad) {
                        
                        String sicil = "YOK";
                        ResultSet r = db.sql("select SICIL_NO,adi,soyadi from personel where ADI like " + q(ad.substring(0,2)) + " and soyadi like " + q(soyad.substring(0,2)));
                        try {
                                    if(r.next()){
                                                sicil=r.getString(1)+"," +r.getString(2)+"," +r.getString(3);
                                    }
                        } catch (SQLException ex) {
                                    Logger.getLogger(SimsekSicilBul.class.getName()).log(Level.SEVERE, null, ex);
                                    sicil="HATA";
                        }
                        return sicil;
            }

            public static void main(String[] args) {

                        SimsekSicilBul sb=new SimsekSicilBul();
                        List<String> list = FileIO.getLines("personel-ad");
                        List<String> sList=new ArrayList<>();
                        for (String s : list) {
                                    String row[] = s.split(" ");
                                    String ad;
                                    String soyad;
                                    ad = row[0];
                                    if (row.length < 3) {

                                                soyad = row[1];
                                    } else {

                                                soyad = row[2];
                                    }

                                    String sm = sb.getSicil(ad, soyad);
                                    System.out.println(sm);
                                    sList.add(sm+"    -  "+ad+" "+soyad);

                        }
                        
                        for(String s:sList){
                                    System.out.println(s);
                        }
            }
}

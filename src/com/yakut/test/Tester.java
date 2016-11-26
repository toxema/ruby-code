package com.yakut.test;

import com.yakut.util.FileIO;
import java.util.List;

/**
 *
 * @author yakut
 */
public class Tester {

          public static void main(String[] args) {
                    List<String> personeller = FileIO.getLines("./cengiz/personel.txt");
                    List<String> hareketler = FileIO.getLines("./cengiz/hareketler.txt");


                    String text = "";
                    for (int k = 0; k < personeller.size(); k++) {
                              boolean bulundu21 = false;
                              boolean bulundu22 = false;
                              String personel = personeller.get(k);
                              for (int j = 0; j < hareketler.size(); j++) {
                                        String hareket = hareketler.get(j);
                                        if (hareket.startsWith(personel + ",21")) {

                                                  bulundu21 = true;
                                        }
                                        if (hareket.startsWith(personel + ",22")) {
                                                  bulundu22 = true;
                                        }
                                        if (bulundu21 && bulundu22) {
                                                  break;
                                        }
                              }

                              if (!bulundu21) {
                                        System.out.println(personel + ", AA,21.09.2015,21.09.2015");
                              }
                              if (!bulundu22) {
                                        System.out.println(personel + ", AA,22.09.2015,22.09.2015");
                              }
                    }


          }
}

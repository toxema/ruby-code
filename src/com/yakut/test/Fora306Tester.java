/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.test;

import com.yakut.util.FileIO;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class Fora306Tester {

            Logger logger = Logger.getRootLogger();
           long hex = 0xFFFFFFFF;
            public void isle(String text) {
                        logger.debug(text);
                        if (text.indexOf("CD") > -1) {
                                    text = text.replace("-", "");
                                    String reader = text.substring(10, 11);
                                    String tag = text.substring(18, 28);

                                    long wig = Long.parseLong(tag.trim());
                                    String hexTag = Long.toHexString(wig);

                                    try {
                                                Integer.parseInt(hexTag);
                                    } catch (Exception ex) {
                                                wig = hex - wig + 1;
                                    }


                                    tag = (Integer.toHexString((int) wig));
                                    tag = "00000000" + tag;
                                    tag = tag.substring(tag.length() - 8);
                                    logger.info(reader + " - " + tag);
                                    int anten = Integer.parseInt(reader);
                                    logger.info("tag:" + tag + ", anten: " + anten);
                                  //  tagOkutuldu(tag, anten);
                        }
            }


            public static void main(String[] args) {
                        Fora306Tester f=new Fora306Tester();
                        
                        List<String> list = FileIO.getLines("log.txt");
                        
                        for(String s:list){
                                    f.isle(s);
//                                    try {
//                                                Thread.sleep(100);
//                                    } catch (InterruptedException ex) {                              
//                                    }
                        }
                        
            }
}

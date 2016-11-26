package com.yakut.test;

import com.yakut.util.DateUtil;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author yakut
 */
public class Run {

          public static void test() {
                    new Run().saatDenGune("26:00");
                    new Run().saatDenGune("15:00");
                    new Run().saatDenGune("07:30");
                    new Run().saatDenGune("03:30");
          }

          public static void toFloatTest() {
                    new Run().toFloat("03:30");
                    new Run().toFloat("11:05");
                    new Run().toFloat("11:10");
                    new Run().toFloat("11:15");
                    new Run().toFloat("11:25");
                    new Run().toFloat("11:35");
                    new Run().toFloat("11:45");
                    new Run().toFloat("11:50");
          }

          public static float toFloat(String saat) {
                    float value = 0.0f;
                    int tam = Integer.parseInt(saat.substring(0, 2));
                    int ondalik = Integer.parseInt(saat.substring(3, 5));
                    ondalik = (ondalik * 100) / 60;
                    value = Float.parseFloat(tam + "." + ondalik);
                    //System.out.println(saat + ": " + value);
                    return value;
          }

          public static float saatDenGune(String saat) {
                    float value = 0.0f;
                    float d1, d2;
                    d1 = toFloat(saat);
                    d2 = toFloat("03:30");

                    value = (d1 / d2);
                    System.out.println(d1 + "  -  " + d2);
                    System.out.println("(" + saat + "/03:30)" + "=" + value + "                    /2  :" + (int) (value / 2));
                    value = (int) value / 2;

                    System.out.println("----------------------------------------------------------------------------------------");
                    return value;
          }

          public static void main(String[] args) {
                    yuvarla(0.13f);
                    yuvarla(0.26f);
                    yuvarla(0.33f);
                    yuvarla(0.43f);
                    yuvarla(0.53f);
                    yuvarla(0.63f);
                    yuvarla(0.73f);
                    yuvarla(0.83f);
                    yuvarla(0.93f);
                    yuvarla(1.03f);
                    yuvarla(1.13f);
                    yuvarla(1.53f);
                    yuvarla(1.93f);


          }

          public static float yuvarla(Float f) {
                    System.out.print("float:" + f + " ");
                    float tam = (float) Math.floor(f);
                    float fark = f - tam;
                    if (fark <0.3f) {
                              f = tam;
                    } 
                    else if (fark <= 0.75f) {
                              f = tam + 0.5f;
                    }else{
                              f=tam+1;
                    }
                    System.out.println(" yuvarla :" + (f));
                    return f;
          }
}

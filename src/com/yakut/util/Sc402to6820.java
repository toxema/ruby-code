package com.yakut.util;

import java.util.List;

public class Sc402to6820 {

          public static String t(int i) {
                    return t(i, 8);
          }

          public static String t(int i, int count) {
                    String hex = "00000000" + i;
                    hex = hex.substring(hex.length() - count, hex.length());
                    return hex;
          }

          public static String t(String s, int count) {
                    String hex = "00000000" + s;
                    hex = hex.substring(hex.length() - count, hex.length());
                    return hex;
          }

          public static String to6820(String codeSc402) {
                    String code6820;
                    String hex = Integer.toHexString(Integer.parseInt(codeSc402));
                    hex = "00000000" + hex;
                    hex = hex.substring(hex.length() - 8, hex.length());
                    String hexLeft = hex.substring(0, 4);
                    String hexRight = hex.substring(4, 8);
                    code6820 = t(Integer.parseInt(hexLeft, 16), 3) + "" + t(Integer.parseInt(hexRight, 16), 5);
                    return code6820;
          }

          public static String toSc402(String codex) {
                    String code;
                    String left = codex.substring(0, 3);
                    String right = codex.substring(3, 8);
                    //  System.out.println(left + " " + right);
                    left = Integer.toHexString(Integer.parseInt(left));
                    right = Integer.toHexString(Integer.parseInt(right));
                    //System.out.println(left + " " + right);
                    code = t(left,4) + t(right,4);
                    code = t(Integer.parseInt(code, 16), 8);
                    return code;
          }

          public static void dosyadanCevir() {
                    List<String> list = FileIO.getLines("list.txt");
                    for (String s : list) {
                              System.out.println(s);
                              //  System.out.println("update person1 set kart_no=_"+toSc402(s)+"_ where sicil_no=_");
                    }
          }

          public static void main(String[] args) {
              //      System.out.println(to6820("05836637"));
                    System.out.println(toSc402("67210648"));
                    //   dosyadanCevir();
          }
}

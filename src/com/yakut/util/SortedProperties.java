
package com.yakut.util;

/**
 *
 * @author http://www.rgagnon.com/javadetails/java-0614.html
 */
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class SortedProperties extends Properties {
  /**
   * Overrides, called by the store method.
   */
  @SuppressWarnings("unchecked")
  public synchronized Enumeration keys() {
     Enumeration keysEnum = super.keys();
     Vector keyList = new Vector();
     while(keysEnum.hasMoreElements()){
       keyList.add(keysEnum.nextElement());
     }
     Collections.sort(keyList);
     return keyList.elements();
  }

  /**
   * Demo
   */
  public static void main(String[] args) throws Exception {
    Properties p = new Properties();
    p.put("B", "value B");
    p.put("C", "value C");
    p.put("A", "value A");
    p.put("D", "value D");
    java.io.FileOutputStream fos = new java.io.FileOutputStream("p.props");
    p.store(fos, "regular props");
  }
}
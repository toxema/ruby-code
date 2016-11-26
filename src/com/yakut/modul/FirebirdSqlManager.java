/*
 * AccessYedek.java
 *
 * Created on 14.Mar.2011, 16:01:03
 */
package com.yakut.modul;
 
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
 
import com.yakut.util.Database;
import com.yakut.util.Excell;
import com.yakut.util.ExceptionHandler; 
import com.yakut.util.Setting;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
  
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yakut
 */
public class FirebirdSqlManager extends javax.swing.JFrame {

        Setting setting = Setting.getSettings();
        ResultSet sonuc;
        Database db = null;
        List<String> sqlHistory = new ArrayList<String>();
        DefaultComboBoxModel comboModel;
        RaporTableCellRenderer renderer;

        public FirebirdSqlManager() {
                initComponents();
        }

        public JFrame setup() {
                this.setBounds(setting.getBounds("firebirdManagerFrame"));
                jDialog1.setBounds(setting.getBounds("connectionDialog"));
                jDialog1.setLocationRelativeTo(null);
                setLocationRelativeTo(null);
                this.setVisible(true);
                comboModel = (DefaultComboBoxModel) jComboBox3.getModel();
                renderer = new RaporTableCellRenderer(6);
                jTable1.setDefaultRenderer(Object.class, renderer);

                return this;
        }

        public void baglan() {
                if (db == null) {
                        db = new Database(jComboBox2.getSelectedItem().toString(), jTextField2.getText());
                        try {
                                if (db.begin()) {
                                        jLabel1.setText("Veritabanı bağlantısı sağlandı");
                                        jButton1.setEnabled(true);
                                        jButton5.setEnabled(true);

                                }
                        } catch (Exception ex) {
                                db = null;
                                jButton1.setEnabled(false);
                                jButton5.setEnabled(false);
                                ExceptionHandler.onException("Veritabanına bağlantı sağlanamadı. ", ex);
                        }
                } else {
                        jButton1.setEnabled(false);
                        jButton5.setEnabled(false);
                        db.end();
                        db = null;
                }
        }

        public JPanel getPanel() {
                return jPanel3;
        }

        @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jDialog1 = new javax.swing.JDialog();
                    jTextField2 = new javax.swing.JTextField();
                    jButton4 = new javax.swing.JButton();
                    jLabel2 = new javax.swing.JLabel();
                    jLabel3 = new javax.swing.JLabel();
                    jButton6 = new javax.swing.JButton();
                    jComboBox2 = new javax.swing.JComboBox();
                    jDialog2 = new javax.swing.JDialog();
                    jLabel4 = new javax.swing.JLabel();
                    jPanel4 = new javax.swing.JPanel();
                    jLabel7 = new javax.swing.JLabel();
                    jButton11 = new javax.swing.JButton();
                    jLabel5 = new javax.swing.JLabel();
                    jLabel6 = new javax.swing.JLabel();
                    jButton10 = new javax.swing.JButton();
                    jComboBox1 = new javax.swing.JComboBox();
                    jButton7 = new javax.swing.JButton();
                    jButton9 = new javax.swing.JButton();
                    jPopupMenu1 = new javax.swing.JPopupMenu();
                    jMenuItem1 = new javax.swing.JMenuItem();
                    jPanel3 = new javax.swing.JPanel();
                    jPanel2 = new javax.swing.JPanel();
                    jButton3 = new javax.swing.JButton();
                    jButton2 = new javax.swing.JButton();
                    jLabel1 = new javax.swing.JLabel();
                    jButton8 = new javax.swing.JButton();
                    jPanel1 = new javax.swing.JPanel();
                    jButton1 = new javax.swing.JButton();
                    jButton5 = new javax.swing.JButton();
                    jScrollPane2 = new javax.swing.JScrollPane();
                    jTextArea1 = new javax.swing.JTextArea();
                    jComboBox3 = new javax.swing.JComboBox();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();

                    jDialog1.setAlwaysOnTop(true);
                    jDialog1.setLocationByPlatform(true);
                    jDialog1.setModal(true);

                    jTextField2.setText("127.0.0.1");

                    jButton4.setText("Bağlan");
                    jButton4.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton4ActionPerformed(evt);
                              }
                    });

                    jLabel2.setText("Server");

                    jLabel3.setText("Database");

                    jButton6.setText("Vazgeç");
                    jButton6.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton6ActionPerformed(evt);
                              }
                    });

                    jComboBox2.setEditable(true);
                    jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C:\\\\UzmanPdks\\\\data\\\\imbat3\\\\DATA.FDB", "C:\\\\UzmanPdks\\\\data\\\\cevher\\\\DATA.FDB", "C:\\\\agsisOgs\\\\data\\\\data.fdb", "C:\\\\azone\\\\data\\\\data.fdb", "C:\\\\Access\\\\data\\\\data.fdb", "C:\\\\yemekhane\\\\data\\\\data.fdb", "C:\\\\Asansor\\\\data\\\\data.fdb", "/home/yakut/data/azone.okul/data.fdb", "/home/yakut/data/bucaspor/pc1/data.FDB" }));

                    javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
                    jDialog1.getContentPane().setLayout(jDialog1Layout);
                    jDialog1Layout.setHorizontalGroup(
                              jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jDialog1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(jDialog1Layout.createSequentialGroup()
                                                            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                      .addComponent(jLabel2)
                                                                      .addComponent(jLabel3))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addGroup(jDialog1Layout.createSequentialGroup()
                                                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                      .addComponent(jComboBox2, 0, 242, Short.MAX_VALUE)))
                                                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                                                            .addGap(0, 0, Short.MAX_VALUE)
                                                            .addComponent(jButton6)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jButton4)))
                                        .addContainerGap())
                    );
                    jDialog1Layout.setVerticalGroup(
                              jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jDialog1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jLabel3)
                                                  .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton4)
                                                  .addComponent(jButton6))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );

                    jLabel4.setBackground(new java.awt.Color(0, 51, 102));
                    jLabel4.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    jLabel4.setText("#{personel.adi}");
                    jLabel4.setOpaque(true);

                    jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                    jLabel7.setText("İzin Türü");

                    jButton11.setText("Sil");

                    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                    jLabel5.setText("Başlangıç");

                    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                    jLabel6.setText("Bitiş");

                    jButton10.setText("Kaydet");

                    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                    jPanel4.setLayout(jPanel4Layout);
                    jPanel4Layout.setHorizontalGroup(
                              jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(jPanel4Layout.createSequentialGroup()
                                                            .addComponent(jButton11)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jButton10))
                                                  .addGroup(jPanel4Layout.createSequentialGroup()
                                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                      .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jComboBox1, 0, 196, Short.MAX_VALUE)))
                                        .addContainerGap())
                    );
                    jPanel4Layout.setVerticalGroup(
                              jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                  .addComponent(jComboBox1)
                                                  .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton10)
                                                  .addComponent(jButton11))
                                        .addGap(0, 0, 0))
                    );

                    javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
                    jDialog2.getContentPane().setLayout(jDialog2Layout);
                    jDialog2Layout.setHorizontalGroup(
                              jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                              .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                    jDialog2Layout.setVerticalGroup(
                              jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jDialog2Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );

                    jButton7.setText("jButton7");

                    jButton9.setText("jButton9");

                    jMenuItem1.setText("Bu sütuna göre renklendir.");
                    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jMenuItem1ActionPerformed(evt);
                              }
                    });
                    jPopupMenu1.add(jMenuItem1);

                    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                    addWindowListener(new java.awt.event.WindowAdapter() {
                              public void windowClosing(java.awt.event.WindowEvent evt) {
                                        formWindowClosing(evt);
                              }
                    });

                    jPanel3.setLayout(new java.awt.BorderLayout());

                    jButton3.setText("Aktar");
                    jButton3.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton3ActionPerformed(evt);
                              }
                    });

                    jButton2.setText("Excell");
                    jButton2.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton2ActionPerformed(evt);
                              }
                    });

                    jLabel1.setText("SQL");

                    jButton8.setText("Con");
                    jButton8.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton8ActionPerformed(evt);
                              }
                    });

                    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                    jPanel2.setLayout(jPanel2Layout);
                    jPanel2Layout.setHorizontalGroup(
                              jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );
                    jPanel2Layout.setVerticalGroup(
                              jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton2)
                                                  .addComponent(jButton3)
                                                  .addComponent(jLabel1)
                                                  .addComponent(jButton8))
                                        .addGap(0, 0, 0))
                    );

                    jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_END);

                    jButton1.setText("SQL");
                    jButton1.setEnabled(false);
                    jButton1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton1ActionPerformed(evt);
                              }
                    });

                    jButton5.setText("Ex");
                    jButton5.setEnabled(false);
                    jButton5.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton5ActionPerformed(evt);
                              }
                    });

                    jTextArea1.setColumns(20);
                    jTextArea1.setRows(5);
                    jTextArea1.setText("select * from personel");
                    jScrollPane2.setViewportView(jTextArea1);

                    jComboBox3.addItemListener(new java.awt.event.ItemListener() {
                              public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                        jComboBox3İtemStateChanged(evt);
                              }
                    });

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                                                  .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(0, 0, 0)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                  .addComponent(jButton5, 0, 0, Short.MAX_VALUE)
                                                  .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap())
                    );
                    jPanel1Layout.setVerticalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, 0)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                  .addComponent(jButton5)
                                                  .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addGap(0, 0, 0))
                    );

                    jPanel3.add(jPanel1, java.awt.BorderLayout.PAGE_START);

                    jTable1.setAutoCreateRowSorter(true);
                    jTable1.setModel(new javax.swing.table.DefaultTableModel(
                              new Object [][] {

                              },
                              new String [] {

                              }
                    ));
                    jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    jTable1.setComponentPopupMenu(jPopupMenu1);
                    jTable1.setRowHeight(24);
                    jScrollPane1.setViewportView(jTable1);

                    jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                    getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

                    pack();
          }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

            sql();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            try {
                    Excell e = new Excell();
                    e.exceleAktar(jTable1);
            } catch (Exception ex) {
                    ExceptionHandler.onException("Excel Aktarmada Hata Oluştu", ex);
            }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
            export();

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

            baglan();
            if (db == null) {
                    jButton4.setText("Bağlan");
            } else {
                    jButton4.setText("Kes");
            }
            jDialog1.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed
        JFileChooser chooser = new JFileChooser();
        String dizin = "";
        String programDizin;

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
            execute();

    }//GEN-LAST:event_jButton5ActionPerformed

            private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                    jDialog1.setVisible(true);
            }//GEN-LAST:event_jButton8ActionPerformed

            private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                    jDialog1.dispose();
            }//GEN-LAST:event_jButton6ActionPerformed

            private void jComboBox3İtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3İtemStateChanged
                    if (evt.getStateChange() == ItemEvent.SELECTED) {
                            jTextArea1.setText(jComboBox3.getSelectedItem().toString());
                    }
            }//GEN-LAST:event_jComboBox3İtemStateChanged

            private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
                    int k = jTable1.getSelectedColumn();
                    System.out.println("k:" + k);
                    if (k > -1) {
                            renderer.setColumnNumber(k);
                            jTable1.setDefaultRenderer(Object.class, renderer);
                            jTable1.repaint();
                    }
            }//GEN-LAST:event_jMenuItem1ActionPerformed

        private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                notifyClose();
        }//GEN-LAST:event_formWindowClosing

        public void notifyClose() {
                setting.setBounds("connectionDialog", jDialog1.getBounds());
                setting.setBounds("firebirdManagerFrame", this.getBounds());
                setting.saveProperties();

        }
        String metadataOlustur = "gbak.exe\" -g -m -user SYSDBA -password masterkey ";
        String metaDatadanVeriTabaniOlustur = "gbak.exe\" -c -v -user SYSDBA -password masterkey ";

        public static void main(String args[]) {
                try {
                        UIManager.setLookAndFeel(new NimbusLookAndFeel());
                } catch (UnsupportedLookAndFeelException ex) {
                }
                java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                                new FirebirdSqlManager().setup().setVisible(true);
                        }
                });
        }
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton10;
          private javax.swing.JButton jButton11;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JButton jButton4;
          private javax.swing.JButton jButton5;
          private javax.swing.JButton jButton6;
          private javax.swing.JButton jButton7;
          private javax.swing.JButton jButton8;
          private javax.swing.JButton jButton9;
          private javax.swing.JComboBox jComboBox1;
          private javax.swing.JComboBox jComboBox2;
          private javax.swing.JComboBox jComboBox3;
          private javax.swing.JDialog jDialog1;
          private javax.swing.JDialog jDialog2;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JLabel jLabel4;
          private javax.swing.JLabel jLabel5;
          private javax.swing.JLabel jLabel6;
          private javax.swing.JLabel jLabel7;
          private javax.swing.JMenuItem jMenuItem1;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JPanel jPanel3;
          private javax.swing.JPanel jPanel4;
          private javax.swing.JPopupMenu jPopupMenu1;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JScrollPane jScrollPane2;
          private javax.swing.JTable jTable1;
          private javax.swing.JTextArea jTextArea1;
          private javax.swing.JTextField jTextField2;
          // End of variables declaration//GEN-END:variables

        private void sql() throws HeadlessException {
                long bas = System.currentTimeMillis();
                if (db != null) {
                        try {
                                comboModel.addElement(jTextArea1.getText());
                                sonuc = db.sql(jTextArea1.getText());
                                DefaultTableModel model = new DefaultTableModel();
                                int colCount = sonuc.getMetaData().getColumnCount();
                                System.out.println("col count:" + colCount);
                                String[] cols = new String[colCount];
                                //  List<Object[]> data=new ArrayList<Object[]>();
                                for (int k = 0; k < colCount; k++) {
                                        cols[k] = sonuc.getMetaData().getColumnName(k + 1);
                                }
                                //     data.add(cols);
                                model.setColumnIdentifiers(cols);

                                Object[] row = new Object[colCount];
                                while (sonuc.next()) {
                                        for (int k = 0; k < colCount; k++) {
                                                row[k] = sonuc.getObject(k + 1);
                                        }
                                        //    data.add(row);
                                        model.addRow(row);
                                }
                                jTable1.setModel(model);
                                jLabel1.setText(jTable1.getRowCount() + " kayıt Listlendi");
                                System.out.println("toplam Kayıt:" + jTable1.getRowCount());

                        } catch (Exception ex) {
                                ExceptionHandler.onException("Sql çalıştırma hatası", ex);
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "Önce veritabanına bağlanın.");
                }

                System.out.println("2   :" + (System.currentTimeMillis() - bas));
        }

        private void execute() throws HeadlessException {
                comboModel.addElement(jTextArea1.getText());
                db.execute(jTextArea1.getText());
                JOptionPane.showMessageDialog(this, "İşlem başarılı");
        }

        private void export() {
                try {
                        File f = new File("rapor-" + new SimpleDateFormat("dd-MM-yyyy_HH_mm").format(new Date()) + ".txt");
                        f.createNewFile();
                        FileWriter fr = new FileWriter(f);
                        String header = "";
                        for (int k = 0; k < jTable1.getColumnCount(); k++) {
                                header += jTable1.getColumnModel().getColumn(k).getHeaderValue().toString() + ",";
                        }
                        fr.write(header + "\r\n");
                        String row = "";
                        for (int k = 0; k < jTable1.getRowCount(); k++) {
                                row = "";
                                for (int j = 0; j < jTable1.getColumnCount(); j++) {
                                        row += jTable1.getValueAt(k, j) + ",";
                                }
                                fr.write(row + "\r\n");
                        }
                        fr.close();
                        Runtime runtime = Runtime.getRuntime();
                        Process p = runtime.exec("" + f.getAbsolutePath());
                } catch (Exception ex) {
                        ExceptionHandler.onException("Dışa Aktarmada Hata Oluştu", ex);
                }
        }
}

package com.yakut.karabaglar;

import com.yakut.terminal.Move;
import com.yakut.util.Database;
import com.yakut.util.DateUtil;
import com.yakut.util.Excell;
import com.yakut.util.Setting;
//import com.yakut.zksofware.T36;
import java.awt.Color;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

public class KarabaglarMail extends javax.swing.JFrame {

          Database db = null;
          Setting setting = Setting.getSettings();
          Map<String, String> mailList = new HashMap<>();
          Logger logger = Logger.getRootLogger();
          Color backgroundColor;// = setting.getColor("theme.backgroud.color");
          Color foregroundColor;//= setting.getColor("theme.foregroud.color");

          public KarabaglarMail() {
                    initComponents();
                    init();
          }

          public void init() {
                    String path = setting.getProperty("pdks.database.path", "c:\\UzmanPdks\\data\\2015\\data.fdb");
                    String server = setting.getProperty("pdks.database.server", "127.0.0.1");
                    File f = new File("./rapor/");
                    if (!f.exists()) {
                              f.mkdir();
                    }
                    db = new Database(path, server);
                    db.connect();
                    // deparmanDialog tanımlamaları
                    departmanDialog.setSize(487, 385);
                    departmanDialog.setLocationRelativeTo(null);
                    departmanDialog.setModal(true);
                    departmanTable.setRowHeight(setting.getTableRowHeight());


                    initTheme();
          }

          public void initTheme() {
                    backgroundColor = setting.getColor("theme.backgroud.color");
                    foregroundColor = setting.getColor("theme.foregroud.color");
                    // anaPanel Tanımlamaları           
                    anaPanel.setBackground(backgroundColor);
                    ustPanel.setBackground(backgroundColor);
                    cihazPanel.setBackground(backgroundColor);
                    jLabel1.setForeground(foregroundColor);
                    jLabel2.setForeground(foregroundColor);
                    jLabel3.setForeground(foregroundColor);
                    dialogPanel.setBackground(backgroundColor);
          }

          public void begin() {
                    departmanListele();
                    terminalleriYukle();
          }

          public void departmanListele() {
                    mailList.clear();
                    DefaultTableModel tableModel = new DefaultTableModel() {
                              @Override
                              public boolean isCellEditable(int row, int column) {
                                        boolean value = false;
                                        if (column == 2) {
                                                  value = true;
                                        }
                                        return value;
                              }
                    };

                    tableModel.setColumnIdentifiers(new String[]{"Kod", "Departman", "Email"});

                    ResultSet sonuc = db.sql("SELECT * FROM DEPARTMAN");
                    try {
                              while (sonuc.next()) {
                                        String kod = sonuc.getString("DEPART_KODU");
                                        String departman = sonuc.getString("DEPART_ADI");
                                        String email = setting.getProperty("departman." + kod + ".email");
                                        if (!email.isEmpty()) {
                                                  mailList.put(kod, email);
                                        }
                                        tableModel.addRow(new String[]{kod, departman, email});
                              }

                    } catch (Exception ex) {
                              ex.printStackTrace();
                    }
                    departmanTable.setModel(tableModel);
                    sutunGenislikleriniAyarla();
          }
//          List<T36> deviceList = new ArrayList<>();
          List<JLabel> labelList = new ArrayList<>();

          public void terminalleriYukle() {
//                    deviceList.clear();
                    labelList.clear();
                    for (int k = 0; k < 50; k++) {
                              String line = setting.getProperty("terminal." + k + ".baglanti");
                              if (line == null || line.isEmpty()) {
                                        break;
                              } else {
                                        String[] row = line.split(",");
                                        String ip = row[0];
                                        String ad = "Terminal";
                                        if (row.length > 1) {
                                                  ad = row[1];
                                        }
//                                        T36 t = new T36(ip);
//                                        deviceList.add(t);
                                        JLabel label = new JLabel(ip + "  :  " + ad);
                                        label.setForeground(foregroundColor);
                                        label.setIcon(redLabel.getIcon());
                                        labelList.add(label);
                                        cihazPanel.add(label);
                              }
                    }
                    cihazPanel.updateUI();
          }

          public void sutunGenislikleriniAyarla() {
                    int kodWidth = setting.getInt("departmantable.kod.width");
                    departmanTable.getColumnModel().getColumn(0).setWidth(kodWidth);
                    departmanTable.getColumnModel().getColumn(0).setMaxWidth(kodWidth);
                    departmanTable.getColumnModel().getColumn(0).setPreferredWidth(kodWidth);

          }

          public void mailListesiKaydet() {
                    mailList.clear();
                    for (int k = 0; k < departmanTable.getRowCount(); k++) {
                              String kod = (String) departmanTable.getValueAt(k, 0);
                              String mail = (String) departmanTable.getValueAt(k, 2);
                              setting.setProperty("departman." + kod + ".email", mail);
                              if (!mail.isEmpty()) {
                                        mailList.put(kod, mail);
                              }
                    }
                    setting.saveProperties();
          }

          public void mailGonder() {
                    new Thread() {
                              @Override
                              public void run() {
                                        jButton1.setEnabled(false);
                                        gonder();
                                        jButton1.setEnabled(true);
                              }
                    }.start();

          }

          public void gonder() {
                    jProgressBar1.setMaximum(mailList.size());
                    int position = 0;
                    Set<String> keys = mailList.keySet();
                    for (String depCode : keys) {
                              position++;
                              String mail = mailList.get(depCode);
                              jLabel1.setText(depCode + "=" + mail);
                              System.out.println(depCode + "=" + mail);
                              jProgressBar1.setValue(position);
                              hareketRaporunuGonder(depCode, mail);
                    }
          }

          public void hareketRaporunuGonder(String depCode, String mail) {
                    File rapor = hareketListesiniAl(depCode);
                    raporGonder(mail, rapor);
          }

          public void raporGonder(String mail, File rapor) {
                    System.out.println(mail + " >" + rapor.getName() + " dosyası gönderildi");
          }
          JTable table = new JTable();
          Excell excell = new Excell();

          public File hareketListesiniAl(String departman) {
                    File raporDosyasi = null;
                    String sql = "select   p.sicil_no,p.adi, p.soyadi, "
                            + "g.tarih,  g.hareket, "
                            + "d.depart_adi,  b.bolum_adi,gr.gorev_adi "
                            + "from gunluk_hareket g "
                            + "left join personel p on p.sicil_no=g.sicil_no "
                            + "left join departman d on d.depart_kodu=p.departman "
                            + "left join Bolum b on b.bolum_kodu=p.bolum "
                            + "left join Gorev gr on gr.gorev_kodu=p.gorevi "
                            + "where g.tarih between ':basTarih' and   ':bitTarih' "
                            + "and d.depart_kodu=':departmanKodu' ";

                    try {

                              sql = sql.replaceAll(":basTarih", DateUtil.fullFormatDate(DateUtil.getGunBaslangis()))
                                      .replaceAll(":bitTarih", DateUtil.fullFormatDate(DateUtil.getGunBitis()))
                                      .replaceAll(":departmanKodu", departman);

                              ResultSet sonuc = db.sql(sql);
                              DefaultTableModel model = new DefaultTableModel();
                              int colCount = sonuc.getMetaData().getColumnCount();
                              System.out.println("col count:" + colCount);
                              String[] cols = new String[colCount];
                              for (int k = 0; k < colCount; k++) {
                                        cols[k] = sonuc.getMetaData().getColumnName(k + 1);
                              }
                              model.setColumnIdentifiers(cols);
                              Object[] row = new Object[colCount];
                              while (sonuc.next()) {
                                        for (int k = 0; k < colCount; k++) {
                                                  row[k] = sonuc.getObject(k + 1);
                                        }
                                        model.addRow(row);
                              }
                              table.setModel(model);
                              raporDosyasi = new File("./rapor/rapor-" + departman + "-" + DateUtil.formatDate(new Date()) + ".xls");
                              excell.exceleAktar(table, raporDosyasi);

                    } catch (Exception ex) {
                              ex.printStackTrace();
                    }
                    return raporDosyasi;
          }
          
          List<Move> hareketList = new ArrayList<>();

          public void veriTopla() {
//                    for (T36 device : deviceList) {
//                              device.getData();
//                              hareketList.addAll(device.getData());
//                    }
          }

          @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    departmanDialog = new javax.swing.JDialog();
                    dialogPanel = new javax.swing.JPanel();
                    jButton3 = new javax.swing.JButton();
                    jButton5 = new javax.swing.JButton();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    departmanTable = new javax.swing.JTable();
                    jButton4 = new javax.swing.JButton();
                    redLabel = new javax.swing.JLabel();
                    greenLabel = new javax.swing.JLabel();
                    anaPanel = new javax.swing.JPanel();
                    jLabel1 = new javax.swing.JLabel();
                    jButton1 = new javax.swing.JButton();
                    jProgressBar1 = new javax.swing.JProgressBar();
                    jSeparator1 = new javax.swing.JSeparator();
                    jScrollPane2 = new javax.swing.JScrollPane();
                    cihazPanel = new javax.swing.JPanel();
                    jButton2 = new javax.swing.JButton();
                    ustPanel = new javax.swing.JPanel();
                    jLabel2 = new javax.swing.JLabel();
                    jLabel3 = new javax.swing.JLabel();
                    jLabel4 = new javax.swing.JLabel();
                    jLabel5 = new javax.swing.JLabel();
                    jButton6 = new javax.swing.JButton();

                    departmanDialog.setTitle("Departman Listesi");
                    departmanDialog.setAlwaysOnTop(true);

                    jButton3.setText("Uygula");
                    jButton3.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton3ActionPerformed(evt);
                              }
                    });

                    jButton5.setText("İptal");
                    jButton5.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton5ActionPerformed(evt);
                              }
                    });

                    departmanTable.setModel(new javax.swing.table.DefaultTableModel(
                              new Object [][] {
                                        {null, null, null},
                                        {null, null, null},
                                        {null, null, null},
                                        {null, null, null}
                              },
                              new String [] {
                                        "Code", "Departman", "Mail"
                              }
                    ) {
                              boolean[] canEdit = new boolean [] {
                                        false, false, true
                              };

                              public boolean isCellEditable(int rowIndex, int columnIndex) {
                                        return canEdit [columnIndex];
                              }
                    });
                    jScrollPane1.setViewportView(departmanTable);

                    jButton4.setText("Tamam");
                    jButton4.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton4ActionPerformed(evt);
                              }
                    });

                    javax.swing.GroupLayout dialogPanelLayout = new javax.swing.GroupLayout(dialogPanel);
                    dialogPanel.setLayout(dialogPanelLayout);
                    dialogPanelLayout.setHorizontalGroup(
                              dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPanelLayout.createSequentialGroup()
                                        .addContainerGap(195, Short.MAX_VALUE)
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton3)
                                        .addContainerGap())
                              .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    );
                    dialogPanelLayout.setVerticalGroup(
                              dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(dialogPanelLayout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton3)
                                                  .addComponent(jButton4)
                                                  .addComponent(jButton5))
                                        .addContainerGap())
                    );

                    departmanDialog.getContentPane().add(dialogPanel, java.awt.BorderLayout.CENTER);

                    redLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/karabaglar/red.png"))); // NOI18N
                    redLabel.setText("192.168.2.2013  :  KARABAĞLAR EVLENDİRME ");

                    greenLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/karabaglar/green.png"))); // NOI18N
                    greenLabel.setText("jLabel4");

                    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                    setTitle("Email Gonder - Kontrolsis Elektronik");
                    addWindowListener(new java.awt.event.WindowAdapter() {
                              public void windowClosing(java.awt.event.WindowEvent evt) {
                                        formWindowClosing(evt);
                              }
                    });

                    jLabel1.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel1.setText("Mail İşlemi");

                    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/karabaglar/gnome-stock-mail-snd.png"))); // NOI18N
                    jButton1.setToolTipText("Gönder");
                    jButton1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton1ActionPerformed(evt);
                              }
                    });

                    jProgressBar1.setStringPainted(true);

                    cihazPanel.setLayout(new javax.swing.BoxLayout(cihazPanel, javax.swing.BoxLayout.PAGE_AXIS));
                    jScrollPane2.setViewportView(cihazPanel);

                    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/karabaglar/config.png"))); // NOI18N
                    jButton2.setToolTipText("Departmanlara ait mail adreslerini belirle");
                    jButton2.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton2ActionPerformed(evt);
                              }
                    });

                    jLabel2.setBackground(new java.awt.Color(255, 255, 255));
                    jLabel2.setForeground(new java.awt.Color(255, 255, 255));
                    jLabel2.setText("Parmak izi Cihazları");

                    jLabel3.setFont(new java.awt.Font("sansserif", 1, 36)); // NOI18N
                    jLabel3.setText("Data24 Postacı");

                    jLabel4.setText("Bir sonraki işlem için kalan süre");

                    jLabel5.setText("12:04:23");

                    jButton6.setText("şimdi topla");
                    jButton6.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton6ActionPerformed(evt);
                              }
                    });

                    javax.swing.GroupLayout ustPanelLayout = new javax.swing.GroupLayout(ustPanel);
                    ustPanel.setLayout(ustPanelLayout);
                    ustPanelLayout.setHorizontalGroup(
                              ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(ustPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addGroup(ustPanelLayout.createSequentialGroup()
                                                            .addComponent(jLabel2)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(ustPanelLayout.createSequentialGroup()
                                                                      .addGap(172, 172, 172)
                                                                      .addComponent(jButton6))
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ustPanelLayout.createSequentialGroup()
                                                                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                      .addComponent(jLabel5)))
                                                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ustPanelLayout.createSequentialGroup()
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel4)))
                                        .addContainerGap())
                    );
                    ustPanelLayout.setVerticalGroup(
                              ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ustPanelLayout.createSequentialGroup()
                                        .addGroup(ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                  .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ustPanelLayout.createSequentialGroup()
                                                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jLabel4)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jLabel5)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(ustPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                  .addComponent(jLabel2)
                                                  .addComponent(jButton6)))
                    );

                    javax.swing.GroupLayout anaPanelLayout = new javax.swing.GroupLayout(anaPanel);
                    anaPanel.setLayout(anaPanelLayout);
                    anaPanelLayout.setHorizontalGroup(
                              anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(anaPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addGroup(anaPanelLayout.createSequentialGroup()
                                                            .addGroup(anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                      .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                      .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                      .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGap(6, 6, 6))
                                                  .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                                  .addComponent(ustPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                        .addContainerGap())
                    );
                    anaPanelLayout.setVerticalGroup(
                              anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(anaPanelLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(ustPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(anaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                  .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap())
                    );

                    getContentPane().add(anaPanel, java.awt.BorderLayout.CENTER);

                    pack();
          }// </editor-fold>//GEN-END:initComponents

          private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    departmanListele();
                    departmanDialog.setVisible(true);
          }//GEN-LAST:event_jButton2ActionPerformed

          private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                    mailListesiKaydet();
          }//GEN-LAST:event_jButton3ActionPerformed

          private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                    mailListesiKaydet();
                    departmanDialog.dispose();
          }//GEN-LAST:event_jButton4ActionPerformed

          private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                    departmanDialog.dispose();
          }//GEN-LAST:event_jButton5ActionPerformed

          private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                    mailGonder();
          }//GEN-LAST:event_jButton1ActionPerformed

          private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                    logger.warn("Program kapanıyor.");
                    setting.saveProperties();
          }//GEN-LAST:event_formWindowClosing

          private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                    veriTopla();
          }//GEN-LAST:event_jButton6ActionPerformed

          /**
           * @param args the command line arguments
           */
          public static void main(String args[]) {
                    /* Set the Nimbus look and feel */
                    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
                     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                     */
                    try {
                              for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                        if ("Nimbus".equals(info.getName())) {
                                                  javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                                  break;
                                        }
                              }
                    } catch (ClassNotFoundException ex) {
                              java.util.logging.Logger.getLogger(KarabaglarMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                              java.util.logging.Logger.getLogger(KarabaglarMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                              java.util.logging.Logger.getLogger(KarabaglarMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                              java.util.logging.Logger.getLogger(KarabaglarMail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                    }
                    //</editor-fold>

                    /* Create and display the form */
                    java.awt.EventQueue.invokeLater(new Runnable() {
                              public void run() {
                                        KarabaglarMail karabaglar = new KarabaglarMail();
                                        karabaglar.setLocationRelativeTo(null);
                                        karabaglar.setVisible(true);
                                        karabaglar.begin();
                              }
                    });
          }
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JPanel anaPanel;
          private javax.swing.JPanel cihazPanel;
          private javax.swing.JDialog departmanDialog;
          private javax.swing.JTable departmanTable;
          private javax.swing.JPanel dialogPanel;
          private javax.swing.JLabel greenLabel;
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JButton jButton4;
          private javax.swing.JButton jButton5;
          private javax.swing.JButton jButton6;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JLabel jLabel4;
          private javax.swing.JLabel jLabel5;
          private javax.swing.JProgressBar jProgressBar1;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JScrollPane jScrollPane2;
          private javax.swing.JSeparator jSeparator1;
          private javax.swing.JLabel redLabel;
          private javax.swing.JPanel ustPanel;
          // End of variables declaration//GEN-END:variables
}

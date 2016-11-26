/*
 * AccessYedek.java
 *
 * Created on 14.Mar.2011, 16:01:03
 */
package com.yakut.modul;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import com.yakut.plugin.VerticalTableHeaderCellRenderer;
import com.yakut.util.Database;
import com.yakut.util.DateUtil;
import com.yakut.util.Excell;
import com.yakut.util.ExceptionHandler;
import com.yakut.util.Setting;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author yakut
 */
public class CengizSoma extends javax.swing.JFrame {

          Setting setting = Setting.getSettings();
          ResultSet sonuc;
          Database db = null;
          List<String> sqlHistory = new ArrayList<String>();
          DefaultComboBoxModel comboModel;
          RaporTableCellRenderer renderer;
          //##########################################
          List<String> departmanKoduList = new ArrayList<>();
          List<String> bolumKoduList = new ArrayList<>();
          List<String> gorevKoduList = new ArrayList<>();
          TableCellRenderer headerRenderer;

          public CengizSoma() {
                    initComponents();
          }

          public JFrame setup() {
                    this.setBounds(setting.getBounds("firebird.manager.frame"));
                    setLocationRelativeTo(null);
                    this.setVisible(true);

                    renderer = new RaporTableCellRenderer(6);
                    jTable1.setDefaultRenderer(Object.class, renderer);
                    jTable2.setDefaultRenderer(Object.class, renderer);
                    jButton5ActionPerformed(null);
                    jButton8ActionPerformed(null);

                    String tarihBas = Setting.getSettings().getProperty("puantaj.baslangic.tarihi", "01.01.2016");
                    String tarihBit = Setting.getSettings().getProperty("puantaj.bitis.tarihi", "31.01.2016");
                    jSpinner1.setValue(DateUtil.parseDate(tarihBas));
                    jSpinner2.setValue(DateUtil.parseDate(tarihBit));

                    begin();

                    headerRenderer = new VerticalTableHeaderCellRenderer();

                    return this;
          }

          public void baglan() {
                    if (db == null) {
                              db = new Database(setting.getProperty("database.path", "c:\\uzmanpdks\\data\\cengiz\\data.fdb"), setting.getProperty("database.server.ip", "127.0.0.1"));
                              try {
                                        if (db.begin()) {
                                                  jLabel1.setText("Veritabanı bağlantısı sağlandı");
                                                  jButton1.setEnabled(true);
                                        }
                              } catch (Exception ex) {
                                        db = null;
                                        jButton1.setEnabled(false);

                                        ExceptionHandler.onException("Veritabanına bağlantı sağlanamadı. ", ex);
                              }
                    } else {
                              jButton1.setEnabled(false);
                              db.end();
                              db = null;
                    }
          }

          public void begin() {
                    baglan();

                    if (db != null) {
                              try {


                                        comboModel = new DefaultComboBoxModel();
                                        comboModel.addElement("Departman Seçiniz...");
                                        departmanKoduList.add("Seçiniz...");
                                        sonuc = db.sql("select * from departman");
                                        while (sonuc.next()) {
                                                  comboModel.addElement(sonuc.getString("depart_adi"));
                                                  departmanKoduList.add(sonuc.getString("depart_kodu"));
                                        }
                                        jComboBox4.setModel(comboModel);
                                        jComboBox7.setModel(comboModel);
                                        //#################################################################3
                                        comboModel = new DefaultComboBoxModel();

                                        comboModel.addElement("Bolum Seçiniz...");
                                        bolumKoduList.add("Seçiniz...");
                                        sonuc = db.sql("select * from bolum");
                                        while (sonuc.next()) {
                                                  comboModel.addElement(sonuc.getString("bolum_adi"));
                                                  bolumKoduList.add(sonuc.getString("bolum_kodu"));
                                        }
                                        jComboBox5.setModel(comboModel);
                                        jComboBox8.setModel(comboModel);
                                        //#################################################################3                                        
                                        comboModel = new DefaultComboBoxModel();

                                        comboModel.addElement("Görev Seçiniz...");
                                        gorevKoduList.add("Seçiniz...");
                                        sonuc = db.sql("select * from gorev");
                                        while (sonuc.next()) {
                                                  comboModel.addElement(sonuc.getString("gorev_adi"));
                                                  gorevKoduList.add(sonuc.getString("gorev_kodu"));
                                        }
                                        jComboBox6.setModel(comboModel);
                                        jComboBox9.setModel(comboModel);
                                        //#################################################################3               

                              } catch (Exception ex) {
                                        ExceptionHandler.onException("Sql çalıştırma hatası", ex);
                              }
                    } else {
                              JOptionPane.showMessageDialog(this, "Önce veritabanına bağlanın.");
                    }

          }

          private void gunlukPuantajRaporu() throws HeadlessException {

                    int NCIzinBas = 11;
                    int NCIzinBit = 16; //ikisi de dahil


                    long bas = System.currentTimeMillis();
                    if (db != null) {
                              try {
                                        String sqlText = jTextArea2.getText();

                                        sqlText += "  AND ((PR.SICIL_NO>='" + jTextField2.getText() + "')  AND  (PR.SICIL_NO<='" + jTextField4.getText() + "')) \n";
                                        if (jComboBox7.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.departman='" + departmanKoduList.get(jComboBox7.getSelectedIndex()) + "')\n ";
                                        }
                                        if (jComboBox8.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.bolum='" + bolumKoduList.get(jComboBox8.getSelectedIndex()) + "')\n ";
                                        }
                                        if (jComboBox9.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.gorevi='" + gorevKoduList.get(jComboBox9.getSelectedIndex()) + "') \n";
                                        }

//                                      --AND((PR.SICIL_NO>='0') and (PR.SICIL_NO<='z'))
//                                        --AND((GP.TARIH>='01.11.2015')AND(GP.TARIH<='30.11.2015'))  

                                        String tarihBas = DateUtil.formatDate((Date) jSpinner1.getValue());
                                        String tarihBit = DateUtil.formatDate((Date) jSpinner2.getValue());

                                        Setting.getSettings().setProperty("puantaj.baslangic.tarihi", tarihBas);
                                        Setting.getSettings().setProperty("puantaj.bitis.tarihi", tarihBit);
                                        Setting.getSettings().saveProperties();

                                        sqlText += "AND((GP.TARIH>='" + tarihBas + "')AND(GP.TARIH<='" + tarihBit + "')) ";

                                        sqlText += " Order by 1,2";
                                        sonuc = db.sql(sqlText);
                                        DefaultTableModel model = new DefaultTableModel();
                                        int colCount = sonuc.getMetaData().getColumnCount();

                                        String[] cols = new String[colCount];
                                        //  List<Object[]> data=new ArrayList<Object[]>();
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
                                        jTable2.setModel(model);
                                        jLabel2.setText(jTable2.getRowCount() + " kayıt Listlendi");
                                        System.out.println("toplam Kayıt:" + jTable2.getRowCount());

                              } catch (Exception ex) {
                                        ExceptionHandler.onException("Sql çalıştırma hatası", ex);
                              }
                    } else {
                              JOptionPane.showMessageDialog(this, "Önce veritabanına bağlanın.");
                    }

                    System.out.println("2   :" + (System.currentTimeMillis() - bas));
          }

          public JPanel getPanel() {
                    return jPanel3;
          }

          @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jPopupMenu1 = new javax.swing.JPopupMenu();
                    jMenuItem1 = new javax.swing.JMenuItem();
                    jPopupMenu2 = new javax.swing.JPopupMenu();
                    jMenuItem2 = new javax.swing.JMenuItem();
                    loadingLabel = new javax.swing.JLabel();
                    jTabbedPane1 = new javax.swing.JTabbedPane();
                    PuantajPanel = new javax.swing.JPanel();
                    jPanel3 = new javax.swing.JPanel();
                    jPanel2 = new javax.swing.JPanel();
                    jToolBar1 = new javax.swing.JToolBar();
                    jLabel1 = new javax.swing.JLabel();
                    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
                    jButton3 = new javax.swing.JButton();
                    jButton2 = new javax.swing.JButton();
                    jPanel1 = new javax.swing.JPanel();
                    jToolBar2 = new javax.swing.JToolBar();
                    jTextField1 = new javax.swing.JTextField();
                    jTextField3 = new javax.swing.JTextField();
                    jComboBox4 = new javax.swing.JComboBox();
                    jComboBox5 = new javax.swing.JComboBox();
                    jComboBox6 = new javax.swing.JComboBox();
                    jButton1 = new javax.swing.JButton();
                    jCheckBox1 = new javax.swing.JCheckBox();
                    jButton5 = new javax.swing.JButton();
                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTable1 = new javax.swing.JTable();
                    jScrollPane2 = new javax.swing.JScrollPane();
                    jTextArea1 = new javax.swing.JTextArea();
                    jPanel4 = new javax.swing.JPanel();
                    jScrollPane3 = new javax.swing.JScrollPane();
                    jTextArea2 = new javax.swing.JTextArea();
                    jPanel5 = new javax.swing.JPanel();
                    jPanel6 = new javax.swing.JPanel();
                    jButton4 = new javax.swing.JButton();
                    jButton6 = new javax.swing.JButton();
                    jLabel2 = new javax.swing.JLabel();
                    jPanel7 = new javax.swing.JPanel();
                    jToolBar3 = new javax.swing.JToolBar();
                    jTextField2 = new javax.swing.JTextField();
                    jTextField4 = new javax.swing.JTextField();
                    jSpinner1 = new javax.swing.JSpinner();
                    jSpinner2 = new javax.swing.JSpinner();
                    jComboBox7 = new javax.swing.JComboBox();
                    jComboBox8 = new javax.swing.JComboBox();
                    jComboBox9 = new javax.swing.JComboBox();
                    jButton7 = new javax.swing.JButton();
                    jButton8 = new javax.swing.JButton();
                    jLabel3 = new javax.swing.JLabel();
                    jScrollPane4 = new javax.swing.JScrollPane();
                    jTable2 = new javax.swing.JTable();

                    jMenuItem1.setText("Bu sütuna göre renklendir.");
                    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jMenuItem1ActionPerformed(evt);
                              }
                    });
                    jPopupMenu1.add(jMenuItem1);

                    jMenuItem2.setText("Bu alana göre renklendir");
                    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jMenuItem2ActionPerformed(evt);
                              }
                    });
                    jPopupMenu2.add(jMenuItem2);

                    loadingLabel.setBackground(new java.awt.Color(51, 51, 51));
                    loadingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    loadingLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/modul/loading1.gif"))); // NOI18N

                    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                    setTitle("Aylık Puantaj Raporu");
                    addWindowListener(new java.awt.event.WindowAdapter() {
                              public void windowClosing(java.awt.event.WindowEvent evt) {
                                        formWindowClosing(evt);
                              }
                    });

                    PuantajPanel.setLayout(new java.awt.BorderLayout());

                    jPanel3.setLayout(new java.awt.BorderLayout());

                    jPanel2.setLayout(new java.awt.BorderLayout());

                    jToolBar1.setFloatable(false);
                    jToolBar1.setRollover(true);

                    jLabel1.setText("SQL");
                    jToolBar1.add(jLabel1);
                    jToolBar1.add(filler1);

                    jButton3.setText("Aktar");
                    jButton3.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton3ActionPerformed(evt);
                              }
                    });
                    jToolBar1.add(jButton3);

                    jButton2.setText("Excell");
                    jButton2.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton2ActionPerformed(evt);
                              }
                    });
                    jToolBar1.add(jButton2);

                    jPanel2.add(jToolBar1, java.awt.BorderLayout.CENTER);

                    jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_END);

                    jPanel1.setLayout(new java.awt.BorderLayout());

                    jToolBar2.setFloatable(false);
                    jToolBar2.setRollover(true);

                    jTextField1.setColumns(5);
                    jTextField1.setText("0");
                    jToolBar2.add(jTextField1);

                    jTextField3.setColumns(5);
                    jTextField3.setText("z");
                    jToolBar2.add(jTextField3);

                    jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar2.add(jComboBox4);

                    jComboBox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar2.add(jComboBox5);

                    jComboBox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar2.add(jComboBox6);

                    jButton1.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
                    jButton1.setText("Listele");
                    jButton1.setFocusable(false);
                    jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    jButton1.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton1ActionPerformed(evt);
                              }
                    });
                    jToolBar2.add(jButton1);

                    jCheckBox1.setSelected(true);
                    jCheckBox1.setText("işlem");
                    jCheckBox1.setFocusable(false);
                    jCheckBox1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    jToolBar2.add(jCheckBox1);

                    jButton5.setText(" V ");
                    jButton5.setFocusable(false);
                    jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                    jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                    jButton5.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton5ActionPerformed(evt);
                              }
                    });
                    jToolBar2.add(jButton5);

                    jPanel1.add(jToolBar2, java.awt.BorderLayout.CENTER);

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
                    jTable1.setGridColor(new java.awt.Color(153, 153, 153));
                    jTable1.setRowHeight(24);
                    jTable1.setShowHorizontalLines(true);
                    jTable1.setShowVerticalLines(true);
                    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                              public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        jTable1MouseClicked(evt);
                              }
                    });
                    jScrollPane1.setViewportView(jTable1);

                    jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                    PuantajPanel.add(jPanel3, java.awt.BorderLayout.CENTER);

                    jTextArea1.setColumns(15);
                    jTextArea1.setRows(8);
                    jTextArea1.setText("SELECT APG.SICIL_NO  \" SICIL \" , PR.ADI, PR.SOYADI,\nDP.DEPART_ADI  \" DEPARTMAN \" , BM.BOLUM_ADI  \" BOLUM \" , GR.GOREV_ADI  \" GOREV \" ,\n\nAPS.NORM_CAL_S  \" CALISMA \" ,\n--APG.NORM_CAL   \" CALISMA \" , \nAPG.IZINSIZ_G  \" KACAK \" , \nAPG.HT_HAK_G  \" HT HAKEDIS \" ,  \nAPG.CT_HAK_G  \" CTS HAKEDİS \" ,\nAPG.GT_HAK_G  \" GT HAKEDIS \" , \nAPG.HT_CAL_G  \" HT CALISMA \" ,\nAPG.CTS_CAL_G  \" CTS CALISMA \" ,\n\nAPIZIN.IZIN_KODG2  \" GOREVLI \" ,\nAPIZIN.IZIN_KODG3  \" DIS GOREV \" ,\nAPIZIN.IZIN_KODG5  \" EGITIM  IZ \" ,\nAPIZIN.IZIN_KODG1  \" VIZITE \" ,\nAPIZIN.IZIN_KODG6  \" SENDIKACI \" ,\n\nAPIZIN.IZIN_KODG12  \" SENDIKAL \" ,\nAPIZIN.IZIN_KODG7  \" HT  IZNI \" ,\nAPIZIN.IZIN_KODG22  \" IDARI IZIN \" ,\nAPIZIN.IZIN_KODG4  \" SUT IZ \" ,\nAPIZIN.IZIN_KODG8  \" DOGUM \" ,\nAPIZIN.IZIN_KODG9  \" OLUM \" ,\nAPIZIN.IZIN_KODG10  \" EVLENME \" ,\nAPIZIN.IZIN_KODG11  \" UCRETLI MAZERET \" ,\nAPIZIN.IZIN_KODG13  \" REFAKAT \" ,\nAPIZIN.IZIN_KODG14  \" YILLIK \" ,\nAPIZIN.IZIN_KODG15  \" HEYET \" ,\nAPIZIN.IZIN_KODG16  \" RAPOR \" ,\nAPIZIN.IZIN_KODG17  \" ISTIRAHAT \" ,\nAPIZIN.IZIN_KODG18  \" IS KAZASI \" ,\nAPIZIN.IZIN_KODG19  \" UCRETSIZ \" ,\nAPIZIN.IZIN_KODG22  \" KACAK IZNI \" ,\nAPIZIN.IZIN_KOD20  \" YARIM UCRETLI \" ,\nAPIZIN.IZIN_KOD21  \" YARIM UCRETSIZ \" \n\n\n\n FROM AYL_PNT_GUN  APG\n LEFT JOIN PERSONEL PR ON (PR.SICIL_NO=APG.SICIL_NO)\n LEFT JOIN DEPARTMAN DP ON (DP.DEPART_KODU=PR.DEPARTMAN)\n LEFT JOIN BOLUM BM ON (BM.BOLUM_KODU=PR.BOLUM)\n LEFT JOIN GOREV GR ON (GR.GOREV_KODU=PR.GOREVI)\n LEFT JOIN UCRET UC ON (UC.UCRET_KODU=PR.UCRET_TURU)\n LEFT JOIN POSTA PS ON (PS.POSTA_KODU=PR.POSTA_KODU)\n LEFT JOIN AYL_PNT_IZIN APIZIN ON (APIZIN.SICIL_NO=PR.SICIL_NO)\n LEFT JOIN AYL_PNT_SAAT APS ON (APS.SICIL_NO=PR.SICIL_NO)\n WHERE (PR.KAYITTURU=1) AND (PR.SIRKET_KODU='01')");
                    jScrollPane2.setViewportView(jTextArea1);

                    PuantajPanel.add(jScrollPane2, java.awt.BorderLayout.PAGE_START);

                    jTabbedPane1.addTab("Aylık Puantaj Cetveli", PuantajPanel);

                    jPanel4.setLayout(new java.awt.BorderLayout());

                    jTextArea2.setColumns(15);
                    jTextArea2.setRows(8);
                    jTextArea2.setText("SELECT \nGP.TARIH, \nGP.SICIL_NO, \nGP.KART_NO, \nPR.ADI, \nPR.SOYADI, \nGP.GIRIS_TARIHI, \nGP.GIRIS_KAPI, \nGP.GIRIS_NEDEN, \nGP.CIKIS_TARIHI, \nGP.CIKIS_KAPI ,\nGP.SURE,\nGP.CIKIS_NEDEN, \nGP.POSTA_KODU, \nGP.VARDIYA_KODU,\nDP.DEPART_ADI, \nBM.BOLUM_ADI, \nGR.GOREV_ADI, \nUC.UCRET_ADI, \nPS.POSTA_ADI,\nGPS.NORM_CAL_S, \nGPS.FIILI_CAL_S, \nGPS.SSK_S, \nGPS.UCRETLI_S, \nGPS.CAL_S, \nGPS.CTS_CAL_S, \nGPS.HT_CAL_S, \nGPS.RT_CAL_S, \nGPS.DT_CAL_S, \nGPS.IZINSIZ_S, \nGPS.FM_1_S, \nGPS.FM_2_S, \nGPS.FM_3_S, \nGPS.FM_4_S, \nGPS.FM_5_S,\nGPS.AR_CAL_S, \nGPS.EKS_CAL_S, \nGPS.HT_HAK_E, \nGPS.GT_HAK_E, \nGPS.CT_HAK_E, \nGPS.VARD_ZAM1, \nGPS.VARD_ZAM2, \nGPS.VARD_ZAM3, \nGPS.UCR_IZIN_S, \nGPS.UCRSZ_IZIN_S, \nGPS.NORM_CAL_I_S,\nGPS.CALM_S, \nGPS.GECE_ZAM,\nGPG.NORM_CAL_G, \nGPG.FIILI_CAL_G, \nGPG.SSK_G, \nGPG.UCRETLI_G, \nGPG.CAL_G, \nGPG.CTS_CAL_G, \nGPG.HT_CAL_G, \nGPG.RT_CAL_G, \nGPG.DT_CAL_G, \nGPG.IZINSIZ_G, \nGPG.AR_CAL_G, GPG.GT_HAK_G, GPG.CT_HAK_G, GPG.HT_HAK_G,\nGPG.EKS_CAL_G, GPG.UCR_IZIN_G, GPG.UCRSZ_IZIN_G, GPG.NORM_CAL_I_G,GPG.CALM_G,IZ.IZIN_ADI,\nGPI.IZIN_KOD1, GPI.IZIN_KOD2, GPI.IZIN_KOD3, GPI.IZIN_KOD4, GPI.IZIN_KOD5, GPI.IZIN_KOD6, GPI.IZIN_KOD7, GPI.IZIN_KOD8, GPI.IZIN_KOD9, GPI.IZIN_KOD10, GPI.IZIN_KOD11, GPI.IZIN_KOD12, GPI.IZIN_KOD13,GPI.IZIN_KOD14, GPI.IZIN_KOD15,\nGPI.IZIN_KOD16, GPI.IZIN_KOD17, GPI.IZIN_KOD18, GPI.IZIN_KOD19, GPI.IZIN_KOD20, GPI.IZIN_KOD21, GPI.IZIN_KOD22, GPI.IZIN_KOD23, GPI.IZIN_KOD24, GPI.IZIN_KOD25, GPI.IZIN_KOD26, GPI.IZIN_KOD27, GPI.IZIN_KOD28,  GPI.IZIN_KOD29, GPI.IZIN_KOD30,\nGPI.IZIN_KODG1, GPI.IZIN_KODG2, GPI.IZIN_KODG3, GPI.IZIN_KODG4, GPI.IZIN_KODG5, GPI.IZIN_KODG6, GPI.IZIN_KODG7, GPI.IZIN_KODG8, GPI.IZIN_KODG9, GPI.IZIN_KODG10, GPI.IZIN_KODG11, GPI.IZIN_KODG12, GPI.IZIN_KODG13,GPI.IZIN_KODG14, GPI.IZIN_KODG15,\nGPI.IZIN_KODG16, GPI.IZIN_KODG17, GPI.IZIN_KODG18, GPI.IZIN_KODG19, GPI.IZIN_KODG20, GPI.IZIN_KODG21, GPI.IZIN_KODG22, GPI.IZIN_KODG23, GPI.IZIN_KODG24, GPI.IZIN_KODG25, GPI.IZIN_KODG26, GPI.IZIN_KODG27, GPI.IZIN_KODG28,  GPI.IZIN_KODG29, GPI.IZIN_KODG30\n\nfrom GUNLUK_PUANTAJ GP\nLEFT JOIN PERSONEL PR ON (PR.SICIL_NO=GP.SICIL_NO)\nLEFT JOIN DEPARTMAN DP ON (DP.DEPART_KODU=PR.DEPARTMAN)\nLEFT JOIN BOLUM BM ON (BM.BOLUM_KODU=PR.BOLUM)\nLEFT JOIN GOREV GR ON (GR.GOREV_KODU=PR.GOREVI)\nLEFT JOIN UCRET UC ON (UC.UCRET_KODU=PR.UCRET_TURU)\nLEFT JOIN POSTA PS ON (PS.POSTA_KODU=GP.POSTA_KODU)\nLEFT JOIN GNL_PNT_SAAT GPS ON (GPS.SICIL_NO=GP.SICIL_NO)and(GPS.TARIH=GP.TARIH)\nLEFT JOIN GNL_PNT_GUN GPG ON (GPG.SICIL_NO=GP.SICIL_NO)and(GPG.TARIH=GP.TARIH)\nLEFT JOIN GNL_PNT_IZIN GPI ON\n(GPI.SICIL_NO=GP.SICIL_NO)and(GPI.TARIH=GP.TARIH)\nLEFT JOIN IZIN IZ ON (IZ.IZIN_KODU=GPS.IZIN_KODU)\nWHERE (PR.KAYITTURU=1)AND(PR.SIRKET_KODU='01')\n\n");
                    jScrollPane3.setViewportView(jTextArea2);

                    jPanel4.add(jScrollPane3, java.awt.BorderLayout.PAGE_START);

                    jPanel5.setBackground(new java.awt.Color(51, 51, 51));
                    jPanel5.setLayout(new java.awt.BorderLayout());

                    jButton4.setText("Aktar");
                    jButton4.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton4ActionPerformed(evt);
                              }
                    });

                    jButton6.setText("Excell");
                    jButton6.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton6ActionPerformed(evt);
                              }
                    });

                    jLabel2.setText("SQL");

                    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                    jPanel6.setLayout(jPanel6Layout);
                    jPanel6Layout.setHorizontalGroup(
                              jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4)
                                        .addGap(2, 2, 2)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );
                    jPanel6Layout.setVerticalGroup(
                              jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jButton6)
                                                  .addComponent(jButton4)
                                                  .addComponent(jLabel2))
                                        .addGap(0, 0, 0))
                    );

                    jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_END);

                    jPanel7.setLayout(new java.awt.BorderLayout());

                    jToolBar3.setFloatable(false);
                    jToolBar3.setRollover(true);

                    jTextField2.setColumns(5);
                    jTextField2.setText("0");
                    jToolBar3.add(jTextField2);

                    jTextField4.setColumns(5);
                    jTextField4.setText("z");
                    jToolBar3.add(jTextField4);

                    jSpinner1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1448284379648L), null, null, java.util.Calendar.DAY_OF_MONTH));
                    jSpinner1.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner1, "dd.MM.yyyy"));
                    jToolBar3.add(jSpinner1);

                    jSpinner2.setModel(new javax.swing.SpinnerDateModel());
                    jSpinner2.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner2, "dd.MM.yyyy"));
                    jToolBar3.add(jSpinner2);

                    jComboBox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar3.add(jComboBox7);

                    jComboBox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar3.add(jComboBox8);

                    jComboBox9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seçiniz...." }));
                    jToolBar3.add(jComboBox9);

                    jButton7.setText("Listele");
                    jButton7.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton7ActionPerformed(evt);
                              }
                    });
                    jToolBar3.add(jButton7);

                    jButton8.setText("  V  ");
                    jButton8.setToolTipText("");
                    jButton8.addActionListener(new java.awt.event.ActionListener() {
                              public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        jButton8ActionPerformed(evt);
                              }
                    });
                    jToolBar3.add(jButton8);

                    jLabel3.setText("Text");
                    jToolBar3.add(jLabel3);

                    jPanel7.add(jToolBar3, java.awt.BorderLayout.CENTER);

                    jPanel5.add(jPanel7, java.awt.BorderLayout.PAGE_START);

                    jTable2.setAutoCreateRowSorter(true);
                    jTable2.setModel(new javax.swing.table.DefaultTableModel(
                              new Object [][] {

                              },
                              new String [] {

                              }
                    ));
                    jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                    jTable2.setComponentPopupMenu(jPopupMenu2);
                    jTable2.setRowHeight(24);
                    jTable2.setShowHorizontalLines(true);
                    jTable2.setShowVerticalLines(true);
                    jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                              public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        jTable2MouseClicked(evt);
                              }
                    });
                    jScrollPane4.setViewportView(jTable2);

                    jPanel5.add(jScrollPane4, java.awt.BorderLayout.CENTER);

                    jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

                    jTabbedPane1.addTab("Günlük Puantaj Cetveli", jPanel4);

                    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

                    pack();
          }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
              rapor();
              renderer.setColumnNumber(jTable1.getColumnCount() - 1);
              jTable1.setDefaultRenderer(Object.class, renderer);
              Enumeration<TableColumn> columns = jTable1.getColumnModel().getColumns();

              while (columns.hasMoreElements()) {
                        TableColumn c = columns.nextElement();
                        if (c.getModelIndex() > 5) {
                                  c.setWidth(36);
                                  c.setPreferredWidth(36);

                                  c.setHeaderRenderer(headerRenderer);
                        }
              }
              jTable1.repaint();
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
          JFileChooser chooser = new JFileChooser();
          String dizin = "";
          String programDizin;

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

          private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                    jScrollPane2.setVisible(!jScrollPane2.isVisible());
                    this.getContentPane().revalidate();
          }//GEN-LAST:event_jButton5ActionPerformed

          private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                    // TODO add your handling code here:
          }//GEN-LAST:event_jButton4ActionPerformed

          private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                    // TODO add your handling code here:
          }//GEN-LAST:event_jButton6ActionPerformed

          private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed


                    new Thread() {
                              @Override
                              public void run() {
//                                        jPanel5.remove(jScrollPane4);
//                                        jPanel5.add(loadingLabel, BorderLayout.CENTER);
//                                        jPanel5.revalidate();

                                        gunlukPuantajRaporu();
                                        renderer.setColumnNumber(jTable2.getColumnCount() - 1);
                                        jTable2.setDefaultRenderer(Object.class, renderer);
                                        Enumeration<TableColumn> columns = jTable2.getColumnModel().getColumns();

                                        while (columns.hasMoreElements()) {
                                                  TableColumn c = columns.nextElement();
                                                  if (c.getModelIndex() > 18) {

                                                            c.setWidth(42);
                                                            c.setPreferredWidth(42);
                                                            c.setHeaderRenderer(headerRenderer);
                                                  }
                                        }

//                                        jPanel5.remove(loadingLabel);
//                                        jPanel5.add(jScrollPane4, BorderLayout.CENTER);
//                                        jPanel5.revalidate();

                              }
                    }.start();

          }//GEN-LAST:event_jButton7ActionPerformed

          private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
                    jScrollPane3.setVisible(!jScrollPane3.isVisible());
                    this.getContentPane().revalidate();
          }//GEN-LAST:event_jButton8ActionPerformed

          private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
          }//GEN-LAST:event_jTable2MouseClicked

          private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                    if (evt.getClickCount() == 2) {
                              int index = jTable1.getSelectedRow();
                              String sicil = (String) jTable1.getValueAt(index, 0);
                              jTextField2.setText(sicil);
                              jTextField4.setText(sicil);
                              jTabbedPane1.setSelectedIndex(1);
                              jButton7ActionPerformed(null);
                    }
          }//GEN-LAST:event_jTable1MouseClicked

          private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
                    int k = jTable2.getSelectedColumn();
                    System.out.println("k:" + k);
                    if (k > -1) {
                              renderer.setColumnNumber(k);
                              jTable2.setDefaultRenderer(Object.class, renderer);
                              jTable2.repaint();
                    }
          }//GEN-LAST:event_jMenuItem2ActionPerformed

          public void notifyClose() {

                    setting.setBounds("firebird.manager.frame", this.getBounds());
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
                                        new CengizSoma().setup().setVisible(true);
                              }
                    });
          }
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JPanel PuantajPanel;
          private javax.swing.Box.Filler filler1;
          private javax.swing.JButton jButton1;
          private javax.swing.JButton jButton2;
          private javax.swing.JButton jButton3;
          private javax.swing.JButton jButton4;
          private javax.swing.JButton jButton5;
          private javax.swing.JButton jButton6;
          private javax.swing.JButton jButton7;
          private javax.swing.JButton jButton8;
          private javax.swing.JCheckBox jCheckBox1;
          private javax.swing.JComboBox jComboBox4;
          private javax.swing.JComboBox jComboBox5;
          private javax.swing.JComboBox jComboBox6;
          private javax.swing.JComboBox jComboBox7;
          private javax.swing.JComboBox jComboBox8;
          private javax.swing.JComboBox jComboBox9;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JLabel jLabel2;
          private javax.swing.JLabel jLabel3;
          private javax.swing.JMenuItem jMenuItem1;
          private javax.swing.JMenuItem jMenuItem2;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JPanel jPanel2;
          private javax.swing.JPanel jPanel3;
          private javax.swing.JPanel jPanel4;
          private javax.swing.JPanel jPanel5;
          private javax.swing.JPanel jPanel6;
          private javax.swing.JPanel jPanel7;
          private javax.swing.JPopupMenu jPopupMenu1;
          private javax.swing.JPopupMenu jPopupMenu2;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JScrollPane jScrollPane2;
          private javax.swing.JScrollPane jScrollPane3;
          private javax.swing.JScrollPane jScrollPane4;
          private javax.swing.JSpinner jSpinner1;
          private javax.swing.JSpinner jSpinner2;
          private javax.swing.JTabbedPane jTabbedPane1;
          private javax.swing.JTable jTable1;
          private javax.swing.JTable jTable2;
          private javax.swing.JTextArea jTextArea1;
          private javax.swing.JTextArea jTextArea2;
          private javax.swing.JTextField jTextField1;
          private javax.swing.JTextField jTextField2;
          private javax.swing.JTextField jTextField3;
          private javax.swing.JTextField jTextField4;
          private javax.swing.JToolBar jToolBar1;
          private javax.swing.JToolBar jToolBar2;
          private javax.swing.JToolBar jToolBar3;
          private javax.swing.JLabel loadingLabel;
          // End of variables declaration//GEN-END:variables

          private void rapor() throws HeadlessException {

                    int NCIzinBas = 11;
                    int NCIzinBit = 16; //ikisi de dahil


                    long bas = System.currentTimeMillis();
                    if (db != null) {
                              try {
                                        String sqlText = jTextArea1.getText();

                                        sqlText += "  AND ((PR.SICIL_NO>='" + jTextField1.getText() + "')  AND  (PR.SICIL_NO<='" + jTextField3.getText() + "')) \n";
                                        if (jComboBox4.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.departman='" + departmanKoduList.get(jComboBox4.getSelectedIndex()) + "')\n ";
                                        }
                                        if (jComboBox5.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.bolum='" + bolumKoduList.get(jComboBox5.getSelectedIndex()) + "')\n ";
                                        }
                                        if (jComboBox6.getSelectedIndex() > 0) {
                                                  sqlText += " and (pr.gorevi='" + gorevKoduList.get(jComboBox6.getSelectedIndex()) + "') \n";
                                        }

                                        sonuc = db.sql(sqlText);
                                        DefaultTableModel model = new DefaultTableModel() {
                                                  @Override
                                                  public boolean isCellEditable(int row, int column) {
                                                            return false;
                                                  }
                                        };
                                        int colCount = sonuc.getMetaData().getColumnCount();
                                        System.out.println("col count:" + colCount);
                                        String[] cols = new String[colCount + 1];
                                        //  List<Object[]> data=new ArrayList<Object[]>();
                                        for (int k = 0; k < colCount; k++) {
                                                  cols[k] = sonuc.getMetaData().getColumnName(k + 1);
                                        }
                                        cols[colCount] = "TOPLAM";
                                        //     data.add(cols);
                                        model.setColumnIdentifiers(cols);

                                        Object[] row = new Object[colCount + 1];

                                        while (sonuc.next()) {
                                                  double toplam = 0;
                                                  for (int k = 0; k < colCount; k++) {
                                                            row[k] = sonuc.getObject(k + 1);
                                                            String ob = row[k] + "";
                                                            if (k > 5) {
                                                                      if (!ob.isEmpty()) {
                                                                                if (jCheckBox1.isSelected()) {
                                                                                          if ((k > colCount - 3)) {
                                                                                                    float d = saatDenGune(ob);
                                                                                                    row[k] = d + "";
                                                                                                    toplam += d;
                                                                                          }
                                                                                          if (k == 6) {// normal çalışma saati güne çevir
                                                                                                    float d = Float.parseFloat(ob);
                                                                                                    float d2 = toFloat("07:30");
                                                                                                    row[k] = yuvarla(d / d2) + "";
                                                                                                    toplam += yuvarla(d / d2);
                                                                                          } else if ((k < NCIzinBas || k > NCIzinBit) && (k < colCount - 2)) { // normal çalışma olan izinler toplamda görünmesin
                                                                                                    toplam += Integer.parseInt(ob);
                                                                                          }
                                                                                }
                                                                      }
                                                            }
                                                  }
                                                  row[colCount] = toplam;
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

          public static float yuvarla(Float f) {
                    System.out.print("float:" + f + " ");
                    float tam = (float) Math.floor(f);
                    float fark = f - tam;
                    if (fark < 0.3f) {
                              f = tam;
                    } else if (fark <= 0.75f) {
                              f = tam + 0.5f;
                    } else {
                              f = tam + 1;
                    }
                    System.out.println(" yuvarla :" + (f));
                    return f;
          }

          public float toFloat(String saat) {
                    float value = 0.0f;
                    int tam = Integer.parseInt(saat.substring(0, 2));
                    int ondalik = Integer.parseInt(saat.substring(3, 5));
                    ondalik = (ondalik * 100) / 60;
                    value = Float.parseFloat(tam + "." + ondalik);
                    //System.out.println(saat + ": " + value);
                    return value;
          }

          public float saatDenGune(String saat) {
                    float value = 0.0f;
                    float d1, d2;
                    d1 = toFloat(saat);
                    d2 = toFloat("03:30");

                    value = (int) (d1 / d2);
                    System.out.println(d1 + "  -  " + d2);
                    System.out.println("(" + saat + "/03:30)" + "=" + value + "                    /2  :" + (int) (value / 2));
                    value = value / 2;

                    System.out.println("----------------------------------------------------------------------------------------");
                    return value;
          }

          private void execute() throws HeadlessException {
          }

          private void export() {
                    try {
                              File f = new File("rapor-" + new SimpleDateFormat("dd-MM-yyyy_HH_mm").format(new Date()) + ".csv");
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
                              Process p = runtime.exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + f.getAbsolutePath());
                    } catch (Exception ex) {
                              ExceptionHandler.onException("Dışa Aktarmada Hata Oluştu", ex);
                    }
          }
}

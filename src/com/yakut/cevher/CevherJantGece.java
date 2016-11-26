package com.yakut.cevher;
 
import com.yakut.util.Database;
import com.yakut.util.DateUtil;
import com.yakut.util.Excell;
import com.yakut.util.Setting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

public class CevherJantGece extends javax.swing.JFrame {

            Logger logger = Logger.getRootLogger();
            List<Hareket> hareketList, islemList;

            public CevherJantGece() {
                        initComponents();
                        init();
            }
            Database db;

            public void init() {
                        String path = Setting.getSettings().getProperty("database.path", "c:\\Uzmanpdks\\data\\cevher2\\data.fdb");
                        String ip = Setting.getSettings().getProperty("database.server.ip", "127.0.0.1");
                        db = new Database(path, ip);
                        if (db.connect()) {
                                    jLabel2.setText("Veritabanına bağlantı sağlandı");
                        } else {
                                    jLabel2.setText("Veritabanına bağlantı sağlanamadı  " + db.getException().getMessage());
                        }
                        //       jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(5));
            }

            public void find() {
                        count = 0;
                        String tarih = DateUtil.formatDate((Date) jSpinner2.getValue());
                        String tarih2 = DateUtil.formatDate((Date) jSpinner5.getValue());
                        String sure = jTextField1.getText();
                        String sql = "select * from gunluk_hareket g"
                                + "  left join personel p on p.sicil_no=g.sicil_no "
                                + " where (g.tarih between '" + tarih + " 00:00:01' and '" + tarih2 + " 23:59:59') "
                                + " and (cast(g.tarih as time) between '07:00:00' and '09:00:00' )"
                                + " and g.sure>'" + sure + ":00' and g.hareket='Ç'"
                                + " order by g.sicil_no,g.tarih";
                        ResultSet rs = db.sql(sql);
                        DefaultTableModel model = new DefaultTableModel() {
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                                return false;
                                    }
                        };
                        model.setColumnIdentifiers(new String[]{"Sicil", "Ad Soyad", "Giriş Tarihi", "Çıkış Tarih", "Sure", "Seç"});
                        try {
                                    hareketList = new ArrayList<>();
                                    while (rs.next()) {
                                                String sicil = rs.getString("SICIL_NO");
                                                String kart = rs.getString("KART_NO");
                                                String posta = rs.getString("POSTA_KODU");
                                                String isim = rs.getString("ADI") + " " + rs.getString("SOYADI");
                                                Date tar = rs.getDate("TARIH");
                                                String su = rs.getString("SURE");
                                                String hareket = "";// rs.getString("HAREKET");
                                                Hareket h = new Hareket(sicil, kart, tar, posta);
                                                h.setAd(isim);
                                                h.setSure(su);
                                                hareketList.add(h);
                                                System.out.println(sicil + "," + DateUtil.fullFormatDate(tar) + "," + su + "," + hareket);
                                    }

                                    jProgressBar1.setIndeterminate(false);
                                    jProgressBar1.setMaximum(hareketList.size() - 1);
                                    islemList = new ArrayList<>();
                                    for (int k = 0; k < hareketList.size(); k++) {
                                                Hareket h = hareketList.get(k);
                                                jProgressBar1.setValue(k);
                                                Date girisTarihi = getOncekiTarih(h);
                                                String girisGun = DateUtil.fullFormatDate(girisTarihi).substring(0, 2);
                                                String cikisGun = DateUtil.fullFormatDate(h.tarih).substring(0, 2);

                                                long g = girisTarihi.getTime();
                                                long c = h.tarih.getTime();

                                                System.out.println("fark:" + (Math.abs((c) / (1000 * 60 * 60 * 24))));
                                                System.out.println("fark:" + (Math.abs((g) / (1000 * 60 * 60 * 24))));
                                                System.out.println("fark:" + (Math.abs((c - g))));

                                                g = ((Math.abs((g) / (1000 * 60 * 60 * 24))));
                                                c = ((Math.abs((c) / (1000 * 60 * 60 * 24))));

                                                if (Math.abs((g - c)) == 1) {
                                                            islemList.add(h);
                                                            model.addRow(new String[]{h.sicil, h.ad, DateUtil.fullFormatDate(girisTarihi), DateUtil.fullFormatDate(h.tarih), h.sure, ""});
                                                }
                                    }

                                    jTable1.setModel(model);
                        } catch (SQLException ex) {
                                    logger.error(ex);
                        }
            }

            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jTabbedPane1 = new javax.swing.JTabbedPane();
                        jPanel4 = new javax.swing.JPanel();
                        jPanel2 = new javax.swing.JPanel();
                        jToolBar1 = new javax.swing.JToolBar();
                        jSpinner2 = new javax.swing.JSpinner();
                        jSpinner5 = new javax.swing.JSpinner();
                        jLabel1 = new javax.swing.JLabel();
                        jTextField1 = new javax.swing.JTextField();
                        jButton1 = new javax.swing.JButton();
                        jCheckBox1 = new javax.swing.JCheckBox();
                        jSeparator1 = new javax.swing.JToolBar.Separator();
                        jButton6 = new javax.swing.JButton();
                        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
                        jButton2 = new javax.swing.JButton();
                        jPanel1 = new javax.swing.JPanel();
                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTable1 = new javax.swing.JTable();
                        jPanel5 = new javax.swing.JPanel();
                        jPanel6 = new javax.swing.JPanel();
                        jButton3 = new javax.swing.JButton();
                        jSpinner3 = new javax.swing.JSpinner();
                        jCheckBox2 = new javax.swing.JCheckBox();
                        jButton4 = new javax.swing.JButton();
                        jSpinner4 = new javax.swing.JSpinner();
                        jButton5 = new javax.swing.JButton();
                        jScrollPane2 = new javax.swing.JScrollPane();
                        jTable2 = new javax.swing.JTable();
                        jPanel3 = new javax.swing.JPanel();
                        jLabel2 = new javax.swing.JLabel();
                        jProgressBar1 = new javax.swing.JProgressBar();

                        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                        setTitle("Cevher Jant - Kontrolsis Elektronik");

                        jPanel4.setLayout(new java.awt.BorderLayout());

                        jPanel2.setLayout(new java.awt.BorderLayout());

                        jToolBar1.setFloatable(false);
                        jToolBar1.setRollover(true);

                        jSpinner2.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1409498400000L), null, null, java.util.Calendar.DAY_OF_MONTH));
                        jSpinner2.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner2, "dd.MM.yyyy"));
                        jToolBar1.add(jSpinner2);

                        jSpinner5.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1409757600000L), null, null, java.util.Calendar.DAY_OF_MONTH));
                        jSpinner5.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner5, "dd.MM.yyyy"));
                        jToolBar1.add(jSpinner5);

                        jLabel1.setText("Süre");
                        jToolBar1.add(jLabel1);

                        jTextField1.setText("10:00");
                        jToolBar1.add(jTextField1);

                        jButton1.setText("  Listele  ");
                        jButton1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton1ActionPerformed(evt);
                                    }
                        });
                        jToolBar1.add(jButton1);

                        jCheckBox1.setText("Seç");
                        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jCheckBox1ActionPerformed(evt);
                                    }
                        });
                        jToolBar1.add(jCheckBox1);

                        jSeparator1.setSeparatorSize(new java.awt.Dimension(50, 10));
                        jToolBar1.add(jSeparator1);

                        jButton6.setText("Rapor");
                        jButton6.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton6ActionPerformed(evt);
                                    }
                        });
                        jToolBar1.add(jButton6);
                        jToolBar1.add(filler1);

                        jButton2.setText("Düzelt                      ");
                        jButton2.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton2ActionPerformed(evt);
                                    }
                        });
                        jToolBar1.add(jButton2);

                        jPanel2.add(jToolBar1, java.awt.BorderLayout.CENTER);

                        jPanel4.add(jPanel2, java.awt.BorderLayout.PAGE_START);

                        jPanel1.setLayout(new java.awt.BorderLayout());

                        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                    new Object [][] {

                                    },
                                    new String [] {

                                    }
                        ));
                        jTable1.setRowHeight(30);
                        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                jTable1MouseClicked(evt);
                                    }
                        });
                        jScrollPane1.setViewportView(jTable1);

                        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

                        jPanel4.add(jPanel1, java.awt.BorderLayout.CENTER);

                        jTabbedPane1.addTab("Giriş Çıkış Ekle", jPanel4);

                        jPanel5.setLayout(new java.awt.BorderLayout());

                        jButton3.setText("Listele");
                        jButton3.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton3ActionPerformed(evt);
                                    }
                        });

                        jSpinner3.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1391444400000L), null, null, java.util.Calendar.DAY_OF_MONTH));
                        jSpinner3.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner3, "dd.MM.yyyy"));

                        jCheckBox2.setText("Seç");
                        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jCheckBox2ActionPerformed(evt);
                                    }
                        });

                        jButton4.setText("Sil");
                        jButton4.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton4ActionPerformed(evt);
                                    }
                        });

                        jSpinner4.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1391444400000L), null, null, java.util.Calendar.DAY_OF_MONTH));
                        jSpinner4.setEditor(new javax.swing.JSpinner.DateEditor(jSpinner4, "dd.MM.yyyy"));

                        jButton5.setText("Rapor");
                        jButton5.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton5ActionPerformed(evt);
                                    }
                        });

                        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                        jPanel6.setLayout(jPanel6Layout);
                        jPanel6Layout.setHorizontalGroup(
                                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jCheckBox2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        );
                        jPanel6Layout.setVerticalGroup(
                                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton3)
                                                .addComponent(jCheckBox2)
                                                .addComponent(jButton4)
                                                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton5))
                        );

                        jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

                        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                                    new Object [][] {

                                    },
                                    new String [] {

                                    }
                        ));
                        jTable2.setRowHeight(30);
                        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                jTable2MouseClicked(evt);
                                    }
                        });
                        jScrollPane2.setViewportView(jTable2);

                        jPanel5.add(jScrollPane2, java.awt.BorderLayout.CENTER);

                        jTabbedPane1.addTab("İşlemi Geri Al", jPanel5);

                        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

                        jPanel3.setLayout(new java.awt.BorderLayout());

                        jLabel2.setText(">");
                        jPanel3.add(jLabel2, java.awt.BorderLayout.CENTER);

                        jProgressBar1.setStringPainted(true);
                        jPanel3.add(jProgressBar1, java.awt.BorderLayout.LINE_END);

                        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

                        pack();
            }// </editor-fold>//GEN-END:initComponents

            private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                        Setting.getSettings().setProperty("gece.baslangic.tarihi", DateUtil.formatDate((Date)jSpinner2.getValue()));
                        Setting.getSettings().setProperty("gece.bitis.tarihi",  DateUtil.formatDate((Date)jSpinner3.getValue()));



                        new Thread() {
                                    @Override
                                    public void run() {
                                                jProgressBar1.setIndeterminate(true);
                                                jLabel2.setText("Kayıtlar alınıyor...");
                                                find();
                                                jLabel2.setText(islemList.size() + " Kayıt listelendi.");
                                                jProgressBar1.setIndeterminate(false);
                                    }
                        }.start();

            }//GEN-LAST:event_jButton1ActionPerformed
            int count = 0;
            private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
                        if (evt.getClickCount() == 2) {
                                    int k = jTable1.getSelectedRow();
                                    if (k > -1) {
                                                String value = jTable1.getValueAt(k, 5).toString();
                                                if (value.equals("EVET")) {
                                                            value = "";
                                                            islemList.get(k).setIslem(false);
                                                            count--;
                                                } else {
                                                            value = "EVET";
                                                            islemList.get(k).setIslem(true);
                                                            count++;
                                                }
                                                jTable1.setValueAt(value, k, 5);
                                                jLabel2.setText(count + " adet personel işlem görecek");

                                    }
                        }
            }//GEN-LAST:event_jTable1MouseClicked
            public String q(String text) {
                        return "'" + text + "'";
            }

            public Date getOncekiTarih(Hareket h) {
                        Date value = null;
                        try {
                                    String sql = "select first 1 tarih from gunluk_hareket"
                                            + " where(tarih < " + q(DateUtil.fullFormatDate(h.getTarih())) + ")"
                                            + " and sicil_no=" + q(h.getSicil()) + " "
                                            + "order by tarih desc";
                                    ResultSet r = db.sql(sql);
                                    if (r.next()) {
                                                value = r.getDate("tarih");
                                                logger.info("önceki hareket tarihi:" + DateUtil.fullFormatDate(value));
                                    } else {
                                                logger.warn(h.getSicil() + " sicilli kişinin " + DateUtil.fullFormatDate(h.getTarih()) + " tarihinden önce hareketi yok.");
                                    }
                        } catch (SQLException ex) {
                                    logger.error(ex);
                        }
                        return value;
            }

            public Date getSonrakiTarih(Hareket h) {
                        Date value = null;
                        try {
                                    String sql = "select first 1 tarih from gunluk_hareket"
                                            + " where(tarih > " + q(DateUtil.fullFormatDate(h.getTarih())) + ")"
                                            + " and sicil_no=" + q(h.getSicil()) + " "
                                            + "order by tarih asc";
                                    ResultSet r = db.sql(sql);
                                    if (r.next()) {
                                                value = r.getDate("tarih");
                                                logger.info("sonraki hareket tarihi:" + DateUtil.fullFormatDate(value));
                                    } else {
                                                logger.warn(h.getSicil() + " sicilli kişinin " + DateUtil.fullFormatDate(h.getTarih()) + " tarihinden sonra hareketi yok.");
                                    }
                        } catch (SQLException ex) {
                                    logger.error(ex);
                        }
                        return value;
            }

            public static String saatFark(Date giris, Date cikis) {
                        String dd;
                        Date d = new Date(cikis.getTime() - giris.getTime() - 2 * 60 * 60 * 1000);
                        dd = DateUtil.formatTime(d) + ":00";
                        return dd;
            }

            public void hareketEkle(Hareket h) {
                        Date d = new Date(h.getTarih().getTime() - 1000 * 60 * 60 * 24);
                        System.out.println(DateUtil.formatDate(h.getTarih()));
                        System.out.println(DateUtil.formatDate(d));
                        Date girisTarihi = getOncekiTarih(h);
                        Date cikisx = DateUtil.fullParseDate(DateUtil.formatDate(d) + " 23:58:00");
                        Date girisx = DateUtil.fullParseDate(DateUtil.formatDate(d) + " 23:59:00");
                        String sure = (saatFark(girisTarihi, cikisx));
                        db.execute("insert into gunluk_hareket (sicil_no,kart_no,tarih,posta_kodu,kapi_no,SIRKET_KODU,neden_kodu,HAREKET,SURE) "
                                + "values(" + q(h.sicil) + "," + q(h.kart) + "," + q(DateUtil.formatDate(d) + " 23:58:00") + "," + q(h.posta) + ",'x','01','000','Ç'," + q(sure) + ")");
                        db.execute("insert into gunluk_hareket (sicil_no,kart_no,tarih,posta_kodu,kapi_no,SIRKET_KODU,neden_kodu,HAREKET) "
                                + "values(" + q(h.sicil) + "," + q(h.kart) + "," + q(DateUtil.formatDate(d) + " 23:59:00") + "," + q(h.posta) + ",'x','01','000','G')");

                        sure = (saatFark(girisx, h.getTarih()));
                        db.execute("update   gunluk_hareket set sure=" + q(sure) + " where sicil_no=" + q(h.getSicil()) + " and tarih=" + q(DateUtil.fullFormatDate(h.getTarih())) + "");

            }
            private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                        int m = JOptionPane.showConfirmDialog(null, "Bu işlemi Yapmak istediğinize emin misiniz?", "Dikkat", JOptionPane.YES_NO_OPTION);
                        if (m == JOptionPane.YES_OPTION) {

                                    new Thread() {
                                                @Override
                                                public void run() {
                                                            jProgressBar1.setMinimum(0);
                                                            jProgressBar1.setMaximum(count);
                                                            for (int k = 0; k < islemList.size(); k++) {
                                                                        Hareket h = islemList.get(k);
                                                                        jProgressBar1.setValue(k);
                                                                        if (h.isIslem()) {
                                                                                    hareketEkle(h);
                                                                        }
                                                            }
                                                }
                                    }.start();

                        }
            }//GEN-LAST:event_jButton2ActionPerformed

            private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
                        for (int k = 0; k < islemList.size(); k++) {
                                    Hareket h = islemList.get(k);
                                    h.setIslem(jCheckBox1.isSelected());
                                    String value = jTable1.getValueAt(k, 5).toString();
                                    if (value.equals("EVET")) {
                                                value = "";
                                                islemList.get(k).setIslem(false);
                                                count--;
                                    } else {
                                                value = "EVET";
                                                islemList.get(k).setIslem(true);
                                                count++;
                                    }
                                    jTable1.setValueAt(value, k, 5);
                        }

                        jLabel2.setText(count + " adet personel işlem görecek");
            }//GEN-LAST:event_jCheckBox1ActionPerformed
            List<Hareket> eklenmisKayitlar;

            public void islemYapilanlariBul() {
                        count2 = 0;
                        String tarih1 = DateUtil.formatDate((Date) jSpinner4.getValue());
                        String tarih2 = DateUtil.formatDate((Date) jSpinner3.getValue());
                        String sql = "select * from gunluk_hareket g"
                                + "  left join personel p on p.sicil_no=g.sicil_no "
                                + " where (g.tarih between '" + tarih1 + " 00:00:01' and '" + tarih2 + " 23:59:00') "
                                + " and g.kapi_no='x'"
                                + " order by g.sicil_no,g.tarih";
                        ResultSet rs = db.sql(sql);
                        DefaultTableModel model = new DefaultTableModel() {
                                    @Override
                                    public boolean isCellEditable(int row, int column) {
                                                return false;
                                    }
                        };
                        model.setColumnIdentifiers(new String[]{"Sicil", "Ad Soyad", "Kapı/Yon", "Tarih", "Sure", "Seç", "Durum"});
                        try {
                                    eklenmisKayitlar = new ArrayList<>();
                                    while (rs.next()) {
                                                String sicil = rs.getString("SICIL_NO");
                                                String kart = rs.getString("KART_NO");
                                                String posta = rs.getString("POSTA_KODU");
                                                String isim = rs.getString("ADI") + " " + rs.getString("SOYADI");
                                                Date tar = rs.getDate("TARIH");
                                                String su = rs.getString("SURE");
                                                String kapi = rs.getString("KAPI_NO") + "/" + rs.getString("HAREKET");
                                                String hareket = "";// rs.getString("HAREKET");
                                                String yon = rs.getString("HAREKET");

                                                Hareket h = new Hareket(sicil, kart, tar, posta);
                                                h.setAd(isim);
                                                h.setSure(su);
                                                h.setKapi(kapi);
                                                h.setYon(yon);
                                                eklenmisKayitlar.add(h);
                                                System.out.println(sicil + "," + DateUtil.fullFormatDate(tar) + "," + su + "," + hareket);
                                    }

                                    jProgressBar1.setIndeterminate(false);
                                    jProgressBar1.setMaximum(eklenmisKayitlar.size() - 1);

                                    for (int k = 0; k < eklenmisKayitlar.size(); k++) {
                                                Hareket h = eklenmisKayitlar.get(k);
                                                jProgressBar1.setValue(k);
                                                model.addRow(new String[]{h.sicil, h.ad, h.getKapi(), DateUtil.fullFormatDate(h.tarih), h.sure, "", ""});
                                    }

                                    jTable2.setModel(model);
                        } catch (SQLException ex) {
                                    ex.printStackTrace();
                        }
            }
            private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

                        new Thread() {
                                    @Override
                                    public void run() {
                                                jProgressBar1.setIndeterminate(true);
                                                jLabel2.setText("Kayıtlar alınıyor...");
                                                islemYapilanlariBul();
                                                jLabel2.setText(eklenmisKayitlar.size() + " Kayıt listelendi.");
                                                jProgressBar1.setIndeterminate(false);
                                    }
                        }.start();
            }//GEN-LAST:event_jButton3ActionPerformed
            int count2 = 0;
            private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
                        for (int k = 0; k < eklenmisKayitlar.size(); k++) {
                                    Hareket h = eklenmisKayitlar.get(k);
                                    h.setIslem(jCheckBox2.isSelected());
                                    String value = jTable2.getValueAt(k, 5).toString();
                                    if (value.equals("EVET")) {
                                                value = "";
                                                eklenmisKayitlar.get(k).setIslem(false);
                                                count2--;
                                    } else {
                                                value = "EVET";
                                                eklenmisKayitlar.get(k).setIslem(true);
                                                count2++;
                                    }
                                    jTable2.setValueAt(value, k, 5);
                        }

                        jLabel2.setText(count2 + " adet personel işlem görecek");
            }//GEN-LAST:event_jCheckBox2ActionPerformed

            public boolean hareketiSil(Hareket h) {
                        boolean value;
                        value = db.execute("delete from gunluk_hareket where sicil_no=" + q(h.getSicil()) + "  and tarih=" + q(DateUtil.fullFormatDate(h.getTarih())));
                        if (h.getYon().equals("G")) {
                                    Date girisTar = getOncekiTarih(h);
                                    Date cikisTarihi = getSonrakiTarih(h);
                                    String sure = saatFark(girisTar, cikisTarihi);
                                    db.execute("delete from gunluk_hareket where sicil_no=" + q(h.getSicil()) + "  and tarih=" + q(DateUtil.fullFormatDate(h.getTarih())));
                                    value = db.execute("update   gunluk_hareket set sure=" + q(sure) + " where sicil_no=" + q(h.getSicil()) + " and tarih=" + q(DateUtil.fullFormatDate(cikisTarihi)) + "");
                        }
                        return value;
            }
            private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                        for (int k = 0; k < eklenmisKayitlar.size(); k++) {
                                    Hareket h = eklenmisKayitlar.get(k);
                                    if (h.isIslem()) {
                                                if (hareketiSil(h)) {
                                                            jTable2.setValueAt("OK", k, 6);
                                                } else {
                                                            jTable2.setValueAt("HATA", k, 6);
                                                }
                                    }
                        }

            }//GEN-LAST:event_jButton4ActionPerformed

            private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
                        if (evt.getClickCount() == 2) {
                                    int k = jTable2.getSelectedRow();
                                    if (k > -1) {
                                                String value = jTable2.getValueAt(k, 5).toString();
                                                if (value.equals("EVET")) {
                                                            value = "";
                                                            eklenmisKayitlar.get(k).setIslem(false);
                                                            count2--;
                                                } else {
                                                            value = "EVET";
                                                            eklenmisKayitlar.get(k).setIslem(true);
                                                            count2++;
                                                }
                                                jTable2.setValueAt(value, k, 5);
                                                jLabel2.setText(count2 + " adet personel işlem görecek");
                                    }
                        }
            }//GEN-LAST:event_jTable2MouseClicked

            private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                        new Excell().exceleAktar(jTable1);
            }//GEN-LAST:event_jButton6ActionPerformed

            private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                        new Excell().exceleAktar(jTable2);
            }//GEN-LAST:event_jButton5ActionPerformed

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
                                    java.util.logging.Logger.getLogger(CevherJantGece.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                                    java.util.logging.Logger.getLogger(CevherJantGece.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                                    java.util.logging.Logger.getLogger(CevherJantGece.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                                    java.util.logging.Logger.getLogger(CevherJantGece.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        //</editor-fold>
                        final CevherJantGece gece = new CevherJantGece();
                        boolean test = false;
                        if (test) {
                        } else {
                                    java.awt.EventQueue.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                            gece.setVisible(true);
                                                }
                                    });
                        }
            }
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.Box.Filler filler1;
            private javax.swing.JButton jButton1;
            private javax.swing.JButton jButton2;
            private javax.swing.JButton jButton3;
            private javax.swing.JButton jButton4;
            private javax.swing.JButton jButton5;
            private javax.swing.JButton jButton6;
            private javax.swing.JCheckBox jCheckBox1;
            private javax.swing.JCheckBox jCheckBox2;
            private javax.swing.JLabel jLabel1;
            private javax.swing.JLabel jLabel2;
            private javax.swing.JPanel jPanel1;
            private javax.swing.JPanel jPanel2;
            private javax.swing.JPanel jPanel3;
            private javax.swing.JPanel jPanel4;
            private javax.swing.JPanel jPanel5;
            private javax.swing.JPanel jPanel6;
            private javax.swing.JProgressBar jProgressBar1;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JScrollPane jScrollPane2;
            private javax.swing.JToolBar.Separator jSeparator1;
            private javax.swing.JSpinner jSpinner2;
            private javax.swing.JSpinner jSpinner3;
            private javax.swing.JSpinner jSpinner4;
            private javax.swing.JSpinner jSpinner5;
            private javax.swing.JTabbedPane jTabbedPane1;
            private javax.swing.JTable jTable1;
            private javax.swing.JTable jTable2;
            private javax.swing.JTextField jTextField1;
            private javax.swing.JToolBar jToolBar1;
            // End of variables declaration//GEN-END:variables
}

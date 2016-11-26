package com.yakut.plugin;

import com.yakut.util.Database;
import com.yakut.util.Setting;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class TelafiCalismasi extends javax.swing.JFrame {

            Database db;
            DefaultTableModel model;
            Logger logger = Logger.getLogger(Telafi.class);

            public TelafiCalismasi() {
                        initComponents();
                        model = (DefaultTableModel) jTable1.getModel();
                        setBounds(Setting.getSettings().getBounds("telafi.frame.bounds"));
                        db = new Database();
                        try {
                                    db.begin();
                        } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, "Veri tabanına bağlanılamadı/r/n" + ex);
                        }
            }

            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jButton1 = new javax.swing.JButton();
                        jLabel1 = new javax.swing.JLabel();
                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTable1 = new javax.swing.JTable();

                        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                        addWindowListener(new java.awt.event.WindowAdapter() {
                                    public void windowClosing(java.awt.event.WindowEvent evt) {
                                                formWindowClosing(evt);
                                    }
                        });

                        jButton1.setText("Telafi İşle");
                        jButton1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton1ActionPerformed(evt);
                                    }
                        });

                        jLabel1.setText("İşlem:");

                        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                    new Object [][] {

                                    },
                                    new String [] {
                                                "Sicil", "FM 1", "Telafi", "Açıklama"
                                    }
                        ) {
                                    boolean[] canEdit = new boolean [] {
                                                false, false, false, false
                                    };

                                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                                                return canEdit [columnIndex];
                                    }
                        });
                        jScrollPane1.setViewportView(jTable1);

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1)
                                                .addContainerGap())
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                        );
                        layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jButton1)
                                                            .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        );

                        pack();
            }// </editor-fold>//GEN-END:initComponents

            class Telafi {

                        String sicil;
                        float saat = 0.0f;

                        public Telafi(String sicil, float saat) {
                                    this.sicil = sicil;
                                    this.saat = saat;
                        }

                        @Override
                        public String toString() {
                                    return "Telafi{" + "sicil=" + sicil + ", telafi-saat=" + saat + '}';
                        }
            }

            public List<Telafi> getTelafiList() {
                        List<Telafi> list = new ArrayList<>();
                        try {
                                    Scanner scan = new Scanner(new File(Setting.getSettings().getProperty("telafi.file.path", "c:\\UzmanPdks\\telafi.txt")));
                                    while (scan.hasNextLine()) {
                                                String line = scan.nextLine();
                                                String row[] = line.split(",");
                                                String sicil = row[0];
                                                float telafi = Float.parseFloat(row[1].replace(',', '.'));
                                                Telafi t = new Telafi(sicil, telafi);
                                                list.add(t);

                                    }
                        } catch (FileNotFoundException ex) {
                                    JOptionPane.showMessageDialog(null, "Telafi Dosyası Bulunamadı.\r\n" + ex.getMessage());
                                    logger.error(ex);
                        }
                        return list;


            }

            public String q(String s) {
                        return "'" + s + "'";
            }

            public Telafi getFM(String sicil) {
                        Telafi t = null;
                        ResultSet r = db.sql("select SICIL_NO,FM1_TOP_S  from ayl_pnt_saat where SICIL_NO=" + q(sicil));
                        try {
                                    while (r.next()) {
                                                float f;
                                                try {
                                                            f = Float.parseFloat(r.getString("FM1_TOP_S"));
                                                } catch (Exception ex) {
                                                            f = 0.0f;
                                                            logger.error(ex);
                                                }
                                                t = new Telafi(sicil, f);
                                    }
                        } catch (Exception ex) {
                                    logger.error(ex);
                        }
                        return t;
            }

            public boolean setFM(String sicil, float fm) {
                        String fm1 = "";
                        if (fm > 0) {// sıfırsa hiç bişey yazma
                                    fm1 = fm + "";
                        }
                        return db.execute("UPDATE AYL_PNT_SAAT SET FM1_TOP_S=" + q(fm1) + " where SICIL_NO=" + q(sicil));
            }
            List<Telafi> kalanTelafi = new ArrayList<>();
            List<Telafi> aktarilanTelafi = new ArrayList<>();

            public void telafiIsle() {
                        List<Telafi> telafiList = getTelafiList();
                        kalanTelafi.clear();
                        aktarilanTelafi.clear();
                        String row[] = new String[]{"", "", "", ""};
                        float yeniFM = 0.0f;
                        for (Telafi t : telafiList) {
                                    jLabel1.setText(t.toString());
                                    row[0] = t.sicil;
                                    row[1] = "";
                                    row[2] = t.saat + "";
                                    Telafi fm = getFM(t.sicil);
                                    if (fm != null) {
                                                row[1] = fm.saat + "";
                                                if (fm.saat > 0) {
                                                            if (t.saat <= fm.saat) {
                                                                        yeniFM = fm.saat - t.saat;
                                                                        aktarilanTelafi.add(new Telafi(t.sicil, t.saat));
                                                                        setFM(t.sicil, yeniFM);
                                                                        row[3] = "Eşlendi";
                                                            } else {
                                                                        yeniFM = t.saat - fm.saat;
                                                                        setFM(t.sicil, 0);
                                                                        aktarilanTelafi.add(new Telafi(t.sicil, fm.saat));
                                                                        kalanTelafi.add(new Telafi(t.sicil, yeniFM));
                                                                        row[3] = "Eşlendi. " + yeniFM + " kaldı.";
                                                            }
                                                } else {
                                                            row[3] = "FM yok";
                                                            kalanTelafi.add(new Telafi(t.sicil, t.saat));
                                                }
                                    } else {
                                                row[3] = "Bu sicile ait aylık puantaj kaydı yok";
                                                kalanTelafi.add(new Telafi(t.sicil, t.saat));
                                    }
                                    model.addRow(row);
                        }

                        save(aktarilanTelafi, "aktarilan-telafi.txt");
                        save(kalanTelafi, "kalan-telafi.txt");
            }

            public void save(List<Telafi> list, String file) {
                        try {
                                    File f = new File(file);
                                    if (!f.exists()) {
                                                f.createNewFile();
                                    }
                                    FileWriter fr = new FileWriter(f, true);
                                    for (Telafi t : list) {
                                                fr.write(t.sicil + "," + t.saat + "\r\n");
                                    }
                                    fr.flush();
                                    fr.close();
                        } catch (IOException ex) {
                                    logger.error(ex);
                        }
            }
            private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                        new Thread() {
                                    @Override
                                    public void run() {
                                                jButton1.setText("İşleniyor...");
                                                jButton1.setEnabled(false);
                                                telafiIsle();
                                                jButton1.setText("Telafi işle");
                                                jButton1.setEnabled(true);
                                    }
                        }.start();
            }//GEN-LAST:event_jButton1ActionPerformed

            private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

                        Setting.getSettings().setBounds("telafi.frame.bounds", this.getBounds());
                        Setting.getSettings().saveProperties();
                        db.end();
            }//GEN-LAST:event_formWindowClosing

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
                                    java.util.logging.Logger.getLogger(TelafiCalismasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                                    java.util.logging.Logger.getLogger(TelafiCalismasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                                    java.util.logging.Logger.getLogger(TelafiCalismasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                                    java.util.logging.Logger.getLogger(TelafiCalismasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        //</editor-fold>

                        /* Create and display the form */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                                    public void run() {
                                                new TelafiCalismasi().setVisible(true);
                                    }
                        });
            }
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JLabel jLabel1;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JTable jTable1;
            // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.plugin;

import com.yakut.util.Database;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yakut
 */
public class PdksHareket extends javax.swing.JFrame {

            /**
             * Creates new form PdksHareket
             */
            public PdksHareket() {
                        initComponents();
                        Database db=new Database("c:\\UzmanPdks\\data\\imbat\\data.fdb");
                        try {
                                    if(db.begin()){
                                                db.sql("select * from personel");
                                    }
                                    
                        } catch (Exception ex) {
                                    Logger.getLogger(PdksHareket.class.getName()).log(Level.SEVERE, null, ex);
                        }
            }


            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        jButton1 = new javax.swing.JButton();
                        jScrollPane1 = new javax.swing.JScrollPane();
                        jTable1 = new javax.swing.JTable();

                        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                        jButton1.setText("jButton1");
                        jButton1.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                jButton1ActionPerformed(evt);
                                    }
                        });

                        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                    new Object [][] {
                                                {null, null, null, null},
                                                {null, null, null, null},
                                                {null, null, null, null},
                                                {null, null, null, null}
                                    },
                                    new String [] {
                                                "Title 1", "Title 2", "Title 3", "Title 4"
                                    }
                        ));
                        jScrollPane1.setViewportView(jTable1);

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addGap(0, 0, Short.MAX_VALUE)
                                                                        .addComponent(jButton1))
                                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE))
                                                .addContainerGap())
                        );
                        layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                                .addContainerGap())
                        );

                        pack();
            }// </editor-fold>//GEN-END:initComponents

            private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                        // TODO add your handling code here:
            }//GEN-LAST:event_jButton1ActionPerformed

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
                                    java.util.logging.Logger.getLogger(PdksHareket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (InstantiationException ex) {
                                    java.util.logging.Logger.getLogger(PdksHareket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                                    java.util.logging.Logger.getLogger(PdksHareket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                                    java.util.logging.Logger.getLogger(PdksHareket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        //</editor-fold>

                        /* Create and display the form */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                                    public void run() {
                                                new PdksHareket().setVisible(true);
                                    }
                        });
            }
            // Variables declaration - do not modify//GEN-BEGIN:variables
            private javax.swing.JButton jButton1;
            private javax.swing.JScrollPane jScrollPane1;
            private javax.swing.JTable jTable1;
            // End of variables declaration//GEN-END:variables
}

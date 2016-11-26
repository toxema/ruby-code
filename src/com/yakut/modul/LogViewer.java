/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yakut.modul;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 * @author yakut
 */
public class LogViewer extends javax.swing.JFrame {

            Logger logger = Logger.getRootLogger();

            /**
             * Creates new form LogTest
             */
            public LogViewer() {
                        initComponents();
                        logger.addAppender(new LogAppender());
            }

            public /*static*/ class LogAppender extends AppenderSkeleton {

                        public LogAppender() {
                        }

                        @Override
                        protected void append(LoggingEvent event) {

                                    jTextArea1.append(event.getLevel().toString() + "  " + event.getLocationInformation().fullInfo + " " + ":" + event.getMessage().toString() + "\r\n");
                        }

                        @Override
                        public void close() {
                        }

                        @Override
                        public boolean requiresLayout() {
                                    return false;
                        }
            }

            @SuppressWarnings("unchecked")
          // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
          private void initComponents() {

                    jScrollPane1 = new javax.swing.JScrollPane();
                    jTextArea1 = new javax.swing.JTextArea();
                    jPanel1 = new javax.swing.JPanel();
                    jComboBox1 = new javax.swing.JComboBox();
                    jCheckBox1 = new javax.swing.JCheckBox();
                    jLabel1 = new javax.swing.JLabel();

                    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                    jTextArea1.setColumns(20);
                    jTextArea1.setRows(5);
                    jScrollPane1.setViewportView(jTextArea1);

                    getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

                    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));

                    jCheckBox1.setText("eşit ise göster");

                    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/yakut/modul/loading1.gif"))); // NOI18N
                    jLabel1.setText("jLabel1");

                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                    jPanel1.setLayout(jPanel1Layout);
                    jPanel1Layout.setHorizontalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                  .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                  .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(jCheckBox1)
                                                            .addGap(0, 0, Short.MAX_VALUE)))
                                        .addContainerGap())
                    );
                    jPanel1Layout.setVerticalGroup(
                              jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                  .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                  .addComponent(jCheckBox1))
                                        .addContainerGap())
                    );

                    getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

                    pack();
          }// </editor-fold>//GEN-END:initComponents

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
                        } catch (Exception ex) {
                        }
                        //</editor-fold>

                        /* Create and display the form */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                                new LogViewer().setVisible(true);
                                    }
                        });
            }
          // Variables declaration - do not modify//GEN-BEGIN:variables
          private javax.swing.JCheckBox jCheckBox1;
          private javax.swing.JComboBox jComboBox1;
          private javax.swing.JLabel jLabel1;
          private javax.swing.JPanel jPanel1;
          private javax.swing.JScrollPane jScrollPane1;
          private javax.swing.JTextArea jTextArea1;
          // End of variables declaration//GEN-END:variables
}


package com.yakut.util;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 *
 * @author yakut
 */
public class ExceptionHandler extends javax.swing.JDialog {

            static Logger logger = Logger.getLogger(ExceptionHandler.class);

            private ExceptionHandler(java.awt.Frame parent, boolean modal) {
                        super(parent, modal);    
                        initComponents();
            }

            public static void notifyException(String message, Exception ex) {
                        onException(message, ex);
                        JOptionPane.showMessageDialog(null, message + "\r\n" + Language.get("hata.mesajı.baslığı","Bir hata oluştu") + "\r\n" + ex.getMessage());
            }

            public static void onException(String message, Exception ex) {
                      
                      logger.error(message,ex);
            }

            @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabel1 = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                jLabel1.setText("jLabel1");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                                .addContainerGap())
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

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
                        } catch (                ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                                    java.util.logging.Logger.getLogger(ExceptionHandler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        //</editor-fold>

                        /* Create and display the dialog */
                        java.awt.EventQueue.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                                ExceptionHandler dialog = new ExceptionHandler(new javax.swing.JFrame(), true);
                                                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                                            @Override
                                                            public void windowClosing(java.awt.event.WindowEvent e) {
                                                                        System.exit(0);
                                                            }
                                                });
                                                dialog.setVisible(true);
                                    }
                        });
            }
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        // End of variables declaration//GEN-END:variables
}

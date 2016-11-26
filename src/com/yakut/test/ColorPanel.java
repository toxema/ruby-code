package com.yakut.test;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


public class ColorPanel extends javax.swing.JPanel {

            int colors[][] = {
                        {0x822111, 0xAC2B16, 0xCC3A21, 0xE66550, 0xEFA093, 0xF6C5BE},
                        {0xA46A21, 0xCF8933, 0xEAA041, 0xFFBC6B, 0xFFD6A2, 0xFFE6C7},
                        {0xAA8831, 0xD5AE49, 0xF2C960, 0xFCDA83, 0xFCE8B3, 0xFEF1D1},
                        {0x076239, 0x0B804B, 0x149E60, 0x44B984, 0x89D3B2, 0xB9E4D0},
                        {0x1A764D, 0x2A9C68, 0x3DC789, 0x68DFA9, 0xA0EAC9, 0xC6F3DE},
                        {0x1C4587, 0x285BAC, 0x3C78D8, 0x6D9EEB, 0xA4C2F4, 0xC9DAF8},
                        {0x41236D, 0x653E9B, 0x8E63CE, 0xB694E8, 0xD0BCF1, 0xE4D7F5},
                        {0x83334C, 0xB65775, 0xE07798, 0xF7A7C0, 0xFBC8D9, 0xFCDEE8},
                        {0x000000, 0x434343, 0x666666, 0x999999, 0xCCCCCC, 0xEFEFEF}};

            public ColorPanel() {
                        initComponents();
            }

            @Override
            public void paint(Graphics g) {
                        super.paint(g);
                        for (int k = 0; k < 9; k++) {
                                    for (int j = 0; j < 6; j++) {
                                                Color c = new Color(colors[k][j]);
                                                g.setColor(c);
                                                g.fillRect(k*40, j*40,40,40);
                                    }

                        }
            }

            
            public static void main(String[] args) {
                        JFrame f=new JFrame("test");
                        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        f.getContentPane().add(new ColorPanel());
                        f.setVisible(true);
            }
            @SuppressWarnings("unchecked")
            // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
            private void initComponents() {

                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                        this.setLayout(layout);
                        layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGap(0, 400, Short.MAX_VALUE)
                        );
                        layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGap(0, 300, Short.MAX_VALUE)
                        );
            }// </editor-fold>//GEN-END:initComponents
            // Variables declaration - do not modify//GEN-BEGIN:variables
            // End of variables declaration//GEN-END:variables
}

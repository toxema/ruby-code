package com.yakut.modul;


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author yakut
 */
public class RaporTableCellRenderer extends DefaultTableCellRenderer {
            int columnNumber = 0;
            public RaporTableCellRenderer() {
                        super();
            }

            public int getColumnNumber() {
                        return columnNumber;
            }

            public void setColumnNumber(int columnNumber) {
                        this.columnNumber = columnNumber;
            }
            
            public RaporTableCellRenderer(int colNo) {
                        this.columnNumber = colNo;
            }
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

            public Color getColor(int colorNo) {
                        int color = Math.abs((100 + 3 * colorNo) % 9);
                        Color c = new Color(colors[color][4]);//colorList[color];
                        return c;
            }

            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {
                        Component c;
                        //  "Tarih"," p.sicil","p.ad","p.soyad","p.departman_id","p.grup_id"
                        c = super.getTableCellRendererComponent(table, value,
                                isSelected, hasFocus,
                                row, column);

                        Object o = table.getValueAt(row, columnNumber);
                        if (o != null) {

                                    if (isSelected) {
                                                c.setBackground(Color.BLUE);
                                                c.setForeground(Color.WHITE);
                                    } else {
                                                c.setBackground(getColor(o.hashCode()));
                                                c.setForeground(Color.BLACK);
                                    }
                        }
                        return c;
            }
}

package com.yakut.util;

import java.io.File;
import javax.swing.ImageIcon;

/**
 *
 * @author yakut
 */
public class ImageLoader {

        ImageIcon image;
        ImageIcon imageDefault;

        public static ImageIcon getImage(String path) {
                File imageFile = new File(path);

                if (imageFile.exists()) {
                        return new javax.swing.ImageIcon(path);
                } else {
                        /*
                        OptionPane.errorIcon
                         OptionPane.informationIcon
                         OptionPane.warningIcon
                         OptionPane.questionIconp
                         */
                        return (ImageIcon) javax.swing.UIManager.getIcon("OptionPane.informationIcon");
                }
        }
}

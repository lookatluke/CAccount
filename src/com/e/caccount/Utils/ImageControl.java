/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author trito
 */
public class ImageControl {

    public ImageControl() {
    }

    public byte[] TransferBufferedImageToByteArray(BufferedImage bfImage) {
        byte[] byteImg = null;
        try {
            ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
            ImageIO.write(bfImage, "png", BAOS);
            byteImg = BAOS.toByteArray();
            BAOS.flush();
            BAOS.close();
        } catch (IOException ex) {
            Logger.getLogger(ImageControl.class.getName()).log(Level.SEVERE, "Cannot Transfer Buffered Image to Byte Array", ex);
        }
        return byteImg;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import com.e.caccount.CAccounting;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author trito
 */
public class QRcodeGenerator {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private static final String QR_CODE_IMAGE_PATH = "Members_QRCodes/";

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               INITIATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////  
    public QRcodeGenerator() {
        new FileUtil().DirectoriesCheck(QR_CODE_IMAGE_PATH);
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////              GENERATION            //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void generatePNGfile(String str1, String str2) {
        generateQRCodeImage(DecodeUTF8(str1, str2), 350, 350, makeQRcodepath(str1));
    }

    private static void generateQRCodeImage(String text, int width, int height, String filePath) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        } catch (WriterException | IOException ex) {
            Logger.getLogger(QRcodeGenerator.class.getName()).log(Level.SEVERE, "There is an problem to make an QRcode Image", ex);
        }
    }

    public String DecodeUTF8(String string1, String string2) {
        try {
            String str1 = new String(string1.getBytes("UTF-8"), "ISO-8859-1");
            String str2 = new String(string2.getBytes("UTF-8"), "ISO-8859-1");
            String text = str1 + "," + str2;
            return text;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(QRcodeGenerator.class.getName()).log(Level.SEVERE, "There is an error to convert String into UTF-8", ex);
        }
        return null;
    }

    public String makeQRcodepath(String name) {
        String path = QR_CODE_IMAGE_PATH + name + ".png";
        return path;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////              GETQRCODE             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public byte[] getByteImage(String str1, String str2) {
        byte[] byteImg = null;
        try {
            byteImg = getQRCodeImage(DecodeUTF8(str1, str2), 350, 350);
        } catch (WriterException | IOException ex) {
            Logger.getLogger(QRcodeGenerator.class.getName()).log(Level.SEVERE, "Cannot get byte type image!", ex);
        }
        return byteImg;
    }

    private byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    public File getQRcodeFile(String name) {
        FileUtil fileUtil = new FileUtil();
        return fileUtil.getQRcodeFileFormList(name, fileUtil.getAllFiles(QR_CODE_IMAGE_PATH, ".png"));
    }
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////NEW STAGE AND SHOW QRCODE IMAGE/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public synchronized void showQRCode(String name) {

        Platform.runLater(() -> {
            try {
                Image image = new Image(new FileInputStream(getQRcodeFile(name)));
                ImageView imgView = new ImageView(image);
                
                String[] values = name.split(",");

                Label label1 = new Label("QR코드를 스캔해 접속하세요!\nIP Address : " + values[0]);
                label1.setStyle("-fx-font : 16 arial;"
                        + "-fx-alignment : center;");

                Label label = new Label("");
                label.setStyle("-fx-padding : 10;");

                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setStyle("-fx-background-color : #FFFFFF;");
                vbox.getChildren().add(label);
                vbox.getChildren().add(label1);
                vbox.getChildren().add(imgView);

                Scene scene = new Scene(vbox);

                Stage stage = new Stage();
                stage.setTitle("C ACCOUNT Ver. 1.0 - SERVER QR CODE");
                stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
                stage.setScene(scene);
                stage.show();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(QRcodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}

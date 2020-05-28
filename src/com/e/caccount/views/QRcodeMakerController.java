/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Utils.PriviegedExceptionAction_O;
import com.e.caccount.Utils.QRcodeGenerator;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class QRcodeMakerController implements Initializable {

    @FXML
    private Button make;
    @FXML
    private Button close;
    @FXML
    private ImageView QRcodeImg;
    @FXML
    private Button openDir;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private Button ChooseDir;

    private QRcodeGenerator qRcodeGenerator = new QRcodeGenerator();

    private String path = "Members_QRCodes/";

    private byte[] byteImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDefaultQRcodeImage();
    }

    public void saveQRCode() {
        if (checkInput()) {
            setByteImage();
            acrFileSave(setFileName(), byteImg);
            setQRimage();
            openSelectedDir();
        }
        resetField();
    }

    public String setFileName() {
        String text = path + "/" + txtName.getText() + "(" + txtType.getText() + ").png";
        return text;
    }

    public void setByteImage() {
        byteImg = qRcodeGenerator.getByteImage(txtName.getText(), txtType.getText());
    }

    public void acrFileSave(String filePath, byte[] byteImg) {
        try {
            PriviegedExceptionAction_O prv = new PriviegedExceptionAction_O(filePath);
            FileOutputStream fos = (FileOutputStream) AccessController.doPrivileged(prv);
            fos.write(byteImg, 0, byteImg.length);
            fos.close();
        } catch (Exception ex) {
            Logger.getLogger(QRcodeMakerController.class.getName()).log(Level.SEVERE, "Cannot save the png file!", ex);
        }
    }

    public void resetField() {
        txtName.setText("");
        txtType.setText("");
    }

    public File showFileChooser() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select a Directory");
        File file = chooser.showDialog(Main.getQRcodeStage());
        return file;
    }

    public void openSelectedDir() {
        try {
            Desktop.getDesktop().open(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(QRcodeMakerController.class.getName()).log(Level.SEVERE, "Cannot open the directory!", ex);
        }
    }

    public void ChooseFilePath() {
        setPath(showFileChooser());
    }

    public void setPath(File file) {
        path = file.getAbsolutePath();
    }

    public void cancelBtn() {
        Main.hideQRstage();
    }

    public void setDefaultQRcodeImage() {
        QRcodeImg.setImage(new Image(CAccounting.class.getResourceAsStream("resources/_avatar.png")));
    }

    public void setQRimage() {
        QRcodeImg.setImage(new Image(getSavedFile().toURI().toString()));
    }

    public File getSavedFile() {
        File file = new File(setFileName());
        return file;
    }

    public boolean checkInput() {
        if (txtName.getText().equals("") || txtName == null || txtType.getText().equals("") || txtType == null) {
            showAlert("입력이 올바르지 않습니다.", "입력", "입력오류");
            return false;
        } else {
            return true;
        }
    }

    public void showAlert(String message, String title, String headerTxt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
        alert.showAndWait();
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }
}

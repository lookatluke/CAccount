/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.Person;
import com.e.caccount.Utils.NetworkConnection;
import com.e.caccount.Utils.ProgressDialog;
import com.e.caccount.Utils.QRcodeGenerator;
import com.e.caccount.Utils.XMLtoEXCEL;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class RootLayoutController implements Initializable {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private NetworkConnection networkConnection = NetworkConnection.getInstance();
    private QRcodeGenerator qRcodeGenerator = new QRcodeGenerator();
    private Person person = Person.getInstance();

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               INITIATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////          MENU ITEM METHOS          //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    public void handleSave() {
        File personFile = Main.getPersonFilePath();
        if (personFile != null) {
            Main.saveUserDataToXMLfile(personFile);
        } else {
            SaveASData();
        }
    }

    //SAVE AS...
    public void SaveASData() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", new String[]{"*.xml"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            Main.saveUserDataToXMLfile(file);
        }
    }

    public void LoadData() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", new String[]{"*.xml"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());

        if (file != null) {
            Main.loadUserDataFromXMLfile(file);
        }
    }

    public void ExitProgram() {
        Main.TerminateApplication();
    }

    public void SaveFileAsExcel() {
        // Save Excel
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", new String[]{"*.xls"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xls")) {
                file = new File(file.getPath() + ".xls");
            }
            // Start Progress
            Main.startProgressIndicator();
            XMLtoEXCEL xmlToexcel = new XMLtoEXCEL(file);
            xmlToexcel.startXMLtoEXCEL();
            // Stop Progress
            Main.stopProgressIndicator();
        }
    }

    public void OpenServer() {
        if (networkConnection.getServerRunning()) {
            showAlert("네트워크가 사용중입니다.\n서버를 실행할 수 없습니다.", "서버", "네트워크 사용 불가");
        } else {
            networkConnection.openServer();
        }
    }

    public void CloseServer() {
        if (networkConnection.getServerRunning()) {
            networkConnection.closeServer();
        } else {
            showAlert("서버가 이미 꺼져 있습니다.\n종료를 실행 할 수 없습니다.", "서버", "실행종료 오류");
        }
    }

    public void OpenQRcodeMaker() {
        Main.openQRCodemaker();
    }

    public void OpenServerQRcodeImage() {
        if (networkConnection.getServerRunning()) {
            //
        } else {
            showAlert("서버가 꺼져 있습니다.\nQR코드를 불러올 수 없습니다.", "QR코드", "불러오기 오류");
        }
    }

    public void OpenClientSwichter() {
        /*
        if (networkConnection.getClientRunning()) {
            showAlert("네트워크가 이미 사용중입니다.\n클라이언트 모드를 실행할 수 없습니다.", "서버", "네트워크 오류");
        } else {
            Main.openClientMode();
            Main.getPrimaryStage().hide();
            networkConnection.StartNewClientThread();
        }
         */
    }

    public void CloseClientSwitcher() {
        /*
        if (networkConnection.getClientRunning()) {
            showAlert("클라이언트 모드를 종료합니다.", "종료", "클라이언드 모드");
            networkConnection.closeClientModeStage();
        } else {
            showAlert("클라이언트 모드가 사용중이지 않습니다.\n클라이언트 모드를 종료 할 수 없습니다.", "실행", "클라이언드 모드");
        }
         */
    }

    public void showAlert(String message, String title, String headerTxt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
        alert.showAndWait();
    }

    public void SaveOneTypeToExcel() {
        Main.openSelectOneTypeToExcel();
    }

    public void openHelpPage() {
        showAlert("현재 개발 중입니다.\n개선할 사항은 개발자에게 문의해 주세요.\ntritonle9135@gmail.com", "도움말", "개선/사용 문의");
        //Main.openHelp();
    }

    public void startOver() {
        showAlert("모든 파일을 지우고 새로 시작합니다.", "종료", "새로시작");
        // YES NO 입력에 따라 다르게 실행
        Main.StartNewFile();
    }

    public void openSendEmail() {
        Main.openSendEmail();
    }

    public void openReorderPage() {
        Main.openReorderStage();
    }
    
    public void openPersonalDataSearchPage(){
        Main.openPersonalDataSearchStage();
    }

}

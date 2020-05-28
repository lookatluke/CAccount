/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.Person;
import com.e.caccount.Utils.XMLtoEXCEL;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class OneTypeToExcelController implements Initializable {

    @FXML
    private ListView<String> typeList;
    @FXML
    private Button OK;
    @FXML
    private Button Close;
    @FXML
    private Label Title;

    private Person person = Person.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeList.setItems(person.getTypeList());
    }

    public void CloseStage() {
        Main.getOneTypeToExcelStage().hide();
    }

    private String type;

    public void MakeExcelFile() {
        if (typeList.getSelectionModel().getSelectedItem() != null) {
            type = typeList.getSelectionModel().getSelectedItem();
            SaveFileAsExcel();
            CloseStage();
        } else {
            showAlert("선택된 타입이 없습니다.\n타입을 선택해 주세요", "엑셀저장", "타입선택 오류");
        }
    }

    public void SaveFileAsExcel() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xls)", new String[]{"*.xls"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(Main.getOneTypeToExcelStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xls")) {
                file = new File(file.getPath() + ".xls");
            }
            // Start Progress
            Main.startProgressIndicator();
            XMLtoEXCEL xmlToexcel = new XMLtoEXCEL(file);
            xmlToexcel.setOneTypeList(type);
            // Stop Progress
            Main.stopProgressIndicator();
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

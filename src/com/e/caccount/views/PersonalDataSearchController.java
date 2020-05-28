/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.UserData;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class PersonalDataSearchController implements Initializable {

    private File url;

    @FXML
    private TextField name;
    @FXML
    private TableView<UserData> uTable;
    @FXML
    private TableColumn<UserData, String> unameColumn;
    @FXML
    private TableColumn<UserData, String> utypeColumn;
    @FXML
    private TableColumn<UserData, Number> uamountColumn;
    @FXML
    private TableColumn<UserData, String> timestampColumn;

    private ObservableList<UserData> uList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 테이블 세팅
        uTable.setItems(uList);
        unameColumn.setCellValueFactory(value -> value.getValue().NameProperty());
        utypeColumn.setCellValueFactory(value -> value.getValue().TypeProperty());
        uamountColumn.setCellValueFactory(value -> value.getValue().AmountProperty());
        timestampColumn.setCellValueFactory(value -> {
            // Get time Reference
            // Convert it            
            StringProperty timeStamp = new SimpleStringProperty();
            return timeStamp;
        });
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN                //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    @FXML
    public void selectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        url = directoryChooser.showDialog(Main.getPrimaryStage());
    }

    @FXML
    public void startFind() {
        if (url != null) {
            String user_name = name.getText().toString();
            if (!user_name.equals("") && user_name != null) {
                getDataList(user_name);
                uList.clear();
                uList.addAll(receivedList);
            } else {
                showAlert("찾으실 분의 이름을 입력해 주세요.", "오류", "정보없음");
            }
        } else {
            showAlert("데이터 폴더를 선택하지 않으셨습니다", "오류", "폴더없음");
        }
    }

    private List<UserData> receivedList = new ArrayList<>();

    private void getDataList(String user_name) {
        receivedList = null;
    }

    public void showAlert(String message, String title, String headerTxt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
        alert.showAndWait();
    }

}

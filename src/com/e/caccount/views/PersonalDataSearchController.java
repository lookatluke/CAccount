/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.UserData;
import com.e.caccount.Model.userModel;
import com.e.caccount.Utils.SearchlData;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

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
    private TableView<userModel> uTable;
    @FXML
    private TableColumn<userModel, String> unameColumn;
    @FXML
    private TableColumn<userModel, String> utypeColumn;
    @FXML
    private TableColumn<userModel, Number> uamountColumn;
    @FXML
    private TableColumn<userModel, String> timestampColumn;
    @FXML
    private Button folder;
    @FXML
    private Button search;

    private ObservableList<userModel> uList = FXCollections.observableArrayList();

    private List<userModel> receivedList = new ArrayList<>();
    private SearchlData searchPersonalData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (searchPersonalData == null) {
            searchPersonalData = new SearchlData();
        }
        searchPersonalData.start();
        // 테이블 세팅
        uTable.setItems(uList);
        uTable.setEditable(false);
        unameColumn.setCellValueFactory(value -> value.getValue().NameProperty());
        utypeColumn.setCellValueFactory(value -> value.getValue().TypeProperty());
        uamountColumn.setCellValueFactory(value -> value.getValue().AmountProperty());
        uamountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        timestampColumn.setCellValueFactory(value -> value.getValue().DateTimeProperty());
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN                //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
        Main.getPersonalDataSearchStage().setOnCloseRequest((event) -> {
            if (searchPersonalData != null) {
                System.out.println("out");
                searchPersonalData.interrupt();
            }
        });
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
                Main.startProgressIndicator();
                getDataList(user_name);
                uList.clear();
                uList.addAll(receivedList);
                Main.stopProgressIndicator();
            } else {
                showAlert("찾으실 분의 이름을 입력해 주세요.", "오류", "정보없음");
            }
        } else {
            showAlert("데이터 폴더를 선택하지 않으셨습니다", "오류", "폴더없음");
        }
    }

    private void getDataList(String user_name) {
        receivedList.clear();
        receivedList = searchPersonalData.getPersonalHistory(url, user_name);
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

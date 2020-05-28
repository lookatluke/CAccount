/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Message.Message;
import com.e.caccount.Model.Person;
import com.e.caccount.Utils.NetworkConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class ClientModeController implements Initializable, Observer {

    @FXML
    private Button send;
    @FXML
    private Button close;
    @FXML
    private TextField name;
    @FXML
    private TextField type;
    @FXML
    private TextField amount;
    @FXML
    private Label StatusMsg;
    @FXML
    private Circle status;
    @FXML
    private TextField ipaddress;
    @FXML
    private Label help_text;
    @FXML
    private Label Title;

    private NetworkConnection networkConnection = NetworkConnection.getInstance();

    private Observable oberverReg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registerOberver();
        setNumberOnlyTextField(amount);
        ipaddress.setText(networkConnection.getHOSTIPADDRESS());
    }

    public void setNumberOnlyTextField(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,9}([\\.]\\d{0,4})?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    public void registerOberver() {
        oberverReg = Person.getInstance();
        oberverReg.addObserver(this);
    }

    public void sendMessage() {
        if (FieldCheck()) {
            networkConnection.SendMessageToServer(getMessage());
        }
        resetField();
    }

    public boolean FieldCheck() {
        if (name.getText().equals("") || name.getText() == null) {
            showAlert("입력하신 이름이 없습니다.", "알람", "입력오류");
            return false;
        }
        if (type.getText().equals("") || type.getText() == null) {
            showAlert("입력하신 타입이 없습니다.", "알람", "입력오류");
            return false;
        }
        if (amount.getText().equals("") || amount.getText() == null) {
            showAlert("입력하신 금액이 없습니다.", "알람", "입력오류");
            return false;
        }

        return true;
    }

    public void resetField() {
        name.setText("");
        amount.setText("");
        name.requestFocus();
    }

    public Message getMessage() {
        Message msg = new Message();
        msg.setName(name.getText());
        msg.setType(type.getText());
        msg.setAmount(Integer.valueOf(amount.getText()));
        return msg;
    }

    public void closeClientMode() {
        networkConnection.closeClientModeStage();
    }

    public void setIpAddress() {
        //networkConnection.setIPaddress(ipaddress.getText());
        //showAlert("입력하신 IP주소로 접속을 시도합니다.\n연결될때까지 기다려주세요!", "클라이언트 모드", "클라이언트 모드 연결");
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Person) {
            switch (arg.toString()) {
                case "SERVER":
                    setClientRunning();
                    break;
            }
        }
    }

    public void setClientRunning() {
        Platform.runLater(() -> {
            if (networkConnection.getClientRunning()) {
                StatusMsg.setText("클라이언트 모드가 운영중!");
                status.setFill(Color.GREEN);
                setIpTextFieldStyle(false);
                help_text.setVisible(false);
            } else {
                StatusMsg.setText("서버에 연결되지 않습니다!");
                status.setFill(Color.RED);
                setIpTextFieldStyle(true);
                help_text.setVisible(true);
            }
        });
    }

    public void setIpTextFieldStyle(boolean TF) {
        //ipaddress.setEditable(TF);        
        ipaddress.setEditable(false);
        ipaddress.setText(networkConnection.getHOSTIPADDRESS());
        //if (TF) {
        //    ipaddress.setStyle(null);
        //} else {
        ipaddress.setStyle("-fx-focus-color: transparent;"
                + "-fx-background-color: -fx-control-inner-background;"
                + "-fx-background-insets: 0;"
                + "-fx-padding: 1 3 1 3;");
        //}
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.Person;
import com.e.caccount.Model.UserData;
import com.e.caccount.Utils.CheckNumber;
import com.e.caccount.Utils.NetworkConnection;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class AccountBookController implements Initializable, Observer {
    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private AnchorPane MainPane;
    @FXML
    private HBox TableView;
    @FXML
    private ComboBox comboBox;
    @FXML
    private TextField user_name;
    @FXML
    private TextField user_amount;
    @FXML
    private AnchorPane ControllPane;
    @FXML
    private FlowPane PlustBtn;
    @FXML
    private ScrollPane TableScrollPane;
    @FXML
    private Button OkBtn;
    @FXML
    private Label TYPESUM;

    private Observable oberverReg;
    private Person person = Person.getInstance();
    private NetworkConnection networkConnection = NetworkConnection.getInstance();

    private Map<String, TableView> tableMap = new HashMap<>();

    @FXML
    private FlowPane RegBtnPane;
    @FXML
    private TextField RegTxtField;
    @FXML
    private Button RegBtn;
    @FXML
    private Button RegCancelBtn;
    @FXML
    private Label RegTitle;
    @FXML
    private Label RegType;

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               INITIATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registerOberver();
        setNumberOnlyTextField(user_amount);
        setPlusBtnSetOnMouseEntered();
        setComboBox();
        setTableInsertBtn();
        setSpinners();
        setServerRunning();
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

    public void setTableInsertBtn() {
        Button table_insert = new Button();
        table_insert.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerPlustBtn);
        table_insert.setId("table_insert");
        table_insert.setPrefSize(30, 30);

        PlustBtn.getChildren().add(table_insert);

        setRegBtn();
        setRegCancelBtn();
    }

    public void setRegBtn() {
        RegBtn.setOnMouseClicked(t -> {
            if (person.isThisTypeExist(RegTxtField.getText()) || RegTxtField.getText() == null || RegTxtField.getText().equals("")) {
                RegBtnPane.setVisible(false);
                RegTxtField.setText("");
                showAlert("이미 존재하는 항목입니다.\n다시 입력해 주세요.", "타입추가", "타입추가 오류발생");
            } else {
                person.setUserData("NEWTABLECREATION", RegTxtField.getText(), 0);
                RegBtnPane.setVisible(false);
                RegTxtField.setText("");
            }
        });
    }

    public void setRegCancelBtn() {
        RegCancelBtn.setOnMouseClicked(e -> {
            RegBtnPane.setVisible(false);
            RegTxtField.setText("");
        });
    }

    EventHandler<MouseEvent> eventHandlerPlustBtn = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            RegBtnPane.setVisible(true);
            RegTxtField.requestFocus();
        }
    };

    public void setPlusBtnSetOnMouseEntered() {
        TableScrollPane.setOnMouseEntered((event) -> {
            PlustBtn.setVisible(true);
        });
        TableScrollPane.setOnMouseExited((event2) -> {
            PlustBtn.setVisible(false);
        });
        PlustBtn.setOnMouseEntered((event) -> {
            PlustBtn.setVisible(true);
        });
    }

    public void setComboBox() {
        comboBox.setItems(person.getTypeList());
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////////////                SPINNER             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private Label SumofCoinAndBills;
    @FXML
    private Spinner Baekwon;
    @FXML
    private Spinner OhBaekwon;
    @FXML
    private Spinner Choenwon;
    @FXML
    private Spinner OhChoenwon;
    @FXML
    private Spinner Manwon;
    @FXML
    private Spinner OhManwon;

    public void setSumOfCoinAndBills() {
        SumofCoinAndBills.setText(NumberFormat.getIntegerInstance().format(person.getSumOfCoinsAndBills()) + " 원");
    }

    public void setCoinCounts() {
        Baekwon.getValueFactory().setValue(person.getBaekwon());
        OhBaekwon.getValueFactory().setValue(person.getOhBaekwon());
        Choenwon.getValueFactory().setValue(person.getChoenwon());
        OhChoenwon.getValueFactory().setValue(person.getOhChoenwon());
        Manwon.getValueFactory().setValue(person.getManwon());
        OhManwon.getValueFactory().setValue(person.getOhManwon());
    }

    public void setSpinners() {
        setSpinnerProperty(Baekwon);
        Baekwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setBaekwon((int) newValue);
            }
        });

        setSpinnerProperty(OhBaekwon);
        OhBaekwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setOhBaekwon((int) newValue);
            }
        });

        setSpinnerProperty(Choenwon);
        Choenwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setChoenwon((int) newValue);
            }
        });

        setSpinnerProperty(OhChoenwon);
        OhChoenwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setOhChoenwon((int) newValue);
            }
        });

        setSpinnerProperty(Manwon);
        Manwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setManwon((int) newValue);
            }
        });

        setSpinnerProperty(OhManwon);
        OhManwon.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (CheckNumber.isNumeric(String.valueOf(newValue))) {
                person.setOhManwon((int) newValue);
            }
        });

    }

    public void setSpinnerProperty(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0));
        spinner.setEditable(true);
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                spinner.increment(0); // won't change value, but will commit editor
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////              DISTRIBUTOR           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Person) {
            switch (arg.toString()) {
                case "NEW":
                    createNewTableView(person.getType());
                    setTypeSum();
                    break;
                case "UPDATE":
                    updateTableView(person.getType());
                    setTypeSum();
                    break;
                case "DELETE":
                    deleteTableView(person.getType());
                    setTypeSum();
                    networkConnection.broadCast("NO NAME", person.getType() + ",TABLEDELETE", 0);
                    break;
                case "COINANDBILLS":
                    setCoinCounts();
                    setSumOfCoinAndBills();
                    break;
                case "REMOVEALL":
                    removeAll();
                    break;
                case "SERVER":
                    setServerRunning();
                    break;
                case "REORDER":
                    reorderTable();
                    break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////             TABLE MAKER            //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void createNewTableView(String type) {
        //BASE LAYOUT INITIALIZE
        AnchorPane LayoutPane = new AnchorPane();
        VBox tempVBox = new VBox();
        tempVBox.setAlignment(Pos.CENTER);
        FlowPane tempFlow = new FlowPane();
        tempFlow.getChildren().add(setDeleteButton(tempFlow, type));

        //TABLE & COLUMNS INITIALIZE
        TableView<UserData> tempTable = new TableView();
        tempTable.setItems(person.getUserData(type));
        TableColumn<UserData, String> tempNameColumn = new TableColumn("이 름");
        TableColumn<UserData, Number> tempAmountColumn = new TableColumn("금 액");
        tempAmountColumn.setId("tempAmountColumn");

        //SET PROPERTIES
        setTableViewScroll(tempTable);
        setFlowPaneMouseEvent(tempFlow, LayoutPane);
        setAnchorConstraints(tempFlow);
        setFlowPaneProperties(tempFlow);
        setAnchorPaneProperties(LayoutPane, tempTable);
        setTableViewProperties(tempTable, tempNameColumn, tempAmountColumn);

        //NAME COLUMN EVENT BUILDING
        tempNameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
        tempNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tempNameColumn.setOnEditCommit((TableColumn.CellEditEvent<UserData, String> event) -> {
            final UserData temUserData = (UserData) event.getTableView().getItems().get(event.getTablePosition().getRow());
            temUserData.setName((String) event.getNewValue());
            setAutoFitTableColumns(tempTable, tempNameColumn);
        });

        //AMOUNT COLUMN EVENT BUILDING
        tempAmountColumn.setCellValueFactory(cellData -> cellData.getValue().AmountProperty());
        tempAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        tempAmountColumn.setOnEditCommit((TableColumn.CellEditEvent<UserData, Number> event) -> {
            final UserData temUserData = (UserData) event.getTableView().getItems().get(event.getTablePosition().getRow());
            temUserData.setAmount((Integer) ((Number) event.getNewValue().longValue()).intValue());
            setAutoFitTableColumns(tempTable, tempAmountColumn);
            Platform.runLater(() -> {
                person.setUserData(temUserData.getName(), temUserData.getType(), temUserData.getAmount());
            });
        });

        //DEPLOY CHILDREN INTO THE PARENT NODE.
        tempTable.getColumns().addAll(tempNameColumn, tempAmountColumn);
        tempVBox.getChildren().addAll(setTypeLabel(type, tempTable), tempTable, setTableTotalLabel(type, tempNameColumn, tempAmountColumn));
        LayoutPane.getChildren().addAll(tempVBox, tempFlow);
        TableView.getChildren().addAll(LayoutPane);

        autoFitTable(tempTable);

        //INSERT INFORMATION INTO THE MAP
        tableMap.put(type, tempTable);
    }

    public void setTableViewScroll(TableView tableView) {
        tableView.getItems().addListener((ListChangeListener<UserData>) (c -> {
            c.next();
            final int size = tableView.getItems().size();
            if (size > 0) {
                tableView.scrollTo(size - 1);
            }
        }));
    }

    public Label setTypeLabel(String type, TableView tempTable) {
        Label typeLabel = new Label(type);
        typeLabel.setId("TableTypeLabel");
        return typeLabel;
    }

    public Label setTableTotalLabel(String type, TableColumn<UserData, String> tempNameColumn, TableColumn<UserData, Number> tempAmountColumn) {
        Label tableTotalLabel = new Label(NumberFormat.getIntegerInstance().format((int) person.getAmount()) + " 원");
        tableTotalLabel.setId("TableTotalLabel");
        tableTotalLabel.prefWidthProperty().bind(tempNameColumn.widthProperty().add(tempAmountColumn.widthProperty()));
        return tableTotalLabel;
    }

    public void setAnchorConstraints(FlowPane tempFlow) {
        AnchorPane.setTopAnchor(tempFlow, 10.0);
        AnchorPane.setRightAnchor(tempFlow, 0.0);
    }

    public void setFlowPaneProperties(FlowPane tempFlow) {
        tempFlow.setPrefSize(20, 20);
        tempFlow.setVisible(false);
    }

    public void setAnchorPaneProperties(AnchorPane LayoutPane, TableView<UserData> tempTable) {
        LayoutPane.setId("LAYOUTPANE");
        LayoutPane.prefWidthProperty().bind(tempTable.widthProperty());
    }

    public void setTableViewProperties(TableView<UserData> tempTable, TableColumn<UserData, String> tempNameColumn, TableColumn<UserData, Number> tempAmountColumn) {
        tempTable.setEditable(true);
        tempTable.setId("TABLE");
        tempTable.prefHeightProperty().bind(MainPane.heightProperty().subtract(ControllPane.heightProperty().add(100.0)));
        tempTable.getSelectionModel().setCellSelectionEnabled(true);
        tempTable.prefWidthProperty().bind(tempNameColumn.widthProperty().add(tempAmountColumn.widthProperty()));
        setTableRightClickMenu(tempTable);
    }

    public void setTableRightClickMenu(TableView<UserData> tempTable) {
        tempTable.setRowFactory(new Callback<TableView<UserData>, TableRow<UserData>>() {
            @Override
            public TableRow<UserData> call(TableView<UserData> tableView) {
                final TableRow<UserData> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem removeMenuItem = new MenuItem("삭제");
                removeMenuItem.setOnAction((event) -> {
                    networkConnection.broadCast(row.getItem().getName(), row.getItem().getType() + ",DELETE", row.getItem().getAmount());
                    tempTable.getItems().remove(row.getItem()); //ListData is automatically deleted when the table cell is deleted.

                    setTypeSum();
                    updateTableView(row.getItem().getType());
                });
                contextMenu.getItems().add(removeMenuItem);

                // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                return row;
            }
        });
    }

    public Button setDeleteButton(FlowPane tempFlow, String type) {
        Button deleteBtn = new Button();
        deleteBtn.setPrefSize(30, 30);
        deleteBtn.setId("DELETEBUTTON");
        deleteBtn.setOnMouseClicked(event -> {
            person.deleteType(type);
            tempFlow.setVisible(false);
        });
        return deleteBtn;
    }

    public void setFlowPaneMouseEvent(FlowPane tempFlow, Pane LayoutPane) {
        LayoutPane.setOnMouseEntered((event) -> {
            tempFlow.setVisible(true);
        });
        LayoutPane.setOnMouseExited((event2) -> {
            tempFlow.setVisible(false);
        });
    }

    public Integer getTableViewindex(String type) {
        for (int i = 0; i < TableView.getChildren().size(); i++) {
            if (getTypeTitleLabel(i).getText().equals(type)) {
                return i;
            }
        }
        return null;
    }

    public void updateTableView(String type) {
        Integer tempIndex = getTableViewindex(type);
        if (tempIndex != null) {
            Platform.runLater(() -> {
                getTypeTotalLabel(tempIndex).setText(NumberFormat.getIntegerInstance().format((int) person.getSelectedTypeSUM(type)) + " 원");
            });
        }
    }

    public void deleteTableView(String type) {
        Integer tempIndex = getTableViewindex(type);
        if (tempIndex != null) {
            TableView.getChildren().remove((int) tempIndex);
        }
        tableMap.remove(type);
    }

    public Label getTypeTitleLabel(int i) {
        Pane LayoutPane = (Pane) TableView.getChildren().get(i);
        VBox tempVBox = (VBox) LayoutPane.getChildren().get(0);
        Label typeLabel = (Label) tempVBox.getChildren().get(0);
        return typeLabel;
    }

    public Label getTypeTotalLabel(int i) {
        Pane LayoutPane = (Pane) TableView.getChildren().get(i);
        VBox tempVBox = (VBox) LayoutPane.getChildren().get(0);
        Label tempLabel = (Label) tempVBox.getChildren().get(2);
        return tempLabel;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////       TABLE/COLUMN FIT SIZE METHOD     ////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private static Method columnToFitMethod;

    static {
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnToFitMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
        }
    }

    public static void autoFitTable(TableView tableView) {
        tableView.getItems().addListener(new ListChangeListener<Object>() {
            @Override
            public void onChanged(Change<?> c) {
                tableView.getColumns().forEach((column) -> {
                    setAutoFitTableColumns(tableView, column);
                });
            }
        });
    }

    public static void setAutoFitTableColumns(TableView tableView, Object tableColumn) {
        try {
            columnToFitMethod.invoke(tableView.getSkin(), tableColumn, -1);
        } catch (Exception e) {
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                METHOS              //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void showAlert(String message, String title, String headerTxt) {
        Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
        alert.showAndWait();
    }

    public void InsertBtn() {
        if (checkComboBox() && checkName() && checkAmount()) {
            person.setUserData(user_name.getText(), (String) comboBox.getValue(), Integer.valueOf(user_amount.getText()));
        } else {
            showAlert("입력값이 올바르지 않습니다.\n확인해 주세요", "입력", "입력오류 발생");
        }
        removeTextField();
    }

    public void removeTextField() {
        user_name.setText("");
        user_amount.setText("");
        user_name.requestFocus();
    }

    public boolean checkComboBox() {
        if (comboBox.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkName() {
        if (user_name.getText() == null || user_name.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkAmount() {
        if (user_amount.getText().equals("") || user_amount.getText() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void removeAll() {
        setCoinCounts();
        setSumOfCoinAndBills();
        TableView.getChildren().clear();
        TYPESUM.setText("0");
    }
    
    public void reorderTable(){
        TableView.getChildren().clear();
        TYPESUM.setText("0");
    }

    public void comboAction(ActionEvent event) {
        user_name.requestFocus();
    }

    public void setTypeSum() {
        TYPESUM.setText(NumberFormat.getIntegerInstance().format(person.getTypeSum()) + " 원");
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                SERVER              //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private Label serverRunnig;
    @FXML
    private Circle status;

    public void setServerRunning() {
        Platform.runLater(() -> {
            if (networkConnection.getServerRunning()) {
                serverRunnig.setText("서버가 운영중입니다!");
                status.setFill(Color.GREEN);
            } else {
                serverRunnig.setText("서버가 운영중이지 않습니다!");
                status.setFill(Color.RED);
            }
        });
    }
}

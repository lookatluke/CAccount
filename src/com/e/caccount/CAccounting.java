/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount;

import com.e.caccount.Model.Coins;
import com.e.caccount.Model.Person;
import com.e.caccount.Model.UserData;
import com.e.caccount.Model.UserDataWrapper;
import com.e.caccount.Utils.NetworkConnection;
import com.e.caccount.Utils.ProgressDialog;
import com.e.caccount.views.AccountBookController;
import com.e.caccount.views.ClientModeController;
import com.e.caccount.views.ExcelOrderController;
import com.e.caccount.views.HelpController;
import com.e.caccount.views.OneTypeToExcelController;
import com.e.caccount.views.PersonalDataSearchController;
import com.e.caccount.views.QRcodeMakerController;
import com.e.caccount.views.RootLayoutController;
import com.e.caccount.views.SendEmailController;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author LUKE
 */
public class CAccounting extends Application {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                PRELOADER           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    BooleanProperty ready = new SimpleBooleanProperty(false);

    private void longStart() {
        //simulate long init in background
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 10;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(200);
                    // Send progress to preloader
                    notifyPreloader(new ProgressNotification(((double) i) / max));
                }
                // After init is ready, the app is ready to be shown
                // Do this before hiding the preloader stage to prevent the 
                // app from exiting prematurely
                ready.setValue(Boolean.TRUE);

                notifyPreloader(new StateChangeNotification(
                        StateChangeNotification.Type.BEFORE_START));

                return null;
            }
        };
        new Thread(task).start();
    }
    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private NetworkConnection networkConnection = NetworkConnection.getInstance();
    private Stage PrimaryStage;
    private BorderPane RootLayout;
    private RootLayoutController rootController;
    private Person person = Person.getInstance();

    private String Title = "C ACCOUNT Ver. 1.8";

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               INITIATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        longStart();
        this.PrimaryStage = primaryStage;
        openRootLayout();
        DefaultTypeSetting();
        setPersonFilePath(null);
        networkConnection.setMain(this);
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                 STAGES             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void openRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/RootLayout.fxml"));
            RootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(RootLayout);

            rootController = loader.getController();
            rootController.setMain(this);
            openAccountBook();

            PrimaryStage.setScene(scene);
            PrimaryStage.setTitle(Title);
            PrimaryStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));

            ready.addListener(new ChangeListener<Boolean>() {
                public void changed(
                        ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (Boolean.TRUE.equals(t1)) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                PrimaryStage.show();
                            }
                        });
                    }
                }
            });;

            closeAll();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openAccountBook() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/AccountBook.fxml"));
            AnchorPane ACbook = (AnchorPane) loader.load();

            AccountBookController controller = loader.getController();
            controller.setMain(this);

            RootLayout.setCenter(ACbook);
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Stage QRStage;

    public void openQRCodemaker() {
        try {
            QRStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/QRcodeMaker.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            QRcodeMakerController controller = loader.getController();
            controller.setMain(this);

            QRStage.setScene(scene);
            QRStage.setTitle(Title + " - QR Code Maker");
            QRStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            QRStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Stage OneTypeToExcel;

    public void openSelectOneTypeToExcel() {
        try {
            OneTypeToExcel = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/OneTypeToExcel.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            OneTypeToExcelController controller = loader.getController();
            controller.setMain(this);

            OneTypeToExcel.setScene(scene);
            OneTypeToExcel.setTitle(Title + " - One type to Excel");
            OneTypeToExcel.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            OneTypeToExcel.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Stage clientModeStage;

    public void openClientMode() {
        try {
            clientModeStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/ClientMode.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            ClientModeController controller = loader.getController();
            controller.setMain(this);

            clientModeStage.setScene(scene);
            clientModeStage.initModality(Modality.APPLICATION_MODAL);
            clientModeStage.setTitle(Title + " - Client Mode");
            clientModeStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            clientModeStage.show();

            clientModeStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    networkConnection.closeClientModeStage();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void openHelp() {
        try {
            Stage HelpStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/Help.fxml"));
            SplitPane page = (SplitPane) loader.load();
            Scene scene = new Scene(page);

            HelpController controller = loader.getController();

            HelpStage.setScene(scene);
            HelpStage.initModality(Modality.APPLICATION_MODAL);
            HelpStage.setTitle(Title + " - HELP");
            HelpStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            HelpStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Stage mailStage;

    public void openSendEmail() {
        try {
            mailStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/SendEmail.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);

            SendEmailController controller = loader.getController();
            controller.setMain(this, mailStage);

            mailStage.setScene(scene);
            mailStage.initModality(Modality.APPLICATION_MODAL);
            mailStage.setTitle(Title + " - Mail Sender");
            mailStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            mailStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Stage reorderStage;
    
    public void openReorderStage(){
        try{
            reorderStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/ExcelOrder.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            
            ExcelOrderController controller = loader.getController();
            controller.setMain(this);
            
            reorderStage.setScene(scene);
            reorderStage.initModality(Modality.APPLICATION_MODAL);
            reorderStage.setTitle(Title + " - 테이블 재배치");
            reorderStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            reorderStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Stage personaldatasearchStage;
    
    public void openPersonalDataSearchStage(){
         try{
            personaldatasearchStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("views/PersonalDataSearch.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Scene scene = new Scene(page);
            
            PersonalDataSearchController controller = loader.getController();
            controller.setMain(this);
            
            personaldatasearchStage.setScene(scene);
            personaldatasearchStage.initModality(Modality.APPLICATION_MODAL);
            personaldatasearchStage.setTitle(Title + " - 개인기록 찾기");
            personaldatasearchStage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
            personaldatasearchStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void StartNewFile() {
        person.RemoveAllDATA();
        DefaultTypeSetting();
        setPersonFilePath(null);
    }

    public Stage getClientModeStage() {
        return clientModeStage;
    }

    public Stage getOneTypeToExcelStage() {
        return OneTypeToExcel;
    }

    public Stage getQRcodeStage() {
        return QRStage;
    }
    
    public Stage getReorderStage(){
        return this.reorderStage;
    }

    public Stage getPersonalDataSearchStage(){
        return this.personaldatasearchStage;
    }
    
    public void hideQRstage() {
        QRStage.hide();
    }

    public void closeAll() {
        PrimaryStage.setOnCloseRequest(t -> {
            TerminateApplication();
        });
    }

    public void TerminateApplication() {
        if (networkConnection.getServerRunning()) {
            networkConnection.closeServer();
        }
        setSaveButtonAlert();
    }

    public void setSaveButtonAlert() {
        ButtonType OK = new ButtonType("예", ButtonBar.ButtonData.OK_DONE);
        ButtonType CANCEL = new ButtonType("아니요", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(AlertType.WARNING, "데이터를 저장 후 종료 하시겠습니까?", OK, CANCEL);
        alert.setHeaderText("데이터 저장");
        alert.setTitle("프로그램 종료");
        alert.initOwner(PrimaryStage.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(CANCEL) == OK) {
            rootController.handleSave();
        } else {
            System.exit(0);
        }
    }

    public Stage getPrimaryStage() {
        return this.PrimaryStage;
    }

    public void showAlert(String message, String title, String headerTxt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES);
        alert.setTitle(title);
        alert.setHeaderText(headerTxt);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CAccounting.class.getResourceAsStream("resources/_stageIcon.png")));
        alert.showAndWait();
    }

    public void DefaultTypeSetting() {
        String[] TempTypes = {"십일조", "감사헌금", "건축헌금", "오병이어", "주정헌금"};
        for (int i = 0; i < TempTypes.length; i++) {
            person.setUserData("NEWTABLECREATION", TempTypes[i], 0);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////         XML FILE CONTROLL          //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void saveUserDataToXMLfile(File file) {
        // Start Progress
        startProgressIndicator();
        // Load Data
        try {
            JAXBContext context = JAXBContext.newInstance(UserDataWrapper.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            UserDataWrapper wrapper = new UserDataWrapper();
            wrapper.setUserData(person.getCompliedList());
            wrapper.setCoins(person.getCoinsList());

            m.marshal(wrapper, file);

            setPersonFilePath(file);
        } catch (JAXBException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, "An error occured during the saving", ex);
        }
        // Stop Progress
        stopProgressIndicator();
    }

    public void loadUserDataFromXMLfile(File file) {
        // Start Progress
        startProgressIndicator();
        // Load Data
        try {
            JAXBContext context = JAXBContext.newInstance(UserDataWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            UserDataWrapper wrapper = (UserDataWrapper) um.unmarshal(file);
            person.RemoveAllDATA();

            setPersonFilePath(file);

            SeperateTypes(wrapper.getUserData());
            setCoinsAndBills(wrapper.getCoins());
        } catch (JAXBException ex) {
            Logger.getLogger(CAccounting.class.getName()).log(Level.SEVERE, "An error occured during the loading", ex);
        }
        // Stop Progress
        stopProgressIndicator();
    }

    public void setUserData(final String name, final String type, final Integer amount) {
        Platform.runLater(() -> {
            person.setUserData(name, type, amount);
        });
    }

    public void SeperateTypes(List<UserData> tempList) {
        try {
            person.setUserData(tempList.get(0).getName(), tempList.get(0).getType(), tempList.get(0).getAmount());
            String type = tempList.get(0).getType();

            for (int i = 1; i < tempList.size(); i++) {
                if (tempList.get(i).getType().equals(type)) {
                    setUserData(tempList.get(i).getName(), tempList.get(i).getType(), tempList.get(i).getAmount());
                } else {
                    person.setUserData(tempList.get(i).getName(), tempList.get(i).getType(), tempList.get(i).getAmount());
                    type = tempList.get(i).getType();
                }
            }
        } catch (Exception ex) {
            StartNewFile();
            showAlert("불러올 데이터가 없습니다.\n확인해 주세요", "불러오기", "불러오기 오류 발생");
        }
    }

    public void setCoinsAndBills(List<Coins> tempList) {
        tempList.stream()
                .forEach(e -> {
                    person.setBaekwon(e.getBaekwon());
                    person.setOhBaekwon(e.getOhBaekwon());
                    person.setChoenwon(e.getChoenwon());
                    person.setOhChoenwon(e.getOhChoenwon());
                    person.setManwon(e.getManwon());
                    person.setOhManwon(e.getOhManwon());
                });
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////         FILE PREFERENCE            //////////////////////
    ////////////////////////////////////////////////////////////////////////////  
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(CAccounting.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(CAccounting.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            PrimaryStage.setTitle(Title + " - [ " + file.getName() + " ]");
        } else {
            prefs.remove("filePath");
            PrimaryStage.setTitle(Title);
        }
    }
    
    /////////////////////////////////////    Progress Indicator ////////////////////////////////////
    private ProgressDialog pd = null;
    private boolean tf;

    public void startProgressIndicator() {
         tf = true;
        
        pd = new ProgressDialog(PrimaryStage.getOwner(), "Processing...");
        pd.addTaskEndNotification(result -> {
            pd = null;
        });

        pd.exec("100", inputParam -> {
            while(tf){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return new Integer(1);
        });
    }    
    
    public void stopProgressIndicator(){
        tf = false;
    }
}

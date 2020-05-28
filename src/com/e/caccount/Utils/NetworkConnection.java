/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import com.e.caccount.CAccounting;
import com.e.caccount.Message.Message;
import com.e.caccount.Model.Person;
import com.e.caccount.NetWork.IPreceiver;
import com.e.caccount.NetWork.TcpClient;
import com.e.caccount.NetWork.TcpServer;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author trito
 */
public class NetworkConnection {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private String HOSTIPADDRESS = "127.0.0.1";
    private int PORT = 1987;

    private boolean Server_running = false;

    private static NetworkConnection INSTANCE = null;
    private QRcodeGenerator qRcodeGenerator = new QRcodeGenerator();

    private Person person = Person.getInstance();

    private ServerSocket serverSocket;

    ////////////////////////////////////////////////////////////////////////////
    //////////////////             SINGLE TON             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public static NetworkConnection getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkConnection();
        }
        return INSTANCE;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               INITIATION           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private TcpServer tcpServer;

    public void openServer() {
        if (Server_running) {
            qRcodeGenerator.showQRCode(HOSTIPADDRESS);
        } else {
            //makeQRcode();
            tcpServer = new TcpServer();
            tcpServer.start();
            // Set ServerRunning
            setServerRunning(true);
            //openServer = new OpenServer();
            //openServer.start();
        }
    }

    public void closeServer() {
        if (Server_running) {
            // Send Close Message
            broadCast("SERVER", "TYPE,DISCONNECT", 0);
            // Close Server
            tcpServer.closeServer();
            setServerRunning(false);
        } else {
            //
        }
    }

    public void broadCast(String name, String type, int amount) {
        Message msg = new Message(name, type, amount);
        tcpServer.broadCastMessage(msg);
    }

    public String getHOSTIPADDRESS() {
        return HOSTIPADDRESS;
    }

    public void setIPaddress(String ipaddress) {
        HOSTIPADDRESS = ipaddress;
    }

    public void setServerRunning(boolean TF) {
        Server_running = TF;
        person.setNetworkingStatus();
    }

    public boolean getServerRunning() {
        return Server_running;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////              Client  Mode          //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private TcpClient tcpClient;

    private volatile String IPADDRESS;

    private void getIpAddressFromServer() {
        IPreceiver ipreceiver = new IPreceiver();
        ipreceiver.start();

        if (!ipreceiver.getIPADDRESS().equals("127.0.0.1")) {
            // Save String
            IPADDRESS = ipreceiver.getIPADDRESS();
            // Terminate Thread
            ipreceiver.interrupt();
            // Start Client 
            tcpClient = new TcpClient();
            tcpClient.start();
            // Set running 
            setClientRunnig(true);
        } else {
            // Terminate Thread
            ipreceiver.interrupt();
            try {
                // Delay 1 sec
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NetworkConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Retry
            getIpAddressFromServer();
        }
    }

    public void StartNewClientThread() {
        // Get Running Server
        if (!getClientRunning()) {
            // Set default IpAddress
            IPADDRESS = "127.0.0.1";
            // During this Method you can't do anything.
            getIpAddressFromServer();
        }
    }

    public void CloseClient() {
        if (getClientRunning()) {
            tcpClient.close();
            setClientRunnig(false);
        }
    }

    public void SendMessageToServer(Message msg) {
        tcpClient.snedMessage(msg);
    }

    public void closeClientModeStage() {
        // Close Client Connection
        CloseClient();
        // Change UI Status
        Platform.runLater(() -> {
            Main.getClientModeStage().hide();
            Main.getPrimaryStage().show();
        });
    }

    private boolean Client_running = false;

    public void setClientRunnig(boolean TF) {
        Client_running = TF;
        person.setNetworkingStatus();
    }

    public boolean getClientRunning() {
        return Client_running;
    }

    public void showAlert(String message, String title, String headerTxt) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.YES);
            alert.setTitle(title);
            alert.setHeaderText(headerTxt);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(CAccounting.class
                    .getResourceAsStream("resources/_stageIcon.png")));
            alert.showAndWait();
        });
    }
}

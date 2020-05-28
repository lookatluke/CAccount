/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import com.e.caccount.Message.Message;
import com.e.caccount.Model.Person;
import com.e.caccount.Utils.NetworkConnection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import javafx.application.Platform;

/**
 *
 * @author trito
 */
public class receiveThread extends Thread {

    private Socket socket;
    private ObjectInputStream inStObj;
    private Map<Socket, Thread> ThreadMap;
    private Person person = Person.getInstance();

    public receiveThread(Socket socket, Map<Socket, Thread> sendThreadMap) {
        this.socket = socket;
        this.ThreadMap = sendThreadMap;
    }

    @Override
    public void run() {
        try {
            inStObj = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            close();
        }

        while (true) {
            try {
                Message msg = (Message) inStObj.readObject();
                System.out.println("msg receiver : " + msg.getName() + " " + msg.getType() + " " + msg.getAmount());
                WhatIsMessageAbout(msg);
            } catch (ClassNotFoundException | IOException ex) {
                System.out.println("[Client Out - " + socket.getInetAddress() + "]");
                // Close Socket
                close();
                // break out of loop
                break;
            }

        }
    }

    // Pattern is like this ("헌금종류,NEW")
    public void WhatIsMessageAbout(Message msg) {
        String[] typeArray = msg.getType().split("\\s*,\\s*");

        if (typeArray[1] != null && !typeArray[1].equals("")) {
            if (ThreadMap != null) {
                DistributeMsgServer(msg, typeArray[0], typeArray[1]);
            } else {
                DistributeMsgClient(msg, typeArray[0], typeArray[1]);
            }
        }
    }

    private void DistributeMsgServer(Message msg, String type, String switcher) {
        switch (switcher) {
            case "DELETE":
                Platform.runLater(() -> {
                    person.deleteUserData(msg.getName(), type);
                });
                broadCast(msg.getName(), msg.getType(), msg.getAmount());
                break;
            case "NEW":
                Platform.runLater(() -> {
                    person.setUserData(msg.getName(), type, msg.getAmount());
                });
                break;
            case "MANUAL":
                Platform.runLater(() -> {
                    person.setUserData(msg.getName(), type, msg.getAmount());
                    person.insertManualReceivedUser(msg.getName(), type);
                });
                break;
            default:
                // Break out of loop
                break;
        }
    }

    private void DistributeMsgClient(Message msg, String type, String switcher) {
        switch (switcher) {
            case "DISCONNECT":
                //showAlert("서버가 꺼졌습니다.\n다시 켜질떄 까지 기다려 주세요!", "클라이언트 모드", "클라이언트 모드 연결");
                break;
            case "DELETE":
                break;
            default:
                // Break out of loop
                break;
        }
    }

    public void broadCast(String name, String type, int amount) {
        Message msg = new Message(name, type, amount);
        for (Map.Entry<Socket, Thread> entry : ThreadMap.entrySet()) {
            sendThread sendThread = (sendThread) entry.getValue();
            sendThread.sendMessage(msg);
        }
    }

    public void close() {
        try {
            if (inStObj != null) {
                inStObj.close();
            }
        } catch (IOException ioe) {

        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ioe) {

        }

        // Close Thread
        interrupt();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import com.e.caccount.Message.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * @author trito
 */
public class TcpClient extends Thread {

    private String hostIp;
    private int hostPort = 1987;
    
    public TcpClient() {
    }

    public TcpClient(String IP, int PORT) {
        this.hostIp = IP;
        this.hostPort = PORT;
    }

    @Override
    public void run() {
        connect();
    }

    private Socket socket;
    private sendThread threadSend;
    private receiveThread threadReceive;

    public void connect() {
        try {
            // Client Socket
            socket = new Socket();
            socket.connect(new InetSocketAddress(hostIp, hostPort), 3000);
            System.out.println("Client Connected");

            // Output Thread
            threadSend = new sendThread(socket);
            threadSend.start();

            // Input Thread
            threadReceive = new receiveThread(socket, null);
            threadReceive.start();

            // IllegalArgumentException was added in case InetsocketAddress' ipaddress null exception
        } catch (IOException | IllegalArgumentException e) {
            try {
                System.out.println("IOException Try Connection Again! " + hostIp);
                Thread.sleep(1000);
                connect();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void close() {
        if (socket != null) {
            threadSend.close();
            threadReceive.close();
            interrupt();
        }
    }

    public void snedMessage(Message msg) {
        if (threadSend != null) {
            threadSend.sendMessage(checkMessage(msg));
        }
    }

    private Message checkMessage(Message msg) {
        if (msg.getName().equals("") || msg.getName() == null) {
            msg.setName("무명");
        }

        if (msg.getType().equals("") || msg.getType() == null) {
            msg.setType("타입없음");
        }

        if (msg.getAmount() == null) {
            msg.setAmount(0);
        }

        return msg;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import com.e.caccount.Message.Message;
import com.e.caccount.Utils.NetworkConnection;
import com.e.caccount.Utils.QRcodeGenerator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trito
 */
public class TcpServer extends Thread {

    private IpAddress ipaddress;
    private String IPADDRESS = "127.0.0.1";
    private final int PORT = 1987;
    private Map<Socket, Thread> sendThreadMap = new HashMap<Socket, Thread>();
    private Map<Socket, Thread> receiveTrheadMap = new HashMap<Socket, Thread>();

    public TcpServer() {
        if (ipaddress == null) {
            ipaddress = new IpAddress();
        }
    }

    private IPnotifier ipnotifier;
    private ServerSocket sSocket;

    @Override
    public void run() {
        try {
            IPADDRESS = ipaddress.getIpAddress();

            sSocket = new ServerSocket();
            sSocket.bind(new InetSocketAddress(IPADDRESS, PORT));

            makeQRcode();

            ipnotifier = new IPnotifier(IPADDRESS);
            ipnotifier.start();

            while (true) {
                final Socket accept = sSocket.accept();
                System.out.println("[Client accepted - " + accept.getInetAddress() + "] " + PORT + "::" + IPADDRESS);

                Thread sendThread = new sendThread(accept);
                checkExistUser(accept, "SEND");
                sendThreadMap.put(accept, sendThread);
                sendThread.start();

                Thread receiveThread = new receiveThread(accept, sendThreadMap);
                checkExistUser(accept, "RECEIVER");
                receiveTrheadMap.put(accept, receiveThread);
                receiveThread.start();
            }
        } catch (IOException ioe) {
            // Close Server
            closeServer();
        }
    }

    private void checkExistUser(Socket socket, String type) {
        Map<Socket, Thread> tempMap = new HashMap<>();
        if (type.equals("SEND")) {
            tempMap.putAll(sendThreadMap);
            for (Map.Entry<Socket, Thread> entry : tempMap.entrySet()) {
                if (entry.getKey().getInetAddress().toString()
                        .equals(socket.getInetAddress().toString())) {
                    sendThreadMap.remove(entry.getKey());
                    // Close Thread
                    sendThread thread = (sendThread) entry.getValue();
                    thread.close();
                }
            }
        } else if (type.equals("RECEIVER")) {
            tempMap.putAll(receiveTrheadMap);
            for (Map.Entry<Socket, Thread> entry : tempMap.entrySet()) {
                if (entry.getKey().getInetAddress().toString()
                        .equals(socket.getInetAddress().toString())) {
                    receiveTrheadMap.remove(entry.getKey());
                    // Close Thread
                    receiveThread thread = (receiveThread) entry.getValue();
                    thread.close();
                }
            }
        }
    }

    public void closeServer() {
        // Close DataGram
        closeIpNotifier();
        // Close Client Sockets
        closeClientSockets();
        // Clsoe Server Socket
        try {
            sSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Close Thread
        interrupt();
    }

    public void closeClientSockets() {
        // Close Send Threads
        for (Map.Entry<Socket, Thread> entry : sendThreadMap.entrySet()) {
            // Close Thread
            sendThread thread = (sendThread) entry.getValue();
            thread.close();
        }
        // Clsoe Receive Threads
        for (Map.Entry<Socket, Thread> entry : receiveTrheadMap.entrySet()) {
            // Close Thread
            receiveThread thread = (receiveThread) entry.getValue();
            thread.close();
        }
        // Clear Map
        sendThreadMap.clear();
        receiveTrheadMap.clear();
    }

    public void closeIpNotifier() {
        ipnotifier.setRunning(false);
        ipnotifier.interrupt();
    }

    public void broadCastMessage(Message msg) {
        for (Map.Entry<Socket, Thread> entry : sendThreadMap.entrySet()) {
            if (!entry.getKey().isClosed()) {
                // Send Message
                sendThread thread = (sendThread) entry.getValue();
                thread.sendMessage(msg);
            } else {
                // Close sendThread
                sendThread sThread = (sendThread) entry.getValue();
                sThread.close();
                // Close Receive Thread
                receiveThread rThread = (receiveThread) receiveTrheadMap.get(entry.getKey());
                rThread.close();
            }
        }
    }

    private QRcodeGenerator qRcodeGenerator = new QRcodeGenerator();

    public void makeQRcode() {
        if (!IPADDRESS.endsWith("127.0.0.1")) {
            qRcodeGenerator.generatePNGfile(IPADDRESS, DecodeInteger(PORT));
            qRcodeGenerator.showQRCode(IPADDRESS);
        }
    }

    public String DecodeInteger(Integer port) {
        String portNumber = String.valueOf(port);
        return portNumber;
    }

}

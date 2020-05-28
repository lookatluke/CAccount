/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import com.e.caccount.Message.Message;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author trito
 */
public class sendThread extends Thread {
    
        private Socket socket;
        private ObjectOutputStream outStObj;

        public sendThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                outStObj = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException ioe) {
                close();
            }
        }

        public void sendMessage(Message msg) {
            try {
                outStObj.writeObject(msg);
                outStObj.flush();
            } catch (IOException ioe) {
                System.out.println("sendThread writeObject Exception Occurs");
                // Close Socket
                close();
            }
        }

        public void close() {
            try {
                if (outStObj != null) {
                    outStObj.close();
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

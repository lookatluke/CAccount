/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author trito
 */
public class IPnotifier extends Thread {

    private String IPADDRESS;

    public IPnotifier(String ipaddress) {
        this.IPADDRESS = ipaddress;
    }

    @Override
    public void run() {
        try {
            InetAddress inetAddress = InetAddress.getByName("255.255.255.255");
            int inetPort = 8200;

            int length = IPADDRESS.length();
            byte[] msgbyte = IPADDRESS.getBytes();

            while (getRunning()) {
                DatagramSocket ds = new DatagramSocket();
                DatagramPacket dp = new DatagramPacket(msgbyte, length, inetAddress, inetPort);

                ds.send(dp);
                ds.close();

                Thread.sleep(1000);
            }
            
            interrupt();
        } catch (Exception e) {

        }
    }

    private boolean running = true;

    public void setRunning(boolean TF) {
        this.running = TF;
    }

    public boolean getRunning() {
        return this.running;
    }

}

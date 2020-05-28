/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author trito
 */
public class IPreceiver extends Thread {

    private volatile String HOSTIPADDRESS = "127.0.0.1";
    private volatile Integer PORT = 1987;

    private DatagramPacket datagramPacket;
    private int port = 8200;
    private byte[] message = new byte[1000];

    private NetworkObserver networkObserver;

    public IPreceiver() {
        if (networkObserver == null) {
            networkObserver = NetworkObserver.getInstance();
        }
    }

    @Override
    public void run() {
        startReceiving();
    }

    private void startReceiving() {
        datagramPacket = new DatagramPacket(message, message.length);
        try {
            // Receive DatagramSocket
            DatagramSocket ds = new DatagramSocket(port);
            ds.receive(datagramPacket);
            ds.close();

            // Set Server IpAddress
            HOSTIPADDRESS = datagramPacket.getAddress().getHostAddress();
            
        } catch (SocketException se) {
            //Log.e("appLog.", "SocketException from new Datagram Constructor " + se);
        } catch (IOException ioe) {
            //Log.e("appLog.", "IOException from receiving Datagram " + ie);
        }
    }
    
    public String getIPADDRESS(){
        return HOSTIPADDRESS;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import java.util.Observable;

/**
 *
 * @author trito
 */
public class NetworkObserver extends Observable {

    private String IPADDRESS;

    public void setIPADDRESS(String ipaddress) {
        this.IPADDRESS = ipaddress;
    }

    public String getIPADDRESS() {
        return this.IPADDRESS;
    }

    private int PORT;

    public void setPORT(int port) {
        this.PORT = port;
    }

    public int getPORT() {
        return this.PORT;
    }

    private boolean client_running = false;

    public void setClientRunning(boolean TF) {
        this.client_running = TF;
    }

    public boolean getClientRunning() {
        return this.client_running;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////             SINGLE TON              //////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static volatile NetworkObserver oSoleInstance;

    //private constructor.
    private NetworkObserver() {

        //Prevent form the reflection api.
        if (oSoleInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static NetworkObserver getInstance() {
        if (oSoleInstance == null) { //if there is no instance available... create new one
            synchronized (NetworkObserver.class) {
                if (oSoleInstance == null) {
                    oSoleInstance = new NetworkObserver();
                }
            }
        }

        return oSoleInstance;
    }

    //Make singleton from serialize and deserialize operation.
    protected NetworkObserver readResolve() {
        return getInstance();
    }

}

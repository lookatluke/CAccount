/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.NetWork;

import com.e.caccount.Utils.NetworkConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author trito
 */
public class IpAddress {

    public IpAddress() {

    }

    public String getIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress() && !addr.getHostAddress().contains(":")) {
                        String HOSTIPADDRESS = addr.getHostAddress();
                        return HOSTIPADDRESS;
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(NetworkConnection.class
                    .getName()).log(Level.SEVERE, "There is an error to find IPADDRESS!", ex);
        }
        return null;
    }

    public String getExternalIpAddreass() {
        BufferedReader in = null;
        String ip = null;
        
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            try {
                in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
                ip = in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(NetworkConnection.class
                        .getName()).log(Level.SEVERE, "Cannot close the BufferedReader", ex);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NetworkConnection.class
                                .getName()).log(Level.SEVERE, "Cannot close the BufferedReader", ex);
                    }
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(NetworkConnection.class
                    .getName()).log(Level.SEVERE, "Cannot get the IP", ex);
        }
        
        return ip;
    }

}

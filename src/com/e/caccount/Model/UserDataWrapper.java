/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luke
 */
@XmlRootElement(name = "USERDATA")
public class UserDataWrapper {

    private List<UserData> userData;

    @XmlElement(name = "USER")
    public List<UserData> getUserData() {
        return userData;
    }

    public void setUserData(List<UserData> userData) {
        this.userData = userData;
    }

    private List<Coins> coins;

    @XmlElement(name = "COINS")
    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins>  test) {
        this.coins = test;
    }
}

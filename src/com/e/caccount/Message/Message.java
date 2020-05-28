/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1112122200L;

    private String name;
    private String type;
    private Integer amount;

    public Message() {

    }

    public Message(String name, String type, int amount) {
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    //name
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    //type
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    //suffering
    public void setAmount(Integer amount) {

        this.amount = amount;
    }

    public Integer getAmount() {

        return this.amount;
    }

}

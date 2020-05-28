/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Luke
 */
public class UserData {

    public UserData() {
        this(null, null, 0);
    }

    public UserData(String name, String type, int amount) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleIntegerProperty(amount);
    }

    private StringProperty name;

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty NameProperty() {
        return name;
    }

    private StringProperty type;

    public void setType(String type) {
        this.type.set(type);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty TypeProperty() {
        return type;
    }

    private IntegerProperty amount;

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty AmountProperty() {
        return amount;
    }

}

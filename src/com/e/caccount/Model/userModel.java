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
 * @author trito
 */
public class userModel {

    public userModel() {
        this(null, null, 0, null);
    }

    public userModel(String name, String type, int amount, String date) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.amount = new SimpleIntegerProperty(amount);
        this.dateTime = new SimpleStringProperty(date);
    }

    private StringProperty name;
    private StringProperty type;
    private IntegerProperty amount;
    private StringProperty dateTime;

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty NameProperty() {
        return name;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty TypeProperty() {
        return type;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty AmountProperty() {
        return amount;
    }

    public void setDateTime(String n) {
        this.dateTime.set(n);
    }

    public StringProperty getDateTime() {
        return dateTime;
    }

    public StringProperty DateTimeProperty() {
        return dateTime;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Luke
 */
public class Coins {

    public Coins() {
        this(0, 0, 0, 0, 0, 0);
    }

    public Coins(int a, int b, int c, int d, int e, int f) {
        this.Baekwon = new SimpleIntegerProperty(a);
        this.OhBaekwon = new SimpleIntegerProperty(b);
        this.Choenwon = new SimpleIntegerProperty(c);
        this.OhChoenwon = new SimpleIntegerProperty(d);
        this.Manwon = new SimpleIntegerProperty(e);
        this.OhManwon = new SimpleIntegerProperty(f);
    }

    private IntegerProperty Baekwon;
    private IntegerProperty OhBaekwon;
    private IntegerProperty Choenwon;
    private IntegerProperty OhChoenwon;
    private IntegerProperty Manwon;
    private IntegerProperty OhManwon;

    public int getBaekwon() {
        return Baekwon.get();
    }

    public void setBaekwon(int amount) {
        this.Baekwon.set(amount);
    }

    public IntegerProperty BaekwonProperty() {
        return Baekwon;
    }

    public int getOhBaekwon() {
        return OhBaekwon.get();
    }

    public void setOhBaekwon(int amount) {
        this.OhBaekwon.set(amount);
    }

    public IntegerProperty OhBaekwonProperty() {
        return OhBaekwon;
    }

    public int getChoenwon() {
        return Choenwon.get();
    }

    public void setChoenwon(int amount) {
        this.Choenwon.set(amount);
    }

    public IntegerProperty ChoenwonProperty() {
        return Choenwon;
    }

    public int getOhChoenwon() {
        return OhChoenwon.get();
    }

    public void setOhChoenwon(int amount) {
        this.OhChoenwon.set(amount);
    }

    public IntegerProperty OhChoenwonProperty() {
        return OhChoenwon;
    }

    public int getManwon() {
        return Manwon.get();
    }

    public void setManwon(int amount) {
        this.Manwon.set(amount);
    }

    public IntegerProperty ManwonProperty() {
        return Manwon;
    }

    public int getOhManwon() {
        return OhManwon.get();
    }

    public void setOhManwon(int amount) {
        this.OhManwon.set(amount);
    }

    public IntegerProperty OhManwonProperty() {
        return OhManwon;
    }

}

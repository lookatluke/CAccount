/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Luke
 */
public class Person extends Observable {

    ////////////////////////////////////////////////////////////////////////////
    //////////////////             SINGLE TON             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private static Person INSTANCE = null;

    public static Person getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Person();
        }
        return INSTANCE;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                VARIABLES           //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private Map<String, ObservableList<UserData>> userMap = new HashMap<>();
    private ObservableList<String> types = FXCollections.observableArrayList();
    private int[] CoinsandBills = new int[6];
    private Map<String, String> manualReceived = new HashMap<>();

    private String name;
    private String type;
    private Integer amount;

    public void setUserData(String name, String type, Integer amount) {
        setThreeVariables(name, type, amount);
        if (isThisTypeExist(type)) {
            CheckExistUserData(name, type, amount);
        } else {
            createNewLIst(name, type, amount);
        }
    }

    public void setThreeVariables(String name, String type, Integer amount) {//mnull check
        this.name = name;
        this.type = type;
        this.amount = amount;
    }

    public void createNewLIst(String name, String type, Integer amount) {
        ObservableList<UserData> tempList = FXCollections.observableArrayList();

        if (name.equals("NEWTABLECREATION")) {
            //사랍없이 입력받을때
            if (type.equals("주정헌금")) {
                tempList.add(new UserData("주정헌금", "주정헌금", 0));
            }
        } else {
            tempList.add(new UserData(name, type, amount));
        }

        userMap.put(type, tempList);
        types.add(type);

        setChanged();
        notifyObservers("NEW");
    }

    public void CheckExistUserData(String name, String type, Integer amount) {
        if (isUserNameIsOnTheList(name, type)) {
            userDataUpdate(name, type, amount);
        } else {
            getUserData(type).add(new UserData(name, type, amount));
        }

        setChanged();
        notifyObservers("UPDATE");
    }

    public void userDataUpdate(String name, String type, Integer amount) {
        getUserData(type).forEach(e -> {
            if (e.getName().equals(name)) {
                e.setAmount(amount);
            }
        });
    }

    // 테이블 삭제하기전에 모든 유저에게 삭제 메세지 전달하기
    public void deleteType(String type) {
        setThreeVariables("DELETE", type, 0);

        types.remove(type);
        userMap.remove(type);
        deleteManualReceivedUser(type);

        setChanged();
        notifyObservers("DELETE");
    }

    public void deleteUserData(String name, String type) {
        getUserData(type).removeIf(e -> e.getName().equals(name));
        deleteManualReceivedUser(name, type);

        setChanged();
        notifyObservers("UPDATE");
    }

    public void insertManualReceivedUser(String name, String type) {
        manualReceived.put(type, name);
    }

    public Map<String, String> getManualReceivedUser() {
        return this.manualReceived;
    }

    private void deleteManualReceivedUser(String name, String type) {
        manualReceived.remove(type, name);
    }

    private void deleteManualReceivedUser(String type) {
        manualReceived.remove(type);
    }

    private void clearManualReceivedUser() {
        manualReceived.clear();
    }

    public void RemoveAllDATA() {
        setThreeVariables(null, null, null);

        for (int i = 0; i < 6; i++) {
            CoinsandBills[i] = 0;
        }
        userMap.clear();
        types.clear();
        clearManualReceivedUser();

        setChanged();
        notifyObservers("REMOVEALL");
    }

    public void ReorderAllDATA() {
        setChanged();
        notifyObservers("REORDER");

        Map<String, ObservableList<UserData>> tempMap = new HashMap<>();
        tempMap.putAll(userMap);
        List<String> tempList = new ArrayList<>();
        tempList.addAll(getTypeList());

        setThreeVariables(null, null, null);
        userMap.clear();
        types.clear();

        for (int i = 0; i < tempList.size(); i++) {
            // Create Type Table
            final String type = tempList.get(i);
            setUserData("NEWTABLECREATION", type, 0);
            // Insert User Data
            for (Map.Entry<String, ObservableList<UserData>> entry : tempMap.entrySet()) {
                entry.getValue().stream()
                        .forEach(e -> {
                            if (e.getType().equals(type)) {
                                setUserData(e.getName(), e.getType(), e.getAmount());
                            }
                        });
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                GET/SET             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public Map<String, ObservableList<UserData>> getUserMap() {
        return userMap;
    }

    public ObservableList<String> getTypeList() {
        return types;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public int getBaekwon() {
        return CoinsandBills[0];
    }

    public int getOhBaekwon() {
        return CoinsandBills[1];
    }

    public int getChoenwon() {
        return CoinsandBills[2];
    }

    public int getOhChoenwon() {
        return CoinsandBills[3];
    }

    public int getManwon() {
        return CoinsandBills[4];
    }

    public int getOhManwon() {
        return CoinsandBills[5];
    }

    public void setBaekwon(int count) {
        CoinsandBills[0] = count;
        notifyCoinAndBillsChange();
    }

    public void setOhBaekwon(int count) {
        CoinsandBills[1] = count;
        notifyCoinAndBillsChange();
    }

    public void setChoenwon(int count) {
        CoinsandBills[2] = count;
        notifyCoinAndBillsChange();
    }

    public void setOhChoenwon(int count) {
        CoinsandBills[3] = count;
        notifyCoinAndBillsChange();
    }

    public void setManwon(int count) {
        CoinsandBills[4] = count;
        notifyCoinAndBillsChange();
    }

    public void setOhManwon(int count) {
        CoinsandBills[5] = count;
        notifyCoinAndBillsChange();
    }

    public void notifyCoinAndBillsChange() {
        setChanged();
        notifyObservers("COINANDBILLS");
    }

    public List<Coins> getCoinsList() {
        List<Coins> tempList = new ArrayList<>();
        Coins coins = new Coins(CoinsandBills[0], CoinsandBills[1], CoinsandBills[2], CoinsandBills[3], CoinsandBills[4], CoinsandBills[5]);
        tempList.add(coins);

        return tempList;
    }

    public List<UserData> getCompliedList() {
        List<UserData> tempList = new ArrayList<>();
        for (int i = 0; i < getTypeList().size(); i++) {
            final String type = getTypeList().get(i);
            for (Map.Entry<String, ObservableList<UserData>> entry : userMap.entrySet()) {
                entry.getValue().stream()
                        .forEach(e -> {
                            if (e.getType().equals(type)) {
                                tempList.add(e);
                            }
                        });
            }
        }
        return tempList;
    }

    public ObservableList<UserData> getUserData(String type) {
        for (Map.Entry<String, ObservableList<UserData>> entry : userMap.entrySet()) {
            if (entry.getKey().equals(type)) {
                return entry.getValue();
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////        MATHEMATICS METHODS    /////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public int getSumOfCoinsAndBills() {
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                sum += CoinsandBills[i] * 100;
            } else if (i == 1) {
                sum += CoinsandBills[i] * 500;
            } else if (i == 2) {
                sum += CoinsandBills[i] * 1000;
            } else if (i == 3) {
                sum += CoinsandBills[i] * 5000;
            } else if (i == 4) {
                sum += CoinsandBills[i] * 10000;
            } else if (i == 5) {
                sum += CoinsandBills[i] * 50000;
            }
        }
        return sum;
    }

    public int getNumberOfCoinAndBills() {
        return CoinsandBills[0] + CoinsandBills[1] + CoinsandBills[2] + CoinsandBills[3] + CoinsandBills[4] + CoinsandBills[5];
    }

    public Integer getSelectedTypeSUM(String type) {
        return getUserData(type).stream()
                .map(e -> e.getAmount())
                .reduce(0, Integer::sum);
    }

    private Integer TypeSum;

    public Integer getTypeSum() {
        TypeSum = 0;
        types.forEach((t) -> {
            TypeSum += getUserData(t).stream()
                    .map(e -> e.getAmount())
                    .reduce(0, Integer::sum);
        });
        return TypeSum;
    }

    public void printoutAllThreads() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        for (Thread thread : threadSet) {
            System.out.println(thread.getName());
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////         BOOLEAN RETURN        /////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public boolean isThisTypeExist(String type) {
        return types.stream()
                .anyMatch(e -> e.equals(type));
    }

    public boolean isUserNameIsOnTheList(String name, String type) {
        return getUserData(type).stream()
                .anyMatch(e -> e.getName().equals(name));
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////                METHOS              //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void setNetworkingStatus() {
        setChanged();
        notifyObservers("SERVER");
    }
}

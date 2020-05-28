/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.PrivilegedExceptionAction;

/**
 *
 * @author trito
 */
public class PriviegedExceptionAction_O implements PrivilegedExceptionAction {

    public String filePath;

    public PriviegedExceptionAction_O(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Object run() throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(filePath);
        return fos;
    }

}

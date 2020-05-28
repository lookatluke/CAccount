/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.Utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author LUKE
 */
public class FileUtil {

    public boolean DirectoriesCheck(String path) {
        File tempDir = new File(path);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return true;
    }

    public synchronized File[] getAllFiles(String path, String extension) {
        File[] fileList = new File(path).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(extension);
            }
        });
        return fileList;
    }

    public File getQRcodeFileFormList(String name, File[] files) {
        for (File file : files) {
            if (file.getName().equals(name + ".png")) {
                return file;
            }
        }
        return null;
    }
}

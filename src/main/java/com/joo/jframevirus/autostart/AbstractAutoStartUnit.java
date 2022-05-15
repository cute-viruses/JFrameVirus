package com.joo.jframevirus.autostart;


import java.io.File;

public abstract class AbstractAutoStartUnit implements AutoStartUnit {
    private String autoStartPath,
                autoStartFileName;

    public AbstractAutoStartUnit(String autoStartPath) {
        setAutoStartPath(autoStartPath);
    }

    public void setAutoStartPath(String autoStartPath) {
        this.autoStartPath = autoStartPath;
    }

    public  void setAutoStartFileName(String autoStartFileName) {
        this.autoStartFileName = autoStartFileName;
    }

    public String getAutoStartFileName() {
        return autoStartFileName;
    }

    @Override
    public String getAutoStartFilePath() {
        return autoStartPath + File.separator + autoStartFileName;
    }
}

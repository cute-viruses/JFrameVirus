package com.joo.jframevirus.autostart;

import java.io.File;

public interface AutoStartUnit {
    void addToAutoStart();
    void removeFromAutoStart();
    String getAutoStartFilePath();

    default boolean isInAutoStartPrograms() {
        return new File(getAutoStartFilePath()).exists();
    }
}

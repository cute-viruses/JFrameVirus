package com.joo.jframevirus.keydialog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class KeyDialogWindowListener implements WindowListener, WindowFocusListener {
    private final KeyDialog keyDialog;

    public KeyDialogWindowListener(KeyDialog keyDialog) {
        this.keyDialog = keyDialog;
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) { }

    @Override
    public void windowClosed(WindowEvent e) { }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) {
        keyDialog.setAccepted(false);
        keyDialog.onAction();
    }

    @Override
    public void windowGainedFocus(WindowEvent e) { }

    @Override
    public void windowLostFocus(WindowEvent e) {
        keyDialog.setAccepted(false);
        keyDialog.onAction();
    }
}

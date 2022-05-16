package com.joo.jframevirus.keydialog;

import com.joo.jframevirus.MainController;

import javax.swing.*;
import java.awt.*;

public class KeyDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField keyField;
    private JLabel keyLabel;
    private boolean accept;
    private final MainController mainController;

    public KeyDialog(MainController mainController) {
        setTitle("Enter key");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setup();
        setContentPane(contentPane);
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true); // always on top
        setResizable(false);
        setModal(true);
        addWindowListener(new KeyDialogWindowListener(this));
        this.mainController = mainController;
    }

    private void setup() {
        init();
        setupFont();
        setupColor();
        setupLayout();
        setupListeners();
    }

    private void setupListeners() {
        buttonOK.addActionListener(e -> {
            if (keyField.getText().equals(MainController.KEY)) {
                accept = true;
            }
            this.onAction();
        });
    }

    public void onAction() {
        setVisible(false);
        mainController.onKeyDialogAction();
    }

    private void setupLayout() {
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(keyLabel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPane.add(keyField, gbc);
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.add(buttonOK, gbc);
    }

    private void setupColor() {
        keyField.setForeground(Color.red);
        keyField.setBackground(Color.black);
        keyField.setCaretColor(Color.white);
        keyField.setBorder(BorderFactory.createLineBorder(Color.red, 6));

        keyLabel.setForeground(Color.red);

        contentPane.setBackground(Color.black);

        buttonOK.setBackground(Color.black);
        buttonOK.setForeground(Color.red);
        buttonOK.setBorder(BorderFactory.createLineBorder(Color.red));

    }

    private void setupFont() {
        Font font = new Font("Arial", Font.PLAIN, 65);
        keyLabel.setFont(font);
        keyField.setFont(font);
        buttonOK.setFont(font);
    }

    private void init() {
        contentPane = new JPanel();
        buttonOK = new JButton("OK");
        keyField = new JTextField();
        keyLabel = new JLabel("To stop virus write Key");
    }

    public void showDialog() {
        setVisible(true); // show dialog
    }

    public void setAccepted(boolean accepted) {
        this.accept = accepted;
    }

    public boolean isAccepted() {
        return accept;
    }
}

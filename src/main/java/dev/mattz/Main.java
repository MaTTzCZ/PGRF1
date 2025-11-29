package dev.mattz;

import dev.mattz.data.gui.views.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}

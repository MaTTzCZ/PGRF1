package dev.mattz;

import dev.mattz.data.D2.gui.views.MainView;

import javax.swing.*;

public class Main2D {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView().setVisible(true));
    }
}

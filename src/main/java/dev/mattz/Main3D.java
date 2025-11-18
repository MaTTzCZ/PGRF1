package dev.mattz;

import dev.mattz.data.gui.views.MainView2D;

import javax.swing.*;

public class Main3D {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView2D().setVisible(true));
    }
}

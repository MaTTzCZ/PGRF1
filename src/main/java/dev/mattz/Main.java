package dev.mattz;

import dev.mattz.data.Canvas;
import dev.mattz.data.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

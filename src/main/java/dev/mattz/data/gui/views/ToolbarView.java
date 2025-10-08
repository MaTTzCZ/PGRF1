package dev.mattz.data.gui.views;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;


public class ToolbarView extends JPanel {
    public ToolbarView() {
    }

    private void createButton(String imageURL) {
        JButton button = new JButton();
        button.setBorder(null);
        button.setFocusable(false);
        button.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(imageURL))));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMouseListener();
            }
        });
    }

    private void addMouseListener(MouseListener mouseListener) {
    }
}

package dev.mattz.data.gui.views;

import dev.mattz.data.gui.controllers.ToolbarController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class ToolbarView extends JPanel {
    private JButton currentModeButton;

    private final ToolbarController controller;

    public ToolbarView(ToolbarController controller) {
        this.controller = controller;

        addToolButtons();
    }

    private void addToolButtons() {
        for (int i = 0; i < controller.getSize() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                JButton jButtonTool = new JButton();
                jButtonTool.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(controller.getToolImageURL(i * 2 + j)))));
                jButtonTool.setBounds(i * 20 + 50, j * 20, 20, 20);
                jButtonTool.setFocusable(false);
                jButtonTool.setBorder(null);
                int finalI = i;
                int finalJ = j;
                if (i == 0 && j == 0) {
                    currentModeButton = jButtonTool;
                    jButtonTool.setBorder(new LineBorder(Color.BLUE, 3));

                }
                jButtonTool.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            if (jButtonTool != currentModeButton) {
                                controller.setCurrentMode(finalI * 2 + finalJ);
                                System.out.println(controller.getCurrentMode());
                                jButtonTool.setBorder(new LineBorder(Color.BLUE, 3));
                                currentModeButton.setBorder(null);
                                currentModeButton = jButtonTool;
                            }
                        }
                    }
                });
                this.add(jButtonTool);
            }
        }
    }
}

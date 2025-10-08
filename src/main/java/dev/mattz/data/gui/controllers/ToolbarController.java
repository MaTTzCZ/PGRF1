package dev.mattz.data.gui.controllers;

import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.ToolbarView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class ToolbarController {
    private final ToolbarModel model;
    private final ToolbarView view;

    JButton currentButton;

    public ToolbarController(ToolbarView view) {
        this.model = new ToolbarModel();
        this.view = view;

        loadImageIcons();

    }

    private void createButtons() {
        for (int index = 0; index < availableModes.length; index++) {
            view.add(createButton(index));
        }
    }

    private JButton createButton(int index) {
        JButton button = new JButton();
        button.setBounds(index * 40, 0, 40, 40);
        button.setIcon(toolbarImageIcons.get(index));
        button.setFocusable(false);
        button.setBorder(null);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button.setBorder(new LineBorder(Color.BLUE, 3));
                model.setCurrentMode(index);
            }
        });
        if (currentButton != null) {
            button.setBorder(new LineBorder(Color.BLUE, 3));
            this.currentButton = button;
        }
        return button;
    }

    private void loadImageIcons() {
        toolbarImageIcons = new ArrayList<>();
        for (String URL : model.getToolbarIcons()) {
            assert toolbarImageIcons != null;
            toolbarImageIcons.add(new ImageIcon(Objects.requireNonNull(getClass().getResource(URL))));
        }
    }
}

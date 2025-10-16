package dev.mattz.data.gui.controllers;

import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.views.ColorPaletteView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPaletteController {
    private final ColorPaletteModel model;
    private final ColorPaletteView view;

    public ColorPaletteController(ColorPaletteView view) {
        this.model = ColorPaletteModel.getInstance();
        this.view = view;

        createButtons();
    }

    private void createButtons() {
        for (int i = 0; i < model.getColors().size() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                view.createButton(i, j, model.getColors().get(index));
                view.addListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) setPrimaryColor(model.getColors().get(index));
                        else if (SwingUtilities.isRightMouseButton(e)) setSecondaryColor(model.getColors().get(index));
                    }
                });
            }


        }
    }
    private void setPrimaryColor(Color color){
        model.setPrimaryColor(color);
    }
    private void setSecondaryColor(Color color){
        model.setSecondaryColor(color);
    }
}

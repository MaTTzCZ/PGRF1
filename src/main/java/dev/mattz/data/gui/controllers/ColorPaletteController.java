package dev.mattz.data.gui.controllers;

import dev.mattz.data.gui.models.ColorPaletteModel;
import dev.mattz.data.gui.views.ColorPaletteView;

import java.awt.*;
import java.util.ArrayList;

public class ColorPaletteController {
    private final ColorPaletteModel model;
    private final ColorPaletteView view;

    public ColorPaletteController() {
        this.model = new ColorPaletteModel();
        this.view = new ColorPaletteView();

        view.createButtons(model.getColors());
    }


}

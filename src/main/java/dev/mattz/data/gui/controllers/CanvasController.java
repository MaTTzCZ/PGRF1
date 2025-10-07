package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;

import java.awt.*;

public class CanvasController {
    private final ColorPaletteController colorPaletteController;
    private final ToolbarController toolbarController;

    public CanvasController(ColorPaletteController colorPaletteController, ToolbarController toolbarController) {
        this.colorPaletteController = colorPaletteController;
        this.toolbarController = toolbarController;
    }
    public Color getPrimaryColor(){
        return colorPaletteController.getPrimaryColor();
    }

    public Color getSecondaryColor(){
        return colorPaletteController.getSecondaryColor();
    }

    public Mode getCurrentMode(){
        return toolbarController.getCurrentMode();
    }
}

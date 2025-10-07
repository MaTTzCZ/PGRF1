package dev.mattz.data.gui.controllers;

public class MainController {
    private final CanvasController canvasController;
    private final ColorPaletteController colorPaletteController;
    private final ToolbarController toolbarController;

    public MainController() {
        this.colorPaletteController = new ColorPaletteController();
        this.toolbarController = new ToolbarController();
        this.canvasController = new CanvasController(colorPaletteController, toolbarController);

    }

    public CanvasController getCanvasController() {
        return canvasController;
    }

    public ColorPaletteController getColorPaletteController() {
        return colorPaletteController;
    }

    public ToolbarController getToolbarController() {
        return toolbarController;
    }
}

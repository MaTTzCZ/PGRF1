package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.CanvasView;
import dev.mattz.data.gui.views.ToolbarView;

public class ToolbarController {
    private final ToolbarModel model;

    public ToolbarController(ToolbarView toolbarView, CanvasView canvasView) {
        this.model = ToolbarModel.getInstance();

        toolbarView.setJButtonSelectModeListener(_ -> {
            model.setCurrentMode(Mode.MOVE);
            canvasView.setCurrentMode(Mode.MOVE);
            toolbarView.setLocked(model.isLocked());
            canvasView.repaint();
        });
        toolbarView.setJButtonLineModeListener(_ -> {
            model.setCurrentMode(Mode.LINE);
            canvasView.setCurrentMode(Mode.LINE);
            toolbarView.setLocked(model.isLocked());
        });
        toolbarView.setJButtonPolygonModeListener(_ -> {
            model.setCurrentMode(Mode.POLYGON);
            canvasView.setCurrentMode(Mode.POLYGON);
            toolbarView.setLocked(model.isLocked());
        });
        toolbarView.setJButtonPencilModeListener(_ -> {
            model.setCurrentMode(Mode.PENCIL);
            canvasView.setCurrentMode(Mode.PENCIL);
            toolbarView.setLocked(model.isLocked());
        });
        toolbarView.setJButtonFillModeListener(_ -> {
            model.setCurrentMode(Mode.FILL);
            canvasView.setCurrentMode(Mode.FILL);
            toolbarView.setLocked(model.isLocked());
        });
        toolbarView.setJButtonRectangleModeListener(_ -> {
            model.setCurrentMode(Mode.RECTANGLE);
            canvasView.setCurrentMode(Mode.RECTANGLE);
            toolbarView.setLocked(model.isLocked());
        });
    }
}

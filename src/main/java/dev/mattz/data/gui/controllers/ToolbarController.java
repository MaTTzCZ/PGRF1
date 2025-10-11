package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.ToolbarView;

public class ToolbarController {
    private final ToolbarModel model;
    private final ToolbarView view;

    public ToolbarController(ToolbarView view) {
        this.model = ToolbarModel.getInstance();
        this.view = view;

        view.setJButtonSelectModeListener(_ -> model.setCurrentMode(Mode.SELECT));
        view.setJButtonLineModeListener(_ -> model.setCurrentMode(Mode.LINE));
        view.setJButtonPolygonModeListener(_ -> model.setCurrentMode(Mode.POLYGON));
        view.setJButtonPencilModeListener(_ -> model.setCurrentMode(Mode.PENCIL));
    }
}

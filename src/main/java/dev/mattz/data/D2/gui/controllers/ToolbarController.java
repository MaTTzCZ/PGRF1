package dev.mattz.data.D2.gui.controllers;

import dev.mattz.data.D2.Mode;
import dev.mattz.data.D2.gui.views.CanvasView;
import dev.mattz.data.D2.gui.views.ToolbarView;

public class ToolbarController {

    public ToolbarController(ToolbarView toolbarView, CanvasView canvasView) {
        Mode[] modes = Mode.values();

        for (Mode mode : modes){
            toolbarView.createButton(mode.getImageURL(), _ -> {
                if (!toolbarView.isLocked()){
                    canvasView.setCurrentMode(mode);
                    canvasView.drawAll();
                }
            });
        }
    }
}

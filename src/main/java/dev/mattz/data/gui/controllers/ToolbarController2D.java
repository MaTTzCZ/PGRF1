package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode2D;
import dev.mattz.data.gui.views.CanvasView2D;
import dev.mattz.data.gui.views.ToolbarView;

public class ToolbarController2D {

    public ToolbarController2D(ToolbarView toolbarView, CanvasView2D canvasView2D) {
        Mode2D[] modes = Mode2D.values();

        for (Mode2D mode : modes){
            toolbarView.createButton(mode.getImageURL(), _ -> {
                if (!toolbarView.isLocked()){
                    canvasView2D.setCurrentMode(mode);
                    canvasView2D.drawAll();
                }
            });
        }
    }
}

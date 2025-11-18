package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode3D;
import dev.mattz.data.gui.views.CanvasView3D;
import dev.mattz.data.gui.views.ToolbarView;

public class ToolbarController3D {

    public ToolbarController3D(ToolbarView toolbarView, CanvasView3D canvasView3D) {
        Mode3D[] modes = Mode3D.values();

        for (Mode3D mode : modes){
            toolbarView.createButton(mode.getImageURL(), _ -> {
                if (!toolbarView.isLocked()){
                    canvasView3D.setCurrentMode(mode);
                    canvasView3D.drawAll();
                }
            });
        }
    }
}

package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;
import dev.mattz.data.gui.models.ToolbarModel;
import dev.mattz.data.gui.views.CanvasView;
import dev.mattz.data.gui.views.ToolbarView;

import java.util.LinkedHashMap;
import java.util.Map;

public class ToolbarController {

    public ToolbarController(ToolbarView toolbarView, CanvasView canvasView) {
        ToolbarModel model = ToolbarModel.getInstance();

        LinkedHashMap<Mode, String> modes = model.getModes();
        for (Map.Entry<Mode, String> entry : modes.entrySet()){
            toolbarView.createButton(entry.getValue(), _ -> {
                if (!toolbarView.isLocked()){
                    canvasView.setCurrentMode(entry.getKey());
                    canvasView.repaint();
                }
            });
        }
    }
}

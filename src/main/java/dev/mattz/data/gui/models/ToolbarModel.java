package dev.mattz.data.gui.models;

import dev.mattz.data.Mode;

public class ToolbarModel {
    private static ToolbarModel model;

    private Mode currentMode;

    private ToolbarModel() {
        this.currentMode = Mode.SELECT;
    }

    public static ToolbarModel getInstance(){
        if (model == null){
            model = new ToolbarModel();
        }
        return model;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }
}

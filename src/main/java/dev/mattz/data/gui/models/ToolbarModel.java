package dev.mattz.data.gui.models;

import dev.mattz.data.Mode;

public class ToolbarModel {
    private static ToolbarModel model;
    private Mode currentMode;
    private boolean isLocked;

    private ToolbarModel() {
        this.currentMode = Mode.POINT_MOVE;
        this.isLocked = false;
    }

    public static ToolbarModel getInstance() {
        if (model == null) {
            model = new ToolbarModel();
        }
        return model;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode mode) {
        if (!isLocked)
            this.currentMode = mode;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}

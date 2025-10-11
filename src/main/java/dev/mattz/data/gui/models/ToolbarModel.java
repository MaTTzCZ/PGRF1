package dev.mattz.data.gui.models;

import dev.mattz.data.Mode;

import java.util.Arrays;
import java.util.List;

public class ToolbarModel {
    private static ToolbarModel model;

    private final List<String> URLs;
    private final List<Mode> modes;

    private Mode currentMode;

    private ToolbarModel() {
        this.URLs = List.of(
                "/images/select_tool_icon.png",
                "/images/line_tool_icon.png",
                "/images/polygon_tool_icon.png",
                "/images/pencil_tool_icon.png",
                "/images/brush_tool_icon.png"
                );
        this.modes = Arrays.asList(Mode.values());
        this.currentMode = Mode.SELECT;
    }

    public static ToolbarModel getInstance(){
        if (model == null){
            model = new ToolbarModel();
        }
        return model;
    }

    public List<String> getURLs() {
        return URLs;
    }

    public List<Mode> getModes() {
        return modes;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode mode) {
        this.currentMode = mode;
    }
}

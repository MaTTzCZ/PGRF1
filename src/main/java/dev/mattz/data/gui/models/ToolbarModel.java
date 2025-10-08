package dev.mattz.data.gui.models;

import dev.mattz.data.Mode;

import java.util.List;

public class ToolbarModel {
    private final List<String> iconsURLs;
    private final Mode[] modes;

    private Mode currentMode;

    public ToolbarModel() {
        this.iconsURLs = List.of(
                "/images/select_tool_icon.png",
                "/images/lines_tool_icon.png",
                "/images/pencil_tool_icon.png",
                "/images/brush_tool_icon.png"
                );
        this.modes = Mode.values();
        this.currentMode = Mode.SELECT;
    }
    public void setCurrentMode(int index){
        this.currentMode = modes[index];
    }

    public List<String> getToolbarIcons() {
        return iconsURLs;
    }

}

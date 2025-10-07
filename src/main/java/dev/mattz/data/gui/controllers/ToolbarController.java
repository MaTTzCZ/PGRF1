package dev.mattz.data.gui.controllers;

import dev.mattz.data.Mode;

import java.util.ArrayList;

public class ToolbarController {
    private final ArrayList<String> toolsImagesURLs;

    private Mode[] availableModes;
    private Mode currentMode;

    public ToolbarController() {
        this.toolsImagesURLs = new ArrayList<>();

        getAllModes();
        addToolsImagesURLs();
    }

    public Mode getCurrentMode() {
        return this.currentMode;
    }

    public void setCurrentMode(int index) {
        this.currentMode = availableModes[index];
    }

    public int getSize() {
        return toolsImagesURLs.size();
    }

    public String getToolImageURL(int index){
        return toolsImagesURLs.get(index);
    }

    public Mode getMode(int index) {
        return availableModes[index];
    }

    private void getAllModes(){
        availableModes = Mode.values();
    }

    private void addToolsImagesURLs() {
        toolsImagesURLs.add("/images/select_tool_icon.png");
        toolsImagesURLs.add("/images/lines_tool_icon.png");
        toolsImagesURLs.add("/images/pencil_tool_icon.png");
        toolsImagesURLs.add("/images/brush_tool_icon.png");
    }
}

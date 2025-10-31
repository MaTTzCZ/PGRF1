package dev.mattz.data.gui.models;

import dev.mattz.data.Mode;

import java.util.LinkedHashMap;

public class ToolbarModel {
    private static ToolbarModel model;

    private final LinkedHashMap<Mode, String> modes;

    private ToolbarModel() {
        modes = new LinkedHashMap<>();

        modes.put(Mode.POINT_MOVE, "/images/point_move_tool_icon.png");
        modes.put(Mode.LINE, "/images/line_tool_icon.png");
        modes.put(Mode.POLYGON, "/images/polygon_tool_icon.png");
        modes.put(Mode.PENCIL_DRAW, "/images/pencil_draw_tool_icon.png");
        modes.put(Mode.SEED_FILL, "/images/seed_fill_tool_icon.png");
        modes.put(Mode.RECTANGLE, "/images/rectangle_tool_icon.png");
    }

    public static ToolbarModel getInstance() {
        if (model == null) {
            model = new ToolbarModel();
        }
        return model;
    }

    public LinkedHashMap<Mode, String> getModes() {
        return modes;
    }
}

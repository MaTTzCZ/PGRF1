package dev.mattz.data;

public enum Mode {
    POINT_MOVE("/images/point_move_tool_icon.png"),
    LINE("/images/line_tool_icon.png"),
    POLYGON("/images/polygon_tool_icon.png"),
    PENCIL_DRAW("/images/pencil_draw_tool_icon.png"),
    SEED_FILL("/images/seed_fill_tool_icon.png"),
    RECTANGLE("/images/rectangle_tool_icon.png"),
    POLYGON_INTERSECTION("/images/polygon_intersection_tool_icon.png");

    private final String imageURL;
    Mode(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}

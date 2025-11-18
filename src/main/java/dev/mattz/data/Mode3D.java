package dev.mattz.data;

public enum Mode3D {
    MOVEMENT("/images/movement_tool_icon.png"),
    POINT_MOVE("/images/point_move_tool_icon.png");

    private final String imageURL;

    Mode3D(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}

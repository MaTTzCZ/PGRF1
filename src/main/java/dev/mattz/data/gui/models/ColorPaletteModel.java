package dev.mattz.data.gui.models;

import java.awt.*;
import java.util.List;

public class ColorPaletteModel {
    private final List<Color> colors;

    private Color primaryColor;
    private Color secondaryColor;

    public ColorPaletteModel() {
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;

        colors = List.of(
                Color.BLACK,
                Color.WHITE,
                Color.DARK_GRAY,
                Color.LIGHT_GRAY,
                new Color(131, 0, 16),
                Color.RED,
                new Color(127, 130, 38),
                Color.YELLOW,
                new Color(5, 127, 35),
                Color.GREEN,
                new Color(0, 129, 123),
                Color.CYAN,
                new Color(0, 25, 120),
                new Color(0, 57, 236),
                new Color(123, 18, 123),
                new Color(248, 42, 238),
                new Color(127, 130, 72),
                new Color(251, 255, 135),
                new Color(5, 65, 63),
                new Color(0, 253, 131),
                new Color(9, 128, 249),
                new Color(129, 254, 255),
                new Color(2, 65, 128),
                new Color(126, 129, 251),
                new Color(127, 3, 247),
                new Color(250, 1, 133),
                new Color(131, 64, 0),
                new Color(251, 129, 68)
        );
    }

    public List<Color> getColors() {
        return colors;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
}

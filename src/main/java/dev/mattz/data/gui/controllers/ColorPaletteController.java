package dev.mattz.data.gui.controllers;

import java.awt.*;
import java.util.ArrayList;

public class ColorPaletteController {
    private final ArrayList<Color> availableColors;
    private Color primaryColor;
    private Color secondaryColor;

    public ColorPaletteController() {
        this.availableColors = new ArrayList<>();
        this.primaryColor = Color.BLACK;
        this.secondaryColor = Color.WHITE;

        addAvailableColors();
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(int index) {
        this.primaryColor = availableColors.get(index);
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(int index) {
        this.secondaryColor = availableColors.get(index);

    }

    public int getSize(){
        return availableColors.size();
    }

    public Color getColor(int index){
        return availableColors.get(index);
    }

    private void addAvailableColors() {
        availableColors.add(Color.BLACK);
        availableColors.add(Color.WHITE);
        availableColors.add(Color.DARK_GRAY);
        availableColors.add(Color.LIGHT_GRAY);
        availableColors.add(new Color(131, 0, 16));
        availableColors.add(Color.RED);
        availableColors.add(new Color(127, 130, 38));
        availableColors.add(Color.YELLOW);
        availableColors.add(new Color(5, 127, 35));
        availableColors.add(Color.GREEN);
        availableColors.add(new Color(0, 129, 123));
        availableColors.add(Color.CYAN);
        availableColors.add(new Color(0, 25, 120));
        availableColors.add(new Color(0, 57, 236));
        availableColors.add(new Color(123, 18, 123));
        availableColors.add(new Color(248, 42, 238));
        availableColors.add(new Color(127, 130, 72));
        availableColors.add(new Color(251, 255, 135));
        availableColors.add(new Color(5, 65, 63));
        availableColors.add(new Color(0, 253, 131));
        availableColors.add(new Color(9, 128, 249));
        availableColors.add(new Color(129, 254, 255));
        availableColors.add(new Color(2, 65, 128));
        availableColors.add(new Color(126, 129, 251));
        availableColors.add(new Color(127, 3, 247));
        availableColors.add(new Color(250, 1, 133));
        availableColors.add(new Color(131, 64, 0));
        availableColors.add(new Color(251, 129, 68));
    }
}

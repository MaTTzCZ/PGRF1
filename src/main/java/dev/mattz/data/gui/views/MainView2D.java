package dev.mattz.data.gui.views;

import dev.mattz.data.gui.controllers.CanvasController2D;
import dev.mattz.data.gui.controllers.ColorPaletteController;
import dev.mattz.data.gui.controllers.ToolbarController2D;

import javax.swing.*;
import java.awt.*;

public class MainView2D extends JFrame {
    JPanel jPanelMainContent = new JPanel(new BorderLayout());
    JPanel jPanelBottom = new JPanel();
    JMenuBar jMenuBarMain = new JMenuBar();

    JMenu jMenuToolbar = new JMenu("Toolbar");

    JMenu jMenuToolbarSubmenuLineMode = new JMenu("Line Mode");
    JCheckBoxMenuItem jCheckBoxMenuItemLineGradient = new JCheckBoxMenuItem("Line Gradient");

    JMenu jMenuToolbarSubmenuPolygonMode = new JMenu("Polygon Mode");
    JCheckBoxMenuItem jCheckBoxMenuItemFillPolygon = new JCheckBoxMenuItem("Fill Polygon");

    JMenu jMenuToolbarSubmenuSeedFillMode = new JMenu("Seed Fill Mode");
    ButtonGroup buttonGroupSeedFillModes = new ButtonGroup();
    JRadioButton jRadioButtonSeedFillBackground = new JRadioButton("Background mode");
    JRadioButton jRadioButtonSeedFillBoundary = new JRadioButton("Boundary mode");

    JMenu jMenuCanvas = new JMenu("Canvas");

    JMenuItem jMenuCanvasClear = new JMenuItem("Clear");

    ColorPaletteView colorPaletteView = new ColorPaletteView();
    ToolbarView toolbarView = new ToolbarView();
    CanvasView2D canvasView2D = new CanvasView2D(1920, 900);

    JScrollPane jScrollPane = new JScrollPane(canvasView2D);

    public MainView2D() {
        new CanvasController2D(this, canvasView2D, toolbarView);
        new ColorPaletteController(colorPaletteView);
        new ToolbarController2D(toolbarView, canvasView2D);

        this.setTitle("Malování");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBarMain);

        jPanelMainContent.add(jScrollPane, BorderLayout.CENTER);

        this.add(jPanelMainContent, BorderLayout.CENTER);
        this.add(jPanelBottom, BorderLayout.SOUTH);

        jPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));
        jPanelBottom.add(colorPaletteView);
        jPanelBottom.add(toolbarView);


        this.setTitle("Malování");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBarMain);
        jPanelMainContent.add(jScrollPane);
        this.add(jPanelMainContent, BorderLayout.CENTER);
        this.add(jPanelBottom, BorderLayout.SOUTH);

        jPanelMainContent.add(jScrollPane, BorderLayout.CENTER);

        jPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanelBottom.add(colorPaletteView);
        jPanelBottom.add(toolbarView);

        jMenuBarMain.add(jMenuCanvas);
        jMenuBarMain.add(jMenuToolbar);

        jMenuCanvas.add(jMenuCanvasClear);

        jMenuToolbar.add(jMenuToolbarSubmenuLineMode);
        jMenuToolbar.add(jMenuToolbarSubmenuPolygonMode);
        jMenuToolbar.add(jMenuToolbarSubmenuSeedFillMode);

        jMenuToolbarSubmenuLineMode.add(jCheckBoxMenuItemLineGradient);

        jMenuToolbarSubmenuPolygonMode.add(jCheckBoxMenuItemFillPolygon);

        buttonGroupSeedFillModes.add(jRadioButtonSeedFillBackground);
        buttonGroupSeedFillModes.add(jRadioButtonSeedFillBoundary);

        jMenuToolbarSubmenuSeedFillMode.add(jRadioButtonSeedFillBackground);
        jMenuToolbarSubmenuSeedFillMode.add(jRadioButtonSeedFillBoundary);

        jRadioButtonSeedFillBackground.setSelected(true);

        jMenuCanvasClear.addActionListener(_ -> {
            canvasView2D.clearDrawables();
            canvasView2D.clearBufferedImage();
        });
    }

    public boolean isGradientLineSelected(){
        return jCheckBoxMenuItemLineGradient.isSelected();
    }

    public boolean isPolygonFillSelected(){
        return jCheckBoxMenuItemFillPolygon.isSelected();
    }

    public boolean isSeedFillBackgroundSelected(){
        return jRadioButtonSeedFillBackground.isSelected();
    }
}

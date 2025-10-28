package dev.mattz.data.gui.views;

import dev.mattz.data.gui.controllers.CanvasController;
import dev.mattz.data.gui.controllers.ColorPaletteController;
import dev.mattz.data.gui.controllers.ToolbarController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainView extends JFrame {
    JPanel jPanelMainContent = new JPanel(new BorderLayout());
    JPanel jPanelBottom = new JPanel();

    JMenuBar jMenuBarMain = new JMenuBar();

    JMenu jMenuCanvas = new JMenu("Canvas");
    JMenu jMenuToolbar = new JMenu("Toolbar");

    JMenu jMenuToolbarSubmenuLine = new JMenu("Line");
    JMenu jMenuToolbarSubmenuPolygon = new JMenu("Polygon");

    JMenuItem jMenuCanvasItem1 = new JMenuItem("Clear");

    JCheckBoxMenuItem jCheckBoxMenuItemLineGradient = new JCheckBoxMenuItem("Line Gradient");
    JCheckBoxMenuItem jCheckBoxMenuItemFillPolygon = new JCheckBoxMenuItem("Fill Polygon");


    ColorPaletteView colorPaletteView = new ColorPaletteView();
    ToolbarView toolbarView = new ToolbarView();
    CanvasView canvasView = new CanvasView(800, 700);

    public MainView() {
        new CanvasController(canvasView, this);
        new ColorPaletteController(colorPaletteView);
        new ToolbarController(toolbarView, canvasView);

        this.setTitle("Malování");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBarMain);
        this.add(jPanelMainContent, BorderLayout.CENTER);
        this.add(jPanelBottom, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvasView.setPreferredSize(new Dimension(getWidth(), getHeight()));
                repaint();
            }
        });

        jPanelMainContent.add(canvasView);

        jPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanelBottom.add(colorPaletteView);
        jPanelBottom.add(toolbarView);
        jPanelMainContent.setBackground(Color.RED);

        jMenuBarMain.add(jMenuCanvas);
        jMenuBarMain.add(jMenuToolbar);

        jMenuCanvas.add(jMenuCanvasItem1);

        jMenuToolbar.add(jMenuToolbarSubmenuLine);
        jMenuToolbar.add(jMenuToolbarSubmenuPolygon);

        jMenuToolbarSubmenuLine.add(jCheckBoxMenuItemLineGradient);

        jMenuToolbarSubmenuPolygon.add(jCheckBoxMenuItemFillPolygon);

        jMenuCanvasItem1.addActionListener(_ -> {
            canvasView.clearDrawables();
            canvasView.clearBufferedImage();
        });
    }

    public boolean isGradientLineSelected(){
        return jCheckBoxMenuItemLineGradient.isSelected();
    }

    public boolean isPolygonFillSelected(){
        return jCheckBoxMenuItemFillPolygon.isSelected();
    }

}

package dev.mattz.data.gui.views;

import dev.mattz.data.graphics.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainView extends JFrame {
    JPanel jPanelMainContent = new JPanel(new BorderLayout());
    JPanel jPanelBottom = new JPanel();

    JMenuBar jMenuBarMain = new JMenuBar();
    JMenu jMenuMainFiles = new JMenu("Files");
    JMenuItem jMenuItem1 = new JMenuItem("test1");
    JMenuItem jMenuItem2 = new JMenuItem("test2");
    JMenuItem jMenuItem3 = new JMenuItem("test3");

    MainController mainController = new MainController();

    ColorPaletteView colorPalette = new ColorPaletteView(mainController.getColorPaletteController());
    ToolbarView toolbar = new ToolbarView(mainController.getToolbarController());
    Canvas canvas = new Canvas(800, 700, mainController.getColorPaletteController(), mainController.getToolbarController());



    public MainView() {
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
                canvas.setPreferredSize(new Dimension(getWidth(), getHeight()));
                repaint();
            }
        });

        jPanelMainContent.add(canvas);

        jPanelBottom.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanelBottom.add(colorPalette);
        jPanelBottom.add(toolbar);
        jPanelMainContent.setBackground(Color.RED);

        jMenuBarMain.add(jMenuMainFiles);
        jMenuMainFiles.add(jMenuItem1);
        jMenuMainFiles.add(jMenuItem2);
        jMenuMainFiles.add(jMenuItem3);
    }
}

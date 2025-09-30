package dev.mattz.data;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    JPanel jPanelTopMenu = new JPanel();
    JPanel jPanelMainContent = new JPanel(new BorderLayout());
    JPanel jPanelToolbar = new JPanel();
    JPanel jPanelSouth = new JPanel();
    ColorPalette colorPalettePanel = new ColorPalette();
    Canvas canvas = new Canvas(800, 700);

    JMenuBar jMenuBarMain = new JMenuBar();
    JMenu jMenuMainFiles = new JMenu("Files");
    JMenuItem jMenuItem1 = new JMenuItem("test1");
    JMenuItem jMenuItem2 = new JMenuItem("test2");
    JMenuItem jMenuItem3 = new JMenuItem("test3");


    public MainFrame() {
        this.setTitle("Malování");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(jMenuBarMain);
        this.add(jPanelMainContent, BorderLayout.CENTER);
        this.add(jPanelSouth, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.setPreferredSize(new Dimension(getWidth(), getHeight()));
                repaint();
            }
        });

        jPanelMainContent.add(canvas);

        jPanelSouth.setLayout(new FlowLayout(FlowLayout.LEFT));

        jPanelSouth.add(colorPalettePanel);
        jPanelMainContent.setBackground(Color.RED);

        jMenuBarMain.add(jMenuMainFiles);
        jMenuMainFiles.add(jMenuItem1);
        jMenuMainFiles.add(jMenuItem2);
        jMenuMainFiles.add(jMenuItem3);
    }
}

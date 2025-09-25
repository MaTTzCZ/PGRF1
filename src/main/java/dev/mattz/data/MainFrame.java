package dev.mattz.data;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JPanel jPanelMain = new JPanel();
    JPanel jPanelCenter = new JPanel();
    JMenuBar jMenuBarMain = new JMenuBar();
    JMenuBar jMenuBarMode = new JMenuBar();

    JMenu JMenuMode = new JMenu();
    JMenu jMenuFile = new  JMenu("File");
    JMenuItem jMenuItem1 = new JMenuItem("Text2");
    JMenuItem jMenuItem2 = new JMenuItem("Text2");
    JMenuItem jMenuItem3 = new JMenuItem("Text3");

    JComboBox<String> jComboBoxPixelUnits = new JComboBox<>();

    public MainFrame() {
        jPanelMain.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.setTitle("Malování");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(jMenuBarMain, BorderLayout.NORTH);
        this.add(jPanelCenter, BorderLayout.CENTER);
        jMenuBarMode.add(new JButton("NEGR"));
        jPanelCenter.add(jMenuBarMode, BorderLayout.NORTH);

        jMenuBarMain.add(jMenuFile);
        jMenuFile.add(jMenuItem1);
        jMenuFile.add(jMenuItem2);
        jMenuFile.add(jMenuItem3);
    }
}

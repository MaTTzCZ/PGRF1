package dev.mattz.data.D2.gui.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPaletteView extends JPanel {
    JButton jButtonPrimaryColor = new JButton();
    JButton jButtonSecondaryColor = new JButton();

    JButton newButton;


    public ColorPaletteView() {

        this.setPreferredSize(new Dimension(330, 40));
        this.setLayout(null);
        this.add(jButtonPrimaryColor);
        this.add(jButtonSecondaryColor);

        jButtonPrimaryColor.setBackground(Color.BLACK);
        jButtonPrimaryColor.setBounds(10, 5, 20, 20);
        jButtonPrimaryColor.setEnabled(false);

        jButtonSecondaryColor.setBackground(Color.WHITE);
        jButtonSecondaryColor.setBounds(20, 15, 20, 20);
        jButtonSecondaryColor.setEnabled(false);
    }

    public void createButton(int i, int j, Color color, MouseAdapter adapter) {
        newButton = new JButton();
        newButton.setBackground(color);
        newButton.setFocusable(false);
        newButton.setBounds(i * 20 + 50, j * 20, 20, 20);
        newButton.addMouseListener(adapter);
        newButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) jButtonPrimaryColor.setBackground(color);
                else if(SwingUtilities.isRightMouseButton(e)) jButtonSecondaryColor.setBackground(color);
            }
        });
        this.add(newButton);
    }
}

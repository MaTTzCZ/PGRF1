package dev.mattz.data.gui.views;

import dev.mattz.data.gui.controllers.ColorPaletteController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPaletteView extends JPanel {
    JButton jButtonPrimaryColor = new JButton();
    JButton jButtonSecondaryColor = new JButton();

    private final ColorPaletteController controller;

    public ColorPaletteView(ColorPaletteController controller) {
        this.controller = controller;

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

        addButtons();
    }

    private void addButtons() {
        for (int i = 0; i < controller.getSize() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                JButton jButtonColor = new JButton();
                jButtonColor.setBackground(controller.getColor(i * 2 + j));
                jButtonColor.setBounds(i * 20 + 50, j * 20, 20, 20);
                int finalI = i;
                int finalJ = j;
                jButtonColor.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            controller.setPrimaryColor(finalI * 2 + finalJ);
                            jButtonPrimaryColor.setBackground(controller.getPrimaryColor());
                        }
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            controller.setSecondaryColor(finalI * 2 + finalJ);
                            jButtonPrimaryColor.setBackground(controller.getSecondaryColor());
                        }
                    }
                });
                this.add(jButtonColor);
            }
        }
    }


}
